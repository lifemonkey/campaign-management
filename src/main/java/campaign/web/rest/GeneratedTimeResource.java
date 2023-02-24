package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.GeneratedTimeService;
import campaign.service.dto.GeneratedTimeDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.GeneratedTimeVM;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneratedTimeResource {

    private final Logger log = LoggerFactory.getLogger(GeneratedTimeResource.class);

    private final GeneratedTimeService generatedTimeService;

    public GeneratedTimeResource(GeneratedTimeService generatedTimeService) {
        this.generatedTimeService = generatedTimeService;
    }

    /**
     * GET /generated-times : get all generatedTimes
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all generatedTimes
     */
    @GetMapping("/generated-times")
    @Timed
    public ResponseEntity<List<GeneratedTimeDTO>> getAllGeneratedTimes(Pageable pageable) {
        Page<GeneratedTimeDTO> page = generatedTimeService.getAllGeneratedTimes(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/generated-times");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /generated-time : get rewagenerated-time by id
     *
     * @PathVariable id generated-time id
     * @return the ResponseEntity with status 200 (OK) and with body all rewards
     */
    @GetMapping("/generated-time/{id}")
    @Timed
    public ResponseEntity<Object> getGeneratedTimeById(@Valid @PathVariable Long id) {
        GeneratedTimeDTO generatedTimeDTO = generatedTimeService.getGeneratedTimeById(id);
        if (generatedTimeDTO != null) {
            return new ResponseEntity<>(generatedTimeDTO, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_GENERATED_TIME_NOT_FOUND,
                "Generated Time ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * POST /generated-time : Create generated-time
     *
     * @RequestBody generated-time information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all generated-time
     */
    @PostMapping("/generated-time")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> createGeneratedTime(@RequestBody GeneratedTimeVM generatedTimeVM) {
        // validate request params
        if (generatedTimeVM.getStartTime() == null || generatedTimeVM.getEndTime() == null) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_GENERATED_TIME_IS_EMPTY,
                    "Generated time start/end time is empty!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<> (generatedTimeService.createGeneratedTime(generatedTimeVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * POST /generated-time : Duplicate existing generated-time
     *
     * @RequestParam to be clone generated-time
     * @return the ResponseEntity with status 200 (OK) and with body all generated-time
     */
    @PostMapping("/generated-time/clone")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<GeneratedTimeDTO> cloneGeneratedTime(@RequestParam Long id) {
        return new ResponseEntity<> (generatedTimeService.cloneGeneratedTime(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /generated-time : Update generated-time
     *
     * @RequestBody generated-time information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/generated-time/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> updateGeneratedTime(@Valid @PathVariable Long id, @RequestBody GeneratedTimeVM generatedTimeVM) {
        GeneratedTimeDTO generatedTimeDTO = generatedTimeService.getGeneratedTimeById(id);
        if (generatedTimeDTO.getId() == null) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_NOT_FOUND,
                    ResponseCode.ERROR_CODE_GENERATED_TIME_NOT_FOUND,
                    "Reward Condition ID:" + id + " not found!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(generatedTimeService.updateGeneratedTime(id, generatedTimeVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /generated-time : Delete generated-time
     *
     * @PathVariable id of generated-time
     * @return the ResponseEntity with status 200 (OK) and with body all generated-time
     */
    @DeleteMapping("/generated-time/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteGeneratedTime(@Valid @PathVariable Long id) {
        generatedTimeService.deleteGeneratedTime(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
