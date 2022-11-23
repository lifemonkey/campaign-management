package campaign.service;

import campaign.domain.TargetList;
import campaign.repository.TargetListRepository;
import campaign.service.dto.TargetListDTO;
import campaign.service.mapper.TargetListMapper;
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

    private final TargetListMapper targetListMapper;

    public TargetListService(TargetListRepository targetListRepository, TargetListMapper targetListMapper) {
        this.targetListRepository = targetListRepository;
        this.targetListMapper = targetListMapper;
    }

    @Transactional(readOnly = true)
    public Page<TargetListDTO> getAllTargetList(Pageable pageable) {
        return targetListRepository.findAll(pageable).map(TargetListDTO::new);
    }

    @Transactional
    public TargetListDTO createTargetList(TargetListDTO targetListDTO) {
        TargetList targetList = targetListMapper.targetListDTOToTargetList(targetListDTO);
        TargetList savedTargetList = targetListRepository.save(targetList);
        return targetListMapper.targetListToTargetListDTO(savedTargetList);
    }
}
