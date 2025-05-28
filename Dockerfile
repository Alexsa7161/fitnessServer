# --- Этап 1: Сборка JAR ---
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Копируем pom.xml и src в рабочую директорию
COPY pom.xml .
COPY src ./src

# Собираем jar
RUN mvn clean package -DskipTests

# --- Этап 2: Финальный образ ---
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=build /app/target/fitness-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
