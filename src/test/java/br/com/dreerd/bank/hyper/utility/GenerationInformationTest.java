package br.com.dreerd.bank.hyper.utility;

import br.com.dreerd.bank.hyper.entity.Contact;
import br.com.dreerd.bank.hyper.enums.AddressType;
import br.com.dreerd.bank.hyper.enums.ContactType;
import br.com.dreerd.bank.hyper.enums.DocumentType;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenerationInformationTest {

    @Test
    public void mustReturnSuccessWhenCreateADocument() {
        DocumentDTO result = GeneratorInformation.createDocument(DocumentDTO.class);
        assertNotNull(result);
        assertEquals(DocumentType.CPF, result.getDocumentType());
        assertEquals("123.456.789-00", result.getValue());
        assertTrue(result.isActive());
    }

    @Test
    public void mustReturnSuccessWhenCreateAContact() {
        Contact result = GeneratorInformation.createContact(Contact.class);
        assertNotNull(result);
        assertEquals(ContactType.EMAIL, result.getType());
        assertEquals("your_name@email.com", result.getValue());
        assertTrue(result.isActive());
        assertTrue(result.isMain());
    }

    @Test
    public void mustReturnSuccessWhenCreateAnAddress() {
        AddressDTO result = GeneratorInformation.createAddress(AddressDTO.class);
        assertNotNull(result);
        assertEquals(AddressType.BUSINESS, result.getAddressType());
        assertEquals("767 5th Ave", result.getStreet());
        assertEquals("New York", result.getCity());
        assertEquals("NY", result.getState());
        assertEquals("US", result.getCountry());
        assertTrue(result.isActive());
        assertTrue(result.isMain());
    }

    @Test
    public void mustReturnSuccessWhenCreateAPerson() {
        PersonDTO result = GeneratorInformation.createPerson(PersonDTO.class);
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertTrue(result.isActive());
    }

    @Test
    public void mustReturnIllegalArgumentExceptionWhenCreateData() {
        assertThrows(IllegalArgumentException.class, () -> GeneratorInformation.createDocument(PersonDTO.class));
    }
}
