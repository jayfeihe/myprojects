package com.tera.payment.fuyou.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
import org.dom4j.Element;

import com.tera.payment.fuyou.constant.FuOuConstant;
import com.tera.payment.fuyou.model.PayReqBean;
import com.tera.payment.fuyou.model.QueryReqBean;
import com.tera.payment.fuyou.model.QueryReqBean.BusicdType;
import com.tera.payment.fuyou.model.QueryResultBean;
import com.tera.payment.fuyou.model.ResultBean;
import com.tera.payment.fuyou.model.IncomeReqBean;
import com.tera.payment.service.AllinPayTranxService;
import com.tera.util.XMLHandler;

/**
 * 富有支付 发送请求 接口
 * 
 * @author XunXiake
 * 
 */
public class ReqService<T> {

	private final static Logger log = Logger.getLogger(AllinPayTranxService.class);

    private static final String CHARSET = "UTF-8";
	
	private String merid;
	private String key;
	private String reqtype;    //类型 付款： payforreq ， 查询：qrytransreq ， 收款：incomeforreq 
	private String xml;
	private String mac;
	private T xmlBean;
	


	/**
	 * 富友支付 请求接口 唯一 入口
	 * 
	 * @param merid  商户号
	 * @param key 商户KEY
	 * @param reqtype 请求类型
	 * @param xmlType 请求的 XML 实体BEAN
	 */
	public ReqService(String merid, String key, String reqtype, T xmlBean) {
		this.merid = merid;
		this.key = key;
		this.reqtype = reqtype;
		this.xmlBean = xmlBean;
	}

	public String getMerid() {
		return merid;
	}

	public String getKey() {
		return key;
	}

	public String getReqtype() {
		return reqtype;
	}

	public String getXml() {
		return xml;
	}

	public String getMac() {
		return mac;
	}
	public T getXmlBean() {
		return xmlBean;
	}

	/**
	 * 执行请求
	 * @param url
	 * @return
	 */
	public ResultBean requestFuOu(String url) {

		HttpClient client = new DefaultHttpClient();
		try {
			this.xml=ReqService.beanToXml(this.xmlBean,CHARSET,false);
			// 创建 签名
			this.createMac();
			// 设置请求 参数
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("merid", new StringBody(merid, Charset.forName(CHARSET)));
			entity.addPart("reqtype", new StringBody(reqtype, Charset.forName(CHARSET)));
			entity.addPart("xml", new StringBody(xml, Charset.forName(CHARSET)));
			entity.addPart("mac", new StringBody(mac, Charset.forName(CHARSET)));
			// 创建请求
			HttpPost request = new HttpPost(url);
			request.setEntity(entity); // 设置请求 参数
			// request.addHeader("Ticket", sign);
			// 发起情趣 获取 响应
			HttpResponse response = client.execute(request);
			// 得到响应状态
			String resStare = response.getStatusLine().toString();
			// 获取响应实体
			HttpEntity httpentity = response.getEntity();
			if (httpentity != null) {
				// 打印响应内容长度
				System.out.println("Response content length: "
						+ httpentity.getContentLength());
				String rspXml = EntityUtils.toString(httpentity, CHARSET);
				
	        	log.info("富有支付调用完成。"+
	        			"\nresStare:" + resStare+
	        			"\nmerid:" + merid+
	        			"\nreqtype:" + reqtype+
	        			"\nxml:" + xml+
	        			"\nmac:" + mac+"" +
	        			"\nResponse content:" + rspXml);
	        	//返回结果
	        	XMLHandler xmlHandler=new XMLHandler(rspXml);
	        	Element root=xmlHandler.getRootElement();
				ResultBean rtBean=new ResultBean();
				rtBean.setRet(xmlHandler.getElementText(root,"ret"));
				rtBean.setMemo(xmlHandler.getElementText(root,"memo"));
				rtBean.setReqtype(reqtype);	
				List<Element> trans=xmlHandler.getListElement(root,"trans");
				if(trans!=null && !trans.isEmpty()){
					for (Element tran : trans) {
						QueryResultBean qrb=new QueryResultBean();
						qrb.setAccntnm(xmlHandler.getElementText(tran,"accntnm"));
						qrb.setAccntno(xmlHandler.getElementText(tran,"accntno"));
						qrb.setAmt(xmlHandler.getElementText(tran,"amt"));
						qrb.setEntseq(xmlHandler.getElementText(tran,"entseq"));
						qrb.setMemo(xmlHandler.getElementText(tran,"memo"));
						qrb.setMerdt(xmlHandler.getElementText(tran,"merdt"));
						qrb.setOrderno(xmlHandler.getElementText(tran,"orderno"));
						qrb.setReason(xmlHandler.getElementText(tran,"reason"));
						qrb.setResult(xmlHandler.getElementText(tran,"result"));
						qrb.setState(xmlHandler.getElementText(tran,"state"));
						rtBean.tranListAdd(qrb);
					}
				}
				
				return rtBean;
			}else{
				return new ResultBean("null", "请求结果异常，没有返回信息。",reqtype);
			}
		} catch (Exception e) {
			log.error("富有支付请求异常。Exception："+e);
			return new ResultBean("error", e.toString(),reqtype);
		} finally {
			client.getConnectionManager().shutdown();
		}
		
	}

