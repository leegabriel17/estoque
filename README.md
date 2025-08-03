# gerenciar_estoque
## Objetivo

Este projeto é uma **API REST** para controle de estoque, desenvolvida em Java com o framework Spring Boot. O objetivo é fornecer endpoints para o gerenciamento completo de produtos, incluindo operações de cadastro, consulta, atualização e remoção.

## Tecnologias e Ferramentas Utilizadas

- **Java**: Linguagem principal do projeto
- **Spring Boot**: Framework para desenvolvimento da aplicação
- **Gradle**: Ferramenta de automação de build
- **MySQL 8**: Banco de dados relacional utilizado
- **Docker e Docker Compose**: Para orquestração dos containers da aplicação e banco de dados
- **Flyway**: Para versionamento e migração do schema do banco de dados
- **Swagger**: Para documentação da API (se configurado)
- **JUnit e Mockito**: Para testes unitários e de integração
- **Git Flow**: Estratégia de branching para controle de versão

## Versões

- Java: 21
- Spring Boot: 3.3.x
- Gradle: 8.5+
- MySQL: 8.0.x
- Docker Compose: 1.29+ (versão mínima recomendada)

## Como executar

1. Certifique-se de ter o Docker e o Docker Compose instalados.
2. Execute o comando abaixo na raiz do projeto para subir os containers:
   ```sh
   docker-compose up --build
   ```
3. A aplicação estará disponível em `http://localhost:8080`.

## Configuração do Banco de Dados

O banco de dados **MySQL** será iniciado automaticamente via Docker Compose, com as seguintes credenciais:

- Banco: `estoque_db`
- Usuário: `glee`
- Senha: `12345`

## Observações

- As configurações de acesso ao banco para o ambiente Docker são passadas como variáveis de ambiente no arquivo `docker-compose.yml`.
- Para customizações, altere as variáveis de ambiente conforme necessário.