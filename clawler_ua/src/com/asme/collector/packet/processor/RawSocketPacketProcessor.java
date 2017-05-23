package com.asme.collector.packet.processor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import jpcap.JpcapSender;
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
public class RawSocketPacketProcessor implements Runnable {

	private static final Log log = LogFactory.getLog(RawSocketPacketProcessor.class);

	// 处理线程的ID
	private static final AtomicInteger ID = new AtomicInteger(1);

	// 抓到的数据包队列
	private LinkedTransferQueue<TCPPacket> packetQueue;
	
	// 劫持处理器
	private HttpGetPacketHandler[] handlers;
	
	private HttpPacketParser parser = new HttpPacketParser();

	public RawSocketPacketProcessor(LinkedTransferQueue<TCPPacket> packetQueue, HttpGetPacketHandler[] handlers) {
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
			sender = JpcapSender.openRawSocket();
			log.info(Thread.currentThread().getName() + "打开原始socket成功");
		} catch (IOException e) {
			log.error("打开原始socket发生错误", e);
		}

		TCPPacket rp = new TCPPacket(0, 0, 0, 0, false, true, true, false, false, false, false, false, 2048, 0);
		rp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 0, 255, IPPacket.IPPROTO_TCP, null, null);

//		TCPPacket rst = new TCPPacket(0, 0, 0, 0, false, false, false, true, false, false, false, false, 1024, 0);
//		rst.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1234, 10, IPPacket.IPPROTO_TCP, null, null);
//		byte[] b0 = new byte[0];

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
			if(resp == null) continue;
			rp.src_port = packet.dst_port;
			rp.dst_port = packet.src_port;
			rp.ack_num = packet.sequence + packet.data.length;
			rp.src_ip = packet.dst_ip;
			rp.dst_ip = packet.src_ip;

			if(resp.length <= 1400) {
				rp.sequence = packet.ack_num;
				rp.data = resp;
				try {
					sender.sendPacket(rp);
				} catch (IOException e) {
					// log.error("发送包发生错误");
					// nothing to do
				} catch (Exception e) {
					log.error("发送包发生错误", e);
				}
			} else {

				// 将resp以每个包最大1400的大小发出去
				int j = 0;
				for (; j + 1400 < resp.length; j += 1400) {
					byte[] d = new byte[1400];
					System.arraycopy(resp, j, d, 0, 1400);
					rp.sequence = packet.ack_num + j;
					rp.data = d;
					try {
						sender.sendPacket(rp);
					} catch (IOException e) {
						// log.error("发送包发生错误");
						// nothing to do
					} catch (Exception e) {
						log.error("发送包发生错误", e);
					}
				}

				// 最后一个包
				int len = resp.length - j;
				if(len > 0) {
					byte[] d = new byte[len];
					System.arraycopy(resp, j, d, 0, len);
					rp.sequence = packet.ack_num + j;
					rp.data = d;
					try {
						sender.sendPacket(rp);
					} catch (IOException e) {
						// log.error("发送包发生错误");
						// nothing to do
					} catch (Exception e) {
						log.error("发送包发生错误", e);
					}
				}
			}

//			if (resp != null) {
//				rp.src_port = packet.dst_port;
//				rp.dst_port = packet.src_port;
//				rp.sequence = packet.ack_num;
//				rp.ack_num = packet.sequence + packet.data.length;
//				rp.src_ip = packet.dst_ip;
//				rp.dst_ip = packet.src_ip;
//				rp.data = resp;
//				try {
//					sender.sendPacket(rp);
//				} catch (IOException e) {
//					// log.error("发送包发生错误");
//					// nothing to do
//				} catch (Exception e) {
//					log.error("发送包发生错误", e);
//				}
//				rst.src_port = packet.src_port;
//				rst.dst_port = packet.dst_port;
//				rst.sequence = packet.sequence + packet.data.length;
//				rst.src_ip = packet.src_ip;
//				rst.dst_ip = packet.dst_ip;
//				rst.data = b0;
//				try {
//					sender.sendPacket(rst);
//				} catch (IOException e) {
//					// log.error("发送包发生错误");
//					// nothing to do
//				} catch (Exception e) {
//					log.error("发送包发生错误", e);
//				}
//			}
		}
	}
}
