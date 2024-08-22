package br.com.dreerd.bank.hyper.controller;

import br.com.dreerd.bank.hyper.controller.common.BaseController;
import br.com.dreerd.bank.hyper.entity.Account;
import br.com.dreerd.bank.hyper.enums.TransitionType;
import br.com.dreerd.bank.hyper.exception.BusinessException;
import br.com.dreerd.bank.hyper.model.AccountDTO;
import br.com.dreerd.bank.hyper.model.BankTransitionDTO;
import br.com.dreerd.bank.hyper.service.AccountTransitionServiceImpl;
import br.com.dreerd.bank.hyper.service.common.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/accounts")
@Log4j2
public class AccountController extends BaseController<Account, AccountDTO, AccountService> {
    private final AccountTransitionServiceImpl accountTransitionService;

    protected AccountController(AccountService service, AccountTransitionServiceImpl accountTransitionService) {
        super(service);
        this.accountTransitionService = accountTransitionService;
    }

    @GetMapping(params = {"accountNumber"})
    public ResponseEntity<AccountDTO> findByAccountNumber(@RequestParam("accountNumber") Long accountNumber) {
        try {
            AccountDTO result = this.service.findByNumber(accountNumber);
            return ResponseEntity.ok(result);
        } catch (BusinessException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("accountId") UUID accountId) {
        try {
            AccountDTO account = this.service.findById(accountId);
            return ResponseEntity.ok(account.getBalance());
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            throw e;
        }
    }

    @PostMapping("/{account}/deposit")
    public ResponseEntity<String> deposit(@PathVariable("account") UUID account, @RequestBody BankTransitionDTO transition) {
        try {
            transition.setDestinationAccountId(account);
            transition.setTransitionType(TransitionType.DEPOSIT);

            accountTransitionService.deposit(transition);

            return ResponseEntity.ok("Deposit operation successfully carried out");
        } catch (BusinessException e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @PostMapping("/{account}/withdrawal")
    public ResponseEntity<String> withdrawal(@PathVariable("account") UUID account, @RequestBody BankTransitionDTO transition) throws JsonProcessingException {
        try {
            transition.setOriginAccountId(account);
            transition.setTransitionType(TransitionType.WITHDRAWAL);
            accountTransitionService.withdrawal(transition);
            return ResponseEntity.ok("Withdrawal operation successfully carried out");
        } catch (BusinessException e) {
            log.info(e.getMessage());
            throw e;
        }


    }

    @PostMapping("/{account}/internal-transfer")
    public ResponseEntity<String> internalTransfer(@PathVariable("account") UUID account, @RequestBody BankTransitionDTO transition) {
        try {
            transition.setOriginAccountId(account);
            transition.setTransitionType(TransitionType.INTERNAL_TRANSFER);

            accountTransitionService.internalTransfer(transition);
            return ResponseEntity.ok("Internal transfer operation successfully carried out");
        } catch (BusinessException e) {
            log.info(e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
