package com.zoologico.zoo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CuidadoDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime data;

    @ManyToOne
    private Cuidador cuidador;

    private String tarefaDiaria;

    @ManyToOne
    private Animal animal;

}
