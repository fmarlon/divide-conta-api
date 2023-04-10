package me.frankmms.divideconta.domain;

import me.frankmms.divideconta.domain.model.FormaPagamento;

public class DomainRegistry {

    private static ServiceProvider provider;

    public static GeradorDadosPagamento geradorDadosPagamento(FormaPagamento formaPagamento) {
        return provider.get(GeradorDadosPagamento.class, formaPagamento);
    }

    public static void setProvider(ServiceProvider provider) {
        DomainRegistry.provider = provider;
    }
}
