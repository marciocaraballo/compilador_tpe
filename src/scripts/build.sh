#! /bin/bash

./yacc -J ../compilador/gramatica.y
sed -i '1s/^/package compilador;\n\n/' ./ParserVal.java
mv ./ParserVal.java ./Parser.java ../compilador
