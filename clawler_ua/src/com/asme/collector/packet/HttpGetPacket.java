package com.asme.collector.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jpcap.packet.TCPPacket;

/**
 * 只在本包内可用
 * @author ASME
 * 
 *         2012-8-20
 */
public class HttpGetPacket {

	private static final String HTTP = "http://";

	// 源IP
	public String srcip;
	public byte[] bsrcip;

	// 目的IP
	public String destip;

	// 请求的资源
	public String uri;
	
	public int srcport;

	// 请求的地址
	public String url;

	// 请求来源
	public String referer;

	// 请求的主机
	public String host;

	// 请求头
	public Map<String, String> headers;
	
	public int contentLength;
	public ByteBuffer body;
	
	public boolean isGET;
	public boolean isPOST;

	public HttpGetPacket(){}

	public HttpGetPacket(TCPPacket packet) {
		srcip = packet.src_ip.getHostAddress();
		bsrcip = packet.src_ip.getAddress();
		destip = packet.dst_ip.getHostAddress();
		int len = packet.data.length;
		List<String> lines = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		if(len < 10) return;
		if(packet.data[0] == 'G' && packet.data[1] == 'E'&& packet.data[2] == 'T'){
			isGET = true;
		} else if(packet.data[0] == 'P' && packet.data[1] == 'O'&& packet.data[2] == 'S' && packet.data[3] == 'T') {
			isPOST = true;
		} else {
			return;
		}
		
		
		
		
		
		
		

		for (int i = 0; i + 3 < len; i++) {
			if (packet.data[i] == '\r' && packet.data[i + 1] == '\n') {
				lines.add(sb.toString());
				sb.delete(0, sb.length());
				if (packet.data[i + 2] == '\r' && packet.data[i + 3] == '\n') {

					// 连续的\r\n\r\n,请求头已经解析完了
					break;
				}

				// 与for里面的i++一起跳过\r\n
				i++;
			} else {
				sb.append((char) packet.data[i]);
			}
		}
		sb.delete(0, sb.length());

		int size = lines.size();
		boolean isHttp11 = false;
		if (size > 0) {
			String firstLine = lines.get(0);
			int i = firstLine.indexOf(" ");
			int j = firstLine.lastIndexOf(" ");
			if (i != j && i != -1) uri = firstLine.substring(i + 1, j);
			isHttp11 = firstLine.substring(j + 1).startsWith("HTTP/");
			headers = new HashMap<String, String>();
			for (int k = 1; k < size; k++) {
				String line = lines.get(k);
				int l = line.indexOf(":");
				if (l != -1) headers.put(line.substring(0, l).toLowerCase(), line.substring(l + 1).trim());
			}

			host = headers.get("host");
			if (isHttp11 && uri != null && host != null) {
				url = sb.append(HTTP).append(host).append(uri).toString();
			}
		}
	}
}
