#!/bin/bash

mvn -f ../generator clean compile assembly:single
mv ./../generator/target/usersGenerator.jar ./../docker
rm -rf ../generator/target