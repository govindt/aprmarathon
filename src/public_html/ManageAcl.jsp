<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.AclBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="AclBean" scope="session" class="app.appui.AclBean"/>
<jsp:setProperty name="AclBean" property="*"/>
<html>
<head>
  <title>Manage Acls</title>
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
<link href="/aprmarathon/jsp/dropdown.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/default.ultimate.css" rel="stylesheet" type="text/css" />
<body>
<%@ include file="Navigation.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post enctype="multipart/form-data">
<% AclBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.ACL_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_ACL_JSP_STR) %>
<%= AclBean.getAclInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
