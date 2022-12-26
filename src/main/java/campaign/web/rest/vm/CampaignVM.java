package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String actionReason;

    private Long statusId;

    private Long approvedRejectedBy;

    private List<Long> targetListIds = new ArrayList<>();

    private List<Long> fileIds = new ArrayList<>();

    private List<Long> ruleIds = new ArrayList<>();

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

    public List<Long> getTargetListIds() {
        return targetListIds;
    }

    public void addTargetListIds(List<Long> targetListIds) {
        this.targetListIds.addAll(targetListIds);
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void addFileIds(List<Long> fileIds) {
        this.fileIds.addAll(fileIds);
    }

    public List<Long> getRuleIds() {
        return ruleIds;
    }

    public void addRuleIds(List<Long> ruleIds) {
        this.ruleIds.addAll(ruleIds);
    }

    @Override
    public String toString() {
        return "CampaignVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fromDate=" + fromDate +
            ", endDate=" + endDate +
            ", campaignType=" + campaignType +
            ", actionReason='" + actionReason + '\'' +
            ", notes='" + notes + '\'' +
            ", statusId=" + statusId +
            ", approvedRejectedBy=" + approvedRejectedBy +
            ", targetListIds=" + targetListIds +
            ", fileIds=" + fileIds +
            ", ruleIds=" + ruleIds +
            '}';
    }
}
