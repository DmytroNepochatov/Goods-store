#!/bin/sh
docker rm -f zipkin
cd api-gateway
sh clear.sh
cd ..
cd discovery-server
sh clear.sh
cd ..
cd auth-server
sh clear.sh
cd ..