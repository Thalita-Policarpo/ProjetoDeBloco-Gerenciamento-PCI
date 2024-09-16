# Gerenciamento de PCI

## Descrição do Projeto
O **Bloco de Gerenciamento de PCI** é um sistema desenvolvido em Java utilizando a arquitetura de microsserviços para gerenciar Extintores, Inspeções e Históricos de alterações. O projeto é composto por três microsserviços principais que se comunicam entre si utilizando o Eureka Server e um Gateway para roteamento. Além disso, RabbitMQ é utilizado para troca de mensagens entre os microsserviços.

## Microsserviços

### 1. **Eureka Server**
Responsável pelo serviço de registro e descoberta dos microsserviços.

### 2. **Extintor Service**
Gerencia o cadastro, atualização, busca e exclusão de extintores. Este serviço armazena os extintores e suas respectivas informações, incluindo inspeções realizadas. Utiliza RabbitMQ para enviar mensagens relacionadas a alterações no cadastro dos extintores para o serviço de histórico.

### 3. **InspecaoExtintor Service**
Gerencia as inspeções realizadas nos extintores. Permite o cadastro de inspeções e realiza validações automáticas, determinando o status "Conforme" ou "Não Conforme" com base nos resultados. As inspeções são vinculadas ao extintor por meio de mensagens enviadas via RabbitMQ.

### 4. **HistoricoExtintor Service**
Responsável por armazenar as alterações realizadas nos extintores. Sempre que um extintor é criado, atualizado ou excluído, uma mensagem é enviada para esse serviço, que armazena a operação realizada e os dados do extintor no momento da mudança.

### 5. **Gateway Service**
Roteia as requisições para os serviços corretos utilizando o serviço de descoberta Eureka. Também é responsável por centralizar o acesso aos microsserviços, fornecendo um ponto de entrada único.

### 6. **Swagger Aggregator**
Centraliza a documentação Swagger de todos os microsserviços, permitindo que a API de cada serviço seja visualizada de forma unificada.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Cloud Netflix Eureka** - Para registro e descoberta de serviços.
- **Spring Cloud Gateway** - Para roteamento de requisições.
- **Spring Data JPA** - Para persistência de dados.
- **H2 Database** - Banco de dados em memória.
- **RabbitMQ** - Para troca de mensagens entre os microsserviços.
- **Springdoc OpenAPI** - Para documentação e exposição da API através do Swagger UI.
- **Lombok** - Para reduzir boilerplate de código.
- **Feign Client** - Para comunicação entre microsserviços.

## Funcionalidades Principais
- **Cadastro e Gerenciamento de Extintores**: Cadastro de extintores com validação de dados, além de busca, atualização e exclusão de extintores.
- **Realização de Inspeções**: Registra inspeções associadas a extintores, com validação automática do status com base nas condições verificadas.
- **Histórico de Alterações**: Armazena todas as operações de criação, atualização e exclusão realizadas em extintores.
- **Documentação Unificada**: O Swagger Aggregator oferece uma interface unificada para acesso à documentação de todos os serviços.

## Configuração e Execução

### Pré-requisitos
- **Java 17**
- **Maven**
- **RabbitMQ** instalado e rodando
- **H2 Database** (já configurado no projeto)

### Passos para Execução
1. **Clone o repositório** e entre no diretório do projeto.
2. Inicie o **Eureka Server** para que os microsserviços possam se registrar.
3. Inicie cada microsserviço individualmente na seguinte ordem:
   - Extintor Service
   - InspecaoExtintor Service
   - HistoricoExtintor Service
4. Execute o **Gateway Service** para centralizar o roteamento.
5. Acesse a documentação da API através do **Swagger Aggregator** utilizando a URL `/swagger-ui.html`.

### URLs Importantes
- **Eureka Server**: `http://localhost:8761`
- **Gateway Service**: `http://localhost:8081`
- **Swagger UI (Aggregator)**: `http://localhost:8080/swagger-ui.html`

## Licença
Este projeto está licenciado sob a licença MIT.
