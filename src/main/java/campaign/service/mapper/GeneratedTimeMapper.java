package campaign.service.mapper;

import campaign.domain.GeneratedTime;
import campaign.domain.Reward;
import campaign.service.dto.GeneratedTimeDTO;
import campaign.service.dto.RewardDTO;
import campaign.web.rest.vm.GeneratedTimeVM;
import campaign.web.rest.vm.RewardVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GeneratedTimeMapper {

    public GeneratedTimeMapper() {
    }

    public GeneratedTimeDTO generatedTimeToGeneratedTimeDTO(GeneratedTime generatedTime) {
        return new GeneratedTimeDTO(generatedTime);
    }

    public List<GeneratedTimeDTO> generatedTimeToGeneratedTimeDTOs(List<GeneratedTime> generatedTimes) {
        return generatedTimes.stream()
            .filter(Objects::nonNull)
            .map(this::generatedTimeToGeneratedTimeDTO)
            .collect(Collectors.toList());
    }

    public GeneratedTime generatedTimeDTOToGeneratedTime(GeneratedTimeDTO generatedTimeDTO) {
        if (generatedTimeDTO == null) {
            return null;
        } else {
            GeneratedTime generatedTime = new GeneratedTime();
            generatedTime.setId(generatedTimeDTO.getId());
            generatedTime.setStartTime(generatedTimeDTO.getStartTime());
            generatedTime.setEndTime(generatedTimeDTO.getEndTime());

            return generatedTime;
        }
    }

    public List<GeneratedTime> generatedTimeDTOToGeneratedTimes(List<GeneratedTimeDTO> generatedTimeDTOList) {
        return generatedTimeDTOList.stream()
            .filter(Objects::nonNull)
            .map(this::generatedTimeDTOToGeneratedTime)
            .collect(Collectors.toList());
    }

    public GeneratedTime generatedTimeVMToGeneratedTime(GeneratedTimeVM generatedTimeVM) {
        if (generatedTimeVM == null) {
            return null;
        } else {
            GeneratedTime generatedTime = new GeneratedTime();
            generatedTime.setId(generatedTimeVM.getId());
            generatedTime.setStartTime(generatedTimeVM.getStartTime());
            generatedTime.setEndTime(generatedTimeVM.getEndTime());

            return generatedTime;
        }
    }

    public List<GeneratedTime> generatedTimeVMToGeneratedTimes(List<GeneratedTimeVM> generatedTimeVMs) {
        return generatedTimeVMs.stream()
            .filter(Objects::nonNull)
            .map(this::generatedTimeVMToGeneratedTime)
            .collect(Collectors.toList());
    }

    public GeneratedTime updateGeneratedTime(GeneratedTime generatedTimeInDb, GeneratedTimeVM generatedTimeVM) {
        if (generatedTimeVM == null || generatedTimeInDb == null) {
            return null;
        } else {
            GeneratedTime generatedTime = generatedTimeInDb;

            if (generatedTimeVM.getStartTime() != null) {
                generatedTime.setStartTime(generatedTimeVM.getStartTime());
            }

            if (generatedTimeVM.getEndTime() != null) {
                generatedTime.setEndTime(generatedTimeVM.getEndTime());
            }

            return generatedTime;
        }
    }
}
