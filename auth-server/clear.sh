#!/bin/sh
mvn clean
docker rm -f auth-service
docker rm -f user_auth_db
docker rmi -f auth-service-image:1.0