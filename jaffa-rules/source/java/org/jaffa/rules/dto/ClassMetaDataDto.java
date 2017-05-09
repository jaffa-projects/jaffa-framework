package org.jaffa.rules.dto;

import java.util.List;

public class ClassMetaDataDto {

	private String name;
    private String sourceFileName;
    private String condition;
    private String language;
    private String variation;
    private String executionRealm;
    private String extendsClass;
    private List<RuleMetaDataDto> rules;
    private List<PropertyMetaDataDto> properties;

    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the sourceFileName
	 */
	public String getSourceFileName() {
		return sourceFileName;
	}
	
	/**
	 * @param sourceFileName the sourceFileName to set
	 */
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
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
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * @return the variation
	 */
	public String getVariation() {
		return variation;
	}
	
	/**
	 * @param variation the variation to set
	 */
	public void setVariation(String variation) {
		this.variation = variation;
	}
	
	/**
	 * @return the executionRealm
	 */
	public String getExecutionRealm() {
		return executionRealm;
	}
	
	/**
	 * @param executionRealm the executionRealm to set
	 */
	public void setExecutionRealm(String executionRealm) {
		this.executionRealm = executionRealm;
	}
	
	/**
	 * @return the extendsClass
	 */
	public String getExtendsClass() {
		return extendsClass;
	}
	
	/**
	 * @param extendsClass the extendsClass to set
	 */
	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}
	
	/**
	 * @return the rules
	 */
	public List<RuleMetaDataDto> getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(List<RuleMetaDataDto> rules) {
		this.rules = rules;
	}

	/**
	 * @return the properties
	 */
	public List<PropertyMetaDataDto> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<PropertyMetaDataDto> properties) {
		this.properties = properties;
	}
	

}
