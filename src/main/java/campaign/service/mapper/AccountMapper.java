package campaign.service.mapper;

import campaign.domain.Account;
import campaign.service.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountMapper {

    public AccountDTO accountToAccountDTO(Account account) {
        return new AccountDTO(account);
    }

    public List<AccountDTO> accountToAccountDTOs(List<Account> accountList) {
        return accountList.stream()
            .filter(Objects::nonNull)
            .map(this::accountToAccountDTO)
            .collect(Collectors.toList());
    }

    public Account accountDTOToAccount(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        } else {
            Account account = new Account();
            account.setId(accountDTO.getId());
            account.setExternalId(accountDTO.getExternalId());
            account.setFirstname(accountDTO.getFirstName());
            account.setLastname(accountDTO.getLastName());
            account.setCreatedBy(accountDTO.getCreatedBy());
            account.setCreatedDate(accountDTO.getCreatedDate());
            account.setLastModifiedBy(accountDTO.getLastModifiedBy());
            account.setLastModifiedDate(accountDTO.getLastModifiedDate());

            return account;
        }
    }

    public List<Account> accountDTOToAccounts(List<AccountDTO> accountDTOs) {
        return accountDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::accountDTOToAccount)
            .collect(Collectors.toList());
    }
}
