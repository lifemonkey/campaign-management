package campaign.service.mapper;

import campaign.domain.Campaign;
import campaign.service.dto.CampaignWRelDTO;
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

    public CampaignWRelDTO campaignToCampaignWRelDTO(Campaign campaign) {
        return new CampaignWRelDTO(campaign);
    }

    public List<CampaignWRelDTO> campaignToCampaignWRelDTOs(List<Campaign> campaignList) {
        return campaignList.stream()
            .filter(Objects::nonNull)
            .map(this::campaignToCampaignWRelDTO)
            .collect(Collectors.toList());
    }

    public Campaign campaignDTOToCampaign(CampaignWRelDTO campaignWRelDTO) {
        if (campaignWRelDTO == null) {
            return null;
        } else {
            Campaign campaign = new Campaign();
            campaign.setId(campaignWRelDTO.getId());
            campaign.setName(campaignWRelDTO.getName());
            campaign.setDescription(campaignWRelDTO.getDescription());
            if (campaignWRelDTO.getFromDate() != null) {
                campaign.setFromDate(campaignWRelDTO.getFromDate());
            }
            if (campaignWRelDTO.getEndDate() != null) {
                campaign.setEndDate(campaignWRelDTO.getEndDate());
            }

            campaign.setCampaignType(campaignWRelDTO.getCampaignType());
            campaign.setNotes(campaignWRelDTO.getNotes());
            campaign.setCreatedBy(campaignWRelDTO.getCreatedBy());
            campaign.setCreatedDate(campaignWRelDTO.getCreatedDate());
            campaign.setLastModifiedBy(campaignWRelDTO.getLastModifiedBy());
            campaign.setLastModifiedDate(campaignWRelDTO.getLastModifiedDate());

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
