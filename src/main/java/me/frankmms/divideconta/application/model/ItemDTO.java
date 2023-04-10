package me.frankmms.divideconta.application.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {

    private String descricao;
    private BigDecimal valor;
    private String solicitante;

}
