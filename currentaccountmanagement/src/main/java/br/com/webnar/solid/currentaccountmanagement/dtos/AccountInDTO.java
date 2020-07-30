package br.com.webnar.solid.currentaccountmanagement.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountInDTO {
    private String document;
    private BigDecimal accountBalance;
}
