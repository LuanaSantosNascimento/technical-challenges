# Sistema de Simulação de empréstimo

Projeto desenvolvido para simular empréstimos com base nos dados fornecidos pelo usuário.

---

## Tecnologias utilizadas

- Java 21+
- Gradle
- JUnit 5 (para testes)
- Spring Boot
- MapStruct

---

## Pré-requisitos

- [Java 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Gradle](https://gradle.org/)
- IDE da sua preferência (IntelliJ, VS Code, Eclipse...)

---

## Como rodar o projeto

1. Clone o repositório:

```bash
https://github.com/{seu-usuario}/technical-challenges.git
cd technical-challenges/Creditas/api--simulador-emprestimos
```

2. Execute o projeto:

```bash
./gradlew bootRun
```

3. Acesse a API:

```bash
POST http://localhost:5000/api--simulador-credito/api/v1/emprestimos/simular
Request body:
{
    "valorEmprestimo": 10000,
    "dataNascimento": "2005-07-22",
    "prazoMeses": 12
}

Response:
{
    "valorEmprestimo": 10000,
    "prazoMeses": 12,
    "totalPago": 10256.40,
    "parcelaMensal": 854.70,
    "totalJurosPagos": 256.40,
    "taxaJurosAnual": 0.05,
    "taxaJurosMensal": 0.004
}
```
4. Para acessar a documentação da API, utilize o Swagger:

[Swagger: api--simulador-credito](http://localhost:5000/api--simulador-credito/swagger-ui/index.html)<br>
[JSON - Swagger: api--simulador-credito](http://localhost:5000/api--simulador-credito/v3/api-docs)

5. Para rodar os testes, execute:

```bash
./gradlew test
```

6. Para gerar o relatório de cobertura de testes, execute:

```bash
./gradlew clean test jacocoTestReport

O relatório será gerado em:
build/reports/coverage/index.html
```
---
## Estrutura do projeto

```bash
src/
├── main/
│   ├── java/
│   │   └── com/creditas/emprestimos/
│   │       ├── configs/
│   │       │       ├── http/
│   │       │       ├── mappers/
│   │       ├── controller/
│   │       │       ├── data/
│   │       │       │   ├── input/
│   │       │       │   ├── output/
│   │       │       ├── http/
│   │       │       ├── mappers/
│   │       ├── usecase/
│   │       │       ├── data/
│   │       │       │   ├── input/
│   │       │       │   ├── output/
│   │       │       ├── impl/
│   │       │       ├── mappers/
│   │       ├── gateway/
│   │       │       ├── data/
│   │       │       │   ├── input/
│   │       │       │   ├── output/
│   │       │       ├── impl/
│   │       │       ├── mappers/
│   │       │       ├── repository/
│   └── resources/
│       └── application.yml
└── test/
└── java/
└── com/creditas/emprestimos/
```
O projeto está estruturado com as camadas principais:

- **Controller**: Interface de entrada, responsável por receber e responder às requisições HTTP.
- **UseCase**: Implementa a lógica de negócio e regras centrais da aplicação.
- **Gateway e Repository (não implementados)**: Camada responsável por persistência e acesso a dados externos (banco de dados, integrações com APIs externas, etc).
- **Configs**: Configurações da aplicação.
