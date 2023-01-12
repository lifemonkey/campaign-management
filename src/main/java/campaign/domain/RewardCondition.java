package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reward_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RewardCondition extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_condition_sequence_generator")
    @SequenceGenerator(name = "reward_condition_sequence_generator", sequenceName = "reward_condition_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_min")
    private Float amountMin;

    @Column(name = "amount_max")
    private Float amountMax;

    @Column(name = "times_min")
    private Integer timesMin;

    @Column(name = "times_max")
    private Integer timesMax;

    @Column(name = "number_codes")
    private Integer numberCodes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private Rule rule;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Reward reward;

    public RewardCondition() {
    }

    public RewardCondition(Float amountMin, Float amountMax, Integer timesMin, Integer timesMax, Integer numberCodes) {
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.timesMin = timesMin;
        this.timesMax = timesMax;
        this.numberCodes = numberCodes;
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

    public RewardCondition removeRule() {
        this.rule = null;
        return this;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "RewardCondition{" +
            "id=" + id +
            ", amountMin=" + amountMin +
            ", amountMax=" + amountMax +
            ", timesMin=" + timesMin +
            ", timesMax=" + timesMax +
            ", numberCodes=" + numberCodes +
            '}';
    }
}
