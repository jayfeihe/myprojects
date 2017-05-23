package com.tera.inter;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

public class TestLoanGuar {

	@Test
	@Rollback(true)
	public void testSave() {
		String jj = "{"
			    +"\"loanGuar\": {"
			        +"\"edu\": 1,"
			        +"\"homeAddr\": \"杭州市余杭区气象台发布\","
			        +"\"id\": \"61\","
			        +"\"idNo\": \"46732245555677\","
			        +"\"idType\": \"01\","
			        +"\"loanId\": \"HZ123201602280733052\","
			        +"\"marriage\": \"02\","
			        +"\"name\": \"放放风\","
			        +"\"nowAddr\": \"杭州市余杭区气象台\","
			        +"\"saleRemark\": \"哈哈哈刚刚\","
			        +"\"sex\": \"F\","
			        +"\"tel\": 15884255369,"
			        +"\"type\": 1"
			    +"},"
			    +"\"loginId\": \"yewu1\""
			+"}";
		
		try {
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/collateral/save.do";
			String dUrl = "http://localhost:8989/Cgtz/inter/loanGuar/save.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			StringEntity e = new StringEntity(jj, "utf-8");
			post.setEntity(e);
			
			HttpResponse resp = client.execute(post);
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
