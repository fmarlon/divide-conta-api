package me.frankmms.divideconta.application.model;

import me.frankmms.divideconta.domain.model.TipoValor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

    private String descricao;
    private TipoValor tipoValor;
    private BigDecimal valor;

}
