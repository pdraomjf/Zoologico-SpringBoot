package com.zoologico.zoo.model.dao;

import com.zoologico.zoo.model.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuidadorDAO extends JpaRepository<Cuidador, Integer> {

    public Cuidador findByLogin(String login);

}
