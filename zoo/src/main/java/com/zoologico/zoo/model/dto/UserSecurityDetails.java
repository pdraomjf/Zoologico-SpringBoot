package com.zoologico.zoo.model.dto;

import com.zoologico.zoo.model.Administrador;
import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.User;
import com.zoologico.zoo.model.Veterinario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {

    private User user;

    public UserSecurityDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Atribui as ROLES para cada usuário conforme o seu tipo, além da ROLE_USER como padrão para todos.
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        if (user instanceof Administrador) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
        }
        if (user instanceof Veterinario) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_VETERINARIO"));
        }
        if (user instanceof Cuidador) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUIDADOR"));
        }

        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
