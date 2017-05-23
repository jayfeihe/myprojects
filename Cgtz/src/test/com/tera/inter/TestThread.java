package com.tera.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestThread {

	public static void main(String[] args) {
		Thread t1 = new myThread("qyz");
		t1.start();
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		Thread t2 = new myThread();
		t2.start();
	}
}


class myThread extends Thread {
	private String loginId;
	
	public myThread() {
		super();
	}

	public myThread(String loginId) {
		super();
		this.loginId = loginId;
	}

	public void run() {
		String token = login();
		visit(loginId,token);
	}
	
	private String login() {
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/app/getToken.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", loginId));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String s = EntityUtils.toString(entity);
				return s;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void visit(String loginId,String token) {
		try {
			HttpClient client = new DefaultHttpClient();
			
			String dUrl = "http://localhost:8989/Cgtz/inter/app/test.do";
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loginId", loginId));
			token = token.trim();
			vps.add(new BasicNameValuePair("token", token));
			UrlEncodedFormEntity formEntity  = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			client.execute(post);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}