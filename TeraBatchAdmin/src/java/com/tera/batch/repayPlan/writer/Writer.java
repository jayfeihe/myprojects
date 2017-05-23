/**
 * 
 */
package com.tera.batch.repayPlan.writer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

public class Writer implements ItemWriter<String> {
	/**
	 * 日志
	 */
	private final static Logger log = Logger.getLogger(Writer.class);
	

	public void write(List<? extends String> items) throws Exception {
		for(String item : items) {
			log.info("===============" + item);			
		}
	}
}
