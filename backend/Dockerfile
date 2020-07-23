# Use a lighter version of OpenJDK as a parent image
FROM openjdk:8-jdk-alpine as build
# Set the working directory to /backend
WORKDIR /backend
# Copy maven utility into the container
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
# Run maven utility to build app
RUN ./mvnw install -DskipTests
# Extract jar files into target/dependency folder
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Docker Multi-Stage Build
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/backend/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Run app
ENTRYPOINT ["java","-cp","app:app/lib/*","ru.matcha.backend.BackendApplication"]