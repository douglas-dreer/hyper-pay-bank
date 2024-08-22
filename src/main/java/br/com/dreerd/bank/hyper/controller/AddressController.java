package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseController;
import br.com.dreerd.bank.hyper.entity.Address;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.service.common.AddressService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addresses")
@Log4j2
public class AddressController extends BaseController<Address, AddressDTO, AddressService> {
    protected AddressController(AddressService service) {
        super(service);
    }
}
