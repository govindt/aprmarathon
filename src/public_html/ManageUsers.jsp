<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.UsersBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="UsersBean" scope="session" class="app.appui.UsersBean"/>
<jsp:setProperty name="UsersBean" property="*"/>
<html>
<head>
  <title>Manage Users</title>
<head>
  <title>Manage Users</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content="APR Charitable Trust">
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/dropdown.css" rel="stylesheet" type="text/css" />
<link href="/aprmarathon/jsp/default.ultimate.css" rel="stylesheet" type="text/css" />
</head>
<title>AppServlet - Manage Users</title>
<script>
<%@ include file="validation.js" %>
function validateUserAndPassword(form) {
	var username = form.<%= AppConstants.NEW_USERNAME_STR %>.value;
	var newPassword = form.<%= AppConstants.NEW_PASSWORD_STR %>.value;
	var rePassword = form.rePassword.value;
	var usernameErrorString = "Invalid Username Supplied " + username;
	if ( isEmpty(username) ) {
		alert(usernameErrorString);
		return false;
	} 
	if ( isEmpty(newPassword) ) {
		alert("Invalid Password Supplied " + newPassword);
		return false;
	}
	else {
		if (newPassword.length < 6) {
			alert("Password should be atleast 6 chars long");
			return false;
		}
	}

	if ( isEmpty(rePassword) ) {
		alert("Invalid Password Supplied for Reenter Password " + rePassword);
		return false;
	}
	else {
		if (rePassword.length < 6) {
			alert("Retype Password should be atleast 6 chars long");
			return false;
		}
	}

	if (newPassword !== rePassword) {
		alert("Passwords do not match");
		return false;
	}
	return true;
}

function validateForm(form) 
{
	return validateUserAndPassword(form);
}
</script>

<body>
<%@ include file="Navigation.jsp" %>
<form action=<%= Util.getBaseurl() %> method=post>
<% UsersBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_USERS_JSP_STR) %>
<%= UsersBean.getUsersInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
