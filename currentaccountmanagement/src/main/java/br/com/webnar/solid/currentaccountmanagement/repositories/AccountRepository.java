package br.com.webnar.solid.currentaccountmanagement.repositories;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {

    Account dataAccount(long accountNumber);

    long returnBankbalance(long accountNumber);

    void movimentAccount(Long accountNumber, BigDecimal value, TypeMoviment typeMoviment);

    Account openAccount(Account account);

    boolean removeAccount(long accountNumber);

    List<Account> dataAccounts();

    BankStatement getBankStatement(long accountNumber);
}
