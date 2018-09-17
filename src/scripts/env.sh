#!/bin/bash
umask 002
export PROJECT=$(basename ${JAVA_DEV_ROOT})
export JAVA_DEV_ROOT=${JAVA_DEV_ROOT:-${HOME}/${PROJECT}}
export JAVA_HOME=${JAVA_HOME:-/usr/java}
if [ -n "`uname -s | grep CYGWIN_NT`" ]; then
    export JAVA_DEV_ROOT=`cygpath -m ${JAVA_DEV_ROOT}`
    export JAVA_HOME=`cygpath -d "$JAVA_HOME"`
    export JAVA_HOME=`cygpath -m "$JAVA_HOME"`
    export ANT_HOME=$(cygpath -u ${JAVA_DEV_ROOT}/../apache-ant-1.8.4)
fi
export WS_ROOT=${JAVA_DEV_ROOT}
export JARHOME=${JAVA_DEV_ROOT}/jars
export CLASSPATH=${JARHOME}/../jst/classes:${JARHOME}/jconn2.jar:${JARHOME}/ojdbc14.jar:${JARHOME}/jakarta-regexp-1.2.jar:${JARHOME}/poi-3.15.jar:${JARHOME}/poi-ooxml-3.15.jar:${JARHOME}/mailapi.jar:${JARHOME}/smtp.jar:${JARHOME}/activation.jar:${JARHOME}/mssqlserver.jar:${JARHOME}/msbase.jar:${JARHOME}/msutil.jar:${JARHOME}/commons-jcs-core-2.2.jar:${JARHOME}/commons-lang-2.1.jar:${JARHOME}/commons-logging.jar:${JARHOME}/log4j-1.2.12.jar:${JARHOME}/httpcore-4.0-beta2.jar:${JARHOME}/httpclient-4.0-beta1.jar:${JARHOME}/httpmime-4.0-beta1.jar:${JARHOME}/apache-mime4j-0.4.jar:${JARHOME}/jettison-1.3.4.jar:${JARHOME}/javax.ws.rs.jar:${JARHOME}/org.apache.poi.xwpf.converter.pdf-1.0.6.jar:${JARHOME}/org.apache.poi.xwpf.converter.core-1.0.6.jar:${JARHOME}/commons-codec-1.11.jar


export ORACLE_HOME=${ORACLE_HOME:-/u01/app/oracle/product/10.1.0/Db_1/}
export PATH=${ORACLE_HOME}/bin:${JAVA_HOME}/bin:${ANT_HOME}/bin:${JAVA_DEV_ROOT}/bin:${JAVA_DEV_ROOT}/bin:{PATH}
export LD_LIBRARY_PATH=${ORACLE_HOME}/lib:/usr/lib:/usr/local/lib
export ORACLE_SID=aprm
#export CATALINA_HOME=${JAVA_DEV_ROOT}/apache-tomcat-9.0.11
export CATALINA_HOME=${JAVA_DEV_ROOT}/jakarta-tomcat
export EDITOR=vi

# Aliases
alias main='cd ${JAVA_DEV_ROOT}'
alias web='cd ${CATALINA_HOME}/bin'
alias bin='cd ${JAVA_DEV_ROOT}/bin'
alias sbin='cd ${JAVA_DEV_ROOT}/src/java/bin'
alias servlet='cd ${JAVA_DEV_ROOT}/src/java/servlet'
alias db='cd ${JAVA_DEV_ROOT}/src/java/core/db'
alias appdb='cd ${JAVA_DEV_ROOT}/src/java/core/appdb'
alias appui='cd ${JAVA_DEV_ROOT}/src/java/core/appui'
alias ui='cd ${JAVA_DEV_ROOT}/src/java/core/ui'
alias util='cd ${JAVA_DEV_ROOT}/src/java/core/util'
alias bo='cd ${JAVA_DEV_ROOT}/src/java/core/busobj'
alias abo='cd ${JAVA_DEV_ROOT}/src/java/app/busobj'
alias abi='cd ${JAVA_DEV_ROOT}/src/java/app/busimpl'
alias abif='cd ${JAVA_DEV_ROOT}/src/java/app/businterface'
alias aappui='cd ${JAVA_DEV_ROOT}/src/java/app/appui'
alias aappdb='cd ${JAVA_DEV_ROOT}/src/java/app/appdb'
alias autil='cd ${JAVA_DEV_ROOT}/src/java/app/util'
alias arest='cd ${JAVA_DEV_ROOT}/src/java/app/restapi'
alias exam='cd ${JAVA_DEV_ROOT}/src/examples'
alias scripts='cd ${JAVA_DEV_ROOT}/src/scripts'
alias html='cd ${JAVA_DEV_ROOT}/src/public_html'
alias sql='sqlplus aprm/aprm@${ORACLE_SID}'


