package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.dtos.AccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;

import java.math.BigDecimal;

public interface BaseAccountService {

    Account openAccount(AccountInDTO dto);

    void deposit(BigDecimal valueDeposit, long accountNumber);

    void accountWithdrawal(BigDecimal valueRedraw, long accountNumber);

    BankStatement bankStatement(long accountNumber);
}
