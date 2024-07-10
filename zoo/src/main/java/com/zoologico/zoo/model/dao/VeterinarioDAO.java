package com.zoologico.zoo.model.dao;

import com.zoologico.zoo.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioDAO extends JpaRepository<Veterinario, Integer> {

    public Veterinario findByLogin(String login);

}
