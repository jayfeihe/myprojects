package com.tera.generater;

import com.tera.generater.factory.CodeGenerateFactory;

public class GenerateTest {
	
	public static void main(String[] args) {
		String tableName = "T_SYSLOG";
		String entityPackage = "syslog";
		String jspPath = "syslog";
		CodeGenerateFactory factory = new CodeGenerateFactory();
		factory.initDBTables();
		factory.codeGenerate(tableName, entityPackage, jspPath);
	}

}