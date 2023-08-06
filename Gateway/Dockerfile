# Use the official OpenJDK base image
FROM openjdk:20-jdk-slim

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's JAR file to the container
COPY ${JAR_FILE} app.jar

# Specify the command to run on container start
CMD ["java", "-jar", "/app.jar"]
