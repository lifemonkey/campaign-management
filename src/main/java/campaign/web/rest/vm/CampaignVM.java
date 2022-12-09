package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class CampaignVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private LocalDateTime fromDate;

    private LocalDateTime endDate;

    private Integer campaignType;

    @Size(max = 4000)
    private String notes;

    private Long statusId;

    private Long approvedRejectedBy;

    private List<Long> targetLists;

    private List<Long> filesList;

    private List<Long> ruleList;

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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
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

    public void addTargetLists(List<Long> targetLists) {
        this.targetLists.addAll(targetLists);
    }

    public List<Long> getFilesList() {
        return filesList;
    }

    public void addFilesList(List<Long> filesList) {
        this.filesList.addAll(filesList);
    }

    public List<Long> getRuleList() {
        return ruleList;
    }

    public void addRuleList(List<Long> ruleList) {
        this.ruleList.addAll(ruleList);
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
            ", filesList=" + filesList +
            '}';
    }
}
