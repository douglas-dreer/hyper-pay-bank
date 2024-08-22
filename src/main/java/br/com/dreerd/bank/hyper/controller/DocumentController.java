package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseController;
import br.com.dreerd.bank.hyper.entity.Document;
import br.com.dreerd.bank.hyper.model.DocumentDTO;
import br.com.dreerd.bank.hyper.service.common.DocumentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/documents")
@Log4j2
public class DocumentController extends BaseController<Document, DocumentDTO, DocumentService> {
    protected DocumentController(DocumentService service) {
        super(service);
    }
}
