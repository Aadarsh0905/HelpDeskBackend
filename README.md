# HelpDeskBackend

A production-ready Spring Boot backend for an AI-powered help desk assistant.

## Features
- AI help desk assistant using Spring AI + Ollama
- JDBC-backed chat memory for conversation continuity
- MySQL persistence for ticket records
- REST APIs for ticket lifecycle management
- OpenAPI / Swagger documentation
- Health metrics via Spring Actuator

## Prerequisites
- Java 21+
- Maven 3.9+
- MySQL 8+
- Ollama installed locally

## Local setup

### Option 1: MySQL via Docker
```bash
docker run --name helpdesk-mysql -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=helpdesk_db -p 3306:3306 -d mysql:8
```

### Option 2: Use Docker Compose
```bash
docker compose up -d
```

### Start Ollama
```bash
ollama serve
ollama pull llama3.1
```

### Run the app
```bash
./mvnw spring-boot:run
```

### Optional: override environment variables
```bash
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=your_password
export OLLAMA_MODEL=llama3.1
./mvnw spring-boot:run
```

## API endpoints

### AI assistant
- `POST /api/v1/helpdesk/response`
- `POST /api/v1/helpdesk/stream`

### Tickets
- `GET /api/v1/tickets`
- `GET /api/v1/tickets/{id}`
- `GET /api/v1/tickets/email/{email}`
- `POST /api/v1/tickets`
- `PUT /api/v1/tickets/{id}`

### Docs and health
- `GET /swagger-ui.html`
- `GET /v3/api-docs`
- `GET /actuator/health`

## Notes
- `Conversation-Id` is used as the chat memory conversation header.
- Default model: `llama3.1`.
