package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.OpenAccountTypeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Qualifier("accountPJService")
public class AccountPJService implements OpenAccountTypeService {

    @Override
    public TypeAccount returnIncome(BigDecimal income) {

        return TypeAccount.EMPRESARIAL;
    }
}
