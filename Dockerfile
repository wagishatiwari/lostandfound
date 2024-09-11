# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/lostandfound-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]