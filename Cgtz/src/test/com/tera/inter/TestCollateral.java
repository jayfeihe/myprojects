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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.tera.interfaces.model.AppBean;
import com.tera.interfaces.model.AppCollateralBean;
import com.tera.interfaces.model.AppGuarBean;
import com.tera.interfaces.util.GsonUtils;

public class TestCollateral {

	@Test
	@Rollback(true)
	public void test01() {
		HttpClient client = null;
		HttpResponse resp = null;
		String jsonparam1 = "{"+
    "\"collateral\": {"
//        +"\"id\": \"1\","
        +"\"assetRemark\": \"123\","
        +"\"billPrice\": \"12312.0\","
        +"\"buyDate\": \"2016-01-22 00:00:00\","
        +"\"carAge\": \"1\","
        +"\"carType\": \"2134234\","
        +"\"engCode\": \"1231\","
        +"\"evalName\": \"123\","
        +"\"evalPrice\": \"1000.0\","
        +"\"evalRemark\": \"13\","
        +"\"frameCode\": \"12311\","
        +"\"license\": \"2342\","
//        +"\"loanId\": \"201601262152320001\","
        +"\"mile\": \"123\","
        +"\"proDate\": \"2016-01-20 00:00:00\","
        +"\"remark\": \"123\","
        +"\"tranTimes\": \"1\","
        +"\"type\": \"01\","
        +"\"warehouseId\": \"8\""
    +"},"
    +"\"loginId\": \"yewu1\""+
"}";
		
		String jsonparam = "{"+
    "\"collateral\": {"
        +"\"assetRemark\": \"dddd\","
        +"\"billPrice\": \"444\","
        +"\"buyDate\": \"2016-01-20\","
        +"\"carAge\": \"2\","
        +"\"carType\": \"15555\","
        +"\"engCode\": \"5555\","
        +"\"evalName\": \"dfgg\","
        +"\"evalPrice\": \"1588\","
        +"\"evalRemark\": \"xxccc\","
        +"\"frameCode\": \"1111\","
        +"\"license\": \"1255\","
        +"\"loanId\": \"201601270843050002\","
        +"\"mile\": \"2455\","
        +"\"proDate\": \"2012-12-12\","
        +"\"remark\": \"ddddd\","
        +"\"tranTimes\": \"4\","
        +"\"type\": \"01\","
+"\"warehouseId\": \"8\""
    +"},"
   + "\"loginId\": \"yewu1\""
+"}";
		
		String jj = "{"
    +"\"collateral\": {"
        +"\"addr\": \"杭州市余杭区气象台\","
        +"\"area\": \"124\","
        +"\"assetRemark\": \"都放广告歌\","
        +"\"buyDate\": \"2008-02-28\","
        +"\"evalName\": \"我们是\","
        +"\"evalPrice\": \"132\","
        +"\"evalRemark\": \"都放广告歌\","
        +"\"housePropertyCode\": \"12345435855\","
        +"\"isSet\": 0,"
        +"\"landCode\": \"134555564444\","
        +"\"loanId\": \"HZ123201602280733052\","
        +"\"type\": 3"
    +"},"
    +"\"loginId\": \"yewu1\""
+"}";
		
		AppCollateralBean collateralBean = GsonUtils.getInstance().fromJson(jj, AppCollateralBean.class,"yyyy-MM-dd");
		System.out.println(collateralBean.getLoginId());
		try {
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/collateral/save.do";
			String dUrl = "http://localhost:8989/Cgtz/inter/collateral/save.do";
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			StringEntity e = new StringEntity(jj, "utf-8");
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			vps.add(new BasicNameValuePair("id", "6"));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(e);
			
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
	public void test02() {
		HttpClient client = null;
		HttpResponse resp = null;
		String jsonparam = "{"+
    "\"loanGuar\": {"
        +"\"id\": \"42\","
        +"\"name\": \"草根\","
        +"\"sex\": \"M\","
        +"\"idType\": \"04\","
        +"\"idNo\": \"142335198911256585\","
        +"\"tel\": \"15233265856\","
        +"\"tel2\": \"18565985256\","
        +"\"marriage\": \"02\","
        +"\"edu\": \"7\","
        +"\"type\": \"2\""
    +"},"
    +"\"loginId\": \"yewu1\""+
"}";
		
		
		System.out.println(jsonparam);
		AppGuarBean guar = GsonUtils.getInstance().fromJson(jsonparam, AppGuarBean.class);
		System.out.println(guar.getLoginId());
		try {
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/loanGuar/save.do";
//			String dUrl = "http://localhost:8989/Cgtz/inter/loanGuar/save.do";
			client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			StringEntity e = new StringEntity(jsonparam, "utf-8");
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			vps.add(new BasicNameValuePair("id", "6"));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(e);
			
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
	public void testUpdate() {
		try {
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/collateral/update.do?id=53";
//			String dUrl = "http://localhost:8989/Cgtz/inter/collateral/update.do";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			vps.add(new BasicNameValuePair("id", "53"));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
//			post.setEntity(formEntity);
			
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
	
	@Test
	public void testLoanLaw() {
		try {
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/collateral/update.do?id=53";
			String dUrl = "http://localhost:8989/Cgtz/inter/loanLaw/save.do";
			
			String json = "{\"id\":\"62\",\"lawRemark\":\"空军建军节\",\"lawSate\":\"1\",\"loanId\":\"HZ123201603060211534\",\"targetType\":\"1\"}";
			
			HttpClient client = new DefaultHttpClient();
			
			
			HttpPost post = new HttpPost(dUrl);
			
			StringEntity en = new StringEntity(json, "utf-8");
			
			post.setEntity(en);
			
			HttpResponse resp = client.execute(post);
			int statusCode = resp.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = resp.getEntity();
				String rj = EntityUtils.toString(entity);
				System.out.println(rj);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJudgeUpdate() {
		try {
//			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/collateral/update.do?id=53";
			String dUrl = "http://localhost:8989/Cgtz/inter/judgeReview/update.do?id=83";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
//			List<NameValuePair> vps = new ArrayList<NameValuePair>();
//			vps.add(new BasicNameValuePair("id", "53"));
//			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
//			post.setEntity(formEntity);
			
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
	
	
	@Test
	public void testBpmlog() {
		try {
			String dUrl = "http://123.57.59.102:6688/Cgtz/inter/getBpmLog.do";
//			String dUrl = "http://localhost:8989/Cgtz/inter/getBpmLog.do?loanId=HZ_0001201602191004173";
			HttpClient client = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(dUrl);
			List<NameValuePair> vps = new ArrayList<NameValuePair>();
			vps.add(new BasicNameValuePair("loanId", "HZ123201603080220092"));
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
			post.setEntity(formEntity);
			
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
