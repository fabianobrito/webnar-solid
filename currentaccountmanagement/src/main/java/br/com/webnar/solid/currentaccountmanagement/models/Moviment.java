package br.com.webnar.solid.currentaccountmanagement.models;

import br.com.webnar.solid.currentaccountmanagement.enums.TypeMoviment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Moviment {

    private BigDecimal value;
    private TypeMoviment type;
}
