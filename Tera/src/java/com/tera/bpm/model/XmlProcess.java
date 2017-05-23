package com.tera.bpm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.tera.util.DateUtils;

/**
 * @author wallace
 *
 */
public class XmlProcess {
	
	protected String name = "";
	
	protected String comment = "";
	
	protected List<XmlState> states = new ArrayList<XmlState>();

	public List<XmlState> getStates() {
		return states;
	}

	public void setStates(List<XmlState> states) {
		this.states = states;
	}
	
	public void addStates(XmlState xmlState) {
		if(null != xmlState) {
			states.add(xmlState);
		}
	}
	
	public void addStates(List<XmlState> xmlStates) {
		if(null != xmlStates) {
			states.addAll(xmlStates);
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * @param stateName
	 * @return
	 */
	public XmlState getXmlState(String stateName) {
		for (XmlState state : states) {
			if(state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		return null;
	}

	/**
	 * @return 开始节点名称
	 */
	public String getStartStateName() {
		for (XmlState state : states) {
			if(state.getState().equalsIgnoreCase("start")) {
				return state.getName();
			}
		}
		return "";
	}

	/**
	 * @return 结束节点名称列表
	 */
	public List<String> getEndStateNames() {
		List<String> list = new ArrayList<String>();
		for (XmlState state : states) {
			if(state.getState().equalsIgnoreCase("end")) {
				list.add(state.getName());
			}
		}
		return list;
	}

	/**
	 * 根据当前节点，获取下一步跳转节点
	 * @param stateName stateName
	 * @return 下一步跳转节点列表
	 */
	public List<String> getTransitionStates(String stateName) {
		List<String> list = new ArrayList<String>();
		for (XmlState state : states) {
			if(state.getName().equalsIgnoreCase(stateName)) {
				List<XmlTransition> transitions = state.getTransitions();
				for (XmlTransition transition : transitions) {
					list.add(transition.getTo());
				}
			}
		}
		return list;
	}

	/**
	 * 根据当前节点，获取所有的流程节点名称
	 * @return 所有的流程节点名称列表
	 */
	public List<String> getAllStates() {
		List<String> list = new ArrayList<String>();
		for (XmlState state : states) {
			list.add(state.getName());
		}
		return list;
	}

	/**
	 * 根据流程名称，获取流程节点的角色
	 * @param stateName stateName
	 * @return 流程节点的角色列表
	 */
	public List<String> getStateRoles(String stateName) {
		List<String> list = new ArrayList<String>();
		for (XmlState state : states) {
			if(state.getName().equalsIgnoreCase(stateName)) {
				list.addAll(state.getRoles());
			}
		}
		return list;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("XmlProcess{");
		buffer.append("name: ").append(name).append(", ");
		buffer.append("comment: ").append(comment).append(", ");
		
		buffer.append("states: [");
		Iterator<XmlState> iterator = states.iterator();
		while (iterator.hasNext()) {
			buffer.append(iterator.next()).append(", ");
		}
		buffer.append("]");
		buffer.append("}");
		return buffer.toString();
	}

}
