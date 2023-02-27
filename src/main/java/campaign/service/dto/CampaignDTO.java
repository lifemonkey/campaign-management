package campaign.service.dto;

import campaign.domain.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampaignDTO {

    private Long id;

    @NotBlank
    private String name;

    @Size(min = 1, max = 4000)
    private String description;

    private LocalDateTime fromDate;

    private LocalDateTime endDate;

    private Integer campaignType;

    @Size(min = 1, max = 4000)
    private String notes;

    private String actionReason;

    private Boolean isTemplate;

    private Boolean unlimitedLuckyCode;

    private Boolean unlimitedPrize;

    private String status;

    private UserDTO approvedRejectedBy;

    private List<TargetListCampaignDTO> targetListList = new ArrayList<>();

    private List<FileDTO> fileList = new ArrayList<>();

    private List<CampaignRuleDTO> ruleList = new ArrayList<>();

    private List<GeneratedTimeDTO> generatedTimeList = new ArrayList<>();

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public CampaignDTO() {
    }

    public CampaignDTO(Campaign campaign) {
        this.id = campaign.getId();
        this.name = campaign.getName();
        this.description = campaign.getDescription();
        if (campaign.getFromDate() != null) {
            this.fromDate = campaign.getFromDate();
        }
        if (campaign.getEndDate() != null) {
            this.endDate = campaign.getEndDate();
        }
        this.campaignType = campaign.getCampaignType();
        this.notes = campaign.getNotes();
        this.actionReason = campaign.getActionReason();
        this.isTemplate = campaign.isTemplate();
        this.unlimitedLuckyCode = campaign.getUnlimitedLuckyCode();
        this.unlimitedPrize = campaign.getUnlimitedPrize();
        if (campaign.getStatus() != null) {
            this.status = campaign.getStatus().getName();
        }
        if (campaign.getApprovedRejectedBy() != null) {
            this.approvedRejectedBy = new UserDTO(campaign.getApprovedRejectedBy());
        }
        if (!campaign.getTargetLists().isEmpty()) {
            this.targetListList = campaign.getTargetLists().stream().map(TargetListCampaignDTO::new).collect(Collectors.toList());
        }
        if (!campaign.getFilesList().isEmpty()) {
            this.fileList = campaign.getFilesList().stream().map(FileDTO::new).collect(Collectors.toList());
        }
        if (!campaign.getRuleList().isEmpty()) {
            this.ruleList = campaign.getRuleList().stream().map(CampaignRuleDTO::new).collect(Collectors.toList());
        }
        if (!campaign.getGeneratedTimeList().isEmpty()) {
            this.generatedTimeList = campaign.getGeneratedTimeList().stream().map(GeneratedTimeDTO::new).collect(Collectors.toList());
        }

        this.createdBy = campaign.getCreatedBy();
        this.createdDate = campaign.getCreatedDate();
        if (campaign.getLastModifiedDate() != null && campaign.getLastModifiedDate().isAfter(campaign.getCreatedDate())) {
            this.lastModifiedBy = campaign.getLastModifiedBy();
            this.lastModifiedDate = campaign.getLastModifiedDate();
        } else {
            this.lastModifiedBy = null;
            this.lastModifiedDate = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Integer campaignType) {
        this.campaignType = campaignType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getActionReason() {
        return actionReason;
    }

    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }

    public Boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    public Boolean getUnlimitedLuckyCode() {
        return unlimitedLuckyCode;
    }

    public void setUnlimitedLuckyCode(Boolean unlimitedLuckyCode) {
        this.unlimitedLuckyCode = unlimitedLuckyCode;
    }

    public Boolean getUnlimitedPrize() {
        return unlimitedPrize;
    }

    public void setUnlimitedPrize(Boolean unlimitedPrize) {
        this.unlimitedPrize = unlimitedPrize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO getApprovedRejectedBy() {
        return approvedRejectedBy;
    }

    public void setApprovedRejectedBy(UserDTO approvedRejectedBy) {
        this.approvedRejectedBy = approvedRejectedBy;
    }

    public List<TargetListCampaignDTO> getTargetListList() {
        return targetListList;
    }

    public void setTargetListList(List<TargetListCampaignDTO> targetListList) {
        this.targetListList = targetListList;
    }

    public List<FileDTO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileDTO> fileList) {
        this.fileList = fileList;
    }

    public List<CampaignRuleDTO> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<CampaignRuleDTO> ruleList) {
        this.ruleList = ruleList;
    }

    public List<GeneratedTimeDTO> getGeneratedTimeList() {
        return generatedTimeList;
    }

    public void setGeneratedTimeList(List<GeneratedTimeDTO> generatedTimeList) {
        this.generatedTimeList = generatedTimeList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "CampaignDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fromDate=" + fromDate +
            ", endDate=" + endDate +
            ", campaignType=" + campaignType +
            ", notes='" + notes + '\'' +
            ", status=" + status +
//            ", approvedRejectedBy='" + approvedRejectedBy + '\'' +
//            ", targetLists=" + targetLists +
//            ", filesList=" + filesList +
            '}';
    }
}
