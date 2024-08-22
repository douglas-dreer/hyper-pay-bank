package br.com.dreerd.bank.hyper.entity;

import br.com.dreerd.bank.hyper.entity.common.BaseEntityTest;
import br.com.dreerd.bank.hyper.enums.AddressType;
import br.com.dreerd.bank.hyper.model.AddressDTO;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressTest extends GeneratorInformation implements BaseEntityTest {
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
    private boolean isActive;

    @BeforeEach
    void setUp() {
        this.id = UUID.randomUUID();
        this.street = "Avenida Brasil";
        this.number = "1500";
        this.complement = "N/A";
        this.neighborhood = "America Latina";
        this.city = "São Paulo";
        this.state = "São Paulo";
        this.postalCode = "80000-000";
        this.country = "Brasil";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isMain = true;
        this.isActive = true;
    }

    @Test
    @Override
    public void mustReturnSuccessWhenInitializeWithAllConstructor() {
        Address result = new Address(
                id, addressType, street, number, complement,
                neighborhood, city, state, postalCode, country,
                createdAt, updatedAt, isMain, isActive
        );

        checking(result);
    }

    @Override
    public void mustReturnSuccessWhenInitializeWithSetters() {

    }

    @Override
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        Address result = Address.builder()
                .id(id).addressType(addressType).street(street)
                .number(number).complement(complement).neighborhood(neighborhood)
                .city(city).state(state).postalCode(postalCode)
                .country(country).createdAt(createdAt).updatedAt(updatedAt)
                .isMain(isMain).isActive(isActive).build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() throws Exception {
        Address entity = createAddress(Address.class);
        assertNotNull(entity.toDTO());
    }

    @Override
    public void mustReturnSuccessWhenEquals() {

    }

    @Override
    public void mustReturnSuccessWhenHash() {

    }

    @Test
    void mustReturnSuccessWhenCreateAddressWithBuilder() {

    }

    @Test
    void mustReturnSuccessWhenCreateAddressUpdateField() {
        Address addressOriginal = Address.builder()
                .id(id).addressType(addressType).street(street)
                .number(number).complement(complement).neighborhood(neighborhood)
                .city(city).state(state).postalCode(postalCode)
                .country(country).createdAt(createdAt).updatedAt(updatedAt)
                .isMain(isMain).isActive(isActive).build();

        AddressDTO addressUpdated = createAddress(AddressDTO.class);

        addressOriginal.updateFields(addressUpdated);
        assertNotNull(addressUpdated);
    }

    private void checking(Address datas){
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The AbilityDTO should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(id, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getNumber(), "The name should not be null"),
                () -> assertEquals(number, datas.getNumber(), "The element name must be the same as the one sent"),
                () -> assertNotNull(datas.getComplement(), "The complement should not be null"),
                () -> assertEquals(complement, datas.getComplement(), "The element complement must be the same as the one sent"),
                () -> assertNotNull(datas.getNeighborhood(), "The neighborhood should not be null"),
                () -> assertEquals(neighborhood, datas.getNeighborhood(), "The element neighborhood must be the same as the one sent"),
                () -> assertNotNull(datas.getCity(), "The city should not be null"),
                () -> assertEquals(city, datas.getCity(), "The element city must be the same as the one sent"),
                () -> assertNotNull(datas.getState(), "The state should not be null"),
                () -> assertEquals(state, datas.getState(), "The element state must be the same as the one sent"),
                () -> assertNotNull(datas.getPostalCode(), "The city should not be null"),
                () -> assertEquals(postalCode, datas.getPostalCode(), "The element postal code must be the same as the one sent"),
                () -> assertNotNull(datas.getCountry(), "The country should not be null"),
                () -> assertEquals(country, datas.getCountry(), "The element country must be the same as the one sent"),
                () -> assertTrue(datas.isActive()),
                () -> assertTrue(datas.isMain())
        );
    }


}
