package campaign.service.dto;

import campaign.domain.TargetList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TargetListCampaignDTO {

    private Long id;

    @NotBlank
    private String name;

    @Size(max = 4000)
    private String description;

    private Integer targetType;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public TargetListCampaignDTO(TargetList targetList) {
        this.id = targetList.getId();
        this.name = targetList.getName();
        this.description = targetList.getDescription();
        this.targetType = targetList.getTargetType();

        this.createdBy = targetList.getCreatedBy();
        this.createdDate = targetList.getCreatedDate();
        if (targetList.getLastModifiedDate() != null && targetList.getLastModifiedDate().isAfter(targetList.getCreatedDate())) {
            this.lastModifiedBy = targetList.getLastModifiedBy();
            this.lastModifiedDate = targetList.getLastModifiedDate();
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

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
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
        return "TargetListCampaignDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", targetType=" + targetType +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
