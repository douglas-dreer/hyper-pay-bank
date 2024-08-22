package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;


public interface PersonService extends BaseService<Person, PersonDTO> {
    List<PersonDTO> list();

    PersonDTO findById(UUID id);

    PersonDTO save(PersonDTO dto);

    PersonDTO edit(PersonDTO dto) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;

}
