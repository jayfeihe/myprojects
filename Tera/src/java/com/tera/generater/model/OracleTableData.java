package com.tera.generater.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tera.generater.util.ResourceUtil;

public class OracleTableData extends TableData {
	
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(OracleTableData.class);
	
	/**
	 * 访问数据库获取表的所有列
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public void initColumnDatas() throws Exception {
		//Mysql查询数据库表
		String sqlColumns = "select a.COLUMN_NAME, a.DATA_TYPE, b.comments,a.DATA_PRECISION, a.DATA_SCALE, a.DATA_LENGTH, a.NULLABLE," +
				"( case (select d.constraint_type from user_cons_columns c, user_constraints d " +
				"where c.constraint_name = d.constraint_name  and d.table_name = a.Table_Name " +
				"and c.column_name = a.COLUMN_NAME and c.position = 1 ) " +
				"when 'P' then 'PRI' " +
				"when 'U' then 'UNI' " +
				"else '' end ) columnKey,'' extra from user_tab_columns a, user_col_comments b " +
				"where a.TABLE_NAME = b.TABLE_NAME and a.COLUMN_NAME = b.column_name and a.Table_Name='" + tableName + "'";
		log.info(sqlColumns);
		Class.forName(ResourceUtil.DIVER_NAME).newInstance();
		Connection con = DriverManager.getConnection(url, username, password);
		PreparedStatement ps = con.prepareStatement(sqlColumns);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();//column_name
			String type = rs.getString(2);//data_type
			String comment = rs.getString(3);//column_comment
			String precision = rs.getString(4);//numeric_precision
			String scale = rs.getString(5);//numeric_scale
			String charmaxLength = rs.getString(6) == null ? "" : rs .getString(6);//character_maximum_length
			String nullable = rs.getString(7).toLowerCase().indexOf("y") == -1 ? "N" : "Y";//is_nullable
			String columnKey = rs.getString(8); //column_key
			String extra = rs.getString(9);//extra,是否为自增
			if ("PRI".equalsIgnoreCase(columnKey)) {
				primaryKeys.add(name);
			} else if ("UNI".equalsIgnoreCase(columnKey)) {
				uniqueKeys.add(name);
			}
			ColumnData cd = new ColumnData();
			cd.setColumnName(name);
			String propName = this.getColumnName2PropName(name);
			cd.setPropName(propName);
			cd.setMethodName(propName.substring(0, 1).toUpperCase() + propName.substring(1, propName.length()));
			//设置类型
			initType(type, precision, scale, cd);
			//int类型的主键通过sequence产生自增
			if ("INTEGER".equalsIgnoreCase(cd.getJdbcType()) && "PRI".equalsIgnoreCase(columnKey)) {
				keyType = "02";
				log.info(keyType);
			}
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			cd.setColumnKey(columnKey);
			cd.setExtra(extra);
			//对于Date和Time类型，在页面显示时添加Str后缀属性
			if (cd.getPropType().indexOf("Date") > -1 || cd.getPropType().indexOf("Timestamp") > -1) {
				cd.setPropNameStr(cd.getPropName() + "Str");
				cd.setPropNameStrType("String");
				cd.setMethodNameStr(cd.getMethodName() + "Str");
			}
			log.info("----------" + cd.getPropNameStr());
			columnDataList.add(cd);
		}
		rs.close();
		ps.close();
		con.close();
	}

}
