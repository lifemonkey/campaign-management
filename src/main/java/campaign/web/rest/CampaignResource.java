package campaign.web.rest;

import campaign.domain.Campaign;
import campaign.security.AuthoritiesConstants;
import campaign.service.CampaignService;
import campaign.service.dto.CampaignDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.CampaignVM;
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
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private final CampaignService campaignService;

    public CampaignResource(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * GET /campaigns : get all campaigns
     *
     * @RequestParam searchValue for campaign search by name
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/campaigns/count")
    @Timed
    public ResponseEntity<Object> getTotalRecord() {
        return new ResponseEntity<Object>(campaignService.countRecords() , new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * GET /campaigns : get all campaigns
     *
     * @param pageable the pagination information
     * @RequestParam searchValue for campaign search by name
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/campaigns")
    @Timed
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns(Pageable pageable, @RequestParam(required = false) String search) {
        Page<CampaignDTO> page;
        if (search != null) {
            page = campaignService.searchCampaigns(pageable, search);
        } else {
            page = campaignService.getAllCampaign(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /campaigns : get all campaigns
     *
     * @PathVariable id campaign id
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/campaign/{id}")
    @Timed
    public ResponseEntity<Object> getCampaignById(@Valid @PathVariable Long id) {
        CampaignDTO campaign = campaignService.getCampaignById(id);
        if (campaign != null) {
            return new ResponseEntity<>(campaign, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_CAMPAIGN_NOT_FOUND,
                "Campaign ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
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
     * POST /campaign : Duplicate existing campaign
     *
     * @RequestParam to be clone campaign
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/campaign/clone")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestParam Long id) {
        return new ResponseEntity<> (campaignService.cloneCampaign(id), new HttpHeaders(), HttpStatus.OK);
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
    public ResponseEntity<Object> updateCampaign(@Valid @PathVariable Long id, @RequestBody CampaignVM campaignVM) {
        CampaignDTO campaign = campaignService.getCampaignById(id);
        if (campaign.getId() != null) {
            return new ResponseEntity<>(campaignService.updateCampaign(id, campaignVM), new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_CAMPAIGN_NOT_FOUND,
                "Campaign ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
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
