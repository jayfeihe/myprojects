package com.tera.batch.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;



public class BatchProcessException  extends Exception {
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(BatchProcessException.class);

	   public BatchProcessException(String message) {
			super(message);
		    }

	   public BatchProcessException(String message, Exception exception) {
	        super(message, exception);
	    }
	   public BatchProcessException(Exception exception) {
	        super( exception);
	    }
}
