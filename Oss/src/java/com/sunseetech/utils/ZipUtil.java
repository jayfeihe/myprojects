package com.sunseetech.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;

public class ZipUtil {
	public static void unZipFiles(File zipfile, String descDir) {
		File file = new File(descDir);
		if (!file.exists())
			try {
				file.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				System.out.println("-----解压缩完成--------");
			}

			zf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void compressFiles(List<File> srcfiles, File zipfile) {
		try {
			FileOutputStream fout = new FileOutputStream(zipfile);
			ArchiveOutputStream archOuts = new ArchiveStreamFactory().createArchiveOutputStream("zip", fout);

			if ((archOuts instanceof ZipArchiveOutputStream)) {
				ZipArchiveOutputStream zipOut = (ZipArchiveOutputStream) archOuts;
				for (int i = 0; i < srcfiles.size(); i++) {
					ZipArchiveEntry zipEntry = new ZipArchiveEntry(srcfiles.get(i), srcfiles.get(i).getName());
					zipOut.putArchiveEntry(zipEntry);
					zipOut.write(FileUtils.readFileToByteArray(srcfiles.get(i)));
				}
				zipOut.closeArchiveEntry();
				zipOut.flush();
				zipOut.finish();
				System.out.println("------压缩完成-------");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}