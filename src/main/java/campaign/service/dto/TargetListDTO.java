package campaign.service.dto;

import campaign.domain.TargetList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TargetListDTO {

    private Long id;

    @NotBlank
    private String name;

    @Size(max = 4000)
    private String description;

    private Integer targetType;

    private List<AccountDTO> accountList;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public TargetListDTO() {
    }

    public TargetListDTO(TargetList targetList) {
        this.id = targetList.getId();
        this.name = targetList.getName();
        this.description = targetList.getDescription();
        this.targetType = targetList.getTargetType();
        this.accountList = new ArrayList<>();

        if(targetList.getAccountList() != null && !targetList.getAccountList().isEmpty()) {
            this.accountList.addAll(targetList.getAccountList().stream().map(AccountDTO::new).collect(Collectors.toList()));
        }

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

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void addAccountList(List<AccountDTO> accountList) {
        this.accountList.addAll(accountList);
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
        return "TargetListDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", targetType=" + targetType +
            ", accountList='" + accountList + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
