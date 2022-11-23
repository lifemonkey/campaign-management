package campaign.service;

import campaign.domain.Account;
import campaign.domain.TargetList;
import campaign.repository.AccountRepository;
import campaign.repository.TargetListRepository;
import campaign.service.dto.AccountDTO;
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

@Service
@Transactional
public class TargetListService {

    private final Logger log = LoggerFactory.getLogger(TargetListService.class);

    private final TargetListRepository targetListRepository;

    private final AccountRepository accountRepository;

    private final TargetListMapper targetListMapper;

    public TargetListService(TargetListRepository targetListRepository, AccountRepository accountRepository, TargetListMapper targetListMapper) {
        this.targetListRepository = targetListRepository;
        this.targetListMapper = targetListMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Page<TargetListDTO> getAllTargetList(Pageable pageable) {
        return targetListRepository.findAll(pageable).map(TargetListDTO::new);
    }

    @Transactional
    public TargetListDTO createTargetList(TargetListVM targetListVM) {
        TargetList targetList = targetListMapper.targetListVMToTargetList(targetListVM);
        if (targetList != null) {
            List<Account> accountList = accountRepository.findByIdIn(targetListVM.getAccountList());
            if (accountList != null) {
                targetList.setAccountList(accountList);
                TargetList savedTargetList = targetListRepository.save(targetList);
                return targetListMapper.targetListToTargetListDTO(savedTargetList);
            }
        }

        return new TargetListDTO();
    }

    @Transactional
    public TargetListDTO updateTargetList(Long id, TargetListVM targetListVM) {
        TargetList targetList = targetListMapper.targetListVMToTargetList(targetListVM);
        targetList.setId(id);
        TargetList updatedTargetList = targetListRepository.save(targetList);
        return targetListMapper.targetListToTargetListDTO(updatedTargetList);
    }

    @Transactional
    public void deleteTargetList(Long id) {
        targetListRepository.deleteById(id);
    }
}
