package campaign.service;

import campaign.config.Constants;
import campaign.domain.TransactionType;
import campaign.repository.TransactionTypeRepository;
import campaign.service.dto.TransactionTypeDTO;
import campaign.service.mapper.TransactionTypeMapper;
import campaign.web.rest.vm.TransactionTypeVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TransactionTypeService {

    private final Logger log = LoggerFactory.getLogger(TransactionTypeService.class);

    private final TransactionTypeRepository transactionTypeRepository;

    private final TransactionTypeMapper transactionTypeMapper;


    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository,
                                  TransactionTypeMapper transactionTypeMapper
    ) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionTypeMapper = transactionTypeMapper;
    }

    @Transactional(readOnly = true)
    public TransactionTypeDTO getTransactionById(Long id) {
        Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(id);
        if (transactionTypeOpt.isPresent()) {
            return transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionTypeOpt.get());
        }

        return new TransactionTypeDTO();
    }

    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> getAllTransactionTypes(Pageable pageable) {
        return transactionTypeRepository.findAll(pageable).map(TransactionTypeDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<TransactionTypeDTO> searchTransactionTypes(Pageable pageable, String search) {
        return transactionTypeRepository.findAllByNameContainingIgnoreCase(search, pageable).map(TransactionTypeDTO::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionTypeDTO createTransactionType(TransactionTypeVM transactionTypeVM) {
        TransactionType transactionType = transactionTypeMapper.transactionTypeVMToTransactionType(transactionTypeVM);

        if (transactionType != null) {
            transactionTypeRepository.save(transactionType);

            return transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionType);
        }

        return new TransactionTypeDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionTypeDTO cloneTransactionType(Long id) {
        Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(id);
        TransactionType toBerInserted = new TransactionType();

        if (transactionTypeOpt.isPresent()) {
            toBerInserted.setName(transactionTypeOpt.get().getName() + Constants.CLONE_POSTFIX);
            toBerInserted.setDescription(transactionTypeOpt.get().getDescription());
        }

        return transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionTypeRepository.save(toBerInserted));
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionTypeDTO updateTransactionType(Long id, TransactionTypeVM transactionTypeVM) {
        TransactionType transactionType = transactionTypeMapper.transactionTypeVMToTransactionType(transactionTypeVM);
        transactionType.setId(id);
        transactionTypeRepository.save(transactionType);
        return transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTransactionType(Long id) {
        Optional<TransactionType> transactionTypeOpt = transactionTypeRepository.findById(id);
        if (transactionTypeOpt.isPresent()) {
            transactionTypeRepository.delete(transactionTypeOpt.get());
        }
    }
}
