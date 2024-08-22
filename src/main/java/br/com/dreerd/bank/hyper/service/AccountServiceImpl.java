package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Account;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.repository.AccountRepository;
import br.com.dreerd.bank.hyper.service.common.AccountService;
import br.com.dreerd.bank.hyper.service.common.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    private final PersonService personService;
    private final AccountRepository accountRepository;

    AccountServiceImpl(AccountRepository AccountRepository, PersonService personService) {
        this.accountRepository = AccountRepository;
        this.personService = personService;
    }

    @Override
    public List<AccountDTO> list() {
        return accountRepository
                .findAll()
                .stream()
                .map(Account::toDTO)
                .toList();
    }

    @Override
    public AccountDTO findById(UUID id) {
        Optional<Account> findOne = accountRepository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(
                HttpStatus.NOT_FOUND, "Account doesn't found")
        );
    }

    public AccountDTO findByNumber(Long numberAccount) {
        Optional<Account> findOne = accountRepository.findByNumber(numberAccount);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Account doesn't found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountDTO save(AccountDTO dto) {
        try {
            dto.setNumber(this.getLastNumber());
            dto.setActive(true);

            PersonDTO person = personService.findById(dto.getPerson().getId());
            dto.setPerson(person);
            Account result = accountRepository.save(dto.toEntity());
            return result.toDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountDTO edit(AccountDTO dto) throws JsonProcessingException {
        Optional<Account> foundOne = accountRepository.findById(dto.getId());

        if (!foundOne.isPresent()) {
            log.info("Account not found");
            throw new BusinessException("404", "Conctact not found", "The Account doesn't found", dto);
        }

        Account AccountDB = foundOne.get();
        AccountDB.updateFields(dto);
        return accountRepository.save(AccountDB).toDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) throws JsonProcessingException {
        if (!accountRepository.existsById(id)) {
            throw new BusinessException("404", "Conctact not found", "The Account doesn't found", id);
        }

        accountRepository.deleteById(id);
    }

    private Long getLastNumber() {
        return accountRepository.count() + 1;
    }
}
