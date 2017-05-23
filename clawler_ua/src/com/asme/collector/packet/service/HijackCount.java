/**
 * 
 */
package com.asme.collector.packet.service;

/**
 * @author asme
 * 
 */
public class HijackCount {

	private String pid;
	private String ymd;
	private int hijacked;
	private int beenHijacked;
	
	public HijackCount(String pid, String ymd, int hijacked, int beenHijacked) {
		this.pid = pid;
		this.ymd = ymd;
		this.hijacked = hijacked;
		this.beenHijacked = beenHijacked;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @return the ymd
	 */
	public String getYmd() {
		return ymd;
	}

	/**
	 * @return the hijacked
	 */
	public int getHijacked() {
		return hijacked;
	}

	/**
	 * @return the beenHijacked
	 */
	public int getBeenHijacked() {
		return beenHijacked;
	}
}
