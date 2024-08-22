package br.com.dreerd.bank.hyper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    NOT_NULL("The element '%s' must not be null"),
    NOT_EQUALS("The element '%s' must be the same as the one sent"),
    NOT_EMPTY("The element '%s' must not be empty");

    final String message;
}
