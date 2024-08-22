package br.com.dreerd.bank.hyper.exception;

import br.com.dreerd.bank.hyper.model.MessageErrorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // Defina um status padrão
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST; // Defina um status padrão
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.status = HttpStatus.BAD_REQUEST; // Defina um status padrão
    }

    public BusinessException(String code, String message, String detailMessage, Object payload) throws JsonProcessingException {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // Defina um status padrão
    }

    public HttpStatus getStatus() {
        return status;
    }
}
