package com.zoologico.zoo.model.dao;

import com.zoologico.zoo.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalDAO extends JpaRepository<Animal, Integer> {
    List<Animal> findBySexoIs(String sexo);

    List<Animal> findBySexoAndEspecie(String sexo, String especie);

    @Query("SELECT DISTINCT a.especie FROM Animal a")
    List<String> findDistinctEspecies();
}
