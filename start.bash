#!/bin/bash

JAVA_HOME=/usr/java/jdk1.8.0_11
CLASSPATH=$JAVA_HOME/bin

APP_HOME=/AP/jobs
APP_NAME=demo_scrap.jar
MAIN_CLASS=com.herokuapp.demo.App
CHK_PATTERN=App

CLASSPATH=$CLASSPATH:$APP_HOME/$APP_NAME

export CLASSPATH=$CLASSPATH

echo $CLASSPATH

rs=`ps -ef | grep -v "grep" | grep "$CHK_PATTERN"`

if [ -z "$rs" ]
then
    echo "$(date) starting [$CHK_PATTERN]" >> $APP_HOME/logs/demo_scrap.log
    java -Xms50m -Xmx250m $MAIN_CLASS
else
    echo "$(date) process is running [$CHK_PATTERN]" >> $APP_HOME/logs/demo_scrap.log
fi

exit
