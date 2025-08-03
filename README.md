# gerenciar_estoque
## Objetivo

Este projeto é uma aplicação de controle de estoque desenvolvida em Java utilizando o framework Spring Boot. O objetivo é fornecer uma API para gerenciamento de produtos em estoque, incluindo operações de cadastro, consulta, atualização e remoção.

## Tecnologias e Ferramentas Utilizadas

- **Java**: Linguagem principal do projeto
- **Spring Boot**: Framework para desenvolvimento da aplicação
- **Gradle**: Ferramenta de automação de build
- **PostgreSQL 16**: Banco de dados relacional utilizado
- **Docker e Docker Compose**: Para orquestração dos containers da aplicação e banco de dados

## Versões

- Java: 21+ (recomendado)
- Spring Boot: 3.x
- Gradle: 7.x ou superior
- PostgreSQL: 16.x
- Docker Compose: 1.29+ (versão mínima recomendada)

## Como executar

1. Certifique-se de ter o Docker e o Docker Compose instalados.
2. Execute o comando abaixo na raiz do projeto para subir os containers:
3. A aplicação estará disponível em `http://localhost:8080`.

## Configuração do Banco de Dados

O banco de dados PostgreSQL será iniciado automaticamente via Docker Compose, com as seguintes credenciais:

- Banco: `estoque_db`
- Usuário: `glee`
- Senha: `12345`

## Observações

- As configurações de acesso ao banco estão definidas no arquivo `docker-compose.yml`.
- Para customizações, altere as variáveis de ambiente conforme necessário.