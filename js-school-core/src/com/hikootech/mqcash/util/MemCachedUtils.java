package com.hikootech.mqcash.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
* @ClassName MemCachedUtils 
* @Description memcached工具类 
* @author 余巍 yuweiqwe@126.com 
* @date 2015年12月19日 上午10:47:56 
*  
*/
public class MemCachedUtils {
	
	private static final String MEM_ADDRESS = "memcached_address";
	private static final String MEM_CONN_TIMEOUT = "memcached_connTimeout";
	private static final String MEM_DEFAULT_EXPIRATION = "memcached_expiration";
	
	private static int expiration;
	private static MemcachedClientBuilder builder;
	private static MemcachedClient client;
	
	private static Logger log = LoggerFactory.getLogger(MemCachedUtils.class);
	
	public static void init() throws IOException{
		log.info("初始化MemCached");
		String memAddress = ConfigUtils.getProperty(MEM_ADDRESS);
		long connTimeout = Long.parseLong(ConfigUtils.getProperty(MEM_CONN_TIMEOUT));
		expiration = Integer.parseInt(ConfigUtils.getProperty(MEM_DEFAULT_EXPIRATION));
		
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(memAddress));
		builder.setFailureMode(true);
		builder.setCommandFactory(new BinaryCommandFactory());
		builder.setConnectionPoolSize(10);  
		builder.setConnectTimeout(connTimeout);
		
		client = builder.build();
	}
	
	public static <T> T get(String key){
		T value = null;
		try {
			value = client.get(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static boolean set(String key, int exp, Object value){
		boolean isSuc = false;
		try {
			isSuc = client.set(key, exp, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuc;
	}
	
	public static boolean set(String key, Object value){
		return set(key, expiration, value);
	}

}
