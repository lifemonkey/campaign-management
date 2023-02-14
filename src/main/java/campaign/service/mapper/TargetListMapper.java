package campaign.service.mapper;

import campaign.domain.TargetList;
import campaign.service.dto.TargetListDTO;
import campaign.web.rest.vm.TargetListVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TargetListMapper {

    private final AccountMapper accountMapper;

    public TargetListMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public TargetListDTO targetListToTargetListDTO(TargetList targetList) {
        return new TargetListDTO(targetList);
    }

    public List<TargetListDTO> targetListToTargetListDTOs(List<TargetList> targetLists) {
        return targetLists.stream()
            .filter(Objects::nonNull)
            .map(this::targetListToTargetListDTO)
            .collect(Collectors.toList());
    }

    public TargetList targetListDTOToTargetList(TargetListDTO targetListDTO) {
        if (targetListDTO == null) {
            return null;
        } else {
            TargetList targetList = new TargetList();
            targetList.setId(targetListDTO.getId());
            targetList.setName(targetListDTO.getName());
            targetList.setDescription(targetListDTO.getDescription());
            targetList.setTargetType(targetListDTO.getTargetType());
            targetList.setCreatedBy(targetListDTO.getCreatedBy());
            targetList.setCreatedDate(targetListDTO.getCreatedDate());
            targetList.setLastModifiedBy(targetListDTO.getLastModifiedBy());
            targetList.setLastModifiedDate(targetListDTO.getLastModifiedDate());

            return targetList;
        }
    }

    public List<TargetList> targetListDTOToTargetLists(List<TargetListDTO> targetListDTOs) {
        return targetListDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::targetListDTOToTargetList)
            .collect(Collectors.toList());
    }

    public TargetList targetListVMToTargetList(TargetListVM targetListVM) {
        if (targetListVM == null) {
            return null;
        } else {
            TargetList targetList = new TargetList();

            if (targetListVM.getName() != null) {
                targetList.setName(targetListVM.getName());
            }
            if (targetListVM.getDescription() != null) {
                targetList.setDescription(targetListVM.getDescription());
            }
            if (targetListVM.getTargetType() != null) {
                targetList.setTargetType(targetListVM.getTargetType());
            }

            return targetList;
        }
    }

    public List<TargetList> targetListVMToTargetLists(List<TargetListVM> targetListVMs) {
        return targetListVMs.stream().map(this::targetListVMToTargetList).collect(Collectors.toList());
    }
}
