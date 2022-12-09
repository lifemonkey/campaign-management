package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rules")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rule_sequence_generator")
    @SequenceGenerator(name = "rule_sequence_generator", sequenceName = "rule_id_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 200)
    private String name;

    @Column(length = 4000)
    private String description;

    @Column(name = "duration_type", length = 1)
    private Integer durationType;

    @Column(name = "duration_value")
    private String durationValue;

    @Column(name = "reward_condition", length = 1)
    private Integer rewardCondition;

    @OneToOne
    @JoinColumn(name = "rule_configuration_id", referencedColumnName = "id")
    private RuleConfiguration ruleConfiguration;

    @OneToOne
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "id")
    private TransactionType transactionType;

    public Rule() {
    }

    public Rule(String name, String description) {
        this.name = name;
        this.description = description;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getRewardCondition() {
        return rewardCondition;
    }

    public void setRewardCondition(Integer rewardCondition) {
        this.rewardCondition = rewardCondition;
    }

    public RuleConfiguration getRuleConfiguration() {
        return ruleConfiguration;
    }

    public void setRuleConfiguration(RuleConfiguration ruleConfiguration) {
        this.ruleConfiguration = ruleConfiguration;
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
            ", rewardCondition=" + rewardCondition +
            ", ruleConfiguration=" + ruleConfiguration +
            '}';
    }
}
