<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page errorPage="/jsp/Error.jsp" language="java" import="app.appui.RegistrantPaymentBean, core.util.Constants,  core.util.Util, app.util.AppConstants" %>
<jsp:useBean id="RegistrantPaymentBean" scope="session" class="app.appui.RegistrantPaymentBean"/>
<jsp:setProperty name="RegistrantPaymentBean" property="*"/>
<html>
<head>
  <title>Manage RegistrantPayments</title>
  <meta http-equiv="content-type"
 content="text/html; charset=ISO-8859-1">
  <meta name="author" content=" Govind Thirumalai">
<script>
<%@ include file="js/validation.js" %>
<%@ include file="js/common_utils.js" %>
function validateForm(form) 
{
	var tmp = form.<%= AppConstants.PAYMENT_AMOUNT_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_AMOUNT_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.PAYMENT_ADDITIONAL_AMOUNT_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_ADDITIONAL_AMOUNT_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.PAYMENT_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.RECEIPT_DATE_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.RECEIPT_DATE_LABEL %> cannot be empty");
		return false;
	}
	var tmp = form.<%= AppConstants.PAYMENT_DETAILS_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_DETAILS_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 200 ) {
		alert("<%= AppConstants.PAYMENT_DETAILS_LABEL %> length cannot be greater than 200");
		return false;
	}
	var tmp = form.<%= AppConstants.PAYMENT_TOWARDS_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_TOWARDS_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 100 ) {
		alert("<%= AppConstants.PAYMENT_TOWARDS_LABEL %> length cannot be greater than 100");
		return false;
	}
	var tmp = form.<%= AppConstants.PAYMENT_REFERENCE_ID_STR%>.value;
	if ( isEmpty(tmp) ) {
		alert("<%= AppConstants.PAYMENT_REFERENCE_ID_LABEL %> cannot be empty");
		return false;
	}
	if ( tmp.length > 100 ) {
		alert("<%= AppConstants.PAYMENT_REFERENCE_ID_LABEL %> length cannot be greater than 100");
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
<% RegistrantPaymentBean.getRequestParameters(request); %>
<%= core.appui.UtilBean.getSaveProfileFlag() %>
<%= core.appui.UtilBean.getDownloadFlag() %>
<%= core.appui.UtilBean.getHiddenField(Constants.SS_IMPL_NAME_STR, AppConstants.REGISTRANTPAYMENT_BEAN_NAME_STR) %>
<%= core.appui.UtilBean.getNextJsp(AppConstants.MANAGE_REGISTRANTPAYMENT_JSP_STR) %>
<%= RegistrantPaymentBean.getRegistrantPaymentInfo() %>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
