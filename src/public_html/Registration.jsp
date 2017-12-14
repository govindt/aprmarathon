<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.RegistrationBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="RegistrationBean" scope="session" class="app.appui.RegistrationBean"/>
<jsp:setProperty name="RegistrationBean" property="*"/>
<html>
<head>
  <title>Manage Registration</title>
<head>
  <title>Manage Registration</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content="APR Charitable Trust">
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
</head>
<title>AppServlet - Manage Registration</title>
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
<% RegistrationBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.REGISTRATION_JSP_STR) %>
<%= core.appui.UtilBean.getHiddenField(AppConstants.ERROR_STR, RegistrationBean.errString) %>
<%= core.appui.UtilBean.getHiddenField(AppConstants.STATUS_STR, RegistrationBean.statusString) %>
<%= RegistrationBean.getRegistrationInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
