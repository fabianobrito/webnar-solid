package br.com.webnar.solid.currentaccountmanagement.models;

import lombok.Data;

@Data
public class Client {

    private String document;
    private String cep;
    private String gender;
    private String motherName;
    private String name;
    private String profession;
}
