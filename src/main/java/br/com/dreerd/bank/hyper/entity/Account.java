package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.utility.Converter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL0005_ACCOUNTS")
@Data
@Builder
public class Account extends BaseEntity<AccountDTO> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Person person;

    private Long number;
    private BigDecimal balance;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive = true;

    public Account() {
        super(AccountDTO.class);
    }

    public Account(UUID id, Person person, Long number, BigDecimal balance, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive) {
        super(AccountDTO.class);
        this.id = id;
        this.person = person;
        this.number = number;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    public Account(Long number, Person person, BigDecimal balance) {
        super(AccountDTO.class);
        this.person = person;
        this.number = number;
        this.balance = balance;
    }

    public void updateFields(AccountDTO newItem) {
        this.balance = !Objects.equals(balance, newItem.getBalance()) ? newItem.getBalance() : balance;
    }

    @Override
    public AccountDTO toDTO() {
        return Converter.convertTo(this, AccountDTO.class);
    }
}
