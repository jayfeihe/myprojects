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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.MD5Util;
import com.asme.collector.util.SystemTime;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class DodHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(DodHandler.class);
	// 计数器
	public static final AtomicLong HIJACKED = new AtomicLong(0);
	public static final AtomicLong DOMAPPED = new AtomicLong(0);

	// 系统时间
	private static SystemTime systemTime;
	private static Set<String> sourceUrls = new HashSet<String>();
	private static Set<String> sourceUrlhosts = new HashSet<String>();
	private static Map<String, Set<String>> regUrls = new HashMap<String, Set<String>>();
	private static Set<String> regUrlhosts;

	private static Set<String> allHosts = new HashSet<String>();

	public DodHandler() {
	}

	static {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream("resources/dod/sourceUrls.txt")));
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				if (line.trim().length() == 0) {
					continue;
				}
				String url = line.trim();
				sourceUrls.add(url);
				URL u = new URL(url);
				sourceUrlhosts.add(u.getHost());
			}
			allHosts.addAll(sourceUrlhosts);
		} catch (Exception e) {
		}
		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream("resources/dod/regUrls.txt")));
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				if (line.trim().length() == 0) {
					continue;
				}
				int index = line.indexOf("=");
				String url = line.substring(0, index);
				String regStr = line.substring(index + 1);
				URL u = new URL(url);
				String host = u.getHost();
				String[] ids = regStr.split("\\|");
				Set<String> idSet = new HashSet<String>();
				for (String id : ids) {
					if (id.trim().length() > 0) {
						idSet.add(id);
					}
				}
				regUrls.put(host, idSet);
			}
			regUrlhosts = regUrls.keySet();
			allHosts.addAll(regUrlhosts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static ArrayBlockingQueue<String[]> URLQ = new ArrayBlockingQueue<String[]>(5000);

	public static void init() {
		final File dir = new File("result/dod");
		if (!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir);
				log.info("创建文件夹[result/dod]成功");
			} catch (IOException e) {
				log.error("创建文件夹[result/dod]失败");
			}
		}
		Thread t = new Thread("dodWriter") {
			private int hour = systemTime.getHour();
			private Map<String, File> writerFiles = new HashMap<String, File>();
			private Map<String, BufferedWriter> writers = new HashMap<String, BufferedWriter>();
			private SimpleDateFormat hmat = new SimpleDateFormat("yyyyMMddHH");

			private void initWriters() {
				for (Entry<String, BufferedWriter> entry : writers.entrySet()) {
					String key = entry.getKey();
					File file = writerFiles.get(key);
					file.renameTo(new File(dir, file.getName().replace("tmp", "txt")));
					BufferedWriter writer = entry.getValue();
					try {
						writer.flush();
						writer.close();
					} catch (IOException e) {
					}
				}
				for (String host : allHosts) {
					try {
						File file = new File(dir, hmat.format(System.currentTimeMillis()) + "_" + host + ".tmp");
						BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, file.exists()), "utf-8"));
						writerFiles.put(host, file);
						writers.put(host, fileWriter);
					} catch (Exception e) {
					}
				}
			}

			public void run() {
				initWriters();
				SimpleDateFormat fmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				StringBuilder sb = new StringBuilder();
				for (; !Thread.interrupted();) {
					String[] u = null;
					try {
						u = URLQ.poll(100, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						break;
					}

					int nowHour = systemTime.getHour();
					if (nowHour != hour) {
						initWriters();
						hour = nowHour;
					}

					if (u == null) {
						try {
							for (BufferedWriter writer : writers.values()) {
								writer.flush();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						continue;
					}
					try {
						BufferedWriter writer = writers.get(u[0]);
						sb.append(fmat.format(new Date())).append("\t");
						for (int i = 1; i < u.length; i++) {
							sb.append(u[i]);
							if (i < u.length - 1) {
								sb.append("\t");
							}
						}
						sb.append("\r\n");
						writer.write(sb.toString());
						// log.info("写入数据:" + sb.toString());
						sb.delete(0, sb.length());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					for (BufferedWriter writer : writers.values()) {
						writer.close();
					}
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
			
//			if(host.equals("www.hao123.com")) {
//				if(url.equals("http://www.hao123.com/") || url.startsWith("http://www.hao123.com/?")) {
//					URLQ.offer(new String[] { host, srcip, ad, url, referer, ua, dstip, cookies });
//				} 
//			}else 
			if (checkUrl(host, url)) {
				URLQ.offer(new String[] { host, srcip, ad, url, referer, ua, dstip, cookies });
				// log.info("url[" + url + "]已校验通过");
			} else if (checkReg(host, url)) {
				URLQ.offer(new String[] { host, url });
			}
		}
		return null;
	}

	private boolean checkReg(String host, String url) {
		if (regUrlhosts.contains(host)) {
			Set<String> ids = regUrls.get(host);
			for (String id : ids) {
				String key = "id=" + id;
				if (url.indexOf(key) > -1) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkUrl(String host, String url) {
		if (sourceUrlhosts.contains(host)) {
			for (String u : sourceUrls) {
				if (url.startsWith(u)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		DodHandler.systemTime = systemTime;
	}
}
