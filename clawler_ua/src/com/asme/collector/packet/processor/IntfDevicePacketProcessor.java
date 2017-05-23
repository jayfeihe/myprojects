package com.asme.collector.packet.processor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asme.collector.packet.HttpGetPacket;
import com.asme.collector.packet.HttpPacketParser;
import com.asme.collector.packet.handler.HttpGetPacketHandler;
import com.asme.collector.util.LinkedTransferQueue;

/**
 * @author ASME
 *
 * 2012-8-20
 */
public class IntfDevicePacketProcessor implements Runnable {

	private static final Log log = LogFactory.getLog(IntfDevicePacketProcessor.class);

	// 处理线程的ID
	private static final AtomicInteger ID = new AtomicInteger(1);

	// 抓到的数据包队列
	private LinkedTransferQueue<TCPPacket> packetQueue;
	
	// 劫持处理器
	private HttpGetPacketHandler[] handlers;

	// 发送数据用的网卡
	private NetworkInterface intf;

	// 收数据包的网卡的MAC, XX:XX:XX:XX:XX:XX格式
	// 一般是连接服务器发送网卡的交换机或者路由的MAC,如果是在本机测试则是本机的MAC
	private String destMac;
	
	private HttpPacketParser parser = new HttpPacketParser();
	
	public IntfDevicePacketProcessor(NetworkInterface intf, String destMac, LinkedTransferQueue<TCPPacket> packetQueue, HttpGetPacketHandler[] handlers) {
		this.intf = intf;
		this.destMac = destMac;
		this.packetQueue = packetQueue;
		this.handlers = handlers;
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

		TCPPacket rp = new TCPPacket(0, 0, 0, 0, false, true, true, false, false, false, false, false, 2048, 0);
		rp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 0, 255, IPPacket.IPPROTO_TCP, null, null);
		EthernetPacket rpep = new EthernetPacket();
		rpep.frametype = EthernetPacket.ETHERTYPE_IP;

		String[] srcb = {
			"00:50:56:96:68:a2"
		};

		byte[][] srcMacBytes = new byte[srcb.length][6];
		for (int i = 0; i < srcb.length; i++) {
			String[] strArr = srcb[i].split(":");
			for (int j = 0; j < strArr.length; j++) {
				int value = Integer.parseInt(strArr[j], 16);
				srcMacBytes[i][j] = (byte) value;
			}
		}

		byte[] destMacBytes = new byte[6];
		String[] strArr = destMac.split(":");
		for (int i = 0; i < strArr.length; i++) {
			int value = Integer.parseInt(strArr[i], 16);
			destMacBytes[i] = (byte) value;
		}

		for (; !Thread.interrupted();) {
			TCPPacket packet = null;
			try {
				packet = packetQueue.take();
			} catch (InterruptedException e) {
				log.info("包处理线程[" + Thread.currentThread().getName() + "]退出");
				break;
			}

			// 需要响应的Http包数据
			byte[] resp = null;
			HttpGetPacket pk = parser.parse(packet);
			if(pk == null) continue;
//			HttpGetPacket pk = new HttpGetPacket(packet);
			for(HttpGetPacketHandler h : handlers) {
				resp = h.handle(pk);
				if(resp != null) break;
			}
			if (resp != null) {

				rp.src_port = packet.dst_port;
				rp.dst_port = packet.src_port;
				rp.sequence = packet.ack_num;
				rp.ack_num = packet.sequence + packet.data.length;
				rp.src_ip = packet.dst_ip;
				rp.dst_ip = packet.src_ip;
				rp.data = resp;
				rpep.src_mac = intf.mac_address;
				rpep.dst_mac = destMacBytes;
				rp.datalink = rpep;
				try {
					sender.sendPacket(rp);
				} catch (IOException e) {
					 log.error("发送包发生错误", e);
					// nothing to do
				} catch (Exception e) {
					log.error("发送包发生错误", e);
				}
			}
		}
	}
}
