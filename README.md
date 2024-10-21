# Gerenciamento de Projetos

## Descrição do Projeto

O **Gerenciamento de Projetos** é um sistema de controle de projetos que permite o cadastro e a gestão de projetos, membros e suas respectivas atribuições. O sistema oferece funcionalidades como a criação, edição, listagem e exclusão de projetos. Além disso, é possível gerenciar os membros atribuídos a cada projeto, verificar o status do projeto e atribuir riscos aos mesmos.

## Funcionalidades

- **Cadastro de Projetos**: O sistema permite a criação de novos projetos, onde o usuário pode definir o nome, data de início, previsão de término, orçamento, status, riscos e o gerente responsável pelo projeto.

- **Gerenciamento de Membros**: Permite adicionar e gerenciar membros que fazem parte de um projeto.

- **Listagem de Projetos**: O usuário pode listar todos os projetos cadastrados, visualizar os detalhes de um projeto específico e editar as informações de um projeto existente.

- **Atribuição de Gerente e Funcionários**: O sistema permite selecionar um gerente e associar funcionários aos projetos.

- **Validações de Exclusão**: O sistema implementa regras de negócio para impedir a exclusão de projetos que estão em andamento, iniciados ou encerrados.

- **Cadastro de Pessoas (Gerente/Funcionário)**: Para adicionar uma nova pessoa, seja gerente ou funcionário, use o endpoint `/api/pessoa` (POST) que recebe um JSON com os dados da pessoa. Exemplo:

```bash
curl --location 'http://localhost:8080/api/pessoa' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Kleber Silva",
    "dataNascimento": "1999-09-01",
    "cpf": "00000000000",
    "funcionario": true,
    "gerente": false
}'
```

## Estrutura do Projeto

O projeto está estruturado seguindo o padrão **MVC** (Model-View-Controller):

- **Model**: Contém as entidades do sistema, como `Projeto`, `Pessoa` e `Membros`.

- **View**: As views são implementadas em **JSP**, utilizadas para renderizar o frontend das funcionalidades do sistema, como a listagem e edição de projetos.

- **Controller**: Os controladores estão divididos entre APIs REST e controladores de visualização para a interação com o frontend.

- **Service**: A camada de serviço contém a lógica de negócios, como a validação de regras de exclusão e a lógica para adicionar ou remover membros dos projetos.

- **Repository**: Interface para interação com o banco de dados, utilizando Spring Data JPA para operações de CRUD.

## Tecnologias Utilizadas

- **Java 17**: Linguagem principal do projeto.
- **Spring Boot 3.3.4**: Framework utilizado para a criação da aplicação backend.
    - **Spring Data JPA**: Para a persistência de dados e interação com o banco de dados.
    - **Spring Web**: Para a criação das APIs REST.
    - **Spring Validation**: Para a validação dos dados.
- **JSP (Java Server Pages)**: Utilizado para renderização do frontend.
- **Flyway**: Para migração e controle de versões do banco de dados.
- **PostgreSQL**: Banco de dados utilizado no projeto.
- **JUnit 5 e Mockito**: Para testes automatizados.

## Requisitos

- **Java 17** ou superior.
- **Maven**: Ferramenta para gerenciamento de dependências e build.
- **PostgreSQL**: Banco de dados para armazenamento de dados.

## Configuração do Ambiente

### Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/gerenciamento-projetos.git
cd gerenciamento-projetos
```
### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL para o projeto.
2. Configure as credenciais do banco de dados no arquivo `application.properties`:

```properties
spring.application.name=gerenciamento-projetos
spring.datasource.url=jdbc:postgresql://localhost:5432/db_gerenciamento_projetos
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.flyway.baseline-on-migrate=true
server.port=8080
spring.flyway.enabled=true

spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

### Build e Execução

1. Compile e execute o projeto usando **Maven**:

```bash
mvn clean install
mvn spring-boot:run
````

2. O aplicativo estará disponível em `http://localhost:8080`.

### Acessando o Sistema

- Acesse a aplicação em seu navegador através da URL mencionada anteriormente:

```
http://localhost:8080
```

## Endpoints Principais

- **/api/projetos** (GET): Retorna todos os projetos cadastrados.
- **/api/projetos** (POST): Cria um novo projeto.
- **/api/projetos** (PUT): Altera os dados de um projeto já existente.
- **/api/projetos/{id}** (GET): Retorna os detalhes de um projeto específico.
- **/api/projetos/{id}** (DELETE): Remove um projeto (respeitando as regras de exclusão).
- **/api/pessoa** (POST): Cria uma nova pesoa.
- **/api/pessoa/autocomplete/gerentes** (GET): Retorna a lista de gerentes filtrados por nome.
- **/api/pessoa/autocomplete/funcionarios** (GET): Retorna a lista de funcionarios filtrados por nome.

## Testes Automatizados

O projeto possui testes automatizados para as principais funcionalidades da aplicação, incluindo:

- Testes de criação e listagem de projetos.
- Testes de validação para exclusão de projetos.
- Testes de regras de negócio relacionadas a membros e gerentes.

Para rodar os testes:

```bash
mvn test
```
