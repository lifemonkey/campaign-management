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

    @Column(name = "name", unique = true, nullable = false, length = 200)
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinTable(name = "rule_transaction_type",
        joinColumns = @JoinColumn(name = "rule_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "transaction_type_id", nullable = false, referencedColumnName = "id"))
    private List<TransactionType> transactionTypes = new ArrayList<>();

    @ManyToMany(mappedBy = "ruleList", fetch = FetchType.LAZY)
    private List<Campaign> campaignList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rule")
    private List<RewardCondition> rewardConditions = new ArrayList<>();

    public Rule(String name, String description, EDuration durationType, String durationValue, Integer ruleConfiguration, Integer campaignType) {
        this.name = name;
        this.description = description;
        this.durationType = durationType;
        this.durationValue = durationValue;
        this.ruleConfiguration = ruleConfiguration;
        this.campaignType = campaignType;
    }

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

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public void addTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes.addAll(transactionTypes);
    }

    public void updateTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes.clear();
        this.transactionTypes.addAll(transactionTypes);
    }

    public Rule clearTransactionTypes() {
        this.transactionTypes.clear();
        return this;
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

    public void updateCampaignList(List<Campaign> campaignList) {
        this.campaignList.clear();
        this.campaignList.addAll(campaignList);
    }

    public Rule clearCampaignList() {
        this.campaignList.clear();
        return this;
    }

    public List<RewardCondition> getRewardConditions() {
        return rewardConditions;
    }

    public void addRewardConditions(List<RewardCondition> rewardConditionList) {
        this.rewardConditions.addAll(rewardConditionList);
    }

    public void updateRewardConditions(List<RewardCondition> rewardConditionList) {
        this.rewardConditions.clear();
        this.rewardConditions.addAll(rewardConditionList);
    }

    public Rule clearRewardConditions() {
        this.rewardConditions.clear();
        return this;
    }

    public Rule clone(String name) {
        return new Rule(name, this.description, this.durationType, this.durationValue, this.ruleConfiguration, this.campaignType);
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", durationType=" + durationType +
            ", durationValue='" + durationValue + '\'' +
            ", ruleConfiguration=" + ruleConfiguration +
            '}';
    }
}
