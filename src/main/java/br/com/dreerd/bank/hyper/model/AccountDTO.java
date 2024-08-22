package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.Account;
import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.model.common.BaseModel;
import br.com.dreerd.bank.hyper.utility.Converter;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AccountDTO extends BaseModel<Account> implements Serializable {
    private UUID id;
    private Long number;
    private PersonDTO person;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    protected AccountDTO() {
        super(Account.class);
    }

    public AccountDTO(UUID id, Long number, PersonDTO person, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive) {
        super(Account.class);
        this.id = id;
        this.number = number;
        this.person = person;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    public AccountDTO(UUID id, Long number, BigDecimal balance){
        super(Account.class);
        this.id = id;
        this.number = number;
        this.balance = balance;
    }

    @Override
    public Account toEntity() {
        return Converter.convertTo(this, Account.class);
    }
}
