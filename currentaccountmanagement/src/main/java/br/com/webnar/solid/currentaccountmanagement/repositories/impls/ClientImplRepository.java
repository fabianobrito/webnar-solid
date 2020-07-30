package br.com.webnar.solid.currentaccountmanagement.repositories.impls;

import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class ClientImplRepository implements ClientRepository {

    private static Set<Client> clients = new HashSet<>();

    @Override
    public boolean createClient(Client client) {
        return clients.add(client);
    }

    @Override
    public Optional<Client> dataClient(String document) {
        Optional<Client> clientOp = clients.stream()
                .filter(client -> client.getDocument()
                        .equals(document)).findFirst();
        if (clientOp.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Cliente inexistente!");
        return clientOp;
    }
}
