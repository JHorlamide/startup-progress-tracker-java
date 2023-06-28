FROM openjdk:11-jdk-slim

WORKDIR /app

# Copy the executable JAR file into the container
COPY target/your-app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
