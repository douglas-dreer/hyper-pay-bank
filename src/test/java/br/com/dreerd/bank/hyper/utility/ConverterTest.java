package br.com.dreerd.bank.hyper.utility;

import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ConverterTest extends GeneratorInformation {

    private PersonDTO dto;
    private Person entity;
    private String json;

    @BeforeEach
    void setUp() {
        dto = createPerson(PersonDTO.class);
        entity = createPerson(Person.class);
        json = "{ \"name\": \"John\", \"surname\": \"Doe\" }";
    }

    @Test
    public void mustReturnSuccessWhenConvertToEntity() {
        Person result = Converter.convertTo(dto, Person.class);
        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
    }

    @Test
    public void mustReturnSuccessWhenConvertToDTO() {
        PersonDTO result = Converter.convertTo(entity, PersonDTO.class);
        assertNotNull(result);
    }

    @Test
    public void mustReturnSuccessWhenConvertToJson() throws JsonProcessingException {
        String result = Converter.toJSON(dto);
        assertNotNull(result);
    }

    @Test
    public void mustReturnSuccessWhenConvertToObject() throws IOException {
        PersonDTO result = Converter.toObject(json, PersonDTO.class);
        assertNotNull(result);
    }

    @Test
    public void mustReturnSuccessWhenConvertToString() throws IOException {
        String result = Converter.toString(dto);
        assertNotNull(result);
    }

    @Test
    public void mustReturnSuccessWhenConvertToMapList() {
        List<Person> persons = Collections.singletonList(entity);
        List<PersonDTO> dtoList = Converter.mapList(persons, PersonDTO.class);

        assertNotNull(dtoList);
        assertEquals(persons.size(), dtoList.size());
    }
}
