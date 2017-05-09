package org.jaffa.rules.dto;

import java.util.List;

public class PropertyMetaDataDto {
	private String propertyName;
    private String condition;
    private String language;
    private String variation;
    private String extendsClass;
    private String extendsProperty;
    private List<RuleMetaDataDto> rules;
	
    /**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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
	 * @return the extendsProperty
	 */
	public String getExtendsProperty() {
		return extendsProperty;
	}
	
	/**
	 * @param extendsProperty the extendsProperty to set
	 */
	public void setExtendsProperty(String extendsProperty) {
		this.extendsProperty = extendsProperty;
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
	


}
