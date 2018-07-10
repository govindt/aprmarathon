<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ page errorPage="Error.jsp" language="java" import="core.appui.UtilBean, app.appui.AppUtilBean, core.util.Constants" %>
<jsp:useBean id="UtilBean" scope="session" class="core.appui.UtilBean"/>
<jsp:setProperty name="UtilBean" property="*"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<title>AppServlet - Logout</title>
<link href="/aprmarathon/jsp/css/main.css" rel="stylesheet" type="text/css" />

<body text="#000000" bgcolor="#FFFFFF" link="#0000EE" vlink="#551A8B" alink="#FF0000">

<br>
<br>
<% UtilBean.logout(request); %>
<%@ include file="Header.jsp" %>
<table>
<tr>
<td><img src="/aprmarathon/jsp/images/t.gif" height="70">
</td>
</tr>
</table>
<center>
	<table border=0>
	<tbody>
		<tr width="100%" bgcolor="#F4F2F2">
			<td><b>Successfully Logged Out</td>
		</tr>
		<tr width="100%" bgcolor="#F4F2F2">
			<td>Click to <%= AppUtilBean.getLoginUrl() %></td>
		</tr>
	</tbody>
	</table>
</center>
<br> <table>
<tr>
<td><img src="/aprmarathon/jsp/images/t.gif" height="170">
</td>
</tr>
</table>
<%@ include file="Footer.jsp" %>
</body>
</html>
