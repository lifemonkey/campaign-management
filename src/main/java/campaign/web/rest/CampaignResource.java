package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.CampaignService;
import campaign.service.dto.CampaignDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.CampaignVM;
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
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final CampaignService campaignService;

    public CampaignResource(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * GET /campaigns : get all target list.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/campaigns")
    @Timed
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns(Pageable pageable) {
        Page<CampaignDTO> page = campaignService.getAllCampaign(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /campaigns : get all target list.
     *
     * @PathVariable id campaign id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/campaign/{id}")
    @Timed
    public ResponseEntity<CampaignDTO> getCampaignById(@Valid @PathVariable Long id) {
        CampaignDTO campaign = campaignService.getCampaignById(id);
        return ResponseEntity.ok(campaign);
    }

    /**
     * POST /campaign : Create campaign
     *
     * @RequestBody campaign information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/campaign")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignVM campaignVM) {
        return new ResponseEntity<> (campaignService.createCampaign(campaignVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /campaign : Update campaign
     *
     * @RequestBody campaign information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/campaign/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<CampaignDTO> updateCampaign(@Valid @PathVariable Long id, @RequestBody CampaignVM campaignVM) {
        return new ResponseEntity<> (campaignService.updateCampaign(id, campaignVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /campaign : Update target list
     *
     * @PathVariable id of campaign
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @DeleteMapping("/campaign/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteCampaign(@Valid @PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
