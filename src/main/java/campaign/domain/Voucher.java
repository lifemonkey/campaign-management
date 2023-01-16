package campaign.domain;

import campaign.service.dto.VoucherDTO;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voucher extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_sequence_generator")
    @SequenceGenerator(name = "voucher_sequence_generator", sequenceName = "voucher_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "voucher_code", length = 200, unique = true)
    private String voucherCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    private Reward reward;

    public Voucher() {
    }

    public Voucher(VoucherDTO voucherCode, Reward reward) {
        if (voucherCode.getId() != null) {
            this.id = voucherCode.getId();
        }
        this.voucherCode = voucherCode.getVoucherCode();
        this.reward = reward;
    }

    public Voucher(String voucherCode, Reward reward) {
        this.voucherCode = voucherCode;
        this.reward = reward;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Voucher removeReward() {
        this.reward = null;
        return this;
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + id +
            ", voucherCode='" + voucherCode + '\'' +
            ", reward=" + reward +
            '}';
    }
}
