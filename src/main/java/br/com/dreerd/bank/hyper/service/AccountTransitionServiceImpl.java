package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.AccountTransation;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.model.AccountTransationDTO;
import br.com.dreerd.bank.hyper.model.BankTransitionDTO;
import br.com.dreerd.bank.hyper.repository.AccountTransationRepository;
import br.com.dreerd.bank.hyper.service.common.AccountTransitionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AccountTransitionServiceImpl implements AccountTransitionService {

    private final AccountTransationRepository repository;
    private final AccountServiceImpl accountService;
    private final static String MSG_NOT_FUNDS = "Hello %s, the requested operation could not be completed at this time due to insufficient funds in your account. We suggest you check your balance and try again. If you have any questions or need assistance, please contact our customer service at +55 11 5555-5555. Our team is available to help you. Thank you for your understanding.";

    AccountTransitionServiceImpl(AccountTransationRepository repository, AccountServiceImpl accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public List<AccountTransationDTO> list() {
        return repository
                .findAll()
                .stream()
                .map(AccountTransation::toDTO)
                .toList();
    }

    @Override
    public AccountTransationDTO findById(UUID id) {
        Optional<AccountTransation> findOne = repository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(
                HttpStatus.NOT_FOUND, "Account transation doesn't found")
        );
    }

    @Override
    public List<AccountTransationDTO> findByAccountId(UUID accountId) {
        List<AccountTransation> resultList = repository.findAllByAccountId(accountId);
        return resultList
                .stream()
                .map(AccountTransation::toDTO)
                .toList();
    }

    @Override
    public List<AccountTransationDTO> findByAccountIdAndDateTransation(UUID accountId, LocalDateTime startDate, LocalDateTime endDate) {
        List<AccountTransation> resultList = repository.findAllByAccountIdAndCreatedAtBetween(accountId, startDate, endDate);
        return resultList
                .stream()
                .map(AccountTransation::toDTO)
                .toList();
    }

    @Override
    public List<AccountTransationDTO> findByAccountIdAndTransationType(UUID accountId, TransitionType transationType) {
        List<AccountTransation> resultList = repository.findAllByAccountIdAndTransitionType(accountId, transationType);
        return resultList
                .stream()
                .map(AccountTransation::toDTO)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccountTransationDTO save(AccountTransationDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(e);
            throw new BusinessException("Something went wrong while saving the account transation.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdrawal(BankTransitionDTO bankTransition) throws JsonProcessingException {
        try {
            AccountDTO sourceAccount = accountService.findById(bankTransition.getOriginAccountId());

            if (!checkFundsTheSourceAccount(bankTransition.getOriginAccountId(), bankTransition.getAmount())) {
                throw new BusinessException(HttpStatus.OK, String.format(MSG_NOT_FUNDS, sourceAccount.getPerson().getName()));
            }

            AccountTransation accountTransation = createAccountTransaction(sourceAccount, bankTransition.getAmount(), TransitionType.WITHDRAWAL, true);

            sourceAccount.setBalance(sourceAccount.getBalance().add(accountTransation.getAmount()));
            accountService.edit(sourceAccount);

            save(accountTransation.toDTO());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deposit(BankTransitionDTO bankTransition) {
        try {
            AccountDTO destinationAccount = accountService.findById(bankTransition.getDestinationAccountId());
            AccountTransation accountTransation = createAccountTransaction(destinationAccount, bankTransition.getAmount(), TransitionType.DEPOSIT, false);

            destinationAccount.setBalance(destinationAccount.getBalance().add(accountTransation.getAmount()));
            accountService.edit(destinationAccount);

            save(accountTransation.toDTO());
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void internalTransfer(BankTransitionDTO bankTransition) throws JsonProcessingException {
        try {
            AccountDTO sourceAccount = accountService.findById(bankTransition.getOriginAccountId());
            AccountDTO destinationAccount = accountService.findById(bankTransition.getDestinationAccountId());

            if (!checkFundsTheSourceAccount(bankTransition.getOriginAccountId(), bankTransition.getAmount())) {
                throw new BusinessException(HttpStatus.OK, String.format(MSG_NOT_FUNDS, sourceAccount.getPerson().getName()));
            }

            AccountTransation sourceAccountTransation = createAccountTransaction(sourceAccount, bankTransition.getAmount(), TransitionType.INTERNAL_TRANSFER, true);
            sourceAccount.setBalance(sourceAccountTransation.getBalance());
            accountService.edit(sourceAccount);
            save(sourceAccountTransation.toDTO());

            AccountTransation destinationAccountTransation = createAccountTransaction(destinationAccount, bankTransition.getAmount(), TransitionType.INTERNAL_TRANSFER, false);
            destinationAccount.setBalance(destinationAccountTransation.getBalance());
            accountService.edit(destinationAccount);
            save(destinationAccountTransation.toDTO());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AccountTransationDTO edit(AccountTransationDTO dto) throws JsonProcessingException {
        throw new NotImplementedException();
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        throw new NotImplementedException();
    }

    private boolean checkFundsTheSourceAccount(UUID sourceAccountId, BigDecimal amount) {
        AccountDTO sourceAccount = this.accountService.findById(sourceAccountId);
        return sourceAccount.getBalance().compareTo(amount) > 0;
    }

    private AccountTransation createAccountTransaction(AccountDTO account, BigDecimal amount, TransitionType transitionType, boolean isSource) {
        AccountTransation accountTransaction = new AccountTransation();
        accountTransaction.setAccount(account.toEntity());
        accountTransaction.setAmount(amount);
        accountTransaction.setTransitionType(transitionType);
        accountTransaction.setPreviousBalance(account.getBalance());

        if (isSource) {
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }

        accountTransaction.setBalance(account.getBalance());
        return accountTransaction;
    }
}