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
TOMCAT_WEBAPP_LIB_DIR=${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/lib
if [ -n "`uname -s | grep CYGWIN_NT`" ]; then
    TOMCAT_DIR=`cygpath --mixed "${TOMCAT_DIR}"`
    TOMCAT_WEBAPP_LIB_DIR=`cygpath --mixed ${TOMCAT_WEBAPP_LIB_DIR}`
fi

GOOGLE_SHEET_CLASSPATH="${TOMCAT_WEBAPP_LIB_DIR}/guava-26.0-jre.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-collect-1.0.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/servlet.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-api-services-gmail-v1-rev93-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-http-client-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-http-client-jackson2-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-oauth-client-java6-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-oauth-client-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-api-client-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-api-services-sheets-v4-rev483-1.22.0.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/jackson-core-2.1.3.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/google-oauth-client-jetty-1.24.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/jetty-6.1.26.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/jetty-util-6.1.26.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/mailapi.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/smtp.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/poi-3.15.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/poi-ooxml-3.15.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/ooxml-schemas-1.3.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/xmlbeans-2.5.0.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/org.apache.poi.xwpf.converter.pdf-1.0.6.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/org.apache.poi.xwpf.converter.core-1.0.6.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/ext.bundle.openconverters-1.2.2.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/fr.opensagres.xdocreport.itext.extension-2.0.0.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/com.lowagie.text-2.1.7.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/fr.opensagres.xdocreport.itext.extension-2.0.0.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/commons-codec-1.11.jar\
"

CLASSPATH="${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/classes:\
${TOMCAT_WEBAPP_LIB_DIR}/commons-jcs-core-2.2.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/commons-logging.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/mysql-connector-java-5.1.6-bin.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/jettison-1.1.jar:\
${TOMCAT_WEBAPP_LIB_DIR}/commons-collections4-4.1.jar:\
${GOOGLE_SHEET_CLASSPATH}"

#for jar in `ls -1 "${TOMCAT_WEBAPP_LIB_DIR}/webapps/aprmarathon/WEB-INF/do
    #if [ -n "`uname -s | grep CYGWIN_NT`" ]; then
		#CLASSPATH="${CLASSPATH};${TOMCAT_LIB_DIR}/webapps/aprmarathon/WEB-INF/lib/$jar"
    #else
		#CLASSPATH="`echo ${CLASSPATH}`:${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/lib/$jar"
    #fi
#done

if [ -n "${JAVA_ARGS}" ]; then
    "${JAVA_HOME}/bin/java" -cp "$CLASSPATH" "${JAVA_ARGS}" $*
else
    "${JAVA_HOME}/bin/java" -cp "$CLASSPATH" $*
fi
