package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseController;
import br.com.dreerd.bank.hyper.entity.Contact;
import br.com.dreerd.bank.hyper.model.ContactDTO;
import br.com.dreerd.bank.hyper.service.common.ContactService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contacts")
@Log4j2
public class ContactController extends BaseController<Contact, ContactDTO, ContactService> {
    protected ContactController(ContactService service) {
        super(service);
    }
}
