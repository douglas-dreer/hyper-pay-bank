package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Document;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import br.com.dreerd.bank.hyper.repository.DocumentRepository;
import br.com.dreerd.bank.hyper.service.common.DocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository DocumentRepository;

    DocumentServiceImpl(DocumentRepository documentRepository) {
        this.DocumentRepository = documentRepository;
    }

    @Override
    public List<DocumentDTO> list() {
        return DocumentRepository
                .findAll()
                .stream()
                .map(Document::toDTO)
                .toList();
    }

    @Override
    public DocumentDTO findById(UUID id) {
        Optional<Document> findOne = DocumentRepository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Document doesn't found"));
    }

    @Override
    public DocumentDTO save(DocumentDTO dto) {
        try {
            Document result = DocumentRepository.save(dto.toEntity());
            return result.toDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public DocumentDTO edit(DocumentDTO dto) throws JsonProcessingException {
        Optional<Document> findOne = DocumentRepository.findById(dto.getId());

        if (!findOne.isPresent()) {
            log.info("Document not found");
            throw new BusinessException("404", "Conctact not found", "The Document doesn't found", dto);
        }

        Document addressDB = findOne.get();
        addressDB.updateFields(dto);
        return DocumentRepository.save(addressDB).toDTO();
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        if (!DocumentRepository.existsById(id)) {
            throw new BusinessException("404", "Conctact not found", "The Document doesn't found", id);
        }

        DocumentRepository.deleteById(id);
    }
}
