# HelpDeskBackend

A Spring Boot backend for an AI-powered help desk assistant.

## Features
- AI ticket support assistant
- JDBC chat memory for conversation context
- MySQL persistence
- Ticket CRUD endpoints

## Prerequisites
- Java 21
- Maven 3.9+
- MySQL 8
- Ollama running locally

## Run locally
1. Start MySQL:
   ```bash
   docker run --name helpdesk-mysql -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=helpdesk_db -p 3306:3306 -d mysql:8
