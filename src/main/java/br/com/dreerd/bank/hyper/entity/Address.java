package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntity;
import br.com.dreerd.bank.hyper.enums.AddressType;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.utility.Converter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "TBL0002_ADDRESSES")
@AllArgsConstructor
@Data
@Builder
public class Address extends BaseEntity<AddressDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isMain;
    private boolean isActive = true;

    public Address() {
        super(AddressDTO.class);
    }

    public void updateFields(AddressDTO newItem) {
        this.addressType = this.addressType != newItem.getAddressType() ? newItem.getAddressType() : this.addressType;
        this.street = !Objects.equals(this.street, newItem.getStreet()) ? newItem.getStreet() : this.street;
        this.number = !Objects.equals(this.number, newItem.getNumber()) ? newItem.getNumber() : this.number;
        this.complement = !Objects.equals(this.complement, newItem.getComplement()) ? newItem.getComplement() : this.complement;
        this.neighborhood = !Objects.equals(this.neighborhood, newItem.getNeighborhood()) ? newItem.getNeighborhood() : this.neighborhood;
        this.city = !Objects.equals(this.city, newItem.getCity()) ? newItem.getCity() : this.city;
        this.state = !Objects.equals(this.state, newItem.getState()) ? newItem.getState() : this.state;
        this.postalCode = !Objects.equals(this.postalCode, newItem.getPostalCode()) ? newItem.getPostalCode() : this.postalCode;
        this.isMain = this.isMain != newItem.isMain() ? newItem.isMain() : this.isMain;
        this.isActive = this.isActive != newItem.isActive() ? newItem.isActive() : this.isActive;
    }

    @Override
    public AddressDTO toDTO() {
        return Converter.convertTo(this, AddressDTO.class);
    }
}
