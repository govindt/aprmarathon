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

GOOGLE_SHEET_CLASSPATH="${TOMCAT_DIR}/lib/guava-26.0-jre.jar:\
${TOMCAT_DIR}/lib/google-collect-1.0.jar:\
${TOMCAT_DIR}/lib/servlet.jar:\
${TOMCAT_DIR}/lib/google-api-services-gmail-v1-rev93-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-http-client-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-http-client-jackson2-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-oauth-client-java6-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-oauth-client-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-api-client-1.24.1.jar:\
${TOMCAT_DIR}/lib/google-api-services-sheets-v4-rev483-1.22.0.jar:\
${TOMCAT_DIR}/lib/jackson-core-2.1.3.jar:\
${TOMCAT_DIR}/lib/google-oauth-client-jetty-1.24.1.jar:\
${TOMCAT_DIR}/lib/jetty-6.1.26.jar:\
${TOMCAT_DIR}/lib/jetty-util-6.1.26.jar:\
${TOMCAT_DIR}/lib/mailapi.jar:\
${TOMCAT_DIR}/lib/smtp.jar:\
${TOMCAT_DIR}/lib/poi-3.15.jar:\
${TOMCAT_DIR}/lib/poi-ooxml-3.15.jar:\
${TOMCAT_DIR}/lib/ooxml-schemas-1.3.jar:\
${TOMCAT_DIR}/lib/xmlbeans-2.5.0.jar:\
${TOMCAT_DIR}/lib/org.apache.poi.xwpf.converter.pdf-1.0.6.jar:\
${TOMCAT_DIR}/lib/org.apache.poi.xwpf.converter.core-1.0.6.jar:\
${TOMCAT_DIR}/lib/ext.bundle.openconverters-1.2.2.jar:\
${TOMCAT_DIR}/lib/fr.opensagres.xdocreport.itext.extension-2.0.0.jar:\
${TOMCAT_DIR}/lib/com.lowagie.text-2.1.7.jar:\
${TOMCAT_DIR}/lib/fr.opensagres.xdocreport.itext.extension-2.0.0.jar:\
${TOMCAT_DIR}/lib/commons-codec-1.11.jar\
"

CLASSPATH="${TOMCAT_DIR}/webapps/aprmarathon/WEB-INF/classes:\
${TOMCAT_DIR}/lib/commons-jcs-core-2.2.jar:\
${TOMCAT_DIR}/lib/commons-logging.jar:\
${TOMCAT_DIR}/lib/mysql-connector-java-5.1.6-bin.jar:\
${TOMCAT_DIR}/lib/jettison-1.3.4.jar:\
${GOOGLE_SHEET_CLASSPATH}"

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
