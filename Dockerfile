# backend/Dockerfile

# Этап сборки приложения с Maven и тестами
FROM maven:3.9.6-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Копируем pom и исходники
COPY pom.xml ./ 
COPY src ./src

# Сборка jar с выполнением тестов для генерации покрытия
RUN mvn clean package

# Production-образ на базе Alpine JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Создаём непользовательского пользователя для безопасности
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Копируем собранный jar из этапа сборки
COPY --from=backend-build /app/target/fitness-server-0.0.1-SNAPSHOT.jar app.jar

# Пробрасываем порт приложения
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
