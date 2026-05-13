# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only current service (because root dir = order-service)
COPY . .

RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built jar (auto-detect)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]