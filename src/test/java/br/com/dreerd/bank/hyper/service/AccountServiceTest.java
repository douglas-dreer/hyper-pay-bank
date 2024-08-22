package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Account;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AccountDTO;

import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.repository.AccountRepository;
import br.com.dreerd.bank.hyper.service.common.BaseServiceTest;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceTest extends GeneratorInformation implements BaseServiceTest {
    @InjectMocks
    private AccountServiceImpl service;

    @Mock
    private AccountRepository repository;

    private Account entity;
    private List<Account> entityList = new ArrayList<>();
    private PersonDTO person = new PersonDTO();

    @Mock
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        person = createPerson(PersonDTO.class);
        entity = createAccount(Account.class);
        entityList.add(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(entityList);
        List<AccountDTO> resultList = service.list();

        assertFalse(resultList.isEmpty());
        assertEquals(1, resultList.size());

        verify(repository, times(1)).findAll();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenFindById() {
        Optional<Account> entityOptional = Optional.of(entity);
        when(repository.findById(any())).thenReturn(entityOptional);

        AccountDTO result = service.findById(entity.getId());
        assertEquals(entity.getId(), result.getId());
        assertNotNull(result);
        verify(repository, times(1)).findById(any());
    }

    @Override
    public void mustReturnNullWhenFindById() {

    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenFindById() {
        doThrow(BusinessException.class).when(repository).findById(any());
        assertThrows(BusinessException.class, () -> service.findById(entity.getId()));
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void mustReturnSuccessWhenFindByNumberAccount() {
        when(repository.findByNumber(any())).thenReturn(Optional.of(entity));
        AccountDTO result = service.findByNumber(entity.getNumber());
        assertEquals(entity.getNumber(), result.getNumber());
    }

    @Test
    public void mustReturnBusinessWhenFindByNumberAccount() {
        doThrow(BusinessException.class).when(repository).findByNumber(any());
        assertThrows(BusinessException.class, () -> service.findByNumber(entity.getNumber()));
        verify(repository, times(1)).findByNumber(any());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSave() {
        when(personService.findById(any())).thenReturn(person);
        when(repository.save(any())).thenReturn(entity);

        AccountDTO result = service.save(entity.toDTO());
        assertNotNull(result);
        verify(personService, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenSave() {
        when(personService.findById(any())).thenReturn(person);
        when(repository.save(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> service.save(entity.toDTO()));
        verify(repository, times(1)).save(any());
        verify(personService, times(1)).findById(any());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenEdit() throws JsonProcessingException {
        entity.setNumber(230883L);
        when(repository.findById(any())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        AccountDTO result = service.edit(entity.toDTO());
        assertNotNull(result);
        assertEquals(entity.getNumber(), result.getNumber());
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenEdit() {
        entity.setNumber(230883L);
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.save(any())).thenThrow(BusinessException.class);
        assertThrows(BusinessException.class, () -> service.edit(entity.toDTO()));

        verify(repository, times(1)).findById(any());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenDelete() throws JsonProcessingException {
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(any());
        service.delete(entity.getId());
        verify(repository, times(1)).existsById(any());
        verify(repository, times(1)).deleteById(any());

    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenDelete() {
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(BusinessException.class, () -> service.delete(entity.getId()));
        verify(repository, times(1)).existsById(any());
    }
}
