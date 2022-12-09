package campaign.service.dto;

import campaign.domain.Rule;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class RuleDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Integer durationType;

    private String durationValue;

    private Integer rewardCondition;

    private String transactionType;

    private String ruleConfiguration;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public RuleDTO() {
    }

    public RuleDTO(Rule rule) {
        this.id = rule.getId();
        this.name = rule.getName();
        this.description = rule.getDescription();
        this.durationType = rule.getDurationType();
        this.durationValue = rule.getDurationValue();
        this.rewardCondition = rule.getRewardCondition();
        this.ruleConfiguration = rule.getRuleConfiguration().getName();
        this.transactionType = rule.getTransactionType().getName();
        this.createdBy = rule.getCreatedBy();
        this.createdDate = rule.getCreatedDate();
        this.lastModifiedBy = rule.getLastModifiedBy();
        this.lastModifiedDate = rule.getLastModifiedDate();
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

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public String getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(String durationValue) {
        this.durationValue = durationValue;
    }

    public Integer getRewardCondition() {
        return rewardCondition;
    }

    public void setRewardCondition(Integer rewardCondition) {
        this.rewardCondition = rewardCondition;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRuleConfiguration() {
        return ruleConfiguration;
    }

    public void setRuleConfiguration(String ruleConfiguration) {
        this.ruleConfiguration = ruleConfiguration;
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
        return "RuleDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", durationType=" + durationType +
            ", durationValue='" + durationValue + '\'' +
            ", rewardCondition=" + rewardCondition +
            ", transactionType='" + transactionType + '\'' +
            ", ruleConfiguration='" + ruleConfiguration + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
