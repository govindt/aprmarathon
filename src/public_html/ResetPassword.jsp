<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.ResetPasswordBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="ResetPasswordBean" scope="session" class="app.appui.ResetPasswordBean"/>
<jsp:setProperty name="ResetPasswordBean" property="*"/>
<html>
<head>
  <title>Manage ResetPassword</title>
<head>
  <title>Manage ResetPassword</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content="APR Charitable Trust">
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
</head>
<title>AppServlet - Manage ResetPassword</title>
<script>
<%@ include file="validation.js" %>
function validateForm(form) 
{
	return true;
}
</script>

<body>
<%@ include file="Header.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post>
<% ResetPasswordBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.RESET_PASSWORD_JSP_STR) %>
<%= core.appui.UtilBean.getHiddenField(AppConstants.ERROR_STR, ResetPasswordBean.errString) %>
<%= core.appui.UtilBean.getHiddenField(AppConstants.STATUS_STR, ResetPasswordBean.statusString) %>
<%= ResetPasswordBean.getResetPasswordInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
