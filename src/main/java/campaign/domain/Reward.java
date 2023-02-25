package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reward extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_sequence_generator")
    @SequenceGenerator(name = "reward_sequence_generator", sequenceName = "reward_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 400)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "prize_type", length = 1)
    private Integer prizeType;

    @Column(name = "prize_value")
    private Integer prizeValue;

    @Column(name = "number_of_prize")
    private Integer numOfPrize;

    @Column(name = "released")
    private Integer released;

    @Column(name = "is_template", nullable = false)
    private Boolean isTemplate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reward")
    private List<File> files = new ArrayList<>();

    @Column(name = "message_winner_en", length = 4000)
    private String messageWinnerEN;

    @Column(name = "message_winner_sw", length = 4000)
    private String messageWinnerSW;

    @Column(name = "message_balance_en", length = 4000)
    private String messageBalanceEN;

    @Column(name = "message_balance_sw", length = 4000)
    private String messageBalanceSW;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reward")
    private List<Voucher> vouchers = new ArrayList<>();

    @Column(name = "reward_level")
    private Integer level;

    @Column(name = "campaign_id")
    private Long campaignId;

    public Reward() {
    }

    public Reward(String name, String description, Integer prizeType, Integer prizeValue, Integer numOfPrize, Integer released, String messageWinnerEN, String messageWinnerSW, String messageBalanceEN, String messageBalanceSW, Integer level, Long campaignId, Boolean isTemplate) {
        this.name = name;
        this.description = description;
        this.prizeType = prizeType;
        this.prizeValue = prizeValue;
        this.numOfPrize = numOfPrize;
        this.released = released;
        this.messageWinnerEN = messageWinnerEN;
        this.messageWinnerSW = messageWinnerSW;
        this.messageBalanceEN = messageBalanceEN;
        this.messageBalanceSW = messageBalanceSW;
        this.level = level;
        this.campaignId = campaignId;
        this.isTemplate = isTemplate;
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

    public List<File> getFiles() {
        return files;
    }

    public void addFiles(List<File> files) {
        this.files.addAll(files);
    }

    public void updateFiles(List<File> files) {
        this.files.clear();
        this.files.addAll(files);
    }

    public void clearFiles() {
        this.files.clear();
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

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void addVouchers(List<Voucher> vouchers) {
        this.vouchers.addAll(vouchers);
    }

    public void updateVouchers(List<Voucher> vouchers) {
        this.vouchers.clear();
        this.vouchers.addAll(vouchers);
    }

    public void clearVouchers() {
        this.vouchers.clear();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public Reward clone(String name) {
        return new Reward(name, this.description, this.prizeType, this.prizeValue, this.numOfPrize, this.released,
            this.messageWinnerEN, this.messageWinnerSW, this.messageBalanceEN, this.messageBalanceSW,
            this.level, null, false);
    }

    public Boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    @Override
    public String toString() {
        return "Reward{" +
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
            ", campaignId=" + campaignId +
            '}';
    }
}
