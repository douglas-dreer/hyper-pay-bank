package br.com.dreerd.bank.hyper.model.common;

import br.com.dreerd.bank.hyper.utility.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Objects;

public abstract class BaseModel<T> implements Identifiable {

    private Class<T> entityClass;

    protected BaseModel(Class<T> entityClass) {
        Objects.requireNonNull(entityClass, "entityClass must not be null");
        this.entityClass = entityClass;
    }

    public T toEntity() {
        return Converter.convertTo(this, entityClass);
    }

    public String toJSON() throws JsonProcessingException {
        return Converter.toJSON(this);
    }

    public T toObject(String dados) throws IOException {
        return Converter.toObject(dados, entityClass);
    }
}