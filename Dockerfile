FROM openjdk:21
MAINTAINER kojukwu.com
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]