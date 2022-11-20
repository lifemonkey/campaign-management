package campaign.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "can_approve_reward", length = 2)
    private Integer canApproveReward;

    @Column(name = "can_reject_reward", length = 2)
    private Integer canRejectReward;

    @Column(name = "campaign_management", length = 2)
    private Integer campaignManagement;

    @Column(name = "rule_management", length = 2)
    private Integer ruleManagement;

    @Column(name = "prize_management", length = 2)
    private Integer prizeManagement;

    public Role() { }

    public Role(String name) {
        this.name = name;
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

    public Integer getCanApproveReward() {
        return canApproveReward;
    }

    public void setCanApproveReward(Integer canApproveReward) {
        this.canApproveReward = canApproveReward;
    }

    public Integer getCanRejectReward() {
        return canRejectReward;
    }

    public void setCanRejectReward(Integer canRejectReward) {
        this.canRejectReward = canRejectReward;
    }

    public Integer getCampaignManagement() {
        return campaignManagement;
    }

    public void setCampaignManagement(Integer campaignManagement) {
        this.campaignManagement = campaignManagement;
    }

    public Integer getRuleManagement() {
        return ruleManagement;
    }

    public void setRuleManagement(Integer ruleManagement) {
        this.ruleManagement = ruleManagement;
    }

    public Integer getPrizeManagement() {
        return prizeManagement;
    }

    public void setPrizeManagement(Integer prizeManagement) {
        this.prizeManagement = prizeManagement;
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", canApproveReward=" + canApproveReward +
            ", canRejectReward=" + canRejectReward +
            ", campaignManagement=" + campaignManagement +
            ", ruleManagement=" + ruleManagement +
            ", prizeManagement=" + prizeManagement +
            '}';
    }
}
