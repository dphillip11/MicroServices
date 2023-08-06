# Get Names Service

This service is configured in **application.properties** in the `src/main/resources` directory to communicate with the database service

The service exposes an API for adding, removing and retrieving names from the database. The routes are in the NameController.java file and each takes a json request body which includes the key-value pair for a name. The testing route is http://localhost:8080/api/names

Requests can be tested using postman which allows tailored requests to be sent.

Useful commands include:

``` docker run -p 8080:8080 getnamesservice:latest```

``` docker build -t getnamesservice:latest . ```    

I have added in a countdown that occurs after any successful requests to simulate service load. This will eventually be overcome by deploying containers on demand using container orchestration.
