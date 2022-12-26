package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class TargetListVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private Integer targetType;

    private List<Long> accountIds = new ArrayList<>();

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

    public List<Long> getAccountIds() {
        return accountIds;
    }

    public void addAccountIds(List<Long> accountIds) {
        this.accountIds.addAll(accountIds);
    }

    @Override
    public String toString() {
        return "TargetListVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", targetType=" + targetType +
            ", accountIds=" + accountIds +
            '}';
    }
}
