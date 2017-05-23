package com.greenkoo.utils;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	
	private static CloseableHttpClient getHttpClient() {
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

	public static String doGet(String url,String charset) throws Exception {
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
	
	public static String doPost(String url, List<NameValuePair> vps, String charset) throws Exception {
		CloseableHttpClient client = null;
		CloseableHttpResponse httpResponse = null;
		try {
			client = getHttpClient();
			HttpPost post = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(vps);
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
	
	public static String doPost(String url, String param, String mimeType, String charset) throws Exception {
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
