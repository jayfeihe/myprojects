package com.tera.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipLong;

/**
 * @author wallace
 *
 */
public class ZipUtils {

	/**
	 * 解压zip文件
	 * @param zipInputPath ZipInputPath
	 * @param zipOutputPath ZipOutputPath
	 * @throws Exception Exception
	 */
	public static Map<String, String> unZip(String zipInputPath, String zipOutputPath) throws Exception {
		ZipFile zipFile = new ZipFile(zipInputPath, "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();
		Map<String, String> rtMap=new HashMap<String, String>();
		
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();

			if (entry.isDirectory()) {
				File dir = new File(zipOutputPath + entry.getName());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				continue;
			}
			byte[] buf = IOUtils.getBytes(zipFile.getInputStream(entry));
			File file = new File(zipOutputPath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			IOUtils.write(file, buf);
			String rul=file.getPath().replace("\\", "/");
			rtMap.put(file.getName(), rul);
		}
		zipFile.close();
		return rtMap;
	}
	
	
	// 用于 传到 云上面的  特殊 解压方法
	public static Map<String, List<Object>> unZip(ZipFile zipFile) throws Exception {
		// 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();
		Map<String, List<Object>> rtMap=new HashMap<String, List<Object>>();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			List<Object> ls=new ArrayList<Object>();
			ls.add(entry.getSize());
			ls.add(zipFile.getInputStream(entry));
			rtMap.put(entry.getName(), ls);
		}
		return rtMap;
	}
	
	
	public static void main(String[] ager){
		try {
			Map<String, String> map=unZip("G:/邮件营销/QQ采集助手.zip", "G:/邮件营销/img/");
			for (String key : map.keySet()) {
				System.out.print("name:"+key);
				System.out.println("\turl:"+map.get(key));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
