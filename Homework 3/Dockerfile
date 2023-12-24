## Use an official OpenJDK runtime as a parent image
#FROM openjdk:17-jdk-alpine
#
## Set the working directory to /app
#WORKDIR /app
#
## Add PostgreSQL JDBC driver
#RUN mkdir -p /app/lib
#WORKDIR /app/lib
#RUN wget https://jdbc.postgresql.org/download/postgresql-42.2.23.jar
#
#
## Copy the current directory contents into the container at /app
#COPY . /app
#
## Make port 8080 available to the world outside this container
#EXPOSE 8080
#
## Run spring boot application
#CMD ["mvn", "spring-boot:run"]
FROM maven:3.8.4-openjdk-17-slim

WORKDIR /app

COPY . /app

EXPOSE 8080

CMD ["mvn", "spring-boot:run"]
