package campaign.service.dto;

import campaign.domain.Status;

import javax.validation.constraints.NotBlank;

public class StatusDTO {

    private Long id;

    @NotBlank
    private String name;

    public StatusDTO() {
    }

    public StatusDTO(String name) {
        this.name = name;
    }

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.name = status.getName();
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

    @Override
    public String toString() {
        return "StatusDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
