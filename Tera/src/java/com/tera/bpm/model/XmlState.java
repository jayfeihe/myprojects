package com.tera.bpm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tera.util.DateUtils;

/**
 * BPM xml元素抽象，节点元素
 * @author wallace
 *
 */
public class XmlState {
	
	protected String state = ""; //start, state, gateway, end 
	
	protected String name = "";
	
	protected String comment = "";
	
	protected String type = "";//gateway的类型：start开始，end结束
	
	protected String mode = "";//gateway的模式：并行AND，异或XOR
	
	protected List<String> roles = new ArrayList<String>();
	
	protected List<XmlTransition> transitions = new ArrayList<XmlTransition>();
	
	/**
	 * @param state
	 * @param name
	 * @param comment
	 */
	public XmlState(String state, String name, String comment) {
		this.state = state;
		this.name = name;
		this.comment = comment;
	}
	
	/**
	 * gateway的初始化
	 * @param state
	 * @param name
	 * @param comment
	 * @param type
	 * @param mode
	 */
	public XmlState(String state, String name, String comment, String type, String mode) {
		this.state = state;
		this.name = name;
		this.comment = comment;
		this.type = type;
		this.mode = mode;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<XmlTransition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<XmlTransition> transitions) {
		this.transitions = transitions;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void addRoles(String role) {
		if(role != null && !"".equals(role)) {
			roles.add(role);
		}
	}
	
	public void addRoles(List<String> roles) {
		if(roles != null) {
			this.roles.addAll(roles);
		}
	}
	
	public void addPTransitions(String to, String comment) {
		if(to != null && !"".equals(to)) {
			transitions.add(new XmlTransition(to, comment));
		}
	}
	
	public void addPTransitions(XmlTransition xmlTransition) {
		if(xmlTransition != null) {
			transitions.add(xmlTransition);
		}
	}
	
	public void addPTransitions(List<XmlTransition> xmlTransitions) {
		if(xmlTransitions != null) {
			this.transitions.addAll(xmlTransitions);
		}
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("XmlState{");
		buffer.append("name: ").append(name).append(", ");		
		buffer.append("state: ").append(state).append(", ");
		buffer.append("mode: ").append(mode).append(", ");
		buffer.append("type: ").append(type).append(", ");
		buffer.append("comment: ").append(comment).append(", ");
		buffer.append("transitions: [");
		Iterator<XmlTransition> iterator = transitions.iterator();
		while (iterator.hasNext()) {
			buffer.append(iterator.next()).append(", ");
		}
		buffer.append("], ");
		buffer.append("roles: [");
		Iterator<String> iterator2 = roles.iterator();
		while (iterator2.hasNext()) {
			buffer.append(iterator2.next()).append(", ");
		}
		buffer.append("]");
		buffer.append("}");
		return buffer.toString();
	}

}
