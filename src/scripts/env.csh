#!/bin/csh
umask 002
setenv PROJECT $(basename ${JAVA_DEV_ROOT})
setenv JAVA_DEV_ROOT ${HOME}/${PROJECT}
if ( ${JAVA_HOME} == "" ) then
    setenv JAVA_HOME /usr/java/j2sdk1.4.2_05
endif
if ( "`uname -s | grep CYGWIN_NT`" != "" ) then
    setenv JAVA_DEV_ROOT `cygpath -m ${JAVA_DEV_ROOT}`
    setenv JAVA_HOME `cygpath -d "$JAVA_HOME"`
    setenv JAVA_HOME `cygpath -m "$JAVA_HOME"`
    setenv ANT_HOME $(cyspath -u ${JAVA_DEV_ROOT}/../apache-ant-1.8.4)
endif
setenv WS_ROOT ${JAVA_DEV_ROOT}
setenv JARHOME ${JAVA_DEV_ROOT}/jars
setenv CLASSPATH ${JARHOME}/../jst/classes:${JARHOME}/jconn2.jar:${JARHOME}/ojdbc14.jar:${JARHOME}/jakarta-regexp-1.2.jar:${JARHOME}/poi-3.15.jar:${JARHOME}/poi-ooxml-3.15.jar:${JARHOME}/mailapi.jar:${JARHOME}/smtp.jar:${JARHOME}/activation.jar:${JARHOME}/mssqlserver.jar:${JARHOME}/msbase.jar:${JARHOME}/msutil.jar:${JARHOME}/commons-jcs-core-2.2.jar:${JARHOME}/commons-lang-2.1.jar:${JARHOME}/commons-logging.jar:${JARHOME}/log4j-1.2.12.jar:${JARHOME}/httpcore-4.0-beta2.jar:${JARHOME}/httpclient-4.0-beta1.jar:${JARHOME}/httpmime-4.0-beta1.jar:${JARHOME}/apache-mime4j-0.4.jar:${JARHOME}/jettison-1.3.4.jar:${JARHOME}/javax.ws.rs.jar


setenv ORACLE_HOME /u01/app/oracle/product/10.1.0/Db_1
setenv PATH ${ORACLE_HOME}/bin:${JAVA_HOME}/bin:${ANT_HOME}/bin:${PATH}
setenv LD_LIBRARY_PATH ${ORACLE_HOME}/lib:/usr/lib:/usr/local/lib
setenv ORACLE_SID aprm
setenv CATALINA_HOME ${JAVA_DEV_ROOT}/jakarta-tomcat

# Aliases
alias main 'cd ${JAVA_DEV_ROOT}'
alias web 'cd ${CATALINA_HOME}/bin'
alias bin 'cd ${JAVA_DEV_ROOT}/bin'
alias sbin 'cd ${JAVA_DEV_ROOT}/src/java/bin'
alias servlet 'cd ${JAVA_DEV_ROOT}/src/java/servlet'
alias db 'cd ${JAVA_DEV_ROOT}/src/java/core/db'
alias appdb 'cd ${JAVA_DEV_ROOT}/src/java/core/appdb'
alias appui 'cd ${JAVA_DEV_ROOT}/src/java/core/appui'
alias ui 'cd ${JAVA_DEV_ROOT}/src/java/core/ui'
alias util 'cd ${JAVA_DEV_ROOT}/src/java/core/util'
alias bo 'cd ${JAVA_DEV_ROOT}/src/java/core/busobj'
alias abo 'cd ${JAVA_DEV_ROOT}/src/java/app/busobj'
alias abi 'cd ${JAVA_DEV_ROOT}/src/java/app/busimpl'
alias abif 'cd ${JAVA_DEV_ROOT}/src/java/app/businterface'
alias aappui 'cd ${JAVA_DEV_ROOT}/src/java/app/appui'
alias aappdb 'cd ${JAVA_DEV_ROOT}/src/java/app/appdb'
alias arest 'cd ${JAVA_DEV_ROOT}/src/java/app/restapi'
alias exam 'cd ${JAVA_DEV_ROOT}/src/examples'
alias scripts 'cd ${JAVA_DEV_ROOT}/src/scripts'
alias html 'cd ${JAVA_DEV_ROOT}/src/public_html'
alias sql 'sqlplus aprm/aprm@${ORACLE_SID}'



