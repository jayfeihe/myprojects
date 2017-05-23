package com.asme.collector.util;

/**
 * @author ASME
 *
 * 2011-7-25
 */
public interface Timer {

	/**
	 * 开始计时
	 */
	public void startup();

	/**
	 * 停止计时
	 */
	public void shutdown();

	/**
	 * 添加一个计时任务
	 * 
	 * @param task
	 * @return
	 */
	public TimerFuture timing(TimerTask task);
}
