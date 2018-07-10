#!/bin/sh

MY_DIR=`dirname "$0"`
sdir="`pwd`"
cd "${MY_DIR}"
MY_DIR="`pwd`"
cd "$sdir"

if [ $# -eq 0 ]; then
    echo "Usage `basename $0` class"
    exit 1
fi

if [ -d "${MY_DIR}/../jakarta-tomcat" ]; then
    TOMCAT_DIR="${MY_DIR}/../jakarta-tomcat"
elif [ -d "${MY_DIR}/../../jakarta-tomcat" ]; then
    TOMCAT_DIR="${MY_DIR}/../../jakarta-tomcat"
else
    echo "Unable to find jakarta-tomcat"
    exit 1
fi
if [ -n "`uname -s | grep CYGWIN_NT`" ]; then
    TOMCAT_DIR=`cygpath --mixed "${TOMCAT_DIR}"`
fi

GOOGLE_SHEET_CLASSPATH="${TOMCAT_DIR}/lib/google-http-client-1.22.0.jar:${TOMCAT_DIR}/lib/google-http-client-jackson2-1.22.0.jar:${TOMCAT_DIR}/lib/google-oauth-client-java6-1.22.0.jar:${TOMCAT_DIR}/lib/google-oauth-client-1.22.0.jar:${TOMCAT_DIR}/lib/google-api-client-1.22.0.jar:${TOMCAT_DIR}/lib/google-api-services-sheets-v4-rev483-1.22.0.jar:${TOMCAT_DIR}/lib/jackson-core-2.1.3.jar:${TOMCAT_DIR}/lib/google-oauth-client-jetty-1.22.0.jar:${TOMCAT_DIR}/lib/jetty-6.1.26.jar:${TOMCAT_DIR}/lib/jetty-util-6.1.26.jar"

CLASSPATH="${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/classes:${TOMCAT_DIR}/lib/commons-jcs-core-2.2.jar:${TOMCAT_DIR}/lib/commons-logging.jar:${TOMCAT_DIR}/lib/mysql-connector-java-5.1.6-bin.jar:${TOMCAT_DIR}/lib/jettison-1.3.4.jar:${GOOGLE_SHEET_CLASSPATH}"

for jar in `ls -1 "${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/lib"`; do
    if [ -n "`uname -s | grep CYGWIN_NT`" ]; then
	CLASSPATH="${CLASSPATH};${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/lib/$jar"
    else
	CLASSPATH="`echo ${CLASSPATH}`:${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/lib/$jar"
    fi
done
if [ -n "${JAVA_ARGS}" ]; then
    "${JAVA_HOME}/bin/java" -cp "$CLASSPATH" "${JAVA_ARGS}" $*
else
    "${JAVA_HOME}/bin/java" -cp "$CLASSPATH" $*
fi
