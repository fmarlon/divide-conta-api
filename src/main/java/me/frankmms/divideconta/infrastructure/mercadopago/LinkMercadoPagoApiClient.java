package me.frankmms.divideconta.infrastructure.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "link-mercadopago-api", url = "${mercadopago.url-api}", configuration = LinkMercadoPagoApiClient.FeignConfiguration.class)
public interface LinkMercadoPagoApiClient {

    @PostMapping("/alias/{alias}/create-intent")
    CreateIntentResponseDTO createIntent(@PathVariable String alias, @RequestBody CreateIntentRequestDTO request);

    @AllArgsConstructor
    class ConfigureHeaders implements RequestInterceptor {

        MercadoPagoConfig config;

        @Override
        public void apply(RequestTemplate requestTemplate) {
            requestTemplate
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json;charset=utf-8")
                    .header("x-csrf-token", config.getCsrfToken())
                    .header("Cookie", config.getCsrfCookie())
            ;
        }
    }

    @Configuration
    class FeignConfiguration {

        @Bean
        public ConfigureHeaders configureHeadersInterceptor(MercadoPagoConfig config) {
            return new ConfigureHeaders(config);
        }
    }

    @Data
    @AllArgsConstructor
    class CreateIntentRequestDTO {
        private String alias;
        @JsonProperty("unit_price")
        private BigDecimal unitPrice;
        private String site;
    }

    @Data
    class CreateIntentResponseDTO {
        private String url;
    }

}
