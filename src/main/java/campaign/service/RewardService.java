package campaign.service;

import campaign.domain.File;
import campaign.domain.Reward;
import campaign.repository.FileRepository;
import campaign.repository.RewardRepository;
import campaign.service.dto.RewardDTO;
import campaign.service.mapper.FileMapper;
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

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public RewardService(RewardRepository rewardRepository,
                         RewardMapper rewardMapper,
                         FileRepository fileRepository,
                         FileMapper fileMapper
    ) {

        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
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
    public Page<RewardDTO> searchRewards(Pageable pageable, String search, Integer type) {
        if (search != null && type == null) {
            return rewardRepository.findAllByNameContaining(search, pageable).map(RewardDTO::new);
        } else if (search == null && type != null) {
            return rewardRepository.findAllByPrizeType(type, pageable).map(RewardDTO::new);
        } else {
            return rewardRepository.findAllByNameContainingAndPrizeType(search, type, pageable).map(RewardDTO::new);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO createReward(RewardVM rewardVM) {
        Reward reward = rewardMapper.rewardVMToReward(rewardVM);

        if (reward != null) {
            File file = fileMapper.fileVMToFile(rewardVM.getImage());
            if (file != null) {
                fileRepository.save(file);
                reward.setImage(file);
            }
            return rewardMapper.rewardToRewardDTO(rewardRepository.save(reward));
        }

        return new RewardDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO cloneReward(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        Reward toBerInserted = new Reward();

        if (rewardOpt.isPresent()) {
            toBerInserted.setName(rewardOpt.get().getName());
            toBerInserted.setDescription(rewardOpt.get().getDescription());
            toBerInserted.setImage(rewardOpt.get().getImage());
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

        Optional<File> imageOpt;
        if (rewardVM.getImage().getName() != null) {
            imageOpt = fileRepository.findByName(rewardVM.getImage().getName());
        } else {
            imageOpt = fileRepository.findByImageUrl(rewardVM.getImage().getImageUrl());
        }

        reward.setImage(imageOpt.orElse(null));

        rewardRepository.save(reward);
        return rewardMapper.rewardToRewardDTO(reward);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReward(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent()) {
            Reward reward = rewardOpt.get();
            reward.setImage(null);
            rewardRepository.save(reward);
            rewardRepository.delete(reward);
        }
    }
}
