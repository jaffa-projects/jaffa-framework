/**
 * 
 */
package org.jaffa.rules.dto;

/**
 * @author BobbyC
 *
 */
public class ClassMetaDataCriteria {
    private String className;
    private String sourceFileName;
    private Boolean returnProperties;
    private String[] hasClassRules;
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
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
	 * @return the returnProperties
	 */
	public Boolean getReturnProperties() {
		return returnProperties;
	}
	/**
	 * @param returnProperties the returnProperties to set
	 */
	public void setReturnProperties(Boolean returnProperties) {
		this.returnProperties = returnProperties;
	}
	/**
	 * @return the hasClassRules
	 */
	public String[] getHasClassRules() {
		return hasClassRules;
	}
	/**
	 * @param hasClassRules the hasClassRules to set
	 */
	public void setHasClassRules(String[] hasClassRules) {
		this.hasClassRules = hasClassRules;
	}

}
