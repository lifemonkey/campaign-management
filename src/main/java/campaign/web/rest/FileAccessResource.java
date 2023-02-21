package campaign.web.rest;

import campaign.service.FileService;
import io.micrometer.core.annotation.Timed;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/files-storage/")
public class FileAccessResource {

    private final FileService fileService;

    public FileAccessResource(FileService fileService) {
        this.fileService = fileService;
    }


    /**
     * GET /file : get file by id
     *
     * @PathVariable id file id
     * @return the ResponseEntity with status 200 (OK) and with body all files
     */
    @GetMapping("/file/{name}")
    @Timed
    public ResponseEntity<Object> downloadFile(@PathVariable String name) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(name);
        return new ResponseEntity<>(fileService.loadFileAsResource(name), new HttpHeaders(), HttpStatus.OK);
        //HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    }
}
