package me.frankmms.divideconta.application.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DivisaoDTO {

    private BigDecimal totalItens;
    private BigDecimal totalAcrescimos;
    private BigDecimal totalDescontos;
    private BigDecimal totalAPagar;
    private List<DivisaoDetalheDTO> detalhes = new ArrayList<>();

    public void addDetalhe(DivisaoDetalheDTO detalhe) {
        detalhes.add(detalhe);
    }

}
