package me.frankmms.divideconta.infrastructure.mercadopago;

import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Tag("ExperimentalTest")
public class LinkMercadoPagoApiClientTest {

    MercadoPagoConfig config = new MercadoPagoConfig();

    @BeforeEach
    void init() {
        config.setCsrfToken("7V3zaKKq-AUWY6boIgZBo9vgEWbo7mqotDLY");
        config.setCsrfCookie("_csrf=_LzXBNjb7kAvKGkuTZp0lQlu");
    }

    @Test
    public void test() {
        var apiClient = createApiClient();

        var requestDTO = new LinkMercadoPagoApiClient.CreateIntentRequestDTO("frankmms", new BigDecimal("15.25"), "MLB");
        var responseDTO = apiClient.createIntent(requestDTO.getAlias(), requestDTO);

        log.info("createIntentResponse: {}", responseDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUrl())
                .startsWith("http://mpago.la/")
                .containsPattern("http\\://mpago.la/\\w+");
    }

    private LinkMercadoPagoApiClient createApiClient() {
        LinkMercadoPagoApiClient apiClient = Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(() -> new HttpMessageConverters()))
                .decoder(new SpringDecoder(() -> new HttpMessageConverters(), Mockito.mock(ObjectProvider.class)))
                .logger(new Slf4jLogger(LinkMercadoPagoApiClient.class))
                .requestInterceptor(new LinkMercadoPagoApiClient.ConfigureHeaders(config))
                .logLevel(Logger.Level.FULL)
                .target(LinkMercadoPagoApiClient.class, "https://link.mercadopago.com.br/api");
        return apiClient;
    }

}
