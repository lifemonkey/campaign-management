package campaign.service;

import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.CampaignDTO;
import campaign.service.mapper.CampaignMapper;
import campaign.web.rest.vm.CampaignVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignService {

//    private final Logger log = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final TargetListRepository targetListRepository;

    private final FilesRepository filesRepository;

    private final StatusRepository statusRepository;

    private final RuleRepository ruleRepository;

    private final CampaignMapper campaignMapper;

    public CampaignService(CampaignRepository campaignRepository,
                           UserRepository userRepository,
                           TargetListRepository targetListRepository,
                           FilesRepository filesRepository,
                           StatusRepository statusRepository,
                           RuleRepository ruleRepository,
                           CampaignMapper campaignMapper
    ) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.targetListRepository =targetListRepository;
        this.filesRepository = filesRepository;
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
            List<Files> fileList = campaignVM.getFilesList() != null
                ? filesRepository.findAllById(campaignVM.getFilesList())
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
        List<Files> fileList = campaignVM.getFilesList() != null
            ? filesRepository.findAllById(campaignVM.getFilesList())
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
}
