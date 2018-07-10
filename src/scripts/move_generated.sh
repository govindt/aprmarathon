#!/bin/sh

DESTDIR="${JAVA_DEV_ROOT}/src/java/app"

if [ ! -d "${DESTDIR}" ]; then
	echo "${DESTDIR} : No such directory"
	exit 1
fi

impl_files=`echo *Impl.java`
if_files=`echo *Interface.java`
bo_files=`echo *Object.java`
appdb_files=`echo Persistent*.java`
pers_file="persistence.properties"
apputil_files="App.java AppConstants.java app.properties"
app_bean_files=`echo *Bean.java`
jsp_files=`echo *.jsp`
rest_files=$(echo *Rest.java)

login_required_files="`grep login_required ${DESTDIR}/util/app.properties | awk -F= '{print $2}'`"

mkdir -p ${DESTDIR}/busimpl ${DESTDIR}/businterface ${DESTDIR}/appdb ${DESTDIR}/busobj
for f in ${impl_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/busimpl
	fi
done 
if [ -f Makefile.busimpl ]; then
    mv Makefile.busimpl ${DESTDIR}/busimpl/Makefile
fi
for f in ${if_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/businterface
	fi
done 
if [ -f Makefile.businterface ]; then
    mv Makefile.businterface ${DESTDIR}/businterface/Makefile
fi
for f in ${bo_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/busobj
	fi
done 
if [ -f Makefile.busobj ]; then
    mv Makefile.busobj ${DESTDIR}/busobj/Makefile
fi
for f in ${appdb_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/appdb
	fi
done 
if [ -f Makefile.appdb ]; then
    mv Makefile.appdb ${DESTDIR}/appdb/Makefile
fi
for f in ${apputil_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/util
	fi
done 

for f in ${app_bean_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/appui
	fi
done 

for f in ${rest_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/restapi
	fi
done 
if [ -f Makefile.appui ]; then
    mv Makefile.appui ${DESTDIR}/appui/Makefile
fi

for f in ${jsp_files}; do
	if [ -f "$f" ]; then
		mv $f ${DESTDIR}/../../public_html
		if [ -z "`echo ${login_required_files} | grep ${f}`" ]; then
		    login_required_files="${login_required_files} ${f}"
		fi
	fi
done 

sed "s/app.login_required_urls=.*/app.login_required_urls=${login_required_files}/g" ${DESTDIR}/util/app.properties > /tmp/app.properties

mv /tmp/app.properties ${DESTDIR}/util/app.properties


if [ -f Makefile.public_html ]; then
    mv Makefile.public_html ${DESTDIR}/../../public_html/Makefile
fi

if [ -f "${pers_file}" ]; then
	for f in `cat ${pers_file}`; do
		if [ -z "`grep $f ${DESTDIR}/../core/db/${pers_file}`" ]; then
			echo "Adding $f to ${DESTDIR}/../core/db/${pers_file}"
			echo $f >> ${DESTDIR}/../core/db/${pers_file}
		fi
	done
else
	echo "Unable to find ${pers_file}"	
fi
