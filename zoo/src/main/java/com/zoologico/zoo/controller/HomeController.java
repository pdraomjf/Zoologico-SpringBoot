package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.User;
import com.zoologico.zoo.model.dao.AdministradorDAO;
import com.zoologico.zoo.model.dao.CuidadorDAO;
import com.zoologico.zoo.model.dao.VeterinarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    AdministradorDAO administradorDAO;

    @Autowired
    CuidadorDAO cuidadorDAO;

    @Autowired
    VeterinarioDAO veterinarioDAO;

    @GetMapping("")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String role = "";

        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMINISTRADOR"))) {
            role = "ROLE_ADMINISTRADOR";
        } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_VETERINARIO"))) {
            role = "ROLE_VETERINARIO";
        } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_CUIDADOR"))) {
            role = "ROLE_CUIDADOR";
        }

        String nomeUsuario = "";

        if ("ROLE_ADMINISTRADOR".equals(role)) {
            nomeUsuario = administradorDAO.findByLogin(username).getNome();
        } else if ("ROLE_VETERINARIO".equals(role)) {
            nomeUsuario = veterinarioDAO.findByLogin(username).getNome();
        } else if ("ROLE_CUIDADOR".equals(role)) {
            nomeUsuario = cuidadorDAO.findByLogin(username).getNome();
        }

        model.addAttribute("nome", nomeUsuario);

        return "template";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
