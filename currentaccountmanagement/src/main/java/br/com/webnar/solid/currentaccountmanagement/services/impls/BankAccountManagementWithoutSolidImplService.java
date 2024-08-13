package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.dtos.NoSolidAccountInDTO;
import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import br.com.webnar.solid.currentaccountmanagement.models.BankStatement;
import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;
import br.com.webnar.solid.currentaccountmanagement.repositories.AccountNotSolidRepository;
import br.com.webnar.solid.currentaccountmanagement.services.AccountManagementWithoutSolidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Essa classe quebra os padrões de SRP, OCP e LSP
 */
@Service
@Qualifier("benchWithoutUsingSolidImplService")
public class BankAccountManagementWithoutSolidImplService implements AccountManagementWithoutSolidService {

    @Autowired
    private AccountNotSolidRepository accountRepository;

    @Override
    public boolean createClient(Client client) {
        return false;
    }

    @Override
    public Optional<Client> findClient(NoSolidAccount account) {
        return Optional.empty();
    }

    @Override
    public NoSolidAccount openAccount(NoSolidAccountInDTO accountDTO) {

        NoSolidAccount account = NoSolidAccount.builder()
                .cnpj(accountDTO.getCnpj())
                .cpf(accountDTO.getCpf())
                .accountBalance(accountDTO.getAccountBalance())
                .build();

        if (!StringUtils.isEmpty(account.getCpf())) {
            if (account.getAccountBalance().compareTo(BigDecimal.valueOf(50)) < 0) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Cliente precisa de deposito mínimo para criar conta");
            }

            if (account.getAccountBalance().compareTo(BigDecimal.valueOf(50)) >= 0
                    && account.getAccountBalance().compareTo(BigDecimal.valueOf(100)) < 0) {
                account.setTypeAccount(TypeAccount.UNIVERSITARIA);
            }

            if (account.getAccountBalance().compareTo(BigDecimal.valueOf(100)) >= 0
                    && account.getAccountBalance().compareTo(BigDecimal.valueOf(1000)) < 0) {
                account.setTypeAccount(TypeAccount.EMERGENTE);
            }

            if (account.getAccountBalance().compareTo(BigDecimal.valueOf(1000)) >= 0) {
                account.setTypeAccount(TypeAccount.PREMIUM);
            }
        }

        if (!StringUtils.isEmpty(account.getCnpj())) {
            if (account.getAccountBalance().compareTo(BigDecimal.valueOf(500)) < 1) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Cliente precisa de deposito mínimo para criar conta");
            }
            account.setTypeAccount(TypeAccount.EMPRESARIAL);
        }

        return this.accountRepository.openAccount(account);
    }

    @Override
    public void deposit(BigDecimal valueDeposit, long accountNumber) {

        NoSolidAccount balance = this.accountRepository.dataAccount(accountNumber);
        balance.setAccountBalance(balance.getAccountBalance().add(valueDeposit));

        this.accountRepository.movimentAccount(balance.getAccountNumber(),
                balance.getAccountBalance(), TypeMoviment.CREDITO);
    }

    @Override
    public void accountWithdrawal(BigDecimal valueRedraw, long accountNumber) {

        NoSolidAccount balance = this.accountRepository.dataAccount(accountNumber);

        if (balance.getAccountBalance().compareTo(valueRedraw) <= 0)
            balance.setAccountBalance(balance.getAccountBalance().subtract(valueRedraw));
        else
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);

        this.accountRepository.movimentAccount(balance.getAccountNumber(),
                balance.getAccountBalance(), TypeMoviment.DEBITO);
    }

    @Override
    public BankStatement bankStatement(long accountNumber) {
        return this.accountRepository.getBankStatement(accountNumber);
    }

    @Override
    public NoSolidAccount findAccount(long accountNumber) {

        return this.accountRepository.dataAccount(accountNumber);
    }

    @Override
    public boolean removeAccount(long accountNumber) {

        return this.accountRepository.removeAccount(accountNumber);
    }

    @Override
    public boolean billPayment(String barCode) {
        return false;
    }

    @Override
    public String smsMessage(long accountNumber) {
        return null;
    }
}
