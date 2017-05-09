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

package org.jaffa.presentation.portlet.widgets.controller;

import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.util.ListProperties;
import java.util.Properties;
import java.util.Enumeration;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.FormBase;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import java.io.IOException;

/**
 * Helper class used by the new 'PropertyEditor' breakup group, now available in
 * the new Finder v2.0 pattern. These helpers convert a property object to and from
 * a string representation into a user grid of name,value,comment rows.
 * <p>
 * The property editor can be specified in a maintence pattern using the 
 * Breakup/WidgetType construct as follows...
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;!DOCTYPE Root PUBLIC "-//JAFFA//DTD Object Maintenance Meta 2.0//EN"
 *   "http://jaffa.sourceforge.net/DTD/object-maintenance-meta_2_0.dtd"&gt;
 * &lt;Root&gt;
 *   ...
 *   &lt;Fields&gt;
 *     ...
 *     &lt;Field&gt;
 *       &lt;Name&gt;UserPrefs&lt;/Name&gt;
 *       &lt;DataType&gt;STRING&lt;/DataType&gt;
 *       &lt;Display&gt;true&lt;/Display&gt;
 *       &lt;DomainField&gt;UserPrefs&lt;/DomainField&gt;
 *       &lt;Breakup&gt;
 *         &lt;WidgetType&gt;PropertyEditor&lt;/WidgetType&gt;
 *       &lt;/Breakup&gt;
 *     &lt;/Field&gt;
 *   &lt;/Fields&gt;
 *   ...
 * &lt;/Root&gt;
 * </pre>
 * @author PaulE
 * @version 1.0
 */
public class PropertyEditorHelper {

    private static final Logger log = Logger.getLogger(PropertyEditorHelper.class);

    private final static String KEY = "key";
    private final static String VALUE = "value";
    private final static String COMMENT = "comments";
    private final static String EMPTY = "";

    /**
     * Convert a property string into a grid model, if the model is already
     * being cached on the form, it returns the cached version. If it was not cached
     * it is added to the cache after it has been create.
     * <p>
     * <b>NOTE: if the model is cached and this method is called with a 'new' source
     * string, it will only return the current cached version, not a new grid based
     * on the new source string. If you want this, clear the model cache prior to calling
     * this method
     * </b>
     * @param form the form object that will contains this model
     * @param cacheName the name being used by the form to cache this model
     * @param source the source string containing the property data
     * @return the converted grid model
     */    
    public static GridModel getPropertiesWM(FormBase form, String cacheName, String source) {
        GridModel model = (GridModel) form.getWidgetCache().getModel(cacheName);
        if (model == null) {
                model = new GridModel();
                ListProperties p = new ListProperties();
                try {
                    if(source != null)
                        p.load(new ByteArrayInputStream(source.getBytes()));
                    for(Enumeration e = p.keys(); e.hasMoreElements(); ) {
                        String key = (String) e.nextElement();
                        String value = (String) p.getProperty(key);
                        String comment = (String) p.getComments(key);
                        GridModelRow row = model.newRow();
                        row.addElement(KEY, new EditBoxModel(key==null?EMPTY:key));
                        row.addElement(VALUE, new EditBoxModel(value==null?EMPTY:value));
                        row.addElement(COMMENT, new EditBoxModel(comment==null?EMPTY:value));
                    }
                    form.getWidgetCache().addModel(cacheName, model);
                } catch (IOException e) {
                    log.error("Failed to Load Properties For Field : " + cacheName, e);
                }
            }
        return model;
    }


    /**
     * Convert the user grid back to a string format
     * @param model User grid model that contains the property entries
     * @return the formated string, including a date time stamp.
     * Returns a null string if there are no rows.
     */    
    public static String getProperties(GridModel model) {
        if(model==null || model.getRows() == null || model.getRows().size() == 0 ) return null;
        ListProperties p = new ListProperties();
        for(Iterator i = model.getRows().iterator(); i.hasNext(); ) {
            GridModelRow row = (GridModelRow) i.next();
            EditBoxModel e = (EditBoxModel) row.get(KEY);
            String key = (String) (e==null?null:e.getValue());
            e = (EditBoxModel) row.get(VALUE);
            String value = (String) (e==null?null:e.getValue());
            e = (EditBoxModel) row.get(COMMENT);
            String comment = (String) (e==null?null:e.getValue());
            if( (key!=null && key.length()>0) || (value!=null && value.length()>0) ) {
                log.debug("Save Prop '" + (key==null ? "null" : key + "[" + key.length() +"]" ) + "="
                            + (value==null ? "null" : value + "[" + value.length() +"]" ) + "'");
                p.setProperty(key,value,comment);
            }
        }

        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            p.store(b, null);
            b.close();
            return b.toString();
        } catch (IOException e) {
            log.error("Failed to Store Field Properties", e);
            return null;
        }
    }

    /**
     * Call from the action class to add a new row at the end of the property list
     * @param model grid model to add the row to
     */    
    public static void addRow(GridModel model) {
        GridModelRow row = model.newRow();
        row.addElement(KEY, new EditBoxModel(EMPTY));
        row.addElement(VALUE, new EditBoxModel(EMPTY));
        row.addElement(COMMENT, new EditBoxModel(EMPTY));
    }

    /**
     * Called by the action class to remove a specified property from the list
     * @param model grid model to remove the row from
     * @param rowId row id to remove
     */    
    public static void removeRow(GridModel model, int rowId) {
        GridModelRow row = model.getRowById(rowId);
        if(row!=null)
            model.removeRow(row);
    }


}
