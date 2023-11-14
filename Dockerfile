FROM openjdk:8-jdk-alpine

EXPOSE 8089

ADD target/gestion-station-ski-1.0-RELEASE.jar gestion-station-ski.jar

ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
