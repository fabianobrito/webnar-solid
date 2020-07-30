package br.com.webnar.solid.currentaccountmanagement.models;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NoSolidAccount {
    private String cpf = null;
    private String cnpj = null;
    private TypeAccount typeAccount;
    private Long accountNumber;
    private BigDecimal accountBalance;
}