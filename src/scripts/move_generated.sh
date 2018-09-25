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
		if [ -f ${DESTDIR}/busimpl/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/busimpl/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/busimpl
			fi
		else #First Time
			mv $f ${DESTDIR}/busimpl
		fi
	fi
done 

if [ -f Makefile.busimpl ]; then
    mv Makefile.busimpl ${DESTDIR}/busimpl/Makefile
fi

for f in ${if_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/businterface/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/businterface/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/businterface
			fi
		else 
			mv $f ${DESTDIR}/businterface
		fi
	fi
done

if [ -f Makefile.businterface ]; then
    mv Makefile.businterface ${DESTDIR}/businterface/Makefile
fi
for f in ${bo_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/busobj/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/busobj/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/busobj
			fi
		else 
			mv $f ${DESTDIR}/busobj
		fi
	fi
done

if [ -f Makefile.busobj ]; then
    mv Makefile.busobj ${DESTDIR}/busobj/Makefile
fi
for f in ${appdb_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/appdb/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/appdb/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/appdb
			fi
		else 
			mv $f ${DESTDIR}/appdb
		fi
	fi
done

if [ -f Makefile.appdb ]; then
    mv Makefile.appdb ${DESTDIR}/appdb/Makefile
fi

for f in ${apputil_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/util/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/util/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/util
			fi
		else 
			mv $f ${DESTDIR}/util
		fi
	fi
done 

if [ -f Makefile.util ]; then
    mv Makefile.util ${DESTDIR}/util/Makefile
fi

for f in ${app_bean_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/appui/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/appui/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/appui
			fi
		else 
			mv $f ${DESTDIR}/appui
		fi
	fi
done

if [ -f Makefile.appui ]; then
    mv Makefile.appui ${DESTDIR}/appui/Makefile
fi

for f in ${rest_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/restapi/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/restapi/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/restapi
			fi
		else 
			mv $f ${DESTDIR}/restapi
		fi
	fi
done 

if [ -f Makefile.rest ]; then
    mv Makefile.rest ${DESTDIR}/appui/Makefile
fi

for f in ${jsp_files}; do
	if [ -f "$f" ]; then
		if [ -f ${DESTDIR}/restapi/$f ]; then
			manual_edit_found=$(grep "MANUAL EDIT" ${DESTDIR}/../../public_html/$f)
			if [ -n "${manual_edit_found}" ]; then
				echo "Not Moving $f. Manual Edits done"
			else
				mv $f ${DESTDIR}/../../public_html
			fi
		else 
			mv $f ${DESTDIR}/../../public_html
		fi
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
