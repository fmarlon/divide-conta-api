package me.frankmms.divideconta.domain.model;

import lombok.Getter;
import me.frankmms.divideconta.domain.DomainRegistry;
import me.frankmms.divideconta.domain.GeradorDadosPagamento;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Cobranca {

    private FormaPagamento formaPagamento;
    private String nomePagador;
    private Dinheiro valor;

    private Map<String, String> dadosParaPagamento = new LinkedHashMap<>();

    public Cobranca(FormaPagamento formaPagamento, String nomePagador, Dinheiro valor) {
        this.formaPagamento = formaPagamento;
        this.nomePagador = nomePagador;
        this.valor = valor;
    }

    public void gerarDadosParaPagamento() {
        var gerador = DomainRegistry.geradorDadosPagamento(formaPagamento);

        this.dadosParaPagamento = gerador.gerar(formaPagamento, valor);
    }

}
