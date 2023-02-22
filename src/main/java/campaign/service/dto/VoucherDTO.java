package campaign.service.dto;

import campaign.domain.Voucher;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class VoucherDTO {

    private Long id;

    @NotBlank
    private String voucherCode;

    private String createdBy;

    private LocalDateTime startDate;

    private LocalDateTime expiredDate;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    public VoucherDTO() {
    }

    public VoucherDTO(Voucher voucher) {
        this.id = voucher.getId();
        this.voucherCode = voucher.getVoucherCode();
        this.startDate = voucher.getStartDate();
        this.expiredDate = voucher.getExpiredDate();
        this.createdBy = voucher.getCreatedBy();
        this.createdDate = voucher.getCreatedDate();
        if (this.lastModifiedDate != null && this.createdDate != null && this.lastModifiedDate.isAfter(this.createdDate)) {
            this.lastModifiedBy = voucher.getLastModifiedBy();
            this.lastModifiedDate = voucher.getLastModifiedDate();
        } else {
            this.lastModifiedBy = null;
            this.lastModifiedDate = null;
        }
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "VoucherDTO{" +
            "id=" + id +
            ", voucherCode='" + voucherCode + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", startDate=" + startDate +
            ", expiredDate=" + expiredDate +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
