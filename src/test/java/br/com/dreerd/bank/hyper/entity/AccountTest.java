package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntityTest;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountTest extends GeneratorInformation implements BaseEntityTest {

    private UUID id;
    private Person person;
    private Long number;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        person = createPerson(Person.class);
        number = 100L;
        balance = BigDecimal.valueOf(100L);
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now().plusHours(12);
        isActive = true;
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithAllConstructor() {
        Account entity = new Account(id, person, number, balance, createdAt, updatedAt, isActive);
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        Account entity = new Account();
        entity.setId(id);
        entity.setPerson(person);
        entity.setNumber(number);
        entity.setBalance(balance);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        entity.setActive(isActive);
        checking(entity);
    }

    @Test
    public void mustReturnSuccessWhenConstructorPartial() {
        Account entity = new Account(number, person, balance);
        assertNotNull(entity);
        assertEquals(number, entity.getNumber());
        assertEquals(person, entity.getPerson());
        assertEquals(balance, entity.getBalance());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        Account entity = Account.builder()
                .id(id)
                .person(person)
                .number(number)
                .balance(balance)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isActive(isActive)
                .build();
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() throws Exception {
        Account entity = createAccount(Account.class);
        assertNotNull(entity.toDTO());
    }

    @Override
    public void mustReturnSuccessWhenEquals() {

    }

    @Override
    @Test
    public void mustReturnSuccessWhenHash() {
        Account accountOriginal = createAccount(Account.class);

        AccountDTO accountUpdated = createAccount(AccountDTO.class);
        accountUpdated.setId(UUID.randomUUID());

        accountOriginal.updateFields(accountUpdated);
        assertNotNull(accountUpdated);
    }

    private void checking(Account datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The AbilityDTO should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(id, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getNumber(), "The name should not be null"),
                () -> assertEquals(number, datas.getNumber(), "The element name must be the same as the one sent"),
                () -> assertNotNull(datas.getBalance(), "The complement should not be null"),
                () -> assertEquals(balance, datas.getBalance(), "The element complement must be the same as the one sent"),
                () -> assertNotNull(datas.getCreatedAt(), "The createdAt should not be null"),
                () -> assertEquals(createdAt, datas.getCreatedAt(), "The element complement must be the same as the one sent"),
                () -> assertNotNull(datas.getUpdatedAt(), "The updatedAt should not be null"),
                () -> assertEquals(updatedAt, datas.getUpdatedAt(), "The element complement must be the same as the one sent"),
                () -> assertTrue(datas.isActive(), "The isActive should not be null")
        );
    }
}
