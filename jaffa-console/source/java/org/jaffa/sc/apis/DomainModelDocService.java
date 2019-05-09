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
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.sc.apis;

import org.jaffa.rules.RulesEngineException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;

/**
 * The WebServiceDocService is used for populating the web service doc tree
 * 
 * @author Saravanan
 */
public class DomainModelDocService {

    /**
     * enable logging for WebServiceDocService
     */
    private static Logger log = Logger.getLogger(DomainModelDocService.class);

    /**
     * @return JSONArray. The JSON array of deployed web service in application.
     */
    public JSONArray loadTree() {

        if (log.isDebugEnabled()) {
            log.debug("Loading tree started");
        }
        JSONArray jsonArray = createTree();
        String jsonResult = jsonArray.toString();
        if (log.isDebugEnabled()) {
            log.debug("result: " + jsonResult); // just for debug purposes-
        }

        return jsonArray;
    }

    /**
     * @return JSONArray.
     */
    private JSONArray createTree() {

        JSONArray array = new JSONArray();
        Map<String, TreeNode> rootNodeCache = new HashMap<String, TreeNode>();
        Map<String, TreeNode> child1NodeCache = new HashMap<String, TreeNode>();
        try {

            Properties props = new Properties();
            String[] classNames = RulesEngineFactory.getRulesEngine().getClassNamesByRuleName("domain-info");

            if (classNames != null) {
                for (String className : classNames) {
                    IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
                    List<Properties> propList = introspector.getMetaDataByRule("domain-info");

                    for (Properties p : propList) {
                        if (p != null && p.size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            String module = (String) p.get("module");
                            String subModule = (String) p.get("sub-module");
                            String domainName = (String) p.get("name");
                            String dbTable = (String) p.get("db-table");

                            if ((module != null && subModule != null) && (!"null".equals(module) && !"null".equals(subModule))) {

                                sb.append(module).append(".").append(subModule).append(".").append(domainName);
                                if (!props.containsKey(sb.toString())) {
                                    if (dbTable != null && !"null".equals(dbTable)) {

                                        StringBuilder value = new StringBuilder();
                                        value.append(className).append("~").append(dbTable);

                                        props.put(sb.toString(), value.toString());

                                    } else {
                                        props.put(sb.toString(), className);
                                    }
                                }
                            }

                            if (log.isDebugEnabled()) {
                                log.debug("key :" + sb.toString());
                                log.debug("className :" + className);
                            }
                        }
                    }
                }
            }

            Enumeration<String> enumeration = (Enumeration<String>) props.propertyNames();
            int rootNodeCounter = 1;

            TreeNode globalRoot = new TreeNode(0, "API Documentation");
            globalRoot.setLeaf(false);
            globalRoot.setIconCls("icon-docs");

            while (enumeration.hasMoreElements()) {
                String serviceName = enumeration.nextElement();
                String[] treeNodeName = serviceName.split("\\.");
                TreeNode root = null;
                TreeNode child1 = null;
                boolean hasRoot = false;
                for (int i = 0; i < treeNodeName.length; i++) {
                    boolean hasChild1 = false;
                    if (i == 0) {
                        if (!rootNodeCache.containsKey(treeNodeName[i])) {
                            root = new TreeNode(rootNodeCounter, treeNodeName[i]);
                            root.setLeaf(false);
                            root.setIconCls("icon-pkg");
                            rootNodeCache.put(treeNodeName[i], root);
                        } else {
                            root = rootNodeCache.get(treeNodeName[i]);
                            hasRoot = true;
                        }
                    } else if (i == 1) {
                        if (!child1NodeCache.containsKey(treeNodeName[i - 1] + treeNodeName[i])) {
                            child1 = new TreeNode(Integer.parseInt(rootNodeCounter + "" + i), treeNodeName[i]);
                            child1.setLeaf(false);
                            child1.setIconCls("icon-pkg");
                            child1NodeCache.put(treeNodeName[i - 1] + treeNodeName[i], child1);
                        } else {
                            child1 = child1NodeCache.get(treeNodeName[i - 1] + treeNodeName[i]);
                            root.setIconCls("icon-pkg");
                            hasChild1 = true;
                        }
                        if (!hasChild1) {
                            root.addChild(child1);
                        }

                    } else if (i == 2) {
                        String[] nodeAtt = props.getProperty(serviceName).split("~");
                        String className = nodeAtt.length > 0 ? nodeAtt[0] : null;
                        String dbTable = nodeAtt.length > 1 ? nodeAtt[1] : null;
                        TreeNode child2 = new TreeNode(Integer.parseInt(rootNodeCounter + "" + i), treeNodeName[i]);
                        child2.setLeaf(true);
                        child2.setIconCls("icon-cls");
                        child2.setIsClass(true);
                        child2.setClassName(className);
                        child2.setServiceName(serviceName);
                        child2.setDbTableName(dbTable);

                        if (child1 != null) {
                            child1.addChild(child2);
                        }
                    }
                }

                if (!hasRoot) {
                    globalRoot.addChild(root);
                }

                rootNodeCounter++;
            }
            array.add(globalRoot.toJson());

        } catch (RulesEngineException ex) {
            log.error(ex);
            ex.printStackTrace(System.out);
        }

        return array;

    }
}
