package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.CategoryAccountChain;

import java.math.BigDecimal;

public class CategoryEmergentChain implements CategoryAccountChain {

    private CategoryAccountChain accountChain;

    @Override
    public void setNextRequestHandler(CategoryAccountChain accountChain) {
        this.accountChain = accountChain;
    }

    @Override
    public TypeAccount process(BigDecimal valeu) {

        if (valeu.compareTo(BigDecimal.valueOf(100)) >= 0
                && valeu.compareTo(BigDecimal.valueOf(1000)) < 0) {
            return TypeAccount.EMERGENTE;
        }

        return this.accountChain.process(valeu);
    }
}
