#!/bin/sh


MY_DIR=`dirname "$0"`
sdir="`pwd`"
cd ${MY_DIR}
MY_DIR="`pwd`"
cd "$sdir"

TOMCAT_BASE_DIR="${MY_DIR}/../jakarta-tomcat"
sdir="`pwd`"
cd "${TOMCAT_BASE_DIR}"
TOMCAT_BASE_DIR=`pwd`
cd "${sdir}"
TOMCAT_LIB_DIR="${TOMCAT_BASE_DIR}/webapps/aprmarathon/WEB-INF/lib"
DB_PROPERTIES="${TOMCAT_LIB_DIR}/core/db/db.properties"
DB_TEMPLATE_PROPERTIES="${TOMCAT_LIB_DIR}/core/db/dbtemplate.properties"
APP_PROPERTIES="${TOMCAT_LIB_DIR}/app/util/app.properties"
SITE_PROPERTIES="${TOMCAT_LIB_DIR}/core/util/site.properties"

ORACLE="oracle"
MY_SQL="mysql"
VALID_DB_TYPES="${ORACLE} ${MY_SQL}"
DB_TYPE=${MY_SQL}

ExtractJars() {
    sdir="`pwd`"
    cd "${TOMCAT_LIB_DIR}"
    jar xf app.jar
    jar xf core.jar
    cd "$sdir"
}

ZipJars() {
    sdir="`pwd`"
    cd "${TOMCAT_LIB_DIR}"
    jar cf app.jar app
    if [ $? -eq 0 ]; then
	rm -rf app
    fi
    jar cf core.jar core
    if [ $? -eq 0 ]; then
	rm -rf core
    fi
    cd "$sdir"
}

GetVar() {
    eval "printf '%s\n' \"\$$1\""
}

ValidateVar() {
    validate_var1="${1}"
    validate_var2="${2}"
    if [ -n "`echo ${validate_var2} | grep -w ${validate_var1}`" ]; then
	return 0
    else
	return 1
    fi
}

GetInputIntoVar() {
    msg="${1}"
    val=`GetVar "$2"`
    var="$2"
    valid_var="$3"
    null_ok=false

    if [ -n "${val}" ]; then
	null_ok=true;
    fi

    while (true); do
	printf "${msg}"
	read USER_RESPONSE
	if [ -n "${USER_RESPONSE}" ]; then
	    if [ -n "${valid_var}" ]; then
		ValidateVar "${USER_RESPONSE}" "${valid_var}"
		if [ $? -eq 0 ]; then
		    eval $var="${USER_RESPONSE}"
		    break;
		else
		    continue;
		fi
	    else
		eval $var="${USER_RESPONSE}"
		break;
	    fi
	fi
	break;
    done
}

if [ -z "${JAVA_HOME}" ]; then
    echo "JAVA_HOME variable needs to be set for the setup program."
    echo "Set the variable and rerun the script again."
    exit 1
else
    if [ ! -f "${JAVA_HOME}/bin/javac" ]; then
	echo "JAVA_HOME variable not pointing to a valid jdk location"
	exit 1
    fi
fi



GetInputIntoVar "Enter the database type (Valid types: ${VALID_DB_TYPES}): [${DB_TYPE}] " DB_TYPE "${VALID_DB_TYPES}"
GetInputIntoVar "Enter the database name " DB_NAME 
if [ "${DB_TYPE}" = "${MY_SQL}" ]; then
    SQL_COMMAND="`which mysql 2> /dev/null`"
    if [ $? -eq 0 ]; then
	SQL_COMMAND=mysql
    else
	echo "SQL client command not found. "
	echo "Make sure to set sql client binary location in path"
	exit 1
    fi
    if [ "`uname -s`" != "CYGWIN_NT-5.1" ]; then
	if [ -z "`ps -ef | grep mysqld | grep -v grep | grep lower_case_table_names`" ]; then
	    echo "mysqld needs to run with -lower_case_table_names option."
	    echo "Make sure to start the mysqld with -lower_case_table_names and "
	    echo "restart setup."
	    exit 1
	fi
    fi
else
    SQL_COMMAND="`which sqlplus 2> /dev/null`"
    if [ $? -eq 0 ]; then
	SQL_COMMAND=sqlplus
    else
	echo "SQL client command not found. "
	echo "Make sure to set sql client binary location in path"
	exit 1
    fi
fi


if [ "${DB_TYPE}" = "${ORACLE}" ]; then
    export ORACLE_SID=${DB_NAME}
    ADMIN_USER=system
    printf "Enter the ${ADMIN_USER} username for database or enter to choose default user[${ADMIN_USER}] "
    read USER_RESPONSE
    if [ -n "${USER_RESPONSE}" ]; then
	ADMIN_USER="${USER_RESPONSE}"
    fi
    while (true); do
	printf "Enter the password for ${ADMIN_USER} "
	stty -echo
	read USER_RESPONSE
	stty echo
	if [ -z "${USER_RESPONSE}" ]; then
	    echo "Invalid password for ${ADMIN_USER}"
	else
	    SYS_P="${USER_RESPONSE}"
	    break;
	fi
    done
    ${SQL_COMMAND} ${ADMIN_USER}/${SYS_P}<<EOF
    create user aprm identified by aprm;
    grant dba to aprm;
    commit;
    connect aprm/aprm;
    @create_tables;
