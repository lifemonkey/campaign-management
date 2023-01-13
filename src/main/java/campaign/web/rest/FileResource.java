package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.FileService;
import campaign.service.dto.FileDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    public ResponseEntity<List<FileDTO>> getAllFiles(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer type
    ) {
        Page<FileDTO> page;
        if (search != null || type != null) {
            page = fileService.searchFiles(pageable, search, type);
        } else {
            page = fileService.getAllFiles(pageable);
        }
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
     * GET /file : get file by id
     *
     * @PathVariable id file id
     * @return the ResponseEntity with status 200 (OK) and with body all files
     */
    @GetMapping("/file/download")
    @Timed
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
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
    public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<> (fileService.uploadFile(file), new HttpHeaders(), HttpStatus.OK);
    }

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
