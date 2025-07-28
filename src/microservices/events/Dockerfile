FROM maven:3.9.3-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8082
ARG JAR_FILE=events-service-1.0.0.jar
COPY --from=build /app/target/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]