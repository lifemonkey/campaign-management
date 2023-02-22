package campaign.service.dto;

import campaign.domain.Campaign;
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

    private Integer ruleConfiguration;

    private Integer campaignType;

    private List<TransactionTypeDTO> transactionTypes = new ArrayList<>();
    private List<RuleRewardConditionDTO> ruleRewardList = new ArrayList<>();

    private List<String> appliedCampaigns = new ArrayList<>();

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
        this.transactionTypes = rule.getTransactionTypes().stream().map(TransactionTypeDTO::new).collect(Collectors.toList());
        this.campaignType = rule.getCampaignType();
        this.ruleRewardList = rule.getRewardConditions().stream().map(RuleRewardConditionDTO::new).collect(Collectors.toList());
        this.appliedCampaigns = rule.getCampaignList().stream().map(Campaign::getName).collect(Collectors.toList());
        this.createdBy = rule.getCreatedBy();
        this.createdDate = rule.getCreatedDate();
        if (this.lastModifiedDate != null && this.createdDate != null && this.lastModifiedDate.isAfter(this.createdDate)) {
            this.lastModifiedBy = rule.getLastModifiedBy();
            this.lastModifiedDate = rule.getLastModifiedDate();
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

    public List<TransactionTypeDTO> getTransactionTypes() {
        return transactionTypes;
    }

    public void addTransactionTypes(List<TransactionTypeDTO> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    public List<RuleRewardConditionDTO> getRuleRewardList() {
        return ruleRewardList;
    }

    public void setRuleRewardList(List<RuleRewardConditionDTO> ruleRewardList) {
        this.ruleRewardList = ruleRewardList;
    }

    public List<String> getAppliedCampaigns() {
        return appliedCampaigns;
    }

    public void setAppliedCampaigns(List<String> appliedCampaigns) {
        this.appliedCampaigns = appliedCampaigns;
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
