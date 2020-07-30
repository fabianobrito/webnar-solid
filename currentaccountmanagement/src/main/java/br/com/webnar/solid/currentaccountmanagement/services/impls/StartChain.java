package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeAccount;
import br.com.webnar.solid.currentaccountmanagement.services.CategoryAccountChain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StartChain {

    private CategoryAccountChain chain;

    public StartChain() {
        this.chain = new CategoryUniversityChain();
        CategoryAccountChain chain2 = new CategoryEmergentChain();
        CategoryAccountChain chain3 = new CategoryPremiumChain();

        this.chain.setNextRequestHandler(chain2);
        chain2.setNextRequestHandler(chain3);
    }

    public TypeAccount execution(BigDecimal value) {
        return this.chain.process(value);
    }
}
