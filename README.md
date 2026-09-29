# PicPay Simplificado

API REST de transferências entre usuários, desenvolvida como solução do desafio técnico backend do PicPay. Existem dois tipos de carteira: **usuários comuns**, que enviam e recebem dinheiro, e **lojistas**, que apenas recebem.

## Tecnologias

- Java 21
- Spring Boot 4 (Web, Data JPA, Validation)
- Spring Cloud OpenFeign (integração com serviços externos)
- PostgreSQL 16
- Docker / Docker Compose
- Lombok
- Maven

## Regras de negócio

- O cadastro exige nome completo, e-mail, senha e CPF/CNPJ.
- CPF e e-mail são únicos no sistema.
- O tipo da carteira é definido pelo documento: **CPF (11 dígitos) = usuário comum** e **CNPJ (14 dígitos) = lojista**.
- Lojistas **não podem** enviar transferências, só receber.
- O pagador precisa ter saldo suficiente.
- Não é permitido transferir para si mesmo.
- Antes de concluir, a transferência consulta um **serviço autorizador externo**.
- A operação é **transacional**: se algo falhar, o saldo dos dois lados é revertido.
- Após a transferência, uma **notificação** é enviada ao recebedor por um serviço externo. Se esse serviço estiver fora do ar, a falha é registrada em log e a transferência **não é desfeita**.

## Arquitetura

O projeto segue uma divisão em camadas:

```
controller  ->  service  ->  repository  ->  banco de dados
                   |
                   +-> client (OpenFeign) -> autorizador / notificação
```

```
src/main/java/com/vitortheof/picpay
├── client        # Feign clients (autorizador e notificação) e seus DTOs
├── controller    # Endpoints REST
├── dto           # Objetos de entrada e saída
├── entity        # Entidades JPA (User, Transaction, WalletType)
├── exceptions    # Exceções de negócio e GlobalExceptionHandler
├── mapper        # Conversão entidade <-> DTO
├── repository    # Repositórios Spring Data JPA
└── service       # Regras de negócio
```

## Como executar

### Pré-requisitos

- JDK 21
- Docker e Docker Compose

### Passo a passo

1. Clone o repositório:

```bash
git clone https://github.com/vtzada/picpay-simplificado-desafio.git
cd picpay
```

2. Suba o banco de dados PostgreSQL:

```bash
docker compose -f docker/docker-compose.yml up -d
```

3. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

### Criar usuário

`POST /users`

```json
{
  "fullName": "Maria Silva",
  "email": "maria@email.com",
  "password": "123456",
  "cpfCnpj": "12345678901"
}
```

Resposta: `201 Created`

### Realizar transferência

`POST /transfer`

```json
{
  "value": 100.00,
  "payer": 1,
  "payee": 2
}
```

Resposta: `200 OK`

```json
{
  "value": 100.00,
  "payer": 1,
  "payee": 2
}
```

## Tratamento de erros

Todos os erros seguem um formato padronizado, com data e hora, status, mensagem e caminho da requisição.

| Situação                                                        | Status |
| --------------------------------------------------------------- | ------ |
| Campos inválidos ou ausentes                                    | 400    |
| Usuário não encontrado                                          | 404    |
| E-mail ou CPF/CNPJ já cadastrado                                | 409    |
| Saldo insuficiente, lojista pagador, autorização negada e outros | 422    |

## Melhorias futuras

- Armazenar senhas com hash (BCrypt / Spring Security)
- Testes unitários e de integração (JUnit, Mockito, Testcontainers)
- Controle de concorrência nas transferências (lock otimista ou pessimista)
- Migrations com Flyway
- Documentação com Swagger/OpenAPI
- Validação de CPF/CNPJ

## Autor

**Vitor**
[GitHub](https://github.com/vtzada) • [LinkedIn](https://www.linkedin.com/in/vitortheodoro)
