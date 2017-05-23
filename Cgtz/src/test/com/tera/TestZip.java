package com.tera;

public class TestZip {

	/*public static void main(String[] args) {
		
		
		File srcfile = new File("D:\\22");
				
		TestZip.compressFiles(srcfile.listFiles(), new File("d:\\22.zip"));
	}
	
	public static void compressFiles(File[] srcfile, File zipfile) {
		try {
			FileOutputStream fout = new FileOutputStream(zipfile);
			ArchiveOutputStream archOuts = new ArchiveStreamFactory().createArchiveOutputStream("zip", fout);

			if ((archOuts instanceof ZipArchiveOutputStream)) {
				ZipArchiveOutputStream zipOut = (ZipArchiveOutputStream) archOuts;
				for (int i = 0; i < srcfile.length; i++) {
					ZipArchiveEntry zipEntry = new ZipArchiveEntry(srcfile[i], srcfile[i].getName());
					zipOut.putArchiveEntry(zipEntry);
					zipOut.write(FileUtils.readFileToByteArray(srcfile[i]));
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
	}*/
}
