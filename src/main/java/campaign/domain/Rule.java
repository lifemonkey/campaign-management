package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rule_sequence_generator")
    @SequenceGenerator(name = "rule_sequence_generator", sequenceName = "rule_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String name;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration_type", length = 50)
    private EDuration durationType;

    @Column(name = "duration_value")
    private String durationValue;

    @Column(name = "rule_configuration")
    private Integer ruleConfiguration;

    @Column(name = "campaign_type")
    private Integer campaignType;

    @OneToOne
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType transactionType;

    @ManyToMany(mappedBy = "ruleList", fetch = FetchType.LAZY)
    private List<Campaign> campaignList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rule")
    private List<RewardCondition> rewardConditions = new ArrayList<>();

    public Rule() {
        this.name = "Default rule";
    }

    public Rule(String name) {
        this.name = name;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
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

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void addCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    public void clearCampaignList() {
        this.campaignList = new ArrayList<>();
    }

    public List<RewardCondition> getRewardConditions() {
        return rewardConditions;
    }

    public void addRewardConditions(List<RewardCondition> rewardConditionList) {
        this.rewardConditions.addAll(rewardConditionList);
    }

    public void clearRewardConditions() {
        this.rewardConditions.clear();
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", durationType=" + durationType +
            ", durationValue='" + durationValue + '\'' +
            ", transactionType=" + transactionType +
            ", ruleConfiguration=" + ruleConfiguration +
            '}';
    }
}
