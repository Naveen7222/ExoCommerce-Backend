FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "target/auth-service-1.0.0.jar"]