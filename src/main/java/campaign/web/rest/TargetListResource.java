package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.TargetListService;
import campaign.service.dto.TargetListDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.TargetListVM;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TargetListResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final TargetListService targetListService;

    public TargetListResource(TargetListService targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * GET /target-list : get all target list.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/target-list")
    @Timed
    public ResponseEntity<List<TargetListDTO>> getAllTargetList(Pageable pageable) {
        Page<TargetListDTO> page = targetListService.getAllTargetList(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/target-list");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /target-list/{id} : get target-list by id
     *
     * @PathParm pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/target-list/{id}")
    @Timed
    public ResponseEntity<TargetListDTO> getTargetListById(@Valid @PathVariable Long id) {
        return new ResponseEntity<>(targetListService.getTargetListById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * POST /target-list : Create target list
     *
     * @RequestBody target list information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/target-list")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<TargetListDTO> createTargetList(@RequestBody TargetListVM targetListVM) {
        return new ResponseEntity<> (targetListService.createTargetList(targetListVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /target-list : Update target list
     *
     * @RequestBody target list information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/target-list/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<TargetListDTO> updateTargetList(@Validated @PathVariable Long id, @RequestBody TargetListVM targetListVM) {
        return new ResponseEntity<> (targetListService.updateTargetList(id, targetListVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /target-list : Update target list
     *
     * @PathVariable id of target-list
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @DeleteMapping("/target-list/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> updateTargetList(@Validated @PathVariable Long id) {
        targetListService.deleteTargetList(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
