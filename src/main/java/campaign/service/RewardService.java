package campaign.service;

import campaign.config.Constants;
import campaign.domain.Campaign;
import campaign.domain.File;
import campaign.domain.Reward;
import campaign.domain.Voucher;
import campaign.repository.CampaignRepository;
import campaign.repository.FileRepository;
import campaign.repository.RewardRepository;
import campaign.repository.VoucherRepository;
import campaign.service.dto.CampaignDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    private final CampaignRepository campaignRepository;

    public RewardService(RewardRepository rewardRepository,
                         RewardMapper rewardMapper,
                         FileRepository fileRepository,
                         FileMapper fileMapper,
                         VoucherRepository voucherRepository,
                         VoucherMapper voucherMapper,
                         CampaignRepository campaignRepository
    ) {

        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.campaignRepository = campaignRepository;
    }

    @Transactional(readOnly = true)
    public RewardDTO getRewardById(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent()) {
            RewardDTO rewardDTO = rewardMapper.rewardToRewardDTO(rewardOpt.get());
            rewardDTO.setAppliedCampaign(appliedCampaign(rewardOpt.get()));
            return rewardDTO;
        }

        return new RewardDTO();
    }

    private CampaignDTO appliedCampaign(Reward reward) {
        if (reward.getCampaignId() != null) {
            Optional<Campaign> campaignOpt = campaignRepository.findById(reward.getCampaignId());
            if (campaignOpt.isPresent()) {
                return new CampaignDTO(campaignOpt.get());
            }
        }

        return null;
    }

    @Transactional(readOnly = true)
    public Page<RewardDTO> getAllRewards(Pageable pageable) {
        return rewardRepository.findAll(pageable).map(RewardDTO::new);
    }

    @Transactional(readOnly = true)
    public Boolean rewardNameExisted(String name) {
        return rewardRepository.findByNameIgnoreCase(name).isPresent();
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
        if (!rewardOpt.isPresent()) return null;

        Reward toBeInserted = new Reward();

        if (rewardOpt.isPresent()) {
            String clonedName = rewardOpt.get().getName() + Constants.CLONE_POSTFIX;
            List<Reward> rewardsByName = rewardRepository.findByNameStartsWithIgnoreCase(clonedName);
            toBeInserted.setName(ServiceUtils
                .clonedCount(clonedName, rewardsByName.stream().map(Reward::getName).collect(Collectors.toList())));
            toBeInserted.setName(rewardOpt.get().getName() + Constants.CLONE_POSTFIX);
            toBeInserted.setDescription(rewardOpt.get().getDescription());
            toBeInserted.setPrizeType(rewardOpt.get().getPrizeType());
            toBeInserted.setPrizeValue(rewardOpt.get().getPrizeValue());
            toBeInserted.setNumOfPrize(rewardOpt.get().getNumOfPrize());
            toBeInserted.setReleased(rewardOpt.get().getReleased());
            toBeInserted.setMessageWinnerEN(rewardOpt.get().getMessageWinnerEN());
            toBeInserted.setMessageWinnerSW(rewardOpt.get().getMessageWinnerSW());
            toBeInserted.setMessageBalanceEN(rewardOpt.get().getMessageBalanceEN());
            toBeInserted.setMessageBalanceSW(rewardOpt.get().getMessageBalanceSW());
            toBeInserted.setLevel(rewardOpt.get().getLevel());
            rewardRepository.save(toBeInserted);
            // voucher code can not be cloned
            //toBeInserted.setVouchers(rewardOpt.get().getVouchers());
            // clone files
            if (rewardOpt.get().getFiles() != null) {
                List<File> toBeCloned = rewardOpt.get().getFiles().stream()
                    .map(file -> {
                        File clonedFile = file.clone();
                        clonedFile.setReward(toBeInserted);
                        return clonedFile;
                    })
                    .collect(Collectors.toList());
                toBeInserted.addFiles(toBeCloned);
            }
        }

        return rewardMapper.rewardToRewardDTO(rewardRepository.save(toBeInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO updateReward(Long id, RewardVM rewardVM) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        // check if reward is existed
        if (!rewardOpt.isPresent()) return null;

        // convert rewardVM input to reward to be saved
        Reward reward = rewardMapper.updateReward(rewardOpt.get(), rewardVM);
        reward.setId(id);

        // handle files
        if (rewardVM.getFiles() != null && !rewardVM.getFiles().isEmpty()) {
            // to be detached files
            Set<Long> fileIds = rewardVM.getFiles().stream().map(FileVM::getId).collect(Collectors.toSet());
            List<File> toBeDetachedFiles = rewardOpt.get().getFiles();
            fileRepository.saveAll(
                toBeDetachedFiles.stream()
                    .filter(file -> !fileIds.contains(file.getId()))
                    .map(File::removeReward)
                    .collect(Collectors.toList()));

            // create or update files
            List<File> toBeSavedFiles = fileRepository.findAllById(fileIds);;
            toBeSavedFiles.stream().forEach(f -> f.setReward(reward));
            // save files
            reward.updateFiles(toBeSavedFiles);
        }

        // handle voucher
        if (rewardVM.getVoucherCodes() != null && !rewardVM.getVoucherCodes().isEmpty()) {
            // remove existing vouchers
            Set<Long> voucherIds =
                rewardVM.getVoucherCodes().stream().map(VoucherDTO::getId).collect(Collectors.toSet());
            List<Voucher> toBeDetachedVouchers = rewardOpt.get().getVouchers();
            if (toBeDetachedVouchers != null && !toBeDetachedVouchers.isEmpty()) {
                voucherRepository.saveAll(
                    toBeDetachedVouchers.stream()
                        .filter(voucher -> !voucherIds.contains(voucher.getId()))
                        .map(Voucher::removeReward)
                        .collect(Collectors.toList()));
            }
            // create vouchers code
            List<Voucher> toBeSavedVouchers = voucherRepository.saveAll(
                voucherMapper.voucherDTOToVouchers(rewardVM.getVoucherCodes()));
            toBeSavedVouchers.stream().forEach(voucher -> voucher.setReward(reward));
            // save vouchers
            reward.updateVouchers(toBeSavedVouchers);
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
