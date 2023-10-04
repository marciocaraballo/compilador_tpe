#! /bin/bash

if [ ! -d "./target" ] 
then
    echo "No se encontro el JAR, por favor ejecutar build.sh primero"
    exit 1
fi

if [ -z "$1" ]
  then
    echo "No se provee archivo de codigo, por ejemplo, proveerlo como ./run.sh \"./codigos/PruebaLexico.txt\""
    exit 1
fi

if [ ! -f "$1" ] 
then
    echo "No se encontro el archivo de codigo. Por favor proveer un archivo de codigo valido."
    exit 1
fi

java -cp target/Compilador-0.0.1-SNAPSHOT.jar compilador.Parser $1