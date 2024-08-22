package br.com.dreerd.bank.hyper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransitionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    INTERNAL_TRANSFER("Internal Transfer");

    private final String name;
}
