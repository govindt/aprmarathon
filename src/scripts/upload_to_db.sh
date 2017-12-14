#!/bin/sh

MY_DIR=`dirname "$0"`
sdir="`pwd`"
cd ${MY_DIR}
MY_DIR="`pwd`"
cd "$sdir"

if [ -d "${MY_DIR}/../data" ]; then
    XLS_DIR="${MY_DIR}/../data"
elif [ -d "${MY_DIR}/../../data" ]; then
    XLS_DIR="${MY_DIR}/../../data"
else
    echo "Unable to find data directory"
    exit 1
fi
if [ -n "`uname -s | grep CYGWIN`" ]; then
    XLS_DIR=`cygpath --mixed "${XLS_DIR}"`
fi
if [ -f "${MY_DIR}/run_java.sh" ]; then
    JAVA_CMD="${MY_DIR}/run_java.sh"
else
    JAVA_CMD="${MY_DIR}/run_java"
fi


BEANS="app.appui.MenuBean|MenuData.xls
app.appui.ColorBean|ColorData.xls
app.appui.CooperBean|CooperData.xls
app.appui.VarietyBean|FruitVarietyData.xls
app.appui.RegionBean|RegionData.xls
app.appui.VarietalBean|VarietalData.xls
app.appui.VineyardBean|VineyardData.xls
"


for line in `echo $BEANS`; do
    bean_name=`echo $line | awk -F '|' '{print $1}'`
    xls_name=`echo $line | awk -F '|' '{print $2}'`
    if [ -f "${XLS_DIR}/${xls_name}" ]; then
	export JAVA_ARGS="-DXLS_FILES=${XLS_DIR}/${xls_name}"
	"${JAVA_CMD}" UploadSpreadSheet ${bean_name} 
    else
	echo "Unable to find ${xls_name}"
    fi
done

