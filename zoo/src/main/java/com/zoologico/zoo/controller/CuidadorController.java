package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.Veterinario;
import com.zoologico.zoo.model.dao.CuidadorDAO;
import com.zoologico.zoo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cuidador")
public class CuidadorController {

    @Autowired
    CuidadorDAO cuidadorDAO;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("")
    public String home(Model model){
        List<Cuidador> cuidadores = cuidadorDAO.findAll();

        model.addAttribute("lista", cuidadores);

        return "cuidador/operacoes";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/criar")
    public String criar(Model model) {
        UserDTO userDTO = new UserDTO();

        model.addAttribute("user", userDTO);

        return "cuidador/criar";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Transactional
    @PostMapping("/criar")
    public String salvar(@ModelAttribute(value = "user") UserDTO userDTO, Model model) {
        Cuidador cuidador = new Cuidador();

        cuidador.setNome(userDTO.getNome());
        cuidador.setLogin(userDTO.getLogin());
        cuidador.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        cuidador.setEmail(userDTO.getEmail());
        cuidador.setDataCriacao(LocalDateTime.now());

        cuidadorDAO.save(cuidador);

        model.addAttribute("message", "O Cuidador " + cuidador.getNome() + " foi registrado com sucesso.");

        return "redirect:/cuidador";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/excluir")
    public String excluir(@RequestParam(value = "id") int id, Model model) {
        Optional<Cuidador> cuidadorOptional = cuidadorDAO.findById(id);
        Cuidador cuidador = new Cuidador();
        if (cuidadorOptional.isPresent()) {
            cuidador = cuidadorOptional.get();

        }

        cuidadorDAO.delete(cuidador);
        model.addAttribute("message", "O cuidador " + cuidador.getNome() + " foi excluído com sucesso.");

        return "redirect:/cuidador";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/consultar")
    public String buscar(@RequestParam(value = "id") int id, Model model) {
        Optional<Cuidador> cuidadorOptional = cuidadorDAO.findById(id);
        Cuidador cuidador = new Cuidador();
        if (cuidadorOptional.isPresent()) {
            cuidador = cuidadorOptional.get();

        }

        model.addAttribute("cuidador", cuidador);

        return "cuidador/consultar";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/alterar")
    public String alterar(@RequestParam(value = "id") int id, Model model) {

        UserDTO userDTO = new UserDTO();

        Optional<Cuidador> cuidadorOptional = cuidadorDAO.findById(id);
        Cuidador cuidador = new Cuidador();
        if (cuidadorOptional.isPresent()) {
            cuidador = cuidadorOptional.get();

        }

        userDTO.setId(cuidador.getId());
        userDTO.setNome(cuidador.getNome());
        userDTO.setEmail(cuidador.getEmail());
        userDTO.setLogin(cuidador.getLogin());
        userDTO.setSenha(passwordEncoder.encode(cuidador.getSenha()));

        model.addAttribute("user", userDTO);

        return "cuidador/alterar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/alterar")
    public String atualizar(@ModelAttribute(value = "user") UserDTO userDTO) {
        Optional<Cuidador> cuidadorOptional = cuidadorDAO.findById(userDTO.getId());
        Cuidador cuidador = new Cuidador();
        if (cuidadorOptional.isPresent()) {
            cuidador = cuidadorOptional.get();
        }

        cuidador.setNome(userDTO.getNome());
        cuidador.setEmail(userDTO.getEmail());
        cuidador.setLogin(userDTO.getLogin());
        cuidador.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        cuidadorDAO.save(cuidador);

        return "redirect:/cuidador";
    }
}
