package campaign.service.mapper;

import campaign.domain.Reward;
import campaign.service.dto.RewardDTO;
import campaign.web.rest.vm.RewardVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RewardMapper {

    public RewardMapper() {
    }

    public RewardDTO rewardToRewardDTO(Reward reward) {
        return new RewardDTO(reward);
    }

    public List<RewardDTO> rewardToRewardDTOs(List<Reward> rewardList) {
        return rewardList.stream()
            .filter(Objects::nonNull)
            .map(this::rewardToRewardDTO)
            .collect(Collectors.toList());
    }

    public Reward rewardDTOToReward(RewardDTO rewardDTO) {
        if (rewardDTO == null) {
            return null;
        } else {
            Reward reward = new Reward();
            reward.setId(rewardDTO.getId());
            reward.setName(rewardDTO.getName());
            reward.setDescription(rewardDTO.getDescription());

            reward.setPrizeType(rewardDTO.getPrizeType());
            reward.setPrizeValue(rewardDTO.getPrizeValue());
            reward.setNumOfPrize(rewardDTO.getNumOfPrize());
            reward.setReleased(rewardDTO.getReleased());
            reward.setMessageWinnerEN(rewardDTO.getMessageWinnerEN());
            reward.setMessageWinnerSW(rewardDTO.getMessageWinnerSW());
            reward.setMessageBalanceEN(rewardDTO.getMessageBalanceEN());
            reward.setMessageBalanceSW(rewardDTO.getMessageBalanceSW());

            reward.setCreatedBy(rewardDTO.getCreatedBy());
            reward.setCreatedDate(rewardDTO.getCreatedDate());
            reward.setLastModifiedBy(rewardDTO.getLastModifiedBy());
            reward.setLastModifiedDate(rewardDTO.getLastModifiedDate());

            return reward;
        }
    }

    public List<Reward> rewardDTOToRewards(List<RewardDTO> rewardDTOList) {
        return rewardDTOList.stream()
            .filter(Objects::nonNull)
            .map(this::rewardDTOToReward)
            .collect(Collectors.toList());
    }

    public Reward rewardVMToReward(RewardVM rewardVM) {
        if (rewardVM == null) {
            return null;
        } else {
            Reward reward = new Reward();
            reward.setId(rewardVM.getId());
            reward.setName(rewardVM.getName());
            reward.setDescription(rewardVM.getDescription());

            reward.setPrizeType(rewardVM.getPrizeType());
            reward.setPrizeValue(rewardVM.getPrizeValue());
            reward.setNumOfPrize(rewardVM.getNumOfPrize());
            reward.setReleased(rewardVM.getReleased());
            reward.setMessageWinnerEN(rewardVM.getMessageWinnerEN());
            reward.setMessageWinnerSW(rewardVM.getMessageWinnerSW());
            reward.setMessageBalanceEN(rewardVM.getMessageBalanceEN());
            reward.setMessageBalanceSW(rewardVM.getMessageBalanceSW());

            return reward;
        }
    }
}
