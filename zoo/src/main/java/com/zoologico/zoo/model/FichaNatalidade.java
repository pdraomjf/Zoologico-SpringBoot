package com.zoologico.zoo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FichaNatalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataNascimento;

    @OneToOne
    @JoinColumn(name = "pai_id")
    private Animal pai;

    @OneToOne
    @JoinColumn(name = "mae_id")
    private Animal mae;

    @ManyToOne
    private Veterinario veterinario;

    @OneToMany(mappedBy = "fichaNatalidade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> filhotes;

    private String estadoSaude;
    private String observacoes;

}
