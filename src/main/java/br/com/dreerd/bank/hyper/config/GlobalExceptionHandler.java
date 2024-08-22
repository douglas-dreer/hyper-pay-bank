package br.com.dreerd.bank.hyper.config;

import br.com.dreerd.bank.hyper.enums.StatusType;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.MessageErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MessageErrorDTO> handleBusinessException(BusinessException e) {
        MessageErrorDTO errorDTO = new MessageErrorDTO(e.getMessage(), StatusType.WARNING);
        HttpStatus status = e.getStatus();

        if (status == HttpStatus.NOT_FOUND) {
            errorDTO.setCode("404");
            errorDTO.setStatus(StatusType.INFO);
        }

        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            errorDTO.setCode("500");
            errorDTO.setStatus(StatusType.ERROR);
        }

        if (status == HttpStatus.BAD_REQUEST) {
            errorDTO.setCode("400");
            errorDTO.setStatus(StatusType.WARNING);
        }

        return new ResponseEntity<>(errorDTO, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageErrorDTO> handleException(Exception e) {
        MessageErrorDTO errorDTO = new MessageErrorDTO(e.getLocalizedMessage(), StatusType.ERROR);
        return new ResponseEntity<>(errorDTO, HttpStatus.OK);
    }
}
