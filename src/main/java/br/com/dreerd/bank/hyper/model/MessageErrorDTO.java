package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.enums.StatusType;
import br.com.dreerd.bank.hyper.utility.Converter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageErrorDTO {
    private String errorMessage;
    private String localDateTime = LocalDateTime.now().toString();
    private String code;
    private StatusType status;
    private String details;
    private Object payload;

    public MessageErrorDTO(String code, String errorMessage, String details) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.details = details;
        this.localDateTime = LocalDateTime.now().toString();
    }


    public MessageErrorDTO(String details, String code, Object payload, String errorMessage) throws JsonProcessingException {
        this.details = details;
        this.code = code;
        this.payload = Converter.toJSON(payload);
        this.errorMessage = errorMessage;
        this.localDateTime = LocalDateTime.now().toString();
    }

    public MessageErrorDTO(String errorMessage, StatusType code) {
        this.details = messageTreatment(errorMessage);
        this.localDateTime = LocalDateTime.now().toString();
        this.code = code.getCode();
        this.errorMessage = code.getText();
    }

    private String messageTreatment(String message) {
        if (message.contains("CityEnum")) {
            return "Unable to find the city.";
        }
        return message;
    }
}
