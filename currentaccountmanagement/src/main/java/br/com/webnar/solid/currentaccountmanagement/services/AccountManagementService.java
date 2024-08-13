package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.dtos.AccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;

import java.math.BigDecimal;

public interface AccountManagementService {

    Account openAccount(AccountInDTO dto) throws Exception;

    Account findAccount(long accountNumber);

    boolean removeAccount(long accountNumber);

    void deposit(BigDecimal valueDeposit, long accountNumber);

    void bankWithdrawal(BigDecimal valueRedraw, long accountNumber);

    BankStatement bankStatement(long accountNumber);
}
