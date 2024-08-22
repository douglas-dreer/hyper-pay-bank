package br.com.dreerd.bank.hyper.model.common;

import br.com.dreerd.bank.hyper.enums.ErrorType;
import br.com.dreerd.bank.hyper.utility.GeneratorInformation;

import java.util.List;

public abstract class BaseModelTest extends GeneratorInformation {

    public abstract void mustReturnSuccessWhenInitializeWithAllConstructor();
    public abstract void mustReturnSuccessWhenInitializeWithPartialConstructor();
    public abstract void mustReturnSuccessWhenSetter();
    public abstract void mustReturnSuccessWhenBuilder();

    public abstract <T> void checking(T data);
    public abstract <T> void checking(T data, List<String> fieldList);

    public String createMessage(String field, ErrorType errorType) {
       return String.format(errorType.getMessage(), field);
    }

}
