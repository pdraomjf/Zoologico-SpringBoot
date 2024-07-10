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
public class Veterinario extends User {
    @OneToMany(mappedBy = "veterinario")
    List<FichaNatalidade> fichasNatalidade;
}
