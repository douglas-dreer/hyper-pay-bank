package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.Address;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;


public interface AddressService extends BaseService<Address, AddressDTO> {
    List<AddressDTO> list();

    AddressDTO findById(UUID id);

    AddressDTO save(AddressDTO dto);

    AddressDTO edit(AddressDTO dto) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;

}
