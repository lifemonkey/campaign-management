package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.TargetListService;
import campaign.service.dto.TargetListDTO;
import campaign.web.rest.util.PaginationUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * GET /targetList : get all target list.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/target-list")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<List<TargetListDTO>> getAllTargetList(Pageable pageable) {
        Page<TargetListDTO> page = targetListService.getAllTargetList(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/target-list");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
