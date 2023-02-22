package campaign.web.rest;

import campaign.service.StatusService;
import campaign.service.dto.StatusDTO;
import campaign.web.rest.util.PaginationUtil;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private final StatusService statusService;

    public StatusResource(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * GET /statuses : get all status
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/statuses")
    @Timed
    public ResponseEntity<List<StatusDTO>> getAllStatus(Pageable pageable, @RequestParam(required = false) String search) {
        Page<StatusDTO> page = statusService.searchStatus(pageable, search);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /status : get status by id
     *
     * @PathVariable id status id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/status/{id}")
    @Timed
    public ResponseEntity<Object> getStatusById(@Valid @PathVariable Long id) {
        StatusDTO status = statusService.getStatusById(id);
        if (status != null) {
            return new ResponseEntity<>(status, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_RECORD_NOT_EXISTED,
                "Status ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

}
