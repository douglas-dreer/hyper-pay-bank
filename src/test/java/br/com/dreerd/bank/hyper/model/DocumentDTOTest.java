package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.enums.DocumentType;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DocumentDTOTest extends GeneratorInformation {
    private DocumentDTO document;
    private final String CPF_INVALID = "123.456.789-00";
    private final String CPF_VALID = "236.514.460-85";
    private final String CNPJ_INVALID = "12.234.567/890-00";
    private final String CNPJ_VALID = "00.220.906/0001-11";

    @BeforeEach
    public void setUp() {
        document = createDocument(DocumentDTO.class);
    }

    @Test
    public void mustReturnTrueWhenValidDocumentCPF() {
       assertTrue(document.isDocumentValid(CPF_VALID, DocumentType.CPF));
    }

    @Test
    public void mustReturnFalseWhenInValidDocumentCPF() {
        assertFalse(document.isDocumentValid(CPF_INVALID, DocumentType.CPF));
    }

    @Test
    public void mustReturnTrueWhenValidDocumentCNPJ() {
        assertTrue(document.isDocumentValid(CNPJ_VALID, DocumentType.CNPJ));
    }

    @Test
    public void mustReturnFalseWhenValidDocumentCNPJ() {
        assertFalse(document.isDocumentValid(CNPJ_INVALID, DocumentType.CNPJ));
    }

    @Test
    public void mustReturnDocumentExceptionWhenValidDocument() {
        assertFalse(document.isDocumentValid(CPF_INVALID, DocumentType.RG));
    }
}
