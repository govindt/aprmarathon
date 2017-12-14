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
appui_files=`echo *Bean.java`
jsp_files=`echo *.jsp`
pers_file="persistence.properties"

dest="${DESTDIR}/busimpl"
for f in ${impl_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 

dest="${DESTDIR}/businterface"
for f in ${if_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 

dest="${DESTDIR}/busobj"
for f in ${bo_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 

dest="${DESTDIR}/appdb"
for f in ${appdb_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 

dest="${DESTDIR}/db"
if [ -f "${pers_file}" ]; then
	echo "Entries in ${f} but not found in ${dest}."
	echo "****************** Start of Difference ************"
	for f in `cat ${pers_file}`; do
		if [ -z "`grep $f ${DESTDIR}/db/${pers_file}`" ]; then
			echo $f
		fi
	done
	echo "****************** End of Difference ************"
	echo -n "Hit Enter to continue :"
	read dummy
else
	echo "Unable to find ${pers_file}"	
fi

dest="${DESTDIR}/appui"
for f in ${appui_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 

dest="${JAVA_DEV_ROOT}/src/public_html"
for f in ${jsp_files}; do
	if [ -f "$f" -a -f "${dest}/$f" ]; then
		echo "Differences between $f and old copy."
		echo "****************** Start of Difference ************"
		diff $f ${dest}/$f
		echo "****************** End of Difference ************"
	else
		echo "File $f is new - no previous copy found in ${dest}."
	fi
	echo -n "Hit Enter to continue :"
	read dummy
done 





