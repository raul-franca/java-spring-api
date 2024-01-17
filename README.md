


# _API REST_  utilizando  [_Spring Boot_](https://spring.io/projects/spring-boot)
O projeto é um API REST para uma clinica medica, sistema realiza CRUD de medicos, pacinetes e marcação de consultas, a autenticação dos usuarios com token JWT.

[_Spring Initializr_](https://start.spring.io)_  para fazer a construção do projeto -   **Java**: 17.

### Maven para a gestão de dependências e _build_.
####  Dependências
- Spring Boot DevTools
- Spring Data JPA
- Spring Web
- Spring Security
- Lombok
- Validation
-  SpringDoc

E outras bibliotecas, como o driver do  _MySQL, Flyway e token [java-jwt](https://github.com/auth0/java-jwt) .

Flyway: para fazer as migrations para ter controle do histórico de evolução do banco de dados.

Lombok é Framework, para gerar códigos, como esses códigos verbosos do Java, de `getter` e `setter`, baseado em anotações. serve para deixar  o código mais simples e menos verboso.

### Tudo que Ja foi implementado
-   Desenvolvimento de uma API Rest
-   ﻿﻿CRUD (Create, Read, Update e Delete)
-   ﻿﻿Validações
-   ﻿﻿Paginação e ordenação
-   ﻿﻿Boas práticas REST
-   ﻿﻿Tratamento de erros
-   ﻿﻿Controle de acesso com JWT
- Documentação Swagger UI para visualizar e testar uma API Rest


## Princípios SOLID em destaque no projeto

-   **Single Responsibility Principle**  (Princípio da responsabilidade única): porque cada classe de validação tem apenas uma responsabilidade.
-   **Open-Closed Principle**  (Princípio aberto-fechado): na classe  _service_,  `AgendadeConsultas`, porque ela está fechada para modificação, não precisamos mexer nela. Mas ela está aberta para extensão, conseguimos adicionar novos validadores apenas criando as classes implementando a interface.
-   **Dependency Inversion Principle**  (Princípio da inversão de dependência): porque nossa classe  _service_  depende de uma abstração, que é a interface, não depende dos validadores, das implementações especificamente. O módulo de alto nível, a  _service_, não depende dos módulos de baixo nível, que são os validadores.

## Testes:
- Controller
- Repository, no projeto a Queries personalizadas para fazer consultas por isso é importante testar.