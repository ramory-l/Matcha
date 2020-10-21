#!/bin/bash

mvn -f ../. clean compile assembly:single
mv ./../target/Matcha.jar ./../docker
rm -rf ../target