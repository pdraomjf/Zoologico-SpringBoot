package com.zoologico.zoo.model.dto;

import com.zoologico.zoo.model.CuidadoDiario;
import com.zoologico.zoo.model.FichaNatalidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {

    private int id;

    private String nome;

    private String especie;

    private String sexo;

    private int idade;

    private double peso;

    private double pesoNascimento;

    private String dataNascimento;

    private String dataEntrada;

}
