/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Yang Weifeng
 * @version 1.0
 */
public class DataSourceUtils {

	/**
	 * dataSource
	 */
	private static DataSource dataSource;


	/**
	 * @param jndi jndi
	 * @return DataSource
	 */
	public static synchronized DataSource getDataSource(String jndi) {
		if (dataSource == null) {
			try {
				dataSource = (DataSource) new InitialContext()
						.lookup(jndi);
			} catch (NamingException e) {
				throw new DataSourceLookupFailureException(
						"cannot find data source by jndi name [" + jndi + "]",
						e);
			}
		}
		return dataSource;
	}

}

/**
 * @author Yang Weifeng
 */
@SuppressWarnings("serial")
class DataSourceLookupFailureException extends RuntimeException {

	/**
	 * @param message message
	 * @param e Exception
	 */
	public DataSourceLookupFailureException(String message, Exception e) {
		super(message, e);
	}

}

