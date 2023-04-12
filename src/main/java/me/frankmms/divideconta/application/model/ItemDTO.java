package me.frankmms.divideconta.application.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemDTO {

    private String descricao;

    @NotNull
    private BigDecimal valor;

    private String participante;

}
