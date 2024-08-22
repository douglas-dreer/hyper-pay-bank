package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntityTest;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTransationTest extends GeneratorInformation implements BaseEntityTest {
    private UUID id;
    private Account account;
    private TransitionType transitionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal previousBalance;
    private LocalDateTime createdAt;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        account = createAccount(Account.class);
        transitionType = TransitionType.DEPOSIT;
        amount = new BigDecimal("100.00");
        balance = new BigDecimal("100.00");
        previousBalance = new BigDecimal("100.00");
        createdAt = LocalDateTime.now();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithAllConstructor() {
        AccountTransation entity = new AccountTransation(id, account, transitionType, amount, balance, previousBalance, createdAt);
        checking(entity);
    }

    @Test
    public void mustReturnSuccessWhenInitializeWithPartialContructors() {
        AccountTransation entity = new AccountTransation(id, account, transitionType, amount, createdAt);
        assertEquals(id, entity.getId());
        assertEquals(account, entity.getAccount());
        assertEquals(transitionType, entity.getTransitionType());
        assertEquals(amount, entity.getAmount());
        assertEquals(createdAt, entity.getCreatedAt());
    }

    @Test
    public void mustReturnSuccessWhenInitializeWithPartialConstructor() {
        AccountTransation entity = new AccountTransation(account, transitionType, amount);
        assertEquals(account, entity.getAccount());
        assertEquals(transitionType, entity.getTransitionType());
        assertEquals(amount, entity.getAmount());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        AccountTransation entity = new AccountTransation();
        entity.setId(id);
        entity.setAccount(account);
        entity.setTransitionType(transitionType);
        entity.setAmount(amount);
        entity.setBalance(balance);
        entity.setPreviousBalance(previousBalance);
        entity.setCreatedAt(createdAt);
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        AccountTransation entity = AccountTransation.builder()
                .id(id)
                .account(account)
                .transitionType(transitionType)
                .amount(amount)
                .balance(balance)
                .previousBalance(previousBalance)
                .createdAt(createdAt)
                .build();
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() throws Exception {
      AccountTransation entity = createAccountTransition(AccountTransation.class);
      assertNotNull(entity.toDTO());
    }


    @Override
    public void mustReturnSuccessWhenEquals() {

    }

    @Override
    public void mustReturnSuccessWhenHash() {

    }

    private void checking(AccountTransation datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The AbilityDTO should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(id, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getAccount(), "The complement should not be null"),
                () -> assertEquals(account, datas.getAccount(), "The element complement must be the same as the one sent"),
                () -> assertNotNull(datas.getTransitionType(), "The transitionType should not be null"),
                () -> assertEquals(transitionType, datas.getTransitionType(), "The element transitionType must be the same as the one sent"),
                () -> assertNotNull(datas.getAmount(), "The amount should not be null"),
                () -> assertEquals(amount, datas.getAmount(), "The amount must be the same as the one sent"),
                () -> assertNotNull(datas.getBalance(), "The balance should not be null"),
                () -> assertEquals(balance, datas.getBalance(), "The balance must be the same as the one sent"),
                () -> assertNotNull(datas.getPreviousBalance(), "The previousBalance should not be null"),
                () -> assertEquals(previousBalance, datas.getPreviousBalance(), "The previousBalance must be the same as the one sent"),
                () -> assertNotNull(datas.getCreatedAt(), "The created at should not be null"),
                () -> assertEquals(createdAt, datas.getCreatedAt(), "The created at should be the same as the one sent")
        );
    }
}
