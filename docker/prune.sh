#!/bin/sh
docker system prune -f
docker container stop $(docker container ls -aq)
docker container rm $(docker container ls -aq)
docker rmi $(docker images -aq)
docker volume prune -f
