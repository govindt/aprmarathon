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
CLASSPATH="${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/classes"

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
