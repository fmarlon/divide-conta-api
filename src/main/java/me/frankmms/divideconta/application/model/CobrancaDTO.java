package me.frankmms.divideconta.application.model;

import me.frankmms.divideconta.domain.model.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CobrancaDTO {

    private FormaPagamento formaPagamento;
    private BigDecimal valor;
    private Map<String, String> dadosParaPagamento;

}
