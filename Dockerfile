
FROM openjdk:8-jdk-alpine


EXPOSE 8089


ADD target/gestion-station-ski-0.0.1-RELEASE.war gestion-station-ski.jar


ENTRYPOINT ["java", "-jar", "/gestion-station-ski-1.0.jar"]
