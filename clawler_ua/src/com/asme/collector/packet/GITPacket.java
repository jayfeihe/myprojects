package com.asme.collector.packet;

import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

/**
 * ----------------
 * |GRE|IP|TCP|DATA
 * ----------------
 * @author ASME
 *
 * 2012-11-28
 */
public class GITPacket {

	private static final int GRE_HEADER_LEN = 4;
	private static final int IP_HEADER_LEN = 20;
	private static final int TCP_HEADER_LEN = 20;
	private static final int GIT_HEADER_LEN = GRE_HEADER_LEN + IP_HEADER_LEN + TCP_HEADER_LEN;

	// 构造这个劫持包要使用的源包和响应的Http数据
	private TCPPacket packet;
	private byte[] httpData;

	// 这个包的数据
	private byte[] bytes;
	
	private int j;

	/**
	 * 造包
	 */
	public void make() {

		bytes = new byte[GIT_HEADER_LEN + httpData.length];

		// GRE包头0x00 0x00 0x08 0x00
		// bytes[0] = 0x00;
		// bytes[1] = 0x08;
		bytes[2] = 0x08;
		// bytes[3] = 0x00;

		// IP包版本和长度 v4 20
		bytes[4] = 0x45;

		// IP包TOS = 0x00
		// bytes[5] = 0x00;

		// IP包总长度(Http包长加上TCP包头长加上IP包头长)
		int iplen = httpData.length + TCP_HEADER_LEN + IP_HEADER_LEN;
		bytes[6] = (byte) ((iplen >>> 8) & 0xFF);
		bytes[7] = (byte) (iplen & 0xFF);

		// Ident 0x00 0x00
		// bytes[8] = 0x00 ;
		// bytes[9] = 0x00;

		// Flags 标记字段 3bit, 偏移字段13bit
//		bytes[10] = 0x40; // 不分片
		bytes[10] = 0x00; // 不分片
		// bytes[11] = 0x00;

		// TTL = 255
		bytes[12] = (byte) 0xFF;

		// Protocol = TCP
		bytes[13] = IPPacket.IPPROTO_TCP;

		// IP包头校验和(稍后补上)
		// bytes[14] = 0x00;
		// bytes[15] = 0x00;

		// 源地址是packet的目的地址
		System.arraycopy(packet.dst_ip.getAddress(), 0, bytes, 16, 4);

		// 目的地址是packet的源地址
		System.arraycopy(packet.src_ip.getAddress(), 0, bytes, 20, 4);

		// 填充IP包头校验和
		fillIpHeaderCheckSum();

		// TCP包头开始
		// 源端口是packet的目的端口, 目的口是packet的源端口
		int srcPort = packet.dst_port, dstPort = packet.src_port;
		bytes[24] = (byte) (srcPort >>> 8 & 0xFF);
		bytes[25] = (byte) (srcPort & 0xFF);
		bytes[26] = (byte) (dstPort >>> 8 & 0xFF);
		bytes[27] = (byte) (dstPort & 0xFF);

		// SEQ是packet的ACK, ACK是packet的SEQ+DataLen
		long seq = packet.ack_num + j, ack = packet.sequence + packet.data.length;
		bytes[28] = (byte) (seq >>> 24 & 0xFF);
		bytes[29] = (byte) (seq >>> 16 & 0xFF);
		bytes[30] = (byte) (seq >>> 8 & 0xFF);
		bytes[31] = (byte) (seq & 0xFF);
		bytes[32] = (byte) (ack >>> 24 & 0xFF);
		bytes[33] = (byte) (ack >>> 16 & 0xFF);
		bytes[34] = (byte) (ack >>> 8 & 0xFF);
		bytes[35] = (byte) (ack & 0xFF);

		// 头部长度20
		bytes[36] = 0x50;

		// PSH ACK
		bytes[37] = 0x18;

		// 窗口大小 65535
		bytes[38] = (byte) 0xFF;
		bytes[39] = (byte) 0xFF;

		// TCP 头部校验和
		// bytes[40] = 0x00; 08
		// bytes[41] = 0x00; E4

		// 紧急指针
		// bytes[42] = 0x00;
		// bytes[43] = 0x00;

		// Http数据
		System.arraycopy(httpData, 0, bytes, GIT_HEADER_LEN, httpData.length);

		// 填充TCP头部校验和
		fillTcpCheckSum();
	}

	/**
	 * 填充IP头部校验和
	 */
	private void fillIpHeaderCheckSum() {
		int sum = 0;
		for (int i = 4; i <= 22; i += 2) {
			sum += (bytes[i] & 0xFF) << 8;
			sum += (bytes[i + 1] & 0xFF);
		}
		sum = (sum >>> 16) + (sum & 0xFFFF);
		sum += sum >>> 16;
		sum = ~sum;
		bytes[14] = (byte) (sum >>> 8 & 0xFF);
		bytes[15] = (byte) (sum & 0xFF);
	}

	/**
	 * 填充TCP校验和
	 */
	private void fillTcpCheckSum() {

		// len = 12 + bytes.length - 24;
		int len = bytes.length - 12;

		// 奇数字节数,要补一个字节计算校验和
		if((len & 0x01) > 0) len++;
		byte[] tcpWithPseudoHeader = new byte[len];

		// 源地址和目的地址
		System.arraycopy(bytes, 16, tcpWithPseudoHeader, 0, 4);
		System.arraycopy(bytes, 20, tcpWithPseudoHeader, 4, 4);

		// 保留字节
		// tcpWithPseudoHeader[8] = 0x00;

		// 协议 TCP
		tcpWithPseudoHeader[9] = bytes[13];

		// TCP包长
		int tcpLen = TCP_HEADER_LEN + httpData.length;
		tcpWithPseudoHeader[10] = (byte)(tcpLen >>> 8 & 0xFF);
		tcpWithPseudoHeader[11] = (byte)(tcpLen & 0xFF);

		// TCP包
		System.arraycopy(bytes, 24, tcpWithPseudoHeader, 12, tcpLen);

		// 计算checksum
		int sum = 0;
		for (int i = 0; i <= len - 2; i += 2) {
			sum += (tcpWithPseudoHeader[i] & 0xFF) << 8;
			sum += (tcpWithPseudoHeader[i + 1] & 0xFF);
		}
		sum = (sum >>> 16) + (sum & 0xFFFF);
		sum += sum >>> 16;
		sum = ~sum;

		// 填充
		bytes[40] = (byte) (sum >>> 8 & 0xFF);
		bytes[41] = (byte) (sum & 0xFF);
	}

	/**
	 * @param packet
	 *            the packet to set
	 */
	public void setPacket(TCPPacket packet) {
		this.packet = packet;
	}

	/**
	 * @param httpData
	 *            the httpData to set
	 */
	public void setHttpData(byte[] httpData) {
		this.httpData = httpData;
	}

	/**
	 * @param j the j to set
	 */
	public void setJ(int j) {
		this.j = j;
	}

	/**
	 * 获取包的字节数据
	 * 
	 * @return
	 */
	public byte[] toBytes() {
		return bytes;
	}
}