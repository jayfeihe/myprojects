package com.tera.generater.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.tera.generater.model.MysqlTableData;
import com.tera.generater.model.OracleTableData;
import com.tera.generater.model.TableData;
import com.tera.generater.util.ResourceUtil;
import com.tera.generater.util.TemplateParser;

/**
 * @author wallace
 *
 */
public class CodeGenerateFactory {

	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(CodeGenerateFactory.class);

	/**
	 * 数据库连接URL
	 */
	private String url = ResourceUtil.URL;
	/**
	 * 数据库连接用户名
	 */
	private String username = ResourceUtil.USERNAME;
	/**
	 * 数据库连接密码
	 */
	private String passWord = ResourceUtil.PASSWORD;

	/**
	 * 数据库类型
	 */
	private String databaseType = ResourceUtil.DATABASE_TYPE;

	// 主键生成方式 01:UUID 02:自增
	public String KEY_TYPE_01 = "01";
	// 主键生成方式 02:自增
	public String KEY_TYPE_02 = "02";


	/**
	 * 代码包名
	 */
	private String bizz_package = ResourceUtil.bizzPackage;


	//项目路径
	private String projectPath = getProjectPath();

	/**
	 * 数据库表
	 */
	private List<TableData> tableList = new ArrayList<TableData>();

	/**
	 * 获取数据库表
	 * @return
	 * @throws SQLException
	 */
	public void initDBTables() {
		try {
			Class.forName(ResourceUtil.DIVER_NAME).newInstance();
			Connection con = DriverManager.getConnection(url, username, passWord);
			PreparedStatement ps = con.prepareStatement(ResourceUtil.SQLTables);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String tableName = rs.getString("Name");
				String tableComment = rs.getString("Comment");
				TableData table = null;
				if(databaseType.equalsIgnoreCase(ResourceUtil.DATABASE_TYPE_MYSQL)) {
					table = new MysqlTableData();
				} else if(databaseType.equalsIgnoreCase(ResourceUtil.DATABASE_TYPE_ORACLE)) {
					table = new OracleTableData();
				}
				table.setTableName(tableName.toUpperCase());
				table.setTableComment(tableComment);
				table.setDBInfo(url, username, passWord);
				log.info("CodeGenerateFactory-getTables():" + tableName.toUpperCase() + ", " + tableComment);
				tableList.add(table);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 代码生成
	 * @param tableName 表名
	 * @param modelPackage 模块名
	 * @param jspPath jsp路径名
	 */
	public void codeGenerate(String tableName, String modelPackage, String jspPath) {
		if (jspPath == null || jspPath.equals("")) {
			jspPath = modelPackage;
		}
		try {
			tableName = tableName.toUpperCase();
			TableData table = null;
			for (TableData tableData : tableList) {
				if (tableData.getTableName().equalsIgnoreCase(tableName)) {
					table = tableData;
				}
			}
			if (table == null) {
				log.info("数据库中没有" + tableName + "表");
				return;
			}
			table.initColumnDatas();
			String className = table.getTableName2ClassName();
			String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());

			String srcPath = projectPath + ResourceUtil.source_root_package	 + "/";
			log.info("CodeGenerateFactory-srcPath:" + srcPath);
			//包路径
			String pckPath = srcPath + ResourceUtil.bizzPackageUrl + "/";
			log.info("CodeGenerateFactory-pckPath:" + pckPath);

			String webPath = projectPath + ResourceUtil.web_root_package + "/view/" + jspPath + "/";
			log.info("CodeGenerateFactory-webPath:" + webPath);

			String modelPath = modelPackage + "/" + "model/" + className + ".java";
			String daoPath = modelPackage + "/" + "dao/" + className + "Dao.java";
			String servicePath = modelPackage + "/" + "service/" + className + "Service.java";
			String controllerPath = modelPackage + "/" + "controller/" + className + "Controller.java";
			String daoMapperPath = modelPackage + "/" + "dao/" + className + "Dao.xml";

			VelocityContext context = new VelocityContext();
			context.put("className", className);
			context.put("author", "CodeGenerater");
			context.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			context.put("copyRight", "天瑞兴隆");
			context.put("lowerName", lowerName);
			context.put("tableComment", table.getTableComment());
			context.put("tableName", tableName);
			context.put("bizzPackage", bizz_package);
			context.put("modelPackage", modelPackage);
			context.put("jspPath", jspPath);
			//类属性
			context.put("feilds", table.getBeanFeilds());
			//生成的SQL
			Map<String, Object> sqlMap = table.getAutoCreateSql();
			context.put("columnDatas", table.getColumnDataList());
			context.put("SQL", sqlMap);
			//主键类型
			context.put("databaseType", databaseType); //数据库类型
			context.put("keyType", table.getKeyType()); //主键类型
			log.info("CodeGenerateFactory-keyType:" + table.getKeyType());
			String primaryKey = "";
			if (table.getPrimaryKeys().size() > 0) {
				primaryKey = table.getPrimaryKeys().get(0);
			} else if (table.getUniqueKeys().size() > 0) {
				primaryKey = table.getUniqueKeys().get(0);
			}
			String primaryProp = table.getColumnName2PropName(primaryKey);
			//
			context.put("primaryKey", primaryKey);
			context.put("primaryProp", primaryProp);
			if (primaryProp != null && !primaryProp.equals("")) {
				context.put("UprimaryProp", primaryProp.substring(0, 1).toUpperCase() + primaryProp.substring(1, primaryProp.length()));
			}
			log.info("CodeGenerateFactory-keyType:" + table.getKeyType());
			log.info("CodeGenerateFactory-primaryKey:" + primaryKey);
			log.info("CodeGenerateFactory-primaryProp:" + primaryProp);

			//JSP相关
			context.put("jspUpdateTr", table.getJspUpdateTr());
			context.put("jspQueryTr", table.getJspQueryTr());
			context.put("jspListTh", table.getJspListTh());
			context.put("jspListTd", table.getJspListTd());
			context.put("jspReadTr", table.getJspReadTr());

			//写文件
			TemplateParser.WriterPage(context, "ModelTemplate.ftl", pckPath, modelPath);
			TemplateParser.WriterPage(context, "DaoTemplate.ftl", pckPath, daoPath);
			TemplateParser.WriterPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
			TemplateParser.WriterPage(context, "MapperTemplate.xml", pckPath, daoMapperPath);
			TemplateParser.WriterPage(context, "ControllerTemplate.ftl", pckPath, controllerPath);

			TemplateParser.WriterPage(context, "JspQueryTemplate.ftl", webPath, lowerName + "Query.jsp");
			TemplateParser.WriterPage(context, "JspListTemplate.ftl", webPath, lowerName + "List.jsp");
			TemplateParser.WriterPage(context, "JspUpdateTemplate.ftl", webPath, lowerName + "Update.jsp");
			TemplateParser.WriterPage(context, "JspReadTemplate.ftl", webPath, lowerName + "Read.jsp");

//			TemplateParser.WriterPage(context, "jspTemplate.ftl", webPath, jspPath);
//			TemplateParser.WriterPage(context, "jsTemplate.ftl", webPath, jsPath);

			log.info("----------------------------代码生成完毕---------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 项目路径
	 * @return 项目路径
	 */
	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

}