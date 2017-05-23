package com.asme.collector.packet.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.binary.Base64;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.LikeBase64_1;
import com.asme.collector.util.SystemTime;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class CookieHandler implements HttpGetPacketHandler {

	public static final AtomicInteger POSTNUM = new AtomicInteger();

	private static SystemTime systemTime;// 系统时间

	public static LinkedTransferQueue<String[]> IDQ = new LinkedTransferQueue<String[]>();

	private static ConcurrentHashMap<String, Long> FREQMAP = new ConcurrentHashMap<String, Long>();
	
	/**
	 * 计数器
	 */
	public static AtomicInteger counter = new AtomicInteger();
	
	/**
	 * 缓存
	 */
	private static ConcurrentHashMap<String, Integer> cacheMap = new ConcurrentHashMap<String, Integer>();

	/**
	 * 缓存大小
	 */
	private static final int CACHE_SIZE = 10000;
	
	public static void init() {

		Thread tt = new Thread("writeid") {

			private int yyyyMMdd = systemTime.getYyyyMMdd();
			private FileWriter w = null;

			public void run() {
				try {
					File f = new File(yyyyMMdd + "_app.txt");
					w = new FileWriter(new File(yyyyMMdd + "_app.txt"), f.exists());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				for (; !Thread.interrupted();) {
					String[] d = null;
					try {
						d = IDQ.poll(10, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						break;
					}
					if (d == null) {
						try {
							w.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						continue;
					}

					int now = systemTime.getYyyyMMdd();
					if (now != yyyyMMdd) {
						yyyyMMdd = now;
						try {
							w.flush();
							w.close();
							w = new FileWriter(new File(now + "_app.txt"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					try {
						w.write(LikeBase64_1.encode(d[0] + "\t" + d[1]) + "\r\n");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		tt.setDaemon(true);
		tt.start();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asme.hijack.HttpPacketHandler#handle(com.asme.hijack.HttpReqPacket)
	 */
	@Override
	public byte[] handle(HttpGetPacket packet) {

		if (packet.isGET && packet.headers != null) {
			String ua = packet.headers.get("user-agent");// ua
			/*try {
				ua = trans(ua, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}*/
			String uaBase64 = "-";
			if (ua == null || "".equals(ua)) {
				ua = "-";
			} else {
				uaBase64 = Base64.encodeBase64String(ua.getBytes());
			}
			
			Integer count = cacheMap.putIfAbsent(uaBase64, 1);
			if (count != null) {
				cacheMap.put(uaBase64, ++count);
			}
			
			if (CACHE_SIZE == counter.incrementAndGet()) {
				Set<Entry<String,Integer>> entries = cacheMap.entrySet();
				for (Entry<String, Integer> en : entries) {
					IDQ.tryTransfer(new String[]{en.getKey(),en.getValue().toString()});
				}
				cacheMap.clear();
				counter.set(0);
			}
		}
		return null;
	}

	public static void clearTimeouts() {
		long now = System.currentTimeMillis();
		for (Iterator<Entry<String, Long>> it = FREQMAP.entrySet().iterator(); it.hasNext();) {
			Entry<String, Long> e = it.next();
			if (e.getValue() + 7200000l > now)
				it.remove();
		}
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		CookieHandler.systemTime = systemTime;
	}

	private static String trans(String src, String enc) throws UnsupportedEncodingException {
		if (src == null)
			return null;
		char[] cs = src.toCharArray();
		byte[] bs = new byte[cs.length];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = (byte) cs[i];
		}
		return new String(bs, enc);
	}

}
