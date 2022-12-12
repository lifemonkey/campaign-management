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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence_generator")
    @SequenceGenerator(name = "role_sequence_generator", sequenceName = "role_id_sequence", initialValue = 100, allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ERole name;

    @Column(length = 4000)
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

    public Role(ERole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
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
