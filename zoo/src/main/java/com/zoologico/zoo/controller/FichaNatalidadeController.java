package com.zoologico.zoo.controller;

import com.zoologico.zoo.model.Animal;
import com.zoologico.zoo.model.FichaNatalidade;
import com.zoologico.zoo.model.Veterinario;
import com.zoologico.zoo.model.dao.AnimalDAO;
import com.zoologico.zoo.model.dao.FichaNatalidadeDAO;
import com.zoologico.zoo.model.dao.VeterinarioDAO;
import com.zoologico.zoo.model.dto.FichaNatalidadeDTO;
import com.zoologico.zoo.model.dto.FilhoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("/natalidade")
class FichaNatalidadeController {

    @Autowired
    private AnimalDAO animalDAO;

    @Autowired
    private FichaNatalidadeDAO fichaNatalidadeDAO;

    @Autowired
    private VeterinarioDAO veterinarioDAO;

    @PreAuthorize("hasRole('VETERINARIO')")
    @GetMapping("")
    public String home(Model model) {
        List<FichaNatalidade> fichaNatalidades = fichaNatalidadeDAO.findAll();

        model.addAttribute("lista", fichaNatalidades);

        return "ficha-natalidade/natalidade";
    }

    @PreAuthorize("hasRole('VETERINARIO')")
    @GetMapping("/consultar")
    public String consultar(@RequestParam(value = "id") int id, Model model) {
        Optional<FichaNatalidade> fichaNatalidade = fichaNatalidadeDAO.findById(id);
        FichaNatalidade ficha = new FichaNatalidade();

        if (fichaNatalidade.isPresent()) {
            ficha = fichaNatalidade.get();
        }

        String especie = ficha.getMae().getEspecie();

        List<Animal> paiLista = animalDAO.findBySexoAndEspecie("masculino", especie);
        List<Animal> maeLista = animalDAO.findBySexoAndEspecie("feminino", especie);

        model.addAttribute("ficha", ficha);
        model.addAttribute("paiLista", paiLista);
        model.addAttribute("maeLista", maeLista);

        return "ficha-natalidade/consultar";
    }

    @PreAuthorize("hasRole('VETERINARIO')")
    @GetMapping("/alterar")
    public String alterar(@RequestParam(value = "id") int id, Model model) {
        Optional<FichaNatalidade> fichaNatalidade = fichaNatalidadeDAO.findById(id);
        FichaNatalidade ficha = new FichaNatalidade();

        if (fichaNatalidade.isPresent()) {
            ficha = fichaNatalidade.get();
        }

        FichaNatalidadeDTO fichaNatalidadeDTO = new FichaNatalidadeDTO(ficha.getId(), String.valueOf(ficha.getPai().getId()), String.valueOf(ficha.getMae().getId()), ficha.getEstadoSaude(), ficha.getObservacoes(), null);

        List<Animal> paiLista = animalDAO.findBySexoAndEspecie("masculino", ficha.getMae().getEspecie());
        List<Animal> maeLista = animalDAO.findBySexoAndEspecie("feminino", ficha.getMae().getEspecie());

        model.addAttribute("fichaNatalidade", fichaNatalidadeDTO);
        model.addAttribute("mae", maeLista);
        model.addAttribute("pai", paiLista);

        return "ficha-natalidade/alterar";
    }

    @Transactional
    @PreAuthorize("hasRole('VETERINARIO')")
    @PostMapping("/alterar")
    public String atualizar(@ModelAttribute("fichaNatalidade") FichaNatalidadeDTO fichaNatalidadeDTO, Model model) {
        Optional<FichaNatalidade> optionalFichaNatalidade = fichaNatalidadeDAO.findById(fichaNatalidadeDTO.getId());
        if (optionalFichaNatalidade.isPresent()) {
            FichaNatalidade natalidade = optionalFichaNatalidade.get();

            Optional<Animal> paiOptional = animalDAO.findById(parseInt(fichaNatalidadeDTO.getPai()));
            Optional<Animal> maeOptional = animalDAO.findById(parseInt(fichaNatalidadeDTO.getMae()));

            if (paiOptional.isPresent() && maeOptional.isPresent()) {
                natalidade.setPai(paiOptional.get());
                natalidade.setMae(maeOptional.get());
            }

            natalidade.setEstadoSaude(fichaNatalidadeDTO.getEstadoSaude());
            natalidade.setObservacoes(fichaNatalidadeDTO.getObservacoes());

            fichaNatalidadeDAO.save(natalidade);

            model.addAttribute("message", "Ficha de natalidade alterada com sucesso.");
        } else {
            model.addAttribute("message", "Ficha de natalidade não encontrada.");
        }

        return "redirect:/natalidade";
    }

