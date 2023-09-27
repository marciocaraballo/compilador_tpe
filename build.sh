#! /bin/bash

./yacc -J ./src/compilador/gramatica.y
sed -i '1s/^/package compilador;\n\n/' ./ParserVal.java
mv ./ParserVal.java ./Parser.java ./src/compilador

mvn package