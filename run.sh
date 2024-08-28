#!/bin/sh
docker-compose up --build -d
cd api-gateway
sh run.sh
cd ..
cd discovery-server
sh run.sh
cd ..
cd auth-server
sh run.sh
cd ..