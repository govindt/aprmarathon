#!/bin/sh


SCHEMA_FILE=${1:-/tmp/schema}
NAWK=/usr/bin/awk
PROJECT_NAME=app
PROJECT_TITLE="APR Marathon Registration App"

if [ ! -f ${SCHEMA_FILE} ]; then
	printf "${SCHEMA_FILE} does not exist\n"
	exit -1;
fi
APP_CONSTANTS=AppConstants;
APP_CONSTANTS_JAVA=${APP_CONSTANTS}.java
APP=App;
APP_JAVA=App.java
APP_PROPERTIES_FILE=app.properties
MAKEFILE=Makefile

cp /dev/null ${APP_CONSTANTS_JAVA}
cp /dev/null ${APP_JAVA}
cp /dev/null ${APP_PROPERTIES_FILE}

######################################################################################################
CreateMakefileEntry() {
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

${NAWK} -v app_properties_file=${APP_PROPERTIES_FILE} -v app_constants=${APP_CONSTANTS} -v app_constants_file=${APP_CONSTANTS_JAVA} -v app_file=${APP_JAVA} -v logname=${LOGNAME} -v version=1.0 -v since=1.0 -v proj_name="${PROJECT_NAME}" -v title="${PROJECT_TITLE}" 'BEGIN { 
	table_name = ""; i = 0; 
	upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	lower="abcdefghijklmnopqrstuvwxyz";
    }

    function init() {
	jsp_constant = "MANAGE_" toupper(tmp_file_name);
	jsp_constant_jsp_str = jsp_constant "_JSP_STR";
	jsp_constant_str = jsp_constant "_STR";
	jsp_filename = "Manage" tmp_file_name ".jsp";
    }

    function write_app_bean_file() {
	app_bean_file = tmp_file_name "Bean.java";
	printf("/*\n")>app_bean_file;
	printf(" *\n")>>app_bean_file;
	printf(" *\n")>>app_bean_file;
	printf(" * %s Project\n", title)>>app_bean_file;
	printf(" *\n")>>app_bean_file;
	printf(" * Author: Govind Thirumalai\n")>>app_bean_file;
	printf(" */\n")>>app_bean_file;
	printf("package %s.appui;\n", proj_name)>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("import java.util.*;\n")>>app_bean_file;
	printf("import java.text.SimpleDateFormat;\n")>>app_bean_file;
	printf("import javax.servlet.http.*;\n")>>app_bean_file;
	printf("import core.ui.*;\n")>>app_bean_file;
	printf("import core.appui.*;\n")>>app_bean_file;
	printf("import core.util.*;\n")>>app_bean_file;
	printf("import %s.util.*;\n", proj_name)>>app_bean_file;
	printf("import %s.busobj.*;\n", proj_name)>>app_bean_file;
	printf("import %s.businterface.*;\n", proj_name)>>app_bean_file;
	printf("import %s.busimpl.*;\n", proj_name)>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("import java.io.InputStream;\n")>>app_bean_file;
	printf("import java.io.IOException;\n")>>app_bean_file;
	printf("import java.io.FileNotFoundException;\n")>>app_bean_file;
	printf("import java.io.ByteArrayInputStream;\n")>>app_bean_file;
	printf("import java.io.FileInputStream;\n")>>app_bean_file;
	printf("import java.io.FileOutputStream;\n")>>app_bean_file;
	printf("import java.io.DataInputStream;\n")>>app_bean_file;
	printf("import java.io.File;\n")>>app_bean_file;
	printf("import org.apache.poi.poifs.filesystem.*;\n")>>app_bean_file;
	printf("import org.apache.poi.hssf.usermodel.*;\n")>>app_bean_file;
	printf("import org.apache.poi.hssf.util.HSSFColor;\n")>>app_bean_file;
	printf("\n", proj_name)>>app_bean_file;
	printf("public class %sBean implements SpreadSheetInterface {\n", tmp_file_name)>>app_bean_file;
	for ( j = 1; j <= i; ++j ) {
	    if (keys[j]  != "" && keys[j] != "not") {  
		if ( field_types[j] == "int" ) {
		    printf("    public %s %s = 0;\n", field_types[j], new_field_names[j])>>app_bean_file;
		}
	    }
	}
	printf("    %sObject selected%sObj = new %sObject();\n", tmp_file_name, tmp_file_name, tmp_file_name)>>app_bean_file;
	lower_table_name = tmp_file_name;
	FIRSTCHAR = substr(lower_table_name,1,1);
	if (CHAR = index(upper, FIRSTCHAR)) {
	    a[1] = substr(lower, CHAR, 1) substr(lower_table_name, 2);

	}
	lower_table_name=a[1];
	printf("    %sInterface %sIf = new %sImpl();\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("    public %sBean() {}\n", tmp_file_name)>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("    public void getRequestParameters(HttpServletRequest request) throws AppException {\n", tmp_file_name)>>app_bean_file;
	printf("\tHttpSession session = request.getSession();\n", tmp_file_name)>>app_bean_file;
	printf("\tif (session == null)\n", tmp_file_name)>>app_bean_file;
	printf("\t    throw new NullPointerException();\n", tmp_file_name)>>app_bean_file;
	printf("\t@SuppressWarnings(\"unchecked\")\n")>>app_bean_file;
	printf("\tHashtable<String,String> valuepairs =\n", tmp_file_name)>>app_bean_file;
	printf("\t    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);\n", tmp_file_name)>>app_bean_file;
	for ( j = 1; j <= i; ++j ) {
	    if (keys[j]  != "" && keys[j] != "not") {  
		if ( field_types[j] == "int" ) {
	            printf("\ttry {\n", tmp_file_name)>>app_bean_file;
		    printf("\t    %s = Integer.parseInt(valuepairs.get(%s.%s_STR));\n", new_field_names[j], app_constants, toupper(field_names[j]))>>app_bean_file;
	            printf("\t} catch (NumberFormatException nfe) {\n", tmp_file_name)>>app_bean_file;
		    printf("\t    %s = 0;\n", new_field_names[j])>>app_bean_file;
	            printf("\t}\n")>>app_bean_file;
		}
	    }
	}

	printf("\tString saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);\n")>>app_bean_file;
	printf("\tif ( saveProfile == null ||\n")>>app_bean_file;
	printf("\t     Boolean.valueOf(saveProfile).booleanValue() == false ) {\n")>>app_bean_file;
	printf("\t    // This is to display the page\n")>>app_bean_file;
	printf("\t    if ( %s != 0 ) // Display the selected %s\n", new_field_names[1], tolower(tmp_file_name))>>app_bean_file;
	tmp_file_length = length(tmp_file_name);
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    new_method = substr(tmp_file_name, 1, tmp_file_length - 1);
	    printf("\t\tselected%sObj = %sIf.get%s(%s);\n", tmp_file_name, lower_table_name, new_method, new_field_names[1])>>app_bean_file;
	} else {
	    printf("\t\tselected%sObj = %sIf.get%s(%s);\n", tmp_file_name, lower_table_name, tmp_file_name, new_field_names[1])>>app_bean_file;
	}
	printf("\t}\n")>>app_bean_file;
	printf("\telse {\n")>>app_bean_file;
	printf("\t    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);\n")>> app_bean_file;
	printf("\t    if ( inputFileName == null ) {\n")>> app_bean_file;
	printf("\t\tif ( %s != 0 ) {\n", new_field_names[1])>>app_bean_file;
	printf("\t\t    String buf = \"\";\n")>>app_bean_file;
	printf("\t\t    Date date = null;\n")>>app_bean_file;
	printf("\t\t    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);\n")>>app_bean_file;
	for ( j = 1; j <= i; ++j ) {
	    if ( j != 1 ) {
		printf("\t\t    buf = Util.trim(valuepairs.get(%s.%s_STR));\n", app_constants, toupper(field_names[j]) )>> app_bean_file;
		FIRSTCHAR = substr(new_field_names[j],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		    temp = substr(upper, CHAR, 1) substr(new_field_names[j], 2);
		} else {
		    temp = new_field_names[j];
		}
		
		if ( field_types[j] == "int" ) {
		    printf("\t\t    selected%sObj.set%s(Integer.parseInt(buf));\n", tmp_file_name, temp )>> app_bean_file;
		}
		else if ( field_types[j] == "String" ) {
		    printf("\t\t    selected%sObj.set%s(buf);\n", tmp_file_name, temp)>> app_bean_file;
		}
		else if ( field_types[j] == "Date" ) {
		    printf("\t\t    try {\n")>> app_bean_file;
		    printf("\t\t\tdate = dateFormatter.parse(buf);\n")>> app_bean_file;
		    printf("\t\t    } catch (java.text.ParseException pe) {\n")>> app_bean_file;
		    printf("\t\t\tthrow new AppException(\"Parse Exception while parsing \" + buf);\n")>> app_bean_file;
		    printf("\t\t    }\n")>> app_bean_file;
		    printf("\t\t    selected%sObj.set%s(date);\n", tmp_file_name, temp)>> app_bean_file;
		}
		else if ( field_types[j] == "double" ) {
		    printf("\t\t    selected%sObj.set%s(Double.parseDouble(buf));\n", tmp_file_name, temp )>> app_bean_file;
		}
		else if ( field_types[j] == "float" ) {
		    printf("\t\t    selected%sObj.set%s(Float.parseFloat(buf));\n", tmp_file_name, temp )>> app_bean_file;
		}
	    }
	}
	printf("\t\t    DebugHandler.debug(\"Modifying %s Object \" + selected%sObj);\n", tmp_file_name, tmp_file_name )>> app_bean_file;
	printf("\t\t    %sIf.update%s(selected%sObj);\n", lower_table_name, tmp_file_name, tmp_file_name )>> app_bean_file;
	printf("\t\t}\n")>> app_bean_file;
	printf("\t\telse {\n")>> app_bean_file;
	printf("\t\t    String buf = \"\";\n")>>app_bean_file;
	printf("\t\t    Date date = null;\n")>>app_bean_file;
	printf("\t\t    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);\n")>>app_bean_file;
	printf("\t\t    %sObject %sObj = new %sObject();\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;

	for ( j = 1; j <= i; ++j ) {
	    if ( j != 1 ) {
		printf("\t\t    buf = Util.trim(valuepairs.get(%s.%s_STR));\n", app_constants, toupper(field_names[j]) )>> app_bean_file;
		FIRSTCHAR = substr(new_field_names[j],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		    temp = substr(upper, CHAR, 1) substr(new_field_names[j], 2);
		} else {
		    temp = new_field_names[j];
		}
		
		if ( field_types[j] == "int" ) {
		    printf("\t\t    %sObj.set%s(Integer.parseInt(buf));\n", lower_table_name, temp )>> app_bean_file;
		}
		else if ( field_types[j] == "String" ) {
		    printf("\t\t    %sObj.set%s(buf);\n", lower_table_name, temp)>> app_bean_file;
		}
		else if ( field_types[j] == "Date" ) {
		    printf("\t\t    try {\n")>> app_bean_file;
		    printf("\t\t\tdate = dateFormatter.parse(buf);\n")>> app_bean_file;
		    printf("\t\t    } catch (java.text.ParseException pe) {\n")>> app_bean_file;
		    printf("\t\t\tthrow new AppException(\"Parse Exception while parsing \" + buf);\n")>> app_bean_file;
		    printf("\t\t    }\n")>> app_bean_file;
		    printf("\t\t    %sObj.set%s(date);\n", lower_table_name, temp)>> app_bean_file;
		}
		else if ( field_types[j] == "double" ) {
		    printf("\t\t    %sObj.set%s(Double.parseDouble(buf));\n", lower_table_name, temp )>> app_bean_file;
		}
		else if ( field_types[j] == "float" ) {
		    printf("\t\t    %sObj.set%s(Float.parseFloat(buf));\n", lower_table_name, temp )>> app_bean_file;
		}
	    }
	}
	
	printf("\t\t    DebugHandler.debug(\"Adding %s Object \" + %sObj);\n", tmp_file_name, lower_table_name )>> app_bean_file;
	printf("\t\t    %sIf.add%s(%sObj);\n", lower_table_name, tmp_file_name, lower_table_name )>> app_bean_file;
	printf("\t\t}\n")>>app_bean_file;
	printf("\t    }\n")>>app_bean_file;
	printf("\t    else {\n")>>app_bean_file;
	printf("\t\tString temp = System.getProperty(\"java.io.tmpdir\");\n")>> app_bean_file;
	printf("\t\tinputFileName = temp + File.separatorChar + inputFileName;\n")>>app_bean_file;   
 	printf("\t\treadFromFile(inputFileName, null);\n")>>app_bean_file;    
	printf("\t    }\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("    }\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("    public String get%sInfo() throws AppException {\n", tmp_file_name)>>app_bean_file;
	printf("\tTableElement te = new TableElement();\n")>>app_bean_file;
	printf("\tte.setClass(Constants.BODY_TABLE_STYLE);\n")>>app_bean_file;
	printf("\tTableRowElement tr = null;\n")>>app_bean_file;
	printf("\tTableDataElement td = null;\n")>>app_bean_file;
	printf("\tBoldElement be = null;\n")>>app_bean_file;
	printf("\tSelectElement se = null;\n")>>app_bean_file;
	printf("\tInputElement ie = null;\n")>>app_bean_file
	printf("\tTextareaElement txt = null;\n")>>app_bean_file;
	tmp_file_length = length(tmp_file_name);
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    new_method = substr(tmp_file_name, 1, tmp_file_length - 1);
	    printf("\t%sObject %sObj = %sIf.get%s(%s);\n", tmp_file_name, lower_table_name, lower_table_name, new_method, new_field_names[1])>>app_bean_file;
	} else {
	    printf("\t%sObject %sObj = %sIf.get%s(%s);\n", tmp_file_name, lower_table_name, lower_table_name, tmp_file_name, new_field_names[1])>>app_bean_file;
	}
	printf("\n")>>app_bean_file;
	
	printf("\tif ( %sObj == null )\n", lower_table_name)>>app_bean_file;
	printf("\t    %sObj = new %sObject();\n", lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\ttr = new TableRowElement();\n")>>app_bean_file
	printf("\tbe = new BoldElement(%s.CURRENT_%s_LABEL);\n", app_constants, toupper(tmp_file_name))>>app_bean_file;
	printf("\tbe.setId(Constants.BODY_ROW_STYLE);\n")>>app_bean_file;
	printf("\ttd = new TableDataElement(be);\n")>>app_bean_file;
	printf("\ttr.addElement(td);\n")>>app_bean_file;
	printf("\tVector<String> nameVector = new Vector<String>();\n")>>app_bean_file;
	printf("\tVector<Integer> valueVector = new Vector<Integer>();\n")>>app_bean_file;

	for ( j = 1; j <= i; ++j ) {
	    FIRSTCHAR = substr(new_field_names[j],1,1);
	    if (CHAR = index(lower, FIRSTCHAR)) {
		    temp_field_names[j] = substr(upper, CHAR, 1) substr(new_field_names[j], 2);
	    } else {
		temp_field_names[j] = new_field_names[j];
	    }
	}
	
	k = 1;
	for ( j = 1; j <= i; ++j ) {
	    if ( keys[j] == "key" ) {
		if ( field_types[j] == "int" ) {
		    tmp_file_length = length(tmp_file_name);
		    last_char=substr(tmp_file_name, tmp_file_length, 1);
		    if ( last_char == "s" ) {
			printf("\t%sObject[] %sArr = %sIf.getAll%s();\n", tmp_file_name, lower_table_name, lower_table_name, tmp_file_name)>>app_bean_file;
		    } else {
			printf("\t%sObject[] %sArr = %sIf.getAll%ss();\n", tmp_file_name, lower_table_name, lower_table_name, tmp_file_name)>>app_bean_file;
		    }
		    printf("\tnameVector.addElement(%s.NEW_%s);\n", app_constants, toupper(tmp_file_name))>>app_bean_file;
		    printf("\tvalueVector.addElement(new Integer(0));\n")>>app_bean_file;
		    printf("\tfor (int iterator = 0; iterator < %sArr.length; iterator++) {\n", lower_table_name)>>app_bean_file;
		    printf("\t    %sObject %sObject = %sArr[iterator];\n", tmp_file_name, lower_table_name, lower_table_name)>>app_bean_file;
		    printf("\t    if ( %sObject == null )\n", lower_table_name)>>app_bean_file;
		    printf("\t\tbreak;\n", lower_table_name)>>app_bean_file;
		    
		    if ( field_types[j+1] == "int" ) 
			printf("\t    nameVector.addElement(String.valueOf(%sObject.get%s()));\n", lower_table_name, temp_field_names[j+1])>>app_bean_file;
		    else
			printf("\t    nameVector.addElement(%sObject.get%s());\n", lower_table_name, temp_field_names[j+1])>>app_bean_file;
		    printf("\t    valueVector.addElement(new Integer(%sObject.get%s()));\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		    printf("\t}\n")>>app_bean_file;
		    printf("\tse = new SelectElement(%s.%s_STR, nameVector, valueVector, String.valueOf(%s), 0);\n", app_constants, toupper(field_names[j]), new_field_names[j])>>app_bean_file;
		    printf("\tse.setOnChange(UtilBean.JS_SUBMIT_FORM);\n")>>app_bean_file;
		    printf("\ttd = new TableDataElement(se);\n")>>app_bean_file;
		    printf("\ttr.addElement(td);\n")>>app_bean_file;
		    printf("\tte.addElement(tr);\n")>>app_bean_file;
		    printf("\n")>>app_bean_file;
		}
	    }
	    else { # Not Keys
		if ( field_types[j] == "int" ) {
		    ref_table_idx = index(keys[j], "("); 
		    if ( ref_table_idx != 0 ) {
			ref_table = substr(keys[j], 0, ref_table_idx - 1);
			ref_table_id = substr(keys[j], ref_table_idx + 1, (length(keys[j]) - ref_table_idx - 1));
			ref_col_name_idx = index(ref_table_id, ".");
			temp_ref_table_id = substr(ref_table_id, 0, ref_col_name_idx - 1);
			ref_col_name = substr(ref_table_id, ref_col_name_idx + 1, (length(ref_table_id) - ref_col_name_idx ));
			found = 0;
			for ( kdx = 1; kdx <= k; kdx++ ) {
			    if ( ref_col_name_arr[kdx]  == ref_col_name ) {
				found = 1;
				break;
			    }
			}
			if ( found == 0 ) 
			    ref_col_name_arr[k++] = ref_col_name;
			    
			ref_table_id = temp_ref_table_id;
			    
			FIRSTCHAR = substr(ref_table, 1, 1);
			if ( CHAR = index(lower, FIRSTCHAR)) {
			    first_upper_ref_table = substr(upper, CHAR, 1) substr(ref_table, 2);
			} else {
				first_upper_ref_table = ref_table;
			}
			gsub("_", "", first_upper_ref_table);
			n=split(ref_table_id, a, "_");
			FIRSTCHAR = substr(a[1],1,1);
			if (CHAR = index(lower, FIRSTCHAR)) {
			    a[1] = substr(upper, CHAR, 1) substr(a[1], 2);
			}
			ref_table_id = a[1];
			    for ( idx = 2; idx <= n; idx++ ) {
			    FIRSTCHAR = substr(a[idx],1,1);
			    if (CHAR = index(lower, FIRSTCHAR)) {
				a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
			    }
			    ref_table_id = ref_table_id a[idx];
			}
			    
			FIRSTCHAR = substr(ref_col_name, 1, 1);
			if ( CHAR = index(lower, FIRSTCHAR)) {
			    first_upper_ref_col = substr(upper, CHAR, 1) substr(ref_col_name, 2);
			} else {
			    first_upper_ref_col = ref_col_name;
			}
			gsub("_", "", first_upper_ref_col);
			n=split(ref_col_name, a, "_");
			FIRSTCHAR = substr(a[1],1,1);
			if (CHAR = index(lower, FIRSTCHAR)) {
			    a[1] = substr(upper, CHAR, 1) substr(a[1], 2);
			}
			ref_col_name = a[1];
			for ( idx = 2; idx <= n; idx++ ) {
			    FIRSTCHAR = substr(a[idx],1,1);
			    if (CHAR = index(lower, FIRSTCHAR)) {
				a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
			    }
			    ref_col_name = ref_col_name a[idx];
			}
			printf("%s\n", ref_col_name);
			printf("\ttr = new TableRowElement();\n")>>app_bean_file
			printf("\tbe = new BoldElement(%s.%s_LABEL);\n", app_constants, toupper(field_names[j]))>>app_bean_file
			printf("\tbe.setId(Constants.BODY_ROW_STYLE);\n")>>app_bean_file
			printf("\ttd = new TableDataElement(be);\n")>>app_bean_file
			printf("\ttr.addElement(td);\n")>>app_bean_file
			if ( found == 0 ) {
			    printf("\tnameVector = new Vector<String>();\n")>>app_bean_file;
			    printf("\tvalueVector = new Vector<Integer>();\n")>>app_bean_file;
			    printf("\t%sInterface %sIf = new %sImpl();\n", first_upper_ref_table, tolower(ref_table), first_upper_ref_table)>>app_bean_file;
			    ref_table_length=length(ref_table);
			    last_char=substr(ref_table, ref_table_length, 1);
			    if (last_char == "s") {
				printf("\t%sObject[] %sRefArr = %sIf.getAll%s();\n", first_upper_ref_table, tolower(ref_table), tolower(ref_table), first_upper_ref_table)>>app_bean_file;
			    } else {
				printf("\t%sObject[] %sRefArr = %sIf.getAll%ss();\n", first_upper_ref_table, tolower(ref_table), tolower(ref_table), first_upper_ref_table)>>app_bean_file;
			    }
			    printf("\tfor (int iterator = 0; iterator < %sRefArr.length; iterator++) {\n", tolower(ref_table))>>app_bean_file;
			    printf("\t    %sObject %sObject = %sRefArr[iterator];\n", first_upper_ref_table, tolower(ref_table), tolower(ref_table))>>app_bean_file;
			    printf("\t    if (%sObject == null)\n", tolower(ref_table))>>app_bean_file;
			    printf("\t\tbreak;\n")>>app_bean_file;
			    printf("\t    nameVector.addElement(String.valueOf(%sObject.get%s()));\n", tolower(ref_table), ref_col_name)>>app_bean_file;
			    printf("\t    valueVector.addElement(new Integer(%sObject.get%s()));\n", tolower(ref_table), ref_table_id)>>app_bean_file;
			    printf("\t}\n")>>app_bean_file;
			}
 			printf("\tif ( %s != 0 )\n", new_field_names[1])>>app_bean_file;
			printf("\t\tse = new SelectElement(%s.%s_STR, nameVector, valueVector, String.valueOf(selected%sObj.get%s()), 0);\n", app_constants, toupper(field_names[j]),  tmp_file_name, temp_field_names[j])>>app_bean_file;
                        printf("\telse\n")>>app_bean_file;
			printf("\t\tse = new SelectElement(%s.%s_STR, nameVector, valueVector, String.valueOf(%s), 0);\n", app_constants, toupper(field_names[j]), new_field_names[j])>>app_bean_file;
			printf("\ttd = new TableDataElement(se);\n")>>app_bean_file;
			printf("\ttr.addElement(td);\n")>>app_bean_file;
			printf("\tte.addElement(tr);\n")>>app_bean_file;
			printf("\n")>>app_bean_file;
		    } # For ints with references
		    else {
			printf("\ttr = new TableRowElement();\n")>>app_bean_file
			printf("\tbe = new BoldElement(%s.%s_LABEL);\n", app_constants, toupper(field_names[j]))>>app_bean_file
			printf("\tbe.setId(Constants.BODY_ROW_STYLE);\n")>>app_bean_file
			printf("\ttd = new TableDataElement(be);\n")>>app_bean_file
			printf("\ttr.addElement(td);\n")>>app_bean_file
			printf("\tif ( %s != 0 )\n", new_field_names[1])>>app_bean_file;
			printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, String.valueOf(selected%sObj.get%s())));\n", app_constants, toupper(field_names[j]), tmp_file_name, temp_field_names[j])>>app_bean_file;
			printf("\telse\n", new_field_names[1])>>app_bean_file;
			printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, Constants.EMPTY));\n", app_constants, toupper(field_names[j]), tmp_file_name)>>app_bean_file;
			printf("\ttr.addElement(td);\n")>>app_bean_file
			printf("\tte.addElement(tr);\n\n")>>app_bean_file
		    }
		} else { # For Not Ints
		    printf("\ttr = new TableRowElement();\n")>>app_bean_file
		    if ( tolower(field_names[j]) == "is_valid" ) {
			printf("\tbe = new BoldElement(Constants.IS_VALID_LABEL);\n")>>app_bean_file
		    } else {
			printf("\tbe = new BoldElement(%s.%s_LABEL);\n", app_constants, toupper(field_names[j]))>>app_bean_file
		    }
		    printf("\tbe.setId(Constants.BODY_ROW_STYLE);\n")>>app_bean_file
		    printf("\ttd = new TableDataElement(be);\n")>>app_bean_file
		    printf("\ttr.addElement(td);\n")>>app_bean_file
		    if ( field_types[j] == "String" ) {
			printf("\tif ( %s != 0 )\n", new_field_names[1])>>app_bean_file;
			if ( tolower(field_names[j]) == "is_valid" ) {
			    printf("\t    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, selected%sObj.getIsValid(), Constants.EMPTY)));\n", tmp_file_name)>>app_bean_file;
			    printf("\telse\n", new_field_names[1])>>app_bean_file;
			    printf("\t    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, Constants.YES_STR, Constants.EMPTY)));\n", tmp_file_name)>>app_bean_file;
			} else if (tolower(field_names[j]) == "address" || tolower(field_names[j]) ~ /description/ ) {
			    printf("\t    txt = new TextareaElement(%s.%s_STR, 10, 80, TextareaElement.SOFT, Util.trim(selected%sObj.get%s()));\n", app_constants, toupper(field_names[j]), tmp_file_name, temp_field_names[j])>>app_bean_file;
			    printf("\telse\n", new_field_names[1])>>app_bean_file;
			    printf("\t    txt = new TextareaElement(%s.%s_STR, 10, 80, TextareaElement.SOFT, Constants.EMPTY);\n", app_constants, toupper(field_names[j]))>>app_bean_file;
			    printf("\ttd = new TableDataElement(txt);\n")>>app_bean_file;
			} else {
			    printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, selected%sObj.get%s()));\n", app_constants, toupper(field_names[j]), tmp_file_name, temp_field_names[j])>>app_bean_file;
			    printf("\telse\n", new_field_names[1])>>app_bean_file;
			    printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, Constants.EMPTY));\n", app_constants, toupper(field_names[j]), tmp_file_name)>>app_bean_file;
			}
			printf("\ttr.addElement(td);\n")>>app_bean_file
			printf("\tte.addElement(tr);\n\n")>>app_bean_file
		    } else if ( field_types[j] == "Date" ) {
                        printf("\tif ( %s != 0 ) {\n", new_field_names[1])>>app_bean_file;
                        printf("\t\tSimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);\n")>>app_bean_file;
                        printf("\t\tString formattedDate = dateFormatter.format(selected%sObj.get%s());\n", tmp_file_name, temp_field_names[j])>>app_bean_file;
			printf("\t\ttd = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, formattedDate));\n", app_constants, toupper(field_names[j]))>>app_bean_file;
                        printf("\t}")>>app_bean_file;
			printf("\telse\n", new_field_names[1])>>app_bean_file;
			printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, Constants.EMPTY));\n", app_constants, toupper(field_names[j]), tmp_file_name)>>app_bean_file;
			printf("\ttr.addElement(td);\n")>>app_bean_file
			printf("\tte.addElement(tr);\n\n")>>app_bean_file
                    } else {
			printf("\tif ( %s != 0 )\n", new_field_names[1])>>app_bean_file;
			printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, String.valueOf(selected%sObj.get%s())));\n", app_constants, toupper(field_names[j]), tmp_file_name, temp_field_names[j])>>app_bean_file;
			printf("\telse\n", new_field_names[1])>>app_bean_file;
			printf("\t    td = new TableDataElement(new InputElement(InputElement.TEXT, %s.%s_STR, Constants.EMPTY));\n", app_constants, toupper(field_names[j]), tmp_file_name)>>app_bean_file;
			printf("\ttr.addElement(td);\n")>>app_bean_file
			printf("\tte.addElement(tr);\n\n")>>app_bean_file
		    }
		}
	    } # End of if for non keys
	} # End of for loop
	printf("\n")>>app_bean_file
	printf("\ttr = new TableRowElement();\n")>>app_bean_file
	printf("\tbe = new BoldElement(Constants.UPLOAD_FILE_LABEL);\n")>>app_bean_file
	printf("\tbe.setId(Constants.BODY_ROW_STYLE);\n")>>app_bean_file
	printf("\ttd = new TableDataElement(be);\n")>>app_bean_file
	printf("\ttr.addElement(td);\n")>>app_bean_file

	printf("\n")>>app_bean_file
	printf("\tie = new InputElement(InputElement.FILE, Constants.UPLOAD_FILE_NAME_STR,\"\");\n")>>app_bean_file
	printf("\ttd = new TableDataElement(ie);\n")>>app_bean_file
	printf("\ttr.addElement(td);\n")>>app_bean_file
	printf("\tte.addElement(tr);\n")>>app_bean_file
	printf("\n")>>app_bean_file

	printf("\treturn te.getHTMLTag() + new BreakElement().getHTMLTag() +  new BreakElement().getHTMLTag() + UtilBean.getSubmitButton() + UtilBean.getDownloadButton();\n")>>app_bean_file
	printf("    }\n")>>app_bean_file;

	printf("\n")>>app_bean_file;
	printf("    public void writeToFile(String outputFileName, Object obj) throws AppException {\n")>>app_bean_file;
	printf("\tDebugHandler.fine(\"writeToFile(\" + outputFileName + \",\" + obj + \")\");\n")>>app_bean_file;
	printf("\tHSSFWorkbook wb = new HSSFWorkbook();\n")>>app_bean_file;
	printf("\tHSSFFont font01Bold = wb.createFont();\n")>>app_bean_file;
	printf("\tfont01Bold.setFontHeightInPoints((short)12);\n")>>app_bean_file;
	printf("\tfont01Bold.setFontName(\"Times New Roman\");\n")>>app_bean_file;
	printf("\tfont01Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\tHSSFFont font01Normal = wb.createFont();\n")>>app_bean_file;
	printf("\tfont01Normal.setFontHeightInPoints((short)12);\n")>>app_bean_file;
	printf("\tfont01Normal.setFontName(\"Times New Roman\");\n")>>app_bean_file;
	printf("\tfont01Normal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\t// Create style\n")>>app_bean_file;
	printf("\tHSSFCellStyle cellstyleTblHdr = wb.createCellStyle();\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setFont(font01Bold);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setWrapText(true);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);\n")>>app_bean_file;
	printf("\tcellstyleTblHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\tHSSFCellStyle cellstyleTblLeft = wb.createCellStyle();\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setFont(font01Normal);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setWrapText(true);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);\n")>>app_bean_file;
	printf("\tcellstyleTblLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\tHSSFSheet sheet = wb.createSheet();\n")>>app_bean_file;
	printf("\tFileOutputStream fileOut = null;\n")>>app_bean_file;
	printf("\tint rowNum = 0;\n")>>app_bean_file;
	printf("\tint col = 0;\n")>>app_bean_file;
	printf("\tHSSFRow row = null;\n")>>app_bean_file;
	printf("\tHSSFCell cell = null;\n")>>app_bean_file;
	printf("\ttry {\n")>>app_bean_file;
	printf("\t    fileOut = new FileOutputStream(outputFileName);\n")>>app_bean_file;
	printf("\t} catch (FileNotFoundException fnf) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"Unable to find file \" + outputFileName);\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("\trow = sheet.createRow((short)rowNum);\n")>>app_bean_file;
	printf("\tsheet.setColumnWidth((short)col, (short) (0));\n")>>app_bean_file;
	printf("\tcell = row.createCell((short)col++);\n")>>app_bean_file;
	printf("\tcell.setCellStyle(cellstyleTblHdr);\n")>>app_bean_file;
	printf("\tcell.setCellValue(%s.%s_LABEL);\n", app_constants, toupper(field_names[1]))>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("\tcell = row.createCell((short)col++);\n")>>app_bean_file;
	printf("\tcell.setCellStyle(cellstyleTblHdr);\n")>>app_bean_file;
	printf("\tcell.setCellValue(\"DB Operation\");\n")>>app_bean_file;
	printf("\n")>>app_bean_file;
	for ( j = 2; j <= i; ++j ) {
	    printf("\tcell = row.createCell((short)col++);\n")>> app_bean_file;
	    printf("\tcell.setCellStyle(cellstyleTblHdr);\n")>> app_bean_file;
	    printf("\tcell.setCellValue(%s.%s_LABEL);\n", app_constants, toupper(field_names[j]))>> app_bean_file;
	    printf("\n")>>app_bean_file;
	}

	lower_table_name = tolower(tmp_file_name);
	printf("\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    printf("\t%sObject[] %sArr = %sIf.getAll%s();\n", tmp_file_name, lower_table_name, lower_table_name, tmp_file_name)>>app_bean_file;
	} else {
	    printf("\t%sObject[] %sArr = %sIf.getAll%ss();\n", tmp_file_name, lower_table_name, lower_table_name, tmp_file_name)>>app_bean_file;
	}
	printf("\tif ( %sArr != null && %sArr.length > 0 ) {\n", lower_table_name, lower_table_name)>>app_bean_file;
	printf("\t    for (int iterator = 0; iterator < %sArr.length; iterator++) {\n", lower_table_name)>>app_bean_file;
	printf("\t\t%sObject %sObj = %sArr[iterator];\n", tmp_file_name, lower_table_name, lower_table_name)>>app_bean_file;
	printf("\t\tif ( %sObj == null )\n", lower_table_name)>>app_bean_file;
	printf("\t\t    break;\n", lower_table_name)>>app_bean_file;
	printf("\t\trowNum++;\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\t\tcol = 0;\n")>>app_bean_file;
	printf("\t\trow = sheet.createRow((short)rowNum);\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\t\tcell = row.createCell((short)col++);\n")>>app_bean_file;
	printf("\t\tcell.setCellStyle(cellstyleTblLeft);\n")>>app_bean_file;
	printf("\t\tcell.setCellValue(%sObj.get%s());\n", lower_table_name, temp_field_names[1])>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\t\tcell = row.createCell((short)col++);\n")>>app_bean_file;
	printf("\t\tcell.setCellStyle(cellstyleTblLeft);\n")>>app_bean_file;
	printf("\t\tcell.setCellValue(\"INFO\");\n")>>app_bean_file;
	printf("\n")>>app_bean_file;

	for ( j = 2; j <= i; ++j ) {
	    printf("\t\tcell = row.createCell((short)col++);\n")>>app_bean_file;
	    printf("\t\tcell.setCellStyle(cellstyleTblLeft);\n")>>app_bean_file;
	    printf("\t\tcell.setCellValue(%sObj.get%s());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    if ( j != i ) 
		printf("\n")>>app_bean_file;
	}

	printf("\t    }\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("\ttry {\n")>>app_bean_file;
	printf("\t    wb.write(fileOut);\n")>>app_bean_file;
	printf("\t    fileOut.close();\n")>>app_bean_file;
	printf("\t} catch (IOException ioe) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"Exception closing file\" + outputFileName);\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("    }\n")>>app_bean_file;
	printf("\n")>>app_bean_file;
	printf("    public void readFromFile(String inputFileName, Object obj) throws AppException {\n")>>app_bean_file;
	printf("\tDebugHandler.fine(\"readFromFile(\" + inputFileName + obj + \")\");\n")>>app_bean_file;
	printf("\tPOIFSFileSystem fs = null;\n")>>app_bean_file;
	printf("\tHSSFWorkbook wb = null;\n")>>app_bean_file;
	printf("\ttry {\n")>>app_bean_file;
	printf("\t    fs = new POIFSFileSystem(new FileInputStream(inputFileName));\n")>>app_bean_file;
	printf("\t} catch (FileNotFoundException fnf) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"Unable to find file \" + inputFileName);\n")>>app_bean_file;
	printf("\t} catch (IOException ioe) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"IOException while opening file \" + inputFileName);\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("\ttry {\n")>>app_bean_file;
	printf("\t    wb = new HSSFWorkbook(fs);\n")>>app_bean_file;
	printf("\t} catch (IOException ioe) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"IOException while getting workbook.\");\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("\tHSSFSheet sheet = wb.getSheetAt(0);\n")>>app_bean_file;
	printf("\tFileInputStream fileIn = null;\n")>>app_bean_file;
	printf("\ttry {\n")>>app_bean_file;
	printf("\t    fileIn = new FileInputStream(inputFileName);\n")>>app_bean_file;
	printf("\t} catch (FileNotFoundException fnf) {\n")>>app_bean_file;
	printf("\t    throw new AppException(\"Unable to find file \" + inputFileName);\n")>>app_bean_file;
	printf("\t}\n")>>app_bean_file;
	printf("\tint rowNum = 0;\n")>>app_bean_file;
	printf("\tint col = 0;\n")>>app_bean_file;
	printf("\tHSSFRow row = null;\n")>>app_bean_file;
	printf("\tHSSFCell cell = null;\n")>>app_bean_file;
	printf("\tString dbOp = null;\n")>>app_bean_file;
	printf("\t%sInterface %sIf = new %sImpl();\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\t%sObject %sObject = new %sObject();\n", tmp_file_name, lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\n")>>app_bean_file;

	printf("\trowNum = 0;\n")>>app_bean_file;
	printf("\twhile ( true ) {\n")>>app_bean_file;
	printf("\t    row = sheet.getRow(++rowNum);\n")>>app_bean_file;
	printf("\t    if ( row == null )\n")>>app_bean_file;
	printf("\t\tbreak;\n")>>app_bean_file;
	printf("\t    %sObject = new %sObject();\n", lower_table_name, tmp_file_name)>>app_bean_file;
	printf("\t    cell = row.getCell((short)1);\n")>>app_bean_file;
	printf("\t    if ( cell != null )\n")>>app_bean_file;
	printf("\t\tdbOp = Util.trim(cell.getStringCellValue());\n")>>app_bean_file;
	printf("\t    else\n")>>app_bean_file;
	printf("\t\tdbOp = null;\n")>>app_bean_file;
	printf("\t    DebugHandler.fine(\"DbOp = |\" + dbOp + \"|\");\n")>>app_bean_file;
	printf("\t    if ( dbOp != null &&  dbOp.equalsIgnoreCase(\"UPDATE\") ) {\n")>>app_bean_file;
	printf("\t\tcell = row.getCell((short)0); // Get the first column\n")>>app_bean_file;
	printf("\t\ttry {\n")>>app_bean_file;
	printf("\t\t    %sObject.set%s((int)cell.getNumericCellValue());\n", lower_table_name, temp_field_names[1])>>app_bean_file;
	printf("\t\t} catch (NumberFormatException nfe) {\n")>>app_bean_file;
	printf("\t\t    throw new AppException(\"Column A has been changed in \" + wb.getSheetName((short)0) + \" Current value is Row num \" + row + \" is : \" + cell.getStringCellValue());\n")>>app_bean_file;
	printf("\t\t}\n")>>app_bean_file;
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    new_method = substr(tmp_file_name, 1, tmp_file_length - 1);
	    printf("\t\t%sObject = %sIf.get%s(%sObject.get%s());\n", lower_table_name, lower_table_name, new_method, lower_table_name,temp_field_names[1])>>app_bean_file;
	} else {
	    printf("\t\t%sObject = %sIf.get%s(%sObject.get%s());\n", lower_table_name, lower_table_name, tmp_file_name, lower_table_name,temp_field_names[1])>>app_bean_file;
	}
	printf("\t\tcol = 2; // Starting from 3rd Column\n")>>app_bean_file;
	for ( j = 2; j <= i; ++j ) {
	    printf("\t\tcell = row.getCell((short)col++);\n")>>app_bean_file;
	    printf("\t\tif ( cell != null )\n")>>app_bean_file;
	    if ( field_types[j] == "int" || field_types[j] == "double" ) {
		printf("\t\ttry {\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		if ( field_types[j] == "int" ) {
		    printf("\t\t    %sObject.set%s((int)cell.getNumericCellValue());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		} else {
		    printf("\t\t    %sObject.set%s(cell.getNumericCellValue());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		}
		printf("\t\t} catch (NumberFormatException nfe) {\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t    %sObject.set%s(0);\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t}\n")>>app_bean_file;
	    }
	    else if ( field_types[j] == "Date" ) {
		printf("\t\t    %sObject.set%s(cell.getDateCellValue());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	    else if ( field_types[j] == "float" ) {
		printf("\t\t    %sObject.set%s(Float.parseFloat(Util.trim(cell.getStringCellValue())));\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	    else {
		printf("\t\t    %sObject.set%s(Util.trim(cell.getStringCellValue()));\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	}
	printf("\t\tDebugHandler.fine(\"Updating %s \" + %sObject);\n", tmp_file_name, lower_table_name)>>app_bean_file;
	printf("\t\t%sIf.update%s(%sObject);\n", lower_table_name, tmp_file_name, lower_table_name)>>app_bean_file;
	printf("\t    } else if ( dbOp != null && dbOp.equalsIgnoreCase(\"INSERT\") ) {\n")>>app_bean_file;
	printf("\t\tcol = 2; // Starting from 3rd Column\n")>>app_bean_file;
	for ( j = 2; j <= i; ++j ) {
	    printf("\t\tcell = row.getCell((short)col++);\n")>>app_bean_file;
	    printf("\t\tif ( cell != null )\n")>>app_bean_file;
	    if ( field_types[j] == "int" || field_types[j] == "double" ) {
		printf("\t\ttry {\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t    %sObject.set%s((int)cell.getNumericCellValue());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t} catch (NumberFormatException nfe) {\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t    %sObject.set%s(0);\n", lower_table_name, temp_field_names[j])>>app_bean_file;
		printf("\t\t}\n")>>app_bean_file;
	    }
	    else if ( field_types[j] == "Date" ) {
		printf("\t\t    %sObject.set%s(cell.getDateCellValue());\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	    else if ( field_types[j] == "float" ) {
		printf("\t\t    %sObject.set%s(Float.parseFloat(Util.trim(cell.getStringCellValue())));\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	    else {
		printf("\t\t    %sObject.set%s(Util.trim(cell.getStringCellValue()));\n", lower_table_name, temp_field_names[j])>>app_bean_file;
	    }
	}
	printf("\t\tDebugHandler.fine(\"Adding %s \" + %sObject);\n", tmp_file_name, lower_table_name)>>app_bean_file;
	printf("\t\t%sIf.add%s(%sObject);\n", lower_table_name, tmp_file_name, lower_table_name)>>app_bean_file;
	printf("\t    } else if ( dbOp != null && dbOp.equalsIgnoreCase(\"DELETE\") ) {\n")>>app_bean_file;
	printf("\t\tcell = row.getCell((short)0); // Get the first column\n")>>app_bean_file;
	printf("\t\ttry {\n")>>app_bean_file;
	printf("\t\t    %sObject.set%s((int)cell.getNumericCellValue());\n", lower_table_name, temp_field_names[1])>>app_bean_file;
	printf("\t\t} catch (NumberFormatException nfe) {\n")>>app_bean_file;
	printf("\t\t    throw new AppException(\"Column A has been changed in \" + wb.getSheetName((short)0) + \" Current value is Row num \" + row + \" is : \" + cell.getStringCellValue());\n")>>app_bean_file;
	printf("\t\t}\n")>>app_bean_file;
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    new_method = substr(tmp_file_name, 1, tmp_file_length - 1);
	    printf("\t\t%sObject = %sIf.get%s(%sObject.get%s());\n", lower_table_name, lower_table_name, new_method, lower_table_name,temp_field_names[1])>>app_bean_file;
	} else {
	    printf("\t\t%sObject = %sIf.get%s(%sObject.get%s());\n", lower_table_name, lower_table_name, tmp_file_name, lower_table_name,temp_field_names[1])>>app_bean_file;
	}
	found = 0;
	for ( j = 2; j <= i; ++j ) {
	    if ( tolower(field_names[j]) == "is_valid" ) {
		found = 1;
	    }
	}
	if ( found == 1 ) {
	    printf("\t\t%sObject.setIsValid(Constants.NO_STR);\n", lower_table_name)>>app_bean_file;	    
	    printf("\t\t%sIf.update%s(%sObject);\n", lower_table_name, tmp_file_name, lower_table_name)>>app_bean_file;	    
	} else {
	    printf("\t\t%sIf.delete%s(%sObject);\n", lower_table_name, tmp_file_name, lower_table_name)>>app_bean_file;	    
	}
	printf("\t    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase(\"INFO\") ) {\n")>>app_bean_file;
	printf("\t\tthrow new AppException(\"Invalid operation \" + dbOp + \" in Row num: \" + rowNum);\n")>>app_bean_file;
	printf("\t    }\n")>>app_bean_file;

	printf("\t}\n")>>app_bean_file;
	printf("    }\n")>>app_bean_file;
	printf("}\n")>>app_bean_file;

	close(app_bean_file);
    }

    function write_app_properties_file() {
	for ( j = 1; j <= i; ++j ) {
	    n=split(field_names[j], a, "_");
	    FIRSTCHAR = substr(a[1],1,1);
	    if (CHAR = index(lower, FIRSTCHAR)) {
		a[1] = substr(upper, CHAR, 1) substr(a[1], 2);
	    }
	    field_labels[j] = a[1];
	    for ( idx = 2; idx <= n; idx++ ) {
		FIRSTCHAR = substr(a[idx],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		    a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
		}
		field_labels[j] = field_labels[j] " " a[idx];
	    }
	}
	for ( j = 1; j <= i; ++j ) {
	    if ( field_types[j] == "Date" )
		    printf("app.%s_label=%s (MM-dd-yyyy):\n", tolower(field_names[j]), field_labels[j])>> app_properties_file;
	    else
		    printf("app.%s_label=%s:\n", tolower(field_names[j]), field_labels[j])>> app_properties_file;
	}
	printf("app.new_%s=New %s\n", tolower(tmp_file_name), tmp_file_name)>> app_properties_file;
	printf("app.current_%s_label=Current %s:\n", tolower(tmp_file_name), tmp_file_name)>>app_properties_file;
	close(app_properties_file);
    }

    function write_app_file() {
	for ( j = 1; j <= i; ++j ) {
	    printf("\n\t%s.%s_LABEL = siteProps.getProperty(\"app.%s_label\");", app_constants, toupper(field_names[j]),tolower(field_names[j]))>> app_file;
	}
	printf("\n")>>app_file;
	close(app_file);
    }

    function write_app_constants_file() {
	printf("    public static String %s = \"Manage %s\";\n", jsp_constant_str, tmp_file_name)>>app_constants_file;
	printf("    public static String %s = \"Manage%s.jsp\";\n", jsp_constant_jsp_str, tmp_file_name)>>app_constants_file;
	printf("    public static String %s_BEAN_NAME_STR = \"app.appui.%sBean\";\n", toupper(tmp_file_name), tmp_file_name)>>app_constants_file;
	for ( j = 1; j <= i; ++j ) {
	    n=split(field_names[j], a, "_");
	    FIRSTCHAR = substr(a[1],1,1);
	    if (CHAR = index(upper, FIRSTCHAR)) {
		a[1] = substr(lower, CHAR, 1) substr(a[1], 2);
	    }
	    new_field_names[j] = a[1];

	    for ( idx = 2; idx <= n; idx++ ) {
		FIRSTCHAR = substr(a[idx],1,1);
		if (CHAR = index(lower, FIRSTCHAR)) {
		    a[idx] = substr(upper, CHAR, 1) substr(a[idx], 2);
		}
		new_field_names[j] = new_field_names[j] a[idx];
	    }
	    printf("    public static String %s_STR = \"%s\";\n", toupper(field_names[j]), new_field_names[j])>>app_constants_file;

	    printf("    public static String %s_LABEL;\n", toupper(field_names[j]))>>app_constants_file;
	}
	close(app_constants_file);
    }

    function write_jsp_file() {
	app_jsp_constant = app_constants "." jsp_constant_jsp_str;
	printf("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n\n")>jsp_filename;
	printf("<%@ page errorPage=\"/jsp/Error.jsp\" language=\"java\" import=\"app.appui.%sBean, core.util.Constants,  core.util.Util, app.util.%s\" %%>\n", tmp_file_name, app_constants)>>jsp_filename;
	printf("<jsp:useBean id=\"%sBean\" scope=\"session\" class=\"app.appui.%sBean\"/>\n", tmp_file_name, tmp_file_name)>>jsp_filename;
	printf("<jsp:setProperty name=\"%sBean\" property=\"*\"/>\n", tmp_file_name)>>jsp_filename;
	printf("<html>\n", tmp_file_name)>>jsp_filename;
	printf("<head>\n", tmp_file_name)>>jsp_filename;
	tmp_file_length = length(tmp_file_name);
	last_char=substr(tmp_file_name, tmp_file_length, 1);
	if ( last_char == "s" ) {
	    new_title = substr(tmp_file_name, 1, tmp_file_length - 1);
	    printf("  <title>Manage %ss</title>\n", new_title)>>jsp_filename;
	} 
	else
	    printf("  <title>Manage %ss</title>\n", tmp_file_name)>>jsp_filename;
	printf("  <meta http-equiv=\"content-type\"\n", tmp_file_name)>>jsp_filename;
	printf(" content=\"text/html; charset=ISO-8859-1\">\n", tmp_file_name)>>jsp_filename;
	printf("  <meta name=\"author\" content=\" Govind Thirumalai\">\n", tmp_file_name)>>jsp_filename;
	printf("<script>\n", tmp_file_name)>>jsp_filename;
	printf("<%%@ include file=\"validation.js\" %%>\n", tmp_file_name)>>jsp_filename;
	printf("<%%@ include file=\"common_utils.js\" %%>\n", tmp_file_name)>>jsp_filename;
	printf("function validateForm(form) \n", tmp_file_name)>>jsp_filename;
	printf("{\n")>>jsp_filename;
        for ( j = 1; j <= i; ++j ) {
            if ( keys[j] == "not" ) {
	        printf("\tvar tmp = form.<%= AppConstants.%s_STR%>.value;\n", toupper(field_names[j]))>>jsp_filename;
	        printf("\tif ( isEmpty(tmp) ) {\n")>>jsp_filename;
	        printf("\t\talert(\"<%= AppConstants.%s_LABEL %> cannot be empty\");\n", toupper(field_names[j]))>>jsp_filename;
	        printf("\t\treturn false;\n", tmp_file_name)>>jsp_filename;
	        printf("\t}\n", tmp_file_name)>>jsp_filename;
                if ( field_types[j] == "String" ) {
                   printf("\tif ( tmp.length > %d ) {\n", field_length[j])>>jsp_filename;
	           printf("\t\talert(\"<%= AppConstants.%s_LABEL %> length cannot be greater than %d\");\n", toupper(field_names[j]), field_length[j])>>jsp_filename;
	           printf("\t\treturn false;\n", tmp_file_name)>>jsp_filename;
	           printf("\t}\n", tmp_file_name)>>jsp_filename;
                } 
            }
        }
	printf("\treturn true;\n", tmp_file_name)>>jsp_filename;
	printf("}\n", tmp_file_name)>>jsp_filename;
	printf("\n")>>jsp_filename;
	printf("function downloadValidateForm(form) \n", tmp_file_name)>>jsp_filename;
	printf("{\n", tmp_file_name)>>jsp_filename;
	printf("    return true;\n", tmp_file_name)>>jsp_filename;
	printf("}\n", tmp_file_name)>>jsp_filename;
	printf("</script>\n", tmp_file_name)>>jsp_filename;
	printf("</head>\n", tmp_file_name)>>jsp_filename;
	printf("<link href=\"/aprmarathon/jsp/main.css\" rel=\"stylesheet\" type=\"text/css\" />\n", tmp_file_name)>>jsp_filename;
        printf("<link href=\"/aprmarathon/jsp/dropdown.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\" />\n")>>jsp_filename;
        printf("<link href=\"/aprmarathon/jsp/default.ultimate.css\" media=\"screen\" rel=\"stylesheet\" type=\"text/css\" />\n")>>jsp_filename;
	
	printf("<body>\n", tmp_file_name)>>jsp_filename;
	printf("<%%@ include file=\"Navigation.jsp\" %%>\n", tmp_file_name)>>jsp_filename;
	printf("<form action=<%%= Util.getBaseurl() %%> method=post enctype=\"multipart/form-data\">\n", tmp_file_name)>>jsp_filename;
	printf("<%% %sBean.getRequestParameters(request); %%>\n", tmp_file_name)>>jsp_filename;
	printf("<%%= core.appui.UtilBean.getSaveProfileFlag() %%>\n", tmp_file_name)>>jsp_filename;
	printf("<%%= core.appui.UtilBean.getDownloadFlag() %%>\n", tmp_file_name)>>jsp_filename;
	printf("<%%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, %s.%s_BEAN_NAME_STR) %%>\n", app_constants, toupper(tmp_file_name))>>jsp_filename;
	printf("<%%= core.appui.UtilBean.getNextJsp(%s) %%>\n", app_jsp_constant)>>jsp_filename;
	printf("<%%= %sBean.get%sInfo() %%>\n", tmp_file_name, tmp_file_name)>>jsp_filename;
	printf("</form>\n", tmp_file_name)>>jsp_filename;
	printf("<%%@ include file=\"Footer.jsp\" %%>\n", tmp_file_name)>>jsp_filename;
	printf("</body>\n", tmp_file_name)>>jsp_filename;
	printf("</html>\n", tmp_file_name)>>jsp_filename;
	close(jsp_filename);
    }

    NF == 1 { 
	if ( table_name != "" ) {
	    init();
	    write_jsp_file();
	    write_app_constants_file();
	    write_app_file();
	    write_app_properties_file();
	    write_app_bean_file();
	}
	i = 0;
	table_name=$1; 
	tmp_file_name = table_name;
	gsub("_", "", tmp_file_name);
	printf("    public static String CURRENT_%s_LABEL;\n", toupper(tmp_file_name))>>app_constants_file;
	printf("    public static String NEW_%s;\n", toupper(tmp_file_name))>>app_constants_file;
	printf("\t%s.CURRENT_%s_LABEL = siteProps.getProperty(\"app.current_%s_label\");\n", app_constants, toupper(tmp_file_name), tolower(tmp_file_name))>>app_file;
	printf("\t%s.NEW_%s = siteProps.getProperty(\"app.new_%s\");\n", app_constants, toupper(tmp_file_name), tolower(tmp_file_name))>app_file;

    }
    NF == 2 || NF == 3 || NF == 4 { 
	i++;
	field_names[i] = tolower($1);
	lower_two = tolower($2)
	if ( lower_two == "integer" || lower_two == "int" )
	  field_types[i] = "int";
	else if ( lower_two ~ /varchar/ || lower_two == "long" || lower_two == "clob" ) {
              field_types[i] = "String";
              if ( lower_two ~ /varchar/ ) {
                   field_length_lower_idx = index(lower_two, "(");
                   field_length_upper_idx = index(lower_two, ")");
                   field_length[i] = substr(lower_two, field_length_lower_idx + 1 , field_length_upper_idx);
              }
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
	if ( $3 == "key" )
	  keys[i] = $3;
	else if ( $3 == "references" ) {
	  keys[i] = $4;
	}
        else if ( $3 == "not" )
	  keys[i] = "not";
	else
	  keys[i] = "";
    }
    END { 
    init();
    write_jsp_file();
    write_app_constants_file();
    write_app_file();
    write_app_properties_file();
    write_app_bean_file();
}' $SCHEMA_FILE

######################################################################################################
# Begin Edits of AppConstants
cp /dev/null /tmp/${APP_CONSTANTS_JAVA}
exec 3<&0
exec 0<${APP_CONSTANTS_JAVA}
while read line; do
    line=`echo $line`;
    if [ -z "`grep \"${line}\" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_CONSTANTS_JAVA}`" ]; then
	printf "    $line\n" >> /tmp/${APP_CONSTANTS_JAVA}
    fi
done
exec 0<&3
sort -u /tmp/${APP_CONSTANTS_JAVA} > ${APP_CONSTANTS_JAVA}
sed "/${APP_CONSTANTS} {/r ${APP_CONSTANTS_JAVA}" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_CONSTANTS_JAVA}> /tmp/${APP_CONSTANTS_JAVA}
mv /tmp/${APP_CONSTANTS_JAVA} .
######################################################################################################
# Begin Edits of App.java
cp /dev/null /tmp/${APP_JAVA}
exec 3<&0
exec 0<${APP_JAVA}
while read line; do
    line=`echo $line`;
    if [ -z "`grep \"${line}\" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_JAVA}`" ]; then
	printf "    $line\n" >> /tmp/${APP_JAVA}
    fi
done
exec 0<&3
sort -u /tmp/${APP_JAVA} > ${APP_JAVA}
mv /tmp/${APP_JAVA} .
sed "/INSERT GENERATED CODE/r ${APP_JAVA}" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_JAVA} > /tmp/${APP_JAVA}
mv /tmp/${APP_JAVA} .
######################################################################################################
# Begin Edits of app.properties
cp /dev/null /tmp/${APP_PROPERTIES_FILE}
exec 3<&0
exec 0<${APP_PROPERTIES_FILE}
while read line; do
    line=`echo $line`;
    if [ -z "`grep \"${line}\" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_PROPERTIES_FILE}`" ]; then
	printf "$line\n" >> /tmp/${APP_PROPERTIES_FILE}
    fi
done
exec 0<&3
sort -u /tmp/${APP_PROPERTIES_FILE} > ${APP_PROPERTIES_FILE}
sed "/INSERT GENERATED CODE/r ${APP_PROPERTIES_FILE}" ${JAVA_DEV_ROOT}/src/java/app/util/${APP_PROPERTIES_FILE} > /tmp/${APP_PROPERTIES_FILE}
mv /tmp/${APP_PROPERTIES_FILE} .
######################################################################################################
# Begin Edits Makefile for appui
CreateMakefileEntry appui '*Bean.java' ${JAVA_DEV_ROOT}/src/java/app/appui UsersBean.java
######################################################################################################
# Begin Edits Makefile for public_html
CreateMakefileEntry public_html '*.jsp' ${JAVA_DEV_ROOT}/src/public_html ManageUsers.jsp
######################################################################################################
