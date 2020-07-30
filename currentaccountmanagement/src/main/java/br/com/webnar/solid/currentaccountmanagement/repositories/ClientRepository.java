package br.com.webnar.solid.currentaccountmanagement.repositories;

import br.com.webnar.solid.currentaccountmanagement.models.Client;

import java.util.Optional;

public interface ClientRepository {

    boolean createClient(Client client);

    Optional<Client> dataClient(String document);
}
