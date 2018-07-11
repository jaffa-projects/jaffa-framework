package org.jaffa.rules.dto;

import com.google.gson.annotations.Expose;

public class FlexClassMetaData {

	@Expose()
	private String domainClass;

	@Expose()
	private String domainName;

	@Expose()
	private String domainLabel;

	@Expose()
	private String domainLabelToken;

	@Expose()
	private String flexClass;

	@Expose()
	private String flexName;

	@Expose()
	private String flexLabel;

	@Expose()
	private String flexLabelToken;
	private String flexSourceFile;
	private String condition;
	/**
	 * @return the domainClass
	 */
	public String getDomainClass() {
		return domainClass;
	}
	/**
	 * @param domainClass the domainClass to set
	 */
	public void setDomainClass(String domainClass) {
		this.domainClass = domainClass;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return the flexClass
	 */
	public String getFlexClass() {
		return flexClass;
	}
	/**
	 * @param flexClass the flexClass to set
	 */
	public void setFlexClass(String flexClass) {
		this.flexClass = flexClass;
	}
	/**
	 * @return the flexName
	 */
	public String getFlexName() {
		return flexName;
	}
	/**
	 * @param flexName the flexName to set
	 */
	public void setFlexName(String flexName) {
		this.flexName = flexName;
	}
	/**
	 * @return the flexLabel
	 */
	public String getFlexLabel() {
		return flexLabel;
	}
	/**
	 * @param flexLabel the flexLabel to set
	 */
	public void setFlexLabel(String flexLabel) {
		this.flexLabel = flexLabel;
	}
	/**
	 * @return the flexLabelToken
	 */
	public String getFlexLabelToken() {
		return flexLabelToken;
	}
	/**
	 * @param flexLabelToken the flexLabelToken to set
	 */
	public void setFlexLabelToken(String flexLabelToken) {
		this.flexLabelToken = flexLabelToken;
	}
	/**
	 * @return the domainLabel
	 */
	public String getDomainLabel() {
		return domainLabel;
	}
	/**
	 * @param domainLabel the domainLabel to set
	 */
	public void setDomainLabel(String domainLabel) {
		this.domainLabel = domainLabel;
	}
	/**
	 * @return the domainLabelToken
	 */
	public String getDomainLabelToken() {
		return domainLabelToken;
	}
	/**
	 * @param domainLabelToken the domainLabelToken to set
	 */
	public void setDomainLabelToken(String domainLabelToken) {
		this.domainLabelToken = domainLabelToken;
	}
	/**
	 * @return the flexSourceFile
	 */
	public String getFlexSourceFile() {
		return flexSourceFile;
	}
	/**
	 * @param flexSourceFile the flexSourceFile to set
	 */
	public void setFlexSourceFile(String flexSourceFile) {
		this.flexSourceFile = flexSourceFile;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
