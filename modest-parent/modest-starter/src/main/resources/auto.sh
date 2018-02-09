#!/bin/bash
source /etc/profile

#defined function
function myslipt(){
  OLD_IFS="$IFS"
  IFS=" "
  arr=($1)
  IFS="$OLD_IFS"
  echo $arr
}


#defined
TOMCAT_HOME="$3"
TOMCAT_PORT="$2"
PROJECT="$1"
#param validate
if [ $# -lt 1 ]; then
  echo "you must use like this : ./publish.sh <projectname> [tomcat port] [tomcat home dir]"
  exit
fi
if [ "$2" != "" ]; then
   TOMCAT_PORT=$2
fi
if [ "$3" != "" ]; then
   TOMCAT_HOME="$3"
fi
#shutdown tomcat
echo -e  "\033[32mtomcat shutdown \033[0m"
#check tomcat process
tomcat_pid=`/usr/sbin/lsof -n -P -t -i :$TOMCAT_PORT`

#this is last modify
echo "find pid :" $tomcat_pid
pids=(`myslipt "$tomcat_pid"`)
for s in ${pids[@]}
do
  sleep 5
  echo "Next kill pid :" $s
  kill -9 $s 2> /dev/null
done

# while [ -n "$tomcat_pid" ]
# do
#  sleep 5
#  tomcat_pid=`ps -ef | grep java| grep "$PROJECT" |awk '{print $2}'`
#   kill -9 $tomcat_pid 2> /dev/null
#  echo -e  "\033[32mkilling processes is success \033[0m"
# done

#publish project
echo "scan no tomcat pid,$PROJECT publishing"
#bak project
BAK_DIR=/opt/bak/$PROJECT/`date +%Y%m%d`
mkdir -p "$BAK_DIR"
if [ "$PROJECT" != "lmw-manage-oss" ]
then
	mv "$TOMCAT_HOME"/webapps/"$PROJECT".war "$BAK_DIR"/"$PROJECT"_`date +%H%M%S`.war
fi
#remove webapps project
rm -rf "$TOMCAT_HOME"/webapps/"$PROJECT"
rm -rf "$TOMCAT_HOME"/webapps/"$PROJECT".war
rm -rf "/data/www/$PROJECT/*"
if [ "$PROJECT" == "lmw-manage-oss" ]
then
	rm -rf /data/www/lmw-manage-oss/*
	cp /opt/pkg/"$PROJECT".war "/data/www/lmw-manage-oss/${PROJECT}.war"
	cd /data/www/lmw-manage-oss/
	jar -xvf lmw-manage-oss.war
	rm -f /data/www//lmw-manage-oss/lmw-manage-oss.war
else
	cp /opt/pkg/"$PROJECT".war "$TOMCAT_HOME"/webapps/"$PROJECT".war
fi
#remove tmp
mv /opt/pkg/"$PROJECT".war  /opt/pkg1/"$PROJECT"_`date +%H%M%S`.war
#start tomcat
"$TOMCAT_HOME"/bin/startup.sh
echo  -e  "\033[32m tomcat is starting,please try to access $PROJECT url \033[0m"
