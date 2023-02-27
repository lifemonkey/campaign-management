package campaign.service;

import campaign.domain.Account;
import campaign.domain.TargetList;
import campaign.repository.AccountRepository;
import campaign.repository.TargetListRepository;
import campaign.service.dto.TargetListDTO;
import campaign.service.mapper.TargetListMapper;
import campaign.web.rest.vm.TargetListVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TargetListService {

    private final Logger log = LoggerFactory.getLogger(TargetListService.class);

    private final TargetListRepository targetListRepository;

    private final AccountRepository accountRepository;

    private final TargetListMapper targetListMapper;

    public TargetListService(
        TargetListRepository targetListRepository,
        AccountRepository accountRepository,
        TargetListMapper targetListMapper
    ) {
        this.targetListRepository = targetListRepository;
        this.targetListMapper = targetListMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Page<TargetListDTO> getAllTargetList(Pageable pageable) {
        return targetListRepository.findAll(pageable).map(TargetListDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<TargetListDTO> searchTargetList(Pageable pageable, String search, Integer type) {
        if (search != null && type == null) {
            return targetListRepository.findAllByNameContainingIgnoreCase(search, pageable).map(TargetListDTO::new);
        } else if (search == null && type != null) {
            return targetListRepository.findAllByTargetType(type, pageable).map(TargetListDTO::new);
        } else {
            return targetListRepository.findAllByNameContainingIgnoreCaseAndTargetType(search, type, pageable).map(TargetListDTO::new);
        }
    }

    @Transactional(readOnly = true)
    public TargetListDTO getTargetListById(Long id) {
        Optional<TargetList> targetListOpt = targetListRepository.findById(id);
        if (targetListOpt.isPresent()) {
            return new TargetListDTO(targetListOpt.get());
        }

        return new TargetListDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public TargetListDTO createTargetList(TargetListVM targetListVM) {
        TargetList targetList = targetListMapper.targetListVMToTargetList(targetListVM);
        if (targetList != null) {
            targetListRepository.save(targetList);

            // find related accounts
            List<Account> accountList = accountRepository.findByIdIn(targetListVM.getAccountIds());

            if (!accountList.isEmpty()) {
                targetList.addAccountList(accountList);
                targetListRepository.save(targetList);
            }

            return targetListMapper.targetListToTargetListDTO(targetList);
        }

        return new TargetListDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public TargetListDTO updateTargetList(Long id, TargetListVM targetListVM) {
        Optional<TargetList> targetListOpt = targetListRepository.findById(id);
        if (!targetListOpt.isPresent()) return null;

        TargetList targetList = targetListMapper.updateTargetList(targetListOpt.get(), targetListVM);
        return targetListMapper.targetListToTargetListDTO(targetListRepository.saveAndFlush(targetList));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTargetList(Long id) {
        targetListRepository.deleteById(id);
    }
}
