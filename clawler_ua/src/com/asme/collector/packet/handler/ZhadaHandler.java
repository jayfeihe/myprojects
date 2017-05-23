package com.asme.collector.packet.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.packet.domain.Domain;
import com.asme.collector.packet.domain.Keyword;
import com.asme.collector.packet.domain.KeywordRule;
import com.asme.collector.packet.domain.TableUtil;
import com.asme.collector.util.MD5Util;
import com.asme.collector.util.SystemTime;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class ZhadaHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(ZhadaHandler.class);
	// 计数器
	public static final AtomicLong HIJACKED = new AtomicLong(0);
	public static final AtomicLong DOMAPPED = new AtomicLong(0);
	// 系统时间
	private static SystemTime systemTime;
	private static final String p1 = "com\\b|cn\\b|net\\b|org\\b|biz\\b|info\\b|cc\\b|tv\\b|edu\\b|ac\\b|mil\\b|arpa\\b|pro\\b|coop\\b|aero\\b|museum\\b|mobi\\b|asia\\b|tel\\b|int\\b|us\\b|travel\\b";
	private static final String p2 = "ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci"
			+ "|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|"
			+ "gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|"
			+ "md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|"
			+ "qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|"
			+ "uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw";
	private static final Pattern p = Pattern.compile("(?<=http://|\\.)([^.]*?\\.(" + p1 + ")(\\.(" + p2 + "))?)", Pattern.CASE_INSENSITIVE);

	private Map<String, String> adxHostTypes = new HashMap<String, String>();
	private Set<String> ipuaSet = new HashSet<String>();
	// key url value campaignIds
	private Map<String, List<String>> domains = new HashMap<String, List<String>>();

	// key keywords value campaignIds
	private Map<String, List<String>> keywords = new HashMap<String, List<String>>();

	private Map<String, List<KeywordRule>> rules = new HashMap<String, List<KeywordRule>>();
	private ShardedJedis jedis;

	public ZhadaHandler() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(20);
		;
		config.setMaxIdle(10);
		config.setMinIdle(2);
		config.setMaxWait(10);
		config.setTestWhileIdle(true);
		JedisShardInfo shardInfo = new JedisShardInfo("redis://gddxrare@183.6.188.235:6739");
		List<JedisShardInfo> shardInfos = new ArrayList<JedisShardInfo>();
		shardInfos.add(shardInfo);
		ShardedJedisPool pool = new ShardedJedisPool(config, shardInfos);
		jedis = pool.getResource();

		adxHostTypes.put("cms.tanx.com", "tanx");
		adxHostTypes.put("cm.pos.baidu.com", "bes");
		// 加载3张表
		List<Domain> domains = TableUtil.loadDomain();
		for (Domain domain : domains) {
			if (!this.domains.containsKey(domain.getDomain())) {
				List<String> campaignIds = new ArrayList<String>();
				campaignIds.add(domain.getCampaignId());
				this.domains.put(domain.getDomain(), campaignIds);
			} else {
				this.domains.get(domain.getDomain()).add(domain.getCampaignId());
			}
		}
		List<Keyword> keywords = TableUtil.loadKeyword();
		for (Keyword keyword : keywords) {
			if (!this.keywords.containsKey(keyword.getKeyword())) {
				List<String> campaignIds = new ArrayList<String>();
				campaignIds.add(keyword.getCampaignId());
				this.keywords.put(keyword.getKeyword(), campaignIds);
			} else {
				this.keywords.get(keyword.getKeyword()).add(keyword.getCampaignId());
			}
		}

		List<KeywordRule> keywordRules = TableUtil.loadKeywordRule();
		for (KeywordRule rule : keywordRules) {
			if (!this.rules.containsKey(rule.getHost())) {
				this.rules.put(rule.getHost(), new LinkedList<KeywordRule>());
			}
			this.rules.get(rule.getHost()).add(rule);
		}
	}

	public static ArrayBlockingQueue<String[]> URLQ = new ArrayBlockingQueue<String[]>(5000);

	public static void init() {
		final File dir = new File("result/zhada");
		if (!dir.exists())
			dir.mkdirs();

		Thread t = new Thread("zhadaWriter") {

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
			String userid = null;
			if (checkUrl(host, url, referer, cookies, srcip, ua)) {
				Map<String, String> values = jedis.hgetAll(srcip);
				int port = packet.srcport;
				for (Entry<String, String> entry : values.entrySet()) {
					String startPort = entry.getKey();
					if (startPort.equals("-1")) {
						continue;
					}
					String value = entry.getValue();
					String[] fields = value.split("_");
					String endPort = fields[0];
					if (port >= Integer.parseInt(startPort) && port <= Integer.parseInt(endPort)) {
						userid = fields[1];
					}
				}
				if (userid == null) {
					for (Entry<String, String> entry : values.entrySet()) {
						String startPort = entry.getKey();
						if (!startPort.equals("-1")) {
							continue;
						}
						String value = entry.getValue();
						String[] fields = value.split("_");
						userid = fields[1];
					}
				}
				if (userid == null) {
					userid = ad;
				}
				URLQ.offer(new String[] { srcip, ad, url, referer, ua, dstip, cookies, userid });
				// log.info("url[" + url + "]已校验通过");
			}

		}
		return null;
	}

	private boolean checkUrl(String host, String url, String referrer, String cookie, String ip, String ua) {

		if (domains.containsKey(host)) {
			return true;
		}

		// 筛选关键词访问记录
		String keyword = parseKeyword(url, referrer, host);
		if (keyword != null) {

			// 匹配关键词. TODO 优化匹配算法.
			for (Iterator<Entry<String, List<String>>> it = keywords.entrySet().iterator(); it.hasNext();) {
				Entry<String, List<String>> e = it.next();
				String kw = e.getKey();
				if (StringUtils.contains(keyword, kw)) {
					return true;
				}
			}
		}

		// 筛选cms访问记录
		String adxType = adxHostTypes.get(host);
		String ipua = ip + ua;
		if (adxType != null && !StringUtils.isEmpty(cookie) && !"NoDef".equals(cookie)) {
			if (ipuaSet.contains(ipua)) {
				return false;
			}
			ipuaSet.add(ipua);
			return true;
		}
		return false;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		ZhadaHandler.systemTime = systemTime;
	}

	public static String getDomain(String url) {
		Matcher matcher = p.matcher(url);

		while (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	private String parseKeyword(String url, String ref, String host) {

		String result = null;
		if (!rules.containsKey(host)) {
			return result;
		}

		for (KeywordRule rule : rules.get(host)) {

			String urltmp = StringUtils.contains(ref, ".baidu.com") ? ref : url;

			if (!StringUtils.isEmpty(rule.getDecode())) {
				// 解码
				try {
					urltmp = URLDecoder.decode(url, rule.getDecode());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Matcher matcher = rule.getPattern().matcher(urltmp);

			if (matcher.find()) {
				result = matcher.group(1);
			}
		}

		return result;
	}
}
