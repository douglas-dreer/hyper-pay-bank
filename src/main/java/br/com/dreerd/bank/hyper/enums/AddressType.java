package br.com.dreerd.bank.hyper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressType {
    RESIDENTIAL("residential"),
    BUSINESS("Business");

    private final String name;
}
