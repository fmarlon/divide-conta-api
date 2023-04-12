package me.frankmms.divideconta.domain.model;

import me.frankmms.divideconta.domain.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Conta {

    public List<Item> itens = new ArrayList<>();

    private Dinheiro totalItens = Dinheiro.of(0);

    private List<Transacao> acrescimos = new ArrayList<>();
    private List<Transacao> descontos = new ArrayList<>();

    private Dinheiro totalDescontos;

    private Dinheiro totalAcrescimos;

    private Dinheiro totalAPagar;

    private Map<Participante, Divisao> divisoes = new LinkedHashMap<>();

    public Dinheiro getTotalAPagar() {
        return totalAPagar;
    }

    public void addItem(String descricao, Double valor, Participante participante) {
        itens.add(new Item(descricao, Dinheiro.of(valor), participante));
    }

    public void addAcrescimo(Transacao transacao) {
        acrescimos.add(transacao);
    }

    public void addDesconto(Transacao transacao) {
        descontos.add(transacao);
    }

    public void addAcrescimo(String descricao, Dinheiro valor) {
        addAcrescimo(Transacao.valorReal(descricao, valor.decimalValue()));
    }

    public void addDesconto(String descricao, Dinheiro valor) {
        addDesconto(Transacao.valorReal(descricao, valor.decimalValue()));
    }

    public void calcularDivisao() {
        totalItens = Dinheiro.sum(itens, Item::getValor);
        totalAcrescimos = Dinheiro.sum(acrescimos, it -> it.getValorReal(totalItens));
        totalDescontos = Dinheiro.sum(descontos, it -> it.getValorReal(totalItens));

        if (totalItens.equals(Dinheiro.ZERO)) {
            throw new DomainException("Total dos itens deve ser maior que zero");
        }

        this.totalAPagar = totalItens.subtract(totalDescontos).add(totalAcrescimos);

        divisoes.clear();

        var totaisPorParticipante =  itens.stream()
                .collect(Collectors.groupingBy(Item::getParticipante, Collectors.mapping(Item::getValor, Dinheiro.summingUp())));

        totaisPorParticipante.forEach((participante, valorTotal) -> {
            Divisao divisao = new Divisao();

            var proporcao = valorTotal.decimalValue().divide(totalItens.decimalValue(), 4, RoundingMode.HALF_EVEN);

            divisao.setParticipante(participante);
            divisao.setValorItens(valorTotal);
            divisao.setProporcao(proporcao);
            divisao.setAcrescimoProporcional(totalAcrescimos.multiply(proporcao).setScale(2));
            divisao.setDescontoProporcional(totalDescontos.multiply(proporcao).setScale(2));
            divisao.calcularValorAPagar();

            divisoes.put(divisao.getParticipante(), divisao);
        });

    }

    public Dinheiro getTotalItens() {
        return totalItens;
    }

    public Dinheiro getValorAPagarPara(Participante participante) {
        Divisao divisao = divisoes.get(participante);
        return divisao == null ? null : divisao.getValorAPagar();
    }

    public Dinheiro getTotalAcrescimos() {
        return totalAcrescimos;
    }

    public Dinheiro getTotalDescontos() {
        return totalDescontos;
    }

    public Collection<Divisao> getDivisoes() {
        return divisoes.values();
    }

    public String printExtratoDivisao() {
        var sb = new StringBuilder();

        sb.append("-------------------------------------------------------------------------------\n");
        sb.append("| SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|\n");
        sb.append("-------------------------------------------------------------------------------\n");

        for (var divisao : divisoes.values()) {
            var line = String.format(
                "| %-12s | %10s | %10s | %10s | %10s | %10s |\n",
                divisao.getParticipante().getNome(),
                divisao.getValorItens(),
                divisao.getProporcao(),
                divisao.getAcrescimoProporcional(),
                divisao.getDescontoProporcional(),
                divisao.getValorAPagar()
            );
            sb.append(line);
        }

        sb.append("-------------------------------------------------------------------------------\n");
        var finalLine = String.format(
            "| %-12s | %10s | %10s | %10s | %10s | %10s |\n",
            "TOTAL", totalItens, "", totalAcrescimos, totalDescontos, totalAPagar
        );
        sb.append(finalLine);
        sb.append("-------------------------------------------------------------------------------\n");

        return sb.toString();
    }

}
