package me.frankmms.divideconta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@AllArgsConstructor
@ToString
public class Participante {
    private String nome;

    public static Participante of(String nome) {
        return new Participante(nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participante that = (Participante) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

}
