package campaign.service;

import campaign.domain.Reward;
import campaign.repository.RewardRepository;
import campaign.service.dto.RewardDTO;
import campaign.service.mapper.RewardMapper;
import campaign.web.rest.vm.RewardVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardService.class);

    private final RewardRepository rewardRepository;

    private final RewardMapper rewardMapper;

    public RewardService(RewardRepository rewardRepository, RewardMapper rewardMapper) {
        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
    }

    @Transactional(readOnly = true)
    public RewardDTO getRewardById(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent()) {
            return rewardMapper.rewardToRewardDTO(rewardOpt.get());
        }

        return new RewardDTO();
    }

    @Transactional(readOnly = true)
    public Page<RewardDTO> getAllRewards(Pageable pageable) {
        return rewardRepository.findAll(pageable).map(RewardDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<RewardDTO> searchRewards(Pageable pageable, String search) {
        return rewardRepository.findAllByNameContaining(search, pageable).map(RewardDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO createReward(RewardVM rewardVM) {
        Reward reward = rewardMapper.rewardVMToReward(rewardVM);

        if (reward != null) {
            return rewardMapper.rewardToRewardDTO(rewardRepository.save(reward));
        }

        return new RewardDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO cloneReward(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        Reward toBerInserted = new Reward();

        if (rewardOpt.isPresent()) {
            toBerInserted.setId(rewardOpt.get().getId());
            toBerInserted.setName(rewardOpt.get().getName());
            toBerInserted.setDescription(rewardOpt.get().getDescription());

            toBerInserted.setPrizeType(rewardOpt.get().getPrizeType());
            toBerInserted.setPrizeValue(rewardOpt.get().getPrizeValue());
            toBerInserted.setNumOfPrize(rewardOpt.get().getNumOfPrize());
            toBerInserted.setReleased(rewardOpt.get().getReleased());
            toBerInserted.setMessageWinnerEN(rewardOpt.get().getMessageWinnerEN());
            toBerInserted.setMessageWinnerSW(rewardOpt.get().getMessageWinnerSW());
            toBerInserted.setMessageBalanceEN(rewardOpt.get().getMessageBalanceEN());
            toBerInserted.setMessageBalanceSW(rewardOpt.get().getMessageBalanceSW());
        }

        return rewardMapper.rewardToRewardDTO(rewardRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO updateReward(Long id, RewardVM rewardVM) {
        Reward reward = rewardMapper.rewardVMToReward(rewardVM);
        reward.setId(id);

        rewardRepository.save(reward);
        return rewardMapper.rewardToRewardDTO(reward);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReward(Long id) {
        rewardRepository.deleteById(id);
    }
}
