package com.tera.inter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.tera.interfaces.model.AppLoginBean;
import com.tera.interfaces.util.GsonUtils;
import com.tera.util.StringUtils;

public class TestLogin {

	public static void main(String[] args) {
		/*Thread t1 = new loginThread("HZ_YW");
		t1.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread t2 = new loginThread("ZB_PSPW");
		t2.start();*/
		
		Thread t3 = new loginThread("yw");
		t3.start();
	}
}

class loginThread extends Thread {
	private String loginId;
	private static String JSESSIONID;
	public loginThread() {
		super();
	}

	public loginThread(String loginId) {
		super();
		this.loginId = loginId;
	}

	public void run() {
		try {
			testLogin();
			test();
			Thread.sleep(1000*80);
			test();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void testLogin() {
		try {
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/app/login.do?loginId="+loginId+"&password=123456";
			String dUrl = "http://localhost:8989/Cgtz/inter/app/login.do";
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/app/login.do";
			DefaultHttpClient client = new DefaultHttpClient();
			
//			HttpGet get = new HttpGet(dUrl);
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", loginId));
			vps.add(new BasicNameValuePair("password", "123456"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
			post.addHeader("Client-Type", "APP");
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(s);
				
				CookieStore cookieStore = client.getCookieStore();  
				List<Cookie> cookies = cookieStore.getCookies();  
				for(int i=0;i<cookies.size();i++){  
				    if("JSESSIONID".equals(cookies.get(i).getName())){  
				        JSESSIONID = cookies.get(i).getValue();  
				        break;  
				    }  
				}  
				
				System.out.println("JSESSIONID:"+JSESSIONID);
				
				AppLoginBean bean = GsonUtils.getInstance().fromJson(s, AppLoginBean.class);
				System.out.println(bean.getLoginId());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/app/test.do";
			DefaultHttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			
			System.out.println("JSESSIONID:"+JSESSIONID);
			
			if(StringUtils.isNullOrEmpty(JSESSIONID)){  
				post.setHeader("Cookie", "JSESSIONID="+JSESSIONID);  
			}
			
			post.addHeader("Client-Type", "APP");
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				System.out.println(new String(s.getBytes("ISO8859-1"), "UTF-8"));
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

