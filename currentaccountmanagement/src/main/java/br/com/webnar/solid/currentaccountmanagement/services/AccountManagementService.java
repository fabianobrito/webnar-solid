package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.models.Account;

public interface AccountManagementService {

    Account findAccount(long accountNumber);

    boolean removeAccount(long accountNumber);
}
