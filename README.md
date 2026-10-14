# HelpDeskBackend

A production-ready Spring Boot backend for an AI-powered help desk assistant.

## Features
- AI help desk assistant using Spring AI + Ollama
- JDBC-backed chat memory for conversation continuity
- MySQL persistence for help desk tickets
- Ticket CRUD APIs
- Health monitoring through Spring Actuator

## Prerequisites
- Java 21+
- Maven 3.9+
- MySQL 8+
- Ollama installed locally

## Local setup

1. Start MySQL:
   ```bash
   docker run --name helpdesk-mysql -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=helpdesk_db -p 3306:3306 -d mysql:8
   ```

2. Start Ollama:
   ```bash
   ollama serve
   ```

3. Pull a model:
   ```bash
   ollama pull llama3.1
   ```

4. Update database credentials in `src/main/resources/application.properties` if needed.

5. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

## API endpoints

### AI assistant
- `POST /api/v1/helpdesk/response`
- `POST /api/v1/helpdesk/stream`

### Tickets
- `GET /api/v1/tickets`
- `GET /api/v1/tickets/{id}`
- `POST /api/v1/tickets`
- `PUT /api/v1/tickets/{id}`

### App health
- `GET /actuator/health`

## Notes
- The app uses `Conversation-Id` as the header for chat memory continuity.
- The default model is `llama3.1` and can be changed in `application.properties`.
