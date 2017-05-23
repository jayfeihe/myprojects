package com.asme.collector.packet.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.MD5Util;
import com.asme.collector.util.SystemTime;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class HuayangHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(HuayangHandler.class);
	// 计数器
	public static final AtomicLong HIJACKED = new AtomicLong(0);
	public static final AtomicLong DOMAPPED = new AtomicLong(0);
	// 系统时间
	private static SystemTime systemTime;
	private static Set<String> sourceUrls = new HashSet<String>();
	private static Set<String> sourceUrlhosts = new HashSet<String>();

	private static Set<String> refererUrls = new HashSet<String>();

	public HuayangHandler() {
	}

//	static {
//		BufferedReader r = null;
//		try {
//			r = new BufferedReader(new InputStreamReader(new FileInputStream("resources/sourceUrls.txt")));
//			for (String line = r.readLine(); line != null; line = r.readLine()) {
//				String url = line.trim();
//				sourceUrls.add(url);
//				URL u = new URL(url);
//				sourceUrlhosts.add(u.getHost());
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		try {
//			r = new BufferedReader(new InputStreamReader(new FileInputStream("resources/refererUrls.txt")));
//			for (String line = r.readLine(); line != null; line = r.readLine()) {
//				String url = line.trim();
//				refererUrls.add(url);
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//	}

	public static ArrayBlockingQueue<String[]> URLQ = new ArrayBlockingQueue<String[]>(5000);

	public static void init() {
		final File dir = new File("result/paize");
		if (!dir.exists())
			dir.mkdirs();

		Thread t = new Thread("PZWriter") {

			private int hour = systemTime.getHour();

			public void run() {

				BufferedWriter bw = null;
				SimpleDateFormat hmat = new SimpleDateFormat("yyyyMMddHH");
				File file = new File(dir, hmat.format(System.currentTimeMillis()) + ".tmp");
				try {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, file.exists()), "GBK"));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				SimpleDateFormat fmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				StringBuilder sb = new StringBuilder();
				for (; !Thread.interrupted();) {
					String[] u = null;
					try {
						u = URLQ.poll(100, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						break;
					}
				
					int nowDate = systemTime.getHour();
					if (nowDate != hour) {
						try {
							bw.close();
							file.renameTo(new File(dir, file.getName().replace("tmp", "txt")));
							file = new File(dir, hmat.format(System.currentTimeMillis()) + ".tmp");
							bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, file.exists()), "GBK"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						hour = nowDate;
					}

					if (u == null) {
						try {
							bw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						continue;
					}
					try {
						sb.append(fmat.format(new Date())).append("\t");
						for (int i = 0; i < u.length; i++) {
							sb.append(u[i]);
							if (i < u.length - 1) {
								sb.append("\t");
							}
						}
						sb.append("\r\n");
						bw.write(sb.toString());
						sb.delete(0, sb.length());
					} catch (Exception e) {
						e.printStackTrace();
						try {
							bw.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				try {
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asme.hijack.HttpPacketHandler#handle(com.asme.hijack.HttpReqPacket)
	 */
	@Override
	public byte[] handle(HttpGetPacket packet) {

		if (packet.headers != null && packet.uri != null && packet.url != null) {
			String srcip = packet.srcip;
			if (srcip == null)
				return null;
			String dstip = packet.destip;
			String url = packet.url;
			String host = packet.headers.get("host");
			if (host == null)
				return null;
			String ua = packet.headers.get("user-agent");
			if (ua == null)
				return null;
			String ad = MD5Util.MD5(ua + srcip);
			String referer = packet.headers.get("referer");
			if (referer == null)
				referer = "--";
			String cookies = packet.headers.get("cookie");

			if (checkUrl(host, url, referer)) {
				URLQ.offer(new String[] { convertIp(srcip), ad, url, referer, ua, dstip, cookies });
				// log.info("url[" + url + "]已校验通过");
			}

		}
		return null;
	}

	private static String convertIp(String srcip) {
		String ip = new String(srcip);
		String[] fields = ip.split("\\.");
		if (fields.length != 4) {
			return srcip;
		} else {
			return fields[0] + "." + fields[1] + ".*.*";
		}
	}

	private static boolean checkUrl(String host, String url, String referrer) {
		if (sourceUrlhosts.contains(host)) {
			for (String u : sourceUrls) {
				if (url.startsWith(u)) {
					return true;
				}
			}
		}
		for (String u : refererUrls) {
			if (url.startsWith(u)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		HuayangHandler.systemTime = systemTime;
	}
}
