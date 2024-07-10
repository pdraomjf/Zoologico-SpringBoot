package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Animal;
import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.dao.AnimalDAO;
import com.zoologico.zoo.model.dto.AnimalDTO;
import com.zoologico.zoo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalDAO animalDAO;

    @GetMapping("")
    public String home(Model model){
        List<Animal> animais = animalDAO.findAll();

        model.addAttribute("lista", animais);

        return "animal/operacoes";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/criar")
    public String criar(Model model) {
        AnimalDTO animalDTO = new AnimalDTO();

        model.addAttribute("animal", animalDTO);

        return "animal/criar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Transactional
    @PostMapping("/criar")
    public String salvar(@ModelAttribute(value = "animal") AnimalDTO animalDTO, Model model){
        Animal animal = new Animal();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        animal.setNome(animalDTO.getNome());
        animal.setEspecie(animalDTO.getEspecie());
        animal.setSexo(animalDTO.getSexo());
        animal.setIdade(animalDTO.getIdade());
        animal.setPeso(animalDTO.getPeso());
        animal.setDataEntrada(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

        animalDAO.save(animal);

        return "redirect:/animal";
    }
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/excluir")
    public String excluir(@RequestParam(value="id") int id, Model model){
        Optional<Animal> animalOptional = animalDAO.findById(id);
        Animal animal = new Animal();
        if(animalOptional.isPresent()){
            animal = animalOptional.get();
        }

        animalDAO.delete(animal);
        model.addAttribute("message", "O animal " + animal.getNome() + " foi excluído com sucesso.");

        return "redirect:/animal";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/consultar")
    public String buscar(@RequestParam(value = "id") int id, Model model) {
        Optional<Animal> animalOptional = animalDAO.findById(id);
        Animal animal = new Animal();
        if(animalOptional.isPresent()){
            animal = animalOptional.get();
        }

        model.addAttribute("animal", animal);

        return "animal/consultar";
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/alterar")
    public String alterar(@RequestParam(value = "id") int id, Model model) {

        AnimalDTO animalDTO = new AnimalDTO();

        Optional<Animal> animalOptional = animalDAO.findById(id);
        Animal animal = new Animal();

        if (animalOptional.isPresent()){
            animal = animalOptional.get();
        }

        animalDTO.setId(animal.getId());
        animalDTO.setNome(animal.getNome());
        animalDTO.setEspecie(animal.getEspecie());
        animalDTO.setSexo(animal.getSexo());
        animalDTO.setIdade(animal.getIdade());
        animalDTO.setPeso(animal.getPeso());
        animalDTO.setPesoNascimento(animal.getPesoNascimento());
        animalDTO.setDataNascimento(null);
        animalDTO.setDataEntrada(null);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        if (animal.getDataNascimento() != null) {
            animalDTO.setDataNascimento(animal.getDataNascimento().format(formatter));
        }

        if (animal.getDataEntrada() != null) {
            animalDTO.setDataEntrada(animal.getDataEntrada().format(formatter));
        }

        System.out.println("Dados enviados: ");
        System.out.println("Peso nascimento: " + animalDTO.getPesoNascimento());
        System.out.println("Data nascimento: " + animalDTO.getDataNascimento());

        model.addAttribute("animal", animalDTO);

        return "animal/alterar";
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/alterar")
    public String atualizar(@ModelAttribute(value = "animal") AnimalDTO animalDTO) {

        Optional<Animal> animalOptional = animalDAO.findById(animalDTO.getId());
        Animal animal = new Animal();

        if (animalOptional.isPresent()){
            animal = animalOptional.get();
        }

        animal.setNome(animalDTO.getNome());
        animal.setEspecie(animalDTO.getEspecie());
        animal.setSexo(animalDTO.getSexo());
        animal.setIdade(animalDTO.getIdade());
        animal.setPeso(animalDTO.getPeso());

        System.out.println("Recebidos: ");
        System.out.println("Data nasc:" + animalDTO.getDataNascimento() + ".fim");

        if (animalDTO.getPesoNascimento() != 0.0) {
            animal.setPesoNascimento(animalDTO.getPesoNascimento());
        }

        String teste = animalDTO.getDataNascimento();

        System.out.println("Teste:" + teste + ".");

        if (animalDTO.getDataNascimento() != "") {
            animal.setDataNascimento(LocalDateTime.parse(animalDTO.getDataNascimento()));
        }

        if (animalDTO.getDataEntrada() != "") {
            animal.setDataEntrada(LocalDateTime.parse(animalDTO.getDataEntrada()));
        }

        animalDAO.save(animal);

        return "redirect:/animal";
    }
}
