#!/bin/bash

if [ -n "$1" ]; then
  case "$1" in
  -c) mvn clean ;;
  -s) java -cp target/Matcha.jar ru.school.matcha.MatchaApplication ;;
  -b) mvn clean compile assembly:single ;;
  -all)
    mvn clean compile assembly:single
    java -cp target/Matcha.jar ru.school.matcha.MatchaApplication
    ;;
  esac
else
  echo "-b - build; -s - start; -all - build and start; -c - clean"
fi
