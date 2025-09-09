# frontend/Dockerfile (в папке fitnessServer/client)

# Этап сборки фронтенда с Node.js
FROM node:20-alpine AS frontend-build
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем весь фронтенд-код
COPY . ./

# Сборка фронтенда
RUN npm run build

# Production-образ на базе nginx
FROM nginx:stable-alpine

# Для безопасности создаём непользовательского пользователя
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Копируем собранный фронтенд
COPY --from=frontend-build /app/build /usr/share/nginx/html

# Пробрасываем порт
EXPOSE 80

# Запуск nginx в foreground
CMD ["nginx", "-g", "daemon off;"]

# Для анализа SonarQube: Node.js нужен только на этапе frontend-build,
# здесь он не нужен — SonarQube анализирует локальные файлы при сборке CI.
