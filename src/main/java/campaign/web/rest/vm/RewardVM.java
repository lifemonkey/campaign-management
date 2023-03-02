package campaign.web.rest.vm;

import campaign.service.dto.VoucherDTO;

import java.util.List;

public class RewardVM {

    private Long id;

    private String name;

    private String description;

    private Integer prizeType;

    private Long prizeValue;

    private Long numOfPrize;

    private Integer released;

    private Boolean isTemplate;

    private List<FileVM> files;

    private String messageWinnerEN;

    private String messageWinnerSW;

    private String messageBalanceEN;

    private String messageBalanceSW;

    private Integer level;

    private List<VoucherDTO> voucherCodes;

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

    public Long getPrizeValue() {
        return prizeValue;
    }

    public void setPrizeValue(Long prizeValue) {
        this.prizeValue = prizeValue;
    }

    public Long getNumOfPrize() {
        return numOfPrize;
    }

    public void setNumOfPrize(Long numOfPrize) {
        this.numOfPrize = numOfPrize;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    public List<FileVM> getFiles() {
        return files;
    }

    public void setFiles(List<FileVM> files) {
        this.files = files;
    }

    public Boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
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

    public List<VoucherDTO> getVoucherCodes() {
        return voucherCodes;
    }

    public void setVoucherCodes(List<VoucherDTO> voucherCodes) {
        this.voucherCodes = voucherCodes;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "RewardVM{" +
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
            ", level=" + level +
            '}';
    }
}
