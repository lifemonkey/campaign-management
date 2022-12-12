package campaign.web.rest.vm;

import campaign.domain.EDuration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RuleVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private EDuration durationType;

    private String durationValue;

    private Integer rewardCondition;

    private Long transactionType;

    private Long ruleConfiguration;

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

    public Integer getRewardCondition() {
        return rewardCondition;
    }

    public void setRewardCondition(Integer rewardCondition) {
        this.rewardCondition = rewardCondition;
    }

    public Long getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Long transactionType) {
        this.transactionType = transactionType;
    }

    public Long getRuleConfiguration() {
        return ruleConfiguration;
    }

    public void setRuleConfiguration(Long ruleConfiguration) {
        this.ruleConfiguration = ruleConfiguration;
    }

    @Override
    public String toString() {
        return "RuleVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", durationType=" + durationType +
            ", durationValue='" + durationValue + '\'' +
            ", rewardCondition=" + rewardCondition +
            ", transactionType='" + transactionType + '\'' +
            ", ruleConfiguration='" + ruleConfiguration + '\'' +
            '}';
    }
}
