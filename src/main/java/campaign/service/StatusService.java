package campaign.service;

import campaign.domain.*;
import campaign.repository.*;
import campaign.service.dto.StatusDTO;
import campaign.service.mapper.StatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final StatusRepository statusRepository;

    private final StatusMapper statusMapper;

    public StatusService(StatusRepository statusRepository, StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    @Transactional(readOnly = true)
    public StatusDTO getStatusById(Long statusId) {
        Optional<Status> statusOpt = statusRepository.findById(statusId);
        if (statusOpt.isPresent()) {
            return statusMapper.statusToStatusDTO(statusOpt.get());
        }

        return new StatusDTO();
    }

    @Transactional(readOnly = true)
    public Page<StatusDTO> searchStatus(Pageable pageable, String search) {
        if (search != null && !search.isEmpty()) {
            return statusRepository.findAllByNameContainingIgnoreCase(search, pageable).map(StatusDTO::new);
        }

        return statusRepository.findAll(pageable).map(StatusDTO::new);
    }
}
