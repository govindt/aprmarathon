<!DOCTYPE html PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<%@ page errorPage="Error.jsp" language="java" import="app.appui.LoginBean, core.util.Constants, core.appui.UtilBean, core.util.Util, app.util.AppConstants, app.appui.AppUtilBean" %>
<jsp:useBean id="LoginBean" scope="session" class="app.appui.LoginBean"/>
<jsp:setProperty name="LoginBean" property="*"/>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/dropdown.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/default.ultimate.css" media="screen" rel="stylesheet" type="text/css" />
<title>AppServlet Login</title>
</head>

<script>
<%@ include file="validation.js" %>
function validateForm(form) 
{
	var userNameStr =  form.username.value;
	var passwordStr =  form.password.value;

	var userNameErrString = "UserId cannot be empty";
	var passwordErrString = "Password cannot be empty";

	if ( isEmpty(userNameStr)) {
		alert(userNameErrString);
		return false;
	}

	if ( isEmpty(passwordStr)) {
		alert(passwordErrString);
		return false;
	}

	return true;
}
</script>

<body>

<%@ include file="Header.jsp" %>
<form method=post enctype=multipart/form-data action=<%= Util.getBaseurl() %>>
<% LoginBean.getRequestParameters(request); %>
<%= UtilBean.getHiddenField(Constants.JSP_STR, LoginBean.getNextJsp()) %>
<%= UtilBean.getHiddenField(AppConstants.LOGIN, AppConstants.LOGIN) %>
<%= UtilBean.getHiddenField(AppConstants.ERROR_STR, LoginBean.errString) %>
<%= UtilBean.getHiddenField(AppConstants.STATUS_STR, LoginBean.statusString) %>
<%= LoginBean.getPriorFields(request) %>
<center>
<table width=100%>
<tr align=left>
	<td width=33%><%@ include file="LeftWideSkyScraper.jsp" %></td>
	<td><table>
	<tr>
	<td><img src="/aprmarathon/jsp/images/t.gif" height="40">
	</td>
	</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" height="250" width="400">
		<tbody>
		<tr width="100%" bgcolor="#3368B7">
			<td align="center"> <span id="headline">Welcome to 
			</span><br>
			<b> <span id="headline">OAPT Login</span></b>
		        <p>
			<p id="body_label"><strong>Release 3.0</strong<p></p>
			<%= LoginBean.getUserInfo() %>
			<tbody align="center">
			<tr width="100%" bgcolor="#3368B7">
				<td><b> <input name="login" value="Login" onclick="if (validateForm(this.form)) { form.submit();}" type="button">
					<input value="Clear" type="reset"> </b>
				</td>
				</tr>
			</tbody>	
		</tr>
		</tbody>
	</table></td>
	<td width=33%><%@ include file="RightWideSkyScraper.jsp" %></td>
</table>
</center>
<table>
<tr>
<td><img src="/aprmarathon/jsp/images/t.gif" height="50">
</td>
</tr>
</table>
<%@ include file="Footer.jsp" %>
</body></html>
