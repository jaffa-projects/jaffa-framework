/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2016 JAFFA Development Group
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

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Saravanan
 */
public class TreeNode {

    private Integer id;
    private String text;
    private boolean leaf = true;
    private String iconCls;
    private boolean isClass;
    private String className;
    private String serviceName;
    private String dbTableName;
    private String soaEventName;
    private String label;
    private String description;
    private String soaEventParams;
    private String uniqueName;
    private boolean isEnabled = true;
    private boolean isSelectable = false;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode(String text) {
        this.text = text;
    }

    public TreeNode(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("text", text);
        json.put("leaf", leaf);
        json.put("isClass", isClass);
        if (iconCls != null) {
            json.put("iconCls", iconCls);
        }
        if (className != null) {
            json.put("className", className);
        }
        if (serviceName != null) {
            json.put("serviceName", serviceName);
        }
        if (dbTableName != null) {
            json.put("dbTableName", dbTableName);
        }
        if (soaEventName != null) {
            json.put("soaEventName", soaEventName);
        }
        if (label != null) {
            json.put("label", label);
        }
        if (description != null) {
            json.put("description", description);
        }
        if (soaEventParams != null && (leaf || isSelectable)) {
            json.put("soaEventParams", soaEventParams);
        }
        if (uniqueName != null) {
            json.put("uniqueName", uniqueName);
        }
        json.put("isEnabled", isEnabled);
        json.put("isSelectable", isSelectable);


        if (children.isEmpty()) {
            return json;
        }

        JSONArray jsonArray = new JSONArray();
        for (TreeNode child : children) {
            jsonArray.add(child.toJson());
        }

        json.put("children", jsonArray);
        return json;
    }

    public List<TreeNode> getChild() {
        return children;
    }

    public void addChild(TreeNode node) {
        leaf = false;
        children.add(node);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * @return the isClass
     */
    public boolean isIsClass() {
        return isClass;
    }

    /**
     * @param isClass the isClass to set
     */
    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }

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
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the dbTableName
     */
    public String getDbTableName() {
        return dbTableName;
    }

    /**
     * @param dbTableName the dbTableName to set
     */
    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    /**
     * @return the soaEventName
     */
    public String getSoaEventName() {
        return soaEventName;
    }

    /**
     * @param soaEventName the soaEventName to set
     */
    public void setSoaEventName(String soaEventName) {
        this.soaEventName = soaEventName;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the unique name of this tree node
     *
     * @return the unique name of this tree node
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * Sets the unique name of this tree node
     *
     * @param uniqueName the unique name of this tree node
     */
    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    /**
     * @return the soaEventParams
     */
    public String getSoaEventParams() {
        return soaEventParams;
    }

    /**
     * @param soaEventParams the soaEventParams to set
     */
    public void setSoaEventParams(String soaEventParams) {
        this.soaEventParams = soaEventParams;
    }

    /**
     * Gets the isEnabled flag for this tree node
     *
     * @return true if this tree node is in the enabled state
     */
    public boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * Sets the isEnabled flag for this tree node
     *
     * @param isEnabled true if this tree node is in the enabled state
     */
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Gets the isSelectable flag for this tree node
     *
     * @return true if this tree node is user selectable
     */
    public boolean getIsSelectable() {
        return isSelectable;
    }

    /**
     * Sets the isSelectable flag of this tree node
     *
     * @param isSelectable true if this tree node is user selectable
     */
    public void setIsSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
    }
}