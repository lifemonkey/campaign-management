package campaign.service.mapper;

import campaign.domain.Campaign;
import campaign.domain.TargetList;
import campaign.service.dto.CampaignDTO;
import campaign.web.rest.vm.CampaignVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CampaignMapper {

    private final UserMapper userMapper;

    private final TargetListMapper targetListMapper;

    private final FilesMapper filesMapper;

    public CampaignMapper(UserMapper userMapper, TargetListMapper targetListMapper, FilesMapper filesMapper) {
        this.userMapper = userMapper;
        this.targetListMapper = targetListMapper;
        this.filesMapper = filesMapper;
    }

    public CampaignDTO campaignToCampaignDTO(Campaign campaign) {
        return new CampaignDTO(campaign);
    }

    public List<CampaignDTO> campaignToCampaignDTOs(List<Campaign> campaignList) {
        return campaignList.stream()
            .filter(Objects::nonNull)
            .map(this::campaignToCampaignDTO)
            .collect(Collectors.toList());
    }

    public Campaign campaignDTOToCampaign(CampaignDTO campaignDTO) {
        if (campaignDTO == null) {
            return null;
        } else {
            Campaign campaign = new Campaign();
            campaign.setId(campaignDTO.getId());
            campaign.setName(campaignDTO.getName());
            campaign.setDescription(campaignDTO.getDescription());
            if (campaignDTO.getFromDate() != null) {
                campaign.setFromDate(campaignDTO.getFromDate());
            }
            if (campaignDTO.getEndDate() != null) {
                campaign.setEndDate(campaignDTO.getEndDate());
            }
            campaign.setCampaignType(campaignDTO.getCampaignType());
            campaign.setNotes(campaignDTO.getNotes());
            campaign.setStatusId(campaignDTO.getStatusId());
            if (campaignDTO.getApprovedRejectedBy() != null) {
                campaign.setApprovedRejectedBy(userMapper.userDTOToUser(campaignDTO.getApprovedRejectedBy()));
            }
            if (campaignDTO.getTargetLists() != null) {
                campaign.setTargetLists(targetListMapper.targetListDTOToTargetLists(campaignDTO.getTargetLists()));
            }
            if (campaignDTO.getFilesList() != null) {
                campaign.setFilesList(filesMapper.filesDTOToFiles(campaignDTO.getFilesList()));
            }

            campaign.setCreatedBy(campaignDTO.getCreatedBy());
            campaign.setCreatedDate(campaignDTO.getCreatedDate());
            campaign.setLastModifiedBy(campaignDTO.getLastModifiedBy());
            campaign.setLastModifiedDate(campaignDTO.getLastModifiedDate());

            return campaign;
        }
    }

    public Campaign campaignVMToCampaign(CampaignVM campaignVM) {
        if (campaignVM == null) {
            return null;
        } else {
            Campaign campaign = new Campaign();

            if (campaignVM.getName() != null) {
                campaign.setName(campaignVM.getName());
            }
            if (campaignVM.getDescription() != null) {
                campaign.setDescription(campaignVM.getDescription());
            }
            if (campaignVM.getFromDate() != null) {
                campaign.setFromDate(campaignVM.getFromDate());
            }
            if (campaignVM.getEndDate() != null) {
                campaign.setEndDate(campaignVM.getEndDate());
            }
            if (campaignVM.getCampaignType() != null) {
                campaign.setCampaignType(campaignVM.getCampaignType());
            }
            if (campaignVM.getNotes() != null) {
                campaign.setNotes(campaignVM.getNotes());
            }
            if (campaignVM.getStatusId() != null) {
                campaign.setStatusId(campaignVM.getStatusId());
            }

            return campaign;
        }
    }
}
