package com.tera.ilog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


public class HttpClientTest {
	
	@Test
	public void postTest() throws Exception  {
		/**
		 *<service name="unscramble">
			<text>password</text>
		 *</service>
		 */
//		String data = "<service name=\"unscramble\">" + "<text>rtsAdmin</text>" + "</service>";
		HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:9081/teamserver/servlet/AjaxServlet");
        ContentProducer cp = new ContentProducer() {
            public void writeTo(OutputStream outstream) throws IOException {
                Writer writer = new OutputStreamWriter(outstream, "UTF-8");
                writer.write("<service name=\"unscramble\">");
                writer.write("<text>rtsAdmin</text>");
                writer.write("</service>");
                writer.flush();
            }
        };
        HttpEntity entity = new EntityTemplate(cp);
        post.setEntity(entity);
        HttpResponse response = httpclient.execute(post);
        System.out.println("===" + response.getStatusLine().getStatusCode());
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
        	System.out.println(response);
            HttpEntity entitys = response.getEntity();
            System.out.println(EntityUtils.toString(entitys));
        }
        httpclient.getConnectionManager().shutdown();
	}

}
