package com.asme.collector.packet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jpcap.packet.TCPPacket;

/**
 * @author asme 2014年9月19日 下午2:45:29
 *
 */
public class HttpPacketParser {
	
	private static final String HTTP = "http://";
	
	private static ConcurrentHashMap<String, HttpGetPacket> waitMap = new ConcurrentHashMap<String, HttpGetPacket>();
	
	public HttpGetPacket parse(TCPPacket packet) {

		int len = packet.data.length;
		if(len < 10) return null;
		if (packet.data[0] == 'G' && packet.data[1] == 'E' && packet.data[2] == 'T') {
			HttpGetPacket http =  parseHeaders(packet);
			http.isGET = true;
			return http;
		} else if (packet.data[0] == 'P' && packet.data[1] == 'O' && packet.data[2] == 'S' && packet.data[3] == 'T') {
			HttpGetPacket http = parseHeaders(packet);
			http.isPOST = true;
			if(!"m.jdh18.com".equals(http.host) && !"fa.163.com".equals(http.host)) return null;
			if(http.body == null) return null;
			if(http.body.position() == http.contentLength) {
				// 收满了
				return http;
			} else {
				// 没收满,放进等待Map
				StringBuilder key = new StringBuilder(http.srcip);
				key.append(":").append(packet.src_port).append("-")
						.append(http.destip).append(":")
						.append(packet.dst_port).append("-")
						.append(packet.sequence + len).append("-")
						.append(packet.ack_num);
				waitMap.put(key.toString(), http);
			}
		} else {
			// 看看是否是等待收数据的POST包
			StringBuilder key = new StringBuilder(packet.src_ip.getHostAddress());
			key.append(":").append(packet.src_port).append("-")
					.append(packet.dst_ip.getHostAddress()).append(":")
					.append(packet.dst_port).append("-")
					.append(packet.sequence).append("-")
					.append(packet.ack_num);
			HttpGetPacket http = waitMap.remove(key.toString());
			if(http != null) {
				http.body.put(packet.data);
				if(http.body.position() == http.contentLength) {
					return http;
				} else {
					key.delete(0, key.length());
					key.append(http.srcip);
					key.append(":").append(packet.src_port).append("-")
							.append(http.destip).append(":")
							.append(packet.dst_port).append("-")
							.append(packet.sequence + len).append("-")
							.append(packet.ack_num);
					waitMap.put(key.toString(), http);
				}
			}
		}
		return null;
	}
	
	/**
	 * 解析HTTP请求头
	 * @param packet
	 * @return
	 */
	private HttpGetPacket parseHeaders(TCPPacket packet) {
		
		HttpGetPacket http = new HttpGetPacket();
		http.srcip = packet.src_ip.getHostAddress();
		http.srcport = packet.src_port;
		http.bsrcip = packet.src_ip.getAddress();
		http.destip = packet.dst_ip.getHostAddress();
		List<String> lines = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		int index = 0;
		int offset = 0;
		int len = 0;
		String header = null;
		for (; index + 3 < packet.data.length; index++) {
			if (packet.data[index] == '\r' && packet.data[index + 1] == '\n') {

				header = new String(packet.data, offset, len);
				offset = offset + len + 2;
				len = 0;
//				lines.add(sb.toString());
				lines.add(header);
//				sb.delete(0, sb.length());
				if (packet.data[index + 2] == '\r' && packet.data[index + 3] == '\n') {

					// 连续的\r\n\r\n,请求头已经解析完了
					break;
				}

				// 与for里面的i++一起跳过\r\n
				index++;
			} else {
				//sb.append((char) packet.data[index]);
				len++;
				
			}
		}
//		sb.delete(0, sb.length());

		int size = lines.size();
		boolean isHttp11 = false;
		if (size > 0) {
			String firstLine = lines.get(0);
			int i = firstLine.indexOf(" ");
			int j = firstLine.lastIndexOf(" ");
			if (i != j && i != -1) http.uri = firstLine.substring(i + 1, j);
			isHttp11 = firstLine.substring(j + 1).startsWith("HTTP/");
			http.headers = new HashMap<String, String>();
			for (int k = 1; k < size; k++) {
				String line = lines.get(k);
				int l = line.indexOf(":");
				if (l != -1) http.headers.put(line.substring(0, l).toLowerCase(), line.substring(l + 1).trim());
			}
			http.host = http.headers.get("host");
			if (isHttp11 && http.uri != null && http.host != null) {
				http.url = sb.append(HTTP).append(http.host).append(http.uri).toString();
			}
		}
		
		if(http.headers != null) {

			// http data的起始下标
			index += 4;
			String clen = http.headers.get("content-length");
			if(packet.data.length > index && clen != null) {
	
				// 有请求体
				http.contentLength = Integer.parseInt(clen);
				http.body = ByteBuffer.allocate(http.contentLength);
				http.body.put(packet.data, index, Math.min(packet.data.length - index, http.contentLength));
//				if(packet.data.length - index > http.contentLength) {
//					System.out.println(packet.data.length - index);
//					System.out.println(http.contentLength);
//					System.out.println(new String(http.body.array()));
//					for(Iterator<Entry<String, String>> it = http.headers.entrySet().iterator(); it.hasNext();){
//						Entry<String, String> ee = it.next();
//						System.out.println(ee.getKey() + ": " + ee.getValue());
//					}
//				}
			}
		}
		return http;
	}
	
	public  static void main(String[] args) throws UnsupportedEncodingException {
		String s = URLDecoder.decode("title=%BE%DBTeenie+Weenie%D0%A1%D0%DC%CE%AC%C4%E1%D7%A8%B9%F1%D5%FD%C6%B714%B4%BA%D0%C2%BF%EE%C5%AE%D3%A2%C2%D7%B7%E7%D2%C2TTJT41201K&x_id=&seller_id=9a7b01b9a5e0bae8d9bc1153d6ba32cb&seller_nickname=teenieweenie%B9%D9%B7%BD%C6%EC%BD%A2%B5%EA&who_pay_ship=%C2%F4%BC%D2%B3%D0%B5%A3%D4%CB%B7%D1&photo_url=i3%2FT1ICBYFqROXXXXXXXX_%21%210-item_pic.jpg&region=%C9%CF%BA%A3&auto_post=false&virtual=false&rootCatId=16&auto_post1=&buyer_from=ecity&allow_quantity=5&buy_param=37121433213_1_40179574376&quantity=1&_tb_token_=lf71Casx6gn&skuInfo=%B3%DF%C2%EB%3A165%2FM%3B%D1%D5%C9%AB%B7%D6%C0%E0%3ABlue+green%C0%B6%C2%CC%C9%AB&use_cod=false&_input_charset=UTF-8&destination=230100&skuId=40179574376&bankfrom=&from_etao=&item_id_num=37121433213&item_id=37121433213&auction_id=37121433213&seller_rank=0&seller_rate_sum=0&is_orginal=no&point_price=false&secure_pay=true&pay_method=%BF%EE%B5%BD%B7%A2%BB%F5&from=item_detail&buy_now=1580.00&current_price=1580.00&auction_type=b&seller_num_id=1771485843&activity=&chargeTypeId=&key=", "GBK");
		String[] ss = s.split("&");
		for(String sss : ss) {
			System.out.println(sss);
		}
		
	}
}
