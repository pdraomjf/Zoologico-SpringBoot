package com.zoologico.zoo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuidadoDiarioDTO {

    private int id, id_animal;
    private String tarefaDiaria, data;

}
