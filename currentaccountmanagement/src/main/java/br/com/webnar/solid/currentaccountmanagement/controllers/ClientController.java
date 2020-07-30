package br.com.webnar.solid.currentaccountmanagement.controllers;

import br.com.webnar.solid.currentaccountmanagement.models.Client;
import br.com.webnar.solid.currentaccountmanagement.services.ClientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Cliente")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping(value = "/createClient")
    public ResponseEntity<?> postClient(@RequestBody Client client) {
        Boolean data = this.service.createClient(client);
        return ResponseEntity.ok(data);
    }
}
