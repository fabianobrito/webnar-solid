package br.com.webnar.solid.currentaccountmanagement.repositories.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.Account;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.Moviment;
import br.com.webnar.solid.currentaccountmanagement.repositories.AccountRepository;
import br.com.webnar.solid.currentaccountmanagement.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;

@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountImplRepository implements AccountRepository {

    private static List<Account> accounts = new ArrayList<>();
    private static Set<BankStatement> statement = new HashSet<>();
    private static long accountNumberCount = 10000;
    private static final String ERRORACCOUNT = "Conta inexistente!";

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Account dataAccount(long accountNumber) {
        Optional<Account> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ERRORACCOUNT);
        return accountOp.get();
    }

    @Override
    public long returnBankbalance(long accountNumber) {
        Optional<Account> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ERRORACCOUNT);
        return accountOp.get().getAccountNumber();
    }

    @Override
    public void movimentAccount(Long accountNumber, BigDecimal value, TypeMoviment typeMoviment) {

        List<Moviment> moviments = new ArrayList<>();
        Optional<Account> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();

        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ERRORACCOUNT);

        Account account = accountOp.get();
        int index = accounts.indexOf(account);
        account.setAccountBalance(value);

        accounts.remove(index);
        accounts.add(account);

        Optional<BankStatement> bankStatementOp = statement.stream()
                .filter(stat -> stat.getAccountNumber()
                        .equals(accountNumber)).findFirst();

        if (bankStatementOp.isPresent()) {
            moviments = bankStatementOp.get().getMoviment();
        }

        Moviment moviment = new Moviment();
        moviment.setType(typeMoviment);
        moviment.setValue(value);
        moviments.add(moviment);

        BankStatement st = new BankStatement();
        st.setMoviment(moviments);
        st.setAccountNumber(accountNumber);

        statement.removeIf(sta -> sta.getAccountNumber().equals(accountNumber));
        statement.add(st);
    }

    @Override
    public Account openAccount(Account account) {
        if (!StringUtils.isEmpty(account.getDocument())) {

            Optional<Client> client = this.clientRepository.dataClient(account.getDocument());

            if (client.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "É necessário um cliente cadastrado para que sua conta seja aberta!");
            }
        }

        account.setAccountNumber(accountNumberCount);
        accountNumberCount++;
        accounts.add(account);
        return account;
    }

    @Override
    public boolean removeAccount(long accountNumber) {
        Optional<Account> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();

        if (accountOp.isPresent()) {
            Account account = accountOp.get();
            if (account.getAccountBalance().compareTo(BigDecimal.ZERO) != 0) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Não pode deletar uma conta com saldo diferente de 0!");
            }
        }

        accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
        return true;
    }

    @Override
    public List<Account> dataAccounts() {
        return accounts;
    }

    @Override
    public BankStatement getBankStatement(long accountNumber) {
        Optional<BankStatement> bankStatement = statement.stream()
                .filter(stat -> stat.getAccountNumber()
                        .equals(accountNumber)).findFirst();
        if (bankStatement.isPresent())
            return bankStatement.get();

        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                "Não possui movimentação!");
    }
}
