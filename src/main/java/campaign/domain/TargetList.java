package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "target_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TargetList extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "target_type", length = 1)
    private Integer targetType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "targetLists")
    private List<Campaign> campaignList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "target_list_account",
        joinColumns = @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "target_list_id", nullable = false, referencedColumnName = "id"))
    private List<Account> accountList = new ArrayList<>();

    public TargetList() {
    }

    public TargetList(String name, String description, Integer targetType) {
        this.name = name;
        this.description = description;
        this.targetType = targetType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return "TargetList{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", targetType=" + targetType +
//            ", campaignList=" + campaignList +
//            ", accountList=" + accountList +
            '}';
    }
}
