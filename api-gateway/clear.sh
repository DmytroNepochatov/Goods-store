#!/bin/sh
mvn clean
docker rm -f api-gateway
docker rm -f token_db
docker rm -f token-redis-cache
docker rmi -f api-gateway-image:1.0