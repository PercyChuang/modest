  set +x
    
    #定义常量
    EVN=bate  #预发布bate 生产pro
    PROJECT_NAME=lmw-borrower-parent
    BASE_DIR=/data/deploy/jenkins_limin/jobs
    SRC_DIR="${BASE_DIR}/${PROJECT_NAME}-${EVN}/workspace/${PROJECT_NAME}"
    #######################################################################
 	#POM文件私服地址修改配置修改
    POM_LIST=`find $SRC_DIR -name pom.xml`
    for file in $POM_LIST
    do
    	sed -i "s/192.168.2.110/10.116.134.140/g 

" ${file}
    done
    #######################################################################