package com.tera.generater.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wallace
 *
 */
public abstract class TableData {

	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(TableData.class);

	/**
	 * 表名称
	 */
	protected String tableName;

	/**
	 * 表注释
	 */
	protected String tableComment;

	/**
	 * 数据库URL
	 */
	protected String url;

	/**
	 * 数据库用户名
	 */
	protected String username;

	/**
	 * 数据库密码
	 */
	protected String password;

	/**
	 * 主键类型
	 */
	protected String keyType;

	/**
	 * 主键
	 */
	protected List<String> primaryKeys = new ArrayList<String>();

	/**
	 * 唯一键
	 */
	protected List<String> uniqueKeys = new ArrayList<String>();

	/**
	 * 表列数据
	 */
	protected List<ColumnData> columnDataList = new ArrayList<ColumnData>();

	/**设置Mysql数据库连接
	 * @param url
	 * @param username
	 * @param password
	 */
	public void setDBInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * 访问数据库获取表的所有列
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public abstract void initColumnDatas() throws Exception;

	/**
	 * 通过Table名称生成内部的属性和方法
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String getBeanFeilds() throws SQLException {
		//生成属性字符串
		StringBuffer str = new StringBuffer();
		//生成方法字符串
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : columnDataList) {
			//将列名转换为对象属性
			String name = d.getPropName();
			String type = d.getPropType();
			String comment = d.getColumnComment();
			//申明属性
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";//").append(comment);
			//方法首字母大写
			String method = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("\t").append("return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("\t").append("this." + name + "=").append(name).append(";\r\t}");
		}
		return str.append("\r\t").toString() + getset.toString();
	}

	/**
	 * 根据数据库表结构定义，映射java类型
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public void initType(String dataType, String precision, String scale, ColumnData columnData) {
		if (columnData == null) {
			return;
		}
		dataType = dataType.toLowerCase();
		if (dataType.contains("char")) {
			columnData.setPropType("String");
			columnData.setJdbcType("VARCHAR");
		} else if (dataType.contains("int")) {
			columnData.setPropType("int");
			columnData.setJdbcType("INTEGER");
		} else if (dataType.contains("float")) {
			columnData.setPropType("double");
			columnData.setJdbcType("DOUBLE");
		} else if (dataType.contains("double")) {
			columnData.setPropType("double");
			columnData.setJdbcType("DOUBLE");
		} else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale))
					&& (Integer.parseInt(scale) > 0)) {
				columnData.setPropType("double");
				columnData.setJdbcType("DOUBLE");
			} else if ((StringUtils.isNotBlank(precision))
					&& (Integer.parseInt(precision) > 6)) {
				columnData.setPropType("long");
				columnData.setJdbcType("INTEGER");
			} else {
				columnData.setPropType("int");
				columnData.setJdbcType("INTEGER");
			}
		} else if (dataType.contains("decimal")) {
			columnData.setPropType("double");
			columnData.setJdbcType("DOUBLE");
		} else if (dataType.equalsIgnoreCase("date")) { //注意Mysql中用datetime的情况
			columnData.setPropType("java.util.Date");
			columnData.setJdbcType("DATE");
		} else if (dataType.contains("time")) { //注意Mysql中用datetime的情况，不能使用因为一个表实际只能有一个Timestamp且不能为空
			columnData.setPropType("java.sql.Timestamp");
			columnData.setJdbcType("TIMESTAMP");
		} else if (dataType.contains("clob")) {
			columnData.setPropType("String");
			columnData.setJdbcType("VARCHAR");
		} else {
			columnData.setPropType("java.lang.Object");
			columnData.setJdbcType("VARCHAR");
		}
	}

	/**
	 * 通过表名获取类名
	 * @return 类名
	 */
	public String getTableName2ClassName() {
		String tableName = this.tableName.toLowerCase();
		//去掉T_开头的表前缀
		if (tableName.startsWith("t_")) {
			tableName = tableName.substring(2);
		}
		//以"_"分隔
		String[] split = tableName.split("_");
		//如果包含"_"
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				//首字母大写
				String tempTableName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}
			return sb.toString();
		}
		//不包含"_"的情况
		String tempTables = split[0].substring(0, 1).toUpperCase()
				+ split[0].substring(1, split[0].length());
		return tempTables;
	}

	/**
	 * 通过列名获取属性名
	 * @param columnName 列名
	 * @return 属性名
	 */
	public String getColumnName2PropName(String columnName) {
		columnName = columnName.toLowerCase();
		//以"_"分隔
		String[] split = columnName.split("_");
		//如果包含"_"
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < split.length; i++) {
				//首字母大写
				String tempPropName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempPropName);
			}
			return split[0] + sb.toString();
		}
		return columnName;
	}

	/**
	 * 生成插入语句
	 * @return sql
	 * @throws Exception Exception
	 */
	public Map<String, Object> getAutoCreateSql() throws Exception {
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("columnFields", getColumnFields(columnDataList));
		sqlMap.put("insert", getInsertSql(columnDataList));
		sqlMap.put("update", getUpdateSql(columnDataList));
//		sqlMap.put("insert", insert.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
//		sqlMap.put("update", update.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("delete", getDeleteSql(columnDataList));
		sqlMap.put("updateSelective", getUpdateSelectiveSql(columnDataList));
		sqlMap.put("selectByKey", getSelectByKeySql(columnDataList));
		return sqlMap;
	}

	/**
	 * 获取insert语句
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getInsertSql(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + tableName + "(");
		for (ColumnData data : columnList) {
			sb.append(data.getColumnName() + ",");
		}
		//去掉最后一个","
		sb.delete(sb.length() - 1, sb.length());
		sb.append(")\n values( ");
		for (ColumnData data : columnList) {
			sb.append("#{");
			sb.append(data.getPropName());
			//,jdbcType=VARCHAR
			sb.append(", jdbcType=").append(data.getJdbcType());
			sb.append("},");
		}
		//去掉最后一个","
		sb.delete(sb.length() - 1, sb.length());
		sb.append(")");
		return sb.toString();
	}

	/**
	 * 生成更新SQL
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getUpdateSql(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		//主键更新
		if (primaryKeys.size() > 0) {
			sb.append("update " + tableName + " set ");
			for (ColumnData data : columnList) {
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					continue;
				}
				sb.append(data.getColumnName()).append("=#{").append(
						data.getPropName()).append(", jdbcType=").append(data.getJdbcType()).append("},");
			}
			//去掉最后一个","
			sb.delete(sb.length() - 1, sb.length()).toString();
			sb.append(" where 1=1 ");
			for (ColumnData data : columnList) {
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		} else if (uniqueKeys.size() > 0) { //唯一键更新
			sb.append("update " + tableName + " set ");
			for (ColumnData data : columnList) {
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					continue;
				}
				sb.append(data.getColumnName()).append("=#{").append(
						data.getPropName()).append(", jdbcType=").append(data.getJdbcType()).append("},");
			}
			//去掉最后一个","
			sb.delete(sb.length() - 1, sb.length()).toString();
			sb.append(" where 1=1 ");
			for (ColumnData data : columnList) {
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

	/**
	 * 生成更新SQL，暂不用
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getUpdateSelectiveSql(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		//主键更新
		if (primaryKeys.size() > 0) {
			sb.append("\t<trim  suffixOverrides=\",\" >\n");
			for (ColumnData data : columnList) {
				//跳过主键
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					continue;
				}
				String columnName = data.getColumnName();
				sb.append("\t<if test=\"").append(data.getPropName()).append(" != null ");
				if ("String" == data.getPropType()) {
					sb.append(" and ").append(data.getPropName()).append(" != ''");
				}
				sb.append(" \">\n\t\t");
				sb.append(columnName).append("=#{").append(data.getPropName()).append(", jdbcType=").append(data.getJdbcType()).append("},\n");
				sb.append("\t</if>\n");
			}
			sb.append("\t</trim>\n\t");
			sb.append("where 1=1 ");
			for (ColumnData data : columnList) {
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		} else if (uniqueKeys.size() > 0) { //唯一键更新
			sb.append("\t<trim  suffixOverrides=\",\" >\n");
			for (ColumnData data : columnList) {
				//跳过唯一键
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					continue;
				}
				String columnName = data.getColumnName();
				sb.append("\t<if test=\"").append(data.getPropName()).append(" != null ");
				if ("String" == data.getPropType()) {
					sb.append(" and ").append(data.getPropName()).append(" != ''");
				}
				sb.append(" \">\n\t\t");
				sb.append(columnName).append("=#{").append(data.getPropName()).append(", jdbcType=").append(data.getJdbcType()).append("},\n");
				sb.append("\t</if>\n");
			}
			sb.append("\t</trim>\n\t");
			sb.append("where 1=1 ");
			for (ColumnData data : columnList) {
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
		}
		String update = "update " + tableName + " set \n" + sb.toString();
		return update;
	}

	/**
	 * 生成删除SQL
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getDeleteSql(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("delete ");
		sb.append("\t from ").append(tableName).append(" where 1=1 ");
		if (primaryKeys.size() > 0) {
			for (ColumnData data : columnList) {
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
		} else if (uniqueKeys.size() > 0) {
			for (ColumnData data : columnList) {
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * 生成根据key查询SQL
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getSelectByKeySql(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("\t from ").append(tableName).append(" where 1=1 ");
		if (primaryKeys.size() > 0) {
			for (ColumnData data : columnList) {
				if ("PRI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
		} else if (uniqueKeys.size() > 0){
			for (ColumnData data : columnList) {
				if ("UNI".equalsIgnoreCase(data.getColumnKey())) {
					sb.append("and " + data.getColumnName() + "=#{" + data.getPropName() + "},");
				}
			}
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * 生成查询SQL的列名
	 * @param columnList 列对象
	 * @return sql
	 * @throws SQLException SQLException
	 */
	public String getColumnFields(List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnList) {
			sb.append(data.getColumnName() + ",");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	/**
	 * 获取列表标题
	 * @param columnDataList
	 * @return String
	 * @throws SQLException
	 */
	public String getJspListTh() throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnDataList) {
			sb.append("<th scope=\"col\">").append(data.getColumnComment()).append("</th>\n");
		}
		return sb.toString();
	}

	/**
	 * 获取列表数据项
	 * @param columnDataList
	 * @return String
	 * @throws SQLException
	 */
	public String getJspListTd() throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnDataList) {
			sb.append("<td>${data.").append(data.getPropName()).append("}</td>\n");
		}
		return sb.toString();
	}

	/**
	 * 获取查询条件
	 * @param columnDataList
	 * @return String
	 * @throws SQLException
	 */
	public String getJspQueryTr() throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnDataList) {
			if ("INTEGER".equalsIgnoreCase(data.getJdbcType()) && "PRI".equalsIgnoreCase(data.getColumnKey())) {
				continue;
			}
			sb.append("<tr>\n");
			sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
			//EMAIL
			if(data.getColumnName().indexOf("email") > -1){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['email','length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append("/></td>\n");
			}
			//日期
			if(data.getPropType().indexOf("Date") > -1){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datebox\"" ).append("/></td>\n");	
			}
			//int || long
			if(data.getPropType().indexOf("int") > -1 || data.getPropType().indexOf("long") > -1){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"min:0,precision:0\" class=\"textbox easyui-numberbox\"" ).append("/></td>\n");	
			}
			//double
			if(data.getPropType().indexOf("double") > -1 || data.getPropType().indexOf("float")>-1){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"min:0,precision:2\" class=\"textbox easyui-numberbox\"" ).append("/></td>\n");	
			}
			//TIME
			if(data.getPropType().indexOf("Timestamp") > -1){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datetimebox\"" ).append("/></td>\n");	
			}
			
			if(data.getPropType().indexOf("String") > -1 && data.getColumnName().indexOf("email") <0 ){
				sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append("/></td>\n");
			}
			
			//sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" class=\"txt\" /></td>\n");
			sb.append("</tr>\n");
		}
		return sb.toString();
	}

	/**
	 * 获取更新条件
	 * @param columnDataList
	 * @return String
	 * @throws SQLException
	 */
	public String getJspUpdateTr() throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnDataList) {
			if ("INTEGER".equalsIgnoreCase(data.getJdbcType()) && "PRI".equalsIgnoreCase(data.getColumnKey())) {
				continue;
			}
			sb.append("<tr>\n");

			//EMAIL
			if(data.getColumnName().indexOf("email") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,validType:['email','length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['email','length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");
				}
			}
			//日期
			if(data.getPropType().indexOf("Date") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true\" editable=\"false\" class=\"textbox easyui-datebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\"/></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\"/></td>\n");	
				}
			}
			//int || long
			if(data.getPropType().indexOf("int") > -1 || data.getPropType().indexOf("long") > -1){
					if(data.getNullable() != null && "N".equals(data.getNullable())){
						sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
						sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,min:0,precision:0\" editable=\"false\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");	
					}else{
						sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
						sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\"  data-options=\"min:0,precision:0\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");	
					}
			}
			//double
			if(data.getPropType().indexOf("double") > -1 || data.getPropType().indexOf("float")>-1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" required=\"true\" editable=\"false\" data-options=\"required:true,min:0,precision:2\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\"  data-options=\"min:0,precision:2\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");	
				}
			}
			//TIME
			if(data.getPropType().indexOf("Timestamp") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true\" editable=\"false\" class=\"textbox easyui-datetimebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\"/></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datetimebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\"/></td>\n");	
				}
			}
			
			if(data.getPropType().indexOf("String") > -1 && data.getColumnName().indexOf("email") <0 ){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,validType:['length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\"/></td>\n");
				}
			}
			sb.append("</tr>\n");
		}
		return sb.toString();
	}

	/**
	 * 获取更新条件
	 * @param columnDataList
	 * @return String
	 * @throws SQLException
	 */
	public String getJspReadTr() throws SQLException {
		StringBuffer sb = new StringBuffer();
		for (ColumnData data : columnDataList) {
			sb.append("<tr>\n");

			//EMAIL
			if(data.getColumnName().indexOf("email") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,validType:['email','length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['email','length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");
				}
			}
			//日期
			if(data.getPropType().indexOf("Date") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true\" editable=\"false\" class=\"textbox easyui-datebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\" disabled=\"disabled\" /></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\" disabled=\"disabled\" /></td>\n");	
				}
			}
			//int || long
			if(data.getPropType().indexOf("int") > -1 || data.getPropType().indexOf("long") > -1){
					if(data.getNullable() != null && "N".equals(data.getNullable())){
						sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
						sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,min:0,precision:0\" editable=\"false\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");	
					}else{
						sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
						sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\"  data-options=\"min:0,precision:0\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");	
					}
			}
			//double
			if(data.getPropType().indexOf("double") > -1 || data.getPropType().indexOf("float")>-1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" required=\"true\" editable=\"false\" data-options=\"required:true,min:0,precision:2\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\"  data-options=\"min:0,precision:2\" class=\"textbox easyui-numberbox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");	
				}
			}
			//TIME
			if(data.getPropType().indexOf("Timestamp") > -1){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true\" editable=\"false\" class=\"textbox easyui-datetimebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\" disabled=\"disabled\" /></td>\n");	
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" editable=\"false\" class=\"textbox easyui-datetimebox\"" ).append(" value=\"${bean.").append(data.getPropName()+"Str").append("}\" disabled=\"disabled\" /></td>\n");	
				}
			}
			
			if(data.getPropType().indexOf("String") > -1 && data.getColumnName().indexOf("email") <0 ){
				if(data.getNullable() != null && "N".equals(data.getNullable())){
					sb.append("<td><SPAN style=\"color:red\">*</SPAN>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"required:true,validType:['length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");
				}else{
					sb.append("<td>").append(data.getColumnComment()).append(":</td>\n");
					sb.append("<td><input id=\"").append(data.getPropName()).append("\" name=\"").append(data.getPropName()).append("\" type=\"text\" data-options=\"validType:['length[0,"+ data.getCharmaxLength() +"]']\" class=\"textbox easyui-validatebox\"" ).append(" value=\"${bean.").append(data.getPropName()).append("}\" disabled=\"disabled\" /></td>\n");
				}
			}
			
			sb.append("</tr>\n");
		}
		return sb.toString();
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public List<ColumnData> getColumnDataList() {
		return columnDataList;
	}

	public void setColumnDataList(List<ColumnData> columnDataList) {
		this.columnDataList = columnDataList;
	}

	/**
	 * @return the primaryKeys
	 */
	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @param primaryKeys the primaryKeys to set
	 */
	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	/**
	 * @return the uniqueKeys
	 */
	public List<String> getUniqueKeys() {
		return uniqueKeys;
	}

	/**
	 * @param uniqueKeys the uniqueKeys to set
	 */
	public void setUniqueKeys(List<String> uniqueKeys) {
		this.uniqueKeys = uniqueKeys;
	}

}