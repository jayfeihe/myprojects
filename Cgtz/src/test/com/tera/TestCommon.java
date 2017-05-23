package com.tera;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.junit.Test;

import com.tera.audit.loan.model.LoanApp;
import com.tera.interfaces.util.GsonUtils;
import com.tera.util.PasswordUtil;
import com.tera.util.RMBUpper;
import com.tera.util.StringUtils;

public class TestCommon {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String s = "\""+"ssss"+"\"";
//		System.out.println(s);
//		
//		String  s = "/userfiles/images/";
//		
//		String ss = "860101";
//		System.out.println(ss.substring(0,4));
		
//		try {
//			AppBean bean = (AppBean) JsonUtil.getInstance().json2obj(FileUtils.readFileToString(new File("app.json"), "UTF-8"), AppBean.class);
//			LoanBase base = bean.getLoanBase();
//			System.out.println(base.getId()+"name:"+base.getName());
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		/*Thread t1 = new myThread();
		t1.start();
		Thread t2 = new myThread();
		t2.start();
		Thread t3 = new myThread();
		t3.start();
		Thread t4 = new myThread();
		t4.start();
		Thread t5 = new myThread();
		t5.start();
		Thread t6 = new myThread();
		t6.start();
		
		Thread t7 = new myThread();
		t7.start();
		Thread t8 = new myThread();
		t8.start();
		Thread t9 = new myThread();
		t9.start();
		Thread t10 = new myThread();
		t10.start();
		Thread t11 = new myThread();
		t11.start();
		Thread t12 = new myThread();
		t12.start();*/
		
		
//		AppAuditBean bean = new AppAuditBean();
//		bean.setLoanId("12345678");
//		bean.setJudgeUids(Arrays.asList(new String[]{"aaa","bbb"}));
//		
//		String json = GsonUtils.getInstance().toJson(bean);
//		System.out.println(json);
		
//		List<String> ll = new ArrayList<>();
//		ll.add("222111");
//		ll.add("111222");
//		String[] array = ll.toArray(new String[ll.size()]);
//		for (int i = 0; i < array.length; i++) {
//			System.out.println(array[i]);
//		}
		
		int[][] a = new int[3][4];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				a[i][j] = 0;
			}
		}
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
		
		int[][] b = new int[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				b[i][j] = 1;
			}
		}
		
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				System.out.print(b[i][j]);
			}
			System.out.println();
		}
		
		for (int i = 0; i < b.length; i++) {
			System.arraycopy(a, 0, b, 0, 3);
		}
		
		
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[i].length; j++) {
				System.out.print(b[i][j]);
				
			}
			System.out.println();
		}
	}
	
	@Test
	public void test02() throws Exception {
		String ctime = "1456473771";
		String seId = "hhuu8j93ky4rsr113vwesdpginjewnbo";
		
		String json = "{"
					    +"\"data\": {"
					         +"\"Debt\": {"
					            +"\"amount\": \"30000.0\","
					            +"\"contract_rate\": \"21.0\","
					            +"\"debt_type\": \"0\","
					            +"\"end_time\": \"2017-02-01\","
					            +"\"fee_rate\": \"3424.0\","
					            +"\"product_type\": \"99\","
					            +"\"repayment\": \"0\","
					            +"\"seral_number\": \"HZ_0001201602191004173\","
					            +"\"start_time\": \"2016-02-19\""
					        +"},"
					        +"\"DebtBorrower\": {"
					            +"\"address\": \"dsdssd\","
					            +"\"age\": \"34\","
					            +"\"borrow_type\": \"1\","
					            +"\"city\": \"北京市\","
					            +"\"education\": \"10\","
					            +"\"identity_number\": \"14233569585652532\","
					            +"\"marrage\": \"1\","
					            +"\"name\": \"牛根生\","
					            +"\"phone\": \"15232658565\","
					            +"\"province\": \"北京市\","
					            +"\"sex\": \"1\""
					        +"},"
					        +"\"DebtCreditor\": {"
					            +"\"address\": \"ddd\","
					            +"\"borrow_type\": \"1\","
					            +"\"city\": \"两江新区\","
					            +"\"identity_number\": \"142332199111253213\","
					            +"\"name\": \"马云\","
					            +"\"phone\": \"13298565589\","
					            +"\"province\": \"重庆市\","
					            +"\"sex\": \"1\""
					        +"},"
					        +"\"DebtDesc\": {"
					            +"\"desc\": \"dfdsss\","
					            +"\"pledge\": \"dddsss\","
					            +"\"use\": \"世界那么大\""
					        +"},"
					        +"\"DebtPledge\": {"
					            +"\"built_up\": \"\","
					            +"\"category\": \"2\","
					            +"\"location\": \"浙江省杭州市仓库1\","
					            +"\"mileage\": \"\","
					            +"\"name\": \"车商\","
					            +"\"quantity\": \"4\","
					            +"\"register_date\": \"\","
					            +"\"type\": \"2\","
					            +"\"valuations\": \"400000.0\""
					        +"},"
					        +"\"UserBankCard\": {"
					            +"\"bank_city\": \"北京市\","
					            +"\"bank_id\": \"中国银行\","
					            +"\"bank_province\": \"北京市\","
					            +"\"card_number\": \"6222020325256587264\","
					            +"\"card_type\": \"0\","
					            +"\"name\": \"牛根生\","
					            +"\"sub_branch_name\": \"中国银行股份有限公司北京将台路支行\""
					        +"},"
					        +"\"debtAttachment\": {"
					            +"\"file_url\": \"https://www.baidu.com/\""
					        +"}"
					    +"}"
					+"}";
		
		
//		String md5 = PasswordUtil.md5(seId + ctime + json);
//		System.out.println(md5);
//		String mdd = StringUtils.md5(seId + ctime + json);
//		System.out.println(mdd);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(seId);
		buffer.append(ctime);
		buffer.append(json);
		
		System.out.println(StringUtils.replaceBlank(buffer.toString().replaceAll("\"", "\'")));
		
		String md5 = PasswordUtil.md5(StringUtils.replaceBlank(buffer.toString().replaceAll("\"", "\'")));
		System.out.println(md5);
	}
	
	@Test
	public void test01() throws UnsupportedEncodingException {
		URLDecoder.decode("", "utf-8");
		String bigAmt = RMBUpper.toBigAmt(190939.88);
		System.out.println(bigAmt);
		
		String decode = URLDecoder.decode("%E8%A1%8C%E6%94%BF%E6%9C%BA%E6%9E%84%E5%AF%B9%E4%B8%AA%E4%BA%BA%E7%9A%84%E5%A5%96%E6%83%A9%E6%83%85%E5%86%B5", "utf-8");
		System.out.println(decode);
		
		String decode2 = URLDecoder.decode("行政机构对个人的奖惩情况", "utf-8");
		System.out.println(decode2);
	}
	
	@Test
	public void test03() {
		String json = "{\"inTime\":\"\"}";
		LoanApp loanApp = GsonUtils.getInstance().fromJson(json, LoanApp.class);
		System.out.println(loanApp.getInTimeStr());
	}
	
	@Test
	public void test04() {
		String[] ss = new String[]{"22222","4444","888888"};
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].length() > 4) {
				ss[i] = ss[i].substring(0, 4);
			}
		}
		System.out.println(ss[0]+","+ss[1]+","+ss[2]);
	}
}


class myThreadTest extends Thread {
	public void run() {
//		System.out.println(LoanIdUtil.getInstance().toString());
	}
}
