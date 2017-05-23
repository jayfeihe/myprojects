package com.asme.collector.packet.handler;

import com.asme.collector.packet.HttpGetPacket;

/**
 * @author ASME
 *
 * 2012-8-20
 */
public interface HttpGetPacketHandler {

	/**
	 * 根据Http请求包获取伪造的响应 
	 * 返回null的话则当前请求不劫持
	 * 
	 * @param packet
	 * @return
	 */
	public byte[] handle(HttpGetPacket packet);
}
