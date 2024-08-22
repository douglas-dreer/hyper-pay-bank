package br.com.dreerd.bank.hyper.model;

import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.utility.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BankTransitionDTO {
    private UUID id;
    private UUID originAccountId;
    private UUID destinationAccountId;
    private TransitionType transitionType;
    private BigDecimal amount;

    public String toJSON() throws JsonProcessingException {
        return Converter.toJSON(this);
    }
}
