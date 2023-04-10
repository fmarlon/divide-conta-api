package me.frankmms.divideconta.infrastructure.spring;

import me.frankmms.divideconta.application.ContaAppService;
import me.frankmms.divideconta.application.PagamentoAppService;
import me.frankmms.divideconta.domain.DomainRegistry;
import me.frankmms.divideconta.domain.GeradorDadosPagamento;
import me.frankmms.divideconta.domain.ServiceProvider;
import me.frankmms.divideconta.infrastructure.mercadopago.GeradorLinkMercadoPago;
import me.frankmms.divideconta.infrastructure.mercadopago.LinkMercadoPagoApiClient;
import me.frankmms.divideconta.infrastructure.mercadopago.MercadoPagoConfig;
import me.frankmms.divideconta.infrastructure.pix.GeradorPix;
import me.frankmms.divideconta.infrastructure.pix.PixConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public ContaAppService contaAppService() {
        return new ContaAppService();
    }

    @Bean
    public PagamentoAppService pagamentoAppService() {
        return new PagamentoAppService();
    }

    @Bean @Qualifier("MERCADO_PAGO")
    public GeradorDadosPagamento geradorDadosPagamentoMercadoPago(LinkMercadoPagoApiClient mercadoPagoApiClient, MercadoPagoConfig mercadoPagoConfig) {
        return new GeradorLinkMercadoPago(mercadoPagoApiClient, mercadoPagoConfig);
    }

    @Bean @Qualifier("PIX")
    public GeradorDadosPagamento geradorDadosPagamentoPix(PixConfig pixConfig) {
        return new GeradorPix(pixConfig);
    }

    @Bean
    public ServiceProvider serviceProvider(ApplicationContext applicationContext) {
        ServiceProvider serviceProvider = new SpringServiceProvider(applicationContext);

        DomainRegistry.setProvider(serviceProvider);

        return serviceProvider;
    }

}
