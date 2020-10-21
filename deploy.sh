#!/bin/bash

docker run -it --rm --name backend -v "$(pwd)/backend":/usr/src/backend -w /usr/src/backend maven:3.3-jdk-8 mvn clean compile assembly:single
mv backend/target/Matcha.jar backend/docker
rm -rf backend/target
docker run -it --rm --name generator -v "$(pwd)/backend/generator":/usr/src/generator -w /usr/src/generator maven:3.3-jdk-8 mvn clean compile assembly:single
mv backend/generator/target/UsersGenerator.jar backend/docker
rm -rf backend/generator/target
cp backend/src/main/resources/db/sql/init_db.sql backend/docker
docker-compose build
mkdir backend/jars
mv backend/docker/usersGenerator.jar backend/jars
mv backend/docker/Matcha.jar backend/jars
rm -f backend/docker/init_db.sql
docker-compose up