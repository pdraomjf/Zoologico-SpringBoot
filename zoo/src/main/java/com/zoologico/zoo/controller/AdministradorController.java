package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Administrador;
import com.zoologico.zoo.model.User;
import com.zoologico.zoo.model.dao.*;
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
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    AdministradorDAO administradorDAO;

    @Autowired
    AnimalDAO animalDAO;

    @Autowired
    CuidadorDAO cuidadorDAO;

    @Autowired
    VeterinarioDAO veterinarioDAO;

    @Autowired
    FichaNatalidadeDAO fichaNatalidadeDAO;

    @Autowired
    CuidadoDiarioDAO cuidadoDiarioDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("")
    public String home(Model model) {
        List<Administrador> administradores = administradorDAO.findAll();

        model.addAttribute("lista", administradores);

        return "administrador/operacoes";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        int totalAnimais = animalDAO.findAll().size();
        int totalCuidador = cuidadorDAO.findAll().size();
        int totalVeterinario = veterinarioDAO.findAll().size();
        int totalFichaNatalidade = fichaNatalidadeDAO.findAll().size();
        int totalCuidadoDiario = cuidadoDiarioDAO.findAll().size();
        int totalAdministrador = administradorDAO.findAll().size();

        model.addAttribute("totalAnimais", totalAnimais);
        model.addAttribute("totalCuidador", totalCuidador);
        model.addAttribute("totalVeterinario", totalVeterinario);
        model.addAttribute("totalFichaNatalidade", totalFichaNatalidade);
        model.addAttribute("totalCuidadoDiario", totalCuidadoDiario);
        model.addAttribute("totalAdministrador", totalAdministrador);

        return "dashboard";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/criar")
    public String criar(Model model) {
        UserDTO userDTO = new UserDTO();

        model.addAttribute("user", userDTO);

        return "administrador/criar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Transactional
    @PostMapping("/criar")
    public String salvar(@ModelAttribute(value = "user") UserDTO userDTO, Model model) {
        Administrador administrador = new Administrador();

        administrador.setNome(userDTO.getNome());
        administrador.setLogin(userDTO.getLogin());
        administrador.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        administrador.setEmail(userDTO.getEmail());
        administrador.setDataCriacao(LocalDateTime.now());

        administradorDAO.save(administrador);

        model.addAttribute("message", "O Administrador " + administrador.getNome() + " foi registrado com sucesso.");

        return "redirect:/administrador";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/excluir")
    public String excluir(@RequestParam(value = "id") int id, Model model) {
        Optional<Administrador> administradorOptional = administradorDAO.findById(id);
        Administrador administrador = new Administrador();
        if (administradorOptional.isPresent()) {
            administrador = administradorOptional.get();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Administrador logado = administradorDAO.findByLogin(username);

        if (administrador.getId() == logado.getId()) {
            List<Administrador> administradores = administradorDAO.findAll();
            model.addAttribute("lista", administradores);
            model.addAttribute("message", "Você não pode realizar a exclusão de sí próprio.");

            return "administrador/operacoes";
        } else {
            administradorDAO.delete(administrador);
            model.addAttribute("message", "O Administrador " + administrador.getNome() + " foi excluído com sucesso.");

            return "redirect:/administrador";
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/consultar")
    public String buscar(@RequestParam(value = "id") int id, Model model) {
        Optional<Administrador> administradorOptional = administradorDAO.findById(id);
        Administrador administrador = new Administrador();
        if (administradorOptional.isPresent()) {
            administrador = administradorOptional.get();
        }

        model.addAttribute("administrador", administrador);

        return "administrador/consultar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/alterar")
    public String alterar(@RequestParam(value = "id") int id, Model model) {
        UserDTO userDTO = new UserDTO();

        Optional<Administrador> administradorOptional = administradorDAO.findById(id);
        Administrador administrador = new Administrador();
        if (administradorOptional.isPresent()) {
            administrador = administradorOptional.get();
        }

        userDTO.setId(administrador.getId());
        userDTO.setNome(administrador.getNome());
        userDTO.setEmail(administrador.getEmail());
        userDTO.setLogin(administrador.getLogin());
        userDTO.setSenha(passwordEncoder.encode(administrador.getSenha()));

        model.addAttribute("user", userDTO);

        return "administrador/alterar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/alterar")
    public String atualizar(@ModelAttribute(value = "user") UserDTO userDTO) {
        Optional<Administrador> administradorOptional = administradorDAO.findById(userDTO.getId());
        Administrador administrador = new Administrador();
        if (administradorOptional.isPresent()) {
            administrador = administradorOptional.get();
        }

        administrador.setNome(userDTO.getNome());
        administrador.setEmail(userDTO.getEmail());
        administrador.setLogin(userDTO.getLogin());
        administrador.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        administradorDAO.save(administrador);

        return "redirect:/administrador";
    }

}
