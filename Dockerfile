FROM maven:3.9.6-amazoncorretto-17 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean install

FROM openjdk:17 AS app-run
EXPOSE 8080
RUN mkdir /opt/app

ARG JAR_FILE=shop-item-microservice.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","shop-item-microservice.jar"]
