package br.com.webnar.solid.currentaccountmanagement.controllers;

import br.com.webnar.solid.currentaccountmanagement.dtos.AccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.services.AccountAdditionalPackageService;
import br.com.webnar.solid.currentaccountmanagement.services.AccountManagementService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Api(tags = "Conta")
public class AccountController {

    @Autowired
    private AccountManagementService accountManagementService;

    @Autowired
    private AccountAdditionalPackageService baseAccountService;

    @PostMapping(value = "/openAccount")
    public ResponseEntity<Account> post(
            @RequestBody AccountInDTO account) throws Exception {
        Account data = this.accountManagementService.openAccount(account);
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/bankStatement/{accountNumber}")
    public ResponseEntity<BankStatement> getBankStatement(@PathVariable long accountNumber) {
        BankStatement data = this.accountManagementService.bankStatement(accountNumber);
        return ResponseEntity.ok(data);
    }

    @GetMapping(value = "/findAccount/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable long accountNumber) {
        Account data = this.accountManagementService.findAccount(accountNumber);
        return ResponseEntity.ok(data);
    }

    @PostMapping(value = "/deposit/{accountNumber}")
    public ResponseEntity<String> deposit(@PathVariable long accountNumber,
                                          @RequestBody(required = true) BigDecimal value) {
        this.accountManagementService.deposit(value, accountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/accountWithdrawal/{accountNumber}")
    public ResponseEntity<String> accountWithdrawal(@PathVariable long accountNumber,
                                                    @RequestBody(required = true) BigDecimal value) {
        this.accountManagementService.bankWithdrawal(value, accountNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/removeAccount/{accountNumber}")
    public ResponseEntity<Boolean> removeAccount(
            @PathVariable long accountNumber) {
        boolean data = this.accountManagementService.removeAccount(accountNumber);
        return ResponseEntity.ok(data);
    }
}
