package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.model.AccountTransationDTO;
import br.com.dreerd.bank.hyper.utility.Converter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "TBL0006_ACCOUNT_TRANSATIONS")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class AccountTransation extends BaseEntity<AccountTransationDTO> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransitionType transitionType;

    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal previousBalance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public AccountTransation(){
        super(AccountTransationDTO.class);
    }

    public AccountTransation(UUID id, Account account, TransitionType transitionType, BigDecimal amount, LocalDateTime createdAt) {
        super(AccountTransationDTO.class);
        this.id = id;
        this.account = account;
        this.transitionType = transitionType;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public AccountTransation(Account account, TransitionType transitionType, BigDecimal amount) {
        super(AccountTransationDTO.class);
        this.account = account;
        this.transitionType = transitionType;
        this.amount = amount;
    }

    public AccountTransation(UUID id, Account account, TransitionType transitionType, BigDecimal amount, BigDecimal previousBalance, BigDecimal balance, LocalDateTime createdAt) {
        super(AccountTransationDTO.class);
        this.id = id;
        this.account = account;
        this.transitionType = transitionType;
        this.amount = amount;
        this.previousBalance = previousBalance;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public AccountTransationDTO toDTO() {
        return Converter.convertTo(this, AccountTransationDTO.class);
    }

}
