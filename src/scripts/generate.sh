#!/bin/sh


SCHEMA_FILE=${1:-/tmp/schema}
NAWK=/usr/bin/awk
PROJECT_NAME=app
PROJECT_TITLE="APR Marathon Registration App"
MAKEFILE=Makefile

if [ ! -f ${SCHEMA_FILE} ]; then
	printf "${SCHEMA_FILE} does not exist\n"
	exit -1;
fi
######################################################################################################
CreateMakefileEntry() {
    return;
    module_name=$1
    file_types="$2"
    dest_dir=$3
    last_file=$4
    # Begin Edits Makefile 
    cp /dev/null ${MAKEFILE}.${module_name}
    for f in `ls ${file_types}`; do
	printf "${f}\n" >> ${MAKEFILE}.${module_name}
    done
    cp /dev/null /tmp/${MAKEFILE}.${module_name}
    exec 3<&0
    exec 0<${MAKEFILE}.${module_name}
    while read line; do
	line=`echo $line`;
	if [ -z "`grep \"${line}\" ${dest_dir}/${MAKEFILE}`" ]; then
	    printf "\t$line %s\n" "\\" >> /tmp/${MAKEFILE}.${module_name}
	fi
    done
    exec 0<&3
    mv /tmp/${MAKEFILE}.${module_name} .
    sed "/$last_file/r ${MAKEFILE}.${module_name}" ${dest_dir}/${MAKEFILE} > /tmp/${MAKEFILE}.${module_name}
    mv /tmp/${MAKEFILE}.${module_name} .
}
######################################################################################################

printf "core.busobj.IdObject=core.appdb.PersistentId\n" > persistence.properties

