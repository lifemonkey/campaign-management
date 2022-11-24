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

    private Status statusId;

    private UserDTO approvedRejectedBy;

    private List<TargetListDTO> targetLists;

    private List<FilesDTO> filesList;

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
        this.statusId = campaign.getStatusId();
        if (campaign.getApprovedRejectedBy() != null) {
            this.approvedRejectedBy = new UserDTO(campaign.getApprovedRejectedBy());
        }
        this.targetLists = new ArrayList<>();
        if (campaign.getTargetLists() != null) {
            this.targetLists.addAll(campaign.getTargetLists().stream().map(TargetListDTO::new).collect(Collectors.toList()));
        }
        if (campaign.getFilesList() != null) {
            this.filesList = campaign.getFilesList().stream().map(FilesDTO::new).collect(Collectors.toList());
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

    public Status getStatusId() {
        return statusId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public UserDTO getApprovedRejectedBy() {
        return approvedRejectedBy;
    }

    public void setApprovedRejectedBy(UserDTO approvedRejectedBy) {
        this.approvedRejectedBy = approvedRejectedBy;
    }

    public List<TargetListDTO> getTargetLists() {
        return targetLists;
    }

    public void addTargetLists(List<TargetListDTO> targetLists) {
        this.targetLists.addAll(targetLists);
    }

    public List<FilesDTO> getFilesList() {
        return filesList;
    }

    public void addFilesList(List<FilesDTO> filesList) {
        this.filesList.addAll(filesList);
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
            ", statusId=" + statusId +
//            ", approvedRejectedBy='" + approvedRejectedBy + '\'' +
//            ", targetLists=" + targetLists +
//            ", filesList=" + filesList +
            '}';
    }
}
