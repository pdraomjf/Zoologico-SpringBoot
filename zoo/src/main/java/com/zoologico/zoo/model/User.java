package com.zoologico.zoo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    String nome;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true, nullable = false)
    String login;

    @Column(nullable = false)
    String senha;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime dataCriacao;
}
