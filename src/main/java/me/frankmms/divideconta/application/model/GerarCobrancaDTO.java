package me.frankmms.divideconta.application.model;

import me.frankmms.divideconta.domain.model.FormaPagamento;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class GerarCobrancaDTO {

    private FormaPagamento formaPagamento;

    @NotBlank
    private BigDecimal valor;

}
