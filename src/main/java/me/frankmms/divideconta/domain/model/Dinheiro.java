package me.frankmms.divideconta.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Dinheiro extends Number {

    RoundingMode defaultRoundingMode = RoundingMode.HALF_EVEN;
    private BigDecimal valor;

    public static Dinheiro ZERO = new Dinheiro(BigDecimal.ZERO);

    private Dinheiro(BigDecimal valor) {
        this.valor = valor;
    }

    public static Dinheiro of(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public static Dinheiro of(double valor) {
        return new Dinheiro(new BigDecimal(String.valueOf(valor)));
    }

    public Dinheiro add(Dinheiro dinheiro) {
        return new Dinheiro(dinheiro.valor.add(this.valor));
    }

    @Override
    public int intValue() {
        return valor.intValue();
    }

    @Override
    public long longValue() {
        return valor.longValue();
    }

    @Override
    public float floatValue() {
        return valor.floatValue();
    }

    @Override
    public double doubleValue() {
        return valor.doubleValue();
    }

    public Dinheiro subtract(Dinheiro dinheiro) {
        return new Dinheiro(this.valor.subtract(dinheiro.valor));
    }

    public BigDecimal decimalValue() {
        return valor;
    }

    public Dinheiro divide(Dinheiro valor) {
        return new Dinheiro(this.valor.divide(valor.valor, 4, RoundingMode.HALF_EVEN));
    }

    public Dinheiro multiply(BigDecimal valor) {
        return new Dinheiro(this.valor.multiply(valor));
    }

    public static Collector<Dinheiro, ?, Dinheiro> summingUp() {
        return Collectors.reducing(Dinheiro.ZERO, Dinheiro::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinheiro dinheiro = (Dinheiro) o;
        return valor.compareTo(dinheiro.valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }

    public Dinheiro setScale(int scale) {
        return Dinheiro.of(valor.setScale(scale, defaultRoundingMode));
    }

    public static <T> Dinheiro sum(Collection<T> items, Function<T, Dinheiro> valueGetter) {
        return items.stream()
                .map(valueGetter)
                .reduce(Dinheiro::add)
                .orElse(Dinheiro.ZERO);
    }

    public static Dinheiro sum(Collection<Dinheiro> valores) {
        return valores.stream()
                .reduce(Dinheiro::add)
                .orElse(Dinheiro.ZERO);
    }

}