	/**
	 * 生成 加密 码
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String createMac() throws UnsupportedEncodingException {
		String macSource = merid + "|" + key + "|" + reqtype + "|" + xml;
		mac = DigestUtils.md5Hex(macSource.getBytes(CHARSET));
		return mac;
	}

	/**
	 * 生成 XML
	 * @param xmlBean  	格式化对象
	 * @param encoding	编码
	 * @param isFormat	是否格式化 xml 
	 * @return
	 */
	public static String beanToXml(Object obj,String encoding,boolean isFormat) { 
		JAXBContext context;
		String jgXml=null;
		try {
			context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();  
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, isFormat);
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
	        StringWriter writer = new StringWriter();  
	        marshaller.marshal(obj, writer); 
	        jgXml=writer.toString();
//	        System.out.println(jgXml);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
		return jgXml;
	}

	public static <T> T xmlToBean(String xml ,Class<T>  objClass){
		 T t = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(objClass);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
        } catch (Exception e) {  
        	log.error("字符串转XML异常。Exception："+e);
            e.printStackTrace();  
        }  
        return t;  
	}
	
/*	public static void main(String[] args) throws JAXBException {
		
		String url = FuOuConstant.URL;
		String merid = FuOuConstant.MERID;
		String key = FuOuConstant.KEY;
		
		ReqService reqService=null;
		ResultBean jg=null;
		String reqtype=null;
		// 付款
		reqtype=FuOuConstant.PAY_REQ_TYPE;
		PayReqBean prb=new PayReqBean();
		prb.setAccntnm("河道客户");
		prb.setAccntno("6228481198332651979");
		prb.setAmt(10000*100+"");//一千元
		prb.setBankno("0103");//银行代码
		prb.setBranchnm("贵州黔东南分行凯里市支行凯里金井支行");
		prb.setCityno("7130");	//城市代码
		prb.setMemo("放款测试");
		prb.setMerdt(DateUtils.format(new Date(), "yyyyMMdd"));
		prb.setMobile("15588889999");
		prb.setOrderno(System.currentTimeMillis()+"");
		prb.setVer("1.0");
//		reqService=new ReqService<PayReqBean>(merid, key, reqtype, prb);
//		jg=reqService.requestFuOu(url);
		
		
		// 代收 同步
		reqtype=FuOuConstant.SINCOME_REQ_TYPE;
		IncomeReqBean srb=new IncomeReqBean();
		srb.setVer("1.0");
		srb.setMerdt(DateUtils.format(new Date(), "yyyyMMdd"));
		srb.setOrderno(System.currentTimeMillis()+"");
		srb.setBankno("0104");//银行代码
		srb.setAccntnm("河道客户");
		srb.setAccntno("1111111111111111111");
		srb.setAmt(1000000*100+"");//一千元
		srb.setCerttp("0");
		srb.setCertno("412209198301220323");
//		reqService=new ReqService<IncomeReqBean>(merid, key, reqtype, srb);
//		jg=reqService.requestFuOu(url);
		
		// 查询
		reqtype = FuOuConstant.QUERY_TYPE;
		QueryReqBean qrb=new QueryReqBean();
		qrb.setVer("1.0");
		qrb.setBusicd(BusicdType.AP01);
		qrb.setOrderno("2014102800011420526720842");
		qrb.setStartdt("20150105");
		qrb.setEnddt(DateUtils.format(new Date(), "yyyyMMdd"));
//		qrb.setTransst()
		reqService=new ReqService<QueryReqBean>(merid, key, reqtype, qrb);
		jg=reqService.requestFuOu(url);
		
		// 查询
		reqtype = FuOuConstant.QUERY_TYPE;
		 qrb=new QueryReqBean();
		qrb.setVer("1.0");
		qrb.setBusicd(BusicdType.TP01);
		qrb.setStartdt("20141119");
		qrb.setEnddt(DateUtils.format(new Date(), "yyyyMMdd"));
//		qrb.setTransst()
//		reqService=new ReqService<QueryReqBean>(merid, key, reqtype, qrb);
//		jg=reqService.requestFuOu(url);

	}*/
	
	/**
	 * 把系统内不的证件类型 转换成 系统需要的 富有需要的证件类型
	 * @param idType
	 * @return
	 */
	public static String getIdType(String idType){
		Map<String, String> map=new HashMap<String, String>();
		map.put("01", "0");
		map.put("身份证", "0");
		map.put("02", "1");
		map.put("护照", "1");
		map.put("03", "2");
		map.put("军官证", "2");
		map.put("05", "5");
		map.put("户口簿", "5");
		
		map.put("04", "7");
		map.put("港澳台居民往来大陆通行证", "7");
		map.put("99", "7");
		map.put("其他", "7");
		
		String fuiouType=map.get(idType);
		fuiouType=fuiouType==null?"7":fuiouType;
		
		return fuiouType;
	}

}
