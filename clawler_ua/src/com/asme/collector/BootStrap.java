/**
 * 2012-11-6 下午10:05:56 by asme
 */
package com.asme.collector;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import com.asme.collector.util.PIDAwareUtil;

/**
 * @author asme
 *
 * 2012-11-6 下午10:05:56
 */
public class BootStrap {

	static {

		// 配置log4j
		PropertyConfigurator.configure("conf/log4j.properties");
	}
	private static final Log log = LogFactory.getLog(BootStrap.class);

	// 配置文件类路径
	private static final String CONFIG_LOCATION = "conf/applicationContext.xml";

	// PID文件的位置
	private static String PID_FILE = "conf/server.pid";

	// spring 上下文环境
	private static FileSystemXmlApplicationContext applicationContext;


	/**
	 * 启动服务器
	 */
	private static void startup() throws Exception {
		applicationContext = new FileSystemXmlApplicationContext(CONFIG_LOCATION);
	}

	/**
	 * 关闭服务器
	 */
	private static void shutdown() throws Exception {
		applicationContext.getBean(Hijacker.class).destroy();
		new File(PID_FILE).delete();
	}

	/**
	 * 根据PID关闭服务器
	 */
	private static void shutdownByPid() {

		try {
			PIDAwareUtil.setPidFilePath(PID_FILE);
			int pid = PIDAwareUtil.readFromFile();
			if(pid == -1) {
				log.info("PID文件不存在");
				return;
			}
			String osname = System.getProperty("os.name").toLowerCase();
			String cmd = (osname.indexOf("windows") != -1 ? "ntsd -c q -p " : "kill ") + pid;
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			log.error("关闭服务器发生错误", e);
		}
	}

	/**
	 * 入口
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) {

		// 关闭指令
		if (args.length != 0 && "-shutdown".equals(args[0])) {
			shutdownByPid();
			return;
		}

		// 启动
		try {
			startup();
			PIDAwareUtil.setPidFilePath(PID_FILE);
			PIDAwareUtil.savePID2File();
			log.info("服务器启动成功, PID: " + PIDAwareUtil.getPid());
		} catch (Exception e) {
			log.error("服务器启动失败", e);
			try { shutdown(); } catch (Exception ex) {
				// nothing to do
			}
			return;
		}

		// 安装退出的信号处理器
		SignalHandler handler = new ExitSignalHandler();
		Signal.handle(new Signal("TERM"), handler);
		Signal.handle(new Signal("INT"), handler);
	}

	/**
	 * @author asme
	 *
	 * 2012-11-6 下午10:14:16
	 */
	private static class ExitSignalHandler implements SignalHandler {

		/**
		 * (non-Javadoc)
		 * 
		 * @see sun.misc.SignalHandler#handle(sun.misc.Signal)
		 */
		public void handle(Signal signal) {

			log.info("收到信号:[" + signal.getName() + " : " + signal.getNumber() + "], 服务器开始关闭");

			try {
				shutdown();
				log.info("服务器关闭成功");
			} catch (Exception e) {
				log.error("服务器关闭失败", e);
			}
		}
	}
}
