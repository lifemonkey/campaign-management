package campaign.service.dto;

import campaign.domain.RewardCondition;

import java.time.LocalDateTime;

public class RewardConditionDTO {

    private Long id;

    private Float amountMin;

    private Float amountMax;

    private Integer timesMin;

    private Integer timesMax;

    private Integer numberCodes;

    private RuleDTO rule;

    private RewardDTO reward;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;


    public RewardConditionDTO() {
    }

    public RewardConditionDTO(RewardCondition rewardCondition) {
        this.id = rewardCondition.getId();
        this.amountMin = rewardCondition.getAmountMin();
        this.amountMax = rewardCondition.getAmountMax();
        this.timesMin = rewardCondition.getTimesMin();
        this.timesMax = rewardCondition.getTimesMax();
        this.numberCodes = rewardCondition.getNumberCodes();

        if (rewardCondition.getRule() != null) {
            this.rule = new RuleDTO(rewardCondition.getRule());
        }
        if (rewardCondition.getReward() != null) {
            this.reward = new RewardDTO(rewardCondition.getReward());
        }

        this.createdBy = rewardCondition.getCreatedBy();
        this.createdDate = rewardCondition.getCreatedDate();
        if (rewardCondition.getLastModifiedDate() != null && rewardCondition.getLastModifiedDate().isAfter(rewardCondition.getCreatedDate())) {
            this.lastModifiedBy = rewardCondition.getLastModifiedBy();
            this.lastModifiedDate = rewardCondition.getLastModifiedDate();
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

    public RuleDTO getRule() {
        return rule;
    }

    public void setRule(RuleDTO rule) {
        this.rule = rule;
    }

    public RewardDTO getReward() {
        return reward;
    }

    public void setReward(RewardDTO reward) {
        this.reward = reward;
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
