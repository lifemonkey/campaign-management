package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;

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

    @OneToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;

    @OneToOne
    @JoinColumn(name = "reward_id")
    private Reward reward;
}
