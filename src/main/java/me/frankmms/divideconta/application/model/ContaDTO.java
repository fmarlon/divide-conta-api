package me.frankmms.divideconta.application.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ContaDTO {

    @NotNull
    private List<ItemDTO> itens;

    @NotNull
    private List<TransacaoDTO> descontos;

    @NotNull
    private List<TransacaoDTO> acrescimos;

}
