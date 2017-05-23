package com.tera.generater.util;

import java.util.ResourceBundle;

public class ResourceUtil {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("generater");
	
	public static String DIVER_NAME = "com.mysql.jdbc.Driver";
	public static String URL = "jdbc:mysql://localhost:3306/hd?useUnicode=true&characterEncoding=UTF-8";
	public static String USERNAME = "root";
	public static String PASSWORD = "root";
	//数据库名
	public static String DATABASE_NAME = "hd";
	public static String DATABASE_TYPE = "mysql";
	public static String DATABASE_TYPE_MYSQL = "mysql";
	public static String DATABASE_TYPE_ORACLE = "oracle";
	public static String source_root_package = "src";
	public static String bizzPackage = "sun";
	public static String bizzPackageUrl = "sun";
	public static String entity_package = "entity";
	public static String page_package = "page";
	public static String SQLTables="";

	static {
		DIVER_NAME = getDIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();
    
		SYSTEM_ENCODING = getSYSTEM_ENCODING();
		TEMPLATEPATH = getTEMPLATEPATH();
		source_root_package = getSourceRootPackage();
		web_root_package = getWebRootPackage();
		bizzPackage = getBizzPackage();
		bizzPackageUrl = bizzPackage.replace(".", "/");
		SQLTables = getSQLTables();
		
		if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0)) {
			DATABASE_TYPE = DATABASE_TYPE_MYSQL;
		} else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
			DATABASE_TYPE = DATABASE_TYPE_ORACLE;
		}
		source_root_package = source_root_package.replace(".", "/");
	}
  
	public static String web_root_package = getWebRootPackage().replace(".", "/");
	public static String ENTITY_URL = source_root_package + "/" + bizzPackageUrl + "/" + entity_package + "/";
	public static String PAGE_URL = source_root_package + "/" + bizzPackageUrl + "/" + page_package + "/";
	public static String ENTITY_URL_INX = bizzPackage + "." + entity_package + ".";
	public static String PAGE_URL_INX = bizzPackage + "." + page_package + ".";
	public static String CODEPATH = source_root_package + "/" + bizzPackageUrl + "/";
	public static String JSPPATH = web_root_package + "/" + bizzPackageUrl + "/";
	public static String TEMPLATEPATH;
	public static String SYSTEM_ENCODING;

	private void ResourceUtil() {
	}

	public static final String getDIVER_NAME() {
		return bundle.getString("diver_name");
	}

	public static final String getURL() {
		return bundle.getString("url");
	}

	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}

	private static String getBizzPackage() {
		return bundle.getString("bizz_package");
	}

	public static final String getEntityPackage() {
		return bundle.getString("entity_package");
	}

	public static final String getPagePackage() {
		return bundle.getString("page_package");
	}

	public static final String getTEMPLATEPATH() {
		return bundle.getString("templatepath");
	}

	public static final String getSourceRootPackage() {
		return bundle.getString("source_root_package");
	}

	public static final String getWebRootPackage() {
		return bundle.getString("webroot_package");
	}

	public static final String getSYSTEM_ENCODING() {
		return bundle.getString("system_encoding");
	}

	public static final String getSQLTables() {
		return bundle.getString("SQLTables");
	}

}