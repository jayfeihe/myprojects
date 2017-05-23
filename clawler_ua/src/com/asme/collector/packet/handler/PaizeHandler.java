package com.asme.collector.packet.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.MD5Util;
import com.asme.collector.util.SystemTime;
import com.asme.collector.util.Timer;
import com.asme.collector.util.TimerFuture;
import com.asme.collector.util.TimerTask;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class PaizeHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(PaizeHandler.class);
	private static final String p1 = "com\\b|cn\\b|net\\b|org\\b|biz\\b|info\\b|cc\\b|tv\\b|edu\\b|ac\\b|mil\\b|arpa\\b|pro\\b|coop\\b|aero\\b|museum\\b|mobi\\b|asia\\b|tel\\b|int\\b|us\\b|travel\\b";
	private static final String p2 = "ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci"
			+ "|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|"
			+ "gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|"
			+ "md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|"
			+ "qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|"
			+ "uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw";
	private static final Pattern p = Pattern.compile("(?<=http://|\\.)([^.]*?\\.(" + p1 + ")(\\.(" + p2 + "))?)", Pattern.CASE_INSENSITIVE);

	// 计数器
	public static final AtomicLong HIJACKED = new AtomicLong(0);
	public static final AtomicLong DOMAPPED = new AtomicLong(0);

	// 系统时间
	private static SystemTime systemTime;
	private static ConcurrentHashMap<String, Integer> sourceUrls = new ConcurrentHashMap<String, Integer>();

	private static Timer timer;
	private static TimerFuture timerFuture;

	public PaizeHandler() {
	}

	static {
		BufferedReader r = null;
		try {
			r = new BufferedReader(new InputStreamReader(new FileInputStream("resources/paize/sourceUrls.txt")));
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				String url = line.trim();
				sourceUrls.put(url, 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static ArrayBlockingQueue<String[]> URLQ = new ArrayBlockingQueue<String[]>(5000);

	public static void init() {
		final File dir = new File("result/paizeHost");
		if (!dir.exists())
			dir.mkdirs();
		final File hostCountFile = new File(dir, System.currentTimeMillis() + ".txt");
		timerFuture = timer.timing(new TimerTask() {
			@Override
			public void run() {
				BufferedWriter br = null;
				try {
					br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hostCountFile)));
					StringBuffer sb = new StringBuffer();
					for (Entry<String, Integer> entry : sourceUrls.entrySet()) {
						String line = sb.append(entry.getKey()).append("\t").append(entry.getValue()).append("\r\n").toString();
						sb.delete(0, sb.length());
						br.write(line);
					}
				} catch (IOException e) {
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
						}
					}
				}
			}

			@Override
			public Type type() {
				return Type.INTERVAL;
			}

			@Override
			public long delayOrIntervalMillis() {
				return 60000L;
			}

			@Override
			public boolean isTriggerIndependently() {
				return false;
			}
		});

		Thread t = new Thread("PZHostWriter") {

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
		// t.setDaemon(true);
		// t.start();
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
			String ua = packet.headers.get("user-agent");
			if (ua == null)
				return null;
			String ad = MD5Util.MD5(ua + srcip);
			if (ad == null) {
				ad = "--";
			}
			String url = packet.url;
			String uri = packet.uri;
			String host = packet.headers.get("host");
			if (host == null)
				return null;
			String referer = packet.headers.get("referer");
			if (referer == null)
				referer = "--";
			String cookies = packet.headers.get("cookie");

			if (checkUrl(host, url, uri)) {
				// URLQ.offer(new String[] { convertIp(srcip), convertAd(ad),
				// url, referer, ua, dstip, cookies });
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

	private static String convertAd(String ad) {
		String cad = MD5Util.MD5(ad);
		return cad;
	}

	private static boolean checkUrl(String host, String url, String uri) {
		String tmpuri;
		int i = uri.indexOf("?");
		if (i == -1) {
			tmpuri = uri;
		} else {
			tmpuri = uri.substring(0, i);
		}
		if (tmpuri.endsWith(".jpg") || tmpuri.endsWith(".png") || tmpuri.endsWith(".gif") || tmpuri.endsWith(".css") || tmpuri.endsWith(".js")) {
			return false;
		}
		String domain = getDomain(host);
		if (domain == null) {
			return false;
		}
		Integer count = sourceUrls.get(domain);
		if (count == null) {
			return false;
		}
		count++;
		sourceUrls.put(domain, count);
		return true;
	}

	public static String getDomain(String url) {
		Matcher matcher = p.matcher(url);
		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		PaizeHandler.systemTime = systemTime;
	}

	public static Timer getTimer() {
		return timer;
	}

	public static void setTimer(Timer timer) {
		PaizeHandler.timer = timer;
	}

}
