package campaign.service.dto;

import campaign.domain.Campaign;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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

    private String status;

    private Long approvedRejectedBy;

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
        if (campaign.getStatus() != null) {
            this.status = campaign.getStatus().getName();
        }
        if (campaign.getApprovedRejectedBy() != null) {
            this.approvedRejectedBy = campaign.getApprovedRejectedBy().getId();
        }

        this.createdBy = campaign.getCreatedBy();
        this.createdDate = campaign.getCreatedDate();
        this.lastModifiedBy = campaign.getLastModifiedBy();
        this.lastModifiedDate = campaign.getLastModifiedDate();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApprovedRejectedBy() {
        return approvedRejectedBy;
    }

    public void setApprovedRejectedBy(Long approvedRejectedBy) {
        this.approvedRejectedBy = approvedRejectedBy;
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
            '}';
    }
}
