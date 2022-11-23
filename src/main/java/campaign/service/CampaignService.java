package campaign.service;

import campaign.domain.Campaign;
import campaign.domain.Files;
import campaign.domain.TargetList;
import campaign.domain.User;
import campaign.repository.CampaignRepository;
import campaign.repository.FilesRepository;
import campaign.repository.TargetListRepository;
import campaign.repository.UserRepository;
import campaign.service.dto.CampaignDTO;
import campaign.service.mapper.CampaignMapper;
import campaign.web.rest.vm.CampaignVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CampaignService {

    private final Logger log = LoggerFactory.getLogger(CampaignService.class);

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final TargetListRepository targetListRepository;

    private final FilesRepository filesRepository;

    private final CampaignMapper campaignMapper;

    public CampaignService(CampaignRepository campaignRepository,
                           UserRepository userRepository,
                           TargetListRepository targetListRepository,
                           FilesRepository filesRepository,
                           CampaignMapper campaignMapper
    ) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
        this.targetListRepository =targetListRepository;
        this.filesRepository = filesRepository;
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
    public Page<CampaignDTO> getAllCampaign(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(CampaignDTO::new);
    }

    @Transactional
    public CampaignDTO createCampaign(CampaignVM campaignVM) {
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);

        if (campaign != null) {
            // handle approved/rejected user
            if (campaignVM.getApprovedRejectedBy() != null) {
                Optional<User> userOpt = userRepository.findById(campaignVM.getApprovedRejectedBy());
                if (userOpt.isPresent()) {
                    campaign.setApprovedRejectedBy(userOpt.get());
                }
            }

            // handle target list
            if (campaignVM.getTargetLists() != null) {
                List<TargetList> targetLists = targetListRepository.findAllById(campaignVM.getTargetLists());
                if (targetLists != null && !targetLists.isEmpty()) {
                    campaign.setTargetLists(targetLists);
                }
            }

            // handle file list
            if (campaignVM.getFilesList() != null) {
                List<Files> fileList = filesRepository.findAllById(campaignVM.getFilesList());
                if (fileList != null && !fileList.isEmpty()) {
                    campaign.setFilesList(fileList);
                }
            }

            Campaign createdCampaign = campaignRepository.save(campaign);
            return campaignMapper.campaignToCampaignDTO(createdCampaign);
        }

        return new CampaignDTO();
    }

    @Transactional
    public CampaignDTO updateCampaign(Long id, CampaignVM campaignVM) {
        Campaign campaign = campaignMapper.campaignVMToCampaign(campaignVM);
        campaign.setId(id);
        Campaign updatedCampaign = campaignRepository.save(campaign);
        return campaignMapper.campaignToCampaignDTO(updatedCampaign);
    }

    @Transactional
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}
