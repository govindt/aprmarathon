<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.ResultBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="ResultBean" scope="session" class="app.appui.ResultBean"/>
<jsp:setProperty name="ResultBean" property="*"/>
<html>
<head>
  <title>Manage Results</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
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
<link href="/aprmarathon/jsp/css/main.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/css/dropdown.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/css/default.ultimate.css" media="screen" rel="stylesheet" type="text/css" />
<body>
<%@ include file="Navigation.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post enctype="multipart/form-data">
<% ResultBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.RESULT_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_RESULT_JSP_STR) %>
<%= ResultBean.getResultInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
