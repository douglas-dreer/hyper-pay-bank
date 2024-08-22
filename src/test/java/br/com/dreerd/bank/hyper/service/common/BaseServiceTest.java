package br.com.dreerd.bank.hyper.service.common;


import com.fasterxml.jackson.core.JsonProcessingException;

public interface BaseServiceTest {
    void mustReturnSuccessWhenList();

    void mustReturnSuccessWhenFindById();

    void mustReturnNullWhenFindById();

    void mustReturnBusinessExceptionWhenFindById();

    void mustReturnSuccessWhenSave();

    void mustReturnBusinessExceptionWhenSave();

    void mustReturnSuccessWhenEdit() throws JsonProcessingException;

    void mustReturnBusinessExceptionWhenEdit();

    void mustReturnSuccessWhenDelete() throws JsonProcessingException;

    void mustReturnBusinessExceptionWhenDelete();
}