package br.com.webnar.solid.currentaccountmanagement.repositories;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Essa interface quebra os padrões de SRP e ISP
 */
public interface AccountNotSolidRepository {

    boolean createClient(Client client);

    Optional<Client> dataClient(String document);

    NoSolidAccount dataAccount(long accountNumber);

    long returnBankbalance(long accountNumber);

    void movimentAccount(Long accountNumber, BigDecimal value, TypeMoviment typeMoviment);

    NoSolidAccount openAccount(NoSolidAccount account);

    boolean removeAccount(long accountNumber);

    List<NoSolidAccount> dataAccounts();

    BankStatement getBankStatement(long accountNumber);
}
