package br.com.dreerd.bank.hyper.utility;


import br.com.dreerd.bank.hyper.entity.*;
import br.com.dreerd.bank.hyper.enums.AddressType;
import br.com.dreerd.bank.hyper.enums.ContactType;
import br.com.dreerd.bank.hyper.enums.DocumentType;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.model.*;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Log4j2
public class GeneratorInformation {
    public GeneratorInformation() {
    }

    public static <T> T createContact(Class<T> clazz) {
        ContactDTO dto = ContactDTO.builder()
                .id(UUID.randomUUID())
                .type(ContactType.EMAIL)
                .value("your_name@email.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(5))
                .isMain(true)
                .isActive(true)
                .build();
        return assignBasedOnClass(dto, clazz, ContactDTO.class, Contact.class);
    }


    public static <T> T createAddress(Class<T> clazz) {
        AddressDTO dto = AddressDTO.builder()
                .id(UUID.randomUUID())
                .addressType(AddressType.BUSINESS)
                .street("767 5th Ave")
                .number("")
                .complement("")
                .neighborhood("")
                .city("New York")
                .state("NY")
                .postalCode("10153")
                .country("US")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(5))
                .isActive(true)
                .isMain(true)
                .build();
        return assignBasedOnClass(dto, clazz, AddressDTO.class, Address.class);
    }

    public static <T> T createPerson(Class<T> clazz) {
        List<ContactDTO> contacts = Collections.singletonList(createContact(ContactDTO.class));
        List<AddressDTO> addresses = Collections.singletonList(createAddress(AddressDTO.class));
        List<DocumentDTO> documents = Collections.singletonList(createDocument(DocumentDTO.class));
        PersonDTO dto = PersonDTO.builder()
                .id(UUID.randomUUID())
                .name("John")
                .surname("Doe")
                .addresses(addresses)
                .contacts(contacts)
                .documents(documents)
                .birthday(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(5))
                .isActive(true)
                .build();
        return assignBasedOnClass(dto, clazz, PersonDTO.class, Person.class);
    }

    public static <T> T createDocument(Class<T> clazz) {
        DocumentDTO dto = DocumentDTO.builder()
                .id(UUID.randomUUID())
                .documentType(DocumentType.CPF)
                .value("123.456.789-00")
                .isActive(true)
                .build();
        return assignBasedOnClass(dto, clazz, DocumentDTO.class, Document.class);
    }

    public static <T> T createAccount(Class<T> clazz) {
        PersonDTO personDTO = createPerson(PersonDTO.class);
        AccountDTO dto = AccountDTO.builder()
                .id(UUID.randomUUID())
                .number(1L)
                .person(personDTO)
                .balance(new BigDecimal("100000"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now().plusHours(5))
                .isActive(true)
                .build();
        return assignBasedOnClass(dto, clazz, AccountDTO.class, Account.class);
    }

    public static BankTransitionDTO createBankTransition() {
        return BankTransitionDTO.builder()
                .id(UUID.randomUUID())
                .originAccountId(UUID.randomUUID())
                .destinationAccountId(UUID.randomUUID())
                .transitionType(TransitionType.DEPOSIT)
                .amount(new BigDecimal("100"))
                .build();

    }

    public static <T> T createAccountTransition(Class<T> clazz) {
        AccountDTO accountDTO = createAccount(AccountDTO.class);
        AccountTransationDTO dto = AccountTransationDTO
                .builder()
                .id(UUID.randomUUID())
                .account(accountDTO)
                .transitionType(TransitionType.DEPOSIT)
                .amount(new BigDecimal("10.75"))
                .previousBalance(new BigDecimal("23.15"))
                .balance(new BigDecimal("153.42"))
                .createdAt(LocalDateTime.now())
                .build();
        return assignBasedOnClass(dto, clazz, AccountTransationDTO.class, AccountTransation.class);
    }
    private static <T, D, E> T assignBasedOnClass(D dto, Class<T> targetClass, Class<D> dtoClass, Class<E> entityClass) {
        final String msgInfo = String.format("Assigning class: { targetClass: %s, dtoClass: %s, entityClass: %s }",
                targetClass.getSimpleName(),
                dtoClass.getSimpleName(),
                entityClass.getSimpleName()
        );
        try {


            if (!targetClass.isAssignableFrom(dtoClass) && !targetClass.isAssignableFrom(entityClass)) {
                throw new IllegalArgumentException("Invalid class type");
            }
            return Converter.convertTo(dto, targetClass);
        } catch (Exception e) {
            log.info(msgInfo);
            throw new IllegalArgumentException("Error converting class types", e);
        }
    }
}
