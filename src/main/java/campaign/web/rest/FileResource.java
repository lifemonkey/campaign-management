package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.FileService;
import campaign.service.dto.FileDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.FileVM;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private final FileService fileService;

    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * GET /files : get all files
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/files")
    @Timed
    public ResponseEntity<List<FileDTO>> getAllFiles(Pageable pageable) {
        Page<FileDTO> page = fileService.getAllFiles(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /file : get file by id
     *
     * @PathVariable id file id
     * @return the ResponseEntity with status 200 (OK) and with body all files
     */
    @GetMapping("/file/{id}")
    @Timed
    public ResponseEntity<Object> getFileById(@Valid @PathVariable Long id) {
        FileDTO file = fileService.getFileById(id);
        if (file != null) {
            return new ResponseEntity<>(file, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_FILE_NOT_FOUND,
                "File ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * POST /file/upload : upload a file
     *
     * @RequestBody file information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/file/upload")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<String> createRule(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<> (fileService.uploadFile(file), new HttpHeaders(), HttpStatus.OK);
    }

//    /**
//     * PUT /file : Update file
//     *
//     * @RequestBody file information to be updated
//     * @return the ResponseEntity with status 200 (OK) and with body all users
//     */
//    @PutMapping("/file/{id}")
//    @Timed
//    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
//    public ResponseEntity<Object> updateFile(@Valid @PathVariable Long id, @RequestBody FileVM fileVM) {
//        FileDTO file = fileService.getFileById(id);
//        if (file.getId() != null) {
//            return new ResponseEntity<>(fileService.updateFile(id, fileVM), new HttpHeaders(), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(
//            new ResponseVM(
//                ResponseCode.RESPONSE_NOT_FOUND,
//                ResponseCode.ERROR_CODE_FILE_NOT_FOUND,
//                "File ID:" + id + " not found!"),
//            new HttpHeaders(),
//            HttpStatus.NOT_FOUND);
//    }

    /**
     * PUT /file : Update file
     *
     * @PathVariable file id
     * @PathVariable campaign id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/file/{id}/campaign/{campaignId}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<FileDTO> updateFileCampaign(@Valid @PathVariable Long id, @Valid @PathVariable Long campaignId) {
        return new ResponseEntity<> (fileService.updateFileCampaign(id, campaignId), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /file : Delete file
     *
     * @PathVariable id of file
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @DeleteMapping("/file/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteFile(@Valid @PathVariable Long id) {
        fileService.deleteFile(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
