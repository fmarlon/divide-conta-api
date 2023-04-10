package me.frankmms.divideconta.application.model;

import lombok.Data;

import java.util.List;

@Data
public class ContaDTO {

    private List<ItemDTO> itens;
    private List<TransacaoDTO> descontos;
    private List<TransacaoDTO> acrescimos;

}
