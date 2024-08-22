package br.com.dreerd.bank.hyper.controller.common;

public interface BaseControllerTest {
    void mustReturnSuccessWhenList() throws Exception;

    void mustReturnSuccessWhenFindById() throws Exception;

    void mustReturnBusinessExceptionNotFoundWhenFindById() throws Exception;

    void mustReturnSuccessWhenSave() throws Exception;

    void mustReturnBusinessExceptionInternalErrorWhenSave() throws Exception;

    void mustReturnSuccessWhenEdit() throws Exception;

    void mustReturnBusinessExceptionBadRquestWhenEdit() throws Exception;
    void mustReturnBusinessExceptionNotFoundWhenEdit() throws Exception;

    void mustReturnSuccessWhenDelete() throws Exception;

    void mustReturnBusinessExceptionNotFoundWhenDelete() throws Exception;
}
