# backend/Dockerfile

# Сборка приложения
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Копируем pom и исходники
COPY pom.xml ./
COPY src ./src

# Сборка jar без запуска тестов
RUN mvn clean package -DskipTests

# Production-образ
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Создаём непользовательского пользователя для безопасности
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Копируем собранный jar из этапа сборки
COPY --from=backend-build /app/target/fitness-server-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
