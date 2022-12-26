package campaign.service;

import campaign.domain.GeneratedTime;
import campaign.repository.GeneratedTimeRepository;
import campaign.service.dto.GeneratedTimeDTO;
import campaign.service.mapper.GeneratedTimeMapper;
import campaign.web.rest.vm.GeneratedTimeVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GeneratedTimeService {

    private final Logger log = LoggerFactory.getLogger(GeneratedTimeService.class);

    private final GeneratedTimeRepository generatedTimeRepository;

    private final GeneratedTimeMapper generatedTimeMapper;

    public GeneratedTimeService(GeneratedTimeRepository generatedTimeRepository, GeneratedTimeMapper generatedTimeMapper) {
        this.generatedTimeRepository = generatedTimeRepository;
        this.generatedTimeMapper = generatedTimeMapper;
    }

    @Transactional(readOnly = true)
    public GeneratedTimeDTO getGeneratedTimeById(Long id) {
        Optional<GeneratedTime> generatedTimeOpt = generatedTimeRepository.findById(id);
        if (generatedTimeOpt.isPresent()) {
            return generatedTimeMapper.generatedTimeToGeneratedTimeDTO(generatedTimeOpt.get());
        }

        return new GeneratedTimeDTO();
    }

    @Transactional(readOnly = true)
    public Page<GeneratedTimeDTO> getAllGeneratedTimes(Pageable pageable) {
        return generatedTimeRepository.findAll(pageable).map(GeneratedTimeDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public GeneratedTimeDTO createGeneratedTime(GeneratedTimeVM generatedTimeVM) {
        GeneratedTime generatedTime = generatedTimeMapper.generatedTimeVMToGeneratedTime(generatedTimeVM);

        if (generatedTime != null) {
            return generatedTimeMapper.generatedTimeToGeneratedTimeDTO(generatedTimeRepository.save(generatedTime));
        }

        return new GeneratedTimeDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public GeneratedTimeDTO cloneGeneratedTime(Long id) {
        Optional<GeneratedTime> generatedTimeOpt = generatedTimeRepository.findById(id);
        GeneratedTime toBerInserted = new GeneratedTime();

        if (generatedTimeOpt.isPresent()) {
            toBerInserted.setId(generatedTimeOpt.get().getId());
            toBerInserted.setStartTime(generatedTimeOpt.get().getStartTime());
            toBerInserted.setEndTime(generatedTimeOpt.get().getEndTime());
            toBerInserted.setCampaign(generatedTimeOpt.get().getCampaign());
        }

        return generatedTimeMapper.generatedTimeToGeneratedTimeDTO(generatedTimeRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public GeneratedTimeDTO updateGeneratedTime(Long id, GeneratedTimeVM generatedTimeVM) {
        GeneratedTime generatedTime = generatedTimeMapper.generatedTimeVMToGeneratedTime(generatedTimeVM);
        generatedTime.setId(id);

        generatedTimeRepository.save(generatedTime);
        return generatedTimeMapper.generatedTimeToGeneratedTimeDTO(generatedTime);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteGeneratedTime(Long id) {
        generatedTimeRepository.deleteById(id);
    }
}
