/*
 * @(#)App.java	1.30 04/08/20
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */



package app.util;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.*;
import core.util.*;
import org.apache.commons.lang.exception.*;

/*
 * Singletone class for the whole Application
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */


public class App {

    private static App 	self;
    private static String baseurl_;
    
    private static Properties siteProps = null;
    private static Vector<String> loginRequiredUrl = new Vector<String>();
    
    private App() throws AppException {
		// Change these later to get from Resource File as Properties
		siteProps = new Properties();	
    }
    
    
    public static App getInstance() throws AppException {
		if ( self == null ) {
			self = new App();
			loadSiteProperties();
			Util util = Util.getInstance();
		}
	
		if ( self == null )
			throw new AppException();
	
		return self;
    }
    
    private static Properties loadSiteProperties() throws AppException {
		String sitePropertiesFile
			= "app.util.app";
		ResourceBundle rb = ResourceBundle.getBundle(sitePropertiesFile);
		Set<String> set = rb.keySet();
		Iterator<String> iterator = set.iterator();
		while ( iterator.hasNext()) {
			String key = iterator.next();
			siteProps.put(key, rb.getString(key));
		} 
		baseurl_ = siteProps.getProperty("site.baseurl");
		AppConstants.LOGIN_STR = siteProps.getProperty("app.login_str");
		AppConstants.LOGIN_REQUIRED_URLS = siteProps.getProperty("app.login_required_urls");
		StringTokenizer st = new StringTokenizer(AppConstants.LOGIN_REQUIRED_URLS);
		while (st.hasMoreTokens()) {
			String url = st.nextToken();
			if ( url != null )
			loginRequiredUrl.addElement(url);
		}
		AppConstants.LOGOUT_STR = siteProps.getProperty("app.logout_str");
		AppConstants.REGISTRATION_LABEL = siteProps.getProperty("app.registration_label");
		AppConstants.RESET_PASSWORD_LABEL = siteProps.getProperty("app.reset_password_label");
		AppConstants.USER_ID_LABEL = siteProps.getProperty("app.user_id_label");
		AppConstants.PASSWORD_LABEL = siteProps.getProperty("app.password_label");
		AppConstants.CURRENT_USERS_LABEL = siteProps.getProperty("app.current_users_label");
		AppConstants.CURRENT_ROLE_LABEL = siteProps.getProperty("app.current_role_label");
		AppConstants.NEW_PASSWORD_LABEL = siteProps.getProperty("app.new_password_label");
		AppConstants.RETYPE_PASSWORD_LABEL = siteProps.getProperty("app.retype_password_label");
		AppConstants.NEW_USER = siteProps.getProperty("app.new_user"); 
		AppConstants.EMAIL_LABEL = siteProps.getProperty("app.email_label"); 
		AppConstants.EMAIL_SUBJECT = siteProps.getProperty("app.email_subject"); 
		AppConstants.EMAIL_BODY_HEADER = siteProps.getProperty("app.email_body_header");
		AppConstants.EMAIL_BODY_FOOTER = siteProps.getProperty("app.email_body_footer");  
		AppConstants.SECURITY_MANAGEMENT_LABEL = siteProps.getProperty("app.security_management_label");  
		AppConstants.HOME_LABEL = siteProps.getProperty("app.home_label");  
		AppConstants.JSP_BASE = siteProps.getProperty("app.jsp_base");  
		AppConstants.CURRENT_MENU_LABEL = siteProps.getProperty("app.current_menu_label");
		AppConstants.NEW_MENU = siteProps.getProperty("app.new_menu");
		AppConstants.MENU_ID_LABEL = siteProps.getProperty("app.menu_id_label");
		AppConstants.MENU_NAME_LABEL = siteProps.getProperty("app.menu_name_label");
		AppConstants.URL_LABEL = siteProps.getProperty("app.url_label");
		AppConstants.MENU_ORDER_LABEL = siteProps.getProperty("app.menu_order_label");
		AppConstants.PARENT_MENU_ID_LABEL = siteProps.getProperty("app.parent_menu_id_label");
		AppConstants.NEW_PARENT_MENU = siteProps.getProperty("app.new_parent_menu");

		AppConstants.PERMISSION_LABEL = siteProps.getProperty("app.permisson_label");
		AppConstants.CURRENT_ACL_LABEL = siteProps.getProperty("app.current_acl_label");
		AppConstants.NEW_ACL = siteProps.getProperty("app.new_acl");
		AppConstants.ACL_ID_LABEL = siteProps.getProperty("app.acl_id_label");
		AppConstants.ACL_PAGE_LABEL = siteProps.getProperty("app.acl_page_label");
		AppConstants.ACCESS_LABEL = siteProps.getProperty("app.access_label");
		AppConstants.NEW_ROLE = siteProps.getProperty("app.new_role");
		AppConstants.ROLE_ID_LABEL = siteProps.getProperty("app.role_id_label");
		AppConstants.ROLE_NAME_LABEL = siteProps.getProperty("app.role_name_label");
		AppConstants.IS_VALID_LABEL = siteProps.getProperty("app.is_valid_label");

		AppConstants.POST_URL = siteProps.getProperty("app.post_url");
		AppConstants.DB_TYPE = siteProps.getProperty("app.dbtype");
		AppConstants.DB_OPERATION_LABEL = siteProps.getProperty("app.dboperation_label");
		
		AppConstants.SMTP_HOST = siteProps.getProperty("app.smtp_host");
		AppConstants.SMTP_DEBUG = Boolean.parseBoolean(siteProps.getProperty("app.smtp_debug"));
		AppConstants.EMAIL_FROM = siteProps.getProperty("app.email_from");
		AppConstants.EMAIL_USERNAMES = siteProps.getProperty("app.email_usernames");
		AppConstants.RECEIPT_NO_PREFIX = siteProps.getProperty("app.receipt_no_prefix");
		AppConstants.RECEIPT_TEMPLATE = siteProps.getProperty("app.receipt_template");
		AppConstants.RECEIPT_MAIL_BODY_TEMPLATE = siteProps.getProperty("app.receipt_mail_body_template");
		AppConstants.RECEIPT_MAIL_SUBJECT = siteProps.getProperty("app.receipt_mail_subject");
		

		// INSERT GENERATED CODE
		AppConstants.MIN_AGE_LABEL = siteProps.getProperty("app.min_age_label");
		AppConstants.MAX_AGE_LABEL = siteProps.getProperty("app.max_age_label");
		AppConstants.CURRENT_SITE_LABEL = siteProps.getProperty("app.current_site_label");
		AppConstants.NEW_SITE = siteProps.getProperty("app.new_site");
		AppConstants.SITE_ID_LABEL = siteProps.getProperty("app.site_id_label");
		AppConstants.SITE_NAME_LABEL = siteProps.getProperty("app.site_name_label");
		AppConstants.SITE_URL_LABEL = siteProps.getProperty("app.site_url_label");
		AppConstants.ONLINE_REGISTRATION_ONLY_LABEL = siteProps.getProperty("app.online_registration_only_label");
		AppConstants.REGISTRANT_PHONE_LABEL = siteProps.getProperty("app.registrant_phone_label");
		AppConstants.CURRENT_REGISTRANTEVENT_LABEL = siteProps.getProperty("app.current_registrantevent_label");
		AppConstants.NEW_REGISTRANTEVENT = siteProps.getProperty("app.new_registrantevent");
		AppConstants.REGISTRANT_EVENT_ID_LABEL = siteProps.getProperty("app.registrant_event_id_label");
		AppConstants.REGISTRANT_EMERGENCY_CONTACT_LABEL = siteProps.getProperty("app.registrant_emergency_contact_label");
		AppConstants.REGISTRANT_EMERGENCY_PHONE_LABEL = siteProps.getProperty("app.registrant_emergency_phone_label");
		AppConstants.PAYMENT_REFERENCE_ID_LABEL = siteProps.getProperty("app.payment_reference_id_label");
		AppConstants.PAYMENT_TAX_LABEL = siteProps.getProperty("app.payment_tax_label");
		AppConstants.PAYMENT_FEE_LABEL = siteProps.getProperty("app.payment_fee_label");
		AppConstants.CURRENT_PARTICIPANTEVENT_LABEL = siteProps.getProperty("app.current_participantevent_label");
		AppConstants.NEW_PARTICIPANTEVENT = siteProps.getProperty("app.new_participantevent");
		AppConstants.PARTICIPANT_EVENT_ID_LABEL = siteProps.getProperty("app.participant_event_id_label");
		AppConstants.REGISTRANT_ADDITIONAL_EMAIL_LABEL = siteProps.getProperty("app.registrant_additional_email_label");
		AppConstants.REGISTRANT_MIDDLE_NAME_LABEL = siteProps.getProperty("app.registrant_middle_name_label");
		AppConstants.REGISTRANT_LAST_NAME_LABEL = siteProps.getProperty("app.registrant_last_name_label");
		AppConstants.RESULT_WINNER_REGISTRANT_LABEL = siteProps.getProperty("app.result_winner_registrant_label");
		AppConstants.BENEFICIARY_ID_LABEL = siteProps.getProperty("app.beneficiary_id_label");
		AppConstants.BENEFICIARY_NAME_LABEL = siteProps.getProperty("app.beneficiary_name_label");
		AppConstants.CURRENT_EVENT_LABEL = siteProps.getProperty("app.current_event_label");
		AppConstants.NEW_EVENT = siteProps.getProperty("app.new_event");
		AppConstants.EVENT_ID_LABEL = siteProps.getProperty("app.event_id_label");
		AppConstants.EVENT_NAME_LABEL = siteProps.getProperty("app.event_name_label");
		AppConstants.EVENT_START_DATE_LABEL = siteProps.getProperty("app.event_start_date_label");
		AppConstants.EVENT_END_DATE_LABEL = siteProps.getProperty("app.event_end_date_label");
		AppConstants.EVENT_DESCRIPTION_LABEL = siteProps.getProperty("app.event_description_label");
		AppConstants.EVENT_REGISTATION_CLOSE_DATE_LABEL = siteProps.getProperty("app.event_registation_close_date_label");
		AppConstants.EVENT_CHANGES_CLOSE_DATE_LABEL = siteProps.getProperty("app.event_changes_close_date_label");
		AppConstants.CURRENT_EVENTTYPE_LABEL = siteProps.getProperty("app.current_eventtype_label");
		AppConstants.NEW_EVENTTYPE = siteProps.getProperty("app.new_eventtype");
		AppConstants.EVENT_TYPE_ID_LABEL = siteProps.getProperty("app.event_type_id_label");
		AppConstants.EVENT_LABEL = siteProps.getProperty("app.event_label");
		AppConstants.EVENT_TYPE_NAME_LABEL = siteProps.getProperty("app.event_type_name_label");
		AppConstants.EVENT_TYPE_DESCRIPTION_LABEL = siteProps.getProperty("app.event_type_description_label");
		AppConstants.EVENT_TYPE_START_DATE_LABEL = siteProps.getProperty("app.event_type_start_date_label");
		AppConstants.EVENT_TYPE_END_DATE_LABEL = siteProps.getProperty("app.event_type_end_date_label");
		AppConstants.EVENT_TYPE_VENUE_LABEL = siteProps.getProperty("app.event_type_venue_label");
		AppConstants.CURRENT_REGISTRATIONTYPE_LABEL = siteProps.getProperty("app.current_registrationtype_label");
		AppConstants.NEW_REGISTRATIONTYPE = siteProps.getProperty("app.new_registrationtype");
		AppConstants.REGISTRATION_TYPE_ID_LABEL = siteProps.getProperty("app.registration_type_id_label");
		AppConstants.REGISTRATION_TYPE_NAME_LABEL = siteProps.getProperty("app.registration_type_name_label");
		AppConstants.CURRENT_REGISTRATIONCLASS_LABEL = siteProps.getProperty("app.current_registrationclass_label");
		AppConstants.NEW_REGISTRATIONCLASS = siteProps.getProperty("app.new_registrationclass");
		AppConstants.REGISTRATION_CLASS_ID_LABEL = siteProps.getProperty("app.registration_class_id_label");
		AppConstants.REGISTRATION_CLASS_NAME_LABEL = siteProps.getProperty("app.registration_class_name_label");
		AppConstants.REGISTRATION_TYPE_LABEL = siteProps.getProperty("app.registration_type_label");
		AppConstants.REGISTRATION_EVENT_LABEL = siteProps.getProperty("app.registration_event_label");
		AppConstants.REGISTRATION_CLASS_VALUE_LABEL = siteProps.getProperty("app.registration_class_value_label");
		AppConstants.REGISTRATION_FREE_TICKETS_LABEL = siteProps.getProperty("app.registration_free_tickets_label");
		AppConstants.CURRENT_REGISTRATIONSOURCE_LABEL = siteProps.getProperty("app.current_registrationsource_label");
		AppConstants.NEW_REGISTRATIONSOURCE = siteProps.getProperty("app.new_registrationsource");
		AppConstants.REGISTRATION_SOURCE_ID_LABEL = siteProps.getProperty("app.registration_source_id_label");
		AppConstants.REGISTRATION_SOURCE_NAME_LABEL = siteProps.getProperty("app.registration_source_name_label");
		AppConstants.CURRENT_BENEFICIARY_LABEL = siteProps.getProperty("app.current_beneficiary_label");
		AppConstants.NEW_BENEFICIARY = siteProps.getProperty("app.new_beneficiary");
		AppConstants.BENEFECIARY_ID_LABEL = siteProps.getProperty("app.benefeciary_id_label");
		AppConstants.BENEFECIARY_NAME_LABEL = siteProps.getProperty("app.benefeciary_name_label");
		AppConstants.BENEFICIARY_EVENT_LABEL = siteProps.getProperty("app.beneficiary_event_label");
		AppConstants.CURRENT_GENDER_LABEL = siteProps.getProperty("app.current_gender_label");
		AppConstants.NEW_GENDER = siteProps.getProperty("app.new_gender");
		AppConstants.GENDER_ID_LABEL = siteProps.getProperty("app.gender_id_label");
		AppConstants.GENDER_NAME_LABEL = siteProps.getProperty("app.gender_name_label");
		AppConstants.CURRENT_AGECATEGORY_LABEL = siteProps.getProperty("app.current_agecategory_label");
		AppConstants.NEW_AGECATEGORY = siteProps.getProperty("app.new_agecategory");
		AppConstants.AGE_CATEGORY_ID_LABEL = siteProps.getProperty("app.age_category_id_label");
		AppConstants.AGE_CATEGORY_LABEL = siteProps.getProperty("app.age_category_label");
		AppConstants.CURRENT_TSHIRTSIZE_LABEL = siteProps.getProperty("app.current_tshirtsize_label");
		AppConstants.NEW_TSHIRTSIZE = siteProps.getProperty("app.new_tshirtsize");
		AppConstants.T_SHIRT_SIZE_ID_LABEL = siteProps.getProperty("app.t_shirt_size_id_label");
		AppConstants.T_SHIRT_GENDER_LABEL = siteProps.getProperty("app.t_shirt_gender_label");
		AppConstants.T_SHIRT_SIZE_NAME_LABEL = siteProps.getProperty("app.t_shirt_size_name_label");
		AppConstants.CURRENT_BLOODGROUP_LABEL = siteProps.getProperty("app.current_bloodgroup_label");
		AppConstants.NEW_BLOODGROUP = siteProps.getProperty("app.new_bloodgroup");
		AppConstants.BLOOD_GROUP_ID_LABEL = siteProps.getProperty("app.blood_group_id_label");
		AppConstants.BLOOD_GROUP_NAME_LABEL = siteProps.getProperty("app.blood_group_name_label");
		AppConstants.CURRENT_PAYMENTTYPE_LABEL = siteProps.getProperty("app.current_paymenttype_label");
		AppConstants.NEW_PAYMENTTYPE = siteProps.getProperty("app.new_paymenttype");
		AppConstants.PAYMENT_TYPE_ID_LABEL = siteProps.getProperty("app.payment_type_id_label");
		AppConstants.PAYMENT_TYPE_NAME_LABEL = siteProps.getProperty("app.payment_type_name_label");
		AppConstants.CURRENT_PAYMENTSTATUS_LABEL = siteProps.getProperty("app.current_paymentstatus_label");
		AppConstants.NEW_PAYMENTSTATUS = siteProps.getProperty("app.new_paymentstatus");
		AppConstants.REGISTRANT_EVENT_LABEL = siteProps.getProperty("app.registrant_event_label");
		AppConstants.PAYMENT_STATUS_ID_LABEL = siteProps.getProperty("app.payment_status_id_label");
		AppConstants.PAYMENT_STATUS_NAME_LABEL = siteProps.getProperty("app.payment_status_name_label");
		AppConstants.CURRENT_REGISTRANT_LABEL = siteProps.getProperty("app.current_registrant_label");
		AppConstants.NEW_REGISTRANT = siteProps.getProperty("app.new_registrant");
		AppConstants.REGISTRANT_ID_LABEL = siteProps.getProperty("app.registrant_id_label");
		AppConstants.REGISTRANT_NAME_LABEL = siteProps.getProperty("app.registrant_name_label");
		AppConstants.REGISTRANT_EVENT_LABEL = siteProps.getProperty("app.registrant_event_label");
		AppConstants.REGISTRANT_TYPE_LABEL = siteProps.getProperty("app.registrant_type_label");
		AppConstants.REGISTRANT_SOURCE_LABEL = siteProps.getProperty("app.registrant_source_label");
		AppConstants.REGISTRANT_CLASS_LABEL = siteProps.getProperty("app.registrant_class_label");
		AppConstants.REGISTRANT_BENEFICIARY_LABEL = siteProps.getProperty("app.registrant_beneficiary_label");
		AppConstants.REGISTRANT_EMAIL_LABEL = siteProps.getProperty("app.registrant_email_label");
		AppConstants.REGISTRANT_PHONE_NUMBER_LABEL = siteProps.getProperty("app.registrant_phone_number_label");
		AppConstants.REGISTRANT_ADDRESS_LABEL = siteProps.getProperty("app.registrant_address_label");
		AppConstants.REGISTRANT_CITY_LABEL = siteProps.getProperty("app.registrant_city_label");
		AppConstants.REGISTRANT_STATE_LABEL = siteProps.getProperty("app.registrant_state_label");
		AppConstants.REGISTRANT_PINCODE_LABEL = siteProps.getProperty("app.registrant_pincode_label");
		AppConstants.REGISTRANT_PAN_LABEL = siteProps.getProperty("app.registrant_pan_label");
		AppConstants.CURRENT_PARTICIPANT_LABEL = siteProps.getProperty("app.current_participant_label");
		AppConstants.NEW_PARTICIPANT = siteProps.getProperty("app.new_participant");
		AppConstants.PARTICIPANT_ID_LABEL = siteProps.getProperty("app.participant_id_label");
		AppConstants.PARTICIPANT_FIRST_NAME_LABEL = siteProps.getProperty("app.participant_first_name_label");
		AppConstants.PARTICIPANT_MIDDLE_NAME_LABEL = siteProps.getProperty("app.participant_middle_name_label");
		AppConstants.PARTICIPANT_LAST_NAME_LABEL = siteProps.getProperty("app.participant_last_name_label");
		AppConstants.PARTICIPANT_EVENT_LABEL = siteProps.getProperty("app.participant_event_label");
		AppConstants.PARTICIPANT_TYPE_LABEL = siteProps.getProperty("app.participant_type_label");
		AppConstants.PARTICIPANT_EVENT_TYPE_LABEL = siteProps.getProperty("app.participant_event_type_label");
		AppConstants.PARTICIPANT_GENDER_LABEL = siteProps.getProperty("app.participant_gender_label");
		AppConstants.PARTICIPANT_DATE_OF_BIRTH_LABEL = siteProps.getProperty("app.participant_date_of_birth_label");
		AppConstants.PARTICIPANT_AGE_CATEGORY_LABEL = siteProps.getProperty("app.participant_age_category_label");
		AppConstants.PARTICIPANT_T_SHIRT_SIZE_LABEL = siteProps.getProperty("app.participant_t_shirt_size_label");
		AppConstants.PARTICIPANT_BLOOD_GROUP_LABEL = siteProps.getProperty("app.participant_blood_group_label");
		AppConstants.PARTICIPANT_CELL_PHONE_LABEL = siteProps.getProperty("app.participant_cell_phone_label");
		AppConstants.PARTICIPANT_EMAIL_LABEL = siteProps.getProperty("app.participant_email_label");
		AppConstants.PARTICIPANT_EMERGENCY_CONTACT_LABEL = siteProps.getProperty("app.participant_emergency_contact_label");
		AppConstants.PARTICIPANT_EMERGENCY_PHONE_LABEL = siteProps.getProperty("app.participant_emergency_phone_label");
		AppConstants.PARTICIPANT_BIB_NO_LABEL = siteProps.getProperty("app.participant_bib_no_label");
		AppConstants.PARTICIPANT_GROUP_LABEL = siteProps.getProperty("app.participant_group_label");
		AppConstants.CURRENT_REGISTRANTPAYMENT_LABEL = siteProps.getProperty("app.current_registrantpayment_label");
		AppConstants.NEW_REGISTRANTPAYMENT = siteProps.getProperty("app.new_registrantpayment");
		AppConstants.REGISTRANT_PAYMENT_ID_LABEL = siteProps.getProperty("app.registrant_payment_id_label");
		AppConstants.REGISTRANT_EVENT_LABEL = siteProps.getProperty("app.registrant_event_label");
		AppConstants.REGISTRANT_LABEL = siteProps.getProperty("app.registrant_label");
		AppConstants.PAYMENT_TYPE_LABEL = siteProps.getProperty("app.payment_type_label");
		AppConstants.PAYMENT_STATUS_LABEL = siteProps.getProperty("app.payment_status_label");
		AppConstants.PAYMENT_AMOUNT_LABEL = siteProps.getProperty("app.payment_amount_label");
		AppConstants.PAYMENT_ADDITIONAL_AMOUNT_LABEL = siteProps.getProperty("app.payment_additional_amount_label");
		AppConstants.PAYMENT_DATE_LABEL = siteProps.getProperty("app.payment_date_label");
		AppConstants.RECEIPT_DATE_LABEL = siteProps.getProperty("app.receipt_date_label");
		AppConstants.PAYMENT_DETAILS_LABEL = siteProps.getProperty("app.payment_details_label");
		AppConstants.PAYMENT_TOWARDS_LABEL = siteProps.getProperty("app.payment_towards_label");
		AppConstants.CURRENT_MEDAL_LABEL = siteProps.getProperty("app.current_medal_label");
		AppConstants.NEW_MEDAL = siteProps.getProperty("app.new_medal");
		AppConstants.MEDAL_ID_LABEL = siteProps.getProperty("app.medal_id_label");
		AppConstants.MEDAL_NAME_LABEL = siteProps.getProperty("app.medal_name_label");
		AppConstants.MEDAL_RANK_LABEL = siteProps.getProperty("app.medal_rank_label");
		AppConstants.CURRENT_RESULT_LABEL = siteProps.getProperty("app.current_result_label");
		AppConstants.NEW_RESULT = siteProps.getProperty("app.new_result");
		AppConstants.RESULT_ID_LABEL = siteProps.getProperty("app.result_id_label");
		AppConstants.RESULT_EVENT_LABEL = siteProps.getProperty("app.result_event_label");
		AppConstants.RESULT_EVENT_TYPE_LABEL = siteProps.getProperty("app.result_event_type_label");
		AppConstants.RESULT_MEDAL_LABEL = siteProps.getProperty("app.result_medal_label");
		AppConstants.RESULT_WINNER_LABEL = siteProps.getProperty("app.result_winner_label");
		AppConstants.RESULT_WINNER_REGISTRRANT_LABEL = siteProps.getProperty("app.result_winner_registrrant_label");
		AppConstants.RESULT_SCORE_LABEL = siteProps.getProperty("app.result_score_label");
		AppConstants.RESULT_TIMING_LABEL = siteProps.getProperty("app.result_timing_label");
		
		return siteProps;
    }
    
    public static boolean isLoginRequired(String url) {
		DebugHandler.debug("isLoginRequired:URL: " + url);
		if (url == null)
			return false;
		for (int i = 0; i < App.loginRequiredUrl.size(); i++) {
			String vurl = App.loginRequiredUrl.elementAt(i);
			DebugHandler.debug("VURL: " + vurl);
			if (vurl.equals(url))
				return true;
		}
		return false;
    }
    
    public static String getSiteProperty (String varKey)
    {
		return (siteProps.getProperty (varKey));
    }
}
