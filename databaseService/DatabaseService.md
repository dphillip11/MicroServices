# Database Service

The database will use postgres, a container should be prepared using a postgres base image.

This is done by creating or modifying a docker-compose.yml configuration file and adding a db service.

The environment variables include the username and password for the database and the port.

The existing services in the configuration file are launched using the command

 "docker compose up -d"

The running containers can be viewed and their names can be checked using

 "docker ps"

In this example the db service is called microservices-db-1

To interact with the container, the following command is used -d 

  "docker exec -it microservices-db-1 bash "

login

  "psql -U postgres -d namesdb"

After this the database can be updated and used by making SQL queries.


