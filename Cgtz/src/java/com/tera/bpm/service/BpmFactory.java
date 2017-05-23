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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.tera.bpm.model.BpmDefine;
import com.tera.bpm.model.BpmLog;
import com.tera.bpm.model.BpmTask;
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
	private static Map<String, BpmXmlParser> bpmDefines = new HashMap<String, BpmXmlParser>();

	/**
	 * 根据processName查看该流程是否已经解析
	 * @param processName processName
	 * @return boolean
	 */
	public static boolean isInitedBpm(String processName) {
		if (null == processName || "".equals(processName)) {
			return false;
		}
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null != parser) {
			return true;
		}
		return false;
	}

	/**
	 * @param bpmDefine bpmDefine
	 */
	@SuppressWarnings("unchecked")
	public static void initBpm(BpmDefine bpmDefine) {
		if (null == bpmDefine) {
			throw new IllegalArgumentException("BpmFactory.initBpm,流程定义为空！");
		}
		String processName = bpmDefine.getProcessName();
		String processDefFile = bpmDefine.getProcessDefFile();
		if ("".equals(processName) || "".equals(processDefFile)) {
			throw new IllegalArgumentException("BpmFactory.initBpm,流程定义对象缺少数据！");
		}
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null != parser) {
			return;
		}
		//获取WEB-INF/classes/的路径
		String xml = "";
		InputStream inputStream = null;
		try {
			inputStream = BpmFactory.class.getResourceAsStream("/" + processDefFile);
			xml = IOUtils.read(inputStream, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		XMLHandler handler = new XMLHandler(xml);
		String name = handler.getElementAttribute("/process/@name");
		parser = new BpmXmlParser();
		parser.setProcessName(processName);
		parser.setProcessDefFile(processDefFile);
		parser.setHandler(handler);
		if (!name.equals(processName) || name.equals("") || processName.equals("")) {
			throw new RuntimeException("BpmFactory.initBpm,流程定义文件里的流程名称和xml中的名称不匹配！");
		}
		if (handler.getElement("/process/start") == null) {
			throw new RuntimeException("BpmFactory.initBpm,流程定义文件里的没有start元素！");
		}
		List endList = handler.getListElement("/process/end");
		if (endList.size() == 0) {
			throw new RuntimeException("BpmFactory.initBpm,流程定义文件里的没有end元素！");
		}
		List<String> endListName = handler.getListElementAttribute("/process/end/@name");
		Set<String> endListSet = new HashSet<String>(handler.getListElementAttribute("/process/end/@name"));
		if (endListName.size() > 1 && (endListName.size() != endListSet.size())) {
			throw new RuntimeException("BpmFactory.initBpm,流程定义文件里的end元素name属性有重复！");
		}
		List<String> stateListName = handler.getListElementAttribute("/process/state/@name");
		Set<String> stateListSet = new HashSet<String>(handler.getListElementAttribute("/process/state/@name"));
		if (stateListName.size() != stateListSet.size()) {
			throw new RuntimeException("BpmFactory.initBpm,流程定义文件里的state元素name属性有重复！");
		}
		for (String stateName : stateListName) {
			if ("".equals(stateName)) {
				throw new RuntimeException("BpmFactory.initBpm,流程定义文件里存在state元素name属性为空！");
			}
		}
		List stateList = handler.getListElement("/process/state");
		for (int i = 0; i < stateList.size(); i++) {
			Element state = (Element) stateList.get(i);
			List<Element> transitionList = handler.getListElement(state, "transition");
			if (transitionList.size() == 0) {
				throw new RuntimeException("BpmFactory.initBpm,流程定义文件里第"
						+ (i + 1) + "个state元素无transition节点！");
			}
			for (int j = 0; j < transitionList.size(); j++) {
				Element transition =  (Element) transitionList.get(j);
				if ("".equals(handler.getElementAttribute(transition, "@to"))) {
					throw new RuntimeException("BpmFactory.initBpm,流程定义文件里("
							+ handler.getElementAttribute(state, "@name")
							+ ")节点第" + (j + 1) + "个transition元素to属性为空！");
				}
			}
		}
//		List<String> transitionList = handler.getListElementAttribute("//transition/@to");
		bpmDefines.put(processName, parser);
		log.info("BpmFactory.initBpm,流程(" + processName + ")初始化成功。");
	}

	/**
	 * @param processName processName
	 * @return 开始节点名称
	 */
	public static String getStartStateName(String processName) {
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null == parser) {
			return null;
		}
		return parser.getStartStateName();
	}

	/**
	 * @param processName processName
	 * @return 结束节点名称列表
	 */
	public static List<String> getEndStateNames(String processName) {
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null == parser) {
			return null;
		}
		return parser.getEndStateNames();
	}

	/**
	 * 根据当前节点，获取下一步跳转节点
	 * @param processName processName
	 * @param stateName stateName
	 * @return 下一步跳转节点列表
	 */
	public static List<String> getTransitionStates(String processName, String stateName) {
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null == parser) {
			return null;
		}
		return parser.getTransitionStates(stateName);
	}

	/**
	 * 根据当前节点，获取所有的流程节点名称
	 * @param processName processName
	 * @return 所有的流程节点名称列表
	 */
	public static List<String> getAllStates(String processName) {
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null == parser) {
			return null;
		}
		return parser.getAllStates();
	}

	/**
	 * 根据流程名称，获取流程节点的角色
	 * @param processName processName
	 * @param stateName stateName
	 * @return 流程节点的角色列表
	 */
	public static List<String> getStateRoles(String processName, String stateName) {
		BpmXmlParser parser = bpmDefines.get(processName);
		if (null == parser) {
			return null;
		}
		return parser.getStateRoles(stateName);
	}

	/**
	 * 根据任务，获取任务的流程日志
	 * @param task task
	 * @return BpmLog
	 */
	public static BpmLog getBpmLog(BpmTask task) {
		if (null == task) {
			throw new IllegalArgumentException("getBpmLog,任务对象为空！");
		}
		if (task.getId() == 0) {
			throw new IllegalArgumentException("getBpmLog,任务对象没有和数据库同步！");
		}
		BpmLog bpmLog = new BpmLog();
		bpmLog.setTaskId(task.getId());
		bpmLog.setBizKey(task.getBizKey());
		bpmLog.setState(task.getState());
		bpmLog.setProcesser(task.getProcesser());
		bpmLog.setOperator(task.getOperator());
		bpmLog.setIntime(new Timestamp(System.currentTimeMillis()));
		return bpmLog;
	}
}

