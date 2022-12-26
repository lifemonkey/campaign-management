package campaign.service.mapper;

import campaign.domain.Campaign;
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

    private final FileMapper fileMapper;

    private final RuleMapper ruleMapper;

    public CampaignMapper(UserMapper userMapper, TargetListMapper targetListMapper, FileMapper fileMapper, RuleMapper ruleMapper) {
        this.userMapper = userMapper;
        this.targetListMapper = targetListMapper;
        this.fileMapper = fileMapper;
        this.ruleMapper = ruleMapper;
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

            return campaign;
        }
    }
}
