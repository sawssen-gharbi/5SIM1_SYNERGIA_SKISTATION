FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 mvn package

# Stage 2: Create the runtime container
FROM openjdk:11-jre-slim
EXPOSE 8089

# Install curl in the container
RUN apk update && apk add --no-cache curl

# Define Nexus URL and Artifact Path (replace with your actual values)
ARG NEXUS_URL="http://localhost:8081/repository/maven-releases/"
ARG ARTIFACT_PATH="tn/esprit/spring/gestion-station-ski/1.0/gestion-station-ski-1.0.jar"

RUN curl -o /gestion-station-ski-1.0.jar ${NEXUS_URL}${ARTIFACT_PATH}

# Copy the built JAR file from the builder stage to the container
COPY --from=builder /app/target/gestion-station-ski-1.0.jar /gestion-station-ski-1.0.jar

# Set Java options and define entry point
ENV JAVA_OPTS="-Dlogging.level.org.springframework.security=DEBUG -Djdk.tls.client.protocols=TLSv1.2"
ENTRYPOINT ["java", "-jar", "/gestion-station-ski-1.0.jar"]

