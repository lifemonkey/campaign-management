package campaign.service;

import campaign.domain.File;
import campaign.domain.Reward;
import campaign.domain.Voucher;
import campaign.repository.FileRepository;
import campaign.repository.RewardRepository;
import campaign.repository.VoucherRepository;
import campaign.service.dto.FileDTO;
import campaign.service.dto.RewardDTO;
import campaign.service.dto.VoucherDTO;
import campaign.service.mapper.FileMapper;
import campaign.service.mapper.RewardMapper;
import campaign.service.mapper.VoucherMapper;
import campaign.web.rest.vm.FileVM;
import campaign.web.rest.vm.RewardVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardService.class);

    private final RewardRepository rewardRepository;

    private final RewardMapper rewardMapper;

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    public RewardService(RewardRepository rewardRepository,
                         RewardMapper rewardMapper,
                         FileRepository fileRepository,
                         FileMapper fileMapper,
                         VoucherRepository voucherRepository,
                         VoucherMapper voucherMapper
    ) {

        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
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
            return rewardRepository.findAllByNameContainingIgnoreCase(search, pageable).map(RewardDTO::new);
        } else if (search == null && type != null) {
            return rewardRepository.findAllByPrizeType(type, pageable).map(RewardDTO::new);
        } else {
            return rewardRepository.findAllByNameContainingIgnoreCaseAndPrizeType(search, type, pageable).map(RewardDTO::new);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO createReward(RewardVM rewardVM) {
        Reward reward = rewardMapper.rewardVMToReward(rewardVM);

        if (reward != null) {
            rewardRepository.save(reward);

            // handle image
            List<Long> fileIds = rewardVM.getFiles().stream().map(FileVM::getId).collect(Collectors.toList());
            List<File> fileList = fileRepository.findAllById(fileIds);
            if (fileList != null && !fileList.isEmpty()) {

                fileList.stream().forEach(file -> file.setReward(reward));
                fileRepository.saveAll(fileList);
                // two ways binding
                reward.addFiles(fileList);
            }

            // handle voucher code
            if (rewardVM.getVoucherCodes() != null) {
                // create vouchers
                if (reward.getVouchers() != null) {
                    List<Voucher> toBeSaved = rewardVM.getVoucherCodes().stream()
                        .map(voucherCode -> new Voucher(voucherCode, reward))
                        .collect(Collectors.toList());
                    // save vouchers
                    voucherRepository.saveAll(toBeSaved);
                    reward.addVouchers(toBeSaved);
                }
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
            toBerInserted.setPrizeType(rewardOpt.get().getPrizeType());
            toBerInserted.setPrizeValue(rewardOpt.get().getPrizeValue());
            toBerInserted.setNumOfPrize(rewardOpt.get().getNumOfPrize());
            toBerInserted.setReleased(rewardOpt.get().getReleased());
            toBerInserted.setMessageWinnerEN(rewardOpt.get().getMessageWinnerEN());
            toBerInserted.setMessageWinnerSW(rewardOpt.get().getMessageWinnerSW());
            toBerInserted.setMessageBalanceEN(rewardOpt.get().getMessageBalanceEN());
            toBerInserted.setMessageBalanceSW(rewardOpt.get().getMessageBalanceSW());
            rewardRepository.save(toBerInserted);
            // voucher code can not be cloned
            //toBerInserted.setVouchers(rewardOpt.get().getVouchers());
            // clone files
            if (rewardOpt.get().getFiles() != null) {
                List<File> toBeCloned = rewardOpt.get().getFiles().stream()
                    .map(file -> {
                        File clonedFile = file.clone();
                        clonedFile.setReward(toBerInserted);
                        return clonedFile;
                    })
                    .collect(Collectors.toList());
                toBerInserted.addFiles(toBeCloned);
            }
        }

        return rewardMapper.rewardToRewardDTO(rewardRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO updateReward(Long id, RewardVM rewardVM) {
        Reward reward = rewardMapper.rewardVMToReward(rewardVM);
        reward.setId(id);

        // handle files
        if (rewardVM.getFiles() != null) {
            // remove existing files
            List<Long> fileIds = rewardVM.getFiles().stream().map(FileVM::getId).collect(Collectors.toList());
            List<File> toBeDetached = fileRepository.findByRewardId(reward.getId()).stream()
                .filter(file -> !fileIds.contains(file.getId()))
                .map(File::removeReward)
                .collect(Collectors.toList());
            fileRepository.saveAll(toBeDetached);

            // create new files
            if (reward.getFiles() != null) {
                List<File> toBeSaved = rewardVM.getFiles().stream()
                    .map(file -> fileMapper.fileVMToFile(file))
                    .collect(Collectors.toList());
                // save files
                reward.updateFiles(toBeSaved);
            }
        }

        // handle voucher
        if (rewardVM.getVoucherCodes() != null) {
            // remove existing vouchers
            List<Long> voucherIds = rewardVM.getVoucherCodes().stream().map(VoucherDTO::getId).collect(Collectors.toList());
            List<Voucher> toBeDetached = voucherRepository.findByRewardId(reward.getId()).stream()
                .filter(voucher -> !voucherIds.contains(voucher.getId()))
                .map(Voucher::removeReward)
                .collect(Collectors.toList());
            voucherRepository.saveAll(toBeDetached);

            // create new vouchers if not existed yet
            if (reward.getVouchers() != null) {
                List<Voucher> toBeSaved = rewardVM.getVoucherCodes().stream()
                    .map(voucherCode -> new Voucher(voucherCode, reward))
                    .collect(Collectors.toList());
                // save vouchers
                reward.updateVouchers(toBeSaved);
            }
        }

        return rewardMapper.rewardToRewardDTO(rewardRepository.save(reward));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReward(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent()) {
            Reward reward = rewardOpt.get();
            rewardRepository.delete(reward);
        }
    }
}
