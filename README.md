# Zoológico

## Descrição do Projeto

Este projeto foi desenvolvido como um trabalho acadêmico utilizando as tecnologias Spring Boot e Thymeleaf, seguindo o padrão de arquitetura MVC (Model-View-Controller). A aplicação se conecta a um banco de dados MySQL para gerenciar as operações de um zoológico. Existem três tipos de usuários no sistema, cada um com suas respectivas funções:

- **Administrador**: Gerencia o sistema e os usuários.
- **Cuidador**: Gerencia a rotina dos animais.
- **Veterinário**: Realiza o cadastro da ficha de natalidade dos animais.

## Funcionalidades

### Administrador
- Gerenciamento de usuários e animais (adicionar, editar, remover).
- Visualização de relatórios gerais.

### Cuidador
- Cadastro e atualização das atividades dos animais.

### Veterinário
- Cadastro e atualização da ficha de natalidade dos animais.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para simplificação da configuração e desenvolvimento de aplicações Java.
- **Thymeleaf**: Motor de templates para construção de páginas dinâmicas em HTML.
- **MVC (Model-View-Controller)**: Padrão de arquitetura para separação de responsabilidades na aplicação.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional.
- **Spring Security**: Para gerenciamento de autenticação e autorização dos usuários.
- **Spring Data JPA**: Simplificação da persistência de dados com JPA.
- **Lombok**: Biblioteca para reduzir o boilerplate no código Java.
- **Thymeleaf Layout Dialect**: Facilita a reutilização de templates em Thymeleaf.

## Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior

## Configuração do Projeto

### Banco de Dados

1. Crie um banco de dados MySQL com o nome `zoologico`.
2. Configure o usuário e senha do banco de dados no arquivo `application.properties`.

```properties
spring.application.name=zoo

server.port=9000

spring.datasource.url=jdbc:mysql://localhost:3306/zoologico
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=drop-and-create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.thymeleaf.cache=false
