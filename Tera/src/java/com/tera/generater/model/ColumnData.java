package com.tera.generater.model;

/**
 * @author wallace
 *
 */
public class ColumnData {

	/**
	 * 数据表列名
	 */
	private String columnName;

	/**
	 * 列名对应的java属性名
	 */
	private String propName;

	/**
	 * 首字母大写方法名，前面加上set和get
	 */
	private String methodName;

	/**
	 * 列名对应的java复杂属性名
	 */
	private String propNameStr;
	/**
	 * 列名对应的java复杂属性名类型
	 */
	private String propNameStrType;
	/**
	 * 首字母大写复杂方法名，前面加上set和get
	 */
	private String methodNameStr;

	/**
	 * 数据库映射的Java类型
	 */
	private String propType;
	/**
	 * 数据库映射的Mybatis类型
	 */
	private String jdbcType;//Mybatis 字段映射
	/**
	 * 列注释
	 */
	private String columnComment;
	/**
	 * DB列类型
	 */
	private String columnType;
	/**
	 * DB列Key
	 */
	private String columnKey;
	/**
	 * 列长度
	 */
	private String charmaxLength = "";
	/**
	 * 列为空
	 */
	private String nullable;
	/**
	 * 精度
	 */
	private String scale;
	/**
	 * 位数
	 */
	private String precision;
	/**
	 * EASYUI使用的类型
	 */
	private String classType = "";
	
	/**
	 * Mysql用来标识自增
	 */
	private String extra = "";

	private String optionType = "";

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPropType() {
		return this.propType;
	}

	public void setPropType(String dataType) {
		this.propType = dataType;
	}

	public String getColumnComment() {
		return this.columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getScale() {
		return this.scale;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getClassType() {
		return this.classType;
	}

	public String getOptionType() {
		return this.optionType;
	}

	public String getCharmaxLength() {
		return this.charmaxLength;
	}

	public String getNullable() {
		return this.nullable;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPropNameStr() {
		return propNameStr;
	}

	public void setPropNameStr(String propNameStr) {
		this.propNameStr = propNameStr;
	}

	public String getMethodNameStr() {
		return methodNameStr;
	}

	public void setMethodNameStr(String methodNameStr) {
		this.methodNameStr = methodNameStr;
	}

	public String getPropNameStrType() {
		return propNameStrType;
	}

	public void setPropNameStrType(String propNameStrType) {
		this.propNameStrType = propNameStrType;
	}
	
}