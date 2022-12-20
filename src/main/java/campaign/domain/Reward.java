package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "message_winner_en", length = 40000)
    private String messageWinnerEN;

    @Column(name = "message_winner_sw", length = 40000)
    private String messageWinnerSW;

    @Column(name = "message_balance_en", length = 40000)
    private String messageBalanceEN;

    @Column(name = "message_balance_sw", length = 40000)
    private String messageBalanceSW;
}
