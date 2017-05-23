package com.tera.inter;

import java.io.IOException;
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
import org.junit.Test;

import com.tera.interfaces.model.AppBean;
import com.tera.interfaces.util.GsonUtils;

public class TestDict {

	@Test
	public void testList() {
		HttpClient client = null;
		HttpResponse resp = null;
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/branchBank.do";
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("province", "北京市"));
			vps.add(new BasicNameValuePair("city", "北京市"));
			vps.add(new BasicNameValuePair("bankName", "中国银行"));
//			vps.add(new BasicNameValuePair("parentKey", "北京市"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps,"utf-8");
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
	
	@Test
	public void testUpdate() {
		HttpClient client = null;
		HttpResponse resp = null;
		try {
			String dUrl = "http://localhost:8989/Cgtz/inter/loanBase/update.do";
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("id", "6"));
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
	public void testSave() {
		
	}
}
