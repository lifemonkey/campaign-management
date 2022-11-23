package campaign.web.rest.vm;

import java.util.List;

public class TargetListVM {

    private String name;

    private String description;

    private Integer targetType;

    private List<Long> accountList;

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

    public List<Long> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Long> accountList) {
        this.accountList = accountList;
    }
}
