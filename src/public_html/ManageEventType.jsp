<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.EventTypeBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="EventTypeBean" scope="session" class="app.appui.EventTypeBean"/>
<jsp:setProperty name="EventTypeBean" property="*"/>
<html>
<head>
  <title>Manage EventTypes</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.EVENT_TYPE_NAME_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_TYPE_NAME_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.EVENT_TYPE_NAME_LABEL %> length cannot be greater than 50");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_TYPE_START_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_TYPE_START_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_TYPE_END_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_TYPE_END_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_TYPE_VENUE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_TYPE_VENUE_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.EVENT_TYPE_VENUE_LABEL %> length cannot be greater than 50");
		return false;
	}
	return true;
}

function downloadValidateForm(form) 
{
    return true;
}
</script>
</head>
<link href="/aprmarathon/jsp/css/main.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/css/dropdown.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/css/default.ultimate.css" media="screen" rel="stylesheet" type="text/css" />
<body>
<%@ include file="Navigation.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post enctype="multipart/form-data">
<% EventTypeBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.EVENTTYPE_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_EVENTTYPE_JSP_STR) %>
<%= EventTypeBean.getEventTypeInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
