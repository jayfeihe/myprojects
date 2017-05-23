package com.tera.interfaces.util;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

/**
 * http工具类
 * @author QYANZE
 *
 */
public class HttpUtil {

	/**
	 * 获取httpClient
	 * @return
	 */
	public static DefaultHttpClient getDefaultHttpClient() {
		// 设置httpClient参数
		HttpParams params = new BasicHttpParams();
		final Integer CONNECTION_TIMEOUT = 50 * 1000; // 设置请求超时50秒钟
		final Integer SO_TIMEOUT = 50 * 1000; // 设置等待数据超时时间50秒钟
		final Long CONN_MANAGER_TIMEOUT = 500L; // 该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大

		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);

		// 在提交请求之前 测试连接是否可用
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
		
		// 注册https
		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		
		PoolingClientConnectionManager conman = new PoolingClientConnectionManager(schreg);
		conman.setMaxTotal(200); // 设置整个连接池最大连接数
		conman.setDefaultMaxPerRoute(20);// 每个路由最大连接数为20（单个路由可设置为最大conman.getMaxTotal()）

		DefaultHttpClient client = new DefaultHttpClient(conman, params);
		
		return client;
	}
}