${NAWK} -v QUOTE="'" -v logname=${LOGNAME} -v version=1.0 -v since=1.0 -v proj_name=${PROJECT_NAME} 'BEGIN { 
  table_name = ""; i = 0; k = 0;
  upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
  lower="abcdefghijklmnopqrstuvwxyz"}

  function write_sql_file() {
	create_name = "create_tables.sql";
	drop_name = "drop_tables.sql";
	tfile = "/tmp/tables";
	rfile = "/tmp/rtables";
	if ( table_name == "" ) {
		printf("@%s;\n", drop_name) > create_name;
		printf("create sequence role_seq start with 1 increment by 1;\n") > create_name;
		printf("create sequence users_seq start with 1 increment by 1;\n") > create_name;
		printf("create sequence acl_seq start with 1 increment by 1;\n") > create_name;
		printf("create sequence site_seq start with 1 increment by 1;\n") > create_name;
		printf("create sequence menu_seq start with 1 increment by 1;\n") > create_name;
		printf("\n") >> create_name;
		printf("create table Role(\n\trole_id number primary key,\n\trole_name varchar2(30) not null,\n\tis_valid varchar2(1) default %sY%s\n);\n", QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;
		printf("insert into Role(role_id, role_name, is_valid) values (role_seq.nextval, %sAdministrator%s, %sY%s);\n", QUOTE, QUOTE, QUOTE, QUOTE) >> create_name;
		printf("insert into Role(role_id, role_name, is_valid) values (role_seq.nextval, %sNormal User%s, %sY%s);\n", QUOTE, QUOTE, QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;
		printf("create table Users(\n\tusers_id number primary key,\n\tusername varchar2(30) not null,\n\tpassword varchar2(30) not null,\n\temail varchar2(100),\n\trole_id number references Role(role_id),\n\tis_valid varchar2(1) default %sY%s\n);\n", QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;
		
		printf("create table Acl(\n\tacl_id number primary key,\n\tacl_page varchar2(30) not null,\n\tis_valid varchar2(1) default %sY%s,\n\trole_id number references Role(role_id),\n\tusers_id number references Users(users_id),\n\tpermission number\n);\n", QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;

		printf("create table Site(\n\tsite_id number primary key,\n\tsite_name varchar2(50) not null,\n\tsite_url varchar2(200)\n);\n") >> create_name;
		printf("\n") >> create_name;
		printf("insert into Site(site_id, site_name, site_url) values (site_seq.nextval, %sLocal Host%s, %shttp://localhost:9002%s);\n", QUOTE, QUOTE, QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;
		
		printf("create table Menu(\n\tmenu_id number primary key,\n\tmenu_name varchar2(50) not null,\n\tsite_id number references Site(site_id),\n\turl varchar2(200),\n\tmenu_order number not null,\n\tparent_menu_id number references Menu(menu_id),\n\trole_id number references Role(role_id)\n);\n") >> create_name;
		printf("insert into Menu values (-1, %sRoot Menu%s, 1, NULL, 0, NULL, 1);\n", QUOTE, QUOTE) >> create_name;
		printf("\n") >> create_name;
	} else {
		printf("create sequence %s_seq start with 1 increment by 1;\n", tolower(table_name))>> create_name;
		printf("create table %s(\n", table_name)>> create_name;
		for ( j = 1; j <= i; j++ ) {
	  		if (keys[j]  == "key" ) {  
				if ( field_types[j] == "int" ) {
					printf("\t%s number primary key", field_names[j]) >> create_name;
				}
			}
			else {
				if ( orig_field_types[j] == "double" )
					temp = "number(30,2)";
				else if ( orig_field_types[j] == "int" )
					temp = "number";
				else
					temp = orig_field_types[j];
                                if ( keys[j] == "not" )
				        printf("\t%s %s not null", field_names[j], temp) >> create_name;
                                else
				        printf("\t%s %s", field_names[j], temp) >> create_name;
				if ( keys[j] != "" && keys[j] != "not" ) {
					ref = keys[j];
					dot_idx = index(ref, ".");
					ref_part = substr(ref, 0, dot_idx - 1);
					printf(" references %s)", ref_part) >> create_name;
				}
			}
			if ( j == i ) {
				printf("\n", field_names[j]) >> create_name;
			} else {
				printf(",\n", field_names[j]) >> create_name;
			}
		}
		printf(");\n\n")>> create_name;
	}
	close(create_name);
	printf("\n") > drop_name;
	rdx = 0;
	printf("\n") > tfile;
	printf("\n") > rfile;

	for ( kdx = 0; kdx < k; kdx++ )  {
		res[rdx++] = all_tables[kdx];
		printf("%s\n", all_tables[kdx])>>tfile;
	}
	for ( kdx = k - 1; kdx >= 0; kdx-- ) {
		printf("drop sequence %s_seq;\n", tolower(res[kdx]))>> drop_name;
		printf("drop table %s;\n", tolower(res[kdx]))>> drop_name;
		printf("\n") >> drop_name;
		printf("%s\n", res[kdx])>>rfile;
	} 
	printf("\n") >> drop_name;

	printf("drop table Menu;\n")>> drop_name;
	printf("drop table Site;\n")>> drop_name;
	printf("drop table Acl;\n")>> drop_name;
	printf("drop table Users;\n")>> drop_name;
	printf("drop table Role;\n")>> drop_name;
	printf("\n") >> drop_name;

	printf("drop sequence menu_seq;\n")>> drop_name;
	printf("drop sequence site_seq;\n")>> drop_name;
	printf("drop sequence acl_seq;\n")>> drop_name;
	printf("drop sequence users_seq;\n")>> drop_name;
	printf("drop sequence role_seq;\n")>> drop_name;
	printf("\n") >> drop_name;

	close(drop_name); 
	close(tfile); 
	close(rfile); 
}

  function write_properties_file() {
	properties_filename = "persistence.properties";
	tmp_name = table_name;
	gsub("_", "", tmp_name);
	printf("%s.busobj.%sObject=%s.appdb.Persistent%s\n", proj_name, tmp_name, proj_name, tmp_name) >> properties_filename;
  }

  function write_persistent_file() {
	persistent_filename = "Persistent" tmp_file_name ".java";
	printf("package %s.appdb;\n\n", proj_name)>persistent_filename;
	printf("import java.sql.ResultSet;\n")>>persistent_filename;
	printf("import java.sql.Types;\n")>>persistent_filename;
	printf("import java.util.ArrayList;\n")>>persistent_filename;
	
	printf("import core.db.PersistentObject;\n")>>persistent_filename;
	printf("import core.db.SQLParam;\n")>>persistent_filename;
	printf("import core.db.SQLStatement;\n")>>persistent_filename;
	printf("import core.db.PreparedSQLStatement;\n")>>persistent_filename;
	printf("import core.db.CallableSQLStatement;\n")>>persistent_filename;
	printf("import core.db.DBException;\n")>>persistent_filename;
	printf("import core.util.DebugHandler;\n")>>persistent_filename;
	printf("import core.util.Constants;\n")>>persistent_filename;
	printf("import app.util.AppConstants;\n")>>persistent_filename;
	printf("import %s.busobj.%sObject;\n\n", proj_name, tmp_file_name)>>persistent_filename;
	FIRSTCHAR = substr(tmp_file_name,1,1);
	if (CHAR = index(upper, FIRSTCHAR)) {
	  tmp = substr(lower, CHAR, 1) substr(tmp_file_name, 2) "Object";
	  tmp1 = substr(lower, CHAR, 1) substr(tmp_file_name, 2);
        }
	else {
	  tmp = tmp_file_name "Object";
	}

	# Initial Comments for the Class
	printf("/**\n") >> persistent_filename;
	printf(" * The persistent implementation of the %sObject\n", tmp_file_name) >> persistent_filename;
	printf(" * @version %s\n", version) >> persistent_filename;
	printf(" * @author %s\n", logname) >> persistent_filename;
	printf(" * @since %s\n", since) >> persistent_filename;
	printf(" */\n\n") >> persistent_filename;
	# End - Initial Comments for the Class

	# Start Class Definition
	printf("public class Persistent%s extends PersistentObject {\n", tmp_file_name)>>persistent_filename;

	printf("\tprivate %sObject %s;\n", tmp_file_name, tmp)>>persistent_filename;

	printf("\t\n")>>persistent_filename;

	# Comments for Constructor
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t * Constructs a Persistent Object for the %sObject\n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @param %s    the %sObject \n", tmp, tmp_file_name) >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for Constructor


	printf("\tpublic Persistent%s (%sObject %s) {\n", tmp_file_name, tmp_file_name, tmp)>>persistent_filename;
	printf("\t    this.%s = %s;\n", tmp, tmp)>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;

	# Comments for list method	
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t * Returns the ArrayList of %sObject.\n", tmp_file_name) >> persistent_filename;
	printf("\t * It is Usually all the rows in the database.\n") >> persistent_filename;
	printf("\t * This calls getResultObjects method in the super class.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return     ArrayList of %sObject \n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @see     #getResultObjects(ResultSet)\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for list method	


	# list Method
	printf("\tpublic Object list() throws DBException {\n")>>persistent_filename;
	printf("\t    PreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t    String statement = \"SELECT ")>>persistent_filename;
	for ( j = 1; j < i; j++ ) {
	  printf("%s, ", field_names[j])>>persistent_filename;
	}
	printf("%s from %s\";\n", field_names[j], table_name)>>persistent_filename;

	printf("\t    int index = 1;\n")>>persistent_filename;
        printf("\t    sql.setStatement(statement);\n        \n")>>persistent_filename;
	printf("\t    setSQLStatement(sql);\n        \n")>>persistent_filename;

        printf("\t    @SuppressWarnings(\"unchecked\")\n")>>persistent_filename;
	printf("\t    ArrayList<%sObject> result = (ArrayList<%sObject>) super.list();\n        \n", tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End - list Method

	# Comments for list overloaded method	
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t * Returns the ArrayList of %sObjects.\n", tmp_file_name) >> persistent_filename;
	printf("\t * It is Usually all the rows that match the criteria in the database.\n") >> persistent_filename;
	printf("\t * This calls getResultObjects method in the super class.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return     ArrayList of %sObject \n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @see     #getResultObjects(ResultSet)\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for list method	


	# list Method
	printf("\tpublic Object list(Object args) throws DBException {\n")>>persistent_filename;
	printf("\t\tPreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t\tString statement = \"SELECT ")>>persistent_filename;
	for ( j = 1; j < i; j++ ) {
	  printf("%s, ", field_names[j])>>persistent_filename;
	}
	printf("%s from %s\";\n", field_names[j], table_name)>>persistent_filename;

	printf("\t\tint index = 1;\n")>>persistent_filename;
	printf("\t\t%sObject passed%sObject = (%sObject)args;\n", tmp_file_name, tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t\tboolean whereSpecified = false;\n\n", tmp_file_name, tmp_file_name)>>persistent_filename;
	for ( j = 1; j <= i; j++ ) {
	    if ( field_types[j] == "int" ) {
			printf("\t\tif ( passed%sObject.get%s() != 0 ) {\n", tmp_file_name, new_field_names[j])>>persistent_filename; 
	    }
	    else if ( field_types[j] == "String" ) {
			printf("\t\tif ( ! passed%sObject.get%s().equals(\"\") ) {\n", tmp_file_name, new_field_names[j])>>persistent_filename; 
	    }
	    else if ( field_types[j] == "Date" ) {
			printf("\t\tif ( passed%sObject.get%s() != null ) {\n", tmp_file_name, new_field_names[j])>>persistent_filename; 
	    }
	    else if ( field_types[j] == "double" ) {
			printf("\t\tif ( passed%sObject.get%s() != 0.0 ) {\n", tmp_file_name, new_field_names[j])>>persistent_filename; 
	    }
	    else if ( field_types[j] == "float" ) {
			printf("\t\tif ( passed%sObject.get%s() != 0.0f ) {\n", tmp_file_name, new_field_names[j])>>persistent_filename; 
	    }
	    if ( j == 1 ) {
			printf("\t\t\tstatement += \" where %s = ?\";\n", field_names[j])>>persistent_filename;
			printf("\t\t\twhereSpecified = true;\n")>>persistent_filename;
	    } else {
			printf("\t\t\tif ( ! whereSpecified ) {\n")>>persistent_filename;
			printf("\t\t\t\tstatement += \" where %s = ?\";\n", field_names[j])>>persistent_filename;
			printf("\t\t\t\twhereSpecified = true;\n")>>persistent_filename;
			printf("\t\t\t} else\n")>>persistent_filename;
			printf("\t\t\t\tstatement += \" and %s = ?\";\n", field_names[j])>>persistent_filename;
	    }
	    printf("\t\t\tsql.setStatement(statement);\n")>>persistent_filename;
			if ( field_types[j] == "int" ) {
			printf("\t\t\tsql.setInParams(new SQLParam(index++, new Integer(passed%sObject.get%s()), Types.INTEGER));\n", tmp_file_name, new_field_names[j])>>persistent_filename;;
	    }
	    else if ( field_types[j] == "String" ) {
			printf("\t\t\tsql.setInParams(new SQLParam(index++,  passed%sObject.get%s(), Types.VARCHAR));\n", tmp_file_name, new_field_names[j])>>persistent_filename;;
	    }
	    else if ( field_types[j] == "Date" ) {
			printf("\t\t\tsql.setInParams(new SQLParam(index++,  passed%sObject.get%s(), Types.TIMESTAMP));\n", tmp_file_name, new_field_names[j])>>persistent_filename;;
	    }
	    else if ( field_types[j] == "double" ) {
			printf("\t\t\tsql.setInParams(new SQLParam(index++,  new Double(passed%sObject.get%s()), Types.DOUBLE));\n", tmp_file_name, new_field_names[j])>>persistent_filename;;
	    }
	    else if ( field_types[j] == "float" ) {
			printf("\t\t\tsql.setInParams(new SQLParam(index++,  new Float(passed%sObject.get%s()), Types.FLOAT));\n", tmp_file_name, new_field_names[j])>>persistent_filename;;
	    }
	    printf("\t\t}\n")>>persistent_filename;
	}
    printf("\t\tsql.setStatement(statement);\n        \n")>>persistent_filename;
	printf("\t\tDebugHandler.debug(statement);\n")>>persistent_filename;
	printf("\t\tsetSQLStatement(sql);\n        \n")>>persistent_filename;

    printf("\t\t@SuppressWarnings(\"unchecked\")\n")>>persistent_filename;
	printf("\t\tArrayList<%sObject> result = (ArrayList<%sObject>) super.list();\n        \n", tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t\treturn result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End - list Method

	# Comments for fetch Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t * Returns the ArrayList of one %sObject.\n", tmp_file_name) >> persistent_filename;
	printf("\t * It is Usually the row that matches primary key.\n") >> persistent_filename;
	printf("\t * This calls getResultSetObject method in the super class.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return     ArrayList of one %sObject \n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @see     #getResultSetObject(ResultSet)\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for fetch Method

	# fetch Method
	printf("\tpublic Object fetch() throws DBException {\n")>>persistent_filename;
	printf("\t    PreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t    String statement = \"SELECT ")>>persistent_filename;
	for ( j = 1; j < i; j++ ) {
	  printf("%s, ", field_names[j])>>persistent_filename;
	}
	printf("%s from %s where ", field_names[j], table_name)>>persistent_filename;

	one_found = 0;
	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		if ( one_found == 0 ) {
		  one_found = 1;
		  printf("%s = ? ", field_names[j])>>persistent_filename;
		}
		else {
		  printf("and %s = ? ", field_names[j])>>persistent_filename;
		}
	  }
	}
	printf("\";\n")>>persistent_filename;
	printf("\t    int index = 1;\n")>>persistent_filename;
        printf("\t    sql.setStatement(statement);\n")>>persistent_filename;

	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		if ( field_types[j] == "int" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "String" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "Date" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "double" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++, new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "float" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++, new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
	  }
	}
	printf("\t    setSQLStatement(sql);\n        \n")>>persistent_filename;

        printf("\t    @SuppressWarnings(\"unchecked\")\n")>>persistent_filename;
	printf("\t    ArrayList<%sObject> result = (ArrayList<%sObject>) super.fetch();\n        \n", tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End - fetch Method

	# Comments for insert Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * Inserts a row in the database.  The values\n", tmp_file_name) >> persistent_filename;
	printf("\t * are got from the %s.\n", tmp) >> persistent_filename;
	printf("\t * Returns an Integer Object with value 0 on success\n") >> persistent_filename;
	printf("\t * and -1 on faliure.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return      Returns an Integer indicating success/failure of the database operation\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for insert Method

	# insert Method
	printf("\tpublic Object insert() throws DBException {\n")>>persistent_filename;
	printf("\t    PreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t    String statement;\n")>> persistent_filename;
	printf("\t    int index = 1;\n\n")>> persistent_filename;
	printf("\t    if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {\n")>> persistent_filename;
	printf("\t        statement = \"INSERT INTO %s (", table_name)>> persistent_filename;
        for ( j = 1; j < i; j++ ) {
	  printf("%s, ", field_names[j])>>persistent_filename;
	}
	printf("%s) ", field_names[j], table_name)>>persistent_filename;
	printf("VALUES(")>>persistent_filename;
	for ( j = 1; j < i; j++ ) {
	  printf("?, ")>>persistent_filename;
	}
	printf("?) ")>>persistent_filename;
	printf("\";\n")>>persistent_filename;

        printf("\t        sql.setStatement(statement);\n")>>persistent_filename;
        if ( field_types[1] == "int" ) {
		printf("\t        sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[1])>>persistent_filename;;
	}
	else if ( field_types[1] == "String" ) {
		printf("\t        sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[1])>>persistent_filename;;
	}
	else if ( field_types[1] == "Date" ) {
		printf("\t        sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[1])>>persistent_filename;;
	}
	else if ( field_types[1] == "double" ) {
		printf("\t        sql.setInParams(new SQLParam(index++,  new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
	}
	else if ( field_types[1] == "float" ) {
		printf("\t        sql.setInParams(new SQLParam(index++,  new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
	}
        printf("\t    } else {\n")>> persistent_filename;
        printf("\t        statement = \"INSERT INTO %s (", table_name)>> persistent_filename;
        for ( j = 2; j < i; j++ ) {
	  printf("%s, ", field_names[j])>>persistent_filename;
	}
	printf("%s) ", field_names[j], table_name)>>persistent_filename;
	printf("VALUES(")>>persistent_filename;
	for ( j = 2; j < i; j++ ) {
	  printf("?, ")>>persistent_filename;
	}
	printf("?) ")>>persistent_filename;
	printf("\";\n")>>persistent_filename;

        printf("\t        sql.setStatement(statement);\n")>>persistent_filename;
        printf("\t    }\n")>> persistent_filename;
	for ( j = 2; j <= i; ++j ) {
	  if ( field_types[j] == "int" ) {
		printf("\t    sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "String" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "Date" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "double" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "float" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	}
	
	printf("\t    setSQLStatement(sql);\n        \n")>>persistent_filename;

	printf("\t    Integer result = (Integer) super.insert();\n        \n")>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End insert Method
	
	# Comments for delete Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * Deletes a row in the database. The key is \n") >> persistent_filename;
	printf("\t * in the %s.\n", tmp) >> persistent_filename;
	printf("\t * Returns an Integer Object with value 0 on success\n") >> persistent_filename;
	printf("\t * and -1 on faliure.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return      Returns an Integer indicating success/failure of the database operation\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for delete Method

	# delete method
	printf("\tpublic Object delete() throws DBException {\n")>>persistent_filename;
	printf("\t    PreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t    String statement = \"DELETE FROM %s WHERE ", table_name)>>persistent_filename;

	one_found = 0;
	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		if ( one_found == 0 ) {
		  one_found = 1;
		  printf("%s = ? ", field_names[j])>>persistent_filename;
		}
		else {
		  printf("AND %s = ? ", field_names[j])>>persistent_filename;
		}
	  }
	}
	printf("\";\n")>>persistent_filename;
	printf("\t    int index = 1;\n")>>persistent_filename;
    printf("\t    sql.setStatement(statement);\n")>>persistent_filename;

	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		if ( field_types[j] == "int" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "String" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "Date" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "double" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "float" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
	  }
	}
	printf("\t    setSQLStatement(sql);\n        \n")>>persistent_filename;

	printf("\t    Integer result = (Integer) super.delete();\n        \n")>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End delete method

	# Comments for update Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * Updates a row in the database. The values are \n") >> persistent_filename;
	printf("\t * got from the %s.\n", tmp) >> persistent_filename;
	printf("\t * Returns an Integer Object with value 0 on success\n") >> persistent_filename;
	printf("\t * and -1 on faliure.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return      Returns an Integer indicating success/failure of the database operation\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @throws     DBException     If a database error occurs\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for update Method

	# update method
	printf("\tpublic Object update() throws DBException {\n")>>persistent_filename;
	printf("\t    PreparedSQLStatement sql = new PreparedSQLStatement();\n")>>persistent_filename;
	printf("\t    String statement = \"UPDATE %s SET ", table_name)>>persistent_filename;
	for ( j = 1; j < i; j++ ) {
	  printf("%s = ?, ", field_names[j])>>persistent_filename;
	}
	printf("%s = ? where ", field_names[j], table_name)>>persistent_filename;

	one_found = 0;
	for ( j = 1; j <= i; ++j ) {
	  if (keys[j] == "key" ) {  
		if ( one_found == 0 ) {
		  one_found = 1;
		  printf("%s = ? ", field_names[j])>>persistent_filename;
		}
		else {
		  printf("AND %s = ? ", field_names[j])>>persistent_filename;
		}
	  }
	}
	printf("\";\n")>>persistent_filename;
	printf("\t    int index = 1;\n")>>persistent_filename;
    printf("\t    sql.setStatement(statement);\n")>>persistent_filename;

	for ( j = 1; j <= i; ++j ) {
	  if ( field_types[j] == "int" ) {
		printf("\t    sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "String" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "Date" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "double" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	  else if ( field_types[j] == "float" ) {
		printf("\t    sql.setInParams(new SQLParam(index++,  new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
	  }
	}
	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		if ( field_types[j] == "int" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++, new Integer(%s.get%s()), Types.INTEGER));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "String" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.VARCHAR));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "Date" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  %s.get%s(), Types.TIMESTAMP));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "double" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  new Double(%s.get%s()), Types.DOUBLE));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
		else if ( field_types[j] == "float" ) {
		  printf("\t    sql.setInParams(new SQLParam(index++,  new Float(%s.get%s()), Types.FLOAT));\n", tmp, new_field_names[j])>>persistent_filename;;
		}
	  }
	}
	printf("\t    setSQLStatement(sql);\n        \n")>>persistent_filename;

	printf("\t    Integer result = (Integer) super.update();\n        \n")>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End update method

	# Comments for getResultsObjects Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * Returns a ArrayList of %sObject from the ResultSet. The values for \n", tmp_file_name) >> persistent_filename;
	printf("\t * each object is got from the ResultSet.\n") >> persistent_filename;
	printf("\t * This is used by the list method.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @param rs      the ResultSet.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return      Returns a ArrayList of %sObject from the ResultSet.\n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @see     #list()\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for getResultsObjects Method

	# getResultObjects method
	printf("\tpublic Object getResultObjects(ResultSet rs) {\n")>>persistent_filename;
	printf("\t    ArrayList<%sObject> result = new ArrayList<%sObject>();\n        \n", tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t    try {\n")>>persistent_filename;
	printf("\t        while(rs.next()) {\n")>>persistent_filename;
	printf("\t            int index = 1;\n")>>persistent_filename;
	printf("\t            %sObject f = new %sObject();\n", tmp_file_name, tmp_file_name)>>persistent_filename;

	for ( j = 1; j <= i; ++j ) {
	  if ( field_types[j] == "int" ) {
		printf("\t            f.set%s(rs.getInt(index++));\n", new_field_names[j])>>persistent_filename;
	  }
	  else if ( field_types[j] == "String" ) {
		printf("\t            f.set%s(rs.getString(index++));\n", new_field_names[j])>>persistent_filename;
	  }
	  else if ( field_types[j] == "Date" ) {
		printf("\t            f.set%s(rs.getDate(index++));\n", new_field_names[j])>>persistent_filename;
	  }
	  else if ( field_types[j] == "double" ) {
		printf("\t            f.set%s(rs.getDouble(index++));\n", new_field_names[j])>>persistent_filename;
	  }
	  else if ( field_types[j] == "float" ) {
		printf("\t            f.set%s(rs.getFloat(index++));\n", new_field_names[j])>>persistent_filename;
	  }
	}
	printf("\t            result.add(f);\n")>>persistent_filename;
	printf("\t        }\n")>>persistent_filename;
	printf("\t    } catch (Exception e) {\n")>>persistent_filename;
	printf("\t        e.printStackTrace();\n")>>persistent_filename;
	printf("\t    }\n")>>persistent_filename;
	printf("\t    return result;\n")>>persistent_filename;
	printf("\t}\n    \n")>>persistent_filename;
	# End getResultObjects method
	
	# Comments for getResultsObject Method
	printf("\t\n    /**\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * Returns a %sObject from the ResultSet. The values for \n", tmp_file_name) >> persistent_filename;
	printf("\t * each object is got from the ResultSet.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * This is used by the fetch method.\n") >> persistent_filename;
	printf("\t * @param rs      the ResultSet.\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @return      Returns a %sObject from the ResultSet.\n", tmp_file_name) >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t * @see     #fetch()\n") >> persistent_filename;
	printf("\t *\n") >> persistent_filename;
	printf("\t */\n    \n") >> persistent_filename;
	# End - Comments for getResultsObject Method

	# getResultSetObject method
	printf("\tpublic Object getResultSetObject(ResultSet rs) {\n")>>persistent_filename;
	printf("\t    try {\n")>>persistent_filename;
        printf("\t    @SuppressWarnings(\"unchecked\")\n")>>persistent_filename;
	printf("\t        ArrayList<%sObject> result = (ArrayList<%sObject>) getResultObjects(rs);\n", tmp_file_name, tmp_file_name)>>persistent_filename;
	printf("\t        return result.get(0);\n")>>persistent_filename;
	printf("\t    } catch (Exception e) {\n")>>persistent_filename;
    printf("\t        return null;\n")>>persistent_filename;
	printf("\t    }\n")>>persistent_filename;
	printf("\t}\n")>>persistent_filename;
	
	printf("}\n    \n")>>persistent_filename; 
	# End getResultSetObject method
	close(persistent_filename);
  }

  function write_rest_file() {
	printf("Generating REST Code for : %s %d\n", table_name, i);
	printf("package %s.restapi;\n\n", proj_name)>rest_file_name;
	printf("import java.util.ArrayList;\n")>>rest_file_name;
	printf("import java.util.Enumeration;\n")>>rest_file_name;
	printf("import java.io.InputStream;\n")>>rest_file_name;
	printf("import javax.ws.rs.Consumes;\n")>>rest_file_name;
	printf("import javax.ws.rs.Produces;\n")>>rest_file_name;
	printf("import javax.ws.rs.POST;\n")>>rest_file_name;
	printf("import javax.ws.rs.Path;\n")>>rest_file_name;
	printf("import javax.ws.rs.core.MediaType;\n")>>rest_file_name;
	printf("import javax.ws.rs.core.Response;\n")>>rest_file_name;
	printf("import core.util.Util;\n")>>rest_file_name;
	printf("import core.util.DebugHandler;\n")>>rest_file_name;
	printf("import core.util.AppException;\n")>>rest_file_name;
	printf("import app.busimpl.%sImpl;\n",tmp_file_name)>>rest_file_name;
	printf("import app.businterface.%sInterface;\n", tmp_file_name)>>rest_file_name;
	printf("import app.busobj.%sObject;\n", tmp_file_name)>>rest_file_name;
	printf("import app.util.App;\n")>>rest_file_name;
	printf("import org.codehaus.jettison.json.JSONObject;\n")>>rest_file_name
	printf("import org.codehaus.jettison.json.JSONException;\n")>>rest_file_name;
	printf("import org.codehaus.jettison.json.JSONArray;\n\n")>>rest_file_name;

	# Initial Comments for the Class
	printf("/**\n") >> rest_file_name;
	printf(" * The implementation of the Data APIS for %s table\n", tmp_file_name) >> rest_file_name;
	printf(" * @version %s\n", version) >> rest_file_name;
	printf(" * @author %s\n", logname) >> rest_file_name;
	printf(" * @since %s\n", since) >> rest_file_name;
	printf(" */\n\n") >> rest_file_name;
	# End - Initial Comments for the Class

	printf("@Path(\"%s\")\n", tolower(tmp_file_name))>>rest_file_name;	
	printf("public class %sRest {\n", tmp_file_name)>>rest_file_name;

	# Start - getMethod
	printf("\t@POST\n")>>rest_file_name;	
	printf("\t@Consumes(MediaType.APPLICATION_JSON)\n")>>rest_file_name;	
	printf("\t@Produces(MediaType.APPLICATION_JSON)\n")>>rest_file_name;
	if (last_char == "s") {
		fname="get" tmp_file_name;
	} else {
		fname="get" tmp_file_name "s";
	}		
	printf("\t@Path(\"%s\")\n",fname)>>rest_file_name;	
	printf("\tpublic Response %s(InputStream incomingData) throws AppException, JSONException {\n", fname)>>rest_file_name;	

	printf("\t\tApp.getInstance();\n")>>rest_file_name;
	printf("\t\tJsonConverter jc = new JsonConverter(incomingData);\n")>>rest_file_name;
	printf("\t\tJSONObject jObject = jc.getJsonObject();\n\n")>>rest_file_name;
	printf("\t\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\t%sObject %sObject = new %sObject(jObject);\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\tJSONArray joArr = new JSONArray();\n\n")>>rest_file_name;
	printf("\t\tDebugHandler.fine(%sObject);\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tArrayList<%sObject> v = %sIf.%s(%sObject);\n", tmp_file_name, tolower(tmp_file_name), fname, tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tint i = 0;\n", tmp_file_name)>>rest_file_name;
	printf("\t\twhile (i < v.size()) {\n")>>rest_file_name;
	printf("\t\t\t%sObject = v.get(i);\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\t\tJSONObject jo = %sObject.toJSON();\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\t\tjoArr.put(jo);\n", tmp_file_name)>>rest_file_name;
	printf("\t\t\ti++;\n")>>rest_file_name;
	printf("\t\t}\n", tmp_file_name)>>rest_file_name;
	printf("\t\treturn Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();\n", tmp_file_name)>>rest_file_name;
	printf("\t};\n\n")>>rest_file_name; #End of get method

	# Start - addMethod
	printf("\t@POST\n")>>rest_file_name;	
	printf("\t@Consumes(MediaType.APPLICATION_JSON)\n")>>rest_file_name;	
	printf("\t@Produces(MediaType.APPLICATION_JSON)\n")>>rest_file_name;
	fname="add" tmp_file_name;
	printf("\t@Path(\"%s\")\n",fname)>>rest_file_name;	
	printf("\tpublic Response %s(InputStream incomingData) throws AppException, JSONException {\n", fname)>>rest_file_name;	
	printf("\t\tApp.getInstance();\n")>>rest_file_name;
	printf("\t\tJsonConverter jc = new JsonConverter(incomingData);\n")>>rest_file_name;
	printf("\t\tJSONObject jObject = jc.getJsonObject();\n\n")>>rest_file_name;
	printf("\t\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\t%sObject %sObject = new %sObject(jObject);\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\tDebugHandler.fine(%sObject);\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tInteger result = %sIf.%s(%sObject);\n", tolower(tmp_file_name), fname, tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tJSONObject jo = %sObject.toJSON();\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tjo.put(\"result\", result);\n", tmp_file_name)>>rest_file_name;
	printf("\t\treturn Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();\n", tmp_file_name)>>rest_file_name;
	printf("\t};\n\n")>>rest_file_name; #End of add method

	
	# Start - updateMethod
	printf("\t@POST\n")>>rest_file_name;	
	printf("\t@Consumes(MediaType.APPLICATION_JSON)\n")>>rest_file_name;	
	printf("\t@Produces(MediaType.APPLICATION_JSON)\n")>>rest_file_name;
	fname="update" tmp_file_name;
	printf("\t@Path(\"%s\")\n",fname)>>rest_file_name;	
	printf("\tpublic Response %s(InputStream incomingData) throws AppException, JSONException {\n", fname)>>rest_file_name;	
	printf("\t\tApp.getInstance();\n")>>rest_file_name;
	printf("\t\tJsonConverter jc = new JsonConverter(incomingData);\n")>>rest_file_name;
	printf("\t\tJSONObject jObject = jc.getJsonObject();\n\n")>>rest_file_name;
	printf("\t\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\t%sObject %sObject = new %sObject(jObject);\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\tDebugHandler.fine(%sObject);\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tInteger result = %sIf.%s(%sObject);\n", tolower(tmp_file_name), fname, tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tJSONObject jo = %sObject.toJSON();\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tjo.put(\"result\", result);\n", tmp_file_name)>>rest_file_name;
	printf("\t\treturn Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();\n", tmp_file_name)>>rest_file_name;
	printf("\t};\n\n")>>rest_file_name; #End of update method

	
	# Start - deleteMethod
	printf("\t@POST\n")>>rest_file_name;	
	printf("\t@Consumes(MediaType.APPLICATION_JSON)\n")>>rest_file_name;	
	printf("\t@Produces(MediaType.APPLICATION_JSON)\n")>>rest_file_name;
	fname="delete" tmp_file_name;
	printf("\t@Path(\"%s\")\n",fname)>>rest_file_name;	
	printf("\tpublic Response %s(InputStream incomingData) throws AppException, JSONException {\n", fname)>>rest_file_name;	
	printf("\t\tApp.getInstance();\n")>>rest_file_name;
	printf("\t\tJsonConverter jc = new JsonConverter(incomingData);\n")>>rest_file_name;
	printf("\t\tJSONObject jObject = jc.getJsonObject();\n\n")>>rest_file_name;
	printf("\t\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\t%sObject %sObject = new %sObject(jObject);\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name)>>rest_file_name;
	printf("\t\tDebugHandler.fine(%sObject);\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tInteger result = %sIf.%s(%sObject);\n", tolower(tmp_file_name), fname, tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tJSONObject jo = %sObject.toJSON();\n", tolower(tmp_file_name))>>rest_file_name;
	printf("\t\tjo.put(\"result\", result);\n", tmp_file_name)>>rest_file_name;
	printf("\t\treturn Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();\n", tmp_file_name)>>rest_file_name;
	printf("\t};\n\n")>>rest_file_name; #End of delete method

	printf("};\n")>>rest_file_name;

	close(rest_file_name);
  }

  function write_file() {
	printf("Generating Java Code for : %s %d\n", table_name, i);
	printf("package %s.busobj;\n\n", proj_name)>file_name;
	printf("import java.util.Date;\n")>>file_name;
	printf("import core.util.DebugHandler;\n")>>file_name;
	printf("import core.util.Util;\n")>>file_name;
	printf("import core.util.Constants;\n")>>file_name;
	printf("import java.text.SimpleDateFormat;\n")>>file_name;
	printf("import java.text.ParseException;\n")>>file_name;

	printf("import org.codehaus.jettison.json.JSONObject;\n")>>file_name;
	printf("import org.codehaus.jettison.json.JSONException;\n\n")>>file_name;

	# Initial Comments for the Class
	printf("/**\n") >> file_name;
	printf(" * The implementation of the %sObject which maps a table\n", tmp_file_name) >> file_name;
	printf(" * in the database\n") >> file_name;
	printf(" * @version %s\n", version) >> file_name;
	printf(" * @author %s\n", logname) >> file_name;
	printf(" * @since %s\n", since) >> file_name;
	printf(" */\n\n") >> file_name;
	# End - Initial Comments for the Class

	printf("public class %sObject implements Cloneable {\n", tmp_file_name)>>file_name;

	for ( j = 1; j <= i; ++j ) 
	  printf("\tprivate %s %s;\n", field_types[j], field_names[j])>>file_name;

	printf("\t\n")>>file_name;

	# Comments for toString Method
	printf("\t/**\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * Returns the String representation of the %sObject.\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * @return\t Returns the String representation of the %sObject.\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End - Comments for toString Method

	# toString method	
	printf("\tpublic String toString() {\n")>>file_name;
	printf("\t   return")>>file_name;
	for ( j = 1; j < i; ++j )
            if ( j == 1 )
		printf("\t\"%s : \" + %s + \"\\n\" +\n", field_names[j], field_names[j])>>file_name;
            else
                printf("\t\t\"%s : \" + %s + \"\\n\" +\n", field_names[j], field_names[j])>>file_name;
	printf("\t\t\"%s : \" + %s + \"\\n\";\n", field_names[j], field_names[j])>>file_name;	
	printf("\t}\n    \n")>> file_name;
	# End toString method	

	# Comments for toJSON Method
	printf("\t/**\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * Returns the JSON representation of the %sObject.\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * @return      Returns the JSON representation of the %sObject.\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End - Comments for toString Method

	# toString method	
	printf("\tpublic JSONObject toJSON() {\n")>>file_name;
	printf("\t\tJSONObject jo = new JSONObject();\n")>>file_name;
	printf("\t\ttry {\n")>>file_name;
	for ( j = 1; j <= i; ++j ) {
		printf("\t\t\t jo.put(\"%s\", %s);\n", field_names[j], field_names[j])>>file_name;
	}
	printf("\t\t} catch (JSONException je) {}\n")>>file_name;
	printf("\t\treturn jo;\n")>>file_name;
	printf("\t}\n    \n")>> file_name;
	# End toJSON method	

	# Comments for hashCode
	printf("\t/**\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * Returns the hashCode representation of the %sObject.\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * @return      Returns the hashCode.\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t*/\n    \n") >> file_name;
	# End - Comments for hashCode Method

	# hashCode method	
	printf("\tpublic int hashCode() {\n")>>file_name;
	printf("\t\treturn %s;\n", field_names[1])>> file_name;
	printf("\t}\n    \n")>> file_name;
	# End toJSON method

	# Comments for Constructor
	printf("\t/**\n") >> file_name;
	printf("\t * Constructs the %sObject\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End - Comments for Constructor

	
	# Constructor
	printf("\tpublic %sObject () {\n", tmp_file_name)>>file_name;
	for ( j = 1; j <= i; ++j ) {
	  n=split(field_names[j], a, "_");
	  FIRSTCHAR = substr(a[1],1,1);
	  if (CHAR = index(lower, FIRSTCHAR)) {
		a[1] = substr(upper, CHAR, 1) substr(a[1], 2);
	  }
	  new_field_names[j] = a[1];

	  for ( idx = 2; idx <= n; idx++ ) {
		FIRSTCHAR = substr(a[idx],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		  a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
		}
		new_field_names[j] = new_field_names[j] a[idx];
	  }

	  if (field_types[j]  == "int" ) { 
		printf("\t\tset%s(0);\n", new_field_names[j])>>file_name;
	  }
	  else if (field_types[j]  == "String" )  {
		printf("\t\tset%s(\"\");\n", new_field_names[j])>>file_name;
	  }
	  else if (field_types[j]  == "Date" )  {
		printf("\t\tset%s(null);\n", new_field_names[j])>>file_name;
	  }
	  else if (field_types[j]  == "double" )  {
		printf("\t\tset%s(0.0);\n", new_field_names[j])>>file_name;
	  }
	  else if (field_types[j]  == "float" )  {
		printf("\t\tset%s(0.0f);\n", new_field_names[j])>>file_name;
	  }
	}
	printf("\t}\n    \n")>>file_name;
	# End Constructor
	
	# Comments for JSON Constructor
	printf("\t/**\n") >> file_name;
	printf("\t * Constructs the %sObject from JSONObject\n", tmp_file_name) >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End - Comments for JSON Constructor
	# Constructor
	printf("\tpublic %sObject (JSONObject jObject) {\n", tmp_file_name)>>file_name;
	for ( j = 1; j <= i; ++j ) {
	  printf("\t\ttry {\n") >> file_name;
	  n=split(field_names[j], a, "_");
	  FIRSTCHAR = substr(a[1],1,1);
	  if (CHAR = index(lower, FIRSTCHAR)) {
		a[1] = substr(upper, CHAR, 1) substr(a[1], 2);
	  }
	  new_field_names[j] = a[1];

	  for ( idx = 2; idx <= n; idx++ ) {
		FIRSTCHAR = substr(a[idx],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		  a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
		}
		new_field_names[j] = new_field_names[j] a[idx];
	  }

	  if (field_types[j]  == "int" ) { 
		printf("\t\t\t%s = jObject.getInt(\"%s\");\n", field_names[j], field_names[j])>>file_name;
	  }
	  else if ( field_types[j] == "String" ) {
		printf("\t\t\t%s = jObject.getString(\"%s\");\n", field_names[j], field_names[j])>>file_name;
	  }
	  else if ( field_types[j] == "Date" ) {
		printf("\t\t\ttry {\n")>>file_name;
		printf("\t\t\t\tSimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);\n")>>file_name;
		printf("\t\t\t\t%s = dateFormatter.parse(jObject.getString(\"%s\"));\n", field_names[j], field_names[j])>>file_name;
		printf("\t\t\t} catch (ParseException e) {\n")>>file_name;
		printf("\t\t\t\te.printStackTrace();\n")>>file_name;
		printf("\t\t\t}\n")>>file_name;
	  }
	  else if ( field_types[j] == "double" ) {
		printf("\t\t\ttry {\n")>>file_name;
		printf("\t\t\t\t%s = Double.parseDouble(jObject.getString(\"%s\"));\n",field_names[j], field_names[j])>>file_name;
		printf("\t\t\t} catch (NumberFormatException e) {\n")>>file_name;
		printf("\t\t\t\te.printStackTrace();\n")>>file_name;
		printf("\t\t\t}\n")>>file_name;
	  }
	  else if ( field_types[j] == "float" ) {
		printf("\t\t\ttry {\n")>>file_name;
		printf("\t\t\t\t%s = Float.parseFloat(jObject.getString(\"%s\"));\n",field_names[j], field_names[j])>>file_name;
		printf("\t\t\t} catch (NumberFormatException e) {\n")>>file_name;
		printf("\t\t\t\te.printStackTrace();\n")>>file_name;
		printf("\t\t\t}\n")>>file_name;
	  }
	  printf("\t\t} catch (JSONException je) {}\n") >> file_name; 
	}
	printf("\t}\n    \n")>>file_name;
	# End Constructor
	

	for ( j = 1; j <= i; ++j ) {
	  if (keys[j] == "key" ) {  
		#printf("\tpublic %sObject (%s %s) {\n", tmp_file_name, field_types[j], field_names[j])>>file_name;
		#printf("\t    set%s(%s);\n", new_field_names[j], field_names[j])>>file_name;
		#printf("\t}\n    \n")>>file_name;
	  }
	}
	
	for ( j = 1; j <= i; ++j ) { 
	  # Comments for setMethod
	  printf("\t\n    /**\n") >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t * Sets the <code>%s</code> field\n", field_names[j]) >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t * @param %s      %s\n", field_names[j], field_types[j]) >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t */\n    \n") >> file_name;
	  # End Comments for setMethod

	  # setMethod
	  printf("\tpublic void set%s(%s %s) {\n", new_field_names[j], field_types[j], field_names[j])>>file_name;
	  printf("\t    this.%s = %s;\n", field_names[j], field_names[j])>>file_name;
	  printf("\t}\n    \n")>>file_name;
	  # End setMethod
	  
	  # Comments for getMethod
	  printf("\t\n    /**\n") >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t * Gets the <code>%s</code> field\n", field_names[j]) >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t * @returns %s\n", field_names[j]) >> file_name;
	  printf("\t *\n") >> file_name;
	  printf("\t */\n    \n") >> file_name;
	  # End Comments for getMethod

	  # getMethod
	  printf("\tpublic %s get%s() {\n", field_types[j], new_field_names[j])>>file_name;
	  printf("\t    return %s;\n", field_names[j])>>file_name;
	  printf("\t}\n\n")>>file_name;
	  # End getMethod
	  
	}
	# Comments for equalsMethod
	printf("\t\n    /**\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * Tests if this object equals <code>obj</code>\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * @returns true if equals\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End Comments for equalsMethod

	# equals Method
        printf("\tpublic boolean equals(Object obj) {\n")>>file_name;
        printf("\t    %sObject other = (%sObject)obj;\n", tmp_file_name, tmp_file_name)>>file_name;
        printf("\t    DebugHandler.finest(\"This: \" + this);\n", tmp_file_name)>>file_name;
        printf("\t    DebugHandler.finest(\"Other: \" + other);\n", tmp_file_name)>>file_name;
	printf("\t    return\n")>>file_name;
	for ( j = 1; j <= i; ++j ) { 
	    if (field_types[j]  == "int" || field_types[j] == "double" || field_types[j] == "float" ) { 
		if ( j == i )
		    printf("\t        %s == other.get%s();\n", field_names[j], new_field_names[j])>>file_name;
		else
		    printf("\t        %s == other.get%s() &&\n", field_names[j], new_field_names[j])>>file_name;
	    } else if ( field_types[j] == "String" ) {
		if ( j == i )
		    printf("\t        Util.trim(%s).equals(Util.trim(other.get%s()));\n", field_names[j], new_field_names[j])>>file_name;
		else
		    printf("\t   Util.trim(%s).equals(Util.trim(other.get%s())) &&\n", field_names[j], new_field_names[j])>>file_name;
	    }
	    else if ( field_types[j] == "Date" ) {
		if ( j == i )
		    printf("\t   %s.equals(other.get%s());\n", field_names[j], new_field_names[j])>>file_name;
		else
		    printf("\t   %s.equals(other.get%s()) &&\n", field_names[j], new_field_names[j])>>file_name;
	    }
	}
	printf("\t}\n")>>file_name;
	# End equals Method

	# Comments for clone Method
	printf("\t\n    /**\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * Clones this object\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t * @returns the clone of this object\n") >> file_name;
	printf("\t *\n") >> file_name;
	printf("\t */\n    \n") >> file_name;
	# End Comments for clone Method

	# Clone Method
        printf("\tpublic Object clone() {\n") >>file_name;
        printf("\t    Object theClone = null;\n") >>file_name;
        printf("\t    try {\n") >>file_name;
        printf("\t   theClone = super.clone();\n")>>file_name;
        printf("\t    } catch (CloneNotSupportedException ce) {\n")>>file_name;
        printf("\t   DebugHandler.severe(\"Cannot clone \" + this);\n")>>file_name;
        printf("\t    }\n")>>file_name;
        printf("\t    return theClone;\n")>>file_name;
        printf("\t}\n")>>file_name;

	printf("}\n")>>file_name; 
	close(filename);
  }

  function write_impl_file() {
	printf("package %s.busimpl;\n\n", proj_name)>impl_file_name;
	printf("import java.lang.*;\n") >>impl_file_name;
	printf("import java.util.*;\n") >>impl_file_name;
	printf("import core.util.Constants;\n") >>impl_file_name;
	printf("import core.util.DebugHandler;\n") >>impl_file_name;
	printf("import core.db.DBUtil;\n") >>impl_file_name;
	printf("import core.util.AppException;\n") >>impl_file_name;
	printf("import core.util.Util;\n") >>impl_file_name;
	printf("import %s.busobj.%sObject;\n", proj_name, tmp_file_name) >>impl_file_name;
	printf("import %s.businterface.%sInterface;\n", proj_name, tmp_file_name) >>impl_file_name;
	printf("import app.util.AppConstants;\n") >>impl_file_name;

	# Initial Comments for the Class
	printf("\n/**\n") >> impl_file_name;
	printf(" * The implementation of the %sInterface\n", tmp_file_name) >> impl_file_name;
	printf(" * @version %s\n", version) >> impl_file_name;
	printf(" * @author %s\n", logname) >> impl_file_name;
	printf(" * @since %s\n", since) >> impl_file_name;
	printf(" */\n\n") >> impl_file_name;
	# End - Initial Comments for the Class

	# get Method
	printf("public class %sImpl implements %sInterface  {\n", tmp_file_name, tmp_file_name)>>impl_file_name;
	printf("\tprivate String %s = \"%sInterface.getAll%s\";\n", toupper(tmp_file_name), tmp_file_name, tmp_file_name)>>impl_file_name;
	# Start get Method Comments
	printf("\t\n    /**\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * Implementation that returns the %sObject given a %sObject filled with values that will be used for query from the underlying datasource.\n", tmp_file_name, tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @param %s_obj\t%sObject\n", tolower(tmp_file_name), tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @return      Returns the ArrayList of %sObjects\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t */\n    \n") >> impl_file_name;
	# End get Method Comments
	if (last_char == "s") {
	    printf("\tpublic ArrayList<%sObject> get%s(%sObject %s_obj) throws AppException{\n", tmp_file_name, tmp_file_name, tmp_file_name, tolower(tmp_file_name)) >> impl_file_name;
	    printf("\t\t%sObject[] %sArr = getAll%s();\n",tmp_file_name, tmp, tmp_file_name) >> impl_file_name;
	} else {
	    printf("\tpublic ArrayList<%sObject> get%ss(%sObject %s_obj) throws AppException{\n", tmp_file_name, tmp_file_name, tmp_file_name, tolower(tmp_file_name)) >> impl_file_name;
	    printf("\t\t%sObject[] %sArr = getAll%ss();\n",tmp_file_name, tmp, tmp_file_name) >> impl_file_name;
	}
	printf("\t\t@SuppressWarnings(\"unchecked\")\n") >> impl_file_name;
	printf("\t\tArrayList<%sObject> v = (ArrayList<%sObject>)DBUtil.list(%s_obj,%s_obj);\n", tmp_file_name, tmp_file_name, tolower(tmp_file_name), tolower(tmp_file_name)) >> impl_file_name;
	#printf("\t\tArrayList<%sObject> v = new ArrayList<%sObject>();\n",tmp_file_name, tmp_file_name) >> impl_file_name;
	#printf("\tif ( %sArr == null )\n", tmp) >> impl_file_name;
	#printf("\t\treturn null;\n") >> impl_file_name;
	#printf("\tfor ( int i = 0; i < %sArr.length; i++ ) {\n", tmp) >> impl_file_name;
	#printf("\t\tif ( %sArr[i] != null ) {\n", tmp) >> impl_file_name;
	#printf("\t\t\tif ( %s_obj.get%s() == Constants.GET_ALL ) {\n", tolower(tmp_file_name), new_field_names[1]) >> impl_file_name;
	#printf("\t\t\t\tv.add((%sObject)%sArr[i].clone());\n", tmp_file_name, tmp)>>impl_file_name;
	#printf("\t\t\t} else {\n") >> impl_file_name;
	#printf("\t\t\t\tif ( ")>>impl_file_name; 
	#for ( j = 1; j <= i; j++ ) {
	#   if ( field_types[j] != "String" && field_types[j]  != "Date" ) {
	#	printf("(%s_obj.get%s() != 0 && %s_obj.get%s() == %sArr[i].get%s())\n", tolower(tmp_file_name), new_field_names[j], tolower(tmp_file_name), new_field_names[j], tmp, new_field_names[j])>>impl_file_name; 
	#    } 
	#    else {
	#	if ( filed_typed[j]  == "String" ) {
	#	    printf("(!Util.trim(%s_obj.get%s()).equals(Constants.SPACE) && %s_obj.get%s().equals(%sArr[i].get%s()))\n", tolower(tmp_file_name), new_field_names[j], tolower(tmp_file_name), new_field_names[j], tmp, new_field_names[j])>>impl_file_name;
	#	} else {
	#	    printf("(%s_obj.get%s() != null && %s_obj.get%s().equals(%sArr[i].get%s()))\n", tolower(tmp_file_name), new_field_names[j], tolower(tmp_file_name), new_field_names[j], tmp, new_field_names[j])>>impl_file_name;
	#	}
	#    }
	#    if ( j != i ) {
	#	printf(" || ")>>impl_file_name;
	#    }
	#}
	#printf(") {\n")>>impl_file_name;
	#printf("\t\t\t\t\tv.add((%sObject)%sArr[i].clone());\n", tmp_file_name, tmp)>>impl_file_name;
	#printf("\t\t\t\t}\n")>> impl_file_name;
	#printf("\t\t\t}\n")>> impl_file_name;
	#printf("\t\t}\n")>> impl_file_name;
	#printf("\t}\n")>> impl_file_name;
	printf("\t\tDebugHandler.finest(\"v: \" + v);\n",tmp_file_name, tolower(tmp_file_name), tmp_file_name) >> impl_file_name;
	printf("\t\treturn v;\n",tmp_file_name, tolower(tmp_file_name), tmp_file_name) >> impl_file_name;
	printf("\t}\n",tmp_file_name, tolower(tmp_file_name), tmp_file_name) >> impl_file_name;

	for ( j = 1; j <= i; ++j ) {
	  if (keys[j]  == "key" ) {  
		# Start get Method Comments
		printf("\t\n    /**\n") >> impl_file_name;
		printf("\t *\n") >> impl_file_name;
		printf("\t * Implementation of the method that returns the %sObject from the underlying datasource.\n", tmp_file_name) >> impl_file_name;
		printf("\t * given %s.\n", field_names[j]) >> impl_file_name;
		printf("\t *\n") >> impl_file_name;
		printf("\t * @param %s     %s\n", field_names[j], field_types[j]) >> impl_file_name;
		printf("\t *\n") >> impl_file_name;
		printf("\t * @return      Returns the %sObject\n", tmp_file_name) >> impl_file_name;
		printf("\t *\n") >> impl_file_name;
		printf("\t * @throws AppException if the operation fails\n") >> impl_file_name;
		printf("\t *\n") >> impl_file_name;
		printf("\t */\n    \n") >> impl_file_name;
		# End get Method Comments

		table_length=length(tmp_file_name);
		last_char=substr(tmp_file_name, table_length, 1);
		if (last_char == "s") {
			newmethod=substr(tmp_file_name,1,table_length - 1)
			printf("\tpublic %sObject get%s(%s %s) throws AppException{\n", tmp_file_name, newmethod, field_types[j], field_names[j]) >> impl_file_name;
			printf("\t\t%sObject[] %sArr = getAll%s();\n",tmp_file_name, tmp, tmp_file_name) >> impl_file_name;
		} else {
			printf("\tpublic %sObject get%s(%s %s) throws AppException{\n",tmp_file_name, tmp_file_name, field_types[j], field_names[j]) >> impl_file_name;
			printf("\t\t%sObject[] %sArr = getAll%ss();\n",tmp_file_name, tmp, tmp_file_name) >> impl_file_name;
		}
		printf("\t\tif ( %sArr == null )\n", tmp) >> impl_file_name;
		printf("\t\t\treturn null;\n") >> impl_file_name;
		printf("\t\tfor ( int i = 0; i < %sArr.length; i++ ) {\n", tmp) >> impl_file_name;
		printf("\t\t\tif ( %sArr[i] == null ) { // Try database and add to cache if found.\n", tmp) >> impl_file_name;
		printf("\t\t\t\t%sObject %sObj = new %sObject();\n", tmp_file_name, tolower(tmp_file_name), tmp_file_name) >> impl_file_name;
		printf("\t\t\t\t%sObj.set%s(%s);\n", tolower(tmp_file_name), new_field_names[j], field_names[j]) >> impl_file_name;
	    printf("\t\t\t\t@SuppressWarnings(\"unchecked\")\n") >> impl_file_name;
		printf("\t\t\t\tArrayList<%sObject> v = (ArrayList)DBUtil.fetch(%sObj);\n", tmp_file_name, tolower(tmp_file_name))>> impl_file_name;
		printf("\t\t\t\tif ( v == null || v.size() == 0 )\n")>> impl_file_name;
		printf("\t\t\t\t\treturn null;\n")>> impl_file_name;
		printf("\t\t\t\telse {\n")>> impl_file_name;
		printf("\t\t\t\t\t%sArr[i] = (%sObject)%sObj.clone();\n", tmp, tmp_file_name, tolower(tmp_file_name) )>> impl_file_name;
		printf("\t\t\t\t\tUtil.putInCache(%s, %sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
		printf("\t\t\t\t}\n")>> impl_file_name;
		printf("\t\t\t}\n")>> impl_file_name;
		printf("\t\t\tif ( %sArr[i].get%s() == %s ) {\n", tmp, new_field_names[j], field_names[j])>> impl_file_name;
		printf("\t\t\t\tDebugHandler.debug(\"Returning \" + %sArr[i]);\n",tmp )>> impl_file_name;
		printf("\t\t\t\treturn (%sObject)%sArr[i].clone();\n",tmp_file_name, tmp)>> impl_file_name;
		printf("\t\t\t}\n")>> impl_file_name;
		printf("\t\t}\n")>> impl_file_name;
		printf("\t\treturn null;\n")>> impl_file_name;
		printf("\t}\n    \n")>> impl_file_name;
	  }
	}
	# End get Method

	# Start getAll Method Comments
	printf("\t\n    /**\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * Implementation that returns all the <code>%sObjects</code> from the underlying datasource.\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @return      Returns an Array of <code>%sObject</code>\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @throws AppException if the operation fails\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t */\n    \n") >> impl_file_name;
	# End getAll Method Comments

	# getAll Method
	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
		printf("\tpublic %sObject[] getAll%s() throws AppException{\n",tmp_file_name, tmp_file_name) >> impl_file_name;
	} else {
		printf("\tpublic %sObject[] getAll%ss() throws AppException{\n",tmp_file_name, tmp_file_name) >> impl_file_name;
	}

	printf("\t\t%sObject %s = new %sObject();\n", tmp_file_name, tmp, tmp_file_name) >> impl_file_name;
	printf("\t\t%sObject[] %sArr = (%sObject[])Util.getAppCache().get(%s);\n", tmp_file_name, tmp, tmp_file_name, toupper(tmp_file_name)) >> impl_file_name;
	printf("\t\tif ( %sArr == null ) {\n", tmp) >> impl_file_name;
	printf("\t\t\tDebugHandler.info(\"Getting %s from database\");\n", tolower(tmp_file_name)) >> impl_file_name;
	printf("\t\t\t@SuppressWarnings(\"unchecked\")\n") >> impl_file_name;
	printf("\t\t\tArrayList<%sObject> v = (ArrayList)DBUtil.list(%s);\n", tmp_file_name, tmp)>> impl_file_name; 
	printf("\t\t\tDebugHandler.finest(\":v: \" +  v);\n")>> impl_file_name;
	printf("\t\t\tif ( v == null )\n")>> impl_file_name;
	printf("\t\t\t\treturn null;\n")>> impl_file_name;
	printf("\t\t\t%sArr = new %sObject[v.size()];\n", tmp, tmp_file_name)>> impl_file_name;
	printf("\t\t\tfor ( int idx = 0; idx < v.size(); idx++ ) {\n")>> impl_file_name;
	printf("\t\t\t\t%sArr[idx] = v.get(idx);\n", tmp)>> impl_file_name;
	printf("\t\t\t}\n")>> impl_file_name;
	printf("\t\t\tUtil.putInCache(%s, %sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\treturn %sArr;\n", tmp)>> impl_file_name;
	printf("\t}\n    \n")>> impl_file_name;
	# End getAll Method

	# Start add Method Comments
	printf("\t\n    /**\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * Implementation to add the <code>%sObject</code> to the underlying datasource.\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @throws AppException if the operation fails\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t */\n    \n") >> impl_file_name;
	# End add Method Comments

	# add Method
	printf("\tpublic Integer add%s(%sObject %s) throws AppException{\n",tmp_file_name, tmp_file_name, tmp) >> impl_file_name;
	printf("\t\tif ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {\n")>> impl_file_name;
	printf("\t\t\tlong l = DBUtil.getNextId(\"%s_seq\");\n", table_name)>> impl_file_name;
	printf("\t\t\t%s.set%s((int)l);\n", tmp, new_field_names[1]) >> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\tInteger i = (Integer)DBUtil.insert(%s);\n", tmp)>> impl_file_name;
	printf("\t\tDebugHandler.fine(\"i: \" +  i);\n")>> impl_file_name;
	printf("\t\t// Do for Non Oracle where there is auto increment\n")>> impl_file_name;
	printf("\t\tif ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {\n")>> impl_file_name;
	printf("\t\t\t%s.set%s(i.intValue());\n", tmp, new_field_names[1]) >> impl_file_name;
	printf("\t\t\tDebugHandler.fine(%s);\n", tmp)>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\t%sObject buf = new %sObject();\n", tmp_file_name, tmp_file_name)>> impl_file_name;
	printf("\t\tbuf.set%s(%s.get%s());\n", new_field_names[1], tmp, new_field_names[1])>> impl_file_name;
	printf("\t\t@SuppressWarnings(\"unchecked\")\n") >> impl_file_name;
	printf("\t\tArrayList<%sObject> v = (ArrayList)DBUtil.list(%s, buf);\n", tmp_file_name, tmp)>> impl_file_name;
	printf("\t\t%s = v.get(0);\n", tmp)>> impl_file_name;
	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
	    printf("\t\t%sObject[] %sArr = getAll%s();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	} else {
	    printf("\t\t%sObject[] %sArr = getAll%ss();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	}
	printf("\t\tboolean foundSpace = false;\n")>> impl_file_name;
	printf("\n")>> impl_file_name;

	printf("\t\tfor ( int idx = 0; idx < %sArr.length; idx++ ) {\n", tmp)>> impl_file_name;
	printf("\t\t\tif ( %sArr[idx] == null ) {\n", tmp)>> impl_file_name;
	printf("\t\t\t\t%sArr[idx] = (%sObject)%s.clone();\n", tmp, tmp_file_name, tmp)>> impl_file_name;
	printf("\t\t\t\tfoundSpace = true;\n")>> impl_file_name;
	printf("\t\t\t\tbreak;\n")>> impl_file_name;
	printf("\t\t\t}\n")>> impl_file_name;
	printf("\t\t}\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	printf("\t\tif ( foundSpace == false ) {\n")>> impl_file_name;
	printf("\t\t\t%sObject[] new%sArr = new %sObject[%sArr.length + 1];\n", tmp_file_name, tmp, tmp_file_name, tmp)>> impl_file_name;
	printf("\t\t\tint idx = 0;\n")>> impl_file_name;
	printf("\t\t\tfor ( idx = 0; idx < %sArr.length; idx++ ) {\n", tmp)>> impl_file_name;
	printf("\t\t\t\tnew%sArr[idx] = (%sObject)%sArr[idx].clone();\n", tmp, tmp_file_name, tmp)>> impl_file_name;
	printf("\t\t\t}\n")>> impl_file_name;
	printf("\t\t\tnew%sArr[idx] = (%sObject)%s.clone();\n", tmp, tmp_file_name, tmp)>> impl_file_name;
	printf("\t\t\tUtil.putInCache(%s, new%sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
	printf("\t\t} else {\n")>> impl_file_name;
	printf("\t\t\tUtil.putInCache(%s, %sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\treturn i;\n", tmp)>> impl_file_name;
	printf("\t}\n\t\n")>> impl_file_name;
	# End add Method

	# Start update Method Comments
	printf("\t\n    /**\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * Implementation to update the <code>%sObject</code> in the underlying datasource.\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @throws AppException if the operation fails\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t */\n    \n") >> impl_file_name;
	# End update Method Comments

	# updateMethod
	printf("\tpublic Integer update%s(%sObject %s) throws AppException{\n",tmp_file_name, tmp_file_name, tmp) >> impl_file_name;
	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
	    newmethod=substr(tmp_file_name,1,table_length - 1);
	    printf("\t\t%sObject new%sObject = get%s(%s.get%s()); // This call will make sure cache/db are in sync\n", tmp_file_name, tmp_file_name, newmethod, tmp, new_field_names[1])>> impl_file_name;
	} else {
	    printf("\t\t%sObject new%sObject = get%s(%s.get%s()); // This call will make sure cache/db are in sync\n", tmp_file_name, tmp_file_name, tmp_file_name, tmp, new_field_names[1])>> impl_file_name;
	}
	printf("\t\tInteger i = (Integer)DBUtil.update(%s);\n", tmp)>> impl_file_name;
	printf("\t\tDebugHandler.fine(\"i: \" +  i);\n")>> impl_file_name;
	if (last_char == "s") {
	    printf("\t\t%sObject[] %sArr = getAll%s();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	} else {
	    printf("\t\t%sObject[] %sArr = getAll%ss();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	}
	printf("\t\tif ( %sArr == null )\n", tmp) >> impl_file_name;
	printf("\t\t\treturn null;\n") >> impl_file_name;
	printf("\t\tfor ( int idx = 0; idx < %sArr.length; idx++ ) {\n", tmp) >> impl_file_name;
	printf("\t\t\tif ( %sArr[idx] != null ) {\n", tmp) >> impl_file_name;
	printf("\t\t\t\tif ( %sArr[idx].get%s() == %s.get%s() ) {\n", tmp, new_field_names[1], tmp, new_field_names[1]) >> impl_file_name;
	printf("\t\t\t\t\tDebugHandler.debug(\"Found %s \" + %s.get%s());\n", tmp_file_name, tmp, new_field_names[1])>> impl_file_name;
	printf("\t\t\t\t\t%sArr[idx] = (%sObject)%s.clone();\n", tmp, tmp_file_name, tmp) >> impl_file_name;
	printf("\t\t\t\t\tUtil.putInCache(%s, %sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
	printf("\t\t\t\t}\n")>> impl_file_name;
	printf("\t\t\t}\n")>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\treturn i;\n", tmp)>> impl_file_name;
	printf("\t}\n    \n")>> impl_file_name;
	# End updateMethod
	
	# Start delete Method Comments
	printf("\t\n    /**\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * Implementation to delete the <code>%sObject</code> in the underlying datasource.\n", tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t * @throws AppException if the operation fails\n") >> impl_file_name;
	printf("\t *\n") >> impl_file_name;
	printf("\t */\n    \n") >> impl_file_name;
	# End delete Method Comments

	# delete Method
	printf("\tpublic Integer delete%s(%sObject %s) throws AppException{\n",tmp_file_name, tmp_file_name, tmp) >> impl_file_name;
	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
	    newmethod=substr(tmp_file_name,1,table_length - 1);
	    printf("\t%sObject new%sObject = get%s(%s.get%s()); // This call will make sure cache/db are in sync\n", tmp_file_name, tmp_file_name, newmethod, tmp, new_field_names[1])>> impl_file_name;
	} else {
	    printf("\t%sObject new%sObject = get%s(%s.get%s()); // This call will make sure cache/db are in sync\n", tmp_file_name, tmp_file_name, tmp_file_name, tmp, new_field_names[1])>> impl_file_name;
	}

	if (last_char == "s") {
	    printf("\t%sObject[] %sArr = getAll%s();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	} else {
	    printf("\t%sObject[] %sArr = getAll%ss();\n", tmp_file_name, tmp, tmp_file_name)>> impl_file_name;
	}
	printf("\tInteger i = new Integer(0);\n")>> impl_file_name;
	printf("\tif ( %s != null ) {\n", tmp)>> impl_file_name;
	printf("\t\ti = (Integer)DBUtil.delete(%s);\n", tmp)>> impl_file_name;
	printf("\t\tDebugHandler.fine(\"i: \" +  i);\n")>> impl_file_name;
	printf("\t\tboolean found = false;\n")>> impl_file_name;
	printf("\t\tif ( %sArr != null ) {\n", tmp)>> impl_file_name;
	printf("\t\t\tfor (int idx = 0; idx < %sArr.length; idx++ ) {\n", tmp)>> impl_file_name;
	printf("\t\t\t\tif ( %sArr[idx] != null && %sArr[idx].get%s() == %s.get%s() ) {\n", tmp, tmp, new_field_names[1], tmp, new_field_names[1])>> impl_file_name;
	printf("\t\t\t\t\tfound = true;\n")>> impl_file_name;
	printf("\t\t\t\t}\n")>> impl_file_name;
	printf("\t\t\t\tif ( found ) {\n")>> impl_file_name;
	printf("\t\t\t\t\tif ( idx != (%sArr.length - 1) )\n",tmp)>> impl_file_name;
	printf("\t\t\t\t\t\t%sArr[idx] = %sArr[idx + 1]; // Move the array\n", tmp, tmp)>> impl_file_name;
	printf("\t\t\t\t\telse\n",tmp)>> impl_file_name;
	printf("\t\t\t\t\t\t%sArr[idx] = null;\n",tmp)>> impl_file_name;
	printf("\t\t\t\t}\n")>> impl_file_name;
	printf("\t\t\t\tif ( %sArr[idx] == null )\n", tmp)>> impl_file_name;
	printf("\t\t\t\t\tbreak;\n")>> impl_file_name;
	printf("\t\t\t}\n")>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\tif ( found )\n")>> impl_file_name;
	printf("\t\t\tUtil.putInCache(%s, %sArr);\n", toupper(tmp_file_name), tmp)>> impl_file_name;
	printf("\t\t}\n")>> impl_file_name;
	printf("\t\treturn i;\n", tmp)>> impl_file_name;
	printf("\t}\n")>> impl_file_name;
	# End delete Method
	printf("}\n")>>impl_file_name; 
	close(impl_file_name);
  } 

  function write_if_file() {
	printf("package %s.businterface;\n\n", proj_name)>if_file_name;
	printf("import java.lang.*;\n") >>if_file_name;
	printf("import java.util.*;\n") >>if_file_name;
	printf("import core.util.AppException;\n") >>if_file_name;
	printf("import %s.busobj.%sObject;\n", proj_name, tmp_file_name) >>if_file_name;

	# Initial Comments for the Class
	printf("\n/**\n") >> if_file_name;
	printf(" * The interface which encapsulates the access of database tables\n" ) >> if_file_name;
	printf(" * in the database\n") >> if_file_name;
	printf(" * @version %s\n", version) >> if_file_name;
	printf(" * @author %s\n", logname) >> if_file_name;
	printf(" * @since %s\n", since) >> if_file_name;
	printf(" */\n\n") >> if_file_name;
	# End - Initial Comments for the Class

	printf("public interface %sInterface {\n", tmp_file_name)>>if_file_name;
	# Start get Method Comments
	printf("\t\n    /**\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * Interface that returns the %sObject given a %sObject filled with values that will be used for query from the underlying datasource.\n", tmp_file_name, tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @param %s_obj\t%sObject\n", tolower(tmp_file_name), tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @return      Returns the ArrayList of %sObjects\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t */\n    \n") >> if_file_name;
	# End get Method Comments
	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
	    printf("\tpublic ArrayList<%sObject> get%s(%sObject %s_obj) throws AppException;\n", tmp_file_name, tmp_file_name, tmp_file_name, tolower(tmp_file_name)) >> if_file_name;
	} else {
	    printf("\tpublic ArrayList<%sObject> get%ss(%sObject %s_obj) throws AppException;\n", tmp_file_name, tmp_file_name, tmp_file_name, tolower(tmp_file_name)) >> if_file_name;
	}

	for ( j = 1; j <= i; ++j ) {
	  if (keys[j] == "key" ) {  
		# Start get Method Comments
		printf("\t\n    /**\n") >> if_file_name;
		printf("\t *\n") >> if_file_name;
		printf("\t * Interface that returns the %sObject given %s from the underlying datasource.\n", tmp_file_name, field_names[j]) >> if_file_name;
		printf("\t *\n") >> if_file_name;
		printf("\t * @param %s     %s\n", field_names[j], field_types[j]) >> if_file_name;
		printf("\t *\n") >> if_file_name;
		printf("\t * @return      Returns the %sObject\n", tmp_file_name) >> if_file_name;
		printf("\t *\n") >> if_file_name;
		printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
		printf("\t *\n") >> if_file_name;
		printf("\t */\n    \n") >> if_file_name;
		# End get Method Comments
		table_length=length(tmp_file_name);
		last_char=substr(tmp_file_name, table_length, 1);
		if (last_char == "s") {
			newmethod=substr(tmp_file_name,1,table_length - 1);
			printf("\tpublic %sObject get%s(%s %s) throws AppException;\n",tmp_file_name, newmethod, field_types[j], field_names[j]) >> if_file_name;
		} else {
			printf("\tpublic %sObject get%s(%s %s) throws AppException;\n",tmp_file_name, tmp_file_name, field_types[j], field_names[j]) >> if_file_name;
		}
	  }
	}

	# Start getAll Method Comments
	printf("\t\n    /**\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * Interface that returns all the <code>%sObject</code> from the underlying datasource.\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @return      Returns an Array of <code>%sObject</code>\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t */\n    \n") >> if_file_name;
	# End getAll Method Comments

	table_length=length(tmp_file_name);
	last_char=substr(tmp_file_name, table_length, 1);
	if (last_char == "s") {
		printf("\tpublic %sObject[] getAll%s() throws AppException;\n",tmp_file_name, tmp_file_name) >> if_file_name;
	} else {
		printf("\tpublic %sObject[] getAll%ss() throws AppException;\n", tmp_file_name, tmp_file_name) >> if_file_name;
	}


	# Start add Method Comments
	printf("\t\n    /**\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * Interface to add the <code>%sObject</code> to the underlying datasource.\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t */\n    \n") >> if_file_name;
	# End add Method Comments

	printf("\tpublic Integer add%s(%sObject %s) throws AppException;\n",tmp_file_name, tmp_file_name, tmp) >> if_file_name;
	
	# Start update Method Comments
	printf("\t\n    /**\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * Interface to update the <code>%sObject</code> in the underlying datasource.\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t */\n    \n") >> if_file_name;
	# End update Method Comments

	printf("\tpublic Integer update%s(%sObject %s) throws AppException;\n",tmp_file_name, tmp_file_name, tmp) >> if_file_name;

	# Start delete Method Comments
	printf("\t\n    /**\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * Interface to delete the <code>%sObject</code> in the underlying datasource.\n", tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @param %s     %sObject\n", tmp, tmp_file_name) >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t * @throws AppException if the underlying operation fails\n") >> if_file_name;
	printf("\t *\n") >> if_file_name;
	printf("\t */\n    \n") >> if_file_name;
	# End delete Method Comments
	printf("\tpublic Integer delete%s(%sObject %s) throws AppException;\n",tmp_file_name, tmp_file_name, tmp) >> if_file_name;
	printf("}\n")>>if_file_name; 
	close(if_file_name);
  }
  NF == 1 { 
	if ( table_name != "" ) {
	  write_file();
	  write_persistent_file();
	  write_properties_file();
	  write_if_file();
	  write_impl_file();
	  write_rest_file();
    	}
	write_sql_file();
	i = 0;
	table_name=$1; 
	all_tables[k++] = table_name;
	tmp_file_name = table_name;
	gsub("_", "", tmp_file_name);
	file_name=tmp_file_name "Object.java"
	if_file_name=tmp_file_name "Interface.java"
	impl_file_name=tmp_file_name "Impl.java"
	rest_file_name=tmp_file_name "Rest.java"
  }
  NF == 2 || NF == 3 || NF == 4{ 
	i++;
	field_names[i] = tolower($1);
	lower_two = tolower($2)
	if ( lower_two == "integer" || lower_two == "int" )
	  field_types[i] = "int";
	else if ( lower_two ~ /varchar/ || lower_two == "long" || lower_two == "clob" ) {
	  field_types[i] = "String";
	}
	else if (lower_two == "datetime" || lower_two == "timestamp" ) {
	  field_types[i] = "Date";
	}
	else if (lower_two == "decimal" || lower_two == "Decimal" || lower_two == "double" ) {
	  field_types[i] = "double";
	}
	else if (lower_two == "float" ) {
	  field_types[i] = "float";
	}
	orig_field_types[i] = lower_two;
	if ( $3 == "key" )
	  keys[i] = $3;
	else if ( $3 == "references" )
	  keys[i] = $4;
	else if ( $3 == "not" )
	  keys[i] = "not";
	else
	  keys[i] = "";
  }
  END { 
	write_file();
	write_persistent_file();
	write_properties_file();
	write_if_file();
	write_impl_file();
	write_rest_file();
	write_sql_file();
  }' $SCHEMA_FILE
  
BASEDIR=`dirname $0`/..
for f in `ls *.java`; do
	echo Adding header to $f
	cat > /tmp/a << EOF
/*
 * $f
 *
 * ${PROJECT_TITLE} Project
 *
 * Author: Govind Thirumalai
 */

EOF
	cat $f >> /tmp/a
	mv /tmp/a $f
done
######################################################################################################
# Begin Edits Makefile for busobj
CreateMakefileEntry busobj '*Object.java' ${JAVA_DEV_ROOT}/src/java/app/busobj UsersObject.java
######################################################################################################
# Begin Edits Makefile for appdb
CreateMakefileEntry appdb 'Persistent*.java' ${JAVA_DEV_ROOT}/src/java/app/appdb PersistentUsers.java
######################################################################################################
# Begin Edits Makefile for businterface
CreateMakefileEntry businterface '*Interface.java' ${JAVA_DEV_ROOT}/src/java/app/businterface UsersInterface.java
######################################################################################################
# Begin Edits Makefile for busimpl
CreateMakefileEntry busimpl '*Impl.java' ${JAVA_DEV_ROOT}/src/java/app/busimpl UsersImpl.java
######################################################################################################

echo "commit;">> create_tables.sql;
echo "exit;">> create_tables.sql;
## Sed the values got from table_names in the import/export scripts

tables="`cat /tmp/tables`"
tables=" role users acl `echo $tables`"
rtables="`cat /tmp/rtables`"
rtables=" `echo $rtables` acl users role"
sed -e "s/TABLES=.*/TABLES=\"\\$tables\"/g" table_export.sh > /tmp/x
if [ $? -eq 0 ]; then
	mv /tmp/x table_export.sh
fi
sed -e "s/TABLES=.*/TABLES=\"\\${tables}\"/g" create_sequence.sh > /tmp/x
if [ $? -eq 0 ]; then
	mv /tmp/x create_sequence.sh
fi
sed -e "s/TABLES=.*/TABLES=\"\\${rtables}\"/g" table_import.sh > /tmp/x

if [ $? -eq 0 ]; then
	mv /tmp/x table_import.sh
fi
rm -f /tmp/tables /tmp/rtables
