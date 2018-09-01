<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.SiteBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="SiteBean" scope="session" class="app.appui.SiteBean"/>
<jsp:setProperty name="SiteBean" property="*"/>
<html>
<head>
  <title>Manage Sites</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.SITE_NAME_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.SITE_NAME_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.SITE_NAME_LABEL %> length cannot be greater than 50");
		return false;
	}
	var tmp = form.<%= AppConstants.SITE_URL_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.SITE_URL_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 100 ) {
		alert("<%= AppConstants.SITE_URL_LABEL %> length cannot be greater than 100");
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
<% SiteBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.SITE_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_SITE_JSP_STR) %>
<%= SiteBean.getSiteInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
