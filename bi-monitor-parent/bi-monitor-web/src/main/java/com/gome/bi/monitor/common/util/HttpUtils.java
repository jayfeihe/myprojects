package com.gome.bi.monitor.common.util;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	
	public static CloseableHttpClient getHttpClient() {
		// 设置httpClient参数
		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
	
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setValidateAfterInactivity(1000);
		connManager.setMaxTotal(200); // 设置整个连接池最大连接数
		connManager.setDefaultMaxPerRoute(20);// 每个路由最大连接数为20（单个路由可设置为最大conman.getMaxTotal()）
	
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config).build();
		
		return client;
	}

	/**
	 * get请求
	 * 
	 * @param url 请求地址
	 * @param charset 字符编码
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url,String charset) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = getHttpClient();
			HttpGet get = new HttpGet(url);
			httpResponse = client.execute(get);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String respStr = EntityUtils.toString(entity, charset);
				return respStr;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * post请求
	 * 
	 * @param url 请求地址
	 * @param params 请求参数，map类型
	 * @param charset 字符编码 ，如Consts.UTF_8
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, String> params, String charset) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = getHttpClient();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	        for (String key : params.keySet()) {  
	            nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs,charset);
			post.setEntity(formEntity);
			httpResponse = client.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String respStr = EntityUtils.toString(entity, charset);
				return respStr;
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * post请求
	 * 
	 * @param url 请求地址
	 * @param param 请求参数
	 * @param mimeType mime类型，如 ContentType.APPLICATION_JSON
	 * @param charset 字符编码，如Consts.UTF_8
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String param, String mimeType, String charset) {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = getHttpClient();
			HttpPost post = new HttpPost(url);
			StringEntity ent = new StringEntity(param, ContentType.create(mimeType, charset));
			post.setEntity(ent);
			httpResponse = client.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = httpResponse.getEntity();
				String respStr = EntityUtils.toString(entity, charset);
				return respStr;
			} else {
				System.err.println("状态码：" + statusCode);
			}
		} catch (UnsupportedCharsetException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
