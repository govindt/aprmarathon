#!/bin/sh

SQLFILES="create_tables.sql drop_tables.sql"
for f in `echo $SQLFILES`; do
    MYSQL=/tmp/mysql_`basename $f`
    TMP=${MYSQL}.tmp

    sed -e 's/number(/decimal(/g' \
	-e 's/number/int/g'\
	-e 's/varchar2/varchar/g' \
	-e 's/timestamp/datetime/g' \
	-e 's/primary key/primary key auto_increment/g' \
	-e '/^create sequence/d' \
	-e '/^drop sequence/d' \
	-e 's/(.*_id,/(/' \
	-e 's/values.*(.*_seq.nextval,/values (/g' \
	-e 's/@drop_tables.sql;/\\. mysql_drop_tables.sql/g' \
	-e 's/drop table /drop table if exists /g' \
	-e 's/exit;/quit/g' \
	${f} > ${TMP}
    mv ${TMP} ${MYSQL}
    mv ${MYSQL} `dirname $0`
done
