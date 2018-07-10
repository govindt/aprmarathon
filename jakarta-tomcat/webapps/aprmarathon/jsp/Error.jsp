<!--
	@(#)Error.jsp	1.3 02/08/07

	Project Name Project

	Author: Govind Thirumalai

-->
<%@ page isErrorPage="true" language="java" import="java.io.*"%>

<title>AppServlet - Error</title>
<link href="/aprmarathon/jsp/css/main.css" rel="stylesheet" type="text/css" />

<HTML>
<BODY TEXT="#000000" LINK="#0000EE" VLINK="#551A8B" ALINK="#FF0000" BGCOLOR="#FFFFFF">
<BR><BR><BR>
<CENTER>
<%@ include file="Navigation.jsp" %>
<TABLE WIDTH="400" CELLSPACING=0 BORDER=3>
<TR>
	<TD BGCOLOR="#FFD700"><BR><CENTER><B><U>Application Error</U></B></CENTER><BR>
	<% String message = exception.toString(); 
	   try {
                message =  exception.getMessage();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            	exception.printStackTrace(new PrintWriter(outStream, true));
            	message += outStream.toString();
           } catch (NullPointerException npe) {
            	message =  null;
           }
	%>
	<%= message %>

    <BR><BR><BR></TD></TR>
       <TR><TD ALIGN="CENTER"><BR><BR>
    </CENTER><BR><BR></TD>
</TR>
</TABLE>
</CENTER>
<%@ include file="Footer.jsp"%>
</BODY>
</HTML>

