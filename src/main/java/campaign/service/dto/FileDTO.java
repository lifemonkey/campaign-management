package campaign.service.dto;

import campaign.domain.File;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class FileDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Integer fileType;

    private String imageUrl;

    private String createdBy;

    private Long campaignId;

    private Long rewardId;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public FileDTO() {
    }

    public FileDTO(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.description = file.getDescription();
        this.fileType = file.getFileType();
        this.imageUrl = file.getImageUrl();
        if (file.getCampaign() != null) {
            this.campaignId = file.getCampaign().getId();
        } else if (file.getReward() != null) {
            this.rewardId = file.getReward().getId();
        }
        this.createdBy = file.getCreatedBy();
        this.createdDate = file.getCreatedDate();
        if (file.getLastModifiedDate() != null && file.getLastModifiedDate().isAfter(file.getCreatedDate())) {
            this.lastModifiedBy = file.getLastModifiedBy();
            this.lastModifiedDate = file.getLastModifiedDate();
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
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
        return "FilesDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", fileType=" + fileType +
            ", imageUrl='" + imageUrl + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