EOF
    if [ $? -ne 0 ]; then
	echo "SQL Command failed. Enter proper password for system user";
	exit 1;
    fi
    ExtractJars
    sed -e "s/%DB_NAME%/$DB_NAME/g" \
        -e "s/%DB_DRIVER%/oracle.jdbc.driver.OracleDriver/" \
	-e "s/%DB_PROTOCOL%/jdbc:oracle:thin/" \
	-e "sX%DB_PROTOCOL_SEPARATOR%X@X" \
	-e "s/%DB_PORT%/1521/" \
	-e "sX%DB_NAME_SEPARATOR%X:X" \
	    "${DB_TEMPLATE_PROPERTIES}" > /tmp/db.properties
    if [ $? -eq 0 ]; then
	mv /tmp/db.properties "${DB_PROPERTIES}"
    else
	echo "Failed to edit ${DB_PROPERTIES}";
	exit 1;
    fi
elif [ "${DB_TYPE}" = "${MY_SQL}" ]; then
    ADMIN_USER=root
    echo "${MY_SQL} database setup requires ${ADMIN_USER} password"
    echo "${SQLCOMMAND}"
    ${SQL_COMMAND} -u "${ADMIN_USER}" -p "${DB_NAME}" <<EOF
    GRANT ALL PRIVILEGES ON *.* TO 'aprm'@'%' IDENTIFIED BY 'aprm' WITH GRANT OPTION;
    create database if not exists aprm;
    source mysql_create_tables.sql
EOF
    if [ $? -ne 0 ]; then
	echo "SQL Command failed. Enter proper password for system user";
	exit 1;
    fi
    ExtractJars
    sed -e "s/%DB_NAME%/$DB_NAME/g" \
        -e "s/%DB_DRIVER%/com.mysql.jdbc.Driver/" \
	-e "s/%DB_PROTOCOL%/jdbc:mysql/" \
	-e "sX%DB_PROTOCOL_SEPARATOR%X//X" \
	-e "s/%DB_PORT%/3306/" \
	-e "sX%DB_NAME_SEPARATOR%X/X" \
	    "${DB_TEMPLATE_PROPERTIES}" > /tmp/db.properties

    if [ $? -eq 0 ]; then
	mv /tmp/db.properties "${DB_PROPERTIES}"
    else
	echo "Failed to edit ${DB_PROPERTIES}";
	exit 1;
    fi
fi

sed -e "s/^app.dbtype=.*/app.dbtype=$DB_TYPE/g" \
    -e "sX^app.jsp_base=.*Xapp.jsp_base=/aprmarathon/jsp/X" \
    "${APP_PROPERTIES}" > /tmp/app.properties
if [ $? -eq 0 ]; then
    mv /tmp/app.properties "${APP_PROPERTIES}"
else
    echo "Failed to edit ${APP_PROPERTIES}";
    exit 1;
fi

if [ "`uname -s`" = "CYGWIN_NT-5.1" ]; then
    MY_TOMCAT_BASE_DIR="`cygpath --mixed ${TOMCAT_BASE_DIR}`"
else
    MY_TOMCAT_BASE_DIR="${TOMCAT_BASE_DIR}"
fi

sed -e "sX^site.logfile=.*Xsite.logfile=${MY_TOMCAT_BASE_DIR}/logs/aprmarathon.logX" \
    "${SITE_PROPERTIES}" > /tmp/site.properties
if [ $? -eq 0 ]; then
    mv /tmp/site.properties "${SITE_PROPERTIES}"
else
    echo "Failed to edit ${SITE_PROPERTIES}";
    exit 1;
fi
ZipJars

if [ -f "${MY_DIR}/run_java.sh" ]; then
    JAVA_CMD="${MY_DIR}/run_java.sh"
else
    JAVA_CMD="${MY_DIR}/run_java"
fi
chmod +x ${JAVA_CMD}

echo "Testing database setup.................."
echo 

"${JAVA_CMD}" DbTest 2> /dev/null
if [ $? -eq 0 ]; then
    echo "------------------------------------"
    echo "Database setup completed successfully"
    echo "------------------------------------"
else
    echo "------------------------------------"
    echo "Database setup failed"
    echo "------------------------------------"

fi

ADMIN_USER=admin
printf "Enter the username for Admin user [${ADMIN_USER}] "
read USER_RESPONSE
if [ -n "${USER_RESPONSE}" ]; then
    ADMIN_USER="${USER_RESPONSE}"
fi

while (true); do
    printf "Enter the password you want for the ${ADMIN_USER} "
    stty -echo
    read USER_RESPONSE
    stty echo
    if [ -z "${USER_RESPONSE}" ]; then
	echo "Invalid password for ${ADMIN_USER}"
    else
	ADMIN_P="${USER_RESPONSE}"
	break;
    fi
done

echo
"${JAVA_CMD}" AddUser ${ADMIN_USER} ${ADMIN_P} 2> /dev/null
if [ $? -eq 0 ]; then
    echo "---------------------------------"
    echo "User Setup completed successfully"
    echo "---------------------------------"
else
    echo "---------------------------------"
    echo "User Setup failed"
    echo "---------------------------------"
fi

chmod +x ${MY_DIR}/*
chmod +x ${MY_DIR}/../jakarta-tomcat/bin/*

echo "+++++++++++++++++++++++++++++++++"
echo 
echo "Next steps:"
echo "==========="
echo
echo "    1. Run ${MY_DIR}/../jakarta-tomcat/bin/startup.bat or startup.sh"
echo "    2. ${MY_DIR}/post_to_url to complete setup"
echo
echo "+++++++++++++++++++++++++++++++++"

