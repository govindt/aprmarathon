#!/bin/sh
# 
#   This script can be used to import data from existing dump files.
#   Script uses following env. variables or you can set them in the script.
#         = PASSWD_SYSTEM  - password for "system" oracle account.
#         = DUMP_DIR - directory where all the dump files exists.
#
#   imp command works only if the table doesn't exist in another database.
#        so if you have created the tables, please remove them before executing
#        this script.
#
#   Also set the ORACLE Varaibles and PATH variables.
#  
#   Author: Govind Thirumalai
#
ORACLE_SID=${ORACLE_SID:-aprm}
ORACLE_HOME=${ORACLE_HOME:-/u01/app/oracle/OraHome_1}
PASSWD_SYSTEM=${ORACLE_SID}
USERNAME=aprm
USERPWD=aprm
TOUSER=aprm
export ORACLE_SID ORACLE_HOME
ORAUSER=system/${PASSWD_SYSTEM}
export ORAUSER
MY_DIR=`dirname $0`
cd ${MY_DIR}
MY_DIR=`pwd`
TABLES=" Menu acl users role"
# set path variable.
PATH=$PATH:$ORACLE_HOME/bin
export PATH

if [ "$#" -ne 1 ]; then
    echo "Usage : table_import <dump_directory_path>"
    exit 1
fi
DUMP_DIR=$1
if [ ! -d ${DUMP_DIR} ]; then
    echo "${DUMP_DIR} not a directory. Enter a valid directory"
    exit 1
else
    if [ ! -f "${DUMP_DIR}/league.dmp" ]; then
	echo "${DUMP_DIR} invalid. Enter a valid dump directory"
	exit 1
    fi
fi
sqlplus ${USERNAME}/${USERPWD} @drop_tables.sql

for t in `echo ${TABLES}`; do
    imp $ORAUSER FILE=$DUMP_DIR/${t}.dmp TOUSER=${TOUSER} FROMUSER=${USERNAME} tables=$t;
done

echo "Running create_sequence"
${MY_DIR}/create_sequence

# Execute app specific stored procedures below







