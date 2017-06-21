#!/usr/bin/env bash

lsof -i:8080 | grep java | awk '{print $2}' | xargs kill -9
nohup java -jar target/study-wx-1.0-SNAPSHOT.jar >> ./log/log &
