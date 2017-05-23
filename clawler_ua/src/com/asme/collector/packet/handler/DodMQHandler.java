package com.asme.collector.packet.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.asme.collector.packet.HttpGetPacket;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class DodMQHandler implements HttpGetPacketHandler {
	
	private static final String CLIENTID = "gz234";

	private static final ConcurrentHashMap<String, AtomicInteger> C = new ConcurrentHashMap<String, AtomicInteger>();
	
	private static final ConcurrentHashMap<String, U> URL = new ConcurrentHashMap<String, U>();

	public static void init() {
		
		Thread r = new Thread("listener") {
			@SuppressWarnings("unchecked")
			public void run(){
		        ConnectionFactory connectionFactory;
		        Connection connection = null;
		        Session session;
		        Topic destination;
		        MessageConsumer consumer;
		        connectionFactory = new ActiveMQConnectionFactory("gylsysamq", "wobushini", "tcp://202.107.192.142:61626");
		        try {
		            connection = connectionFactory.createConnection();
		            connection.setClientID(CLIENTID);
		            connection.start();
		            session = connection.createSession(Boolean.FALSE,
		                    Session.AUTO_ACKNOWLEDGE);
		            destination = session.createTopic("urltopic");
		            consumer = session.createDurableSubscriber(destination, "gzhij");
		           for(;!Thread.interrupted();) {
		                TextMessage message = (TextMessage) consumer.receive();
		                String t = message.getText();
						if (t.startsWith("add")) {
							String[] f = t.split("\t");
							if (f.length == 3) {
								URL.put(f[1], new U(f[1], f[2], 0));
							}
							if (f.length == 4) {
								URL.put(f[1], new U(f[1], f[2], Integer.parseInt(f[3])));
							}
						} else
		                if(t.startsWith("chst")) {
		                	String[] f = t.split("\t");
		                	if(f.length == 3) {
		                		List<String> ids = JSON.parseObject(f[1], ArrayList.class);
		                		int status = Integer.parseInt(f[2]);
		                		for(String id : ids) {
		                			U u = URL.get(id);
		                			if(u != null) u.setStatus(status);
		                		}
		                	}
		                }else
		                if(t.startsWith("del")) {
		                	String[] f = t.split("\t");
		                	if(f.length == 2) {
		                		List<String> ids = JSON.parseObject(f[1], ArrayList.class);
		                		for(String id : ids) {
		                			URL.remove(id);
		                		}
		                	}
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                if (null != connection)
		                    connection.close();
		            } catch (Throwable ignore) {
		            }
		        }
			}
		};
		r.setDaemon(true);
		r.start();
		
		Thread t = new Thread("sender") {
			public void run(){
			 Connection connection = null;
		        Session session = null;
		        MessageProducer producer = null;
		        try {
		            // 创建链接工厂
		            ConnectionFactory factory = new ActiveMQConnectionFactory("gylsysamq", "wobushini", "tcp://202.107.192.142:61626");
		            // 通过工厂创建一个连接
		            connection = factory.createConnection();
		            // 启动连接
		            connection.start();
		            // 创建一个session会话
		            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		            
		            // 创建一个消息队列
		            Destination destination = session.createQueue("urlnq");
		            // 创建消息制作者
		            producer = session.createProducer(destination);
		            // 设置持久化模式
		            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        for(;!Thread.interrupted();) {
		        	
		        	for(Iterator<Entry<String, AtomicInteger>> it = C.entrySet().iterator(); it.hasNext();) {
		        		Entry<String, AtomicInteger> e = it.next();
		        		it.remove();
		        		Map<String, Object> m = new HashMap<String, Object>();
		        		m.put("id", e.getKey());
		        		m.put("count", e.getValue().get());
		        		try {
							producer.send(session.createTextMessage(JSON.toJSONString(m)));
							session.commit();
						} catch (Exception ee) {
							ee.printStackTrace();
						}
		        	}
		        	
		        	try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
			String srcip = packet.srcip;
			if (srcip == null) return null;
			String host = packet.headers.get("host");
			if (host == null) return null;
			String ua = packet.headers.get("user-agent");
			if (ua == null) return null;
			Collection<U> us = URL.values();
			for(U u : us) {
				if(u.getStatus() == 1) continue;
				if (packet.url.startsWith(u.getUrl())) {
					AtomicInteger c = C.get(u.getId());
					if (c == null) {
						c = new AtomicInteger(1);
						c = C.putIfAbsent(u.getId(), c);
						if (c != null)
							c.getAndIncrement();
					} else {
						c.getAndIncrement();
					}
					break;
				}
			}
		}
		return null;
	}
	
	public static class U {
		private String id;
		private String url;
		private int status;
		public U(String id, String url, int status) {
			this.id = id;
			this.url = url;
			this.status = status;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
	}
}
