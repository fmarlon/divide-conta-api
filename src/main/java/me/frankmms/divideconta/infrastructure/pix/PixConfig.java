package me.frankmms.divideconta.infrastructure.pix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("pix")
public class PixConfig {

    private String tipoChave;
    private String chave;
    private String nomeRecebedor;
    private String cidadeRecebedor;

}
