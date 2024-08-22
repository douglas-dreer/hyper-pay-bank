package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Contact;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.ContactDTO;
import br.com.dreerd.bank.hyper.repository.ContactRepository;
import br.com.dreerd.bank.hyper.service.common.ContactService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactDTO> list() {
        return contactRepository
                .findAll()
                .stream()
                .map(Contact::toDTO)
                .toList();
    }

    @Override
    public ContactDTO findById(UUID id) {
        Optional<Contact> findOne = contactRepository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Contact doesn't found"));
    }

    @Override
    public ContactDTO save(ContactDTO dto) {
        try {
            Contact result = contactRepository.save(dto.toEntity());
            return result.toDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ContactDTO edit(ContactDTO dto) throws JsonProcessingException {
        Optional<Contact> findOne = contactRepository.findById(dto.getId());

        if (!findOne.isPresent()) {
            log.info("Contact not found");
            throw new BusinessException("404", "Conctact not found", "The contact doesn't found", dto);
        }

        Contact contactDB = findOne.get();
        contactDB.updateFields(dto);
        return contactRepository.save(contactDB).toDTO();
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        if (!contactRepository.existsById(id)) {
            log.info("Contact not found");
            throw new BusinessException("404", "Conctact not found", "The contact doesn't found", id);
        }

        contactRepository.deleteById(id);
    }
}
