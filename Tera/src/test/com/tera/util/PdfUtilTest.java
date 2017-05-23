package com.tera.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.junit.Test;

public class PdfUtilTest {

	@Test
	public void pringHtml2PdfTest() {
		try {
			OutputStream pdfOut = new FileOutputStream("xml/pdf.pdf");
			InputStream htmlIn = new FileInputStream("xml/index.html");
			PdfUtil.pringHtml2Pdf(pdfOut, htmlIn, "天瑞软件", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void pringHtml2PdfTest2() {
		try {
			OutputStream pdfOut = new FileOutputStream("xml/pdf2.pdf");
			InputStream htmlIn = new FileInputStream("xml/index2.html");
			PdfUtil.pringHtml2Pdf(pdfOut, htmlIn, "天瑞软件", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void pringHtml2PdfTest3() {
		try {
			OutputStream pdfOut = new FileOutputStream("xml/pdf3.pdf");
			InputStream htmlIn = new FileInputStream("xml/index3.html");
			PdfUtil.pringHtml2Pdf(pdfOut, htmlIn, "天瑞软件",  "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void pringHtml2PdfTest4() {
		try {
			OutputStream pdfOut = new FileOutputStream("xml/pdf4.pdf");
			InputStream htmlIn = new FileInputStream("xml/index4.html");
			PdfUtil.pringHtml2Pdf(pdfOut, htmlIn,  "天瑞软件", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void zipPdf() {
		try {
			//将pdf流输入到byte流中
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			InputStream htmlIn = new FileInputStream("xml/index4.html");
			PdfUtil.pringHtml2Pdf(baos, htmlIn,  "天瑞软件", "");
			
			FileOutputStream fileOutputStream = new FileOutputStream("xml/pdf.zip");
           
            ZipOutputStream out = new ZipOutputStream(fileOutputStream);
            out.setEncoding("GBK");
            
			ZipEntry entry = new ZipEntry("测试.pdf");
			entry.setSize(baos.toByteArray().length);
			out.putNextEntry(entry);
			out.write(baos.toByteArray());
			
			out.closeEntry();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
