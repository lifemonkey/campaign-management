package campaign.web.rest;

import campaign.security.AuthoritiesConstants;
import campaign.service.RewardService;
import campaign.service.dto.RewardDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import campaign.web.rest.vm.RewardVM;
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
public class RewardResource {

    private final Logger log = LoggerFactory.getLogger(RewardResource.class);

    private final RewardService rewardService;

    public RewardResource(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * GET /rewards : get all rewards
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all rewards
     */
    @GetMapping("/rewards")
    @Timed
    public ResponseEntity<List<RewardDTO>> getAllRewards(
        Pageable pageable,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer type,
        @RequestParam(required = false) String appliedCampaign,
        @RequestParam(required = false) Integer campaignType,
        @RequestParam(required = false) boolean appliedRule,
        @RequestParam(required = false) boolean showTemplate
    ) {
        Page<RewardDTO> page = rewardService.searchRewards(
            pageable, search, type, appliedCampaign, campaignType, appliedRule, showTemplate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reward-conditions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /reward : get reward by id
     *
     * @PathVariable id reward id
     * @return the ResponseEntity with status 200 (OK) and with body all rewards
     */
    @GetMapping("/reward/{id}")
    @Timed
    public ResponseEntity<Object> getRewardConditionById(@Valid @PathVariable Long id) {
        RewardDTO rewardDTO = rewardService.getRewardById(id);
        if (rewardDTO != null) {
            return new ResponseEntity<>(rewardDTO, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_REWARD_NOT_FOUND,
                "Reward ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * POST /reward : Create reward
     *
     * @RequestBody reward information to be created
     * @return the ResponseEntity with status 200 (OK) and with body all reward
     */
    @PostMapping("/reward")
    @Timed
    public ResponseEntity<Object> createReward(@RequestBody RewardVM rewardVM) {
        // validate request params
        if (rewardVM.getName() == null || rewardVM.getName().isEmpty()) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_REWARD_NAME_IS_EMPTY,
                    "Reward name is empty!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        // check duplicated name
        if (rewardService.rewardNameExisted(null, rewardVM.getName())) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_REWARD_NAME_IS_DUPLICATED,
                    "Reward name is already existing!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        // validate voucher
        if (rewardService.isRewardVouchersValid(rewardVM.getVoucherCodes())) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_REWARD_VOUCHER_INVALID,
                    ResponseCode.RESPONSE_REWARD_VOUCHER_INVALID),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<> (rewardService.createReward(rewardVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * POST /reward : Duplicate existing reward
     *
     * @RequestParam to be clone reward
     * @return the ResponseEntity with status 200 (OK) and with body all reward
     */
    @PostMapping("/reward/clone")
    @Timed
    public ResponseEntity<RewardDTO> cloneReward(@RequestParam Long id) {
        return new ResponseEntity<> (rewardService.cloneReward(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /reward : Update reward
     *
     * @RequestBody reward information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/reward/{id}")
    @Timed
    public ResponseEntity<Object> updateReward(@Valid @PathVariable Long id, @RequestBody RewardVM rewardVM) {
        RewardDTO rewardDTO = rewardService.getRewardById(id);
        if (rewardDTO.getId() == null) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_NOT_FOUND,
                    ResponseCode.ERROR_CODE_REWARD_CONDITION_NOT_FOUND,
                    "Reward ID:" + id + " not found!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        // check duplicated name
        if (rewardService.rewardNameExisted(id, rewardVM.getName())) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_REWARD_NAME_IS_DUPLICATED,
                    ResponseCode.RESPONSE_REWARD_NAME_IS_DUPLICATED),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        // validate voucher
        if (rewardService.isRewardVouchersValid(rewardVM.getVoucherCodes())) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_WRONG_PARAM,
                    ResponseCode.ERROR_CODE_REWARD_VOUCHER_INVALID,
                    ResponseCode.RESPONSE_REWARD_VOUCHER_INVALID),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(rewardService.updateReward(id, rewardVM), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * PUT /reward : Set reward as template
     *
     * @RequestBody reward information to be updated
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @PutMapping("/reward/set-as-template/{id}")
    @Timed
    public ResponseEntity<Object> setAsTemplate(@Valid @PathVariable Long id) {
        RewardDTO rewardDTO = rewardService.getRewardById(id);
        if (rewardDTO.getId() == null) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_NOT_FOUND,
                    ResponseCode.ERROR_CODE_REWARD_NOT_FOUND,
                    "Reward ID:" + id + " not found!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        if (rewardService.hasAppliedRule(id)
            ||rewardDTO.getAppliedCampaign() != null || rewardDTO.getAppliedCampaign().getId() != null
        ) {
            return new ResponseEntity<>(
                new ResponseVM(
                    ResponseCode.RESPONSE_NOT_FOUND,
                    ResponseCode.ERROR_CODE_REWARD_TEMPLATE_CANNOT_BE_CLONED,
                    "Reward has applied campaign OR rule. Reward ID:" + id + " could not set as template!"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rewardService.setAsTemplate(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * DELETE /reward : Delete reward
     *
     * @PathVariable id of reward
     * @return the ResponseEntity with status 200 (OK) and with body all reward
     */
    @DeleteMapping("/reward/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.BO_STAFF + "')")
    public ResponseEntity<Object> deleteReward(@Valid @PathVariable Long id) {
        RewardDTO rewardDTO = rewardService.getRewardById(id);
        if (rewardDTO.getId() == null) {
            return new ResponseEntity<> (new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_REWARD_NOT_FOUND,
                "Reward ID:" + id + " not found!"),
                new HttpHeaders(),
                HttpStatus.OK);
        }
        // not allow to delete rule with appliedCampaign
        if (rewardDTO.getAppliedCampaign() != null || rewardService.hasAppliedRule(id)) {
            return new ResponseEntity<> (new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_REWARD_CANNOT_BE_DELETED,
                "Could not delete appliedCampaign / appliedRule prizeId:" + id + ""),
                new HttpHeaders(),
                HttpStatus.OK);
        }

        rewardService.deleteReward(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
