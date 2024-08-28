#!/bin/sh
mvn clean
docker rm -f discovery-server
docker rmi -f discovery-server-image:1.0