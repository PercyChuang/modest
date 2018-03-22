set +x
except(){
          if [ $? != 0 ];
          then
          	   echo "#################$1，Check the configuration and publishing process"
               exit
          fi
	}
    #开关
    switch=${switch}
    if [ ${switch} == "OFF" ]
    then
    	exit
    fi
    #定义常量
    EVN=bate  #预发布bate 生产pro
    PROJECT_NAME=lmw-borrower-parent
    WAR_NAME=lmw-borrower-service.war
    BASE_DIR=/data/deploy/jenkins_limin/jobs
    SRC_DIR="${BASE_DIR}/${PROJECT_NAME}-${EVN}/workspace/${PROJECT_NAME}"
    WAR_DIR="${SRC_DIR}/${PROJECT_NAME}/target/"
    TOMCAT_DIR="/app/tomcat/lmw-borrower-parent"
    RELEASE_DIR="/data/deploy/release/lmw-borrower-parent"    #上生产所需的war包存放目录
    PID_FILE=/app/tomcat/pid_dir/${PROJECT_NAME}.pid 
    #######################################################################
    TIME=`date +%F-%s`
    BAK_DIR="/data/backup/tomcat/${PROJECT_NAME}_${EVN}"
    DEPLOY_DIR="/data/www/${PROJECT_NAME}"
    #######################################################################
    cd $WAR_DIR
    #预发布war包备份
  	mkdir -p ${BAK_DIR}/${TIME}
	ansible liminappserversbate -m synchronize -a "mode=pull src=${DEPLOY_DIR} dest=${BAK_DIR}/${TIME} compress=yes delete=yes archive=yes recursive=yes"
    except "File backup error"
    #停止远程服务
	for web in liminappserversbate
    do
			pid=`ansible $web -m command -a "cat $PID_FILE"|grep -v -E "rc|^\$"`
            cnt=`ansible $web -m command -a "ps -ef"|grep java|grep -w  ${PROJECT_NAME}|awk -v p=$pid '{if(\$2==p) print \$2 ;else print 0}'`           
            if [ ! -z "$cnt" ];then
				ansible $web -m command -a "kill -9 $pid"
                echo "The program has stopped"
            fi
    done
	#创建发布目录并将war包解压到发布目录中
    if [ ! -d "${RELEASE_DIR}" ]
    then
  		mkdir -p ${RELEASE_DIR}
	fi
    rm -rf ${RELEASE_DIR}/*
    unzip -oq ./$WAR_NAME -d $RELEASE_DIR  
    except "Unzip error"
    
    #将发布目录同步到远程服务器的一个用目录中
    cd ${RELEASE_DIR}
    ansible liminappserversbate -m synchronize -a "src=${RELEASE_DIR}/ dest=${DEPLOY_DIR} compress=yes delete=yes archive=yes recursive=yes" 
    except "File synchronization error"
    #启动服务

    ansible liminappserversbate -m command -a "monit start ${PROJECT_NAME}-parent"
    sleep 3
    cnt=`ansible liminappserversbate -m command -a "ps -aux" |grep ${PROJECT_NAME}|wc -l`
    if [ $cnt -ge 1 ]
    then
        	tags='/data/deploy/tags/'
    		if [ ! -d "${tags}${TIME}/${PROJECT_NAME}" ]
    		then
  					mkdir -p ${tags}${TIME}/${PROJECT_NAME}
			fi
    		cp -rf ${SRC_DIR}/*  ${tags}${TIME}/${PROJECT_NAME}
            cd ${tags}${TIME}
            zip -q -r -P 5hua8men ${PROJECT_NAME}.zip  ./${PROJECT_NAME}
            rm -rf ./${PROJECT_NAME}
    		#删除源码文件
    		rm -rf ${SRC_DIR}/*
            echo "*********************************DEPLOY SUCCESS*********************************************************"
            sleep 20
            ansible liminappserversbate -m raw -a "tail -n 3000 ${TOMCAT_DIR}/logs/catalina.out"
    else
        	echo "**********************************DEPLOY FAIL***********************************************************"
    fi