package br.com.webnar.solid.currentaccountmanagement.repositories.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.Moviment;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;
import br.com.webnar.solid.currentaccountmanagement.repositories.AccountNotSolidRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Essa classe quebra os padrões de SRP, OCP e LSP
 */
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountNotSolidImplRepository implements AccountNotSolidRepository {

    private static List<NoSolidAccount> accounts = new ArrayList<>();
    private static Set<BankStatement> statement = new HashSet<>();
    private static Set<Client> clients = new HashSet<>();
    private static long accountNumberCount = 10000;

    @Override
    public boolean createClient(Client client) {
        return clients.add(client);
    }

    @Override
    public Optional<Client> dataClient(String document) {
        Optional<Client> clientOp = clients.stream()
                .filter(client -> client.getDocument()
                        .equals(document)).findFirst();
        if (clientOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cliente inexistente!");
        return clientOp;
    }

    @Override
    public NoSolidAccount dataAccount(long accountNumber) {
        Optional<NoSolidAccount> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Conta inexistente!");
        return accountOp.get();
    }

    @Override
    public long returnBankbalance(long accountNumber) {
        Optional<NoSolidAccount> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();
        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Conta inexistente!");
        return accountOp.get().getAccountNumber();
    }

    @Override
    public void movimentAccount(Long accountNumber, BigDecimal value, TypeMoviment typeMoviment) {

        List<Moviment> moviments = new ArrayList<>();
        Optional<NoSolidAccount> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();

        if (accountOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Conta inexistente!");

        NoSolidAccount account = accountOp.get();
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
    public NoSolidAccount openAccount(NoSolidAccount account) {
        if (!StringUtils.isEmpty(account.getCpf())) {

            Optional<Client> client = this.dataClient(account.getCpf());

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
        Optional<NoSolidAccount> accountOp = accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst();

        if (accountOp.get().getAccountBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Não pode deletar uma conta com saldo diferente de 0!");
        }

        accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
        return true;
    }

    @Override
    public List<NoSolidAccount> dataAccounts() {
        return accounts;
    }

    @Override
    public BankStatement getBankStatement(long accountNumber) {
        return statement.stream()
                .filter(stat -> stat.getAccountNumber()
                        .equals(accountNumber)).findFirst().get();
    }
}
