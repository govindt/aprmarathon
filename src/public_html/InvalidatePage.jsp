<!--
	@(#)InvalidatePage.jsp	1.3 02/08/07

	Project Name Project

	Author: Govind Thirumalai

-->
<%@ page errorPage="Error.jsp" language="java" import="core.appui.InvalidatePage" %>
<jsp:useBean id="InvalidatePage" scope="session" class="core.appui.InvalidatePage"/>
<jsp:setProperty name="InvalidatePage" property="*"/>
<title>AppServlet - Invalid Page</title>
<link href="/aprmarathon/jsp/main.css" rel="stylesheet" type="text/css" />

<% InvalidatePage.logoutNow(request,response); %>

<HTML>
<BODY TEXT="#000000" LINK="#0000EE" VLINK="#551A8B" ALINK="#FF0000" BGCOLOR="#FFFFFF">
<BR><BR><BR>
<CENTER>
<TABLE WIDTH="400" CELLSPACING=0 BORDER=3>
<TR>
    <TD BGCOLOR="#FFD700"><BR><CENTER><B><U>Session Error.</U></B></CENTER><BR>
    Your working session is no longer valid due to either 
    Your Session timed out which occurs after 1 Hour of Non-Usage
    OR
    Server was Restarted 
    OR
    a problem with your environment.
    Thank you.
    <BR><BR><BR></TD></TR>
       <TR><TD ALIGN="CENTER"><BR><BR>
    </CENTER><BR><BR></TD>
</TR>
</TABLE>
</CENTER>
</BODY>
</HTML>

