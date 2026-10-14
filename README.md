# HelpDeskBackend

AI-powered help desk backend built with Spring Boot, Spring AI, Ollama, MySQL, and JPA.

## Features
- AI assistant endpoint using Spring AI + Ollama
- JDBC-backed conversation memory for chat continuity
- Ticket storage and lifecycle management with JPA/MySQL
- CRUD API for tickets
- OpenAPI / Swagger documentation
- Health endpoints with Spring Actuator
- Validation and centralized exception handling

## Tech stack
- Java 21
- Spring Boot 3.3.5
- Spring AI 1.0.0
- MySQL 8
- Ollama
- Maven
- Springdoc OpenAPI

## Prerequisites
- Java 21+
- Maven 3.9+
- MySQL 8+
- Ollama installed and running locally

## Quick start

1. Start MySQL:

```bash
docker run --name helpdesk-mysql -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=helpdesk_db -p 3306:3306 -d mysql:8
```

or:

```bash
docker compose up -d
```

2. Start Ollama:

```bash
ollama serve
ollama pull llama3.1
```

3. Configure environment variables or update `application.properties`.

4. Run the application:

```bash
./mvnw spring-boot:run
```

## Environment variables
You can override the default settings using environment variables:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/helpdesk_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=your_password
export OLLAMA_BASE_URL=http://localhost:11434
export OLLAMA_MODEL=llama3.1
./mvnw spring-boot:run
```

## API endpoints

### Help desk assistant
- `POST /api/v1/helpdesk/response`
- `POST /api/v1/helpdesk/stream`

### Tickets
- `GET /api/v1/tickets`
- `GET /api/v1/tickets/{id}`
- `GET /api/v1/tickets/email/{email}`
- `POST /api/v1/tickets`
- `PUT /api/v1/tickets/{id}`

### Monitoring and docs
- `GET /actuator/health`
- `GET /swagger-ui.html`
- `GET /v3/api-docs`

## Example request

```http
POST /api/v1/helpdesk/response
Content-Type: text/plain
Conversation-Id: user-123

I forgot my password and need help resetting it.
```

## Notes
- The `Conversation-Id` header is used to give each user a separate chat memory context.
- The default model is `llama3.1`.
- The app is designed to work with local Ollama and MySQL.
