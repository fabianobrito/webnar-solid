package br.com.webnar.solid.currentaccountmanagement.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NoSolidAccountInDTO {
    private String cpf;
    private String cnpj;
    private BigDecimal accountBalance;
}
