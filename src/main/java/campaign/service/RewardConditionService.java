package campaign.service;

import campaign.domain.Reward;
import campaign.domain.RewardCondition;
import campaign.domain.Rule;
import campaign.repository.*;
import campaign.service.dto.RewardConditionDTO;
import campaign.service.mapper.RewardConditionMapper;
import campaign.web.rest.vm.RewardConditionVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RewardConditionService {

    private final Logger log = LoggerFactory.getLogger(RewardConditionService.class);

    private final RewardConditionRepository rewardConditionRepository;

    private final RuleRepository ruleRepository;

    private final RewardRepository rewardRepository;

    private final RewardConditionMapper rewardConditionMapper;

    public RewardConditionService(RewardConditionRepository rewardConditionRepository,
                                  RuleRepository ruleRepository,
                                  RewardRepository rewardRepository,
                                  RewardConditionMapper rewardConditionMapper
    ) {
        this.rewardConditionRepository = rewardConditionRepository;
        this.ruleRepository = ruleRepository;
        this.rewardRepository = rewardRepository;
        this.rewardConditionMapper = rewardConditionMapper;
    }

    @Transactional(readOnly = true)
    public RewardConditionDTO getRewardConditionById(Long id) {
        Optional<RewardCondition> rewardConditionOpt = rewardConditionRepository.findById(id);
        if (rewardConditionOpt.isPresent()) {
            return rewardConditionMapper.rewardConditionToRewardConditionDTO(rewardConditionOpt.get());
        }

        return new RewardConditionDTO();
    }

    @Transactional(readOnly = true)
    public Page<RewardConditionDTO> getAllRewardConditions(Pageable pageable) {
        return rewardConditionRepository.findAll(pageable).map(RewardConditionDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardConditionDTO createRewardCondition(RewardConditionVM rewardConditionVM) {
        RewardCondition rewardCondition = rewardConditionMapper.rewardConditionVMToRewardCondition(rewardConditionVM);

        if (rewardCondition != null) {
            Optional<Rule> ruleOpt = ruleRepository.findById(rewardConditionVM.getRuleId());
            if (ruleOpt.isPresent()) {
                rewardCondition.setRule(ruleOpt.get());
            }

            Optional<Reward> rewardOpt = rewardRepository.findById(rewardConditionVM.getRewardId());
            if (rewardOpt.isPresent()) {
                rewardCondition.setReward(rewardOpt.get());
            }

            return rewardConditionMapper.rewardConditionToRewardConditionDTO(rewardConditionRepository.save(rewardCondition));
        }

        return new RewardConditionDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardConditionDTO cloneRewardCondition(Long id) {
        Optional<RewardCondition> rewardConditionOpt = rewardConditionRepository.findById(id);
        RewardCondition toBerInserted = new RewardCondition();

        if (rewardConditionOpt.isPresent()) {
            toBerInserted.setAmountMin(rewardConditionOpt.get().getAmountMin());
            toBerInserted.setAmountMax(rewardConditionOpt.get().getAmountMax());
            toBerInserted.setTimesMin(rewardConditionOpt.get().getTimesMin());
            toBerInserted.setTimesMax(rewardConditionOpt.get().getTimesMax());
            toBerInserted.setNumberCodes(rewardConditionOpt.get().getNumberCodes());

            if (rewardConditionOpt.get().getRule() != null) {
                toBerInserted.setRule(rewardConditionOpt.get().getRule());
            }
            if (rewardConditionOpt.get().getReward() != null) {
                toBerInserted.setReward(rewardConditionOpt.get().getReward());
            }

            toBerInserted.setCreatedBy(rewardConditionOpt.get().getCreatedBy());
            toBerInserted.setCreatedDate(rewardConditionOpt.get().getCreatedDate());
        }

        return rewardConditionMapper.rewardConditionToRewardConditionDTO(rewardConditionRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardConditionDTO updateRewardCondition(Long id, RewardConditionVM rewardConditionVM) {
        RewardCondition rewardCondition = rewardConditionMapper.rewardConditionVMToRewardCondition(rewardConditionVM);
        rewardCondition.setId(id);

        Optional<Rule> ruleOpt = rewardConditionVM.getRuleId() != null
            ? ruleRepository.findById(rewardConditionVM.getRuleId())
            : null;
        if (ruleOpt.isPresent()) {
            rewardCondition.setRule(ruleOpt.get());
        }

        Optional<Reward> rewardOpt = rewardConditionVM.getRewardId() != null
            ? rewardRepository.findById(rewardConditionVM.getRewardId())
            : null;
        if (rewardOpt.isPresent()) {
            rewardCondition.setReward(rewardOpt.get());
        }

        rewardConditionRepository.save(rewardCondition);
        return rewardConditionMapper.rewardConditionToRewardConditionDTO(rewardCondition);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRewardCondition(Long id) {
        rewardConditionRepository.deleteById(id);
    }
}
