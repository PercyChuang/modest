#!/bin/sh

cd `dirname $0`
BIN_DIR=`pwd`
cd ..

START_CLASS=com.modest.starter.startup.Server
SERVER_HOME=`pwd`
CONTAINER_NAME=`pwd | rev | awk -F \/ '{print $1}' | rev`
CONFIG_DIR="/etc/lmw/$CONTAINER_NAME/config"
echo CONFIG_DIR
LIB_DIR=$SERVER_HOME/lib
LOGS_DIR="/data/logs/$CONTAINER_NAME"
STDOUT_FILE=$LOGS_DIR/all.log
cd $BIN_DIR

if [ ! -d $LOGS_DIR ]; then
	mkdir -p $LOGS_DIR
fi

if [ ! -d $CONFIG_DIR ]; then
  CONFIG_DIR=$SERVER_HOME/config
fi
echo $CONFIG_DIR

if [ -n "$JAVA_HOME" ]; then
    if [ -x "$JAVA_HOME/bin/java" ]; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        echo "** ERROR: java under JAVA_HOME=$JAVA_HOME cannot be executed"
        exit 1
    fi
else
    JAVACMD=`which java 2> /dev/null`
    if [ -z "$JAVACMD" ]; then
        JAVACMD=java
    fi
fi

# Verify that it is java 5+
javaVersion=`$JAVACMD -version 2>&1 | grep "java version" | egrep -e "1\.[678]"`
if [ -z "$javaVersion" ]; then
    $JAVACMD -version
    echo "** ERROR: The Java of $JAVACMD version is not 1.6 and above."
    exit 1
fi


# Verify minimal JVM props are set
hasMinHeapSize=`echo "$JAVA_OPTIONS" | grep "\\-Xms"`
if [ -z "$hasMinHeapSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Xms1g"
fi
hasMaxHeapSize=`echo "$JAVA_OPTIONS" | grep "\\-Xmx"`
if [ -z "$hasMaxHeapSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Xmx1g"
fi
hasMinPermSize=`echo "$JAVA_OPTIONS" | grep "\\-XX:PermSize"`
if [ -z "$hasMinPermSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -XX:PermSize=128m"
fi
hasMaxPermSize=`echo "$JAVA_OPTIONS" | grep "\\-XX:MaxPermSize"`
if [ -z "$hasMaxPermSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -XX:MaxPermSize=128m"
fi
hasMinNewSize=`echo "$JAVA_OPTIONS" | grep "\\-XX:NewSize"`
if [ -z "$hasMinNewSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Xms1024M"
fi
hasMaxNewSize=`echo "$JAVA_OPTIONS" | grep "\\-XX:MaxNewSize"`
if [ -z "$hasMaxNewSize" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -Xmx1024M"
fi
hasGcFlags=`echo "$JAVA_OPTIONS" | grep "\\-XX:-Use.*GC"`
if [ -z "$hasGcFlags" ]; then
    JAVA_OPTIONS="$JAVA_OPTIONS -XX:-UseConcMarkSweepGC -XX:+UseParNewGC"
fi

JAVA_OPTIONS="$JAVA_OPTIONS -server -Dserver.home=$SERVER_HOME -Dconfig.home=$CONFIG_DIR -Dfile.encoding=UTF8"


# Add all jars under the lib dir to the classpath
for i in `ls $LIB_DIR/*.jar`
do
    CLASSPATH="$CLASSPATH:$i"
done

nohup "$JAVACMD" $JAVA_OPTIONS -cp "$CONFIG_DIR:$CLASSPATH" $START_CLASS "$@" > /dev/null 2>&1 &

COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1 
    	COUNT=`ps -f | grep java | grep "$SERVER_HOME" | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps -f | grep java | grep "$SERVER_HOME" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"