package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.enums.DocumentType;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import br.com.dreerd.bank.hyper.utility.Converter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TBL0004_DOCUMENTS")
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Document extends BaseEntity<DocumentDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(unique = true)
    private String value;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isActive = true;

    public Document() {
        super(DocumentDTO.class);
    }

    public void updateFields(DocumentDTO newItem) {
        this.documentType = !Objects.equals(this.documentType, newItem.getDocumentType()) ? newItem.getDocumentType() : this.documentType;
        this.value = !Objects.equals(this.value, newItem.getValue()) ? newItem.getValue() : this.value;
        this.isActive = this.isActive != newItem.isActive() ? newItem.isActive() : this.isActive;
    }

    @Override
    public DocumentDTO toDTO() {
        return Converter.convertTo(this, DocumentDTO.class);
    }
}
