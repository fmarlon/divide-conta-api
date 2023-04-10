package me.frankmms.divideconta.domain;

import me.frankmms.divideconta.domain.model.Conta;
import me.frankmms.divideconta.domain.model.Dinheiro;
import me.frankmms.divideconta.domain.model.Participante;
import me.frankmms.divideconta.domain.model.Transacao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CalculoDivisaoTests {

    Participante participante1 = Participante.of("Frank");
    Participante participante2 = Participante.of("Gleyson");
    Participante participante3 = Participante.of("Raimundo");

    /**
     * Cenario:
     * <pre>
     * -------------------------------------------------------------------------------
     * | SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|
     * -------------------------------------------------------------------------------
     * | Frank        |      42.00 |     0.8400 |       0.00 |       0.00 |      42.00 |
     * | Gleyson      |       8.00 |     0.1600 |       0.00 |       0.00 |       8.00 |
     * -------------------------------------------------------------------------------
     * | TOTAL        |      50.00 |            |       0.00 |       0.00 |      50.00 |
     * -------------------------------------------------------------------------------
     * </pre>
     */
    @Test
    public void divisaoComUmAmigoSemDescontosEAcrescimos() {
        var conta = new Conta();

        conta.addItem("Hamburger", 40.00, participante1);
        conta.addItem("Sobremesa", 2.00, participante1);
        conta.addItem("Sanduiche", 8.00, participante2);

        conta.calcularDivisao();

        log.info("Extrato divisao: \n{}", conta.printExtratoDivisao());

        assertEquals(2, conta.getDivisoes().size());
        assertEquals(Dinheiro.of(50.00), conta.getTotalAPagar());
        assertEquals(Dinheiro.of(42.00), conta.getValorAPagarPara(participante1));
        assertEquals(Dinheiro.of(8.00), conta.getValorAPagarPara(participante2));

    }

    /**
     * Cenario:
     * <pre>
     * -------------------------------------------------------------------------------
     * | SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|
     * -------------------------------------------------------------------------------
     * | Frank        |      42.00 |     0.8400 |       6.72 |      16.80 |      31.92 |
     * | Gleyson      |       8.00 |     0.1600 |       1.28 |       3.20 |       6.08 |
     * -------------------------------------------------------------------------------
     * | TOTAL        |      50.00 |            |       8.00 |      20.00 |      38.00 |
     * -------------------------------------------------------------------------------
     * </pre>
     */
    @Test
    public void divisaoComUmAmigoComTaxaDeEntregaEDesconto() {
        var conta = new Conta();

        conta.addItem("Hamburger", 40.00, participante1);
        conta.addItem("Sobremesa", 2.00, participante1);
        conta.addItem("Sanduiche", 8.00, participante2);

        conta.addAcrescimo("Taxa Entrega", Dinheiro.of(8.00));
        conta.addDesconto("Desconto", Dinheiro.of(20.00));

        conta.calcularDivisao();

        log.info("Extrato divisao: \n{}", conta.printExtratoDivisao());

        assertEquals(2, conta.getDivisoes().size());
        assertEquals(Dinheiro.of(38.00), conta.getTotalAPagar());
        assertEquals(Dinheiro.of(31.92), conta.getValorAPagarPara(participante1));
        assertEquals(Dinheiro.of(6.08), conta.getValorAPagarPara(participante2));
    }

    /**
     * Cenario:
     * <pre>
     * -------------------------------------------------------------------------------
     * | SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|
     * -------------------------------------------------------------------------------
     * | Frank        |      22.80 |     0.3816 |       5.34 |       0.00 |      28.14 |
     * | Gleyson      |      17.80 |     0.2979 |       4.17 |       0.00 |      21.97 |
     * | Raimundo     |      19.15 |     0.3205 |       4.49 |       0.00 |      23.64 |
     * -------------------------------------------------------------------------------
     * | TOTAL        |      59.75 |            |      14.00 |       0.00 |      73.75 |
     * -------------------------------------------------------------------------------
     * </pre>
     */
    @Test
    public void divisaoComDoisAmigoComTaxaDeEntregaSemDesconto() {
        var conta = new Conta();

        conta.addItem("Hamburger", 18.30, participante1);
        conta.addItem("Sobremesa", 4.50, participante1);
        conta.addItem("Sanduiche", 12.50, participante2);
        conta.addItem("Refrigerante", 5.30, participante2);
        conta.addItem("Salgado", 10.72, participante3);
        conta.addItem("Suco", 8.43, participante3);

        conta.addAcrescimo("Entrega", Dinheiro.of(14.00));

        conta.calcularDivisao();

        log.info("Extrato divisao: \n{}", conta.printExtratoDivisao());

        assertEquals(Dinheiro.of(73.75), conta.getTotalAPagar());
        assertEquals(Dinheiro.of(28.14), conta.getValorAPagarPara(participante1));
        assertEquals(3, conta.getDivisoes().size());
        assertEquals(Dinheiro.of(21.97), conta.getValorAPagarPara(participante2));
        assertEquals(Dinheiro.of(23.64), conta.getValorAPagarPara(participante3));
    }

    /**
     * Cenário:
     * <pre>
     * -------------------------------------------------------------------------------
     * | SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|
     * -------------------------------------------------------------------------------
     * | Frank        |      22.80 |     0.3816 |       5.34 |       1.14 |      27.00 |
     * | Gleyson      |      17.80 |     0.2979 |       4.17 |       0.89 |      21.08 |
     * | Raimundo     |      19.15 |     0.3205 |       4.49 |       0.96 |      22.68 |
     * -------------------------------------------------------------------------------
     * | TOTAL        |      59.75 |            |      14.00 |       2.99 |      70.76 |
     * -------------------------------------------------------------------------------
     * </pre>
     */
    @Test
    public void divisaoComDoisAmigoComTaxaDeEntregaEDesconto() {
        var conta = new Conta();

        conta.addItem("Hamburger", 18.30, participante1);
        conta.addItem("Sobremesa", 4.50, participante1);
        conta.addItem("Sanduiche", 12.50, participante2);
        conta.addItem("Refrigerante", 5.30, participante2);
        conta.addItem("Salgado", 10.72, participante3);
        conta.addItem("Suco", 8.43, participante3);

        conta.addAcrescimo("Entrega", Dinheiro.of(14.00));
        conta.addDesconto("Desconto", Dinheiro.of(2.99));

        conta.calcularDivisao();

        log.info("Extrato divisao: \n{}", conta.printExtratoDivisao());

        assertEquals(3, conta.getDivisoes().size());
        assertEquals(Dinheiro.of(70.76), conta.getTotalAPagar());
        assertEquals(Dinheiro.of(27.00), conta.getValorAPagarPara(participante1));
        assertEquals(Dinheiro.of(21.08), conta.getValorAPagarPara(participante2));
        assertEquals(Dinheiro.of(22.68), conta.getValorAPagarPara(participante3));
    }

    /**
     * Cenário:
     * <pre>
     * -------------------------------------------------------------------------------
     * | SOLICITANTE  |  TOT ITENS |  PROPORCAO | ACRESCIMOS |  DESCONTOS | VALOR PAGAR|
     * -------------------------------------------------------------------------------
     * | Frank        |      50.00 |     0.5000 |      10.00 |       7.50 |      52.50 |
     * | Amigo1       |      30.00 |     0.3000 |       6.00 |       4.50 |      31.50 |
     * | Amigo2       |      20.00 |     0.2000 |       4.00 |       3.00 |      21.00 |
     * -------------------------------------------------------------------------------
     * | TOTAL        |     100.00 |            |      20.00 |      15.00 |     105.00 |
     * -------------------------------------------------------------------------------
     * </pre>
     */
    @Test
    public void divisaoComDoisAmigoComPorcentagemDeAcrescimentoEDesconto() {
        var conta = new Conta();

        conta.addItem("Hamburger", 30.00, participante1);
        conta.addItem("Sobremesa", 20.00, participante1);
        conta.addItem("Sanduiche", 25.00, participante2);
        conta.addItem("Refrigerante", 5.00, participante2);
        conta.addItem("Salgado", 12.00, participante3);
        conta.addItem("Suco", 8.00, participante3);

        conta.addAcrescimo(Transacao.porcentagem("20% Garçon", 20));
        conta.addDesconto(Transacao.porcentagem("15% Desconto", 15));

        conta.calcularDivisao();

        log.info("Extrato divisao: \n{}", conta.printExtratoDivisao());

        assertEquals(3, conta.getDivisoes().size());
        assertEquals(Dinheiro.of(105.00), conta.getTotalAPagar());
        assertEquals(Dinheiro.of(52.50), conta.getValorAPagarPara(participante1));
        assertEquals(Dinheiro.of(31.50), conta.getValorAPagarPara(participante2));
        assertEquals(Dinheiro.of(21.00), conta.getValorAPagarPara(participante3));
    }

}
