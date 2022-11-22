package campaign.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "target_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TargetList extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @NotBlank
    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "target_type", length = 1)
    private Integer targetType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_target_list",
        joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "target_list_id", referencedColumnName = "id"))
    private List<Campaign> campaignList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "account_target_list",
        joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "target_list_id", referencedColumnName = "id"))
    private List<Account> accountList;

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
            ", campaignList=" + campaignList +
            ", accountList=" + accountList +
            '}';
    }
}
