package com.asme.collector.util;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;

/**
 * @author ASME
 * 
 *         2012-7-18
 */
public class NetInterfaceUtil {
	
	private static final char[] HEXS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

	/**
	 * 根据IP地址获取对应的网卡
	 * 
	 * @param ip
	 * @return
	 */
	public static NetworkInterface getIntfByIp(String ip) {
		if (ip == null)
			throw new IllegalArgumentException("网卡IP名不能为空");
		NetworkInterface[] intfs = JpcapCaptor.getDeviceList();
		for (NetworkInterface intf : intfs) {
			for (NetworkInterfaceAddress addr : intf.addresses)
				if (addr.address.getHostAddress().equals(ip))
					return intf;
		}
		return null;
	}

	/**
	 * 根据网卡名称获取对应的网卡
	 * 
	 * @param name
	 * @return
	 */
	public static NetworkInterface getInftByName(String name) {
		if (name == null)
			throw new IllegalArgumentException("网卡名不能为空");
		NetworkInterface[] intfs = JpcapCaptor.getDeviceList();
		for (NetworkInterface intf : intfs) {
			if (name.equalsIgnoreCase(intf.name))
				return intf;
		}
		return null;
	}

	/**
	 * 根据MAC获取网卡
	 * @param mac
	 * @return
	 */
	public static NetworkInterface getIntfByMACAddr(String mac) {
		if (mac == null) throw new IllegalArgumentException("MAC地址不能为空");
		String[] macs = mac.split(":");
		if (macs.length != 6) throw new IllegalArgumentException("MAC地址" + mac + "不合法");
		NetworkInterface[] intfs = JpcapCaptor.getDeviceList();
		for(NetworkInterface intf : intfs) {
			boolean match = true;
			byte[] bmacs = intf.mac_address;
			for(int i = 0; i < 6; i++) {
				String t = macs[i].toUpperCase();
				if (!(t.length() == 2 && t.charAt(0) == HEXS[(bmacs[i] >>> 4) & 0x0F] && t.charAt(1) == HEXS[bmacs[i] & 0x0F])) {
					match = false;
					break;
				}
			}
			if(match) return intf;
		}
		return null;
	}
}
