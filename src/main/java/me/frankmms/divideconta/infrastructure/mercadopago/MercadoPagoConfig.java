package me.frankmms.divideconta.infrastructure.mercadopago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("mercadopago")
public class MercadoPagoConfig {

    private String site;
    private String alias;
    private String csrfToken;
    private String csrfCookie;

}
