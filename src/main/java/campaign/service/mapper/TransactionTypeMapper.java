package campaign.service.mapper;

import campaign.domain.Rule;
import campaign.domain.TransactionType;
import campaign.service.dto.RuleDTO;
import campaign.service.dto.TransactionTypeDTO;
import campaign.web.rest.vm.RuleVM;
import campaign.web.rest.vm.TransactionTypeVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionTypeMapper {


    public TransactionTypeMapper() {
    }

    public TransactionTypeDTO transactionTypeToTransactionTypeDTO(TransactionType transactionType) {
        return new TransactionTypeDTO(transactionType);
    }

    public List<TransactionTypeDTO> transactionTypeToTransactionTypeDTOs(List<TransactionType> transactionTypeList) {
        return transactionTypeList.stream()
            .filter(Objects::nonNull)
            .map(this::transactionTypeToTransactionTypeDTO)
            .collect(Collectors.toList());
    }

    public TransactionType transactionTypeDTOToTransactionType(TransactionTypeDTO transactionTypeDTO) {
        if (transactionTypeDTO == null) {
            return null;
        } else {
            TransactionType transactionType = new TransactionType();
            transactionType.setId(transactionTypeDTO.getId());
            transactionType.setName(transactionTypeDTO.getName());
            transactionType.setDescription(transactionTypeDTO.getDescription());

            transactionType.setCreatedBy(transactionTypeDTO.getCreatedBy());
            transactionType.setCreatedDate(transactionTypeDTO.getCreatedDate());
            transactionType.setLastModifiedBy(transactionTypeDTO.getLastModifiedBy());
            transactionType.setLastModifiedDate(transactionTypeDTO.getLastModifiedDate());

            return transactionType;
        }
    }

    public List<TransactionType> transactionTypeDTOToTransactionTypes(List<TransactionTypeDTO> transactionTypeList) {
        return transactionTypeList.stream()
            .filter(Objects::nonNull)
            .map(this::transactionTypeDTOToTransactionType)
            .collect(Collectors.toList());
    }

    public TransactionType transactionTypeVMToTransactionType(TransactionTypeVM transactionTypeVM) {
        if (transactionTypeVM == null || transactionTypeVM.getName() == null) {
            return null;
        } else {
            TransactionType transactionType = new TransactionType(transactionTypeVM.getName());

            if (transactionTypeVM.getDescription() != null) {
                transactionType.setDescription(transactionTypeVM.getDescription());
            }

            return transactionType;
        }
    }
}
