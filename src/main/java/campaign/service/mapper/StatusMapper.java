package campaign.service.mapper;

import campaign.domain.Status;
import campaign.service.dto.StatusDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatusMapper {

    public StatusMapper() {}

    public StatusDTO statusToStatusDTO(Status status) {
        return new StatusDTO(status);
    }

    public List<StatusDTO> statusToStatusDTOs(List<Status> statusList) {
        return statusList.stream()
            .filter(Objects::nonNull)
            .map(this::statusToStatusDTO)
            .collect(Collectors.toList());
    }

}
