package campaign.service;

import campaign.config.Constants;
import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.CampaignDTO;
import campaign.service.dto.CampaignWRelDTO;
import campaign.service.mapper.CampaignMapper;
import campaign.service.mapper.FileMapper;
import campaign.service.mapper.GeneratedTimeMapper;
import campaign.web.rest.vm.ActionCampaignVM;
import campaign.web.rest.vm.CampaignVM;
import campaign.web.rest.vm.FileVM;
import campaign.web.rest.vm.GeneratedTimeVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final TargetListRepository targetListRepository;

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final StatusRepository statusRepository;

    private final RuleRepository ruleRepository;

    private final CampaignMapper campaignMapper;

    private final GeneratedTimeRepository generatedTimeRepository;

    private final GeneratedTimeMapper generatedTimeMapper;

    public CampaignService(CampaignRepository campaignRepository,
                           UserRepository userRepository,
                           TargetListRepository targetListRepository,
                           FileRepository fileRepository,
                           FileMapper fileMapper,
                           StatusRepository statusRepository,
                           RuleRepository ruleRepository,
                           CampaignMapper campaignMapper,
                           GeneratedTimeRepository generatedTimeRepository,
                           GeneratedTimeMapper generatedTimeMapper
    ) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.targetListRepository = targetListRepository;
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.statusRepository = statusRepository;
        this.ruleRepository = ruleRepository;
        this.campaignMapper = campaignMapper;
        this.generatedTimeRepository = generatedTimeRepository;
        this.generatedTimeMapper = generatedTimeMapper;
    }

    @Transactional(readOnly = true)
    public CampaignWRelDTO getCampaignById(Long campaignId) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(campaignId);
        if (campaignOpt.isPresent()) {
            return campaignMapper.campaignToCampaignWRelDTO(campaignOpt.get());
        }

        return new CampaignWRelDTO();
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> searchCampaigns(Pageable pageable, String search, Integer type) {
        if (search != null && type == null) {
            return campaignRepository.findAllByNameContainingIgnoreCase(search, pageable).map(CampaignDTO::new);
        } else if (search == null && type != null) {
            return campaignRepository.findAllByCampaignType(type, pageable).map(CampaignDTO::new);
        } else {
            return campaignRepository.findAllByNameContainingIgnoreCaseAndCampaignType(search, type, pageable).map(CampaignDTO::new);
        }
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> getAllCampaign(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(CampaignDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignWRelDTO createCampaign(CampaignVM campaignVM) {
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);

        if (campaign != null) {
            campaignRepository.save(campaign);

            // handle status
            Optional<Status> statusOpt = campaignVM.getStatusId() != null
                ? statusRepository.findById(campaignVM.getStatusId())
                : null;
            if (statusOpt != null && statusOpt.isPresent()) {
                campaign.setStatus(statusOpt.get());
            }

            // handle approved/rejected user
            Optional<User> userOpt = campaignVM.getApprovedRejectedBy() != null
                ? userRepository.findById(campaignVM.getApprovedRejectedBy())
                : null;
            if (userOpt.isPresent()) {
                campaign.setApprovedRejectedBy(userOpt.get());
            }

            // handle target list
            List<TargetList> targetLists = targetListRepository.findAllById(campaignVM.getTargetListIds());
            if (targetLists != null && !targetLists.isEmpty()) {
                campaign.addTargetLists(targetLists);
            }

            // handle rule list
            List<Rule> ruleList = ruleRepository.findAllById(campaignVM.getRuleIds());
            if(ruleList != null && !ruleList.isEmpty()) {
                campaign.addRuleList(ruleList);
            }
            campaignRepository.save(campaign);

            // handle file list
            List<Long> fileIds = campaignVM.getFiles().stream().map(FileVM::getId).collect(Collectors.toList());
            List<File> fileList = fileRepository.findAllById(fileIds);
            if (fileList != null && !fileList.isEmpty()) {
                fileList.stream().forEach(file -> file.setCampaign(campaign));
                fileRepository.saveAll(fileList);
                // two ways binding
                campaign.addFilesList(fileList);
            }

            // handle generated time list
            List<GeneratedTime> generatedTimes = generatedTimeMapper.generatedTimeVMToGeneratedTimes(campaignVM.getGeneratedTimes());
            if (generatedTimes != null && !generatedTimes.isEmpty()) {
                generatedTimes.stream().forEach(generatedTime -> generatedTime.setCampaign(campaign));
                generatedTimeRepository.saveAll(generatedTimes);
                // two ways binding
                campaign.addGeneratedTimeList(generatedTimes);
            }

            campaignRepository.save(campaign);
            return campaignMapper.campaignToCampaignWRelDTO(campaign);
        }

        return new CampaignWRelDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignWRelDTO cloneCampaign(Long id) {
        Optional<Campaign> clonedCampaignOpt = campaignRepository.findById(id);
        Campaign toBeInserted = new Campaign();

        if (clonedCampaignOpt.isPresent()) {
            toBeInserted.setName(clonedCampaignOpt.get().getName() + "-cloned");
            toBeInserted.setDescription(clonedCampaignOpt.get().getDescription());
            toBeInserted.setFromDate(clonedCampaignOpt.get().getFromDate());
            toBeInserted.setEndDate(clonedCampaignOpt.get().getEndDate());
            toBeInserted.setCampaignType(clonedCampaignOpt.get().getCampaignType());
            toBeInserted.setNotes(clonedCampaignOpt.get().getNotes());
            toBeInserted.setStatus(clonedCampaignOpt.get().getStatus());
            toBeInserted.setApprovedRejectedBy(clonedCampaignOpt.get().getApprovedRejectedBy());
            toBeInserted.addTargetLists(clonedCampaignOpt.get().getTargetLists());
            toBeInserted.addRuleList(clonedCampaignOpt.get().getRuleList());
            toBeInserted.addGeneratedTimeList(clonedCampaignOpt.get().getGeneratedTimeList());
            campaignRepository.save(toBeInserted);

            // clone files
            if (clonedCampaignOpt.get().getFilesList() != null && !clonedCampaignOpt.get().getFilesList().isEmpty()) {
                List<File> toBeCloned = clonedCampaignOpt.get().getFilesList().stream()
                    .map(file -> {
                        File clonedFile = file.clone();
                        clonedFile.setCampaign(toBeInserted);
                        return clonedFile;
                    })
                    .collect(Collectors.toList());
                toBeInserted.addFilesList(toBeCloned);
            }
        }

        return campaignMapper.campaignToCampaignWRelDTO(campaignRepository.save(toBeInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignWRelDTO updateCampaign(Long id, CampaignVM campaignVM) {
        Optional<Campaign> campaignInDb = campaignRepository.findById(id);
        // check if campaign is existed
        if (!campaignInDb.isPresent()) return null;
        // convert campaignVM input to campaign to be saved
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);
        campaign.setId(id);

        // handle status
        Optional<Status> statusOpt = statusRepository.findById(campaignVM.getStatusId());
        if (statusOpt.isPresent()) {
            campaign.setStatus(statusOpt.get());
        }

        // handle target list
        if (campaignVM.getTargetListIds() != null && !campaignVM.getTargetListIds().isEmpty()) {
            Set<Long> targetListIds = campaignVM.getTargetListIds().stream().collect(Collectors.toSet());
            // to be detached target list
            List<TargetList> toBeDetachedTargetList = campaignInDb.get().getTargetLists();
            targetListRepository.saveAll(
                toBeDetachedTargetList.stream()
                    .filter(tl -> !targetListIds.contains(tl.getId()))
                    .map(TargetList::clearCampaignList)
                    .collect(Collectors.toList()));
            // add target list to campaign
            List<TargetList> toBeSaved = targetListRepository.findAllById(targetListIds);
            campaign.addTargetLists(toBeSaved);
        }

        // handle rule list
        if (campaignVM.getRuleIds() != null && !campaignVM.getRuleIds().isEmpty()) {
            Set<Long> ruleIds = campaignVM.getRuleIds().stream().collect(Collectors.toSet());
            // to be detached rule list
            List<Rule> toBeDetachedRuleList = campaignInDb.get().getRuleList();
            ruleRepository.saveAll(
                toBeDetachedRuleList.stream()
                    .filter(r -> !ruleIds.contains(r.getId()))
                    .map(Rule::clearCampaignList)
                    .collect(Collectors.toList()));
            // add rule list to campaign
            campaign.addRuleList(ruleRepository.findAllById(ruleIds));
        }

        // handle file list
        if (campaignVM.getFiles() != null && !campaignVM.getFiles().isEmpty()) {
            // remove existing files
            List<Long> fileIds = campaignVM.getFiles().stream().map(FileVM::getId).collect(Collectors.toList());
            List<File> toBeDetachedFiles = campaignInDb.get().getFilesList();
            fileRepository.saveAll(
                toBeDetachedFiles.stream()
                .filter(file -> !fileIds.contains(file.getId()))
                .map(File::removeCampaign)
                .collect(Collectors.toList()));

            // create or update files
            List<File> toBeSavedFiles = fileRepository.findAllById(fileIds);
            toBeSavedFiles.stream().forEach(f -> f.setCampaign(campaign));
            // two ways binding
            campaign.updateFileList(toBeSavedFiles);
        }

        // handle generated time list
        if (campaignVM.getGeneratedTimes() != null && !campaignVM.getGeneratedTimes().isEmpty()) {
            Set<Long> generatedTimeIds =
                campaignVM.getGeneratedTimes().stream().map(GeneratedTimeVM::getId).collect(Collectors.toSet());
            // to be detached generated time
            List<GeneratedTime> toBeDetachedGeneratedTimes = campaignInDb.get().getGeneratedTimeList();
            if (toBeDetachedGeneratedTimes != null && !toBeDetachedGeneratedTimes.isEmpty()) {
                generatedTimeRepository.saveAll(
                    toBeDetachedGeneratedTimes.stream()
                        .filter(gt -> !generatedTimeIds.contains(gt.getId()))
                        .map(GeneratedTime::removeCampaign)
                        .collect(Collectors.toList()));
            }
            // create generated time
            List<GeneratedTime> toBeSavedGeneratedTimes = generatedTimeRepository.saveAll(
                generatedTimeMapper.generatedTimeVMToGeneratedTimes(campaignVM.getGeneratedTimes()));
            toBeSavedGeneratedTimes.stream().forEach(gt -> gt.setCampaign(campaign));
//            // two ways binding
            campaign.addGeneratedTimeList(toBeSavedGeneratedTimes);
        }

        campaignRepository.save(campaign);
        return campaignMapper.campaignToCampaignWRelDTO(campaign);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCampaign(Long id) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        if (campaignOpt.isPresent()) {
            Campaign campaign = campaignOpt.get();
            // detach user
            campaign.setStatus(null);
            campaign.setApprovedRejectedBy(null);
            campaign.clearTargetLists();
            campaign.clearRuleList();
            campaignRepository.save(campaign);
            campaignRepository.delete(campaign);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignWRelDTO approveOrRejectCampaign(Long id, boolean isApprove) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        if (campaignOpt.isPresent()) {
            Optional<Status> statusOpt = statusRepository.findByNameIgnoreCase(
                isApprove ? Constants.APPROVED_STATUS : Constants.REJECTED_STATUS);
            Campaign campaign = campaignOpt.get();
            campaign.setStatus(statusOpt.orElse(null));
            return campaignMapper.campaignToCampaignWRelDTO(campaignRepository.save(campaign));
        }

        return new CampaignWRelDTO();
    }

    /**
     * Action on/off/pause campaign: Toggle ON
     *
     *  1. Status=Initialization: toDate > currentDate
     *      - True: change status=Running AND update fromDate=currentDate
     *      - False: return error
     *  2. Status=Pending Approve:
     *      - Show error
     *  3. Status=Paused: toDate > currentDate
     *      - True: change status=Running AND update fromDate=currentDate
     *      - False: show error
     *  4. Status is other value show error
     *
     * @param id
     * @return String
     */
    @Transactional(rollbackFor = Exception.class)
    public String toggleOnCampaign(Long id) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        if (campaignOpt.isPresent()) {
            Campaign campaign = campaignOpt.get();
            Optional<Status> statusOpt = statusRepository.findByNameIgnoreCase(Constants.RUNNING_STATUS);

            // if status is Initialization
            if (campaign.getStatus().getName() == Constants.INITIALIZATION_STATUS
                || campaign.getStatus().getName() == Constants.PAUSE_STATUS
            ) {
                if (campaign.getEndDate().isAfter(LocalDateTime.now())) {
                    campaign.setStatus(statusOpt.orElse(null));
                    campaign.setFromDate(LocalDateTime.now());
                    return "Campaign " + campaign.getName() + " is running now.";
                } else {
                    return "Campaign " + campaign.getName() + " could not run because the end date is less than the current date";
                }
            } else if (campaign.getStatus().getName() == Constants.PENDING_APPROVE_STATUS) {
                // if status is Pending Approve
                if (campaign.getEndDate().isAfter(LocalDateTime.now())) {
                    return "Campaign " + campaign.getName() + " need to be approved before starting.";
                } else {
                    return "Campaign " + campaign.getName() + " is running now.";
                }
            } else {
                return "Could not run this campaign!";
            }
        }

        return "Not found campaign by the given ID=" + id;
    }

    /**
     * Action on/off/pause campaign: Toggle OFF on campaign with status is Running
     *      - Pause campaign: change status to Paused
     *      - Off campaign: change status to Cancelled
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String toggleOffCampaign(Long id, ActionCampaignVM actionCampaignVM) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        if (campaignOpt.isPresent()) {
            Campaign campaign = campaignOpt.get();
            // get status of campaign to be updated
            String status = actionCampaignVM.getAction() == Constants.CANCEL_CAMPAIGN
                ? Constants.CANCELLED_STATUS
                : Constants.PAUSE_STATUS;
            Optional<Status> statusOpt = statusRepository.findByNameIgnoreCase(status);
            // update value for campaign
            campaign.setStatus(statusOpt.orElse(null));
            campaign.setActionReason(actionCampaignVM.getActionReason());
            return "The campaign " + campaign.getName() + "'s status updated to " + status;
        }

        return "Not found campaign by the given ID=" + id;
    }

    /**
     * Auto activate campaign
     * This feature will run every day at 00:00 to change status of campaign to Running
     * If fromDate equal to currentDate
     */
    @Transactional(rollbackFor = Exception.class)
    public void activateCampaign() {
        Optional<Status> statusOpt = statusRepository.findByNameIgnoreCase(Constants.INITIALIZATION_STATUS);

        if (statusOpt.isPresent()) {
            List<Campaign> toBeActivated = campaignRepository.findAllByStatus(statusOpt.get())
                .stream()
                .filter(campaign -> LocalDate.now().isEqual(LocalDate.from(campaign.getFromDate())))
                .collect(Collectors.toList());

            if (toBeActivated.size() > 0) {
                campaignRepository.saveAll(toBeActivated);
            }
        }
    }

}
