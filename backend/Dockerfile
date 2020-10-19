FROM maven:3.6.3-jdk-8

WORKDIR /backend

EXPOSE 8080
ENV TZ=Europe/Moscow

ARG JAR_FILE=target/*.jar
# Copy backend app into the container at /backend
COPY ${JAR_FILE} /backend/app.jar
# Run the app when the container launches
CMD ["java","-jar","app.jar"]