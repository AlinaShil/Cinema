# ---------- STAGE 1: Build ----------
FROM maven:3.9.3-eclipse-temurin-17-alpine AS build

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем pom.xml и загружаем зависимости (для кэширования в Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходники и собираем приложение
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:17-jre-jammy

# Порт, на котором запускается Spring Boot
EXPOSE 8080

# Копируем собранный JAR из предыдущего этапа
ARG JAR_FILE=proxy-service-1.0.0.jar
COPY --from=build /app/target/${JAR_FILE} /app/app.jar

# Устанавливаем точку входа
ENV SERVER_PORT=8000
ENTRYPOINT ["java","-jar","/app/app.jar"]