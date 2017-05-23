package com.asme.collector.packet.domain;

import java.util.regex.Pattern;


public class KeywordRule {

	private String id;
	private String name;
	private String host;
	private String patternstr;
	private String action;
	private String type;
	private String classify;
	private String decode;
	private String checkdate;
	
	private Pattern pattern = null;

	public Pattern getPattern() {
		if (this.pattern == null) {
			this.pattern = Pattern.compile(this.patternstr, Pattern.CASE_INSENSITIVE);
		}
		return this.pattern;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the patternstr
	 */
	public String getPatternstr() {
		return patternstr;
	}

	/**
	 * @param patternstr
	 *            the patternstr to set
	 */
	public void setPatternstr(String patternstr) {
		this.patternstr = patternstr;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the classify
	 */
	public String getClassify() {
		return classify;
	}

	/**
	 * @param classify
	 *            the classify to set
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}

	/**
	 * @return the decode
	 */
	public String getDecode() {
		return decode;
	}

	/**
	 * @param decode
	 *            the decode to set
	 */
	public void setDecode(String decode) {
		this.decode = decode;
	}

	/**
	 * @return the checkdate
	 */
	public String getCheckdate() {
		return checkdate;
	}

	/**
	 * @param checkdate
	 *            the checkdate to set
	 */
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
}
