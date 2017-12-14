#!/bin/sh

MY_DIR=`dirname "$0"`
sdir="`pwd`"
cd ${MY_DIR}
MY_DIR="`pwd`"
cd "$sdir"

if [ -d "${MY_DIR}/../data" ]; then
    XLS_DIR="${MY_DIR}/../data"
elif [ -d "${MY_DIR}/../../data" ]; then
    XLS_DIR="${MY_DIR}/../../data"
else
    echo "Unable to find data directory"
    exit 1
fi

if [ -d "${MY_DIR}/../jakarta-tomcat" ]; then
    JSP_DIR="${MY_DIR}/../jakarta-tomcat/webapps/aprmarathon/jsp"
elif [ -d "${MY_DIR}/../../jakarta-tomcat" ]; then
    JSP_DIR="${MY_DIR}/../../jakarta-tomcat/webapps/aprmarathon/jsp"
else
    echo "Unable to find jsp directory"
    exit 1
fi

if [ -n "`uname -s | grep CYGWIN`" ]; then
    XLS_DIR=`cygpath --mixed "${XLS_DIR}"`
    JSP_DIR=`cygpath --mixed "${JSP_DIR}"`
fi

if [ -f "${MY_DIR}/run_java.sh" ]; then
    JAVA_CMD="${MY_DIR}/run_java.sh"
else
    JAVA_CMD="${MY_DIR}/run_java"
fi


JSPS="ManageMenu.jsp|MenuData.xls
"


for line in `echo $JSPS`; do
    jsp_name=`echo $line | awk -F '|' '{print $1}'`
    xls_name=`echo $line | awk -F '|' '{print $2}'`
    if [ -f "${XLS_DIR}/${xls_name}" ]; then
	if [ -z "${xls_files}" ]; then
	    xls_files="${XLS_DIR}/${xls_name}"
	else
	    xls_files="${xls_files}, ${XLS_DIR}/${xls_name}"
	fi
	if [ -f "${JSP_DIR}/${jsp_name}" ]; then
	    jsp_files="${jsp_files} ${jsp_name}"
	else
	    echo "Unable to find ${JSP_DIR}/${jsp_name}"
	fi
    else
	echo "Unable to find ${XLS_DIR}/${xls_name}"
    fi
done
export JAVA_ARGS="-DXLS_FILES=${xls_files}"
"${JAVA_CMD}" HttpClientPost ${jsp_files} 2> /dev/null
if [ $? -eq 0 ]; then
    echo "Post Install successful."
fi

