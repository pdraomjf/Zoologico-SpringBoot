package com.zoologico.zoo;

import com.zoologico.zoo.model.Administrador;
import com.zoologico.zoo.model.Animal;
import com.zoologico.zoo.model.Cuidador;
import com.zoologico.zoo.model.Veterinario;
import com.zoologico.zoo.model.dao.AdministradorDAO;
import com.zoologico.zoo.model.dao.AnimalDAO;
import com.zoologico.zoo.model.dao.CuidadorDAO;
import com.zoologico.zoo.model.dao.VeterinarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ZooApplication implements CommandLineRunner {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Autowired
	AdministradorDAO administradorDAO;

	@Autowired
	VeterinarioDAO veterinarioDAO;

	@Autowired
	CuidadorDAO cuidadorDAO;

	@Autowired
	AnimalDAO animalDAO;

	public static void main(String[] args) {
		SpringApplication.run(ZooApplication.class, args);
	}

	@Override
	public void run(String... args) {
		administradorDAO.save(criarAdmin());
		veterinarioDAO.save(criarVet());
		cuidadorDAO.save(criarCuidador());

		criarAnimal_01();
		criarAnimal_02();
		criarAnimal_03();
		criarAnimal_04();
	}

	public Administrador criarAdmin() {
		Administrador administrador = new Administrador();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		administrador.setNome("Pedro");
		administrador.setLogin("pedro");
		administrador.setEmail("pedro@email.com");
		administrador.setSenha(passwordEncoder().encode("123456"));
		administrador.setDataCriacao(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		return administrador;
	}

	public Veterinario criarVet() {
		Veterinario veterinario = new Veterinario();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		veterinario.setNome("Carlos");
		veterinario.setLogin("carlos");
		veterinario.setEmail("carlos@email.com");
		veterinario.setSenha(passwordEncoder().encode("123456"));
		veterinario.setDataCriacao(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		return veterinario;
	}

	public Cuidador criarCuidador() {
		Cuidador cuidador = new Cuidador();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		cuidador.setNome("Augusto");
		cuidador.setLogin("augusto");
		cuidador.setEmail("augusto@email.com");
		cuidador.setSenha(passwordEncoder().encode("123456"));
		cuidador.setDataCriacao(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		return cuidador;
	}

	public void criarAnimal_01() {
		Animal animal = new Animal();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		animal.setNome("Thor");
		animal.setEspecie("Leão");
		animal.setSexo("masculino");
		animal.setPeso(190.5);
		animal.setIdade(9);
		animal.setDataEntrada(LocalDateTime.parse(LocalDateTime.now().format(formatter)));


		animalDAO.save(animal);
	}

	private void criarAnimal_02() {
		Animal animal = new Animal();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		animal.setNome("Susy");
		animal.setEspecie("Leão");
		animal.setSexo("feminino");
		animal.setPeso(129.5);
		animal.setIdade(12);
		animal.setDataEntrada(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		animalDAO.save(animal);
	}

	private void criarAnimal_03() {
		Animal animal = new Animal();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		animal.setNome("Bob");
		animal.setEspecie("Leão");
		animal.setSexo("masculino");
		animal.setPeso(184.8);
		animal.setIdade(7);
		animal.setDataEntrada(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		animalDAO.save(animal);
	}

	private void criarAnimal_04() {
		Animal animal = new Animal();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

		animal.setNome("Pit");
		animal.setEspecie("Leão");
		animal.setSexo("feminino");
		animal.setPeso(117.5);
		animal.setIdade(10);
		animal.setDataEntrada(LocalDateTime.parse(LocalDateTime.now().format(formatter)));

		animalDAO.save(animal);
	}
}
