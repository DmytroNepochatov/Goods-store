#!/bin/sh
mvn clean package
docker build -t discovery-server-image:1.0 .
docker run -d -p 8761:8761 --name discovery-server --network my_network discovery-server-image:1.0