# --- Этап 1: Сборка backend (maven) ---
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# --- Этап 2: Сборка frontend (node) ---
FROM node:20-alpine AS frontend-build
WORKDIR /app/client

COPY client/package*.json ./
RUN npm install

COPY client/ ./
RUN npm run build

# --- Этап 3: Финальный образ ---
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Копируем backend JAR
COPY --from=backend-build /app/target/fitness-server-0.0.1-SNAPSHOT.jar app.jar

# Копируем собранный frontend
COPY --from=frontend-build /app/client/build ./client/build

EXPOSE 8080

# Запуск backend приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
