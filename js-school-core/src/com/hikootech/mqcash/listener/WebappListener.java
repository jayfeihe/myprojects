/**
 * <pre>
 * Copyright Notice:
 *    Copyright (c) 2005-2009 China Financial Certification Authority(CFCA)
 *    A-1 You An Men Nei Xin An Nan Li, Xuanwu District, Beijing ,100054, China
 *    All rights reserved.
 * 
 *    This software is the confidential and proprietary information of
 *    China Financial Certification Authority (&quot;Confidential Information&quot;).
 *    You shall not disclose such Confidential Information and shall use 
 *    it only in accordance with the terms of the license agreement you 
 *    entered into with CFCA.
 * </pre>
 */

package com.hikootech.mqcash.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.xml.DOMConfigurator;


import payment.api.system.PaymentEnvironment;


public class WebappListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        head();
        try {
            String systemConfigPath = servletContextEvent.getServletContext().getInitParameter("system.config.path");
            String paymentConfigPath = servletContextEvent.getServletContext().getInitParameter("payment.config.path");
            
            // Log4j
            String log4jConfigFile = systemConfigPath + File.separatorChar + "log4j.xml";
            System.out.println(log4jConfigFile);
            DOMConfigurator.configure(log4jConfigFile);
            
            // 初始化支付环境
            PaymentEnvironment.initialize(paymentConfigPath);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void head() {
        System.out.println("==========================================");
        System.out.println("China Payment & Clearing Network Co., Ltd.");
        System.out.println("Payment and Settlement System");
        System.out.println("Institution Simulator v1.7.8.6");
        System.out.println("==========================================");
    }

}
