


# _API REST_  utilizando  [_Spring Boot_](https://spring.io/projects/spring-boot)
O projeto é um App para uma clinica medica, sistema realiza CRUD de medicos, pacinetes e marcação de consultas, a autenticação dos usuarios com token JWT.

[_Spring Initializr_](https://start.spring.io)_  para fazer a construção do projeto -   **Java**: 17.

### Maven para a gestão de dependências e _build_.
####  Dependências
- Spring Boot DevTools
- Spring Data JPA
- Spring Web
- Spring Security
- Lombok
- Validation

E outras bibliotecas, como o driver do  _MySQL, Flyway e token [java-jwt](https://github.com/auth0/java-jwt) .

Flyway: para fazer as migrations para ter controle do histórico de evolução do banco de dados.

Lombok é Framework, para gerar códigos, como esses códigos verbosos do Java, de `getter` e `setter`, baseado em anotações. serve para deixar  o código mais simples e menos verboso.

### Tudo que Ja foi implementado
-   Desenvolvimento de uma API Rest
-   CRUD (Create, Read, Update e Delete)
-   Validações
-   Paginação e ordenação
-   Boas práticas REST
-   Tratamento de erros
-   Controle de acesso com JWT