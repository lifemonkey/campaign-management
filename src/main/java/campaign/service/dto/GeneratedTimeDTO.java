package campaign.service.dto;

import campaign.domain.GeneratedTime;

import java.time.LocalDateTime;

public class GeneratedTimeDTO {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long campaignId;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;


    public GeneratedTimeDTO() {
    }

    public GeneratedTimeDTO(GeneratedTime generatedTime) {
        this.id = generatedTime.getId();
        this.startTime = generatedTime.getStartTime();
        this.endTime = generatedTime.getEndTime();
        if (generatedTime.getCampaign() != null) {
            this.campaignId = generatedTime.getCampaign().getId();
        }
        this.createdBy = generatedTime.getCreatedBy();
        this.createdDate = generatedTime.getCreatedDate();
        if (generatedTime.getLastModifiedDate() != null && generatedTime.getLastModifiedDate().isAfter(generatedTime.getCreatedDate())) {
            this.lastModifiedBy = generatedTime.getLastModifiedBy();
            this.lastModifiedDate = generatedTime.getLastModifiedDate();
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
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
        return "GeneratedTimeDTO{" +
            "id=" + id +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", campaignId=" + campaignId +
            '}';
    }
}
