package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Animal;
import com.zoologico.zoo.model.CuidadoDiario;
import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.dao.AnimalDAO;
import com.zoologico.zoo.model.dao.CuidadoDiarioDAO;
import com.zoologico.zoo.model.dao.CuidadorDAO;
import com.zoologico.zoo.model.dto.CuidadoDiarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cuidado")
public class CuidadoDiarioController {

    @Autowired
    CuidadoDiarioDAO cuidadoDiarioDAO;

    @Autowired
    AnimalDAO animalDAO;

    @Autowired
    CuidadorDAO cuidadorDAO;

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("")
    public String home(Model model) {
        List<CuidadoDiario> cuidadoDiario = cuidadoDiarioDAO.findAll();

        model.addAttribute("lista", cuidadoDiario);

        return "cuidado-diario/cuidado";
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("/consultar")
    public String consultar(@RequestParam(value = "id") int id, Model model) {
        Optional<CuidadoDiario> cuidadoDiarioOptional = cuidadoDiarioDAO.findById(id);
        CuidadoDiario cuidadoDiario = new CuidadoDiario();
        if (cuidadoDiarioOptional.isPresent()){
            cuidadoDiario = cuidadoDiarioOptional.get();
        }

        model.addAttribute("dados", cuidadoDiario);

        return "cuidado-diario/consultar";
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("/excluir")
    public String excluir(@RequestParam(value = "id") int id) {
        Optional<CuidadoDiario> cuidadoDiarioOptional = cuidadoDiarioDAO.findById(id);
        CuidadoDiario cuidadoDiario = new CuidadoDiario();
        if (cuidadoDiarioOptional.isPresent()){
            cuidadoDiario = cuidadoDiarioOptional.get();
        }

        cuidadoDiarioDAO.delete(cuidadoDiario);

        return "redirect:/cuidado";
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("/alterar")
    public String atualizar(@RequestParam(value = "id") int id, Model model) {
        Optional<CuidadoDiario> cuidadoDiarioOptional = cuidadoDiarioDAO.findById(id);
        CuidadoDiario cuidadoDiario = new CuidadoDiario();
        if (cuidadoDiarioOptional.isPresent()) {
            cuidadoDiario = cuidadoDiarioOptional.get();
        }

        CuidadoDiarioDTO cuidadoDiarioDTO = new CuidadoDiarioDTO();

        cuidadoDiarioDTO.setId(cuidadoDiario.getId());
        cuidadoDiarioDTO.setData(String.valueOf(cuidadoDiario.getData()));
        cuidadoDiarioDTO.setId_animal(cuidadoDiario.getAnimal().getId());
        cuidadoDiarioDTO.setTarefaDiaria(cuidadoDiario.getTarefaDiaria());

        List<Animal> animais = animalDAO.findAll();

        model.addAttribute("cuidadoDiario", cuidadoDiarioDTO);
        model.addAttribute("animais", animais);

        return "cuidado-diario/alterar";
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @PostMapping("/alterar")
    public String alterar(@ModelAttribute(value = "cuidadoDiario") CuidadoDiarioDTO cuidadoDiarioDTO, Model model) {
        Optional<CuidadoDiario> cuidadoDiarioOptional = cuidadoDiarioDAO.findById(cuidadoDiarioDTO.getId());
        CuidadoDiario cuidadoDiario = new CuidadoDiario();
        if (cuidadoDiarioOptional.isPresent()) {
            cuidadoDiario = cuidadoDiarioOptional.get();
        }

        Optional<Animal> animalOptional = animalDAO.findById(cuidadoDiarioDTO.getId_animal());
        Animal animal = new Animal();
        if (animalOptional.isPresent()) {
            animal = animalOptional.get();
        }

        cuidadoDiario.setData(LocalDateTime.parse(cuidadoDiarioDTO.getData()));
        cuidadoDiario.setTarefaDiaria(cuidadoDiarioDTO.getTarefaDiaria());
        cuidadoDiario.setAnimal(animal);

        cuidadoDiarioDAO.save(cuidadoDiario);

        return "redirect:/cuidado";
    }

    @PreAuthorize("hasRole('CUIDADOR')")
    @GetMapping("/criar")
    public String criar(Model model) {
        CuidadoDiarioDTO cuidadoDiarioDTO = new CuidadoDiarioDTO();
        List<Animal> animais = animalDAO.findAll();

        model.addAttribute("cuidadoDiario", cuidadoDiarioDTO);
        model.addAttribute("animais", animais);

        return "cuidado-diario/criar";
    }

    @Transactional
    @PreAuthorize("hasRole('CUIDADOR')")
    @PostMapping("/criar")
    public String salvar(@ModelAttribute CuidadoDiarioDTO cuidadoDiarioDTO, Model model) {
        CuidadoDiario cuidadoDiario = new CuidadoDiario();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cuidador cuidador = cuidadorDAO.findByLogin(username);

        Animal animal = new Animal();
        Optional<Animal> animalOptional = animalDAO.findById(cuidadoDiarioDTO.getId_animal());
        if (animalOptional.isPresent()) {
            animal = animalOptional.get();
        }

        cuidadoDiario.setCuidador(cuidador);
        cuidadoDiario.setAnimal(animal);
        cuidadoDiario.setTarefaDiaria(cuidadoDiarioDTO.getTarefaDiaria());
        cuidadoDiario.setData(LocalDateTime.parse(cuidadoDiarioDTO.getData()));

        cuidadoDiarioDAO.save(cuidadoDiario);
        model.addAttribute("message", "Cuidado diário adicionado com sucesso.");

        return "redirect:/cuidado";
    }
}
