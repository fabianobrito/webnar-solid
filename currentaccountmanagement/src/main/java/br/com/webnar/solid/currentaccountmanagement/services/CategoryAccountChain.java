package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;

import java.math.BigDecimal;

public interface CategoryAccountChain {

    void setNextRequestHandler(CategoryAccountChain accountChain);

    TypeAccount process(BigDecimal valeu);
}
