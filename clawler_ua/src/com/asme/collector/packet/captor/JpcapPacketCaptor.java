package com.asme.collector.packet.captor;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asme.collector.util.LinkedTransferQueue;

/**
 * @author ASME
 * 
 *         2012-7-20
 */
public class JpcapPacketCaptor {

	private static final Log log = LogFactory.getLog(JpcapPacketCaptor.class);
	
	// tcpdump格式的过滤HTTP GET包
//	private static final String HTTP_GET_FILTER = "tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420";

	private static final int DEFAULT_MIN_DATA_LEN = 100;

	// 要抓的网卡
	private NetworkInterface intf;

	// 抓到的包和来不及处理的包
	private long capted = 0;
	private long failed = 0;

	// 抓到的数据包队列
	private LinkedTransferQueue<TCPPacket>[] packetQueues;
	private int queueSize;

	// 抓包器
	private JpcapCaptor captor = null;

	// 是否混杂模式
	private boolean promiscMode;

	// 数据最小长度,小于这个长度的包不抓
	private int minDataLen = DEFAULT_MIN_DATA_LEN;

	// 抓包线程
	private Thread captThread;

	/**
	 * 初始化
	 */
	public JpcapPacketCaptor() {
		captThread = new Thread("CaptorThread") {
			public void run() {
				try {
					captor = JpcapCaptor.openDevice(intf, 1536, promiscMode, 10);
					captor.setNonBlockingMode(false);
//					captor.setFilter(HTTP_GET_FILTER, true);
					log.info("打开网卡成功,开始抓包");
				} catch (IOException e) {
					log.error("打开网卡发生错误", e);
				}

				captor.loopPacket(-1, new PacketReceiver() {
					private int index = 0;
					public void receivePacket(Packet pkg) {
						if (pkg instanceof TCPPacket) {

							TCPPacket p = (TCPPacket) pkg;

							// 只抓一定长度以上的GET包,GET包的过滤由JPCAP来做
							if(p.data.length < minDataLen || p.dst_port != 80) return;

							capted++;
							int cur = index;
							index++; if(index == queueSize) index = 0;
							int i = cur;
							for (int j = 0; j < queueSize; j++) {
								if (packetQueues[i++].tryTransfer(p)) return;
								if (i == queueSize) i = 0;
							}
							failed++;
						}
					}
				});
				log.info("抓包线程已经关闭");
			}
		};
		captThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.error("线程[" + t.getName() + "]发生异常", e);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.asme.htb.packet.captor.PacketCaptor#start()
	 */
	public void start() {
		captThread.setPriority(Thread.MAX_PRIORITY);
		captThread.start();
	}

	/* (non-Javadoc)
	 * @see com.asme.htb.packet.captor.PacketCaptor#stop()
	 */
	public boolean stop() {
		if (captor != null) {
			try {
				captor.breakLoop();
			} catch (Throwable e) {
				log.info("抓包线程关闭发生错误", e);
			}
		}
		return false;
	}
	
	public boolean isAlive() {
		return captThread.isAlive();
	}

	/**
	 * @param packetQueues the packetQueues to set
	 */
	public void setPacketQueues(LinkedTransferQueue<TCPPacket>[] packetQueues) {
		this.packetQueues = packetQueues;
		queueSize = packetQueues.length;
	}

	/* (non-Javadoc)
	 * @see com.asme.htb.packet.captor.PacketCaptor#getCapted()
	 */
	public long getCapted() {
		return capted;
	}

	/**
	 * @return the failed
	 */
	public long getFailed() {
		return failed;
	}

	/* (non-Javadoc)
	 * @see com.asme.htb.packet.captor.PacketCaptor#setCaptIntf(jpcap.NetworkInterface)
	 */
	public void setCaptIntf(NetworkInterface intf) {
		this.intf = intf;
	}

	/**
	 * @return the promiscMode
	 */
	public boolean isPromiscMode() {
		return promiscMode;
	}

	/**
	 * @param promiscMode the promiscMode to set
	 */
	public void setPromiscMode(boolean promiscMode) {
		this.promiscMode = promiscMode;
	}

	/**
	 * @return the minDataLen
	 */
	public int getMinDataLen() {
		return minDataLen;
	}

	/**
	 * @param minDataLen the minDataLen to set
	 */
	public void setMinDataLen(int minDataLen) {
		this.minDataLen = minDataLen;
	}
}
