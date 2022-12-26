package campaign.web.rest.vm;

public class RewardConditionVM {

    private Long id;

    private Float amountMin;

    private Float amountMax;

    private Integer timesMin;

    private Integer timesMax;

    private Integer numberCodes;

    private Long ruleId;

    private Long rewardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(Float amountMin) {
        this.amountMin = amountMin;
    }

    public Float getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(Float amountMax) {
        this.amountMax = amountMax;
    }

    public Integer getTimesMin() {
        return timesMin;
    }

    public void setTimesMin(Integer timesMin) {
        this.timesMin = timesMin;
    }

    public Integer getTimesMax() {
        return timesMax;
    }

    public void setTimesMax(Integer timesMax) {
        this.timesMax = timesMax;
    }

    public Integer getNumberCodes() {
        return numberCodes;
    }

    public void setNumberCodes(Integer numberCodes) {
        this.numberCodes = numberCodes;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
    }

    @Override
    public String toString() {
        return "RewardConditionVM{" +
            "id=" + id +
            ", amountMin=" + amountMin +
            ", amountMax=" + amountMax +
            ", timesMin=" + timesMin +
            ", timesMax=" + timesMax +
            ", numberCodes=" + numberCodes +
            ", ruleId=" + ruleId +
            ", rewardId=" + rewardId +
            '}';
    }
}
