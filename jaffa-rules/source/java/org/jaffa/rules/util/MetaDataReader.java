/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.rules.util;

import org.apache.log4j.Logger;
import org.jaffa.rules.dto.*;
import org.jaffa.rules.meta.ClassMetaData;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.meta.PropertyMetaData;
import org.jaffa.rules.meta.RuleMetaData;
import org.jaffa.rules.rulemeta.RuleMetaHelper;
import org.jaffa.rules.rulemeta.data.RuleMetaDataCriteria;
import org.jaffa.security.VariationContext;
import org.jaffa.util.MessageHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A helper class to interact with meta data.
 */
public class MetaDataReader {

	private static Logger log = Logger.getLogger(MetaDataReader.class);
	private static String AOP_CORE_PATH = "/aop/core/";
	private static String AOP_VARIATION_PATH = "/aop/customer-";

	/**
	 *  Return information about all the classes that are configured to support flex fields 
	 */
	public static List<FlexClassMetaData> getFlexClass() {
		List<FlexClassMetaData> flexClassMetaData = new LinkedList<FlexClassMetaData>();

		// Get an array of all the classes that support flex fields
		String[] classNames = MetaDataRepository.instance().getClassNamesByRuleName("flex-fields");
		String variation = VariationContext.getVariation();
		if (classNames != null) {
			for (String className : classNames) {
				if (log.isDebugEnabled()){
					log.debug("Checking flex for: " + className);
				}
				RuleMetaData domainInfo = findRule(className, null, null, "domain-info");
				if (domainInfo != null) {
					Map<String, List<RuleMetaData>> ruleMap = MetaDataRepository.instance().getPropertyRuleMap(className, "flex-fields");
					List<RuleMetaData> rules = ruleMap != null ? ruleMap.get(null) : null;
					List<String> processedClasses = new LinkedList<String>();
					if (rules != null) {
						for (RuleMetaData rule : rules) {
							if (log.isDebugEnabled()){
								log.debug("Checking flex for class: " + className + " rule: " + rule.getName());
							}
							// Class flex-fields should only be defined in core aop folders
							if (rule.getSource().toLowerCase().indexOf(AOP_CORE_PATH) > 0) {
								if (processedClasses.indexOf(rule.getParameter("source"))>=0){
									log.error("Flex-fields rules have been defined, in core, multiple times for the same source: " + rule.getParameter("source"));
									continue;
								}
								processedClasses.add(rule.getParameter("source"));
								// Get class meta data by flex class name
								List<ClassMetaData> cmd = MetaDataRepository.instance().getClassMetaDataListByClassName(rule.getParameter("source"));
								Boolean hasCustomerDefinition = false;
								Boolean hasCoreDefinition = false;
								String coreSourceFile = "";
								if (cmd!=null){
									for (ClassMetaData flexMetaData : cmd) {
										String flexSourcePath = flexMetaData.getSource();
										if (flexSourcePath.toLowerCase().indexOf(AOP_VARIATION_PATH + variation.toLowerCase() + "/")>0){
											hasCustomerDefinition = true;
										}
										if (flexSourcePath.toLowerCase().indexOf(AOP_CORE_PATH)>0){
											hasCoreDefinition = true;
											coreSourceFile = flexSourcePath; 
										}
									}
								}

								if (!hasCoreDefinition){
									log.error("Core AOP definition not found for:" + rule.getParameter("source") + ". This should include value for domain-info rule.");
								}else if (!hasCustomerDefinition){
									//define stub for customer-variation
									FlexClassMetaData fcmd = new FlexClassMetaData();
									
									String flexClassName = rule.getParameter("source");
									RuleMetaData flexDomainInfo = findRule(flexClassName, null, null, "domain-info");
									if (flexDomainInfo==null){
										log.error("No domain-info defined for: " + flexClassName);
										continue;
									}
									fcmd.setDomainClass(className);

									RuleMetaData labelRule = findRule(className, null, null, "label");
									if (labelRule!=null){
										fcmd.setDomainLabel(MessageHelper.replaceTokens(labelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
										fcmd.setDomainLabelToken(MessageHelper.removeTokenMarkers(labelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
									}
									String dummyPath = coreSourceFile.replace(AOP_CORE_PATH, (AOP_VARIATION_PATH+variation.toLowerCase()+"/"));
									int lastIndex = dummyPath.lastIndexOf("/");
									if (flexDomainInfo.getParameter("name")!=null){
									  dummyPath = dummyPath.substring(0,(lastIndex+1)) + flexDomainInfo.getParameter("name").replace("$", "") + "-aop.xml";
									} else {
										log.error ("Name not found for class:" + flexClassName);
										continue;
									}

									fcmd.setFlexSourceFile(dummyPath);
									fcmd.setDomainName(domainInfo.getParameter("name"));
									fcmd.setFlexClass(flexClassName);
									fcmd.setFlexName(flexDomainInfo.getParameter("name"));

									RuleMetaData flexLabelRule = findRule(flexClassName, null, null, "label");
									if (flexLabelRule!=null){
										fcmd.setFlexLabel(MessageHelper.replaceTokens(flexLabelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
										fcmd.setFlexLabelToken(MessageHelper.removeTokenMarkers(flexLabelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
									}
									
									fcmd.setCondition(rule.getParameter("condition"));
									flexClassMetaData.add(fcmd);
								
								}else if (hasCustomerDefinition){
									for (ClassMetaData flexMetaData : cmd) {
										String flexSourcePath = flexMetaData.getSource();
										if (flexSourcePath.toLowerCase().indexOf(AOP_VARIATION_PATH + variation.toLowerCase() + "/")>0){
											Map<String, String> parameters = rule.getParameters();
											if (parameters != null) {
												FlexClassMetaData fcmd = new FlexClassMetaData();
		
												String flexClassName = rule.getParameter("source");
	
												RuleMetaData flexDomainInfo = findRule(flexClassName, null, null, "domain-info");
												fcmd.setDomainClass(className);
		
												RuleMetaData labelRule = findRule(className, null, null, "label");
												if (labelRule!=null){
												    fcmd.setDomainLabel(MessageHelper.replaceTokens(labelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
												    fcmd.setDomainLabelToken(MessageHelper.removeTokenMarkers(labelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
												}
												fcmd.setFlexSourceFile(flexSourcePath);
												fcmd.setDomainName(domainInfo.getParameter("name"));

												RuleMetaData flexLabelRule = findRule(flexClassName, null, null, "label");
												if (flexLabelRule!=null){
    												fcmd.setFlexLabel(MessageHelper.replaceTokens(flexLabelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
	    											fcmd.setFlexLabelToken(MessageHelper.removeTokenMarkers(flexLabelRule.getParameter(RuleMetaData.PARAMETER_VALUE)));
												}

												fcmd.setFlexClass(flexClassName);
												fcmd.setFlexName(flexDomainInfo.getParameter("name"));
												fcmd.setCondition(rule.getParameter("condition"));
												flexClassMetaData.add(fcmd);
											}
										}
									}
								}
							}
						}
					}
				}

			}
		}
		return flexClassMetaData;
	}

	/**
	 * Returns all the meta data defined for a given class in a given source folder
	 */
	public static ClassMetaDataDto[] getClassMetaData(String flexClassName, String fileName) {
		List<ClassMetaData> resultCmd = new LinkedList<ClassMetaData>();
		if (flexClassName != null && !flexClassName.equals("")) {
			List<ClassMetaData> cmd = MetaDataRepository.instance().getClassMetaDataListByClassName(flexClassName);
			if (fileName != null && cmd != null) {
				for (ClassMetaData meta : cmd) {
					if (meta.getSource().equals(fileName)) {
						resultCmd.add(meta);
					}
				}
			}
		} else {
			if (fileName != null) {
				resultCmd = MetaDataRepository.instance().getClassMetaDataListBySource(fileName);
			}
		}

		return classMetaDataToDto(resultCmd, true);
	}

	public static ClassMetaDataDto[] getMetaData(ClassMetaDataCriteria criteria) {
		String className = criteria.getClassName();
		List<ClassMetaData> cmd = MetaDataRepository.instance().getClassMetaDataListByClassName(className);
		return classMetaDataToDto(cmd, criteria.getReturnProperties());
	}

	/**
	 * Returns the first applicable rule for the className/propertyName/ruleName
	 * combination.
	 */
	private static RuleMetaData findRule(String className, String propertyName, Object obj, String ruleName) {
		RuleMetaDataCriteria criteria = new RuleMetaDataCriteria();
		criteria.setClassName(className);
		criteria.setPropertyName(propertyName);
		criteria.setRuleName(ruleName);
		criteria.setObj(obj);
		return RuleMetaHelper.findRule(criteria);
	}

	/**
	 * Convert a list of ClassMetaData objects into an array of ClassMetaDataDto
	 * objects
	 */
	private static ClassMetaDataDto[] classMetaDataToDto(List<ClassMetaData> classMetaData, Boolean returnProperties) {
		return classMetaDataToDto(classMetaData, returnProperties, false);
	}

	/**
	 * Convert a list of ClassMetaData objects into an array of ClassMetaDataDto
	 * objects
	 */
	private static ClassMetaDataDto[] classMetaDataToDto(List<ClassMetaData> classMetaData, Boolean returnProperties, Boolean aggregate) {
		if (classMetaData == null)
			return null;
		List<ClassMetaDataDto> returnCmd = new LinkedList<ClassMetaDataDto>();
		for (ClassMetaData metaData : classMetaData) {
			ClassMetaDataDto dto = new ClassMetaDataDto();
			dto.setName(metaData.getName());
			dto.setSourceFileName(metaData.getSource());
			dto.setCondition(metaData.getCondition());
			dto.setLanguage(metaData.getLanguage());
			dto.setVariation(metaData.getVariation());
			dto.setExecutionRealm(metaData.getExecutionRealm());
			dto.setExtendsClass(metaData.getExtendsClass());

			// Load rules into dto
			List<RuleMetaDataDto> ruleMetaDataDtos = new LinkedList<RuleMetaDataDto>();
			List<RuleMetaData> rules = metaData.getRules();
			if (rules != null) {
				for (RuleMetaData rmd : rules) {
					RuleMetaDataDto ruleMetaDataDto = new RuleMetaDataDto();
					ruleMetaDataDto.setRuleName(rmd.getName());
					ruleMetaDataDto.setVariation(rmd.getVariation());
					ruleMetaDataDto.setVariationArray(rmd.getVariationArray());
					ruleMetaDataDto.setParameters(rmd.getParameters());
					ruleMetaDataDtos.add(ruleMetaDataDto);
				}
			}

			dto.setRules(ruleMetaDataDtos);

			// Load properties into dto
			if (returnProperties != null && returnProperties) {
				List<PropertyMetaDataDto> propertyMetaDataDtos = new LinkedList<PropertyMetaDataDto>();
				List<PropertyMetaData> props = metaData.getProperties();
				if (props != null) {
					for (PropertyMetaData pmd : props) {
						PropertyMetaDataDto propertyMetaDataDto = new PropertyMetaDataDto();
						propertyMetaDataDto.setPropertyName(pmd.getName());
						propertyMetaDataDto.setCondition(pmd.getCondition());
						propertyMetaDataDto.setLanguage(pmd.getLanguage());
						propertyMetaDataDto.setVariation(pmd.getVariation());
						propertyMetaDataDto.setExtendsClass(pmd.getExtendsClass());
						propertyMetaDataDto.setExtendsProperty(pmd.getExtendsProperty());

						// Load rules into dto
						List<RuleMetaDataDto> propertyRuleMetaDataDtos = new LinkedList<RuleMetaDataDto>();
						List<RuleMetaData> propertyRules = pmd.getRules(metaData.getName());
						if (propertyRules != null) {
							for (RuleMetaData rmd : propertyRules) {
								RuleMetaDataDto ruleMetaDataDto = new RuleMetaDataDto();
								ruleMetaDataDto.setRuleName(rmd.getName());
								ruleMetaDataDto.setVariation(rmd.getVariation());
								ruleMetaDataDto.setVariationArray(rmd.getVariationArray());
								ruleMetaDataDto.setParameters(rmd.getParameters());
								propertyRuleMetaDataDtos.add(ruleMetaDataDto);
							}
						}
						propertyMetaDataDto.setRules(propertyRuleMetaDataDtos);

						propertyMetaDataDtos.add(propertyMetaDataDto);
					}
				}
				dto.setProperties(propertyMetaDataDtos);
			}

			returnCmd.add(dto);
		}
		return (ClassMetaDataDto[]) returnCmd.toArray(new ClassMetaDataDto[returnCmd.size()]);
	}

}
