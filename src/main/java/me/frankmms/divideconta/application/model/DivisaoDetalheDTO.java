package me.frankmms.divideconta.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DivisaoDetalheDTO {
    private String participante;
    private BigDecimal valorItens;
    private BigDecimal proporcao;
    private BigDecimal descontos;
    private BigDecimal acrescimos;
    private BigDecimal valorAPagar;
}
