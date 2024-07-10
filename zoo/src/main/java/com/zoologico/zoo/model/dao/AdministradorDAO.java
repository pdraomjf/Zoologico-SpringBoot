package com.zoologico.zoo.model.dao;

import com.zoologico.zoo.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorDAO extends JpaRepository<Administrador, Integer> {

    public Administrador findByLogin(String login);

}
