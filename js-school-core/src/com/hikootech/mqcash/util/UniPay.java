package com.hikootech.mqcash.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniPay {
	private static Logger log = LoggerFactory.getLogger(UniPay.class);

	static String account = ConfigUtils.getProperty("unipay.account");
	static String key = ConfigUtils.getProperty("unipay.key");
	static String url = ConfigUtils.getProperty("unipay.url");
	
	public static void main(String[] args) {
		String s=requestPersonalReport("6228480841461627710");
		System.out.println(s);
	}
	
	//卡号、姓名对应关系认证
	private String isMatchable(String cardNumber,String name){
		Integer type = 1; //
		
		String param = type.toString() + cardNumber + name + account;
		String sign = md5(param + md5(key));
		String url;
		try {
			url = "https://upay.sfxxrz.com/service/isMatchable?type=" + type.toString() + "&cardNumber="
						+ cardNumber + "&name=" + URLEncoder.encode(name, "UTF-8") + "&account=" + account + "&sign=" + sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		String json = getHtml(url);
		System.out.println(json);
		return json;
	}

	//个人数据报告
	public static String requestPersonalReport(String cardNumber) {
		String param = cardNumber + account;
		String sign = md5(param + md5(key));
		String request = url+"cardNumber=" + cardNumber + "&account=" + account + "&sign=" + sign;
		String json = getHtml(request);
		log.info(json);
		return json;
	}

	static String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			log.error(e.getMessage());
			return "";
		} 
	}

	static String getHtmls(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			return response.toString();
		} catch (Exception e) {
			log.error("请求银联接口失败："+e);
			return "";
		}
	}
	static String getHtml(String urlStr){
		String repString="";
		ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {
            URL url = new URL(urlStr);
            /*
             * use ignore host name verifier
             */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


            // Prepare SSL Context
            TrustManager[] tm = { ignoreCertificationTrustManger };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);
            
            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[512];
            int length = reader.read(bytes);


            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);


            // result.setResponseData(bytes);
            System.out.println(buffer.toString());
            reader.close();
            
            connection.disconnect();
            repString= new String (buffer.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
        return repString;
	}
	public static String replaceBlank(String str){
		if(StringUtils.isEmpty(str))
			return "";
		Pattern pattern = Pattern.compile("\\s");
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("");
	}
	 /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };
    /**
     * Ignore Certification
     */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

        private X509Certificate[] certificates;

        @Override
        public void checkClientTrusted(X509Certificate certificates[],
                String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                System.out.println("init at checkClientTrusted");
            }
        }


        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate,
                String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                System.out.println("init at checkServerTrusted");
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };
}
