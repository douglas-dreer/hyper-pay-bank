package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.Contact;
import br.com.dreerd.bank.hyper.enums.ContactType;
import br.com.dreerd.bank.hyper.model.common.BaseModel;
import br.com.dreerd.bank.hyper.utility.Converter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
public class ContactDTO extends BaseModel<Contact> {

    private UUID id;
    private ContactType type;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isMain = false;
    private boolean isActive = true;

    public ContactDTO() {
        super(Contact.class);
    }

    public ContactDTO(UUID id, ContactType type, String value, boolean isMain) {
        super(Contact.class);
        this.id = id;
        this.type = type;
        this.value = value;
        this.isMain = isMain;
    }

    public ContactDTO(UUID id, ContactType type, String value, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isMain, boolean isActive) {
        super(Contact.class);
        this.id = id;
        this.type = type;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isMain = isMain;
        this.isActive = isActive;
    }

    @Override
    public Contact toEntity() {
        return Converter.convertTo(this, Contact.class);
    }
}

