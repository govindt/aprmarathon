<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.EventBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="EventBean" scope="session" class="app.appui.EventBean"/>
<jsp:setProperty name="EventBean" property="*"/>
<html>
<head>
  <title>Manage Events</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.EVENT_NAME_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_NAME_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.EVENT_NAME_LABEL %> length cannot be greater than 50");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_START_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_START_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_END_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_END_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_REGISTATION_CLOSE_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_REGISTATION_CLOSE_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.EVENT_CHANGES_CLOSE_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.EVENT_CHANGES_CLOSE_DATE_LABEL %> cannot be empty");
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
<% EventBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.EVENT_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_EVENT_JSP_STR) %>
<%= EventBean.getEventInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
