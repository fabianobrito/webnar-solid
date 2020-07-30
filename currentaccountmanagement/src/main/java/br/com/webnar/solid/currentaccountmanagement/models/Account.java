package br.com.webnar.solid.currentaccountmanagement.models;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Account {
    private String document;
    private TypeAccount typeAccount;
    private Long accountNumber;
    private BigDecimal accountBalance;
}