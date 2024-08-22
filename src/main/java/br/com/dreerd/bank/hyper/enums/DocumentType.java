package br.com.dreerd.bank.hyper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum DocumentType {
    CPF,
    RG,
    CNPJ,
    PASSPORT;

    public static boolean check(String documentType) {
        DocumentType result = Arrays.stream(DocumentType.values())
                .filter(item -> item.name().equalsIgnoreCase(documentType))
                .findFirst()
                .orElse(null);
        return result != null;
    }
}
