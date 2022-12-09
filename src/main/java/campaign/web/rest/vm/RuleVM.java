package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class RuleVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private Integer durationType;

    private String durationValue;

    private Integer rewardCondition;

    private String transactionType;

    private String ruleConfiguration;

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
