package campaign.service.dto;

import campaign.domain.Reward;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RewardDTO {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Integer prizeType;

    private Integer prizeValue;

    private Integer numOfPrize;

    private Integer released;

    private List<FileDTO> files;

    private String messageWinnerEN;

    private String messageWinnerSW;

    private String messageBalanceEN;

    private String messageBalanceSW;

    private List<VoucherDTO> voucherCodes;

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    private Integer level;

    private CampaignDTO appliedCampaign;

    public RewardDTO() {
    }

    public RewardDTO(Reward reward) {
        this.id = reward.getId();
        this.name = reward.getName();
        this.description = reward.getDescription();
        this.prizeType = reward.getPrizeType();
        this.prizeValue = reward.getPrizeValue();
        this.numOfPrize = reward.getNumOfPrize();
        this.released = reward.getReleased();
        if (reward.getFiles() != null) {
            this.files = reward.getFiles().stream()
                .map(FileDTO::new)
                .collect(Collectors.toList());
        }
        this.messageWinnerEN = reward.getMessageWinnerEN();
        this.messageWinnerSW = reward.getMessageWinnerSW();
        this.messageBalanceEN = reward.getMessageBalanceEN();
        this.messageBalanceSW = reward.getMessageBalanceSW();
        this.level = reward.getLevel();
        if (reward.getVouchers() != null) {
            this.voucherCodes = reward.getVouchers().stream()
                .map(VoucherDTO::new)
                .collect(Collectors.toList());
        }
        this.createdBy = reward.getCreatedBy();
        this.createdDate = reward.getCreatedDate();
        if (this.lastModifiedDate != null && this.createdDate != null && this.lastModifiedDate.isAfter(this.createdDate)) {
            this.lastModifiedBy = reward.getLastModifiedBy();
            this.lastModifiedDate = reward.getLastModifiedDate();
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

    public List<FileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<FileDTO> files) {
        this.files = files;
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

    public List<VoucherDTO> getVoucherCodes() {
        return voucherCodes;
    }

    public void setVoucherCodes(List<VoucherDTO> voucherCodes) {
        this.voucherCodes = voucherCodes;
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

    public CampaignDTO getAppliedCampaign() {
        return appliedCampaign;
    }

    public void setAppliedCampaign(CampaignDTO appliedCampaign) {
        this.appliedCampaign = appliedCampaign;
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
