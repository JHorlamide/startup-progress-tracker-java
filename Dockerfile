# The base image is set to maven:3.8.3-openjdk-11-slim,
# which includes Maven for building the application.
FROM maven:3.8.3-openjdk-11-slim AS build

# The working directory for the build stage is set to /app.
WORKDIR /app

# The first stage, build, sets up the Maven build environment and
# copies the project's pom.xml file. It then resolves the dependencies
# using mvn dependency:go-offline to optimize subsequent builds.
COPY pom.xml .
RUN mvn dependency:go-offline

# The src directory is copied into the build environment, and the
# application is packaged using mvn package -DskipTests
# (skipping tests for the Docker build).
COPY src ./src
RUN mvn package -DskipTests

# The second stage starts with a new base image openjdk:11-jre-slim
# to reduce the image size.
FROM openjdk:11-jre-slim

# The working directory is set to /app.
WORKDIR /app

# The JAR file built in the build stage is copied from the
# previous stage using COPY --from=build.
COPY --from=build /app/target/*.jar app.jar

# Port 8080 is exposed to allow external access.
EXPOSE 8080

# The CMD instruction specifies the command to run the Spring Boot
# application using the JAR file
CMD ["java", "-jar", "app.jar"]
