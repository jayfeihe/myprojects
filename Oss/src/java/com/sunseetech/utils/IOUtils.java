package com.sunseetech.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class IOUtils {

	/**
	 * 获取文件内容
	 * @param file file
	 * @return String
	 * @throws IOException IOException
	 */
	public static String read(File file) throws IOException {
		return new String(getBytes(new FileInputStream(file)));
	}

	/**
	 * 获取Exception的String信息
	 * @param e exception
	 * @return String
	 */
	public static String getStackTraceMsg(Exception e) {
		StringWriter strWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(strWriter));
		return strWriter.toString();
	}

	/**
	 * 获取jar资源
	 * @param fileName fileName
	 * @param encoding encoding
	 * @return String
	 */
	public static String readJarResource(String fileName, String encoding) {
		String content = "";
		try {
			InputStream in = IOUtils.class.getResourceAsStream(fileName);
			content = IOUtils.read(in, encoding);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 获取InputStream的内容
	 * @param in InputStream
	 * @param encoding encoding
	 * @return String
	 */
	public static String read(InputStream in, String encoding) {
		String content = "";
		try {
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(in, encoding));
			String msg = "";
			while ((msg = bufReader.readLine()) != null) {
				content += msg;
			}
			bufReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 写文件
	 * @param file file
	 * @param str str
	 * @throws IOException IOException
	 */
	public static void write(File file, String str) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(file));
		out.print(str);
		out.close();
	}

	/**
	 * 写文件
	 * @param file file
	 * @param inputStream inputStream
	 * @throws IOException IOException
	 */
	public static void write(File file, InputStream inputStream) throws IOException {
		FileOutputStream out =  new FileOutputStream(file);
		int byteCount = 0;
		byte[] bytes = new byte[1024];
		while ((byteCount = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, byteCount);
		}
		inputStream.close();
		out.close();
	}
	/**
	 * 写文件
	 * @param file file
	 * @param bytes bytes
	 * @throws IOException IOException
	 */
	public static void write(File file, byte[] bytes) throws IOException {
		FileOutputStream out =  new FileOutputStream(file);
		out.write(bytes);
		out.close();
	}

	/**
	 * 追加内容
	 * @param file file
	 * @param str str
	 * @throws IOException IOException
	 */
	public static void append(File file, String str) throws IOException {
		RandomAccessFile appFile = new RandomAccessFile(file, "rw");
		appFile.seek(appFile.length());
		appFile.write(str.getBytes());
		appFile.close();
	}

	/**
	 * 获取InputStream的byte[]
	 * @param input InputStream
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] getBytes(InputStream input) throws IOException {
		int c;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((c = input.read()) != -1) {
			output.write(c);
		}
		input.close();
		output.close();
		return output.toByteArray();
	}

	/**
	 * byte[]转成压缩后的byte[]
	 * @param bytes bytes[]
	 * @return bytes[]
	 * @throws IOException IOException
	 */
	public static byte[] getZipedBytes(byte[] bytes) throws IOException {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		GZIPOutputStream output = new GZIPOutputStream(byteOutput);
		output.write(bytes);
		output.close();
		return byteOutput.toByteArray();
	}

	/**
	 * 从InputStream获取解压后的byte[]
	 * @param input InputStream
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] getUnZippedBytes(InputStream input) throws IOException {
		return getBytes(new GZIPInputStream(input));
	}

	/**
	 * 获取object序列化后的压缩byte[]
	 * @param object object
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] serializeWithZip(Serializable object)
			throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		writeSerializeWithZip(object, output);
		output.close();
		return output.toByteArray();
	}

	/**
	 * 获取object序列化后的byte[]
	 * @param object object
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] serialize(Serializable object) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		writeSerialize(object, output);
		output.close();
		return output.toByteArray();
	}

	/**
	 * 压缩序列化object到OutputStream
	 * @param object object
	 * @param baos OutputStream
	 * @throws IOException IOException
	 */
	public static void writeSerializeWithZip(Serializable object,
			OutputStream baos) throws IOException {
		writeSerialize(object, new GZIPOutputStream(baos));
	}

	/**
	 * 序列化object到OutputStream
	 * @param object object
	 * @param baos OutputStream
	 * @throws IOException IOException
	 */
	public static void writeSerialize(Serializable object, OutputStream baos)
			throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(baos);
		output.writeObject(object);
	}

	/**
	 * 从压缩的byte[]中反序列化对象
	 * @param bytes byte[]
	 * @return Object
	 * @throws IOException IOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public static Object deserializeWithUnzip(byte[] bytes) throws IOException,
			ClassNotFoundException {
		return deserializeWithUnzip(new ByteArrayInputStream(bytes));
	}

	/**
	 * 从压缩的InputStream中反序列化对象
	 * @param inputStream InputStream
	 * @return Object
	 * @throws IOException IOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public static Object deserializeWithUnzip(InputStream inputStream)
			throws IOException, ClassNotFoundException {
		return deserialize(new GZIPInputStream(inputStream));
	}

	/**
	 * 从byte[]中反序列化对象
	 * @param bytes byte[]
	 * @return Object
	 * @throws IOException IOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public static Object deserialize(byte[] bytes) throws IOException,
			ClassNotFoundException {
		return deserialize(new ByteArrayInputStream(bytes));
	}

	/**
	 * 从InputStream中反序列化对象
	 * @param inputStream InputStream
	 * @return Object
	 * @throws IOException IOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	public static Object deserialize(InputStream inputStream)
			throws IOException, ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(inputStream);
		return input.readObject();
	}

	/**
	 * 删除文件夹
	 * @param folderPath 文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	}
	/**
	 * 删除指定文件夹下所有文件
	 * @return boolean
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]); //先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]); //再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }

}
