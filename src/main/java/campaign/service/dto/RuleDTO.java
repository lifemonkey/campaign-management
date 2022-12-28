package campaign.service.dto;

import campaign.domain.EDuration;
import campaign.domain.Rule;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RuleDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private EDuration durationType;

    private String durationValue;

    private TransactionTypeDTO transactionType;

    private Integer ruleConfiguration;

    private Integer campaignType;

    private List<RuleRewardDTO> ruleRewardList = new ArrayList<>();

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
        this.ruleConfiguration = rule.getRuleConfiguration();
        this.transactionType = new TransactionTypeDTO(rule.getTransactionType());
        this.campaignType = rule.getCampaignType();
        this.ruleRewardList = rule.getRewardConditions().stream().map(RuleRewardDTO::new).collect(Collectors.toList());
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

    public EDuration getDurationType() {
        return durationType;
    }

    public void setDurationType(EDuration durationType) {
        this.durationType = durationType;
    }

    public String getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(String durationValue) {
        this.durationValue = durationValue;
    }

    public TransactionTypeDTO getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeDTO transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getRuleConfiguration() {
        return ruleConfiguration;
    }

    public void setRuleConfiguration(Integer ruleConfiguration) {
        this.ruleConfiguration = ruleConfiguration;
    }

    public Integer getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(Integer campaignType) {
        this.campaignType = campaignType;
    }

    public List<RuleRewardDTO> getRuleRewardList() {
        return ruleRewardList;
    }

    public void setRuleRewardList(List<RuleRewardDTO> ruleRewardList) {
        this.ruleRewardList = ruleRewardList;
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
            ", ruleConfiguration='" + ruleConfiguration + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
