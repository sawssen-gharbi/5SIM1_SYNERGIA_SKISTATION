FROM openjdk:8-jdk-alpine

EXPOSE 8089

COPY target/gestion-station-ski-1.0.jar gestion-station-ski.jar

ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
