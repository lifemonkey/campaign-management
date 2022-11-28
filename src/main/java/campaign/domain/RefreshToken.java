package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_sequence_generator")
//    @SequenceGenerator(name = "refresh_token_sequence_generator", sequenceName = "refresh_token_id_sequence", allocationSize = 1)
    @GenericGenerator(name = "refresh_token_sequence_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "refresh_token_sequence_generator"),
        }
    )
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
