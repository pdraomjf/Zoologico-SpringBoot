package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Administrador;
import com.zoologico.zoo.model.Veterinario;
import com.zoologico.zoo.model.dao.VeterinarioDAO;
import com.zoologico.zoo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/veterinario")
public class VeterinarioController {

    @Autowired
    VeterinarioDAO veterinarioDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("")
    public String home(Model model){
        List<Veterinario> veterinarios = veterinarioDAO.findAll();

        model.addAttribute("lista", veterinarios);

        return "veterinario/operacoes";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/criar")
    public String criar(Model model) {
        UserDTO userDTO = new UserDTO();

        model.addAttribute("user", userDTO);

        return "veterinario/criar";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Transactional
    @PostMapping("/criar")
    public String salvar(@ModelAttribute(value = "user") UserDTO userDTO, Model model) {
        Veterinario veterinario = new Veterinario();

        veterinario.setNome(userDTO.getNome());
        veterinario.setLogin(userDTO.getLogin());
        veterinario.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        veterinario.setEmail(userDTO.getEmail());
        veterinario.setDataCriacao(LocalDateTime.now());

        veterinarioDAO.save(veterinario);

        model.addAttribute("message", "O Veterinário " + veterinario.getNome() + " foi registrado com sucesso.");

        return "redirect:/veterinario";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/excluir")
    public String excluir(@RequestParam(value = "id") int id, Model model) {
        Optional<Veterinario> veterinarioOptional = veterinarioDAO.findById(id);
        Veterinario veterinario = new Veterinario();
        if (veterinarioOptional.isPresent()) {
            veterinario = veterinarioOptional.get();

        }

        veterinarioDAO.delete(veterinario);
        model.addAttribute("message", "O Veterinário " + veterinario.getNome() + " foi excluído com sucesso.");

        return "redirect:/veterinario";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/consultar")
    public String buscar(@RequestParam(value = "id") int id, Model model) {
        Optional<Veterinario> veterinarioOptional = veterinarioDAO.findById(id);
        Veterinario veterinario = new Veterinario();
        if (veterinarioOptional.isPresent()) {
            veterinario = veterinarioOptional.get();

        }

        model.addAttribute("veterinario", veterinario);

        return "veterinario/consultar";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/alterar")
    public String alterar(@RequestParam(value = "id") int id, Model model) {

        UserDTO userDTO = new UserDTO();

        Optional<Veterinario> veterinarioOptional = veterinarioDAO.findById(id);
        Veterinario veterinario = new Veterinario();
        if (veterinarioOptional.isPresent()) {
            veterinario = veterinarioOptional.get();
        }

        userDTO.setId(veterinario.getId());
        userDTO.setNome(veterinario.getNome());
        userDTO.setEmail(veterinario.getEmail());
        userDTO.setLogin(veterinario.getLogin());
        userDTO.setSenha(passwordEncoder.encode(veterinario.getSenha()));

        model.addAttribute("user", userDTO);

        return "veterinario/alterar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/alterar")
    public String atualizar(@ModelAttribute(value = "user") UserDTO userDTO) {
        Optional<Veterinario> veterinarioOptional = veterinarioDAO.findById(userDTO.getId());
        Veterinario veterinario = new Veterinario();
        if (veterinarioOptional.isPresent()) {
            veterinario = veterinarioOptional.get();
        }

        veterinario.setNome(userDTO.getNome());
        veterinario.setEmail(userDTO.getEmail());
        veterinario.setLogin(userDTO.getLogin());
        veterinario.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        veterinarioDAO.save(veterinario);

        return "redirect:/veterinario";
    }
}
