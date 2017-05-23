package com.tera.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.google.gson.reflect.TypeToken;
import com.tera.audit.loan.model.LoanBase;
import com.tera.interfaces.model.AppBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.interfaces.util.HttpUtil;

public class TestLoanBase {

	@Test
	public void testList() {
		HttpClient client = null;
		HttpResponse resp = null;
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/list.do";
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/loanBase/list.do";
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", "yw"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String json = EntityUtils.toString(entity);
				System.out.println(json);
				
				TypeToken<List<LoanBase>> typeToken = new TypeToken<List<LoanBase>>() {};
				List<LoanBase> bases = (List<LoanBase>) GsonUtils.getInstance().fromJson(json, typeToken);
				if (bases == null) {
					System.out.println("服务端出错！");
				} else if (bases.size() > 0) {
					System.out.println(bases.get(0).getName());
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		HttpClient client = null;
		HttpResponse resp = null;
		try {
//			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/update.do";
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/loanBase/update.do";
			
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("id", "113"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String json = EntityUtils.toString(entity);
				System.out.println(json);
				
				AppBean bean = GsonUtils.getInstance().fromJson(json, AppBean.class);
				System.out.println(bean.getLoanBase().getName());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Rollback(true)
	public void testSave() throws Exception {
		String json = 
				"{"+
					    "\"loanBase\": {"
					        +"\"acctBank\": \"杭州市气象台\","
					        +"\"acctBranch\": \"杭州市气象台发布暴雨\","
					        +"\"acctCode\": 2556555555996,"
					        +"\"acctName\": \"你说我\","
					        +"\"endDate\": \"2012-12-12\","
					        +"\"inteDays\": 360,"
					        +"\"isTgth\": 0,"
					        +"\"lawFee\": 5555,"
					        +"\"loanAmt\": 5566,"
					        +"\"loanUse\": \"高规格g\","
					        +"\"memFee\": 55555,"
					        +"\"otherFee\": 6666,"
					        +"\"product\": 1,"
					        +"\"rate\": 12,"
					        +"\"retLoanSol\": 1,"
					        +"\"retWay\": 1,"
					        +"\"loanType\": \"01\","
					        +"\"salesman\": \"yewu1\""
					    +"},"
					    +"\"buttonType\": \"save\","
					    +"\"contacts\": ["
					        +"{"
					            +"\"company\": \"杭州市余杭区\","
					            +"\"name\": \"我们\","
					            +"\"relation\": 5,"
					            +"\"tel\": 224567"
					        +"}"
					    +"],"
					    +"\"loanApp\": {"
					        +"\"chilNum\": 2,"
					        +"\"companyAddr\": \"仿佛国会尽快\","
					        +"\"companyName\": \"杭州市余杭区政府\","
					        +"\"companyTel\": 12345677999,"
					        +"\"companyType\": 0,"
					        +"\"dept\": \"杭州市\","
					        +"\"edu\": 1,"
					        +"\"email\": \"cvhjeee\","
					        +"\"hasChil\": 1,"
					        +"\"homeAddr\": \"杭州市余杭区政府\","
					        +"\"homeCity\": \"丽水\","
					        +"\"homeCtry\": \"莲都区\","
					        +"\"homePrvn\": \"浙江\","
					        +"\"idNo\": \"344556744345667\","
					        +"\"idType\": 1,"
					        +"\"inTime\": \"2013-12-15\","
					        +"\"job\": \"疼痛感h\","
					        +"\"legalIdNo\": 0,"
					        +"\"legalIdType\": 0,"
					        +"\"legalTel\": 0,"
					        +"\"live\": 0,"
					        +"\"marriage\": 2,"
					        +"\"monthIncome\": 4467,"
					        +"\"monthOther\": 678899,"
					        +"\"name\": \"我是个\","
					        +"\"nativeHouse\": 0,"
					        +"\"nativeTime\": 5,"
					        +"\"nowAddr\": \"杭州市余杭区政府\","
					        +"\"orgPeriod\": 0,"
					        +"\"orgRegAmt\": 0,"
					        +"\"orgSalesAmt\": 0,"
					        +"\"phone\": 4578990077,"
					        +"\"postcode\": 566555,"
					        +"\"qq\": 555645566,"
					        +"\"sex\": \"M\","
					        +"\"shareIdNo\": 0,"
					        +"\"shareIdType\": 0,"
					        +"\"shareTel\": 0,"
					        +"\"tel\": 12456765799,"
					        +"\"tel2\": 0,"
					        +"\"wechat\": \"dgdfj\","
					        +"\"yearIncome\": 45678"
					    +"},"
					    +"\"loginId\": \"yw\""
					+"}";
		String json2 = "{\"loanApp\":{\"id\":\"1\",\"sex\":1}}";
		
		String json3 = "{"
    +"\"buttonType\": \"save\","
    +"\"contacts\": ["
        +"{"
            +"\"company\": \"aa\","
            +"\"name\": \"11\","
            +"\"relation\": 0,"
            +"\"tel\": 3433"
        +"}"
    +"],"
    +"\"loanApp\": {"
        +"\"chilNum\": 1,"
        +"\"companyAddr\": \"jdjdjdjd\","
        +"\"companyCity\": \"杭州市\","
        +"\"companyCtry\": \"余杭区\","
        +"\"companyName\": \"草根投资\","
        +"\"companyPrvn\": \"浙江省\","
        +"\"companyTel\": 8000,"
        +"\"companyType\": \"01\","
        +"\"dept\": \"财务部\","
        +"\"edu\": 7,"
        +"\"eduTime\": \"2012-02-08\","
        +"\"email\": \"zxx@qq.com\","
        +"\"hasChil\": 1,"
        +"\"homeAddr\": \"rrrr\","
        +"\"homeCity\": \"杭州市\","
        +"\"homeCtry\": \"西湖区\","
        +"\"homePrvn\": \"浙江省\","
        +"\"id\": \"61\","
        +"\"idNo\": \"142335198511026598\","
        +"\"idType\": \"01\","
        +"\"inTime\": \"2013-02-12\","
        +"\"job\": \"总监\","
        +"\"legalTel\": 0,"
        +"\"live\": \"01\","
        +"\"marriage\": \"02\","
        +"\"monthIncome\": 8000,"
        +"\"monthOther\": 2000,"
        +"\"name\": \"乱码修改\","
        +"\"nativeHouse\": \"03\","
        +"\"nativeTime\": 3,"
        +"\"nowAddr\": \"ddd\","
        +"\"nowCity\": \"杭州市\","
        +"\"nowCtry\": \"西湖区\","
        +"\"nowPrvn\": \"浙江省\","
        +"\"orgPeriod\": 0,"
        +"\"orgRegAmt\": 0,"
        +"\"orgSalesAmt\": 0,"
        +"\"phone\": 21212233,"
        +"\"postcode\": 100001,"
        +"\"qq\": 78526325,"
        +"\"saleRemark\": \"fdfdf\","
        +"\"school\": \"杭州大学\","
        +"\"sex\": \"M\","
        +"\"shareTel\": 0,"
        +"\"tel\": 13252532565,"
        +"\"tel2\": 0,"
        +"\"wechat\": \"dd002\","
        +"\"yearIncome\": 10"
    +"},"
    +"\"loanBase\": {"
        +"\"acctBank\": \"中国银行\","
        +"\"acctBranch\": \"(null)\","
        +"\"acctCity\": \"北京市\","
        +"\"acctCode\": 6222020337467392,"
        +"\"acctName\": \"乱码修改\","
        +"\"acctPrvn\": \"北京市\","
        +"\"deRate\": 0,"
        +"\"endDate\": \"2016-07-14\","
        +"\"ext2\": \"1\","
        +"\"id\": \"61\","
        +"\"inteDays\": 360,"
        +"\"isTgth\": 0,"
        +"\"lawFee\": 1200,"
        +"\"loanAmt\": 20000,"
        +"\"loanId\": \"HZ123201602200519301\","
        +"\"loanType\": \"01\","
        +"\"loanUse\": \"旅行\","
        +"\"magin\": 1100,"
        +"\"memFee\": 1200,"
        +"\"otherFee\": 1300,"
        +"\"product\": \"01\","
        +"\"rate\": 21,"
        +"\"retLoanSol\": \"01\","
        +"\"retWay\": \"01\","
        +"\"salesman\": \"yewu1\""
    +"},"
    +"\"loginId\": \"yw\""
+"}";
		
		
		AppBean bean = GsonUtils.getInstance().fromJson(json3, AppBean.class);
		System.out.println(bean.getButtonType());
		
		
		String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/save.do";
		DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
		HttpPost post = new HttpPost(dUrl);
		StringEntity e = new StringEntity(json3, "utf-8");
		post.setEntity(e);
		HttpResponse response = client.execute(post);
		System.out.println(response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String respJson = EntityUtils.toString(entity);
			System.out.println(respJson);
		}
	}
	
	@Test
	public void test00() throws UnsupportedEncodingException{
		String json3 = "%28null%29=%7B%0A++%22contacts%22+%3A+%5B%0A++++%7B%0A++++++%22relation%22+%3A+%2299%22%2C%0A++++++%22state%22+%3A+%221%22%2C%0A++++++%22company%22+%3A+%22esweswe%22%2C%0A++++++%22name%22+%3A+%22fdfewfwe%22%2C%0A++++++%22tel%22+%3A+%22141586415%22%0A++++%7D%0A++%5D%2C%0A++%22loanBase%22+%3A+%7B%0A++++%22acctName%22+%3A+%22sarangi%22%2C%0A++++%22otherFee%22+%3A+%22216%22%2C%0A++++%22lawFee%22+%3A+%221515%22%2C%0A++++%22acctBank%22+%3A+%22savoys+an%22%2C%0A++++%22memFee%22+%3A+%22100%22%2C%0A++++%22acctCode%22+%3A+%2258558525526644%22%2C%0A++++%22loanAmt%22+%3A+%2210000%22%2C%0A++++%22endDate%22+%3A+%222018-05-12%22%2C%0A++++%22retLoanSol%22+%3A+%2201%22%2C%0A++++%22salesman%22+%3A+%22yewu1%22%2C%0A++++%22loanUse%22+%3A+%22Sbaoishaoshjaop%22%2C%0A++++%22inteDays%22+%3A+%22180%22%2C%0A++++%22isTgth%22+%3A+%220%22%2C%0A++++%22rate%22+%3A+%2212%22%2C%0A++++%22acctBranch%22+%3A+%22sbgaoshap0s%22%2C%0A++++%22retWay%22+%3A+%2201%22%0A++%7D%2C%0A++%22loginId%22+%3A+%22yewu1%22%2C%0A++%22loanApp%22+%3A+%7B%0A++++%22yearIncome%22+%3A+%22150000%22%2C%0A++++%22chilNum%22+%3A+%221%22%2C%0A++++%22monthIncome%22+%3A+%2210000%22%2C%0A++++%22homeAddr%22+%3A+%22sahsasuy0oa%22%2C%0A++++%22eduTime%22+%3A+%222015-06-12%22%2C%0A++++%22idNo%22+%3A+%22411525559668654858%22%2C%0A++++%22nativeTime%22+%3A+%2212%22%2C%0A++++%22nowPrvn%22+%3A+%22%22%2C%0A++++%22nowAddr%22+%3A+%22asg9asyha0-us0a%22%2C%0A++++%22wechat%22+%3A+%221464416%22%2C%0A++++%22dept%22+%3A+%22sass%22%2C%0A++++%22tel%22+%3A+%2213526996548%22%2C%0A++++%22hasChil%22+%3A+%221%22%2C%0A++++%22nowCtry%22+%3A+%22%E6%96%B0%E7%AB%B9%22%2C%0A++++%22monthOther%22+%3A+%22500%22%2C%0A++++%22live%22+%3A+%2299%22%2C%0A++++%22sex%22+%3A+%22%E7%94%B7%22%2C%0A++++%22email%22+%3A+%2216161165%22%2C%0A++++%22nowCity%22+%3A+%22%E5%8F%B0%E6%B9%BE%22%2C%0A++++%22name%22+%3A+%22again%22%2C%0A++++%22nativeHouse%22+%3A+%2203%22%2C%0A++++%22companyAddr%22+%3A+%22adsawdwesw%22%2C%0A++++%22school%22+%3A+%22Saudi%22%2C%0A++++%22companyTel%22+%3A+%22165465446%22%2C%0A++++%22saleRemark%22+%3A+%22See+we%27re%22%2C%0A++++%22companyType%22+%3A+%2299%22%2C%0A++++%22postcode%22+%3A+%22464586%22%2C%0A++++%22job%22+%3A+%22wakes%22%2C%0A++++%22inTime%22+%3A+%222012-10-16%22%2C%0A++++%22edu%22+%3A+%221%22%2C%0A++++%22qq%22+%3A+%222141646%22%2C%0A++++%22phone%22+%3A+%2225685984%22%2C%0A++++%22companyName%22+%3A+%22Hasidism%22%2C%0A++++%22idType%22+%3A+%2201%22%2C%0A++++%22marriage%22+%3A+%2202%22%0A++%7D%2C%0A++%22buttonType%22+%3A+%22submit%22%0A%7D";
		String jj = "2222";
		String decode = URLDecoder.decode(json3, "UTF-8");
		System.out.println(decode);
	}
	
	@Test
	public void testJudge() {
		HttpClient client = null;
		HttpResponse resp = null;
		try {
//			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/update.do";
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/judgeFirst/update.do";
			
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("id", "21"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String json = EntityUtils.toString(entity);
				System.out.println(json);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
