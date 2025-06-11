# frontend/Dockerfile (в папке fitnessServer/client)

FROM node:20-alpine AS frontend-build
WORKDIR /app

# Копируем package.json и package-lock.json из текущей папки (client)
COPY package*.json ./

RUN npm install

# Копируем весь фронтенд-код
COPY . ./

RUN npm run build

FROM nginx:stable-alpine

COPY --from=frontend-build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
