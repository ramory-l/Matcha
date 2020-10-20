#!/bin/bash

rm -rf ./jars
mkdir jars
docker run -it --rm --name backend -v "$(pwd)/backend":/usr/src/backend -w /usr/src/backend maven:3.3-jdk-8 mvn clean compile assembly:single
mv ./backend/target/Matcha.jar ./jars/
rm -rf backend/target
docker run -it --rm --name generator -v "$(pwd)/generator":/usr/src/generator -w /usr/src/generator maven:3.3-jdk-8 mvn clean compile assembly:single
mv ./generator/target/usersGenerator.jar ./jars/
rm -rf generator/target
docker build -t backend . --file backend/Dockerfile
docker build -t database . --file db/Dockerfile
rm -rf jars