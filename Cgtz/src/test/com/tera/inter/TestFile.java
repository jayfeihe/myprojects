package com.tera.inter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.tera.file.model.Files;
import com.tera.interfaces.util.GsonUtils;
import com.tera.interfaces.util.HttpUtil;

public class TestFile {

	@Test
	public void test01() throws Exception {
		DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
		
		String imgUrl = "http://123.57.59.102:6688/Cgtz/files/getCategories.do";
//		String imgUrl = "http://localhost:8989/Cgtz/files/getCategories.do";
		
		HttpPost post = new HttpPost(imgUrl);
		
		List<NameValuePair> vps = new ArrayList<NameValuePair>();
		vps.add(new BasicNameValuePair("loanId", "HZ123201603070248549"));
		vps.add(new BasicNameValuePair("secId", "filesce1"));
		vps.add(new BasicNameValuePair("biz", "HZ123201603070248549"));
//		vps.add(new BasicNameValuePair("catId", "行政机构对个人的奖惩情况"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps,"utf-8");
		
		post.setEntity(formEntity);
		
		HttpResponse resp = client.execute(post);
		
		int statusCode = resp.getStatusLine().getStatusCode();
		
		if (statusCode == HttpStatus.SC_OK) {
			HttpEntity entity = resp.getEntity();
			String json = EntityUtils.toString(entity);
			System.out.println(json);
//			List<Files> files = GsonUtils.getInstance().fromJson(json, new TypeToken<List<Files>>(){});
//			
//			for (Files f : files) {
//				System.out.println(f.getFileName());
//				String url = f.getFilePath();
////				url = url.replace("\\", "/");
//				System.out.println(url);
//				
//				String getImgUrl = "http://123.57.59.102:6688/Cgtz/files/imgRead.do?imgurl="+url;
//				HttpGet get = new HttpGet(getImgUrl);
//				
//				HttpResponse response = client.execute(get);
//				byte[] bs = EntityUtils.toByteArray(response.getEntity());
//				FileUtils.writeByteArrayToFile(new File("d:/"+f.getFileName()), bs);
//				
//				System.out.println("完成");
//			}
			
		}
	}
	
	@Test
	public void test02() throws Exception {
		DefaultHttpClient client = HttpUtil.getDefaultHttpClient();
		
		String imgUrl = "http://111.202.58.61:8083/certification-web/queryRealName";
		
		HttpPost post = new HttpPost(imgUrl);
		
		List<NameValuePair> vps = new ArrayList<NameValuePair>();
		vps.add(new BasicNameValuePair("data", "HZ123201603070248549"));
		vps.add(new BasicNameValuePair("sign", "filesce1"));
		vps.add(new BasicNameValuePair("sign_type", "HZ123201603070248549"));
		vps.add(new BasicNameValuePair("input_charset", "行政机构对个人的奖惩情况"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps,"utf-8");
		
		post.setEntity(formEntity);
		
		HttpResponse resp = client.execute(post);
		
		int statusCode = resp.getStatusLine().getStatusCode();
		
		if (statusCode == HttpStatus.SC_OK) {
			HttpEntity entity = resp.getEntity();
			String json = EntityUtils.toString(entity);
			System.out.println(json);
//			List<Files> files = GsonUtils.getInstance().fromJson(json, new TypeToken<List<Files>>(){});
//			
//			for (Files f : files) {
//				System.out.println(f.getFileName());
//				String url = f.getFilePath();
////				url = url.replace("\\", "/");
//				System.out.println(url);
//				
//				String getImgUrl = "http://123.57.59.102:6688/Cgtz/files/imgRead.do?imgurl="+url;
//				HttpGet get = new HttpGet(getImgUrl);
//				
//				HttpResponse response = client.execute(get);
//				byte[] bs = EntityUtils.toByteArray(response.getEntity());
//				FileUtils.writeByteArrayToFile(new File("d:/"+f.getFileName()), bs);
//				
//				System.out.println("完成");
//			}
			
		}
	}
}
