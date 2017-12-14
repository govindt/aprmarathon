#!/bin/sh

TABLES=" role users acl Event Event_Type Registration_Type Registration_Class Registration_Source Beneficiary Gender Age_Category T_Shirt_Size Blood_Group Payment_Type Payment_Status Registrant Participant Registrant_Payment Medal Result"
TMP_FILE=/tmp/seq.sql
ORAUSER=${1:-aprm}
ORAPASS=${2:-aprm}

for table in `echo ${TABLES}`; do
	echo "set heading off;" > ${TMP_FILE}
	column=${table}_id;
	if [ -n "`echo ${table} | grep '|'`" ]; then
		column=`echo $table | awk -F'|' '{print $2}'`
		table=`echo $table | awk -F'|' '{print $1}'`
	fi
	echo "select max(${column}) from ${table};" >> ${TMP_FILE}
	echo "exit;" >> ${TMP_FILE}
	max_id=`sqlplus -s ${ORAUSER}/${ORAPASS} @${TMP_FILE}`
	echo "create sequence ${table}_seq start with `expr ${max_id} + 1` increment by 1;" > ${TMP_FILE}
	echo "exit;" >> ${TMP_FILE}
	sqlplus ${ORAUSER}/${ORAPASS} @${TMP_FILE}
done

/bin/rm -f ${TMP_FILE}
