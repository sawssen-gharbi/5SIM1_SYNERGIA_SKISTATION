# Stage 1: Build the application
FROM maven:3.8.3-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 mvn package

# Stage 2: Create the runtime container
FROM openjdk:11-jre-slim
EXPOSE 8089

# Install curl in the container
RUN apt-get update && apt-get install -y curl

# Define Nexus URL and Artifact Path (replace with your actual values)
ARG NEXUS_URL="http://192.168.33.10:8081/repository/maven-releases/"
ARG ARTIFACT_PATH="tn/esprit/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"

# Download the .jar file from Nexus and copy it to the container
RUN curl -o /gestion-station-ski-1.0.jar ${NEXUS_URL}${ARTIFACT_PATH}

# Copy the built JAR file from the builder stage to the container
COPY --from=builder /app/target/gestion-station-ski-1.0.jar /gestion-station-ski-1.0.jar

# Set Java options and define entry point
ENV JAVA_OPTS="-Dlogging.level.org.springframework.security=DEBUG -Djdk.tls.client.protocols=TLSv1.2"
ENTRYPOINT ["java", "-jar", "/gestion-station-ski-1.0.jar"]
