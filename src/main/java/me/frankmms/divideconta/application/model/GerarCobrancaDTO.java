package me.frankmms.divideconta.application.model;

import me.frankmms.divideconta.domain.model.FormaPagamento;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GerarCobrancaDTO {

    private FormaPagamento formaPagamento;
    private BigDecimal valor;

}
