#! /bin/bash

./yacc -Jpackage="compilador" -J ./src/compilador/gramatica.y
mv ./ParserVal.java ./Parser.java ./src/compilador