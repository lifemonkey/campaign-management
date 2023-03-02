package campaign.domain;

import campaign.service.dto.VoucherDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

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


    @Column(name = "voucher_number", length = 10)
    private Long voucherNumber;

    @NotBlank
    @Column(name = "voucher_code", length = 10, unique = true)
    private String voucherCode;


    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "is_import")
    private Boolean isImport;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    @JsonIgnore
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "expired_date")
    @JsonIgnore
    private LocalDateTime expiredDate = null;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "id")
    private Reward reward;

    public Voucher() {
    }

    public Voucher(VoucherDTO voucherCode, Reward reward) {
        if (voucherCode.getId() != null) {
            this.id = voucherCode.getId();
        }
        this.voucherNumber = voucherCode.getVoucherNumber();
        this.voucherCode = voucherCode.getVoucherCode();
        this.description = voucherCode.getDescription();
        this.isImport = voucherCode.getImport();
        this.startDate = voucherCode.getStartDate();
        this.expiredDate = voucherCode.getExpiredDate();
        this.reward = reward;
    }

    public Voucher(Long voucherNumber, String voucherCode, String description, Boolean isImport, LocalDateTime startDate, LocalDateTime expiredDate, Reward reward) {
        this.voucherNumber = voucherNumber;
        this.voucherCode = voucherCode;
        this.description = description;
        this.isImport = isImport;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
        this.reward = reward;
    }

    public Voucher(Long voucherNumber, String voucherCode, String description, Boolean isImport, LocalDateTime startDate, LocalDateTime expiredDate) {
        this.voucherNumber = voucherNumber;
        this.voucherCode = voucherCode;
        this.description = description;
        this.isImport = isImport;
        this.startDate = startDate;
        this.expiredDate = expiredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(Long voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getImport() {
        return isImport;
    }

    public void setImport(Boolean anImport) {
        isImport = anImport;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
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

    public Voucher clone() {
        return new Voucher(this.voucherNumber, this.voucherCode, this.description, false, this.startDate, this.expiredDate);
    }

    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + id +
            ", voucherCode='" + voucherCode + '\'' +
            ", voucherCode='" + voucherCode + '\'' +
            ", isImport=" + isImport +
            ", startDate=" + startDate +
            ", expiredDate=" + expiredDate +
            '}';
    }
}
