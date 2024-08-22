package br.com.dreerd.bank.hyper.exception;

import br.com.dreerd.bank.hyper.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentException extends RuntimeException {

    private String document;
    private String documentType;

    public DocumentException(String document, String documentType, String message, Exception e) {
        super(message);
        this.document = document;
        this.documentType = documentType;
    }

    public DocumentException(String document, DocumentType documentType, String message) {
        super(message);
    }
}

