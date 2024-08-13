package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.OpenAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Primary
public class PfAccountPerfilService implements OpenAccountTypeService {

    @Autowired
    private StartChain startChain;

    @Override
    public TypeAccount returnIncome(BigDecimal income) {

        return this.startChain.execution(income);
    }
}
