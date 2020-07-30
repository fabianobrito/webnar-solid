package br.com.webnar.solid.currentaccountmanagement.services.impls;

import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.models.NoSolidAccount;
import br.com.webnar.solid.currentaccountmanagement.repositories.ClientRepository;
import br.com.webnar.solid.currentaccountmanagement.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientImplService implements ClientService {

    @Autowired
    private ClientRepository repository;

    @Override
    public boolean createClient(Client client) {

        return this.repository.createClient(client);
    }

    @Override
    public Optional<Client> findClient(NoSolidAccount account) {

        return this.repository.dataClient(account.getCpf());
    }
}
