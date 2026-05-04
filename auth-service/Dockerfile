FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY auth-service ./auth-service
WORKDIR /app/auth-service
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/auth-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]