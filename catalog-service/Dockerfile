FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/catalog-service-0.0.1-SNAPSHOT.jar catalog-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
