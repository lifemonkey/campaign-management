package campaign.service;

import campaign.config.Constants;
import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.CampaignDTO;
import campaign.service.dto.RewardCampaignDTO;
import campaign.service.dto.RewardDTO;
import campaign.service.dto.VoucherDTO;
import campaign.service.mapper.RewardMapper;
import campaign.service.mapper.VoucherMapper;
import campaign.web.rest.vm.FileVM;
import campaign.web.rest.vm.RewardVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RewardService {

    private final Logger log = LoggerFactory.getLogger(RewardService.class);

    private final RewardRepository rewardRepository;

    private final RewardMapper rewardMapper;

    private final FileRepository fileRepository;

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    private final CampaignRepository campaignRepository;

    private final RewardConditionRepository rewardConditionRepository;

    public RewardService(RewardRepository rewardRepository,
                         RewardMapper rewardMapper,
                         FileRepository fileRepository,
                         VoucherRepository voucherRepository,
                         VoucherMapper voucherMapper,
                         CampaignRepository campaignRepository,
                         RewardConditionRepository rewardConditionRepository) {

        this.rewardRepository = rewardRepository;
        this.rewardMapper = rewardMapper;
        this.fileRepository = fileRepository;
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.campaignRepository = campaignRepository;
        this.rewardConditionRepository = rewardConditionRepository;
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

    public boolean hasAppliedRule(Long rewardId) {
        // get list rewards that applied rule
        Optional<RewardCondition> rewardConditionOpt = rewardConditionRepository.findAll().stream()
            .filter(rc -> rc.getReward().getId() == rewardId)
            .findAny();
        return rewardConditionOpt == null || rewardConditionOpt.isPresent();
    }

    private RewardCampaignDTO appliedCampaign(Reward reward) {
        if (reward.getCampaignId() != null) {
            Optional<Campaign> campaignOpt = campaignRepository.findById(reward.getCampaignId());
            if (campaignOpt.isPresent()) {
                return new RewardCampaignDTO(campaignOpt.get());
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
    public Page<RewardDTO> searchRewards(Pageable pageable, String search, Integer type, String appliedCampaign,
                                         Integer campaignType, boolean appliedRule, boolean showTemplate) {
        List<Reward> rewardList;

        if (search != null && type == null) {
            rewardList = rewardRepository.findAllByNameContainingIgnoreCase(search);
        } else if (search == null && type != null) {
            rewardList = rewardRepository.findAllByPrizeType(type);
        } else if (search != null && type != null) {
            rewardList = rewardRepository.findAllByNameContainingIgnoreCaseAndPrizeType(search, type);
        } else {
            rewardList = rewardRepository.findAll();
        }

        // sort
        sortResults(pageable, rewardList);

        // get applied campaigns
        Map<Long, RewardCampaignDTO> appliedCampaigns = appliedCampaigns(rewardList, campaignType);

        // get list rewards that applied rule
        Set<Long> rewardAppliedRuleIds = appliedRule
            ? rewardConditionRepository.findAll().stream().map(rc -> rc.getReward().getId()).collect(Collectors.toSet())
            : Collections.emptySet();

        List<RewardDTO> filteredList = rewardList.stream()
            .filter(reward -> {
                if (appliedCampaign == null || appliedCampaign.isEmpty()
                    || appliedCampaign.equalsIgnoreCase("all")
                ) {
                    return true;
                }

                // filter for appliedCampaign is none
                if (appliedCampaign.equalsIgnoreCase("none")) {
                    return reward.getCampaignId() == null;
                } else if (reward.getCampaignId() != null && appliedCampaigns.containsKey(reward.getCampaignId())) {
                    // filter for specific appliedCampaign name
                    RewardCampaignDTO rewardCampaign = appliedCampaigns.get(reward.getCampaignId());
                    return rewardCampaign.getName().toLowerCase().contains(appliedCampaign.toLowerCase());
                }

                return false;
            })
            .filter(reward -> rewardAppliedRuleIds.isEmpty() || !rewardAppliedRuleIds.contains(reward.getId()))
            .filter(reward -> showTemplate || !reward.isTemplate())
            .map(reward -> {
                RewardDTO rewardDTO = new RewardDTO(reward);
                rewardDTO.setAppliedCampaign(appliedCampaigns.get(reward.getCampaignId()));
                return rewardDTO;
            })
            .collect(Collectors.toList());

        // applied campaign: campaign details
        List<RewardDTO> results = ServiceUtils.getPageContent(pageable, filteredList);
        return new PageImpl<>(results, pageable, results.size());
    }

    private void sortResults(Pageable pageable, List<Reward> toBeSortedList) {
        if (pageable.getSort().stream()
            .filter(sort -> sort.getProperty().equalsIgnoreCase(Constants.SORT_BY_CREATED_DATE)).findAny()
            .isPresent()
        ) {
            if (pageable.getSort().stream().filter(sort -> sort.isDescending()).findAny().isPresent()) {
                Collections.sort(toBeSortedList, Comparator.comparing(Reward::getCreatedDate).reversed());
            } else {
                Collections.sort(toBeSortedList, Comparator.comparing(Reward::getCreatedDate));
            }
        } else {
            if (pageable.getSort().stream().filter(sort -> sort.isDescending()).findAny().isPresent()) {
                Collections.sort(toBeSortedList, Comparator.comparing(Reward::getName).reversed());
            } else {
                Collections.sort(toBeSortedList, Comparator.comparing(Reward::getName));
            }
        }
    }

    private Map<Long, RewardCampaignDTO> appliedCampaigns(List<Reward> rewardList, Integer campaignType) {
        List<Long> appliedCampaignIds = rewardList.stream()
            .filter(reward -> reward.getCampaignId() != null)
            .map(Reward::getCampaignId)
            .collect(Collectors.toList());

        Map<Long, RewardCampaignDTO> appliedCampaigns = new HashMap<>();

        if (!appliedCampaignIds.isEmpty()) {
            List<Campaign> campaignList = campaignRepository.findAllById(appliedCampaignIds);
            if (!campaignList.isEmpty()) {
                appliedCampaigns.putAll(campaignList.stream()
                    .filter(campaign -> campaignType == null || campaign.getCampaignType() == campaignType)
                    .collect(Collectors.toMap(Campaign::getId, campaign -> new RewardCampaignDTO(campaign))));
            }
        }

        return appliedCampaigns;
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

        String clonedName = rewardOpt.get().getName() + Constants.CLONE_POSTFIX;
        List<Reward> rewardsByName = rewardRepository.findByNameStartsWithIgnoreCase(clonedName);
        Reward toBeInserted = rewardOpt.get().clone(
            ServiceUtils
                .clonedFileName(clonedName, rewardsByName.stream().map(Reward::getName).collect(Collectors.toList())));
        // set appliedCampaign is null, do not clone voucher code
        rewardRepository.save(toBeInserted);

        // clone files
        if (!rewardOpt.get().getFiles().isEmpty()) {
            List<File> toBeCloned = rewardOpt.get().getFiles().stream()
                .map(file -> {
                    String clonedFileName = ServiceUtils.getFileName(file.getName());
                    List<File> filesByName = fileRepository.findByNameStartsWithIgnoreCase(clonedFileName);
                    File clonedFile = file.clone(
                        ServiceUtils
                            .clonedFileName(clonedFileName, filesByName.stream().map(File::getName).collect(Collectors.toList()))
                        + ServiceUtils.getFileNameExt(file.getName()));
                    clonedFile.setReward(toBeInserted);
                    return clonedFile;
                })
                .collect(Collectors.toList());
            // two ways binding
            toBeInserted.addFiles(toBeCloned);
        }

        return rewardMapper.rewardToRewardDTO(rewardRepository.save(toBeInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public RewardDTO updateReward(Long id, RewardVM rewardVM) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        // convert rewardVM input to reward to be saved
        Reward reward = rewardMapper.updateReward(rewardOpt.get(), rewardVM);

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
    public RewardDTO setAsTemplate(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        // check if reward is existed
        if (!rewardOpt.isPresent()) return null;

        // remove constraint and set reward as a template
        Reward toBeSaved = rewardOpt.get();
        toBeSaved.setTemplate(true);
        toBeSaved.setCampaignId(null);
        return rewardMapper.rewardToRewardDTO(rewardRepository.save(toBeSaved));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteReward(Long id) {
        Optional<Reward> rewardOpt = rewardRepository.findById(id);
        if (rewardOpt.isPresent() && rewardOpt.get().getCampaignId() == null) {
            Reward reward = rewardOpt.get();
            rewardRepository.delete(reward);
        }
    }
}
