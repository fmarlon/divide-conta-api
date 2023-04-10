package me.frankmms.divideconta.domain.model;

public class Item {
    private String descricao;
    private Dinheiro valor;
    private Participante participante;

    public Item(String descricao, Dinheiro valor, Participante participante) {
        this.descricao = descricao;
        this.valor = valor;
        this.participante = participante;
    }

    public Dinheiro getValor() {
        return valor;
    }

    public Participante getParticipante() {
        return participante;
    }
}
