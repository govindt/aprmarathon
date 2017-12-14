#!/bin/sh

USERS="govind"
i=1;
TEMP=/tmp/u.sql

cp /dev/null ${TEMP}
for u in `echo $USERS`; do
	passwd=`ypmatch $u passwd | awk -F: '{print $2}'`
	echo "insert into user_tbl values(user_tbl_seq.nextval, '$u', '$passwd');" >> ${TEMP}
	i=`expr $i + 1`
done
