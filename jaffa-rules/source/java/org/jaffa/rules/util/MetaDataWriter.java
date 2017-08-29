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
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.dto.*;
import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.rulemeta.Rule;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.util.LabelHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A helper class to interact with meta data.
 */
@Deprecated
public class MetaDataWriter {

	private static Logger log = Logger.getLogger(MetaDataWriter.class);

	/**
	 * Writes the supplied meta data into the supplied source file
	 */
	@Deprecated
	public static void write(ClassMetaDataDto cmd) throws MetaDataWriterException {
		if (cmd.getSourceFileName()==null){
			log.error("No source folder was supplied for meta data export.");
			throw new MetaDataWriterException(MetaDataWriterException.SOURCE_NOT_FOUND);
		}

		Document document = null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.newDocument();
	
		} catch ( javax.xml.parsers.ParserConfigurationException dbe){
			log.error("No source folder was supplied for meta data export.");
			throw new MetaDataWriterException(MetaDataWriterException.PARSE_ERROR, null, dbe);
		}
			
		Element aop = document.createElement("aop");
		Element metadata = document.createElement("metadata");
		metadata.setAttribute("tag", "jaffa.rules");
		metadata.setAttribute("class", cmd.getName());
		if (cmd.getExecutionRealm()!=null && !cmd.getExecutionRealm().equals(""))
			metadata.setAttribute("execution-realm", cmd.getExecutionRealm());
		if (cmd.getExtendsClass()!=null && !cmd.getExtendsClass().equals(""))
			metadata.setAttribute("extends-class", cmd.getExtendsClass());
		if (cmd.getLanguage()!=null && !cmd.getLanguage().equals(""))
			metadata.setAttribute("language", cmd.getLanguage());
		if (cmd.getCondition() != null && !cmd.getCondition().equals(""))
			metadata.setAttribute("condition", cmd.getCondition());

		List<RuleMetaDataDto> classRules = cmd.getRules();
		if (classRules != null) {
			for (RuleMetaDataDto classRule : classRules) {
				Rule ruleInfo = RuleRepository.instance().getRuleByName(classRule.getRuleName());
				Element propertyRule = document.createElement(classRule.getRuleName());
				Map<String, String> params = classRule.getParameters();
				Boolean customProcess = false;
				//If label rule contains a token and a tokenValue then update applicationResources with the new token value
				if (classRule.getRuleName().equals("label")){
					String token = null, value = null;
					if (params != null) {
						for (Map.Entry<String, String> param : params.entrySet()) {
							if (param.getKey().equals("token")){
								token = param.getValue();
							}
							if (param.getKey().equals("tokenValue")){
								value = param.getValue();
							}
						}
					}
					if (token!=null && value!=null){
						try {
							LabelHelper.setLabel(token, value);
						} catch (FrameworkException e) {
							log.debug(e.getLocalizedMessage());
						}
						customProcess=true;
					}
				} 
				if (!customProcess){
					if (params != null) {
						for (Map.Entry<String, String> param : params.entrySet()) {
							if (ruleInfo.getTextParameter()!=null && ruleInfo.getTextParameter().equals(param.getKey())){
								propertyRule.setTextContent(param.getValue());
							}else{
								propertyRule.setAttribute(param.getKey(), param.getValue());
							}
						}
					}
					metadata.appendChild(propertyRule);
				}
			}
		}

		List<PropertyMetaDataDto> fields = cmd.getProperties();
		List<String> processedProperties = new LinkedList<String>();
		if (fields != null) {
			for (PropertyMetaDataDto field : fields) {
				if (processedProperties.indexOf(field.getPropertyName().toLowerCase())>=0){
					log.error("Property has been defined multiple times: " + field.getPropertyName());
					throw new MetaDataWriterException(MetaDataWriterException.DUPLICATE_PROPERTY);
				}
				processedProperties.add(field.getPropertyName().toLowerCase());
				
				Element property = document.createElement("property");
				property.setAttribute("name", field.getPropertyName());
				if (field.getExtendsProperty()!=null && !field.getExtendsProperty().equals(""))
					metadata.setAttribute("extends-property", field.getExtendsProperty());
				if (field.getExtendsClass()!=null && !field.getExtendsClass().equals(""))
					metadata.setAttribute("extends-class", field.getExtendsClass());
				if (field.getLanguage()!=null && !field.getLanguage().equals(""))
					metadata.setAttribute("language", field.getLanguage());
				if (field.getCondition() != null && !field.getCondition().equals(""))
					property.setAttribute("condition", field.getCondition());

				List<RuleMetaDataDto> rules = field.getRules();
				if (rules != null) {
					for (RuleMetaDataDto rule : rules) {
						Rule ruleInfo = RuleRepository.instance().getRuleByName(rule.getRuleName());
						Element propertyRule = document.createElement(rule.getRuleName());
						Map<String, String> params = rule.getParameters();
						if (params != null) {
							for (Map.Entry<String, String> param : params.entrySet()) {
								if (ruleInfo.getTextParameter()!=null && ruleInfo.getTextParameter().equals(param.getKey())){
									propertyRule.setTextContent(param.getValue());
								}else{
									propertyRule.setAttribute(param.getKey(), param.getValue());
								}
							}
						}
						property.appendChild(propertyRule);
					}
				}
				metadata.appendChild(property);
			}
		}

		aop.appendChild(metadata);
		document.appendChild(aop);

		File file = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(document);
			file = new File(new URI(cmd.getSourceFileName()));
			log.debug("Writing aop file to: " + file.getAbsolutePath());
			File parentDir = file.getParentFile();
		    if(! parentDir.exists()) 
		        parentDir.mkdirs();
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (javax.xml.transform.TransformerException te){
			log.error("Error writing xml document into file.");
			throw new MetaDataWriterException(MetaDataWriterException.XML_PARSE_ERROR, null, te);
		} catch (URISyntaxException se) {
			log.error("Source path could not be converted to URI");
			throw new MetaDataWriterException(MetaDataWriterException.SOURCE_NOT_FOUND, null, se);
		}
        //Deprecated
		/*try {

			MetaDataRepository.instance().unload(file);
			MetaDataRepository.instance().load(file);
		} catch (JaffaRulesFrameworkException jrfe){
			log.error("Error loading/unloading class meta data from file.");
			throw new MetaDataWriterException(MetaDataWriterException.FILE_ERROR, null, jrfe);
		}*/

	}

}
