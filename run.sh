#! /bin/bash

if [ ! -d "./target" ] 
then
    echo "JAR build not found, please run build.sh first"
    exit 1
fi

if [ -z "$1" ]
  then
    echo "No code file provided, for example, run it as ./run.sh \"./codigos/PruebaLexico.txt\""
    exit 1
fi

if [ ! -f "$1" ] 
then
    echo "Code file not found. Please provide a valid code file"
    exit 1
fi

java -cp target/Compilador-0.0.1-SNAPSHOT.jar compilador.Parser $1