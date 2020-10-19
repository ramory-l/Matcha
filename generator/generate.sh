#!/bin/bash

mvn clean compile assembly:single
java -cp target/usersGenerator.jar org.example.Main 500
