package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_black_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TokenBlackList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_black_list_sequence_generator")
//    @SequenceGenerator(name = "token_black_list_sequence_generator", sequenceName = "token_black_list_id_sequence", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "expired_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiredDate;

    public TokenBlackList() {
    }

    public TokenBlackList(String token, LocalDateTime expiredDate) {
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

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
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
