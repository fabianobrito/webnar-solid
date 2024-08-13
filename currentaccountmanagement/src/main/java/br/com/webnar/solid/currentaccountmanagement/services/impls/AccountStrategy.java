package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.OpenAccountTypeService;

import java.math.BigDecimal;

public class AccountStrategy {

    private OpenAccountTypeService openAccountTypeService;

    public AccountStrategy(OpenAccountTypeService openAccountTypeService) {
        this.openAccountTypeService = openAccountTypeService;
    }

    public TypeAccount getTypeAccount(BigDecimal income) throws Exception {

        return openAccountTypeService.returnIncome(income);
    }
}
