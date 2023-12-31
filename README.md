# MicroServices

### Aim

Create a simple application that uses microservices and stateless services with the exception of a database that can be deployed and scaled
## Architecture
                                          +-----------------+
                                          |    Frontend     |
                                          |  Application    |
                                          +-----------------+
                                                |  ^
                                                |  |
                                                V  |
                                          +-----------------+
                                          |   Gateway API   |
                                          |(Docker Container)|
                                          +-----------------+
                                     ^ /           | ^          \ ^
                                    / /            | |           \ \
                                   / /             | |            \ \
                                  / /              | |             \ \
                                 / /               | |              \ \
                                / /                | |               \ \
                               / V                 V |                V \
          +-----------------+            +-----------------+            +-----------------+
          |    getNames     |            |getDecoratedNames|            | getLetterCount |
          |(Docker Container)|            |(Docker Container)|            |(Docker Container)|
          +-----------------+            +-----------------+            +-----------------+
                 ^ |                                                            
                 | V                                                            
        +-----------------+                                         
        | PostgreSQL DB   |                                         
        |(Docker Container)|                                         
        +-----------------+                                          

## Plan

Microservices application using Java, Docker, and PostgreSQL.

### 1. Java Applications

Three different Java applications - getNames, getDecoratedNames, and getLetterCount.

Spring Boot to build these services. Each service will have its own pom.xml file for managing dependencies, and a main class to bootstrap the application.

Each application will expose RESTful APIs:

getNames service: This will connect to your PostgreSQL database and fetch the names. Uses Spring's JPA repository to manage database operations.

getDecoratedNames service: This service will call the getNames API and then add "decorated " to each name before returning the result.

getLetterCount service: This service will either call the getNames API or the getDecoratedNames API, calculate the letter counts, and then return the result.

### 2. Dockerize Java Applications

Dockerfile for each application.

Dockerfile for a Spring Boot application:

Dockerfile
Copy code
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /usr/src/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
This Dockerfile first builds the application using Maven, and then creates a final image based on OpenJDK with the packaged jar file.

Build the Docker images for each service using the docker build command, and then push the images to a Docker registry.

### 3. Create Docker Compose File

Use Docker Compose to manage application's services. With Docker Compose, start all application's services with a single command.

Example of docker-compose.yml:

yaml
Copy code
version: '3'
services:
  getNames:
    image: getnames:latest
    ports:
      - 8081:8080
  getDecoratedNames:
    image: getdecoratednames:latest
    ports:
      - 8082:8080
  getLetterCount:
    image: getlettercount:latest
    ports:
      - 8083:8080
  db:
    image: postgres:latest
    ports:
      - 5432:5432
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: namesdb
Gateway application that routes the requests from the client to the corresponding services.

### 4. API Gateway

The Gateway application will be another Spring Boot application, which will use Spring Cloud Gateway. This application will also be Dockerized.

The Gateway will call the necessary services based on the request and return the result to the client. Created and started on demand, which could be done using a Kubernetes orchestration system, allowing deployment and scaling of services as needed.

### 5. Frontend Application

This could be a simple HTML/CSS/JavaScript webpage, or a full-fledged Angular application.

Your frontend application will have the three buttons, which when clicked, will make AJAX calls to the Gateway application, and then display the result.
