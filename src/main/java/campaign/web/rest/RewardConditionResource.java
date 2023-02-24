package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.RewardConditionService;
import campaign.service.dto.RewardConditionDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import campaign.web.rest.vm.RewardConditionVM;
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
public class RewardConditionResource {

    private final Logger log = LoggerFactory.getLogger(RewardConditionResource.class);

    private final RewardConditionService rewardConditionService;

    public RewardConditionResource(RewardConditionService rewardConditionService) {
        this.rewardConditionService = rewardConditionService;
    }

    /**
     * GET /reward-conditions : get all rewardConditions
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/reward-conditions")
    @Timed
    public ResponseEntity<List<RewardConditionDTO>> getAllRewardConditions(
        Pageable pageable,
        @RequestParam(required = false) Float amountMin,
        @RequestParam(required = false) Float amountMax,
        @RequestParam(required = false) Integer timesMin,
        @RequestParam(required = false) Integer timesMax
    ) {
        Page<RewardConditionDTO> page = rewardConditionService.getAllRewardConditions(
            pageable, amountMin, amountMax, timesMin, timesMax);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reward-conditions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /reward-condition : get rewardCondition by id
     *
     * @PathVariable id rewardCondition id
     * @return the ResponseEntity with status 200 (OK) and with body all reward-conditions
     */
    @GetMapping("/reward-condition/{id}")
    @Timed
    public ResponseEntity<Object> getRewardConditionById(@Valid @PathVariable Long id) {
        RewardConditionDTO rewardConditionDTO = rewardConditionService.getRewardConditionById(id);
        if (rewardConditionDTO != null) {
            return new ResponseEntity<>(rewardConditionDTO, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_REWARD_CONDITION_NOT_FOUND,
                "Reward Condition ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * POST /reward-condition : Create rewardCondition
     *
     * @RequestBody reward-condition information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all reward-conditions
     */
    @PostMapping("/reward-condition")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<RewardConditionDTO> createRewardCondition(@RequestBody RewardConditionVM rewardConditionVM) {
        return new ResponseEntity<> (rewardConditionService.createRewardCondition(rewardConditionVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * POST /reward-condition : Duplicate existing reward-condition
     *
     * @RequestParam to be clone reward-condition
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PostMapping("/reward-condition/clone")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<RewardConditionDTO> cloneRewardCondition(@RequestParam Long id) {
        return new ResponseEntity<> (rewardConditionService.cloneRewardCondition(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /reward-condition : Update reward-condition
     *
     * @RequestBody reward-condition information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/reward-condition/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> updateRewardCondition(@Valid @PathVariable Long id, @RequestBody RewardConditionVM rewardConditionVM) {
        RewardConditionDTO rewardConditionDTO = rewardConditionService.getRewardConditionById(id);
        if (rewardConditionDTO.getId() == null) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_NOT_FOUND,
                    ResponseCode.ERROR_CODE_REWARD_CONDITION_NOT_FOUND,
                    "Reward Condition ID:" + id + " not found!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rewardConditionService.updateRewardCondition(id, rewardConditionVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /reward-condition : Delete rule
     *
     * @PathVariable id of reward-condition
     * @return the ResponseEntity with status 200 (OK) and with body all reward-conditions
     */
    @DeleteMapping("/reward-condition/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<String> deleteRewardCondition(@Valid @PathVariable Long id) {
        rewardConditionService.deleteRewardCondition(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
