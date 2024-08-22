package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.model.ContactDTO;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.utility.Converter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "TBL0001_PERSONS")
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity<PersonDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<Document> documents = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private List<Address> addresses = new ArrayList<>();

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    public Person() {
        super(PersonDTO.class);
    }

    public void updateFields(PersonDTO newItem) {
        this.name = !Objects.equals(this.name, newItem.getName()) ? newItem.getName() : this.name;
        this.surname = !Objects.equals(this.surname, newItem.getSurname()) ? newItem.getSurname() : this.surname;
        this.contacts = mergeContacts(newItem.getContacts());
        this.addresses = mergeAddresses(newItem.getAddresses());
        this.documents = mergeDocuments(newItem.getDocuments());
        this.birthday = this.birthday != newItem.getBirthday() ? newItem.getBirthday() : this.birthday;
        this.isActive = this.isActive != newItem.isActive() ? newItem.isActive() : this.isActive;
    }

    private List<Document> mergeDocuments(List<DocumentDTO> documents) {
        Set<Document> documentSet = new HashSet<>(this.documents);
        documentSet.addAll(documents.stream().map(DocumentDTO::toEntity).toList());
        return new ArrayList<>(documentSet);
    }

    private List<Contact> mergeContacts(List<ContactDTO> contacts) {
        Set<Contact> contactSet = new HashSet<>(this.contacts);
        contactSet.addAll(contacts.stream().map(ContactDTO::toEntity).toList());
        return new ArrayList<>(contactSet);
    }

    private List<Address> mergeAddresses(List<AddressDTO> addresses) {
        Set<Address> addressSet = new HashSet<>(this.addresses);
        addressSet.addAll(addresses.stream().map(AddressDTO::toEntity).toList());
        return new ArrayList<>(addressSet);
    }

    @Override
    public PersonDTO toDTO() {
        return Converter.convertTo(this, PersonDTO.class);
    }
}
