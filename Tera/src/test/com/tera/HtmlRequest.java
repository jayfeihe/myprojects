package com.tera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlRequest {
	public static void main(String[] args) throws IOException {  
        URL url = new URL("http://hd-wealth.com/");  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        InputStream inputStream = conn.getInputStream();
        byte[] getData = readInputStream(inputStream);
        String data = new String(getData, "utf-8");  
        System.out.println(data);  
          
    }  

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
          
        bos.close();  
        return bos.toByteArray();  
    }  

}
