package campaign.web.rest;

import campaign.service.FileService;
import campaign.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/file/")
public class FilePreviewResource {

    private final FileService fileService;

    public FilePreviewResource(FileService fileService) {
        this.fileService = fileService;
    }


    /**
     * GET /file : get file by id
     *
     * @PathVariable id file id
     * @return the ResponseEntity with status 200 (OK) and with body all files
     */
    @GetMapping("/preview/{name}")
    @Timed
    public ResponseEntity<Resource> previewFile(@PathVariable String name) throws IOException {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + name + "\"")
            .contentType(HeaderUtil.getImageMediaType(FilenameUtils.getExtension(name)))
            .body(fileService.loadFileAsResource(name));
    }
}
