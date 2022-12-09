package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.CampaignService;
import campaign.service.RuleService;
import campaign.service.dto.CampaignDTO;
import campaign.service.dto.RuleDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.CampaignVM;
import campaign.web.rest.vm.RuleVM;
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
public class RuleResource {

    private final Logger log = LoggerFactory.getLogger(RuleResource.class);

    private final RuleService ruleService;

    public RuleResource(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * GET /rules : get all rules
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/rules")
    @Timed
    public ResponseEntity<List<RuleDTO>> getAllRules(Pageable pageable) {
        Page<RuleDTO> page = ruleService.getAllRules(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /rule : get rule by id
     *
     * @PathVariable id rule id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/rule/{id}")
    @Timed
    public ResponseEntity<RuleDTO> getRuleById(@Valid @PathVariable Long id) {
        RuleDTO ruleDTO = ruleService.getRuleById(id);
        return ResponseEntity.ok(ruleDTO);
    }

    /**
     * POST /rule : Create rule
     *
     * @RequestBody rule information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/rule")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<RuleDTO> createRule(@RequestBody RuleVM ruleVM) {
        return new ResponseEntity<> (ruleService.createRule(ruleVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /rule : Update rule
     *
     * @RequestBody rule information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/rule/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<RuleDTO> updateCampaign(@Valid @PathVariable Long id, @RequestBody RuleVM ruleVM) {
        return new ResponseEntity<> (ruleService.updateRule(id, ruleVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /rule : Delete rule
     *
     * @PathVariable id of rule
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @DeleteMapping("/rule/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteRule(@Valid @PathVariable Long id) {
        ruleService.deleteRule(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
