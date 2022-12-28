package campaign.web.rest.vm;

import campaign.domain.EDuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class RuleVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private EDuration durationType;

    private String durationValue;

    private Long transactionType;

    private Integer ruleConfiguration;

    private Integer campaignType;

    private List<RewardConditionVM> rewardConditions = new ArrayList<>();

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

    public Long getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Long transactionType) {
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

    public List<RewardConditionVM> getRewardConditions() {
        return rewardConditions;
    }

    public void addRewardConditions(List<RewardConditionVM> rewardConditions) {
        this.rewardConditions.addAll(rewardConditions);
    }

    @Override
    public String toString() {
        return "RuleVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", durationType=" + durationType +
            ", durationValue='" + durationValue + '\'' +
            ", transactionType='" + transactionType + '\'' +
            ", campaignType='" + campaignType + '\'' +
            ", ruleConfiguration='" + ruleConfiguration + '\'' +
            '}';
    }
}
