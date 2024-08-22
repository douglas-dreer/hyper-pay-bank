package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.model.common.BaseModel;
import br.com.dreerd.bank.hyper.utility.Converter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PersonDTO extends BaseModel<Person> {
    private UUID id;
    private String name;
    private String surname;
    private List<ContactDTO> contacts;
    private List<AddressDTO> addresses;
    private List<DocumentDTO> documents;
    private LocalDate birthday;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    public PersonDTO() {
        super(Person.class);
    }

    public PersonDTO(UUID id, String name, String surname, List<ContactDTO> contacts, List<AddressDTO> addresses, List<DocumentDTO> documents, LocalDate birthday) {
        super(Person.class);
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contacts = contacts;
        this.addresses = addresses;
        this.documents = documents;
        this.birthday = birthday;
    }

    public PersonDTO(UUID id, String name, String surname, List<ContactDTO> contacts, List<AddressDTO> addresses, List<DocumentDTO> documents, LocalDate birthday, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive) {
        super(Person.class);
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contacts = contacts;
        this.addresses = addresses;
        this.documents = documents;
        this.birthday = birthday;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }

    @Override
    public Person toEntity() {
        return Converter.convertTo(this, Person.class);
    }
}
