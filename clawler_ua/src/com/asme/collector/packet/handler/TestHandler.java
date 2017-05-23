package com.asme.collector.packet.handler;

import org.apache.log4j.Logger;

import com.asme.collector.packet.HttpGetPacket;

/**
 * @author ASME
 *
 *         2012-8-21
 */
public class TestHandler implements HttpGetPacketHandler {
	private static final Logger log = Logger.getLogger(TestHandler.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.asme.hijack.HttpPacketHandler#handle(com.asme.hijack.HttpReqPacket)
	 */
	@Override
	public byte[] handle(HttpGetPacket packet) {
		
		if(packet == null) return null;
		if(packet.isPOST) {
			packet.body.flip();
			System.out.println(new String(packet.body.array()));
		}
		return null;
	}

}
