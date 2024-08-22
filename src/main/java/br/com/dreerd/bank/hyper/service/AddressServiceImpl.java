package br.com.dreerd.bank.hyper.service;

import br.com.dreerd.bank.hyper.entity.Address;
import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.repository.AddressRepository;
import br.com.dreerd.bank.hyper.service.common.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDTO> list() {
        return addressRepository
                .findAll()
                .stream()
                .map(Address::toDTO)
                .toList();
    }

    @Override
    public AddressDTO findById(UUID id) {
        Optional<Address> findOne = addressRepository.findById(id);
        return findOne.map(BaseEntity::toDTO).orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Address doesn't found"));
    }

    @Override
    public AddressDTO save(AddressDTO dto) {
        try {
            Address result = addressRepository.save(dto.toEntity());
            return result.toDTO();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public AddressDTO edit(AddressDTO dto) throws JsonProcessingException {
        Optional<Address> foundOne = addressRepository.findById(dto.getId());

        if (!foundOne.isPresent()) {
            log.info("Address not found");
            throw new BusinessException("404", "Conctact not found", "The Address doesn't found", dto);
        }

        Address addressDB = foundOne.get();
        addressDB.updateFields(dto);
        return addressRepository.save(addressDB).toDTO();
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        if (!addressRepository.existsById(id)) {
            throw new BusinessException("404", "Conctact not found", "The Address doesn't found", id);
        }

        addressRepository.deleteById(id);
    }
}
