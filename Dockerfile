# ==========================================
# Stage 1: Build React Frontend
# ==========================================
FROM node:20-alpine AS frontend-builder
WORKDIR /app/frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ ./
RUN npm run build

# ==========================================
# Stage 2: Build Spring Boot Backend with React bundle
# ==========================================
FROM maven:3.9-eclipse-temurin-21-alpine AS backend-builder
WORKDIR /app

COPY pom.xml ./
# Pre-fetch dependencies for faster build caching
RUN mvn dependency:go-offline -B || true

# Copy frontend build output into Spring Boot's static assets directory
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static/

COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 3: Lightweight Production Container
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=backend-builder /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
