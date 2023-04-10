package me.frankmms.divideconta.domain;

import me.frankmms.divideconta.domain.model.Dinheiro;
import me.frankmms.divideconta.domain.model.FormaPagamento;

import java.util.HashMap;
import java.util.Map;

public interface GeradorDadosPagamento {

    public Map<String, String> gerar(FormaPagamento forma, Dinheiro valor);

}
