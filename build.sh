#!/bin/sh

set -xe

JAVAC=$JAVA_HOME/bin/javac
JAR=$JAVA_HOME/bin/jar

mkdir -p build

$JAVAC -cp spigot.jar src/max/NoNetherRoof.java -d build

$JAR cfv build.jar plugin.yml -C build .
