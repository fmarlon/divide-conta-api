package me.frankmms.divideconta.application;

import me.frankmms.divideconta.application.model.CobrancaDTO;
import me.frankmms.divideconta.application.model.GerarCobrancaDTO;
import me.frankmms.divideconta.domain.model.Cobranca;
import me.frankmms.divideconta.domain.model.Dinheiro;
import me.frankmms.divideconta.domain.model.FormaPagamento;

import javax.validation.ValidationException;
import java.util.List;

public class PagamentoAppService {

    public List<FormaPagamento> getFormasPagamentoDisponiveis() {
        return List.of(FormaPagamento.PIX, FormaPagamento.MERCADO_PAGO);
    }

    public CobrancaDTO gerarCobranca(GerarCobrancaDTO solicitacao) {
        if (solicitacao.getFormaPagamento() == null) {
            solicitacao.setFormaPagamento(FormaPagamento.MERCADO_PAGO);
        }
        try {
            var cobranca = new Cobranca(solicitacao.getFormaPagamento(), null, Dinheiro.of(solicitacao.getValor()));

            cobranca.gerarDadosParaPagamento();

            return new CobrancaDTO(solicitacao.getFormaPagamento(), solicitacao.getValor(), cobranca.getDadosParaPagamento());
        } catch(IllegalArgumentException e) {
            throw new ValidationException(e.getMessage());
        }

    }

}
