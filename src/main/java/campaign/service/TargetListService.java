package campaign.service;

import campaign.repository.TargetListRepository;
import campaign.service.dto.TargetListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TargetListService {

    private final Logger log = LoggerFactory.getLogger(TargetListService.class);

    private final TargetListRepository targetListRepository;

    public TargetListService(TargetListRepository targetListRepository) {
        this.targetListRepository = targetListRepository;
    }

    @Transactional(readOnly = true)
    public Page<TargetListDTO> getAllTargetList(Pageable pageable) {
        return targetListRepository.findAll(pageable).map(TargetListDTO::new);
    }

}
