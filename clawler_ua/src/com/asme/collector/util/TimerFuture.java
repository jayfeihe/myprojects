package com.asme.collector.util;


/**
 * @author ASME
 *
 * 2011-7-25
 */
public interface TimerFuture {

	public String getId();

	/**
	 * 获取跟它关联的计时任务
	 * 
	 * @return
	 */
	public TimerTask getTimerTask();

	/**
	 * 计时任务是否已经到期
	 * 对于interval类型的任务,Cancel掉后才返回true
	 * 
	 * @return
	 */
	public boolean isExpired();

	/**
	 * 计时任务是否已经取消
	 * 
	 * @return
	 */
	public boolean isCancelled();

	/**
	 * 取消计时任务
	 */
	public void cancel();
}
