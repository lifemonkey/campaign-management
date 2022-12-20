package campaign.service.dto;

import campaign.domain.Reward;
import campaign.domain.Rule;

public class RewardConditionDTO {

    private Long id;

    private Float amountMin;

    private Float amountMax;

    private Integer timesMin;

    private Integer timesMax;

    private Integer numberCodes;

    private Rule rule;

    // TODO link to rewardDTO
//    private Reward reward;


    public RewardConditionDTO() {
    }

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

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "RewardConditionDTO{" +
            "id=" + id +
            ", amountMin=" + amountMin +
            ", amountMax=" + amountMax +
            ", timesMin=" + timesMin +
            ", timesMax=" + timesMax +
            ", numberCodes=" + numberCodes +
            '}';
    }
}
