package campaign.service.mapper;

import campaign.domain.RewardCondition;
import campaign.service.dto.RewardConditionDTO;
import campaign.web.rest.vm.RewardConditionVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

//            if (rewardConditionDTO.getRuleId() != null) {
//                rewardCondition.setRule(rewardConditionDTO.getRuleId());
//            }
            if (rewardConditionDTO.getRewardDTO() != null) {
                rewardCondition.setReward(rewardMapper.rewardDTOToReward(rewardConditionDTO.getRewardDTO()));
            }

            rewardCondition.setCreatedBy(rewardConditionDTO.getCreatedBy());
            rewardCondition.setCreatedDate(rewardConditionDTO.getCreatedDate());
            rewardCondition.setLastModifiedBy(rewardConditionDTO.getLastModifiedBy());
            rewardCondition.setLastModifiedDate(rewardConditionDTO.getLastModifiedDate());

            return rewardCondition;
        }
    }

    public List<RewardCondition> ruleDTOToRules(List<RewardConditionDTO> rewardConditionDTOS) {
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

//            if (rewardConditionDTO.getRuleId() != null) {
//                rewardCondition.setRule(rewardConditionDTO.getRuleId());
//            }
//            if (rewardConditionVM.getRewardDTO() != null) {
//                rewardCondition.setReward(rewardMapper.rewardDTOToReward(rewardConditionVM.getRewardDTO()));
//            }

            return rewardCondition;
        }
    }
}
