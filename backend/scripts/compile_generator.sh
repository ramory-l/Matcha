#!/bin/bash

mvn -f ../generator clean compile assembly:single
mv ./../generator/target/UsersGenerator.jar ./../docker
rm -rf ../generator/target