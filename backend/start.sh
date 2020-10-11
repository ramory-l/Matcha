#!/bin/bash
mvn clean dependency:copy-dependencies package
java -cp target/matcha-0.0.1-SNAPSHOT.jar ru.school.matcha.MatchaApplication
