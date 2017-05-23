package com.greenkoo;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.greenkoo.utils.HttpUtil;

public class TestPacket {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpUtil.getHttpClient();
			HttpGet get = new HttpGet("http://www.jd.com");
			get.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7"); 
			String ua = new String("汽车之家");
			byte b[] = ua.getBytes();
			get.setHeader("User-Agent", new String(b));
			httpClient.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
