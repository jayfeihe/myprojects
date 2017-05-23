package com.tera;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.tera.util.DateUtils;

public class MyTest {
	
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private String dbUser = "tera";
    private String dbPwd = "tera";
	
	private ResultSet getResultSet() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			if (null != conn) {
				ps = conn.prepareStatement("Select * from dual");
				//ps.setString(1, userName);
				rs = ps.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getString(1));
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB Exception!" + e.toString());
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
				if (null != ps) {
					ps.close();
				}
				if (null != conn) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		return rs;
	}


	public static void main(String[] args) {
		/*MyTestThread mtt1 = new MyTestThread();
		Thread t1 = new Thread(mtt1);
		mtt1.setThreadName("threadName11");
		MyTestThread mtt2 = new MyTestThread();
		Thread t2 = new Thread(mtt2);
		mtt2.setThreadName("threadName22");
		t2.start();
		t1.start();
		System.out.println("---------------");*/
//		System.out.println(DateUtils.formatDate(new Date()));
		try {
			MyTest test = new MyTest();
			ResultSet rs = test.getResultSet();
//			while (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String s1 = new String("abcde");
//		String s2 = new String("abcde"); 
//		boolean b1= s1.equals(s2);
//		boolean b2 = s1== s2;            
//		System.out.print(b1+"   "+b2);   

	}
}
