package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.FileImportService;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileImportResource {

    private final Logger log = LoggerFactory.getLogger(FileImportResource.class);

    private final FileImportService fileImportService;

    public FileImportResource(FileImportService fileImportService) {
        this.fileImportService = fileImportService;
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
    public ResponseEntity<String> importFile(@RequestParam MultipartFile file,
                                             @RequestParam(required = false) boolean overwrite) {
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        boolean isImported = false;
        switch (fileExt) {
            case "csv":
                isImported = fileImportService.importCsvFile(file, overwrite);
                break;
            case "xlsx":
                isImported = fileImportService.importExcelFile(file, overwrite);
                break;
        }

        return new ResponseEntity<> (
            isImported ? "Import successfully" : "Import failed",
            new HttpHeaders(),
            HttpStatus.OK);
    }

}
