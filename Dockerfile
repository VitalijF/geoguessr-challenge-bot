# We select the base image from. Locally available or from https://hub.docker.com/
# This image name must match entry in Jenkinsfile
FROM eclipse-temurin:21.0.1_12-jdk

# Update image dependencies
RUN apt-get update && apt-get upgrade -y && rm -rf /var/lib/apt/lists/*


# Add the application's jar to the container
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]