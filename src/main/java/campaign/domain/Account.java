package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Account extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", length = 22)
    private String externalId;

    @Size(max = 200)
    @Column(name = "first_name", length = 200)
    private String firstname;

    @Size(max = 200)
    @Column(name = "last_name", length = 200)
    private String lastname;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        },
        mappedBy = "accountList")
    @JsonIgnore
    List<TargetList> targetLists = new ArrayList<>();;

    public Account() {
    }

    public Account(String externalId, String firstname, String lastname) {
        this.externalId = externalId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<TargetList> getTargetLists() {
        return targetLists;
    }

    public void setTargetLists(List<TargetList> targetLists) {
        this.targetLists = targetLists;
    }

    @Override
    public String toString() {
        return "Account{" +
            "id=" + id +
            ", externalId=" + externalId +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
//            ", targetLists=" + targetLists +
            '}';
    }
}
