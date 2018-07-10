#!/bin/sh

sdir=`pwd`
my_dir=`dirname $0`
cd ${my_dir}
my_name=`basename $0`
my_dir=`pwd`
cd $sdir

WS_DIR=`echo ${my_dir}/../..`
cd ${WS_DIR}
WS_DIR=`pwd`
cd ${sdir}


ReplaceText() {
	src="$1"
	dst="$2"
	src_template="$3"
	dest_file="$4"
	if [ $# -eq 2 ]; then	
		for f in `echo $files`; do
			if [ -n "`grep ${src} $f | grep -v Binary`" ]; then
				sed -e "sX${src}X${dst}Xg" $f > /tmp/`basename $f`
				if [ $? -eq 0 ]; then
					mv /tmp/`basename $f` $f
				else
					echo "SRC=$src"
					echo "DST=$dst"
					echo "FILE=$f"
				fi
			fi
		done
	else
		sed -e "sX${src}X${dst}Xg" ${src_template} > ${dest_file}
	fi
}

while ( true ) 
do
	echo "The current value for JAVA_DEV_ROOT is ${JAVA_DEV_ROOT}"
	echo "If this is correct press Y or y"
	echo "If NOT enter the new value for JAVA_DEV_ROOT"
	read x
	if [ "${x}" != "y" -a "${x}" != "Y" ]; then
		if [ -d "${x}" ]; then
			MY_JAVA_DEV_ROOT=${x}
			break
		else
			echo "${x} not a valid directory"
		fi
	else
		if [ ! -d "${JAVA_DEV_ROOT}" ]; then
			echo "JAVA_DEV_ROOT is not a valid directory"
		else
			MY_JAVA_DEV_ROOT=${JAVA_DEV_ROOT}
			break
		fi
		
	fi
done

if [ "${JAVA_DEV_ROOT}" !=  "${MY_JAVA_DEV_ROOT}" ]; then
	echo "Please make sure that the Environment Variable is set to ${MY_JAVA_DEV_ROOT} and rerun the script"
fi

cd ${my_dir}
ReplaceText %SERVLET_PATH% "aprmregistration" web_template.xml web.xml
ReplaceText %REST_NAME% "APR Marathon Rest" web_template.xml web.xml
exit
cd ${WS_DIR}
files=`find . -type f | grep -v $my_name`
ReplaceText %JAVA_DEV_ROOT% "${JAVA_DEV_ROOT}"
ReplaceText %AUTHOR% "Govind Thirumalai"
ReplaceText %PROJECT% "APRMRegistration"
ReplaceText %DBNAME% "aprm"
ReplaceText %DBUSER% "aprm"
ReplaceText %DBPASSWORD% "aprm"
ReplaceText %BASEURL% "aprmarathon"
ReplaceText %COMPANY% "APR Charitable Trust"
ReplaceText %YEAR% "2017"
mv jakarta-tomcat/webapps/%BASEURL% jakarta-tomcat/webapps/$PROJECT



