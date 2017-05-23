package com.tera;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.tera.audit.common.model.BranchBankJson;
import com.tera.audit.loan.model.LoanBase;
import com.tera.interfaces.model.AppBean;
import com.tera.interfaces.model.AppLoginBean;
import com.tera.interfaces.util.GsonUtils;

public class TestJson {

	@Test
	public void test01() {
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/list.do";
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(dUrl);
			HttpResponse resp = client.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				String ss = new String(s.getBytes("ISO-8859-1"), "UTF-8");
				System.out.println(ss);
				
				TypeToken<List<LoanBase>> typeToken = new TypeToken<List<LoanBase>>() {};
				List<LoanBase> bases = (List<LoanBase>) GsonUtils.getInstance().fromJson(ss, typeToken);
				System.out.println(bases.get(0).getName());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02() {
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/update.do";
			HttpClient client = new DefaultHttpClient();
//			HttpGet get = new HttpGet(dUrl);
			HttpPost post = new HttpPost(dUrl);
			
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			NameValuePair pair = new BasicNameValuePair("id", "3");
			vps.add(pair);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
				AppBean bean = GsonUtils.getInstance().fromJson(s, AppBean.class);
				System.out.println(bean.getLoanBase().getName());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPost01() {
		try {
			LoanBase base = new LoanBase();
			base.setName("eeww");
			base.setOrg("8601");
			String json = GsonUtils.getInstance().toJson(base);
			
			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/list.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			StringEntity se = new StringEntity(json,"utf-8");
			
			se.setContentEncoding("UTF-8");    
            se.setContentType("application/json"); 
			post.setEntity(se);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
				TypeToken<List<LoanBase>> typeToken = new TypeToken<List<LoanBase>>() {};
				List<LoanBase> bases = (List<LoanBase>) GsonUtils.getInstance().fromJson(s, typeToken);
				System.out.println(bases.get(0).getName());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testColl() {
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/collateral/update.do";
//			String dUrl = "http://182.92.77.38:6688/Cgtz/inter/collateral/update.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			NameValuePair pair = new BasicNameValuePair("id", "201601251209460003");
//			vps.add(pair);
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
//			post.setEntity(formEntity);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
//				TypeToken<List<Collateral>> typeToken = new TypeToken<List<Collateral>>() {};
//				List<Collateral> colls = (List<Collateral>) GsonUtils.getInstance().fromJson(s, typeToken);
//				System.out.println(colls.get(0).getType());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogin() {
		try {
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/app/login.do";
//			String dUrl = "http://localhost:8989/Cgtz/inter/app/login.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", "yewu1"));
			vps.add(new BasicNameValuePair("password", "123456"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
				AppLoginBean bean = GsonUtils.getInstance().fromJson(s, AppLoginBean.class);
				System.out.println(bean.getLoginId());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCollUpdate() {
		try {
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/app/login.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", "admin"));
			vps.add(new BasicNameValuePair("password", "123456"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
				AppLoginBean bean = GsonUtils.getInstance().fromJson(s, AppLoginBean.class);
				System.out.println(bean.getLoginId());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@Test
	public void testBank() {
		try {
			String province = "上海市";
			String city = "上海市";
			String bank_name = "农业银行";
			String dUrl = "http://172.16.33.47/open/input/GetBankList?province="+province+"&city="+city+"&bank_name="+bank_name;
			HttpClient client = new DefaultHttpClient();
			
			HttpGet get = new HttpGet(dUrl);
//			String uranium= "http://172.16.33.47/open/input/GetBankList/?province=%E4%B8%8A%E6%B5%B7%E5%B8%82&city=%E4%B8%8A%E6%B5%B7%E5%B8%82&bank_name=%E5%86%9C%E4%B8%9A%E9%93%B6%E8%A1%8C";
//			String dUrl = "http://172.16.33.47/open/input/GetBankList/";
//			HttpPost post = new HttpPost(dUrl);
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			vps.add(new BasicNameValuePair("province", "上海市"));
//			vps.add(new BasicNameValuePair("city", "上海市"));
//			vps.add(new BasicNameValuePair("bank_name", "农业银行"));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
//			post.setEntity(formEntity);
			
			HttpResponse resp = client.execute(get);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String json = EntityUtils.toString(entity);
				System.out.println(json.trim());
				
				BranchBankJson bankJson = GsonUtils.getInstance().fromJson(json, BranchBankJson.class);
				System.out.println(bankJson.getCode());
				
				List<String> data = bankJson.getData();
				for (String string : data) {
					System.out.println(string);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	@Test
	public void test09() throws ParseException {
//		Date date = DateUtils.getDate("2019-08-09");
//		String string = DateUtils.formatDate(DateUtils.getDate("2019-08-09"), "yyyy/MM/dd");
//		
//		String string = DateUtils.formatDate(DateUtils.getDate("2016-02-26"), "yyyy/MM/dd");
//		System.out.println(string);
		
		
		String agent = "mozilla/5.0 (windows nt 10.0; wow64; rv:43.0) gecko/20100101 firefox/43.0";
		System.out.println(agent.indexOf("msie") > 0);
	}
	
	@Test
	public void test10() {
//		String data = "data={\"loanId\":\"99930939\",\"name\":\"hhss\"}";
//		JSONObject jsonobj = JSONObject.fromObject(data);
//		System.out.println(jsonobj.get("loanId"));
	}
}
