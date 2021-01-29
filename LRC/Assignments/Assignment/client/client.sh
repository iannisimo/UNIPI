#!/bin/bash

java -jar -Djava.library.path=lib/ out/artifacts/client_jar/client.jar "$@"
