package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "token_black_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TokenBlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotBlank
    @Column(name = "token", length = 255, unique = true, nullable = false)
    private String token;

    @Column(name = "expired_date")
    private Instant expiredDate;

    public TokenBlackList() {
    }

    public TokenBlackList(String token, Instant expiredDate) {
        this.token = token;
        this.expiredDate = expiredDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public String toString() {
        return "TokenBlackList{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", expiredDate=" + expiredDate +
            '}';
    }
}
