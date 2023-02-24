package campaign.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransactionTypeVM {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @Size(max = 4000)
    private String description;

    private String externalId;

    private Integer status;

    private String transTypeEN;

    private String transTypeSW;

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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransTypeEN() {
        return transTypeEN;
    }

    public void setTransTypeEN(String transTypeEN) {
        this.transTypeEN = transTypeEN;
    }

    public String getTransTypeSW() {
        return transTypeSW;
    }

    public void setTransTypeSW(String transTypeSW) {
        this.transTypeSW = transTypeSW;
    }

    @Override
    public String toString() {
        return "TransactionTypeVM{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", externalId='" + externalId + '\'' +
            ", status=" + status +
            ", transTypeEN='" + transTypeEN + '\'' +
            ", transTypeSW='" + transTypeSW + '\'' +
            '}';
    }
}
