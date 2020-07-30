package br.com.webnar.solid.currentaccountmanagement.services;

import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;

import java.util.Optional;

public interface ClientService {

    boolean createClient(Client client);

    Optional<Client> findClient(NoSolidAccount account);
}
