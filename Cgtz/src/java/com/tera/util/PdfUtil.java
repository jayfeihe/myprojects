package com.tera.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
/**
 * @author wallace
 *
 */
public class PdfUtil {

	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(PdfUtil.class);

	/**
	 * 将html答应到PDF
	 * @param pdfOut pdf输出流
	 * @param htmlIn html输入流
	 * @param encoding 编码格式
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void pringHtml2Pdf(OutputStream pdfOut, InputStream htmlIn, String header, String encoding) throws DocumentException, IOException {
		// step 1
		Document document = new Document(PageSize.A4, 50, 50, 100, 100);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, pdfOut);
		Rectangle rect = new Rectangle(36, 54, 559, 788); //页眉页脚
		System.out.println(rect.getLeft()+","+rect.getBottom()+","+rect.getRight()+","+rect.getTop());
//		rect.setBorderColor(BaseColor.BLACK);
		writer.setBoxSize("art", rect);
		writer.setPageEvent(new HeaderFooter(header));
		// step 3
		document.open();
		// step 4
		if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlIn, null, Charset.forName(encoding), new AsianFontProvider());
		// step 5
		document.close();
		log.info("PDF Created!");
	}

	/**
	 * 将html答应到PDF
	 * @param pdfOut pdf输出流
	 * @param html html字符串
	 * @param encoding 编码格式
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void pringHtml2Pdf(OutputStream pdfOut, String html, String header, String encoding) throws DocumentException, IOException {
		// step 1
		Document document = new Document(PageSize.A4,50,50,100,100);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, pdfOut);
		Rectangle rect = new Rectangle(36, 54, 559, 788); //注意x，y坐标是以左上角为坐标原点的
		System.out.println(rect.getLeft()+","+rect.getBottom()+","+rect.getRight()+","+rect.getTop());
//		rect.setBorderColor(BaseColor.BLACK);
		writer.setBoxSize("art", rect);
		writer.setPageEvent(new HeaderFooter(header));
		// step 3
		document.open();
		// step 4
		if (encoding == null || "".equals(encoding)) {
			encoding = "UTF-8";
		}
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.getBytes(encoding)), null,
				Charset.forName(encoding), new AsianFontProvider());
		// step 5
		document.close();
		log.info("PDF Created!");
	}
	
	/**
	 * 重新操作pdf，增加内容
	 * @param putInByte pdf的字节数组
	 * @param pdfOut pdf输出流
	 * @param url 增加内容的url
	 * @param pos 存放位置的map<int key,int[] value>
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void addContent(byte[] putInByte,OutputStream pdfOut,String url,Map pos) throws IOException, DocumentException{
		   //创建一个pdf读入流 
	      PdfReader reader = new PdfReader(putInByte);  
	      //根据一个pdfreader创建一个pdfStamper.用来生成新的pdf. 
	      PdfStamper stamper = new PdfStamper(reader,pdfOut);  
	      //页数是从1开始的 
	      for (int i=1; i<=reader.getNumberOfPages(); i++){ 
	    	 
	         //获得pdfstamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上. 
	          PdfContentByte over = stamper.getOverContent(i); 
	          //用pdfreader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息. 
	          PdfDictionary p = reader.getPageN(i); 
	          //拿到mediaBox 里面放着该页pdf的大小信息. 
	         PdfObject po =  p.get(new PdfName("MediaBox")); 
	         System.out.println(po.isArray()); 
	         //po是一个数组对象.里面包含了该页pdf的坐标轴范围. 
	         PdfArray pa = (PdfArray) po; 
	         System.out.println(pa.size()); 
	         //看看y轴的最大值. 
	         System.out.println(pa.getAsNumber(pa.size()-1)); 
	          //创建一个image对象. 
//	          Image image = Image.getInstance("http://127.0.0.1:8080/Hedao/seal/getSealImage.do?contractNo=10010"); 
	         Image image = Image.getInstance(url); 
	          //设置image对象的输出位置pa.getAsNumber(pa.size()-1).floatValue() 是该页pdf坐标轴的y轴的最大值 
//	          image.setAbsolutePosition(0,pa.getAsNumber(pa.size()-1).floatValue()-100);//0, 0, 841.92, 595.32
	          image.scaleAbsolute(100,100);//控制图片大小
	          //如果扣章位置没有给出，则每页都会在如下位置扣章
	          if(pos!=null){
	        	  if(pos.get(i)!=null){
	        		  image.setAbsolutePosition( ((int[])pos.get(i))[0],((int[])pos.get(i))[1]);
	        		  over.addImage(image); 
	        	  }
	    	  }else{
	    		  image.setAbsolutePosition(350,130);
	    		  over.addImage(image); 
	    	  }
	      } 
	      stamper.close(); 
	      log.info("PDF addContent OVER!");
	  }

}

 
/**
 * @author wallace
 *
 */
class AsianFontProvider extends XMLWorkerFontProvider {

	@Override
	public Font getFont(final String fontname, final String encoding,
			final boolean embedded, final float size, final int style,
			final BaseColor color) {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Font font = new Font(bf, size, style, color);
		font.setColor(color);
		return font;
	}

}

/**
 * @author wallace
 *
 */
class HeaderFooter extends PdfPageEventHelper {

	/**
	 * headerText
	 */
	private String headerText = "";

	/**
	 * @param headerText headerText
	 */
	public HeaderFooter(String headerText) {
		this.headerText = headerText;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {
			Rectangle rect = writer.getBoxSize("art");
			PdfContentByte cb = writer.getDirectContent();
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			cb.setFontAndSize(bf, 10);

			Paragraph header = new Paragraph(headerText, new Font(bf));
			// header.add(new Chunk(new LineSeparator()));
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_RIGHT,
					header,
					rect.getRight(), rect.getTop(), 0); //注意x，y坐标是以左下角为坐标原点的
			System.out.println(rect.getRight()+","+rect.getTop());
			if (headerText != null && !"".equals(headerText)) {
				ColumnText.showTextAligned(writer.getDirectContent(),
						Element.ALIGN_CENTER,
						new Paragraph(new Chunk(new LineSeparator())),
						rect.getRight() - 5, rect .getTop() - 5, 0);
			}

			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_CENTER,
					new Paragraph(String.format("第 %d 页", writer.getPageNumber()), new Font(bf)),
					(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}