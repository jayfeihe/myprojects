/*
 *  Copyright 2012, Tera-soft Co., Ltd.  All right reserved.
 *
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF TERA-SOFT CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF TERA-SOFT CO., LTD
 *
 */
package com.tera.bpm.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.tera.bpm.model.BpmDefine;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
import com.tera.bpm.model.XmlProcess;
import com.tera.bpm.model.XmlState;
import com.tera.bpm.model.XmlTransition;
import com.tera.util.IOUtils;
import com.tera.util.XMLHandler;

/**
 * @author Administrator
 */
public class BpmFactory {

	/**
	 * 日志
	 */
	private static Logger log = Logger.getLogger(BpmFactory.class);

	/**
	 * bpmDefines
	 */
	private static ConcurrentHashMap<String, XmlProcess> bpmDefines = new ConcurrentHashMap<String, XmlProcess>();

	public static XmlProcess getBpmInstance(BpmDefine bpmDefine) {
		if (null == bpmDefine) {
			throw new IllegalArgumentException("BpmFactory.getBpmInstance,流程定义为空！");
		}
		String processName = bpmDefine.getProcessName();
		if ("".equals(processName)) {
			throw new IllegalArgumentException("BpmFactory.getBpmInstance,流程定义缺少流程名称！");
		}
		if (bpmDefines.get(processName) == null) {
			String processDefFile = bpmDefine.getProcessDefFile();
			if ("".equals(processDefFile)) {
				throw new IllegalArgumentException("BpmFactory.getBpmInstance,流程定义缺少xml文件路径数据！");
			}
			//获取WEB-INF/classes/的路径
			String xml = "";
			InputStream inputStream = null;
			try {
				inputStream = BpmFactory.class.getResourceAsStream("/" + processDefFile);
				xml = IOUtils.read(inputStream, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			//流程定义文件检查
			XMLHandler handler = new XMLHandler(xml);
			List<String> endListName = handler.getListElementAttribute("/process/end/@name");
			Set<String> endListSet = new HashSet<String>(handler.getListElementAttribute("/process/end/@name"));
			if (endListName.size() > 1 && (endListName.size() != endListSet.size())) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的end元素name属性有重复！");
			}
			List<String> stateListName = handler.getListElementAttribute("/process/state/@name");
			Set<String> stateListSet = new HashSet<String>(handler.getListElementAttribute("/process/state/@name"));
			if (stateListName.size() != stateListSet.size()) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的state元素name属性有重复！");
			}
			for (String stateName : stateListName) {
				if ("".equals(stateName)) {
					throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里存在state元素name属性为空！");
				}
			}
			List stateList = handler.getListElement("/process/state");
			for (int i = 0; i < stateList.size(); i++) {
				Element state = (Element) stateList.get(i);
				List<Element> transitionList = handler.getListElement(state, "transition");
				if (transitionList.size() == 0) {
					throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里第"
							+ (i + 1) + "个state元素无transition节点！");
				}
				for (int j = 0; j < transitionList.size(); j++) {
					Element transition =  (Element) transitionList.get(j);
					if ("".equals(handler.getElementAttribute(transition, "@to"))) {
						throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里("
								+ handler.getElementAttribute(state, "@name")
								+ ")节点第" + (j + 1) + "个transition元素to属性为空！");
					}
				}
			}
			XmlProcess process = new XmlProcess();
			//流程名称
			String name = handler.getElementAttribute("/process/@name");
			String comment = handler.getElementAttribute("/process/@comment");
			process.setName(name);
			process.setComment(comment);
			if (!name.equals(processName) || name.equals("") || processName.equals("")) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的流程名称和xml中的名称不匹配！");
			}
			//start元素
			List startList = handler.getListElement("/process/start");
			if (startList == null || startList.size() == 0) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的没有start元素！");
			}
			List<XmlState> starts = new ArrayList<XmlState>();
			for (Iterator iterator = startList.iterator(); iterator.hasNext();) {
				Element start = (Element) iterator.next();
				String startName = handler.getElementAttribute(start, "@name");
				String startComment = handler.getElementAttribute(start, "@comment");
				XmlState xmlState = new XmlState("start", startName, startComment);
				//transitions
				List<XmlTransition> transitions = new ArrayList<XmlTransition>();
				List transitionList = handler.getListElement(start, "transition");
				for (Iterator iterator2 = transitionList.iterator(); iterator2.hasNext();) {
					Element transition = (Element) iterator2.next();
					String transitionTo = handler.getElementAttribute(transition, "@to");
					String transitionComment = handler.getElementAttribute(transition, "@comment");
					transitions.add(new XmlTransition(transitionTo, transitionComment));
				}
				xmlState.addPTransitions(transitions);
				//roles
				List<String> roles = new ArrayList<String>();
				String role = handler.getElementAttribute(start, "@role");
				role.replace("\\s", "");
				role.replaceAll("，", ",");
				roles.addAll(Arrays.asList(role.split(",")));
				xmlState.addRoles(roles);
				starts.add(xmlState);
			}
			process.addStates(starts);
			
			//state元素
			stateList = handler.getListElement("/process/state");
			if (stateList == null || stateList.size() == 0) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的没有start元素！");
			}
			List<XmlState> states = new ArrayList<XmlState>();
			for (Iterator iterator = stateList.iterator(); iterator.hasNext();) {
				Element state = (Element) iterator.next();
				String stateName = handler.getElementAttribute(state, "@name");
				String stateComment = handler.getElementAttribute(state, "@comment");
				XmlState xmlState = new XmlState("state", stateName, stateComment);
				//transitions
				List<XmlTransition> transitions = new ArrayList<XmlTransition>();
				List transitionList = handler.getListElement(state, "transition");
				for (Iterator iterator2 = transitionList.iterator(); iterator2.hasNext();) {
					Element transition = (Element) iterator2.next();
					String transitionTo = handler.getElementAttribute(transition, "@to");
					String transitionComment = handler.getElementAttribute(transition, "@comment");
					transitions.add(new XmlTransition(transitionTo, transitionComment));
				}
				xmlState.addPTransitions(transitions);
				//roles
				List<String> roles = new ArrayList<String>();
				String role = handler.getElementAttribute(state, "@role");
				role.replace("\\s", "");
				role.replaceAll("，", ",");
				roles.addAll(Arrays.asList(role.split(",")));
				xmlState.addRoles(roles);
				states.add(xmlState);
			}
			process.addStates(states);
			
			//gateway元素
			List gatewayList = handler.getListElement("/process/gateway");
			List<XmlState> gateways = new ArrayList<XmlState>();
			for (Iterator iterator = gatewayList.iterator(); iterator.hasNext();) {
				Element gateway = (Element) iterator.next();
				String gatewayName = handler.getElementAttribute(gateway, "@name");
				String gatewayComment = handler.getElementAttribute(gateway, "@comment");
				String gatewayType = handler.getElementAttribute(gateway, "@type");
				String gatewayMode = handler.getElementAttribute(gateway, "@mode");
				XmlState xmlState = new XmlState("gateway", gatewayName, gatewayComment, gatewayType, gatewayMode);
				//transitions
				List<XmlTransition> transitions = new ArrayList<XmlTransition>();
				List transitionList = handler.getListElement(gateway, "transition");
				for (Iterator iterator2 = transitionList.iterator(); iterator2.hasNext();) {
					Element transition = (Element) iterator2.next();
					String transitionTo = handler.getElementAttribute(transition, "@to");
					String transitionComment = handler.getElementAttribute(transition, "@comment");
					transitions.add(new XmlTransition(transitionTo, transitionComment));
				}
				xmlState.addPTransitions(transitions);
				//roles
				List<String> roles = new ArrayList<String>();
				String role = handler.getElementAttribute(gateway, "@role");
				role.replace("\\s", "");
				role.replaceAll("，", ",");
				roles.addAll(Arrays.asList(role.split(",")));
				xmlState.addRoles(roles);
				gateways.add(xmlState);
			}
			process.addStates(gateways);
			
			//end元素
			List endList = handler.getListElement("/process/end");
			if (endList == null || endList.size() == 0) {
				throw new RuntimeException("BpmFactory.getBpmInstance,流程定义文件里的没有end元素！");
			}
			List<XmlState> ends = new ArrayList<XmlState>();
			for (Iterator iterator = endList.iterator(); iterator.hasNext();) {
				Element end = (Element) iterator.next();
				String endName = handler.getElementAttribute(end, "@name");
				String endComment = handler.getElementAttribute(end, "@comment");
				XmlState xmlState = new XmlState("end", endName, endComment);
				//transitions
				//roles
				ends.add(xmlState);
			}
			process.addStates(ends);
			XmlProcess old = bpmDefines.putIfAbsent(processName, process);
			if(old != null) {
				process = old;
			}
			log.info("BpmFactory.getBpmInstance,流程(" + processName + ")初始化成功。");
			return process;
		} else {
//			System.out.println("重用processName：" + processName);
			return bpmDefines.get(processName);
		}
	}

}
