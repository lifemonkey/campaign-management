package campaign.service;

import campaign.config.Constants;
import campaign.domain.TransactionType;
import campaign.domain.Voucher;
import campaign.excel.*;
import campaign.repository.TransactionTypeRepository;
import campaign.repository.VoucherRepository;
import campaign.service.dto.VoucherDTO;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileImportService {

    private final TransactionTypeRepository transactionTypeRepository;

    private final VoucherRepository voucherRepository;

    public FileImportService(TransactionTypeRepository transactionTypeRepository,VoucherRepository voucherRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.voucherRepository = voucherRepository;
    }

    public boolean importCsvFile(MultipartFile multipartFile, boolean overwrite) {
        try {
            List<TransactionType> transactionTypes = convertTransTypeFromCsv(multipartFile.getInputStream());
            if (!transactionTypes.isEmpty()) {
                // if overwritten, delete all records then saveAll
                if (overwrite) {
                    List<TransactionType> toBeDeleted = transactionTypeRepository.findAll();
                    transactionTypeRepository.saveAll(
                        toBeDeleted.stream().map(TransactionType::clearRuleList).collect(Collectors.toList()));
                    transactionTypeRepository.deleteAll();
                }
                // else just saveAll
                transactionTypeRepository.saveAll(transactionTypes);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<ExcelField[]> getExcelFields(MultipartFile multipartFile) {
        try {
            XSSFSheet sheet = ExcelReader.readInputStream(multipartFile.getInputStream());
            Map<String, List<ExcelField[]>> excelRowValuesMap = ExcelReader.getExcelRowValues(sheet);
            return excelRowValuesMap.get(ExcelSection.VOUCHERS.getValue());
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        return Arrays.asList();
    }

    public List<VoucherDTO> convertToVoucherList(List<ExcelField[]> excelFields) {
        if (excelFields.isEmpty()) return Arrays.asList();

        return ExcelFieldMapper.getPojos(excelFields, Voucher.class).stream()
            .map(VoucherDTO::new)
            .collect(Collectors.toList());
    }

    public boolean requireFieldMissing(List<ExcelField[]> excelFields){
        return excelFields.stream()
            .filter(efs -> Arrays.stream(efs)
                .filter(ef -> ef.isRequired() && ef.getExcelValue().isEmpty())
                .findAny().isPresent())
            .findAny().isPresent();
    }

    public ExcelField invalidateDateFormat(List<ExcelField[]> excelFieldsList){
        ExcelField excelField = null;
        for (ExcelField[] excelFields : excelFieldsList) {
            for (ExcelField ef : excelFields) {
                if (FieldType.DATETIME.getValue().equalsIgnoreCase(ef.getExcelColType())
                    && !Constants.DATE_FORMAT_DD_MM_YYY.equalsIgnoreCase(ef.getCellFormat())
                    && !isValidDateString(ef.getExcelValue())
                ) {
                    excelField = ef;
                    break;
                }
            }
        }
        return excelField;
    }

    private boolean isValidDateString(String dateValue) {
        String[] splittedDate = dateValue.split("/");
        if (splittedDate.length == 3 && splittedDate[0].length() == 2 && splittedDate[1].length() == 2 && splittedDate[2].length() == 4) {
            return true;
        }
        return false;
    }

    public boolean isValidDateFormat(SimpleDateFormat sdf, String dateValue) {
        try {
            sdf.parse(dateValue);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ExcelField fieldLengthTooLong(List<ExcelField[]> excelFieldsList){
        ExcelField excelField = null;
        for (ExcelField[] excelFields : excelFieldsList) {
            for (ExcelField ef : excelFields) {
                if (ef.getMaxLength() > 0 && !ef.getExcelValue().isEmpty() && ef.getExcelValue().length() > ef.getMaxLength()) {
                    excelField = ef;
                    break;
                }
            }
        }

        return excelField;
    }

    public boolean importExcelFile(MultipartFile multipartFile, boolean overwrite) {
        try {
            XSSFSheet sheet = ExcelReader.readInputStream(multipartFile.getInputStream());
            Map<String, List<ExcelField[]>> excelRowValuesMap = ExcelReader.getExcelRowValues(sheet);
            List<ExcelField[]> excelFields = excelRowValuesMap.get(ExcelSection.VOUCHERS.getValue());
            List<Voucher> voucherList = new ArrayList<>();
            if (requireFieldMissing(excelFields) || fieldLengthTooLong(excelFields) == null) {
                voucherList = ExcelFieldMapper.getPojos(excelFields, Voucher.class);
            }
            if (voucherList.isEmpty()) return false;

            // if overwritten, delete all records then saveAll
            if (overwrite) {
                List<Voucher> toBeDeleted = voucherRepository.findAll();
                voucherRepository.saveAll(toBeDeleted.stream().map(Voucher::removeReward).collect(Collectors.toList()));
                voucherRepository.deleteAll();
            }
            // else just saveAll
            voucherRepository.saveAll(voucherList);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<TransactionType> convertTransTypeFromCsv(InputStream inputStream) {
        try {
            Iterable<CSVRecord> csvRecords = FileImportUtils.readCsvFile(inputStream);
            List<TransactionType> transactionTypes = new ArrayList<>();
            // Till there is an element condition holds true
            for (CSVRecord csvRecord : csvRecords) {
                transactionTypes.add(new TransactionType()
                        .externalId(csvRecord.get("ID"))
                        .name(csvRecord.get("NAME"))
                        .description(csvRecord.get("DESCRIPTIONS"))
                        .status(Integer.parseInt(csvRecord.get("STATUS")))
                        .transTypeEN(csvRecord.get("TRANS_TYPE_ENGLISH"))
                        .transTypeSW(csvRecord.get("TRANS_TYPE_SWAHILI"))
                );
            }

            return transactionTypes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
