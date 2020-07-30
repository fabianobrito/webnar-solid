package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.dtos.NoSolidAccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Essa interface quebra os padrões de SRP e ISP
 */
public interface AccountManagementWithoutSolidService {

    boolean createClient(Client client);

    Optional<Client> findClient(NoSolidAccount account);

    NoSolidAccount openAccount(NoSolidAccountInDTO account);

    void deposit(BigDecimal valueDeposit, long accountNumber);

    void accountWithdrawal(BigDecimal valueRedraw, long accountNumber);

    BankStatement bankStatement(long accountNumber);

    NoSolidAccount findAccount(long accountNumber);

    boolean removeAccount(long accountNumber);

    boolean billPayment(String barCode);

    String smsMessage(long accountNumber);
}
