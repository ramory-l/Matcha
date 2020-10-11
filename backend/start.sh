#!/bin/bash

if [ -n "$1" ]; then
  case "$1" in
  -c) mvn clean ;;
  -s) java -cp target/matcha-0.0.1-SNAPSHOT.jar ru.school.matcha.MatchaApplication ;;
  -b) mvn clean dependency:copy-dependencies package ;;
  -all)
    mvn clean dependency:copy-dependencies package
    java -cp target/matcha-0.0.1-SNAPSHOT.jar ru.school.matcha.MatchaApplication
    ;;
  esac
else
  echo "-b - build; -s - start; -all - build and start; -c - clean"
fi
