package campaign.service.mapper;

import campaign.domain.Reward;
import campaign.domain.RewardCondition;
import campaign.domain.Rule;
import campaign.service.dto.RewardConditionDTO;
import campaign.web.rest.vm.RewardConditionVM;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardConditionMapper {

    private final RewardMapper rewardMapper;

    public RewardConditionMapper(RewardMapper rewardMapper) {
        this.rewardMapper = rewardMapper;
    }

    public RewardConditionDTO rewardConditionToRewardConditionDTO(RewardCondition rewardCondition) {
        return new RewardConditionDTO(rewardCondition);
    }

    public List<RewardConditionDTO> rewardConditionToRewardConditionDTOs(List<RewardCondition> rewardConditionList) {
        return rewardConditionList.stream()
            .filter(Objects::nonNull)
            .map(this::rewardConditionToRewardConditionDTO)
            .collect(Collectors.toList());
    }

    public RewardCondition rewardConditionDTOToRewardCondition(RewardConditionDTO rewardConditionDTO) {
        if (rewardConditionDTO == null) {
            return null;
        } else {
            RewardCondition rewardCondition = new RewardCondition();
            rewardCondition.setId(rewardConditionDTO.getId());
            rewardCondition.setAmountMin(rewardConditionDTO.getAmountMin());
            rewardCondition.setAmountMax(rewardConditionDTO.getAmountMax());
            rewardCondition.setTimesMin(rewardConditionDTO.getTimesMin());
            rewardCondition.setTimesMax(rewardConditionDTO.getTimesMax());
            rewardCondition.setNumberCodes(rewardConditionDTO.getNumberCodes());
            rewardCondition.setCreatedBy(rewardConditionDTO.getCreatedBy());
            rewardCondition.setCreatedDate(rewardConditionDTO.getCreatedDate());
            rewardCondition.setLastModifiedBy(rewardConditionDTO.getLastModifiedBy());
            rewardCondition.setLastModifiedDate(rewardConditionDTO.getLastModifiedDate());

            return rewardCondition;
        }
    }

    public List<RewardCondition> rewardConditionDTOToRewardConditions(List<RewardConditionDTO> rewardConditionDTOS) {
        return rewardConditionDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::rewardConditionDTOToRewardCondition)
            .collect(Collectors.toList());
    }

    public RewardCondition rewardConditionVMToRewardCondition(RewardConditionVM rewardConditionVM) {
        if (rewardConditionVM == null) {
            return null;
        } else {
            RewardCondition rewardCondition = new RewardCondition();
            rewardCondition.setId(rewardConditionVM.getId());
            rewardCondition.setAmountMin(rewardConditionVM.getAmountMin());
            rewardCondition.setAmountMax(rewardConditionVM.getAmountMax());
            rewardCondition.setTimesMin(rewardConditionVM.getTimesMin());
            rewardCondition.setTimesMax(rewardConditionVM.getTimesMax());
            rewardCondition.setNumberCodes(rewardConditionVM.getNumberCodes());

            return rewardCondition;
        }
    }

    public RewardCondition rewardConditionVMToRewardCondition(RewardConditionVM rewardConditionVM, Rule rule, Reward reward) {
        if (rewardConditionVM == null) {
            return null;
        } else {
            RewardCondition rewardCondition = this.rewardConditionVMToRewardCondition(rewardConditionVM);
            rewardCondition.setRule(rule);

            if (reward != null) {
                rewardCondition.setReward(reward);
            }
            return rewardCondition;
        }
    }

    public List<RewardCondition> rewardConditionVMToRewardConditions(
        List<RewardConditionVM> rewardConditionVMs,
        Rule rule,
        List<Reward> rewardList
    ) {
        return rewardConditionVMs.stream()
            .filter(Objects::nonNull)
            .map(rewardConditionVM -> {
                Reward reward = rewardList.stream()
                    .filter(r -> rewardConditionVM.getRewardId() == r.getId())
                    .findFirst().orElse(null);
                return this.rewardConditionVMToRewardCondition(rewardConditionVM, rule, reward);
            }).collect(Collectors.toList());
    }

    public RewardCondition updateRewardCondition(RewardCondition rewardConditionInDb, RewardConditionVM rewardConditionVM) {
        if (rewardConditionVM == null || rewardConditionInDb == null) {
            return null;
        } else {
            RewardCondition rewardCondition = rewardConditionInDb;

            if (rewardConditionVM.getAmountMin() != null) {
                rewardCondition.setAmountMin(rewardConditionVM.getAmountMin());
            }
            if (rewardConditionVM.getAmountMax() != null) {
                rewardCondition.setAmountMax(rewardConditionVM.getAmountMax());
            }
            if (rewardConditionVM.getTimesMin() != null) {
                rewardCondition.setTimesMin(rewardConditionVM.getTimesMin());
            }
            if (rewardConditionVM.getTimesMax() !=null) {
                rewardCondition.setTimesMax(rewardConditionVM.getTimesMax());
            }
            if (rewardConditionVM.getNumberCodes() != null) {
                rewardCondition.setNumberCodes(rewardConditionVM.getNumberCodes());
            }

            return rewardCondition;
        }
    }
}
