package com.zoologico.zoo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;

    private String especie;

    private String sexo;

    private int idade;

    private double peso;

    @Column(nullable = true)
    private double pesoNascimento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private LocalDateTime dataNascimento;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEntrada;

    @ManyToOne
    private FichaNatalidade fichaNatalidade;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuidadoDiario> cuidadosDiarios;
}
