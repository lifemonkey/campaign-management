package campaign.service.dto;

import campaign.domain.Reward;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class RewardConditionRewardDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Integer prizeType;

    private Integer prizeValue;

    private Integer numOfPrize;

    private Integer released;

    private String messageWinnerEN;

    private String messageWinnerSW;

    private String messageBalanceEN;

    private String messageBalanceSW;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    private Integer level;

    public RewardConditionRewardDTO() {
    }

    public RewardConditionRewardDTO(Reward reward) {
        this.id = reward.getId();
        this.name = reward.getName();
        this.description = reward.getDescription();
        this.prizeType = reward.getPrizeType();
        this.prizeValue = reward.getPrizeValue();
        this.numOfPrize = reward.getNumOfPrize();
        this.released = reward.getReleased();
        this.messageWinnerEN = reward.getMessageWinnerEN();
        this.messageWinnerSW = reward.getMessageWinnerSW();
        this.messageBalanceEN = reward.getMessageBalanceEN();
        this.messageBalanceSW = reward.getMessageBalanceSW();
        this.level = reward.getLevel();
        this.createdBy = reward.getCreatedBy();
        this.createdDate = reward.getCreatedDate();
        this.lastModifiedBy = reward.getLastModifiedBy();
        this.lastModifiedDate = reward.getLastModifiedDate();
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

    public Integer getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(Integer prizeType) {
        this.prizeType = prizeType;
    }

    public Integer getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(Integer prizeValue) {
        this.prizeValue = prizeValue;
    }

    public Integer getNumOfPrize() {
        return numOfPrize;
    }

    public void setNumOfPrize(Integer numOfPrize) {
        this.numOfPrize = numOfPrize;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    public String getMessageWinnerEN() {
        return messageWinnerEN;
    }

    public void setMessageWinnerEN(String messageWinnerEN) {
        this.messageWinnerEN = messageWinnerEN;
    }

    public String getMessageWinnerSW() {
        return messageWinnerSW;
    }

    public void setMessageWinnerSW(String messageWinnerSW) {
        this.messageWinnerSW = messageWinnerSW;
    }

    public String getMessageBalanceEN() {
        return messageBalanceEN;
    }

    public void setMessageBalanceEN(String messageBalanceEN) {
        this.messageBalanceEN = messageBalanceEN;
    }

    public String getMessageBalanceSW() {
        return messageBalanceSW;
    }

    public void setMessageBalanceSW(String messageBalanceSW) {
        this.messageBalanceSW = messageBalanceSW;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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
        return "RewardDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", prizeType=" + prizeType +
            ", prizeValue=" + prizeValue +
            ", numOfPrize=" + numOfPrize +
            ", released=" + released +
            ", messageWinnerEN='" + messageWinnerEN + '\'' +
            ", messageWinnerSW='" + messageWinnerSW + '\'' +
            ", messageBalanceEN='" + messageBalanceEN + '\'' +
            ", messageBalanceSW='" + messageBalanceSW + '\'' +
            '}';
    }
}
