package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ActionCampaignVM {

    @Size(min = 1, max = 255)
    private Long id;

    @NotNull
    private String action;

    @NotNull
    @Size(max = 500)
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]+", message = "The reason contains special characters")
    private String actionReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionReason() {
        return actionReason;
    }

    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }

    @Override
    public String toString() {
        return "ActionCampaignVM{" +
            "id=" + id +
            ", action='" + action + '\'' +
            ", actionReason='" + actionReason + '\'' +
            '}';
    }
}
