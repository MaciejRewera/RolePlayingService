#!/usr/bin/env bash

docker run -d -p 27018:27017 -v /data/dockerMongo/:/data/db mongo
