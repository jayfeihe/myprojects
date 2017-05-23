package com.asme.collector.packet.processor;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asme.collector.packet.GITPacket;
import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.packet.HttpPacketParser;
import com.asme.collector.packet.handler.HttpGetPacketHandler;
import com.asme.collector.util.LinkedTransferQueue;

/**
 * @author ASME
 *
 * 2012-8-20
 */
public class IntfDeviceGrePacketProcessor implements Runnable {

	private static final Log log = LogFactory.getLog(IntfDeviceGrePacketProcessor.class);

	// 处理线程的ID
	private static final AtomicInteger ID = new AtomicInteger(1);

	// 抓到的数据包队列
	private LinkedTransferQueue<TCPPacket> packetQueue;

	// 劫持处理器
	private HttpGetPacketHandler[] handlers;

	// 发送数据用的网卡
	private NetworkInterface intf;

	// 网关的MAC,XX:XX:XX:XX:XX:XX格式
	private String gateMac;
	
	// 数据包源IP
	private InetAddress srcAddr;

	// 目的IP
	private InetAddress destAddr;
	
	private HttpPacketParser parser = new HttpPacketParser();

	public IntfDeviceGrePacketProcessor(NetworkInterface intf, String gateMac, LinkedTransferQueue<TCPPacket> packetQueue, HttpGetPacketHandler[] handlers, InetAddress srcAddr, InetAddress destAddr) {
		this.intf = intf;
		this.gateMac = gateMac;
		this.packetQueue = packetQueue;
		this.handlers = handlers;
		this.srcAddr = srcAddr;
		this.destAddr = destAddr;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		Thread.currentThread().setName("PacketProcessor-" + ID.getAndIncrement());
		JpcapSender sender = null;
		try {
			sender = JpcapSender.openDevice(intf);
			log.info(Thread.currentThread().getName() + "打开网卡成功");
		} catch (IOException e) {
			log.error("打开网卡成功", e);
		}

		IPPacket rp = new IPPacket();
		rp.setIPv4Parameter(0, false, false, false, 0, false, true, false, 0, 0, 255, IPPacket.IPPROTO_GRE, srcAddr, destAddr);
		EthernetPacket rpep = new EthernetPacket();
		rpep.frametype = EthernetPacket.ETHERTYPE_IP;
		GITPacket git = new GITPacket();

		byte[] gateMacBytes = new byte[6];
		String[] strArr = gateMac.split(":");
		for (int i = 0; i < strArr.length; i++) {
			int value = Integer.parseInt(strArr[i], 16);
			gateMacBytes[i] = (byte) value;
		}

		for (; !Thread.interrupted();) {
			TCPPacket packet = null;
			try {
				packet = packetQueue.take();
			} catch (InterruptedException e) {
				log.info("包处理线程[" + Thread.currentThread().getName() + "]退出", e);
				break;
			}
			// 非GET包,不处理
			if(packet.data.length < 4 || !((packet.data[0] == 'G' && packet.data[1] == 'E' && packet.data[2] == 'T')||
					(packet.data[0] == 'P' && packet.data[1] == 'O' && packet.data[2] == 'S' && packet.data[3] == 'T'))) continue;

			// 需要响应的Http包数据
			byte[] httpData = null;
			HttpGetPacket pk = parser.parse(packet);
			if(pk == null) continue;
//			HttpGetPacket pk = new HttpGetPacket(packet);
			for(HttpGetPacketHandler h : handlers) {
				httpData = h.handle(pk);
				if(httpData != null) break;
			}
			if (httpData != null) {
				git.setPacket(packet);
				git.setHttpData(httpData);
				git.make();
				rp.data = git.toBytes();
				rpep.src_mac = intf.mac_address;
				rpep.dst_mac = gateMacBytes;
				rp.datalink = rpep;
				try {
					sender.sendPacket(rp);
				} catch (IOException e) {
					log.error("发送响应发生错误", e);
				} catch (Exception e) {
					log.error("发送包发生错误", e);
				}
			}
		}
	}
}
