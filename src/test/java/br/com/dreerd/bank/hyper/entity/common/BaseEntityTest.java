package br.com.dreerd.bank.hyper.entity.common;

public interface BaseEntityTest {
    void mustReturnSuccessWhenInitializeWithAllConstructor();

    void mustReturnSuccessWhenInitializeWithSetters();

    void mustReturnSuccessWhenInitializeWithBuilder();

    void mustReturnSuccessWhenConvertToDTO() throws Exception;

    // void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException;

    // void mustReturnSuccessWhenConterToObject() throws IOException;
    void mustReturnSuccessWhenEquals();
    void mustReturnSuccessWhenHash();
}