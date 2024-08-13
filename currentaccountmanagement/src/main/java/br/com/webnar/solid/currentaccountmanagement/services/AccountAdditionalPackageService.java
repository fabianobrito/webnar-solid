package br.com.webnar.solid.currentaccountmanagement.services;

import java.math.BigDecimal;

public interface AccountAdditionalPackageService {

    void additionalCard(String number);

    boolean bankLoans(BigDecimal value);
}
