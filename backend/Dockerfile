# Use a lighter version of OpenJDK as a parent image
FROM openjdk:8-jdk-alpine
# Set the working directory to /backend
WORKDIR /backend
# Set path to app variable
ARG JAR_FILE=target/*.jar
# Copy backend app into the container at /backend
COPY ${JAR_FILE} /backend/app.jar
# Run the app when the container launches
CMD ["java","-jar","app.jar"]