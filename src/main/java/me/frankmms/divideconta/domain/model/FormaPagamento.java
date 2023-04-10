package me.frankmms.divideconta.domain.model;

import java.util.List;

public enum FormaPagamento {

    PIX (List.of("TIPO_CHAVE", "CHAVE", "CODIGO_COPIA_COLA", "QRCODE")),
    MERCADO_PAGO (List.of("LINK_PAGAMENTO"));

    private List<String> dadosPagamento;

    FormaPagamento(List<String> dadosPagamento) {
        this.dadosPagamento = dadosPagamento;
    }

    public List<String> getDadosPagamento() {
        return dadosPagamento;
    }
}
