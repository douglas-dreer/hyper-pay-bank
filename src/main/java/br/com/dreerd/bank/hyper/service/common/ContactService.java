package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.Contact;
import br.com.dreerd.bank.hyper.model.ContactDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface ContactService extends BaseService<Contact, ContactDTO> {
    List<ContactDTO> list();

    ContactDTO findById(UUID id);

    ContactDTO save(ContactDTO dto);

    ContactDTO edit(ContactDTO contactDTO) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;
}
