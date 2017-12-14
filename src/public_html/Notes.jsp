<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.NotesBean, core.util.Constants, core.appui.UtilBean, core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="NotesBean" scope="session" class="app.appui.NotesBean"/>
<jsp:setProperty name="NotesBean" property="*"/>
<html>
<head>
  <title>Test</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content="APR Charitable Trust">
</head>
<title>AppServlet - Notes</title>
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
<script>
function validateForm(form)
{
	return true;
}
</script>
<%@ include file="Header.jsp" %>
<table>
<tr>
<td><img src="/aprmarathon/jsp/images/t.gif" height="40">
</td>
</tr>
</table>
<body>
<form action=<%= Util.getBaseurl() %> method=post>
<% NotesBean.getRequestParameters(request); %>
<%= UtilBean.getNextJsp(AppConstants.NOTE_JSP_STR) %>
<%= UtilBean.getSaveProfileFlag() %>
<%= NotesBean.getNotes() %>
<br>
<%= UtilBean.getSubmitButton() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
