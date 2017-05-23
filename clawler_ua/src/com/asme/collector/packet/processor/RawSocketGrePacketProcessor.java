package com.asme.collector.packet.processor;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

import jpcap.JpcapSender;
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
 * 2012-11-28
 */
public class RawSocketGrePacketProcessor implements Runnable {

	private static final Log log = LogFactory.getLog(RawSocketGrePacketProcessor.class);

	// 处理线程的ID
	private static final AtomicInteger ID = new AtomicInteger(1);

	// 抓到的数据包队列
	private LinkedTransferQueue<TCPPacket> packetQueue;

	// 劫持处理器
	private HttpGetPacketHandler[] handlers;

	// 发送数据用的网卡的IP地址
	private InetAddress sendIntfAddr;

	// 目的IP
	private InetAddress destAddr;
	
	private HttpPacketParser parser = new HttpPacketParser();

	public RawSocketGrePacketProcessor(LinkedTransferQueue<TCPPacket> packetQueue, HttpGetPacketHandler[] handlers, InetAddress sendIntfAddr, InetAddress destAddr) {
		this.packetQueue = packetQueue;
		this.handlers = handlers;
		this.sendIntfAddr = sendIntfAddr;
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
			sender = JpcapSender.openRawSocket();
			log.info(Thread.currentThread().getName() + "打开原始socket成功");
		} catch (IOException e) {
			log.error("打开原始socket发生错误", e);
		}

		IPPacket rp = new IPPacket();
		rp.setIPv4Parameter(0, false, false, false, 0, false, true, false, 0, 0, 255, IPPacket.IPPROTO_GRE, sendIntfAddr, destAddr);
		GITPacket git = new GITPacket();
		for (; !Thread.interrupted();) {
			TCPPacket packet = null;
			try {
				packet = packetQueue.take();
			} catch (InterruptedException e) {
				log.info("包处理线程[" + Thread.currentThread().getName() + "]退出");
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
				
				if(httpData.length <= 1400) {
				
					git.setJ(0);
					git.setPacket(packet);
					git.setHttpData(httpData);
					git.make();
					rp.data = git.toBytes();
					try{
						sender.sendPacket(rp);
					} catch (IOException e) {
						log.error("发送包发生错误", e);
						// nothing to do
					} catch (Exception e) {
						log.error("发送包发生错误", e);
					}
				} else {
					// 将resp以每个包最大1400的大小发出去
					int j = 0;
					for (; j + 1400 < httpData.length; j += 1400) {
						byte[] d = new byte[1400];
						System.arraycopy(httpData, j, d, 0, 1400);
						
						git.setJ(j);
						git.setPacket(packet);
						git.setHttpData(d);
						git.make();
						rp.data = git.toBytes();
						try{
							sender.sendPacket(rp);
						} catch (IOException e) {
							log.error("发送包发生错误", e);
							// nothing to do
						} catch (Exception e) {
							log.error("发送包发生错误", e);
						}
					}

					// 最后一个包
					int len = httpData.length - j;
					if(len > 0) {
						byte[] d = new byte[len];
						System.arraycopy(httpData, j, d, 0, len);
						git.setJ(j);
						git.setPacket(packet);
						git.setHttpData(d);
						git.make();
						rp.data = git.toBytes();
						try{
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
	}
}