package com.tera.rule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.stereotype.Service;


@Service
public class BrmsService {

	/**
	 * 线程安全类，缓存agent实例
	 */
	private static ConcurrentHashMap<String, KnowledgeAgent> agentMap = new ConcurrentHashMap<String, KnowledgeAgent>();

	/**
	 * 获取Classpath下的KnowledgeBase实例
	 * @param changeSet
	 * @return
	 * @throws Exception
	 */
	public static KnowledgeBase getClassPathInstance(String changeSet) throws Exception {
		if (null == changeSet || "".equals(changeSet)) {
			return null;
		}
		if (agentMap.get(changeSet) == null) {
			KnowledgeAgent kagent = getClassPathKnowledgeAgent(changeSet, changeSet, 30);
			KnowledgeAgent old = agentMap.putIfAbsent(changeSet, kagent);
			if(old != null) {
				kagent = old;
			}
			KnowledgeBase kbase = kagent.getKnowledgeBase();
			System.out.println("新建KAgent：" + changeSet);
			return kbase;
		} else {
			System.out.println("重用KAgent：" + changeSet);
			return agentMap.get(changeSet).getKnowledgeBase();
		}
	}


	/**
	 * 获取URL下的KnowledgeBase实例
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static KnowledgeBase getURLInstance(String url) throws Exception {
		if (null == url || "".equals(url)) {
			return null;
		}
		if (agentMap.get(url) == null) {
			KnowledgeAgent kagent = getURLKnowledgeAgent(url, url, 30);
			KnowledgeAgent old = agentMap.putIfAbsent(url, kagent);
			if(old != null) {
				kagent = old;
			}
			KnowledgeBase kbase = kagent.getKnowledgeBase();
			System.out.println("新建KAgent：" + url);
			return kbase;
		} else {
			System.out.println("重用KAgent：" + url);
			return agentMap.get(url).getKnowledgeBase();
		}
	}

	/**
	 * 调用决策引擎
	 * @param kbase 规则库
	 * @param globals 全局变量，全局变量通常用作返回，全局变量不会插入到Workmemery，rete算法可能失效
	 * @param objs 决策对性 Fact
	 * @param flowId 规则流ID
	 */
	public void callRuleEnging(KnowledgeBase kbase,
			Map<String, Object> globals, List<Object> objs, String flowId) {
		try {
			long start = System.currentTimeMillis();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			if (globals != null){
				for (Map.Entry<String, Object> entry : globals.entrySet()) {
				     ksession.setGlobal(entry.getKey(), entry.getValue());
				}
			}
			if (objs != null) {
				for (Object object : objs) {
					if (object != null) {
						ksession.insert(object);
					}
				}
			}
			//打印触发规则
			ksession.addEventListener(new DefaultAgendaEventListener() {
				public void afterActivationFired(AfterActivationFiredEvent event) {
					super.afterActivationFired(event);
					System.out.println("触发规则" + event.getActivation().getRule().getName());
				}
			});
			if (flowId != null && !"".equals(flowId)) {
				ksession.startProcess(flowId); //注意是rf属性的ID
			}
			ksession.fireAllRules();
			System.out.println("=========执行完成=========耗时：" + (System.currentTimeMillis() - start) + "=====");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * 编译决策表
	 * @param file
	 * @param toDir
	 * @return
	 * @throws Exception
	 */
	public static String compileDtable(String file, String toDir) throws Exception {
		SpreadsheetCompiler sc = new SpreadsheetCompiler();
		File f = new File(file);
		String fileName = f.getName();
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		FileInputStream xlsStream = new FileInputStream(f);
		String drlFileTmp = sc.compile(xlsStream, InputType.XLS);
		if (toDir != null && !"".equals(toDir)) {
			File drlFile = new File(toDir + "/" + name + ".drl");
			FileWriter writer = new FileWriter(drlFile);
			writer.write(drlFileTmp);
			writer.close();
		}
		return drlFileTmp;
	}
	
	/**
	 * 编译KBase
	 * @param changeSet
	 * @return
	 */
	public static KnowledgeBase compileClassPathKBase(String changeSet) {
		if(null == changeSet || "".equals(changeSet)) {
			return null;
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		//通过changeSet xml加载规则库
		kbuilder.add(ResourceFactory.newClassPathResource(changeSet), ResourceType.CHANGE_SET);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			System.out.println("编译失败");
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}			
			return kbase;
		}
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		System.out.println("编译成功");
		return kbase;
	}

	/**
	 * @param agentName
	 * @param changeSet
	 * @param interval
	 * @return
	 */
	private static KnowledgeAgent getClassPathKnowledgeAgent(String agentName, String changeSet, long interval) {
	    ResourceChangeScannerConfiguration sconf =
	    		ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
	    sconf.setProperty("drools.resource.scanner.interval", interval + "");
	    ResourceFactory.getResourceChangeScannerService().configure(sconf);
	    ResourceFactory.getResourceChangeScannerService().start();
	    ResourceFactory.getResourceChangeNotifierService().start();
	    KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
	    aconf.setProperty("drools.agent.scanDirectories", "true");
	    aconf.setProperty("drools.agent.newInstance", "true");
	    KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent(agentName, aconf);
	    kagent.applyChangeSet(ResourceFactory.newClassPathResource(changeSet));
	    return kagent;
	}

	/**
	 * @param agentName
	 * @param url
	 * @param interval
	 * @return
	 */
	private static KnowledgeAgent getURLKnowledgeAgent(String agentName, String url, long interval) {
	    ResourceChangeScannerConfiguration sconf =
	    		ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
	    sconf.setProperty("drools.resource.scanner.interval", interval + "");
	    ResourceFactory.getResourceChangeScannerService().configure(sconf);
	    ResourceFactory.getResourceChangeScannerService().start();
	    ResourceFactory.getResourceChangeNotifierService().start();
	    KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
	    aconf.setProperty("drools.agent.scanDirectories", "true");
	    aconf.setProperty("drools.agent.newInstance", "true");
	    KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent(agentName, aconf);
	    kagent.applyChangeSet(ResourceFactory.newClassPathResource(url));
	    return kagent;
	}

}