/**
 * 流程定义文件解析类
 * @author Administrator
 */
class BpmXmlParser {
	/**
	 * 流程定义文件路径
	 */
	private String processDefFile = "";
	/**
	 * 流程名称
	 */
	private String processName = "";
	/**
	 * 流程定义xml解析工具
	 */
	private XMLHandler handler;

	/**
	 * 获取开始节点名称
	 * @return 开始节点名称
	 */
	public String getStartStateName() {
		String startName = handler.getElementAttribute("/process/start/@name");
//		if ("".equals(startName)) {
//			startName = "开始";
//		}
		return startName;
	}
	/**
	 * 获取结束节点名称
	 * @return 开始节点名称
	 */
	public List<String> getEndStateNames() {
		return handler.getListElementAttribute("/process/end/@name");
	}

	/**
	 * 根据节点名称获取角色
	 * @param state 节点名称
	 * @return 角色
	 */
	public List<String> getStateRoles(String state) {
		List<String> roles = new ArrayList<String>();
		String role = handler.getElementAttribute("/process/state[@name='" + state + "']/@role");
		role.replace("\\s", "");
		roles.addAll(Arrays.asList(role.split(",")));
		return roles;
	}
	/**
	 * 根据当前节点，获取下一步跳转节点
	 * @param stateName stateName
	 * @return 下一步跳转节点列表
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTransitionStates(String stateName) {
		//start节点
		Element element = handler.getElement("/process/start");
		String state = handler.getElementAttribute(element, "@name");
		if (stateName.equals(state)) {
			return handler.getListElementAttribute(element, "transition/@to");
		}
		//state节点
		List elementList = handler.getListElement("/process/state");
		for (int i = 0; i < elementList.size(); i++) {
			element = (Element) elementList.get(i);
			state = handler.getElementAttribute(element, "@name");
			if (stateName.equals(state)) {
				return handler.getListElementAttribute(element, "transition/@to");
			}
		}
		//end节点
		elementList = handler.getListElement("/process/end");
		for (int i = 0; i < elementList.size(); i++) {
			element = (Element) elementList.get(i);
			state = handler.getElementAttribute(element, "@name");
			if (stateName.equals(state)) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 查询所有的流程节点名称
	 * @return 流程节点名称
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAllStates() {
		Set<String> states = new HashSet<String>();
		//start节点
		Element element = handler.getElement("/process/start");
		String state = handler.getElementAttribute(element, "@name");
		states.add(state);
		states.addAll(handler.getListElementAttribute(element, "transition/@to"));
		//state节点
		List elementList = handler.getListElement("/process/state");
		for (int i = 0; i < elementList.size(); i++) {
			element = (Element) elementList.get(i);
			state = handler.getElementAttribute(element, "@name");
			states.add(state);
			states.addAll(handler.getListElementAttribute(element, "transition/@to"));
		}
		//end节点
		elementList = handler.getListElement("/process/end");
		for (int i = 0; i < elementList.size(); i++) {
			element = (Element) elementList.get(i);
			state = handler.getElementAttribute(element, "@name");
			states.add(state);
		}
		List<String> allStates = new ArrayList<String>();
		allStates.addAll(states);
		return allStates;
	}

	/**
	 * @return the processDefFile
	 */
	public String getProcessDefFile() {
		return processDefFile;
	}
	/**
	 * @param processDefFile the processDefFile to set
	 */
	public void setProcessDefFile(String processDefFile) {
		this.processDefFile = processDefFile;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * @return the handler
	 */
	public XMLHandler getHandler() {
		return handler;
	}
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(XMLHandler handler) {
		this.handler = handler;
	}

}
