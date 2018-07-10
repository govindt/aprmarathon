<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.ParticipantBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="ParticipantBean" scope="session" class="app.appui.ParticipantBean"/>
<jsp:setProperty name="ParticipantBean" property="*"/>
<html>
<head>
  <title>Manage Participants</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.PARTICIPANT_FIRST_NAME_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PARTICIPANT_FIRST_NAME_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 50 ) {
		alert("<%= AppConstants.PARTICIPANT_FIRST_NAME_LABEL %> length cannot be greater than 50");
		return false;
	}
	var tmp = form.<%= AppConstants.PARTICIPANT_CELL_PHONE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PARTICIPANT_CELL_PHONE_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 20 ) {
		alert("<%= AppConstants.PARTICIPANT_CELL_PHONE_LABEL %> length cannot be greater than 20");
		return false;
	}
	var tmp = form.<%= AppConstants.PARTICIPANT_EMAIL_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PARTICIPANT_EMAIL_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 75 ) {
		alert("<%= AppConstants.PARTICIPANT_EMAIL_LABEL %> length cannot be greater than 75");
		return false;
	}
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
<% ParticipantBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.PARTICIPANT_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_PARTICIPANT_JSP_STR) %>
<%= ParticipantBean.getParticipantInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
