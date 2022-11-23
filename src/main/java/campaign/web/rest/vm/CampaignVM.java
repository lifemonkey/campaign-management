package campaign.web.rest.vm;

import campaign.domain.Status;

import java.time.LocalDateTime;
import java.util.List;

public class CampaignVM {

    private String name;

    private String description;

    private LocalDateTime fromDate;

    private LocalDateTime endDate;

    private Integer campaignType;

    private String notes;

    private Status statusId;

    private Long approvedRejectedBy;

    private List<Long> targetLists;

    private List<Long> filesList;

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

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public Long getApprovedRejectedBy() {
        return approvedRejectedBy;
    }

    public void setApprovedRejectedBy(Long approvedRejectedBy) {
        this.approvedRejectedBy = approvedRejectedBy;
    }

    public List<Long> getTargetLists() {
        return targetLists;
    }

    public void setTargetLists(List<Long> targetLists) {
        this.targetLists = targetLists;
    }

    public List<Long> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Long> filesList) {
        this.filesList = filesList;
    }

    @Override
    public String toString() {
        return "CampaignVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fromDate=" + fromDate +
            ", endDate=" + endDate +
            ", campaignType=" + campaignType +
            ", notes='" + notes + '\'' +
            ", statusId=" + statusId +
            ", approvedRejectedBy=" + approvedRejectedBy +
            ", targetLists=" + targetLists +
            ", filesList=" + filesList +
            '}';
    }
}
