package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.repository.PersonRepository;
import br.com.dreerd.bank.hyper.service.common.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<PersonDTO> list() {
        return personRepository
                .findAll()
                .stream()
                .map(Person::toDTO)
                .toList();
    }

    @Override
    public PersonDTO findById(UUID id) {
        Optional<Person> findOne = personRepository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Person doesn't found"));
    }

    @Override
    public PersonDTO save(PersonDTO dto) {
        try {
            Person result = personRepository.save(dto.toEntity());
            return result.toDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public PersonDTO edit(PersonDTO dto) throws JsonProcessingException {
        Optional<Person> findOne = personRepository.findById(dto.getId());

        if (!findOne.isPresent()) {
            log.info("Person not found");
            throw new BusinessException("404", "Conctact not found", "The Person doesn't found", dto);
        }

        Person AddressDB = findOne.get();
        AddressDB.updateFields(dto);
        return personRepository.save(AddressDB).toDTO();
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        if (!personRepository.existsById(id)) {
            throw new BusinessException("404", "Conctact not found", "The Person doesn't found", id);
        }

        personRepository.deleteById(id);
    }
}
