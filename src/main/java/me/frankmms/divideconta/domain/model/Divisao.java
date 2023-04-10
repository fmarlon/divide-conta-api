package me.frankmms.divideconta.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PROTECTED)
public class Divisao {

    private Participante participante;
    private Dinheiro valorItens;
    private BigDecimal proporcao;
    private Dinheiro descontoProporcional;
    private Dinheiro acrescimoProporcional;
    private Dinheiro valorAPagar;

    protected void calcularValorAPagar() {
        this.valorAPagar = valorItens.subtract(descontoProporcional).add(acrescimoProporcional);
    }

}
