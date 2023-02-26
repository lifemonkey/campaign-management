package campaign.service.dto;

import campaign.domain.TransactionType;

import java.time.LocalDateTime;

public class TransactionTypeDTO {

    private Long id;

    private String name;

    private String description;

    private String externalId;

    private Integer status;

    private String transTypeEN;

    private String transTypeSW;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public TransactionTypeDTO() {
    }

    public TransactionTypeDTO(TransactionType transactionType) {
        this.id = transactionType.getId();
        this.name = transactionType.getName();
        this.description = transactionType.getDescription();
        this.externalId = transactionType.getExternalId();
        this.status = transactionType.getStatus();
        this.transTypeEN = transactionType.getTransTypeEN();
        this.transTypeSW = transactionType.getTransTypeSW();

        this.createdBy = transactionType.getCreatedBy();
        this.createdDate = transactionType.getCreatedDate();
        if (transactionType.getLastModifiedDate() != null && transactionType.getLastModifiedDate().isAfter(transactionType.getCreatedDate())) {
            this.lastModifiedBy = transactionType.getLastModifiedBy();
            this.lastModifiedDate = transactionType.getLastModifiedDate();
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransTypeEN() {
        return transTypeEN;
    }

    public void setTransTypeEN(String transTypeEN) {
        this.transTypeEN = transTypeEN;
    }

    public String getTransTypeSW() {
        return transTypeSW;
    }

    public void setTransTypeSW(String transTypeSW) {
        this.transTypeSW = transTypeSW;
    }

    @Override
    public String toString() {
        return "TransactionTypeDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", externalId='" + externalId + '\'' +
            ", status=" + status +
            ", transTypeEN='" + transTypeEN + '\'' +
            ", transTypeSW='" + transTypeSW + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
