package com.zoologico.zoo.service;

import com.zoologico.zoo.model.dao.AdministradorDAO;
import com.zoologico.zoo.model.dao.CuidadorDAO;
import com.zoologico.zoo.model.dao.VeterinarioDAO;
import com.zoologico.zoo.model.Administrador;
import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.Veterinario;
import com.zoologico.zoo.model.dto.UserSecurityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService implements UserDetailsService {

    @Autowired
    AdministradorDAO administradorDAO;

    @Autowired
    VeterinarioDAO veterinarioDAO;

    @Autowired
    CuidadorDAO cuidadorDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Procura o usuário pelo parâmetro de Login recebido.
        Administrador administrador = this.administradorDAO.findByLogin(username);
        Veterinario veterinario = this.veterinarioDAO.findByLogin(username);
        Cuidador cuidador = this.cuidadorDAO.findByLogin(username);

        // Verifica o tipo de usuário para atribuição das ROLES na classe criada, além de modificar a forma de obter senha e login.
        if (administrador != null) {
            return new UserSecurityDetails(administrador);
        } else if (veterinario != null) {
            return new UserSecurityDetails(veterinario);
        } else if (cuidador != null) {
            return new UserSecurityDetails(cuidador);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
    }

}
