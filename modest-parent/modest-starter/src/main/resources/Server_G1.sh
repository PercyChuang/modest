#!/bin/sh
PROG_NAME=$0
ACTION=$1

usage() {
    echo "Usage: $PROG_NAME {start|stop}"
    exit 1;
}

if [ $# -lt 1 ] ; then
    usage
fi

JAVA_HOME=/usr/java/jdk1.7.0_79
CLASSPATH=.:$JAVA_HOME/lib:$CLASSPATH

##########################################################################################
APP_NAME=testing
APP_ROOT=/home/admin
PID_ROOT="$APP_ROOT/work"
LOG_ROOT="$APP_ROOT/logs"
APP_HOME="$APP_ROOT/$APP_NAME"
LOG_HOME="$LOG_ROOT/$APP_NAME"
STD_LOG="$LOG_HOME/stdout.log"
GC_LOG="$LOG_HOME/gc.log"
APP_PID="$PID_ROOT/$APP_NAME.pid"
MAINCLASS=orj/worf/container/spring/startup/Startup
BASE_OPTS="-Dserver.home=$APP_HOME -Dlogback.configurationFile=$APP_HOME/conf/logback.xml -Djms.clientId=123456"
##########################################################################################

start()
{
mkdir -p $PID_ROOT
if [ ! -z "$APP_PID" ]; then
  if [ -f "$APP_PID" ]; then
    if [ -s "$APP_PID" ]; then
      echo "Existing PID file found during start."
      if [ -r "$APP_PID" ]; then
        PID=`cat "$APP_PID"`
        ps -p $PID >/dev/null 2>&1
        if [ $? -eq 0 ] ; then
          echo "$APP_NAME appears to still be running with PID $PID. Start aborted."
          exit 1
        else
          echo "Removing/clearing stale PID file."
          rm -f "$APP_PID" >/dev/null 2>&1
          if [ $? != 0 ]; then
            if [ -w "$APP_PID" ]; then
              cat /dev/null > "$APP_PID"
            else
              echo "Unable to remove or clear stale PID file. Start aborted."
              exit 1
            fi
          fi
        fi
      else
        echo "Unable to read PID file. Start aborted."
        exit 1
      fi
    else
      rm -f "$APP_PID" >/dev/null 2>&1
      if [ $? != 0 ]; then
        if [ ! -w "$APP_PID" ]; then
          echo "Unable to remove or write to empty PID file. Start aborted."
          exit 1
        fi
      fi
    fi
  fi
fi
##########################################################################################
JAVA_OPTS="$JAVA_OPTS -server -Xms3550m -Xmx3550m -XX:PermSize=256m -XX:MaxPermSize=256m -Xss256k"
JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=55 -XX:G1ReservePercent=15"
JAVA_OPTS="$JAVA_OPTS -XX:MaxTenuringThreshold=15 -XX:ParallelGCThreads=8 -XX:+UnlockExperimentalVMOptions"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:+UseFastAccessorMethods -XX:SoftRefLRUPolicyMSPerMB=0"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGC -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps -XX:+PrintGCCause"
JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_ROOT/"
JAVA_OPTS="$JAVA_OPTS -Xloggc:$GC_LOG"
JAVA_OPTS="$JAVA_OPTS $BASE_OPTS"
##########################################################################################
mkdir -p $APP_ROOT
if [ -d "$APP_HOME" ] ; then
    tar -zcf "$APP_NAME.`date '+%Y%m%d%H%M%S'`.tgz" "$APP_NAME"
fi
mkdir -p $LOG_HOME
if [ -f "$GC_LOG" ] ; then
    mv -f $GC_LOG "$GC_LOG.`date '+%Y%m%d%H%M%S'`"
fi
if [ -f "$STD_LOG" ] ; then
    mv -f $STD_LOG "$STD_LOG.`date '+%Y%m%d%H%M%S'`"
fi
touch $STD_LOG
rm -rf $APP_HOME >/dev/null 2>&1
tar -zxvf "$APP_NAME.tgz" -C $APP_ROOT
sleep 3
cd $APP_HOME/bin
nohup $JAVA_HOME/bin/java $JAVA_OPTS $MAINCLASS start > $STD_LOG 2>&1 &
if [ ! -z "$APP_PID" ]; then
  echo $! > "$APP_PID"
fi
tail -f $STD_LOG
}

stop()
{
if [ ! -z "$APP_PID" ]; then
  if [ -f "$APP_PID" ]; then
    if [ -s "$APP_PID" ]; then
      kill -0 `cat "$APP_PID"` >/dev/null 2>&1
      if [ $? -gt 0 ]; then
        echo "PID file found but no matching process was found. Stop aborted."
        exit 1
      fi
    else
      echo "PID file is empty and has been ignored."
    fi
  else
    echo "\$APP_PID was set but the specified file does not exist. Is $APP_NAME running? Stop aborted."
    exit 1
  fi
fi
cd $APP_HOME/bin
$JAVA_HOME/bin/java $BASE_OPTS $MAINCLASS stop > $STD_LOG 2>&1 &
if [ -f "$STD_LOG" ] ; then
  mv -f $STD_LOG "$STD_LOG.`date '+%Y%m%d%H%M%S'`"
fi
if [ -f "$GC_LOG" ] ; then
  mv -f $GC_LOG "$GC_LOG.`date '+%Y%m%d%H%M%S'`"
fi
SLEEP=5
if [ ! -z "$APP_PID" ]; then
  if [ -f "$APP_PID" ]; then
    while [ $SLEEP -ge 0 ]; do
      kill -0 `cat "$APP_PID"` >/dev/null 2>&1
      if [ $? -gt 0 ]; then
        rm -f "$APP_PID" >/dev/null 2>&1
        if [ $? != 0 ]; then
          if [ -w "$APP_PID" ]; then
            cat /dev/null > "$APP_PID"
          else
            echo "$APP_NAME stopped but the PID file could not be removed or cleared."
          fi
        fi
        break
      fi
      if [ $SLEEP -gt 0 ]; then
        sleep 1
      fi
      if [ $SLEEP -eq 0 ]; then
        echo "$APP_NAME did not stop in time. PID file was not removed."
      fi
      SLEEP=`expr $SLEEP - 1 `
    done
  fi
fi
}

case "$ACTION" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    *)
        usage
    ;;
esac
