package com.tera.report;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tera.report.excel.ExcelMatrix;
import com.tera.report.excel.ExcelRegion;
import com.tera.report.excel.ExcelUtils;
import com.tera.sys.model.User;
import com.tera.sys.service.UserService;


/**
 * @author Wallace Chu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				//测试的数据源配置：请修改daoTestContext.xml文件
				"file:WebRoot/WEB-INF/daoTestContext.xml"
				}
		)
public class ExcelUtilsTest extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * UserDao
	 */
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	FileOutputStream out;
	
	ExcelMatrix excelMatrix;
	
	public void setUp1() throws Exception {
		out = new FileOutputStream("doc/测试.xls");
		excelMatrix = new ExcelMatrix();
		for (int i = 0; i < 20; i++) {
			List<Object> row = new ArrayList<Object>();
			for (int j = 0; j < 10; j++) {
				if(j ==0)
					row.add("NULL value");
				else if(j ==1)
						row.add("value");
				else if(j ==2)
					row.add("String value");
				else if(j ==3)
						row.add(i + "*" + j);
				else if(j ==4)
					row.add("Date value");
				else if(j ==5)
					row.add(new Date());
				else if(j ==6)
					row.add("Int value");
				else if(j ==7)
					row.add(i*j);
				else if(j ==8)
					row.add("Double value");
				else if(j ==9)
					row.add(2.0/3);
			}
			if(i == 5)
				excelMatrix.addRow(null);
			else
				excelMatrix.addRow(row);
		}
		List<Object> column = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			column.add(i);
		}
		excelMatrix.addColumn(0, column);
		excelMatrix.addRegion(0, 0, 10, 4);
		excelMatrix.setValue(0, 0, "测试");
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testWrite1() throws Exception {
		setUp1();
		HSSFWorkbook wb = new HSSFWorkbook();
		System.out.println(excelMatrix);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 20; i++) {
			HSSFSheet sheet = wb.createSheet("测试" + i);
			ExcelUtils.writeData(wb, sheet, excelMatrix, 2, 3);
		}
		wb.write(out);
		out.close();
		long end = System.currentTimeMillis();
		System.out.println("Complete Time:" + (end - begin));
	}
	
	@Test
	public void testWrite2() throws Exception {
		out = new FileOutputStream("doc/测试2.xls");
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> users = userService.queryUserByOrgAndRoleAndDepart(map);
		excelMatrix = new ExcelMatrix();
		String[] titles = new String[] {"ID","登录ID","姓名","密码","性别","EMAIL","固定电话"};
		String[] fields = new String[] {"id","loginId","name","password","gender","email","phone"};
		excelMatrix.bean2Matrix(users,Arrays.asList(titles),Arrays.asList(fields), true);
		
		ExcelRegion header = new ExcelRegion(0,0,7,1,"头部\n测试时测试是谁会");
		ExcelRegion footer = new ExcelRegion(0,0,7,1,"脚部");
		excelMatrix.setHeader(header);
		excelMatrix.setFooter(footer);
		
		long begin = System.currentTimeMillis();
		HSSFWorkbook wb = new HSSFWorkbook();
		System.out.println(excelMatrix);		
		for (int i = 0; i < 1; i++) {
			HSSFSheet sheet = wb.createSheet("测试" + i);
			ExcelUtils.writeData(wb, sheet, excelMatrix, 2, 3);
		}
		wb.write(out);
		out.close();
		long end = System.currentTimeMillis();
		System.out.println("Complete Time:" + (end - begin));
	}
	
	/**
	 * 基于annotation的
	 * @throws Exception
	 */
	@Test
	public void testWrite3() throws Exception {
		out = new FileOutputStream("doc/测试2.xls");
		com.tera.report.model.User u1 = new com.tera.report.model.User("张三", "男", 12);
		com.tera.report.model.User u2 = new com.tera.report.model.User("张三", "男", 12);
		com.tera.report.model.User u3 = new com.tera.report.model.User("小红", "女", 13);
		
		List<com.tera.report.model.User> users = new ArrayList<com.tera.report.model.User>();
		users.add(u1);
		users.add(u2);
		users.add(u3);
		
		excelMatrix = new ExcelMatrix();
		excelMatrix.bean2Matrix(users, com.tera.report.model.User.class, true);
		
		ExcelRegion header = new ExcelRegion(0,0,3,1,"头部");
		ExcelRegion footer = new ExcelRegion(0,0,3,1,"脚部");
		excelMatrix.setHeader(header);
		excelMatrix.setFooter(footer);
		
		long begin = System.currentTimeMillis();
		HSSFWorkbook wb = new HSSFWorkbook();
		System.out.println(excelMatrix);		
		for (int i = 0; i < 1; i++) {
			HSSFSheet sheet = wb.createSheet("测试" + i);
			ExcelUtils.writeData(wb, sheet, excelMatrix);
		}
		wb.write(out);
		out.close();
		long end = System.currentTimeMillis();
		System.out.println("Complete Time:" + (end - begin));
	}
}
