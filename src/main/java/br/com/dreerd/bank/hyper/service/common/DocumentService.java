package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.Document;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;


public interface DocumentService extends BaseService<Document, DocumentDTO> {
    List<DocumentDTO> list();

    DocumentDTO findById(UUID id);

    DocumentDTO save(DocumentDTO dto);

    DocumentDTO edit(DocumentDTO dto) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;

}
