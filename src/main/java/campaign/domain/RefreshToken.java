package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RefreshToken extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_sequence_generator")
//    @SequenceGenerator(name = "refresh_token_sequence_generator", sequenceName = "refresh_token_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @JsonIgnore
    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expiredDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private User user;

    public RefreshToken() {
    }

    public RefreshToken(String token, LocalDateTime expiredDate, User user) {
        this.token = token;
        this.expiredDate = expiredDate;
        this.user = user;
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

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiryDate) {
        this.expiredDate = expiryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", expiryDate=" + expiredDate +
            ", user=" + user +
            '}';
    }
}
