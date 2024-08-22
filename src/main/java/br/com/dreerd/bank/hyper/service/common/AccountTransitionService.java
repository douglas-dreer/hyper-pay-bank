package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.AccountTransation;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.model.AccountTransationDTO;
import br.com.dreerd.bank.hyper.model.BankTransitionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccountTransitionService extends BaseService<AccountTransation, AccountTransationDTO>  {
    List<AccountTransationDTO> list();

    AccountTransationDTO findById(UUID id);
    List<AccountTransationDTO> findByAccountId(UUID accountId);
    List<AccountTransationDTO> findByAccountIdAndDateTransation(UUID accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<AccountTransationDTO> findByAccountIdAndTransationType(UUID accountId, TransitionType transationType);
    AccountTransationDTO save(AccountTransationDTO dto);

    void withdrawal(BankTransitionDTO bankTransition) throws JsonProcessingException;
    void deposit(BankTransitionDTO bankTransition);
    void internalTransfer(BankTransitionDTO bankTransition) throws JsonProcessingException;
    AccountTransationDTO edit(AccountTransationDTO dto) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;
}
