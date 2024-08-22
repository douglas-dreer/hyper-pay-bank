package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.repository.PersonRepository;
import br.com.dreerd.bank.hyper.service.common.BaseServiceTest;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest extends GeneratorInformation implements BaseServiceTest {
    @InjectMocks
    private PersonServiceImpl service;

    @Mock
    private PersonRepository repository;

    private Person entity;
    private List<Person> entityList = new ArrayList<>();

    private PersonServiceTest() {
    }

    @BeforeEach
    void setUp() {
        entity = createPerson(Person.class);
        entityList.add(entity);
    }

    @Test
    @Override
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(entityList);

        List<PersonDTO> resultList = service.list();

        assertFalse(resultList.isEmpty());
        assertEquals(1, resultList.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    @Override
    public void mustReturnSuccessWhenFindById() {
        Optional<Person> entityOptional = Optional.of(entity);
        when(repository.findById(any())).thenReturn(entityOptional);

        PersonDTO result = service.findById(entity.getId());
        assertEquals(entity.getId(), result.getId());
        assertNotNull(result);
        verify(repository, times(1)).findById(any());
    }

    @Override
    public void mustReturnNullWhenFindById() {
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionWhenFindById() {
        doThrow(BusinessException.class).when(repository).findById(any());
        assertThrows(BusinessException.class, () -> service.findById(entity.getId()));
        verify(repository, times(1)).findById(any());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(entity);
        PersonDTO result = service.save(entity.toDTO());
        assertEquals(entity.getId(), result.getId());
        assertNotNull(result);
        verify(repository, times(1)).save(any());
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionWhenSave() {
        doThrow(BusinessException.class).when(repository).save(any());
        assertThrows(BusinessException.class, () -> service.save(entity.toDTO()));
        verify(repository, times(1)).save(any());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenEdit() throws JsonProcessingException {
        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());

        when(repository.findById(any())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);

        PersonDTO result = service.edit(entity.toDTO());
        assertEquals(entity.getId(), result.getId());
        assertNotNull(result);
        verify(repository, times(1)).findById(any());
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionWhenEdit() {
        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());

        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> service.edit(entity.toDTO()));
        verify(repository, times(1)).findById(any());
    }

    @Test
    @Override
    public void mustReturnSuccessWhenDelete() throws JsonProcessingException {
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(any());
        service.delete(entity.getId());
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @Override
    public void mustReturnBusinessExceptionWhenDelete() {
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(BusinessException.class, () -> service.delete(entity.getId()));
        verify(repository, times(1)).existsById(any());
    }
}
