# Item-microservice

Application exposes a CRUD api to manage shop items.
Shop items are stored in an in-memory database. 
There is no memory limit - it is dependent on application memory - use at your own risk.

# Application security
Application endpoints are secured with username+password authentication (default user//user1234)

The credentials can be configured via SPRING_SECURITY_USER_NAME and SPRING_SECURITY_USER_PASSWORD env variables

# Running application from containers

1. Run command 'docker compose up' from  root directory of project

2. Swagger is available at http://localhost:8080/swagger-ui/index.html

# Running spring application with mvn

1. Run command 'docker compose up -d kafka zookeeper' from  root directory of project

2. Set JAVA_HOME env variable pointing to Java17 distribution

3. ./mvnw spring-boot:run

4. Swagger is available at http://localhost:8080/swagger-ui/index.html
