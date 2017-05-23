package com.tera.report.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 报表表头定义
 * @author QYANZE
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResource {

	/**
	 * 表头中文标题
	 * @return
	 */
	String title();
	/**
	 * 表头排序
	 * @return
	 */
	int order() default 999; 
}
