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
        @RequestParam(required = false) Integer type
    ) {
        Page<RewardDTO> page;

        if (search != null || type != null) {
            page = rewardService.searchRewards(pageable, search, type);
        } else {
            page = rewardService.getAllRewards(pageable);
        }
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
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<RewardDTO> createReward(@RequestBody RewardVM rewardVM) {
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
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
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
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<Object> updateReward(@Valid @PathVariable Long id, @RequestBody RewardVM rewardVM) {
        RewardDTO rewardDTO = rewardService.getRewardById(id);
        if (rewardDTO.getId() != null) {
            return new ResponseEntity<>(rewardService.updateReward(id, rewardVM), new HttpHeaders(), HttpStatus.OK);
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
     * DELETE /reward : Delete reward
     *
     * @PathVariable id of reward
     * @return the ResponseEntity with status 200 (OK) and with body all reward
     */
    @DeleteMapping("/reward/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<String> deleteReward(@Valid @PathVariable Long id) {
        rewardService.deleteReward(id);
        return new ResponseEntity<> ("Delete successfully", new HttpHeaders(), HttpStatus.OK);
    }
}
