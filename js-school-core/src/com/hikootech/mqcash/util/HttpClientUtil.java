package com.hikootech.mqcash.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2014年2月19日
 * com.jiexun.pos.utilHttpClientUtil.java
 * @author yuwei
 * httpclient工具类
 */
public class HttpClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static Integer connTimeout = 30000;
	
	private static Integer soTimeout = 30000;
	
	private static String contentCharset = "utf-8";
	
	private HttpClient httpClient = null;
	private HttpConnectionManagerParams managerParams = null;
	private HttpConnectionManager manager = null;
	
	public HttpClientUtil() {
		httpClient = new HttpClient();
		
		managerParams = new HttpConnectionManagerParams();
		managerParams.setConnectionTimeout(connTimeout);
		managerParams.setSoTimeout(soTimeout);
		managerParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharset);
		
		manager = new SimpleHttpConnectionManager();
		manager.setParams(managerParams);
		
		httpClient.setHttpConnectionManager(manager);
	}
	
	public HttpClientUtil(Integer timeout) {
		httpClient = new HttpClient();
		
		managerParams = new HttpConnectionManagerParams();
		managerParams.setConnectionTimeout(timeout);
		managerParams.setSoTimeout(timeout);
		managerParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, contentCharset);
		
		manager = new SimpleHttpConnectionManager();
		manager.setParams(managerParams);
		
		httpClient.setHttpConnectionManager(manager);
	}
	
	public String doPost(String uri, String queryString) throws Exception{
		PostMethod postMethod = new PostMethod(uri);
		postMethod.setQueryString(queryString);
		
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK){
				log.error(postMethod.getStatusLine().toString());
				throw new Exception(postMethod.getStatusLine().toString());
			} else {
				postMethod.getParams().setContentCharset(contentCharset);  
				return postMethod.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			log.error("http请求有误", e);
			throw new Exception("http请求有误", e);
		} catch (IOException e) {
			log.error("http请求有误IO异常", e);
			throw new Exception("http请求有误IO异常" + e, e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	public String doGet(String uri, String queryString) throws Exception{
		GetMethod getMethod = new GetMethod(uri);
		getMethod.setQueryString(queryString);
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode != HttpStatus.SC_OK){
				log.error(getMethod.getStatusLine().toString());
				throw new Exception(getMethod.getStatusLine().toString());
			} else {
				return getMethod.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			log.error("http请求有误", e);
			throw new Exception("http请求有误", e);
		} catch (IOException e) {
			log.error("http请求有误IO异常", e);
			throw new Exception("http请求有误IO异常" + e, e);
		} finally {
			getMethod.releaseConnection();
		}
	}
	
	public String doPost(String uri, Map<String, String> paramsMap) throws Exception{
		PostMethod postMethod = new PostMethod(uri);
//		NameValuePair
		
		Iterator<String> iterator = paramsMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = paramsMap.get(key);
			postMethod.setParameter(key, value);
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK){
				log.error(postMethod.getStatusLine().toString());
				throw new Exception(postMethod.getStatusLine().toString());
			} else {
				return postMethod.getResponseBodyAsString();
			}
		} catch (HttpException e) {
			log.error("http请求有误", e);
			throw new Exception("http请求有误", e);
		} catch (IOException e) {
			log.error("http请求有误IO异常", e);
			throw new Exception("http请求有误IO异常" + e, e);
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String uri = 
		"sCdKey=BGHMPAAAAFSdaUwU&" +
		"iUin=&" +
		"sVerifyCode=TUAB&" +
		"isVerifyCode=1" +
		"verifysession=h01db7c99cd0b05c0195af0de280343244a7aee48c95fa26e852d0921fc731dd93b3c92666bd9415326&" +
		"iVerifyId=21000104&" +
		"activityId=427&" +
		"contentId=activiteCDKeyValue&" +
		"gameId=&" +
		"verifyImgId=activiteImgVerify&" +
		"verifyCodeInput=activiteVerifyCodeId&" +
		"verifyChangeBtn=changeVerifyBtn&" +
		"iAMSActivityId=10270&" +
		"iLotteryFlowId=86201&" +
		"iFlowId=86201&" +
		"iActivityId=10270&" +
		"g_tk=497645497&" +
		"sServiceDepartment=x6m5&" +
		"sServiceType=k&"
		;
		String queryString = "http://x6m5.ams.game.qq.com/ams/ame/ame.php?ameVersion=0.3&sServiceType=k&iActivityId=10270&sServiceDepartment=x6m5";
//		String queryString = "http://117.135.171.237:80/ams/ame/ame.php?ameVersion=0.3&sServiceType=k&iActivityId=10270&sServiceDepartment=x6m5";

		System.out.println(uri.trim());
		HttpClientUtil clientUtil = new HttpClientUtil();
		String result = clientUtil.doPost(uri, queryString);
		
		System.out.println(result);
	}
	
}
