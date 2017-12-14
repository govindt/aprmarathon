#!/bin/sh

# 
#   This script can be used to export data from one instance of database.
#   Script uses following env. variables or you can set them in the script.
#         = PASSWD_SYSTEM  - password for "system" oracle account.
#         = DUMP_DIR - directory where data from tables would be dumped.
#
#
#   Also set the ORACLE Varaibles and PATH variables.
#  
#   Author: Govind Thirumalai
#

DATE_STR=`date '+%d%m%Y'` 
ORACLE_SID=${ORACLE_SID:-aprm}
PASSWD_SYSTEM=${ORACLE_SID}
ORACLE_HOME=${ORACLE_HOME:-/u01/app/oracle/OraHome_1}
USER_NAME=${1:-aprm}
export ORACLE_SID ORACLE_HOME
ORAUSER=system/${PASSWD_SYSTEM}
export ORAUSER
DUMP_DIR=${DUMP_DIR:-/tmp/${DATE_STR}}


mkdir -p ${DUMP_DIR}
TABLES=" role users acl Event Event_Type Registration_Type Registration_Class Registration_Source Beneficiary Gender Age_Category T_Shirt_Size Blood_Group Payment_Type Payment_Status Registrant Participant Registrant_Payment Medal Result"

# set path variable.
PATH=$PATH:$ORACLE_HOME/bin
export PATH
for t in `echo ${TABLES}`; do
    exp $ORAUSER FILE=$DUMP_DIR/${t}.dmp tables=$USER_NAME.${t};
done
