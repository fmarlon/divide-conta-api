package me.frankmms.divideconta.infrastructure.mercadopago;

import lombok.AllArgsConstructor;
import me.frankmms.divideconta.domain.GeradorDadosPagamento;
import me.frankmms.divideconta.domain.model.Dinheiro;
import me.frankmms.divideconta.domain.model.FormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@AllArgsConstructor
public class GeradorLinkMercadoPago implements GeradorDadosPagamento {

    @Autowired
    LinkMercadoPagoApiClient apiClient;

    @Autowired
    MercadoPagoConfig config;

    @Override
    public Map<String, String> gerar(FormaPagamento forma, Dinheiro valor) {
        LinkMercadoPagoApiClient.CreateIntentRequestDTO requestDTO = new LinkMercadoPagoApiClient.CreateIntentRequestDTO(config.getAlias(), valor.decimalValue(), config.getSite());
        LinkMercadoPagoApiClient.CreateIntentResponseDTO responseDTO = apiClient.createIntent(requestDTO.getAlias(), requestDTO);

        String link = responseDTO.getUrl();

        return Map.of("LINK_PAGAMENTO", link);
    }
}
