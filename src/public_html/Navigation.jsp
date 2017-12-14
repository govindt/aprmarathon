<!--
	@(#)@(#)Navigation.jsp	1.1 04/03/12

	Project Name Project

	Author: Govind Thirumalai

-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		
<%@ page import="app.appui.AppUtilBean" language="java" import="core.util.Util, app.menu.AppMenuBuilder" contentType="text/html" %>
  <jsp:useBean id="AppUtilBean" scope="session" class="app.appui.AppUtilBean"/>
    <jsp:setProperty name="AppUtilBean" property="*"/>

      <HTML>
	<%@ include file="Header.jsp" %>
	<table border=0 cellpadding=0 cellspacing=0 width=100% >
	<tr>
	<td width="100%" bgcolor="#000000"><img src="/aprmarathon/jsp/images/t.gif" width=5 height=5 border=0</td>
	</tr>
        <tr>
	<td><% String buf = "";
	      AppUtilBean aub = new AppUtilBean();
	      int usersId = aub.getLoggedUserId(request);
	      AppMenuBuilder amb = new AppMenuBuilder(usersId); 
	      buf = amb.renderMenu();
	      %> 
	      
	      <%= buf %>
	</td>
        </tr>
        </table>
	<table>
	  <tr>
	    <td><img src="/aprmarathon/jsp/images/t.gif" height="20">
	    </td>
	  </tr>
	</table>
	<b><%= AppUtilBean.getLoggedUsername(request) %></b>

	<br>
	</CENTER>
      </HTML>

