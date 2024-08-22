package br.com.dreerd.bank.hyper.service.common;

import br.com.dreerd.bank.hyper.entity.Account;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface AccountService extends BaseService<Account, AccountDTO>  {
    List<AccountDTO> list();

    AccountDTO findById(UUID id);
    AccountDTO findByNumber(Long number);

    AccountDTO save(AccountDTO dto);

    AccountDTO edit(AccountDTO dto) throws JsonProcessingException;

    void delete(UUID id) throws JsonProcessingException;
}
