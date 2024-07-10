package com.zoologico.zoo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilhoteDTO {

    private int id;
    private String nome, sexo, pesoNascimento;

}
