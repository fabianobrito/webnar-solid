package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;

import java.math.BigDecimal;

public interface OpenAccountTypeService {
    TypeAccount returnIncome(BigDecimal income);
}