    @PreAuthorize("hasRole('VETERINARIO')")
    @GetMapping("/especie")
    public String especie(Model model) {
        List<String> especies = animalDAO.findDistinctEspecies();

        model.addAttribute("especies", especies);

        return "ficha-natalidade/especie";
    }

    @PreAuthorize("hasRole('VETERINARIO')")
    @GetMapping("/criar")
    public String criar(@RequestParam(value = "especie", required = false) String especie, Model model) {
        List<Animal> pai = animalDAO.findBySexoAndEspecie("masculino", especie);
        List<Animal> mae = animalDAO.findBySexoAndEspecie("feminino", especie);

        if (pai == null || mae == null) {
            model.addAttribute("message", "A espécie selecionada não possui animais de ambos os sexos.");

            return "ficha-natalidade/especie";
        } else {
            FichaNatalidadeDTO fichaNatalidade = new FichaNatalidadeDTO();

            model.addAttribute("pai", pai);
            model.addAttribute("mae", mae);
            model.addAttribute("fichaNatalidade", fichaNatalidade);

            return "ficha-natalidade/criar";
        }
    }

    @Transactional
    @PreAuthorize("hasRole('VETERINARIO')")
    @PostMapping("/criar")
    public String salvar(@ModelAttribute("fichaNatalidade") FichaNatalidadeDTO fichaNatalidade, Model model) {

        FichaNatalidade natalidade = new FichaNatalidade();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Veterinario veterinario = veterinarioDAO.findByLogin(username);

        Optional<Animal> paiOptional = animalDAO.findById(parseInt(fichaNatalidade.getPai()));
        Optional<Animal> maeOptional = animalDAO.findById(parseInt(fichaNatalidade.getMae()));
        Animal pai = new Animal();
        Animal mae = new Animal();

        if (paiOptional.isPresent() && maeOptional.isPresent()) {
            pai = paiOptional.get();
            mae = maeOptional.get();
        }

        natalidade.setDataNascimento(LocalDateTime.now());
        natalidade.setPai(pai);
        natalidade.setMae(mae);
        natalidade.setEstadoSaude(fichaNatalidade.getEstadoSaude());
        natalidade.setObservacoes(fichaNatalidade.getObservacoes());
        natalidade.setVeterinario(veterinario);

        List<FilhoteDTO> filhoteDTOs = fichaNatalidade.getFilhotes();
        List<Animal> filhotes = filhoteDTOs.stream().map(dto -> {
            Animal filhote = new Animal();

            filhote.setPeso(Double.parseDouble(dto.getPesoNascimento()));
            filhote.setEspecie(natalidade.getMae().getEspecie());
            filhote.setDataEntrada(LocalDateTime.now());
            filhote.setDataNascimento(LocalDateTime.now());
            filhote.setIdade(0);
            filhote.setNome(dto.getNome());
            filhote.setSexo(dto.getSexo());
            filhote.setPesoNascimento(Double.parseDouble(dto.getPesoNascimento()));
            filhote.setFichaNatalidade(natalidade);

            return filhote;
        }).collect(Collectors.toList());

        natalidade.setFilhotes(filhotes);

        fichaNatalidadeDAO.save(natalidade);

        model.addAttribute("message", "Ficha de natalidade criada com sucesso.");

        return "redirect:/natalidade";
    }
}
