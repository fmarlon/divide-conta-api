package me.frankmms.divideconta.domain.model;

import lombok.Getter;
import me.frankmms.divideconta.domain.DomainRegistry;
import me.frankmms.divideconta.domain.GeradorDadosPagamento;
import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Cobranca {

    private FormaPagamento formaPagamento;
    private String nomePagador;
    private Dinheiro valor;

    private Map<String, String> dadosParaPagamento = new LinkedHashMap<>();

    public Cobranca(FormaPagamento formaPagamento, String nomePagador, Dinheiro valor) {
        Validate.notNull(formaPagamento, "Forma de pagamento deve ser informada");
        Validate.notNull(valor, "Valor da cobrança deve ser informado");
        Validate.isTrue(valor.doubleValue() > 0, "O valor cobrado deve ser maior que zero");

        this.formaPagamento = formaPagamento;
        this.nomePagador = nomePagador;
        this.valor = valor;
    }

    public void gerarDadosParaPagamento() {
        var gerador = DomainRegistry.geradorDadosPagamento(formaPagamento);

        this.dadosParaPagamento = gerador.gerar(formaPagamento, valor);
    }

}
