package me.frankmms.divideconta.application;

import me.frankmms.divideconta.application.model.DivisaoDetalheDTO;
import me.frankmms.divideconta.application.model.DivisaoDTO;
import me.frankmms.divideconta.application.model.ContaDTO;
import me.frankmms.divideconta.domain.model.Conta;
import me.frankmms.divideconta.domain.model.Participante;
import me.frankmms.divideconta.domain.model.Transacao;

public class ContaAppService {

    public DivisaoDTO calcularDivisao(ContaDTO contaDTO) {
        Conta conta = criarConta(contaDTO);

        conta.calcularDivisao();

        DivisaoDTO divisaoDTO = preencherDivisaoDTO(conta);

        return divisaoDTO;
    }

    private DivisaoDTO preencherDivisaoDTO(Conta conta) {
        var divisaoDTO = new DivisaoDTO();

        divisaoDTO.setTotalItens(conta.getTotalItens().decimalValue());
        divisaoDTO.setTotalAPagar(conta.getTotalAPagar().decimalValue());
        divisaoDTO.setTotalAcrescimos(conta.getTotalAcrescimos().decimalValue());
        divisaoDTO.setTotalDescontos(conta.getTotalDescontos().decimalValue());

        conta.getDivisoes().forEach(it -> {
            var detalheDTO = new DivisaoDetalheDTO();

            detalheDTO.setParticipante(it.getParticipante().getNome());
            detalheDTO.setValorItens(it.getValorItens().decimalValue());
            detalheDTO.setProporcao(it.getProporcao());
            detalheDTO.setAcrescimos(it.getAcrescimoProporcional().decimalValue());
            detalheDTO.setDescontos(it.getDescontoProporcional().decimalValue());
            detalheDTO.setValorAPagar(it.getValorAPagar().decimalValue());

            divisaoDTO.addDetalhe(detalheDTO);
        });
        return divisaoDTO;
    }

    private Conta criarConta(ContaDTO contaDTO) {
        var conta = new Conta();

        contaDTO.getItens().forEach(it -> {
            conta.addItem(it.getDescricao(), it.getValor().doubleValue(), Participante.of(it.getSolicitante()));
        });
        contaDTO.getAcrescimos().forEach(it -> {
            conta.addAcrescimo(new Transacao(it.getDescricao(), it.getTipoValor(), it.getValor()));
        });
        contaDTO.getDescontos().forEach(it -> {
            conta.addDesconto(new Transacao(it.getDescricao(), it.getTipoValor(), it.getValor()));
        });
        return conta;
    }

}
