FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "target/eureka-service-1.0.0.jar"]