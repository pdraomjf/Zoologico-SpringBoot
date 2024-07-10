package com.zoologico.zoo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FichaNatalidadeDTO {

    private int id;
    private String pai, mae, estadoSaude, observacoes;
    private List<FilhoteDTO> filhotes;

}
