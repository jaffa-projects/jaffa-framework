package org.jaffa.rules.dto;
import java.util.Map;

public class RuleMetaDataDto {
    private String ruleName;
    private String variation;
    private String[] variationArray;
    private String text;
    Map<String, String> parameters;
	
    /**
	 * @return the ruleName
	 */
	public String getRuleName() {
		return ruleName;
	}
	
	/**
	 * @param ruleName the ruleName to set
	 */
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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
	 * @return the variationArray
	 */
	public String[] getVariationArray() {
		return variationArray;
	}
	
	/**
	 * @param variationArray the variationArray to set
	 */
	public void setVariationArray(String[] variationArray) {
		this.variationArray = variationArray;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
}
