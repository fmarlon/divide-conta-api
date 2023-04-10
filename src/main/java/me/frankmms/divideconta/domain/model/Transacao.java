package me.frankmms.divideconta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Transacao {

    private String descricao;
    private TipoValor tipoValor;
    private BigDecimal valor;

    public static Transacao valorReal(String descricao, Number valor) {
        return new Transacao(descricao, TipoValor.DINHEIRO, numberToBigDecimal(valor));
    }

    public static Transacao porcentagem(String descricao, Number valor) {
        return new Transacao(descricao, TipoValor.PORCENTAGEM, numberToBigDecimal(valor));
    }

    public Dinheiro getValorReal(Dinheiro totalBase) {
        if (tipoValor == TipoValor.PORCENTAGEM) {
            return totalBase.multiply(valor.movePointLeft(2));
        } else {
            return Dinheiro.of(valor);
        }
    }

    private static BigDecimal numberToBigDecimal(Number number) {
        return number instanceof BigDecimal ? (BigDecimal) number : new BigDecimal(number.toString());
    }

}
