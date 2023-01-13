package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Account extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence_generator")
    @SequenceGenerator(name = "account_sequence_generator", sequenceName = "account_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", length = 22)
    private String externalId;

    @Size(max = 200)
    @Column(name = "first_name", length = 200)
    private String firstname;

    @Size(max = 200)
    @Column(name = "last_name", length = 200)
    private String lastname;

    @Size(max = 200)
    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Size(max = 200)
    @Column(name = "address", length = 4000)
    private String address;

    @Column(name = "language_id")
    private Integer languageId;

    @JsonIgnore
    @Column(name = "dob", columnDefinition = "TIMESTAMP")
    private LocalDateTime dob;

    @ManyToMany(mappedBy = "accountList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TargetList> targetLists = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public List<TargetList> getTargetLists() {
        return targetLists;
    }

    public void addTargetLists(List<TargetList> targetLists) {
        this.targetLists = targetLists;
    }

    @Override
    public String toString() {
        return "Account{" +
            "id=" + id +
            ", externalId='" + externalId + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", description='" + description + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", address='" + address + '\'' +
            ", languageId=" + languageId +
            ", dob=" + dob +
            '}';
    }
}
