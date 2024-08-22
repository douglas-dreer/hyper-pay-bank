package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.AccountTransation;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.model.AccountTransationDTO;
import br.com.dreerd.bank.hyper.model.BankTransitionDTO;
import br.com.dreerd.bank.hyper.repository.AccountTransationRepository;
import br.com.dreerd.bank.hyper.service.common.BaseServiceTest;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountTransitionServiceImplTest extends GeneratorInformation implements BaseServiceTest {
    @InjectMocks
    private AccountTransitionServiceImpl service;

    @Mock
    private AccountTransationRepository repository;

    @Mock
    private AccountServiceImpl accountService;

    private List<AccountTransation> entityList = new ArrayList<>();
    private AccountTransation entity;

    private AccountDTO account;

    private BankTransitionDTO bankTransition;

    private final UUID ID = UUID.randomUUID();
    private final LocalDateTime START_DATE = LocalDateTime.now();
    private final LocalDateTime END_DATE = LocalDateTime.now().plusDays(7);
    private final TransitionType TRANSITION_TYPE_DEPOSIT = TransitionType.DEPOSIT;

    @BeforeEach
    void setUp() {
        entity = createAccountTransition(AccountTransation.class);
        entityList = Collections.singletonList(entity);
        account = createAccount(AccountDTO.class);
        bankTransition = createBankTransition();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(entityList);

        List<AccountTransationDTO> resultList = service.list();

        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.of(entity));

        AccountTransationDTO resultList = service.findById(entity.getId());
        assertNotNull(resultList);
        verify(repository, times(1)).findById(any());

    }

    @Override
    public void mustReturnNullWhenFindById() {
       throw new NotImplementedException();
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> service.findById(entity.getId()));
        verify(repository, times(1)).findById(any());

    }

    @Test
    public void mustReturnSuccessFindByAccountId(){
        when(repository.findAllByAccountId(any())).thenReturn(entityList);

        List<AccountTransationDTO> resultList = service.findByAccountId(entity.getAccount().getId());

        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        verify(repository, times(1)).findAllByAccountId(any());
    }

    @Test
    public void mustReturnSuccessWhenFindByAccountIdAndDateTransation(){
        when(repository.findAllByAccountIdAndCreatedAtBetween(any(), any(), any())).thenReturn(entityList);

        List<AccountTransationDTO> resultList = service.findByAccountIdAndDateTransation(ID, START_DATE, END_DATE);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        verify(repository, times(1)).findAllByAccountIdAndCreatedAtBetween(any(), any(), any());
    }

    @Test
    public void mustReturnSuccessWhenFindByAccountIdAndByTransationType(){
        when(repository.findAllByAccountIdAndTransitionType(any(), any())).thenReturn(entityList);

        List<AccountTransationDTO> resultList = service.findByAccountIdAndTransationType(ID, TRANSITION_TYPE_DEPOSIT);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        verify(repository, times(1)).findAllByAccountIdAndTransitionType(any(), any());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(entity);

        AccountTransationDTO result = service.save(entity.toDTO());
        assertNotNull(result);
        verify(repository, times(1)).save(any());
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenSave() {
        when(repository.save(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> service.save(entity.toDTO()));
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnSuccessWhenWithdrawal() throws JsonProcessingException {
        when(accountService.findById(any())).thenReturn(account);
        when(accountService.edit(any())).thenReturn(account);
        when(repository.save(any())).thenReturn(entity);

        service.withdrawal(bankTransition);
        verify(accountService, times(2)).findById(any());
        verify(accountService, times(1)).edit(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnBusinessExceptionNotFoudsWhenWithdrawal() throws JsonProcessingException {
        bankTransition.setAmount(new BigDecimal(1000));
        account.setBalance(new BigDecimal(100));

        when(accountService.findById(any())).thenReturn(account);
        assertThrows(BusinessException.class, () -> service.withdrawal(bankTransition));
        verify(accountService, times(2)).findById(any());
    }

    @Test
    public void mustReturnSuccessWhenDeposit() throws JsonProcessingException {
        when(accountService.findById(any())).thenReturn(account);
        when(accountService.edit(any())).thenReturn(account);
        when(repository.save(any())).thenReturn(entity);

        service.deposit(bankTransition);
        verify(accountService, times(1)).findById(any());
        verify(accountService, times(1)).edit(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void mustReturnBusinessExceptionWhenDeposit() throws JsonProcessingException {
        doThrow(BusinessException.class).when(accountService).findById(any());
        assertThrows(BusinessException.class, () -> service.deposit(bankTransition));
        verify(accountService, times(1)).findById(any());
    }

    @Test
    public void mustReturnSuccessWhenInternalTransfer() throws JsonProcessingException {
        when(accountService.findById(any())).thenReturn(account);
        when(accountService.edit(any())).thenReturn(account);
        when(repository.save(any())).thenReturn(entity);

        service.internalTransfer(bankTransition);
        verify(accountService, times(3)).findById(any());
        verify(accountService, times(2)).edit(any());
        verify(repository, times(2)).save(any());
    }

    @Test
    public void mustReturnBusinessExceptionNotFundsWhenInternalTransfer() throws JsonProcessingException {
        bankTransition.setAmount(new BigDecimal(1000));
        account.setBalance(new BigDecimal(100));

        when(accountService.findById(any())).thenReturn(account);
        when(accountService.edit(any())).thenReturn(account);
        when(repository.save(any())).thenReturn(entity);

        assertThrows(BusinessException.class, () -> service.internalTransfer(bankTransition));
        verify(accountService, times(3)).findById(any());
    }

    @Override
    @Disabled
    public void mustReturnSuccessWhenEdit() throws JsonProcessingException {
        throw new NotImplementedException();
    }

    @Override
    public void mustReturnBusinessExceptionWhenEdit() {
        throw new NotImplementedException();
    }

    @Override
    public void mustReturnSuccessWhenDelete() throws JsonProcessingException {
        throw new NotImplementedException();
    }

    @Override
    public void mustReturnBusinessExceptionWhenDelete() {
        throw new NotImplementedException();
    }
}