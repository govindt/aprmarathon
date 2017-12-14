/*
 *  @(#)LdapUtil.java	1.9 04/09/16 
 *
 *  Project Name Project
 *
 *  Author: Govind Thirumalai
 *
 */
import java.io.*;
import java.lang.*;
import java.text.*;
import java.util.*;
import netscape.ldap.*;
import netscape.ldap.factory.JSSESocketFactory;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.KeyStore;

public class LdapUtil {
	static public int port;
	static public int ssl_port;
	static public String server;
	static public String base;
	static public String dirmgr;
	static public String dirmgr_password;
	static public boolean is_ad = false;
	static public String ad_root_dn;
	static public String ad_ca_name;
	static public LDAPConnection ldapConnect = new LDAPConnection();
	static public LDAPConnection ldapSSLConnect;
	static public LdapUtil lu = null;
	static final String DESCRIPTION = "description";
	static final String PASSWORD = "unicodePwd";
	static final String UNICODE_LITTLE_UNMARKED = "UnicodeLittleUnmarked";
	static final String AD_CERT_BASEDN = "CN=Certification Authorities,CN=Public Key Services,CN=Services,cn=configuration,";
	static final String AD_CERT_ATTRIBUTE="cacertificate";
	static final String NEW_PASSWORD = "newPass";
	static boolean firstTime = true;

	public static boolean importCACert() {

		byte[] certArr = lu.getADCACertificate();
		String sep = System.getProperty("file.separator");
		String keyStoreFilePath = System.getProperty("java.home") + sep + "lib" + sep + "security" + sep + "cacerts" ;
		String alias = "adcacert";
		String pass = "changeit";
		char[] keyStorePassword = pass.toCharArray();
		
        InputStream inStream = new ByteArrayInputStream(certArr);
        try{
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
			// Create an empty keystore object - jks
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			// Load the keystore contents
            FileInputStream in = new FileInputStream(keyStoreFilePath);
            keystore.load(in, keyStorePassword);
            in.close();
			// Add the certificate
            keystore.setCertificateEntry(alias, cert);
            // Save the new keystore contents
            FileOutputStream out = new FileOutputStream(keyStoreFilePath);
            keystore.store(out, keyStorePassword);
            out.close();
			System.setProperty("javax.net.ssl.trustStore", keyStoreFilePath);
			System.setProperty("javax.net.ssl.trustStorePassword", pass);

        } catch(Exception e){
            System.out.println("Error occurred importing certificate: " +
                    e.getMessage());
            e.printStackTrace();
            return false;
        }
		return false;
    }


