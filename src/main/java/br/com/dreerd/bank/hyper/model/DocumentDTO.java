package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.Document;
import br.com.dreerd.bank.hyper.enums.DocumentType;
import br.com.dreerd.bank.hyper.exception.DocumentException;
import br.com.dreerd.bank.hyper.model.common.BaseModel;
import br.com.dreerd.bank.hyper.utility.Converter;
import br.com.dreerd.bank.hyper.utility.DocumentCNPJ;
import br.com.dreerd.bank.hyper.utility.DocumentCPF;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Log4j2
public class DocumentDTO extends BaseModel<Document> {
    private UUID id;
    private DocumentType documentType;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    protected DocumentDTO() {
        super(Document.class);
    }

    public DocumentDTO(UUID id, DocumentType documentType, String value) {
        super(Document.class);
        this.id = id;
        this.documentType = documentType;
        this.value = value;
    }

    public DocumentDTO(UUID id, DocumentType documentType, String value, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive) {
        super(Document.class);
        this.id = id;
        this.documentType = documentType;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    @Override
    public Document toEntity() {
        return Converter.convertTo(this, Document.class);
    }

    public boolean isDocumentValid(String document, DocumentType documentType) {
        boolean result = false;
        try {
            if (!DocumentType.check(documentType.name())) {
                throw new DocumentException(document, documentType, "Document type not avaliable");
            }

            if (documentType.equals(DocumentType.CPF)) {
                result =  DocumentCPF.isValidCPF(document);
            }

            if (documentType.equals(DocumentType.CNPJ)) {
                result = DocumentCNPJ.isValidCNPJ(document);
            }

            return result;
        } catch (DocumentException e) {
            log.error(e);
            return false;
        }
    }
}
