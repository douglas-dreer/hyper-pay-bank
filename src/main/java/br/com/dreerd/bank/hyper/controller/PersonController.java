package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseController;
import br.com.dreerd.bank.hyper.entity.Person;
import br.com.dreerd.bank.hyper.model.PersonDTO;
import br.com.dreerd.bank.hyper.service.common.PersonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
@Log4j2
public class PersonController extends BaseController<Person, PersonDTO, PersonService> {
    protected PersonController(PersonService service) {
        super(service);
    }
}
