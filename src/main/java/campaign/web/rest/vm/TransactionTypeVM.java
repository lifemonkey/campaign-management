package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionTypeVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    public TransactionTypeVM() {
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

    @Override
    public String toString() {
        return "TransactionTypeVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
