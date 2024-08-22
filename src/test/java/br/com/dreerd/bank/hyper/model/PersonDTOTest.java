package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.enums.ErrorType;
import br.com.dreerd.bank.hyper.model.common.BaseModelTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDTOTest extends BaseModelTest {
    private final List<String> FIELD_LIST = Arrays.asList("id", "name", "surname", "addresses", "contacts", "documents", "birthDate", "createdAt", "updatedAt", "isActive");

    private UUID id;
    private String name;
    private String surname;
    private List<ContactDTO> contacts;
    private List<DocumentDTO> documents;
    private List<AddressDTO> addresses;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    @BeforeEach
    public void setUp() {
        documents = Collections.singletonList(createDocument(DocumentDTO.class));
        addresses = Collections.singletonList(createAddress(AddressDTO.class));
        contacts = Collections.singletonList(createContact(ContactDTO.class));

        id = UUID.randomUUID();
        name = "John Doe";
        surname = "Doe";
        birthDate = LocalDate.from(LocalDateTime.now().minusYears(2000));
        createdAt = LocalDateTime.from(LocalDateTime.now());
        updatedAt = LocalDateTime.from(LocalDateTime.now().plusDays(2));
        isActive = true;
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithAllConstructor() {
        PersonDTO dto = new PersonDTO(id, name, surname, contacts, addresses, documents, birthDate, createdAt, updatedAt, isActive);
        checking(dto);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithPartialConstructor() {
        List<String> fields = Arrays.asList("id", "name", "surname", "contacts", "addresses", "documents", "birthDate");
        PersonDTO dto = new PersonDTO(id, name, surname, contacts, addresses, documents, birthDate);
        checking(dto, fields);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSetter() {
        PersonDTO dto = new PersonDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setSurname(surname);
        dto.setContacts(contacts);
        dto.setAddresses(addresses);
        dto.setDocuments(documents);
        dto.setBirthday(birthDate);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        dto.setActive(isActive);
        checking(dto);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenBuilder() {
        PersonDTO dto = PersonDTO.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .contacts(contacts)
                .addresses(addresses)
                .documents(documents)
                .birthday(birthDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .isActive(isActive)
                .build();
        checking(dto);
    }

    @Override
    public <T> void checking(T data) {
        checking(data, FIELD_LIST);
    }

    @Override
    public <T> void checking(T data, List<String> fieldList) {
        PersonDTO dto = (PersonDTO) data;

        Map<String, Executable> checks = new HashMap<>();
        checks.put("id", () -> {
            assertNotNull(dto.getId(), createMessage("id", ErrorType.NOT_NULL));
            assertEquals(id, dto.getId(), createMessage("id", ErrorType.NOT_EQUALS));
        });
        checks.put("name", () -> {
            assertNotNull(dto.getName(), createMessage("name", ErrorType.NOT_NULL));
            assertEquals(name, dto.getName(), createMessage("name", ErrorType.NOT_EQUALS));
        });
        checks.put("surname", () -> {
            assertNotNull(dto.getSurname(), createMessage("surname", ErrorType.NOT_NULL));
            assertEquals(surname, dto.getSurname(), createMessage("surname", ErrorType.NOT_EQUALS));
        });
        checks.put("addresses", () -> assertFalse(dto.getAddresses().isEmpty(), createMessage("addresses", ErrorType.NOT_EMPTY)));
        checks.put("contacts", () -> assertFalse(dto.getContacts().isEmpty(), createMessage("contacts", ErrorType.NOT_EMPTY)));
        checks.put("documents", () -> assertFalse(dto.getDocuments().isEmpty(), createMessage("documents", ErrorType.NOT_EMPTY)));
        checks.put("birthDate", () -> assertEquals(birthDate, dto.getBirthday(), createMessage("birthday", ErrorType.NOT_EQUALS)));
        checks.put("createdAt", () -> assertNotNull(dto.getCreatedAt(), createMessage("created at", ErrorType.NOT_NULL)));
        checks.put("updatedAt", () -> assertNotNull(dto.getUpdatedAt(), createMessage("updated at", ErrorType.NOT_NULL)));
        checks.put("isActive", () -> assertTrue(dto.isActive(), createMessage("is active", ErrorType.NOT_EQUALS)));

        // Aplicar verificações
        assertAll("Verify selected fields",
                fieldList.stream()
                        .filter(checks::containsKey)
                        .map(checks::get)
        );
    }
}
