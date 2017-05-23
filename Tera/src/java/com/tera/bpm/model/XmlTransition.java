package com.tera.bpm.model;

import java.util.Iterator;

import com.tera.util.DateUtils;

/**
 * BPM xml元素抽象，跳转
 * @author wallace
 *
 */
public class XmlTransition {
	
	protected String to = "";
	
	protected String comment = "";
	
	public XmlTransition(String to, String comment) {
		this.to = to;
		this.comment = comment;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("XmlTransition{");
		buffer.append("to: ").append(to).append(", ");
		buffer.append("comment: ").append(comment).append("");;
		buffer.append("}");
		return buffer.toString();
	}

}
