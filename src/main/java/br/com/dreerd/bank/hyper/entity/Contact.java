package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.enums.ContactType;
import br.com.dreerd.bank.hyper.model.ContactDTO;
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

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL0003_CONTACTS")
@AllArgsConstructor
@Data
public class Contact extends BaseEntity<ContactDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ContactType type;
    @Column(nullable = false)
    private String value;
    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isMain = false;
    private boolean isActive = true;

    public Contact() {
        super(ContactDTO.class);
    }

    public void updateFields(ContactDTO newItem) {
        this.id = this.id != newItem.getId() ? newItem.getId() : this.id;
        this.type = this.type != newItem.getType() ? newItem.getType() : this.type;
        this.value = !Objects.equals(this.value, newItem.getValue()) ? newItem.getValue() : this.value;
        this.isMain = this.isMain != newItem.isMain() ? newItem.isMain() : this.isMain;
        this.isActive = this.isActive != newItem.isActive() ? newItem.isActive() : this.isActive;
    }

    @Override
    public ContactDTO toDTO() {
        return Converter.convertTo(this, ContactDTO.class);
    }
}
