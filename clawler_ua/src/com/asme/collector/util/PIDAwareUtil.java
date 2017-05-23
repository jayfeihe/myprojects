package com.asme.collector.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * @author ASME
 * 
 *         2011-8-22
 */
public class PIDAwareUtil {

	// PID文件的位置
	private static String PID_FILE;

	// 当前进程的PID
	private static final int PID;
	static {
		String mBeanName = ManagementFactory.getRuntimeMXBean().getName();
		PID = Integer.parseInt(mBeanName.substring(0, mBeanName.indexOf("@")));
	}

	/**
	 * 获取PID
	 * 
	 * @return
	 */
	public static final int getPid() {
		return PID;
	}

	/**
	 * 保存PID到文件中
	 * 
	 * @param pidFile
	 * @throws IOException
	 */
	public static final void savePID2File() throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(PID_FILE);
			writer.write(String.valueOf(PID));
		} finally {
			try {
				if (writer != null) writer.close();
			} catch (Exception e) {
				// Nothing to do
			}
		}
	}

	/**
	 * 从文件中读出PID
	 * 
	 * @return
	 * @throws IOException
	 */
	public static final int readFromFile() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(PID_FILE)));
			String pid = reader.readLine();
			return Integer.parseInt(pid);
		} catch (Exception e) {
			return -1;
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (Exception e) {
				// Nothing to do
			}
		}
	}

	/**
	 * 设置PID文件路径
	 * 
	 * @param pidFile
	 */
	public static final void setPidFilePath(String pidFile) {
		PID_FILE = pidFile;
	}
}
