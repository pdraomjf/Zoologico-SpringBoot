package com.zoologico.zoo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuidador extends User {
    @OneToMany(mappedBy = "cuidador")
    private List<CuidadoDiario> cuidadosDiarios;
}