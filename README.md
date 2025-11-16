## CRUD Graylog

Projeto acadêmico que demonstra a construção de uma API REST modularizada em duas camadas (módulo `domain` e módulo `framework`) usando Spring Boot 3, Java 21 e MySQL, com observabilidade de logs centralizados via Graylog (stack Graylog + MongoDB + OpenSearch) e envio de eventos GELF através do `logback-gelf`.

### Motivação da Implementação

1. Separação de responsabilidades: o módulo `domain` contém regras de negócio puras (entidade `Pessoa`, serviço `PessoaService`, exceções e contrato de repositório) sem dependências de framework, favorecendo testes e manutenção. O módulo `framework` faz a orquestração web (controllers, DTOs, configuração) e integrações (persistência, logging).
2. Padronização de logs estruturados: utilizar Graylog permite centralizar, filtrar e correlacionar erros e acessos. O appender GELF (`logback-spring.xml`) envia logs enriquecidos (localização, facility) para a porta UDP 12201, melhorando o diagnóstico em produção/acadêmico.
3. Observabilidade e rastreabilidade de falhas: o `RestExceptionHandler` registra erros não tratados garantindo que exceções virem eventos auditáveis no Graylog, acelerando troubleshooting.
4. Arquitetura evolutiva: com domínio desacoplado fica simples trocar persistência (ex.: migrar de JPA para outra tecnologia) ou adicionar novos casos de uso sem impactar camadas externas.
5. Demonstração prática de boas práticas: validação de dados, paginação, soft delete (campo `ativo`), e uso de DTOs para entrada controlada.

### Tecnologias Principais

- Java 21 / Records para modelagem imutável de dados (`Pessoa`).
- Spring Boot 3 (Web, Validation, Data JPA).
- MySQL como banco transacional (provisionado via Docker Compose).
- Graylog 5 + OpenSearch 2.4 + MongoDB para armazenamento e indexação de logs.
- Logback + Appender GELF (`de.siegmar:logback-gelf`) para envio dos logs estruturados.

### Estrutura dos Módulos

`domain/`
- Entidade: `Pessoa` (record com id, nome, dataNascimento, ativo).
- Serviço: `PessoaService` (CRUD, validações, paginação, soft delete).
- Exceções: `DadosInvalidosException`, `PessoaNaoEncontradaException`, `DomainException`.
- Repositório (interface): `PessoaRepository` - abstrai persistência.

`framework/`
- Bootstrap: `CrudGraylogApplication`.
- Controllers: `PessoaController` expondo endpoints REST `/pessoas`.
- DTOs: `CriarPessoaDto`, `AtualizarPessoaDto`, `ErrorDto`.
- Configuração: `PessoaServiceConfiguration` para wiring de beans.
- Implementação de repositório: `PessoaRepositoryImpl` (integra MySQL/JPA).
- Logging: configuração em `logback-spring.xml` (console + Graylog GELF).
- Tratamento de erros: `RestExceptionHandler` (gera `ErrorDto` e loga).

### Endpoints (Resumo)

- GET `/pessoas?pagina=1&tamanho=10` – Lista paginada de pessoas ativas.
- GET `/pessoas/{id}` – Obtém pessoa por id (ativo).
- POST `/pessoas` – Cria pessoa (JSON: nome, dataNascimento dd/MM/yyyy).
- PUT `/pessoas/{id}` – Atualiza pessoa existente.
- DELETE `/pessoas/{id}` – Soft delete (marca `ativo=false`).

### Execução Local

1. Subir infraestrutura Graylog + MySQL:
	```bash
	docker compose up -d
	```
2. Acessar Graylog em `http://localhost:9000` (usuário padrão admin / senha admin).
3. Criar entrada GELF UDP em Graylog (caso necessário) porta 12201 (System -> Inputs -> Select Input -> GELF UDP -> Launch new input -> Colocar nome -> Launch Input).
4. Executar build e aplicação:
	```bash
	./mvnw clean install
	./mvnw -pl framework spring-boot:run
	```
5. Testar criação:
	```bash
	curl -X POST http://localhost:8080/pessoas \
	  -H 'Content-Type: application/json' \
	  -d '{"nome":"Maria Silva","dataNascimento":"10/05/1990"}'
	```

### Observabilidade

Com o appender GELF ativo, logs da aplicação (pacote `br.fatec`) são enviados para Graylog incluindo stack traces e metadados. Isso facilita:
- Contar ocorrências de validações falhas.
- Criar dashboards de atividade CRUD.


