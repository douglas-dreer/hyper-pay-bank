package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.AccountTransation;
import br.com.dreerd.bank.hyper.enums.TransitionType;
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
public class AccountTransationDTO extends BaseModel<AccountTransation> implements Serializable {
    private UUID id;
    private AccountDTO account;
    private TransitionType transitionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal previousBalance;
    private LocalDateTime createdAt;

    public AccountTransationDTO() {
        super(AccountTransation.class);
    }

    public AccountTransationDTO(UUID id, AccountDTO account, TransitionType transitionType, BigDecimal amount, BigDecimal balance, BigDecimal previousBalance, LocalDateTime createdAt) {
        super(AccountTransation.class);
        this.id = id;
        this.account = account;
        this.transitionType = transitionType;
        this.amount = amount;
        this.balance = balance;
        this.previousBalance = previousBalance;
        this.createdAt = createdAt;
    }


    public AccountTransationDTO(AccountDTO account, TransitionType transitionType, BigDecimal amount) {
        super(AccountTransation.class);
        this.account = account;
        this.transitionType = transitionType;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public AccountTransationDTO(AccountDTO account, BigDecimal amount) {
        super(AccountTransation.class);
        this.account = account;
        this.amount = amount;
    }

    public AccountTransation toEntity() {
        return Converter.convertTo(this, AccountTransation.class);
    }
}
