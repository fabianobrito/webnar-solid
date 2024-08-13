package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.OpenAccountTypeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Qualifier("accountPJService")
public class PjAccountPerfilService implements OpenAccountTypeService {

    @Override
    public TypeAccount returnIncome(BigDecimal income) throws Exception {
        if (income.compareTo(BigDecimal.valueOf(10000)) >= 0) {
            throw new Exception("Essa conta não pode ser empresarial");
        }
        return TypeAccount.EMPRESARIAL;
    }
}
