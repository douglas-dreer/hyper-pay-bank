package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.entity.Address;
import br.com.dreerd.bank.hyper.enums.AddressType;
import br.com.dreerd.bank.hyper.model.common.BaseModel;
import br.com.dreerd.bank.hyper.utility.Converter;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AddressDTO extends BaseModel<Address> {
    private UUID id;
    private AddressType addressType;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isMain;
    private boolean isActive = true;

    public AddressDTO() {
        super(Address.class);
    }

    public AddressDTO(UUID id, AddressType addressType, String street, String number, String complement, String neighborhood, String city, String state, String postalCode, String country) {
        super(Address.class);
        this.id = id;
        this.addressType = addressType;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public AddressDTO(UUID id, AddressType addressType, String street, String number, String complement, String neighborhood, String city, String state, String postalCode, String country, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isMain, boolean isActive) {
        super(Address.class);
        this.id = id;
        this.addressType = addressType;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isMain = isMain;
        this.isActive = isActive;
    }

    @Override
    public Address toEntity() {
        return Converter.convertTo(this, Address.class);
    }

}
