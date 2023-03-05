package campaign.web.rest;

import campaign.config.Constants;
import campaign.excel.ExcelField;
import campaign.security.AuthoritiesConstants;
import campaign.service.FileExportService;
import campaign.service.FileImportService;
import campaign.service.dto.VoucherDTO;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImportExportResource {

    private final Logger log = LoggerFactory.getLogger(ImportExportResource.class);

    private final FileImportService fileImportService;

    private final FileExportService fileExportService;

    public ImportExportResource(FileImportService fileImportService, FileExportService fileExportService) {
        this.fileImportService = fileImportService;
        this.fileExportService = fileExportService;
    }

    /**
     * POST /file/import : import a file to db
     *
     * @RequestBody file data to be imported
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/file/import")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> importFile(@RequestParam MultipartFile file,
                                             @RequestParam(required = false) boolean overwrite) {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!fileExt.equalsIgnoreCase("csv")
            && !fileExt.equalsIgnoreCase("xlsx")
            && !fileExt.equalsIgnoreCase("xls")
        ) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_EXTENSION_INVALID,
                "File could not be imported. File extension is not supported: " + fileExt),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        boolean isImported = false;
        switch (fileExt) {
            case "csv":
                isImported = fileImportService.importCsvFile(file, overwrite);
                break;
            case "xlsx":
            case "xls":
                isImported = fileImportService.importExcelFile(file, overwrite);
                break;
        }

        return new ResponseEntity<> (
            isImported ? "Import successfully" : "Invalid format, please check again!",
            new HttpHeaders(),
            HttpStatus.OK);
    }

    /**
     * POST /file/read : read a file a return list
     *
     * @RequestBody file data to be read
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/file/read")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> readFile(@RequestParam MultipartFile file) {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!fileExt.equalsIgnoreCase("xlsx") && !fileExt.equalsIgnoreCase("xls")) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_EXTENSION_INVALID,
                "File could not be read. File extension is not supported: " + fileExt),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        List<ExcelField[]> excelFields = fileImportService.getExcelFields(file);
        if (excelFields.isEmpty()) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_CONTENT_EMPTY,
                "File is empty OR could not read file content, file name: " + file.getOriginalFilename()),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        if (fileImportService.requireFieldMissing(excelFields)) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_CONTENT_REQUIRED_FIELD_MISSING,
                "File is empty OR could not read file content, file name: " + file.getOriginalFilename()),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        ExcelField excelField = fileImportService.invalidateDateFormat(excelFields);
        if (excelField != null) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_CONTENT_DATE_FIELD_WRONG_FORMAT,
                "Column " + excelField.getExcelHeader() + " has has wrong format. "
                    + "Current format is " + excelField.getCellFormat() + " "
                    + "Expectation is " + Constants.DATE_FORMAT_DD_MM_YYY),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        // validate field length
        excelField = fileImportService.fieldLengthTooLong(excelFields);
        if (excelField != null) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_CONTENT_FIELD_LENGTH_LIMIT,
                "Column " + excelField.getExcelHeader() + " has value " + excelField.getExcelValue() + " which has length greater than " + excelField.getMaxLength()),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        // validate effective date and expired date
        List<VoucherDTO> voucherList = fileImportService.convertToVoucherList(excelFields);
        if (voucherList.stream().filter(voucher -> inValidVoucher(voucher)).findAny().isPresent()) {
            return new ResponseEntity<>(new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_CONTENT_DATE_FIELD_WRONG_FORMAT,
                "Effective date greater than Expired date OR less than today"),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        return new ResponseEntity<> (voucherList, new HttpHeaders(), HttpStatus.OK);
    }

    private boolean inValidVoucher(VoucherDTO voucher) {
        return voucher.getStartDate().isBefore(LocalDateTime.now())
            || voucher.getExpiredDate().isBefore(LocalDateTime.now())
            || voucher.getExpiredDate().isBefore(voucher.getStartDate());
    }

    /**
     * POST /file/export : export a file
     *
     * @RequestBody file data to be imported
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/file/export")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> exportFile(@RequestParam String name,
                                             @RequestParam(required = false) String type) {
        Resource resource = fileExportService.exportFileAsResource(name, type);
        if (resource != null) {
            return new ResponseEntity<>(resource, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_EXPORT_FAILED,
                "Could not export file name:" + name + " type:" + type + ". Reason: this feature is not supported yet"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

}