	public byte[] getADCACertificate() {
		LDAPSearchResults searchResults = null;
		byte[] stAttrVal = null;
		String baseDN = "CN=" + LdapUtil.ad_ca_name + "," + LdapUtil.AD_CERT_BASEDN + LdapUtil.ad_root_dn;
		String[] attrList = {AD_CERT_ATTRIBUTE};
		try {
			searchResults = ldapConnect.search(baseDN,
							   LDAPv3.SCOPE_SUB, "(" + AD_CERT_ATTRIBUTE + "=*)", attrList, false);
			
			while (searchResults.hasMoreElements()) {
				LDAPEntry entry = searchResults.next();
				LDAPAttributeSet attrs = entry.getAttributeSet();
				LDAPAttribute caCertAttr = attrs.getAttribute(AD_CERT_ATTRIBUTE);
				if (caCertAttr != null) {
					stAttrVal = caCertAttr.getByteValueArray()[0];
				}
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			System.out.println("Error getting CA cert - cannot continue: " +
							   e.getMessage());
		}
		return stAttrVal;
	}

	public String getDn(String start, int i) {
		return "cn=" + start + i + "," + LdapUtil.base;
	}

	public String getAuthDn(String start, int i) {
		if ( is_ad )
			return "cn=" + start + i + "," + LdapUtil.base;
		else
			return "uid=" + start + i + "," + LdapUtil.base;
		
	}
	private Properties search(String filter, String[] attrs) {

		Properties userProperties = new Properties();
		try {
			LDAPSearchResults res = ldapConnect.search( LdapUtil.base,
								    LDAPConnection.SCOPE_SUB,
								    filter, attrs, false );
			while ( res.hasMoreElements() ) {
				/* Get the next directory entry. */
				LDAPEntry findEntry = null;
				try {
					findEntry = res.next();
				} catch ( LDAPReferralException e ) {
					continue;
				} catch ( LDAPException e ) {
					continue;
				}
				LDAPAttributeSet findAttrs = findEntry.getAttributeSet();
				@SuppressWarnings("unchecked")
				Enumeration<LDAPAttribute> enumAttrs = (Enumeration<LDAPAttribute>)findAttrs.getAttributes();
				while ( enumAttrs.hasMoreElements()) {
					LDAPAttribute anAttr = enumAttrs.nextElement();
					String attrName = anAttr.getName();
					@SuppressWarnings("unchecked")
					Enumeration<String> enumVals = (Enumeration<String>)anAttr.getStringValues();
					if (enumVals != null) {
						String aVal = "";
						while ( enumVals.hasMoreElements() ) {
							aVal += enumVals.nextElement() + " ";
						}
						userProperties.put(attrName, aVal);					} 
				}
			}
		} catch (LDAPException le) {
		}
 		return userProperties;
	}

	public boolean IsModified(String start, int num) {
		int i = 0;
		String username = "";
		String attr[] = {DESCRIPTION};
		boolean retval = false;
		for (  i = 1; i <= num; i++ ) {
			Properties p = null;
			username = "cn=" + start + i;
			p = this.search(username , attr);
			if ( p != null ) {
				String desc_val = ((String)p.get(DESCRIPTION));
				if ( desc_val != null )
					desc_val = desc_val.trim();
				else
					desc_val = "";
				// May be a bug that we have extra space added on DS side
				retval = desc_val.equals ("Modified description for " + start  + i);
				if ( ! retval )
					return retval;
				else
					System.out.println("Modification of description successful for " + username);
			}
		}
		return retval;
	}

	public boolean IsPresent(String start, int num ) {
		boolean present = false;
		int i = 0;
		try {
			for (  i = 1; i <= num; i++ ) {
				LDAPEntry entry = ldapConnect.read(getDn(start, i));
				System.out.println("Found User : " + entry.getDN());
				present = true;
			}
		} catch (LDAPException e) {
			System.out.println("Error: " +  e.toString());
			e.printStackTrace();
			present = false;
		}
		return present;
	}

	public void Delete(String start, int  num) {
		int i = 0;
		String username = "";
		try {
			for (  i = 1; i <= num; i++ ) {
				username = lu.getDn(start, i);
				ldapConnect.delete(username);
				System.out.println("Deleted User : " + username);
			}
		} catch (LDAPException e) {
			if ( e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT) 
				System.out.println("User :" + username + " does  not exists");
			else if ( e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS) 
				System.out.println("Error: User :" + username + " Insufficient access rights ");
			else {
				System.out.println("Error: " +  e.toString());
				System.exit(1);
			}
		}
	}

	public String getUnicodePassword(String password) {
		password = "\"" + password + "\"";
		byte[] unicode = null;
		try {
			unicode = password.getBytes(UNICODE_LITTLE_UNMARKED);
			password = new String(unicode);
		}
		catch (UnsupportedEncodingException UnsuppoEx) {
			System.out.println("Error: " + UnsuppoEx.toString());
		}
		return password;
	}

	public void Auth(String start, int num, String value) {
		int i = 0;
		String username = "";
		try {
			for (  i = 1; i <= num; i++ ) {
				username=lu.getAuthDn(start, i);
				ldapConnect.authenticate(username, value + i);
				System.out.println("Authenticated User : " + username);
			}
		} catch (LDAPException e) {
			System.out.println( username + " NOT authenticated with password " + value + i);
			System.out.println("Error: " +  e.toString());
			System.exit(1);
		}
		
	}
	
	public void Modify(String start, int num, String modattr,  String value) {
		
		LDAPAttribute attr = null;
		int i = 0;
		String username = "";
		try {
			for (  i = 1; i <= num; i++ ) {
				LDAPModificationSet attrSet = new LDAPModificationSet();
				username=lu.getDn(start, i);
				if ( modattr.equals(PASSWORD) ) {
					attr = new LDAPAttribute(modattr, getUnicodePassword(value + i));
					attrSet.add(LDAPModification.REPLACE, attr);
					ldapConnect.modify(username, attrSet);
				} 
				else {
					attr = new LDAPAttribute(modattr, value + start + i);
					attrSet.add(LDAPModification.REPLACE, attr);
					ldapConnect.modify(username, attrSet);
				}

				System.out.println("Modified User : " + username);
			}
		} catch (LDAPException e) {
			if ( e.getLDAPResultCode() == LDAPException.NO_SUCH_OBJECT) 
				System.out.println("User :" + username + " does  not exists");
			else if ( e.getLDAPResultCode() == LDAPException.INSUFFICIENT_ACCESS_RIGHTS) 
				System.out.println("Error: User :" + username + i + " Insufficient access rights ");
			else {
				System.out.println("Error: " +  e.toString());
				System.exit(1);
			}
		}
		
	}

	public void Add(String start, int num) {
		
		LDAPAttribute attr = null;
		int i = 0;
		String username = "";
		try {
			for (  i = 1; i <= num; i++ ) {
				LDAPAttributeSet attrSet = new LDAPAttributeSet();
				String[] oc = new String[4];
				oc[0] = "top";
				oc[1] = "person";
				oc[2] = "organizationalPerson";

				if ( LdapUtil.is_ad )
					oc[3] = "user";
				else
					oc[3] = "inetorgPerson";
				String tmp = start + i;
				attr = new LDAPAttribute("objectclass", oc);
				attrSet.add(attr);
				attr = new LDAPAttribute("cn", tmp );
				attrSet.add(attr);
				attr = new LDAPAttribute("sn", tmp );
				attrSet.add(attr);
				if ( LdapUtil.is_ad ) {
					attr = new LDAPAttribute("samaccountname", tmp ); 
					attrSet.add(attr);
					attr = new LDAPAttribute("userAccountControl", "544");
					attrSet.add(attr);
				}
				else {
					attr = new LDAPAttribute("Description for ", tmp );
					attrSet.add(attr);
					attr = new LDAPAttribute("uid", tmp );
					attrSet.add(attr);


				}
				username = lu.getDn(start, i);
				LDAPEntry myEntry = new LDAPEntry(username, attrSet);
				System.out.println("Adding User : " + username);
				ldapConnect.add(myEntry);
				System.out.println("Added User : " + username);
			}
		} catch (LDAPException e) {
			if ( e.getLDAPResultCode() == LDAPException.ENTRY_ALREADY_EXISTS ) 
				System.out.println("User :" + username + " Already exists");
			else {
				e.printStackTrace();
				System.out.println("Error: " +  e.toString());
				System.exit(1);
			}
		}
	}

    public static void main(String argv[]) {
		lu = new LdapUtil();
		int num = 0;

		LdapUtil.server = System.getProperty("LDAP_SERVER");
		LdapUtil.base = System.getProperty("LDAP_BASE");
		LdapUtil.dirmgr = System.getProperty("LDAP_DIR_MGR");
		LdapUtil.dirmgr_password = System.getProperty("LDAP_DIR_MGR_PASSWORD");
		LdapUtil.ad_root_dn = System.getProperty("AD_ROOT_DN");
		LdapUtil.ad_ca_name = System.getProperty("AD_CA_NAME");
		try {
			LdapUtil.is_ad = (new Boolean(System.getProperty("IS_AD"))).booleanValue();
			if ( LdapUtil.is_ad ) {
				if ( LdapUtil.ad_root_dn == null ) {
					System.err.println("AD_ROOT_DN property not specified");
					System.exit(1);
				}
				if ( LdapUtil.ad_ca_name == null ) {
					System.err.println("AD_CA_NAME property not specified");
					System.exit(1);
				}
			}
		} catch (NumberFormatException npe) {
			System.err.println("IS_AD property not specified");
			System.exit(1);
		}
		if ( LdapUtil.server == null ) {
			System.err.println("LDAP_SERVER property not specified");
			System.exit(1);
		}
		if ( LdapUtil.base == null ) {
			System.err.println("LDAP_BASE property not specified");
			System.exit(1);
		}
		if ( LdapUtil.dirmgr == null ) {
			System.err.println("LDAP_DIR_MGR property not specified");
			System.exit(1);
		}
		if ( LdapUtil.dirmgr_password == null ) {
			System.err.println("LDAP_DIR_MGR_PASSWORD property not specified");
			System.exit(1);
		}
		
		if ( argv.length != 3 ) {
			System.err.println("Invalid Usage: LdapUtil <operation> <user_start_string> <num_users>");
			System.exit(1);
		}
		try {
			num = Integer.parseInt(argv[2]);
		}
		catch (NumberFormatException npe) {
			System.err.println("Invalid number arg passed: " + argv[2]);
			System.exit(1);
		}
		try {
			LdapUtil.port = Integer.parseInt(System.getProperty("LDAP_PORT"));
			LdapUtil.ssl_port = Integer.parseInt(System.getProperty("LDAP_SSL_PORT"));
		} catch (NumberFormatException npe) {
			System.err.println("Invalid Port: Specify valid port in LDAP_PORT/LDAP_SSL_PORT property");
			System.exit(1);
		}

		try {
			ldapConnect.connect(LdapUtil.server, LdapUtil.port);
			ldapConnect.authenticate(LdapUtil.dirmgr, LdapUtil.dirmgr_password);

			if ( argv[0].equalsIgnoreCase("add")) {
				lu.Add(argv[1], num);
			}
			else if ( argv[0].equalsIgnoreCase("modify")) {
				lu.Modify(argv[1], num, "description", "Modified description for ");
			}
			else if ( argv[0].equalsIgnoreCase("modifyPassword")) {
				if ( LdapUtil.firstTime ) {
					LdapUtil.importCACert();
					LdapUtil.firstTime = false;
					ldapSSLConnect = new LDAPConnection(new JSSESocketFactory(null));
					ldapSSLConnect.setOption(LDAPv2.PROTOCOL_VERSION, new Integer(3));
					ldapSSLConnect.connect(LdapUtil.server, LdapUtil.ssl_port);
					ldapSSLConnect.authenticate(LdapUtil.dirmgr, LdapUtil.dirmgr_password);
				}
				lu.Modify(argv[1], num, PASSWORD, NEW_PASSWORD);
			}
			else if ( argv[0].equalsIgnoreCase("authenticate")) {
				lu.Auth(argv[1], num, NEW_PASSWORD);
			}
			else if ( argv[0].equalsIgnoreCase("delete")) {
				lu.Delete(argv[1], num);
			}
			else if ( argv[0].equalsIgnoreCase("check")) {
				if ( lu.IsPresent(argv[1], num) )
					System.exit(0);
				else 
					System.exit(1);
			}
			else if ( argv[0].equalsIgnoreCase("search")) {
				Properties p = lu.search(argv[1], null);
				System.out.println(p);
				System.exit(0);

			}
			else if ( argv[0].equalsIgnoreCase("checkAdModify")) {
				if ( lu.IsModified(argv[1], num))
					System.exit(0);
				else
					System.exit(1);
			}
			else {
				System.out.println("Unsupported Operation " + argv[0]);
			}
		} catch (LDAPException e) {
			System.out.println("Error: " +  e.toString());
			e.printStackTrace();
			System.exit(1);
		}

	} 
}
