package campaign.service;

import campaign.config.Constants;
import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.CampaignDTO;
import campaign.service.mapper.CampaignMapper;
import campaign.web.rest.vm.ActionCampaignVM;
import campaign.web.rest.vm.CampaignVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CampaignService {

//    private final Logger log = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final TargetListRepository targetListRepository;

    private final FileRepository fileRepository;

    private final StatusRepository statusRepository;

    private final RuleRepository ruleRepository;

    private final CampaignMapper campaignMapper;

    public CampaignService(CampaignRepository campaignRepository,
                           UserRepository userRepository,
                           TargetListRepository targetListRepository,
                           FileRepository fileRepository,
                           StatusRepository statusRepository,
                           RuleRepository ruleRepository,
                           CampaignMapper campaignMapper
    ) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.targetListRepository =targetListRepository;
        this.fileRepository = fileRepository;
        this.statusRepository = statusRepository;
        this.ruleRepository = ruleRepository;
        this.campaignMapper = campaignMapper;
    }

    @Transactional(readOnly = true)
    public CampaignDTO getCampaignById(Long campaignId) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(campaignId);
        if (campaignOpt.isPresent()) {
            return campaignMapper.campaignToCampaignDTO(campaignOpt.get());
        }

        return new CampaignDTO();
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> searchCampaigns(Pageable pageable, String search) {
        return campaignRepository.findAllByNameContaining(search, pageable).map(CampaignDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<CampaignDTO> getAllCampaign(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(CampaignDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignDTO createCampaign(CampaignVM campaignVM) {
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);

        if (campaign != null) {
            campaignRepository.save(campaign);

            // handle approved/rejected user
            Optional<User> userOpt = campaignVM.getApprovedRejectedBy() != null
                ? userRepository.findById(campaignVM.getApprovedRejectedBy())
                : null;
            if (userOpt.isPresent()) {
                campaign.setApprovedRejectedBy(userOpt.get());
            }

            // handle target list
            List<TargetList> targetLists = campaignVM.getTargetLists() != null
                ? targetListRepository.findAllById(campaignVM.getTargetLists())
                :null;
            if (targetLists != null && !targetLists.isEmpty()) {
                campaign.addTargetLists(targetLists);
            }

            // handle rule list
            List<Rule> ruleList = campaignVM.getRuleList() != null
                ? ruleRepository.findAllById(campaignVM.getRuleList())
                : null;
            if(ruleList != null && !ruleList.isEmpty()) {
                campaign.addRuleList(ruleList);
            }

            // handle file list
            List<File> fileList = campaignVM.getFilesList() != null
                ? fileRepository.findAllById(campaignVM.getFilesList())
                : null;
            if (fileList != null && !fileList.isEmpty()) {
                campaign.addFilesList(fileList);
            }

            campaignRepository.save(campaign);
            return campaignMapper.campaignToCampaignDTO(campaign);
        }

        return new CampaignDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignDTO cloneCampaign(Long id) {
        Optional<Campaign> clonedCampaignOpt = campaignRepository.findById(id);
        Campaign toBeInserted = new Campaign();

        if (clonedCampaignOpt.isPresent()) {
            toBeInserted.setName(clonedCampaignOpt.get().getName());
            toBeInserted.setDescription(clonedCampaignOpt.get().getDescription());
            toBeInserted.setFromDate(clonedCampaignOpt.get().getFromDate());
            toBeInserted.setEndDate(clonedCampaignOpt.get().getEndDate());
            toBeInserted.setCampaignType(clonedCampaignOpt.get().getCampaignType());
            toBeInserted.setNotes(clonedCampaignOpt.get().getNotes());
            toBeInserted.setStatus(clonedCampaignOpt.get().getStatus());
            toBeInserted.setApprovedRejectedBy(clonedCampaignOpt.get().getApprovedRejectedBy());
            toBeInserted.addTargetLists(clonedCampaignOpt.get().getTargetLists());
            toBeInserted.addFilesList(clonedCampaignOpt.get().getFilesList());
            toBeInserted.addRuleList(clonedCampaignOpt.get().getRuleList());
        }

        return campaignMapper.campaignToCampaignDTO(campaignRepository.save(toBeInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignDTO updateCampaign(Long id, CampaignVM campaignVM) {
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);
        campaign.setId(id);

        // handle status
        Optional<Status> statusOpt = campaignVM.getStatusId() != null
            ? statusRepository.findById(campaignVM.getStatusId())
            : null;
        if (statusOpt.isPresent()) {
            campaign.setStatus(statusOpt.get());
        }

        // handle target list
        List<TargetList> targetLists = campaignVM.getTargetLists() != null
            ? targetListRepository.findAllById(campaignVM.getTargetLists())
            : null;
        if (targetLists != null && !targetLists.isEmpty()) {
            campaign.addTargetLists(targetLists);
        }

        // handle rule list
        List<Rule> ruleList = campaignVM.getRuleList() != null
            ? ruleRepository.findAllById(campaignVM.getRuleList())
            : null;
        if(ruleList != null && !ruleList.isEmpty()) {
            campaign.addRuleList(ruleList);
        }

        // handle file list
        List<File> fileList = campaignVM.getFilesList() != null
            ? fileRepository.findAllById(campaignVM.getFilesList())
            : null;
        if (fileList != null && !fileList.isEmpty()) {
            campaign.addFilesList(fileList);
        }

        campaignRepository.save(campaign);
        return campaignMapper.campaignToCampaignDTO(campaign);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public CampaignDTO approveOrRejectCampaign(Long id, boolean isApprove) {
        Optional<Campaign> campaignOpt = campaignRepository.findById(id);
        if (campaignOpt.isPresent()) {
            Optional<Status> statusOpt = statusRepository.findByName(
                isApprove ? Constants.APPROVED_STATUS : Constants.REJECTED_STATUS);
            Campaign campaign = campaignOpt.get();
            campaign.setStatus(statusOpt.orElse(null));
            return campaignMapper.campaignToCampaignDTO(campaignRepository.save(campaign));
        }

        return new CampaignDTO();
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
            Optional<Status> statusOpt = statusRepository.findByName(Constants.RUNNING_STATUS);

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
            Optional<Status> statusOpt = statusRepository.findByName(status);
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
        Optional<Status> statusOpt = statusRepository.findByName(Constants.INITIALIZATION_STATUS);

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
