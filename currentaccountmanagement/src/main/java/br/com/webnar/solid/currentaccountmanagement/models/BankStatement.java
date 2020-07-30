package br.com.webnar.solid.currentaccountmanagement.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BankStatement {

    private Long accountNumber;
    List<Moviment> moviment = new ArrayList<>();
}
