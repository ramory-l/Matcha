FROM openjdk:8u265-jdk
WORKDIR /backend
COPY ../jars/Matcha.jar .
EXPOSE 8080
ENV TZ=Europe/Moscow
CMD ["java","-cp","Matcha.jar", "ru.school.matcha.MatchaApplication"]