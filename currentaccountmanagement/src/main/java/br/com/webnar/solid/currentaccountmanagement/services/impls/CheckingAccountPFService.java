package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.dtos.AccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.repositories.AccountRepository;
import br.com.webnar.solid.currentaccountmanagement.services.AccountManagementService;
import br.com.webnar.solid.currentaccountmanagement.services.BaseAccountService;
import br.com.webnar.solid.currentaccountmanagement.services.OpenAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckingAccountPFService implements BaseAccountService, AccountManagementService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    @Qualifier("accountPFService")
    private OpenAccountTypeService openAccountTypeService;

    @Override
    public Account findAccount(long accountNumber) {
        return this.accountRepository.dataAccount(accountNumber);
    }

    @Override
    public boolean removeAccount(long accountNumber) {
        return this.accountRepository.removeAccount(accountNumber);
    }

    @Override
    public Account openAccount(AccountInDTO dto) {

        Account account = Account.builder()
                .document(dto.getDocument())
                .accountBalance(dto.getAccountBalance())
                .build();

        AccountStrategy accountStrategy = new AccountStrategy(openAccountTypeService);
        TypeAccount typeAccount = accountStrategy.getTypeAccount(dto.getAccountBalance());
        account.setTypeAccount(typeAccount);

        return this.accountRepository.openAccount(account);
    }

    @Override
    public void deposit(BigDecimal valueDeposit, long accountNumber) {
        Account balance = this.accountRepository.dataAccount(accountNumber);
        balance.setAccountBalance(balance.getAccountBalance().add(valueDeposit));

        this.accountRepository.movimentAccount(balance.getAccountNumber(),
                balance.getAccountBalance(), TypeMoviment.CREDITO);
    }

    @Override
    public void accountWithdrawal(BigDecimal valueRedraw, long accountNumber) {
        Account balance = this.accountRepository.dataAccount(accountNumber);
        balance.setAccountBalance(balance.getAccountBalance().subtract(valueRedraw));
        this.accountRepository.movimentAccount(balance.getAccountNumber(),
                balance.getAccountBalance(), TypeMoviment.DEBITO);
    }

    @Override
    public BankStatement bankStatement(long accountNumber) {
        return this.accountRepository.getBankStatement(accountNumber);
    }
}
