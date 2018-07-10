<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.RegistrationClassBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="RegistrationClassBean" scope="session" class="app.appui.RegistrationClassBean"/>
<jsp:setProperty name="RegistrationClassBean" property="*"/>
<html>
<head>
  <title>Manage RegistrationClass</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.REGISTRATION_CLASS_NAME_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.REGISTRATION_CLASS_NAME_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.REGISTRATION_CLASS_NAME_LABEL %> length cannot be greater than 50");
		return false;
	}
	var tmp = form.<%= AppConstants.REGISTRATION_CLASS_VALUE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.REGISTRATION_CLASS_VALUE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.REGISTRATION_FREE_TICKETS_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.REGISTRATION_FREE_TICKETS_LABEL %> cannot be empty");
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
<% RegistrationClassBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.REGISTRATIONCLASS_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_REGISTRATIONCLASS_JSP_STR) %>
<%= RegistrationClassBean.getRegistrationClassInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
