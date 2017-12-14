<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.MenuBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="MenuBean" scope="session" class="app.appui.MenuBean"/>
<jsp:setProperty name="MenuBean" property="*"/>
<html>
<head>
  <title>Manage Menus</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="validation.js" %>
<%@ include file="common_utils.js" %>
function validateForm(form) 
{
    return true;
}

function downloadValidateForm(form) 
{
    return true;
}
</script>
</head>
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
<body>
<%@ include file="Navigation.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post enctype="multipart/form-data">
<% MenuBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.MENU_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_MENU_JSP_STR) %>
<%= MenuBean.getMenuInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
