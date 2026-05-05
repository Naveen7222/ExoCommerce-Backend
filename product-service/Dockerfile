FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar app.jar

# optional (just documentation)
EXPOSE 8080

ENTRYPOINT ["java", "-Xms64m", "-Xmx128m", "-jar", "app.jar"]