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
package org.jaffa.presentation.portlet.widgets.controller;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.controller.exceptions.XmlStructureRuntimeException;
import org.jaffa.presentation.portlet.widgets.controller.usergriddomain.ObjectFactory;
import org.jaffa.presentation.portlet.widgets.controller.usergriddomain.UserGridColumnSettings;
import org.jaffa.presentation.portlet.widgets.controller.usergriddomain.UserGridSettings;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.SimpleWidgetModel;
import org.jaffa.presentation.portlet.widgets.taglib.GridTag;
import org.jaffa.security.SecurityManager;
import org.jaffa.session.ContextManagerFactory;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

/** Controller for the Grid widget.
 * @author PaulE
 * @version 1.1
 * This has been merged with the UserGrid so it no manages
 * <li> customization of the grid
 * <li> state of rows for tree widget
 * <li> toggling floating hints feature
 */
public class GridController {

    private static Logger log = Logger.getLogger(GridController.class);

    // These constants are used for creating a well-formed XML
    private static final String XML_START = "<?xml version=\"1.0\"?><root>";
    private static final String XML_END = "</root>";

    // These constants are used for accessing the XML elements
    private static final String XML_WIDGET = "widget";
    private static final String XML_ROW = "row";
    private static final String XML_FIELD = "field";
    private static final String XML_COLUMN = "column";
    private static final String XML_NAME = "name";
    private static final String XML_WIDTH = "width";
    private static final String XML_USER = "user";
    private static final String XML_GRIDID = "gridid";
    private static final String XML_SETTINGS = "settings";
    private static final String XML_TABLEWIDTH = "tableWidth";
    private static final String XML_RESTORE = "restore";
    private static final String XML_HINTS = "hints";
    private static final String XML_EXPANDED = "expanded";
    private static final String XML_DISPLAY = "display";

    /** Updates the model with the input value.
     * This will throw the XmlStructureRuntimeException, in case the input is not well-formed.
     * @param value The new value for the model.
     * @param model The model to be updated.
     */
    public static void updateModel(String value, GridModel model) {
        try {
            String user = null;
            String gridId = null;
            String tableWidth = null;
            List listSettings = new ArrayList();
            // get the root element for the input XML
            Element root = getRootElement(value);

            // iterate through each element in the Grid, and update there model
            for (Iterator it = root.getChildren(XML_WIDGET).iterator(); it.hasNext();) {
                Element element = (Element) it.next();
                int rowId = Integer.parseInt(element.getAttribute(XML_ROW).getValue());
                String field = element.getAttribute(XML_FIELD).getValue();
                String contents = getContents(element);
                GridModelRow row = model.getRowById(rowId);
                Object innerModel = row.get(field);
                if (innerModel != null) {
                    interpretAndUpdateModel(contents, innerModel);
                }
            }

            // Gets the Setting info posted from the JSP
            // This is only posted when the Grid's Layout is being modified
            Element element = root.getChild(XML_SETTINGS);
            if (element != null) {
                //user = element.getChildText(XML_USER);
                // no longer expect the user to be passed as this could be spoofed, get it from the security manager
                if (SecurityManager.getPrincipal() != null) {
                    user = SecurityManager.getPrincipal().getName();
                } else {
                    // Error can't save settings if no user is set
                    log.error(" Can't save user settings: User unknown or not set by SecurityManager.");
                    model.setErrorInSavingUserSettings(true);
                    return;
                }
                gridId = element.getChildText(XML_GRIDID);
                log.debug("Grid id=" + gridId + " for user=" + user + " is being modified");
                // See if the user selected to restore the settings
                // This means delete there customized version
                if (element.getChild(XML_RESTORE) != null) {
                    // Delete custom settings
                    model.setErrorInSavingUserSettings(!UserGridManager.restore(user, gridId));
                    log.debug("Grid id=" + gridId + " for user=" + user + " has been reset to the default");
                } else {

                    tableWidth = element.getChildText(XML_TABLEWIDTH);

                    // create an ObjectFactory instance.
                    ObjectFactory objFactory = new ObjectFactory();
                    UserGridSettings thing = objFactory.createUserGridSettings();
                    List cols = thing.getUserGridColumnSettings();

                    //Iterates through all the column settings builds them into a List
                    //And passes that list to the UserGridManager setColSettings()
                    if (log.isDebugEnabled()) {
                        log.debug("Saving Grid Column Listing for User=" + user + ", GridId=" + gridId);
                    }

                    for (Iterator it = element.getChildren(XML_COLUMN).iterator(); it.hasNext();) {
                        Element column = (Element) it.next();
                        String name = column.getAttribute(XML_NAME).getValue();
                        String width = column.getAttribute(XML_WIDTH).getValue();
                        UserGridColumnSettings thing2 = objFactory.createUserGridColumnSettings();
                        thing2.setName(name);
                        thing2.setWidth(width);
                        cols.add(thing2);
                        if (log.isDebugEnabled()) {
                            log.debug("    show column = " + name + ", width=" + width);
                        }
                    }
                    //Sets the value of the Outer Table Width
                    thing.setGridWidth(tableWidth);

                    // Save settings to xml file
                    // Raise the error flag in the model, in case the 'save' fails
                    // The UserGridTag will raise an Error, in case the error-flag is set, and then reset the error-flag
                    model.setErrorInSavingUserSettings(!UserGridManager.setColSettings(user, gridId, thing));
                }

                // See if the user whats to toggle the popup hints.
                String hints = element.getChildText(XML_HINTS);
                if (hints != null) {
                    try {
                        log.debug("Current Rule " + GridTag.RULE_USERGRID_POPUP + " = " +
                                ContextManagerFactory.instance().getProperty(GridTag.RULE_USERGRID_POPUP));
                        ContextManagerFactory.instance().setUserPreference(GridTag.RULE_USERGRID_POPUP, hints);
                        log.debug("New Rule " + GridTag.RULE_USERGRID_POPUP + " = " +
                                ContextManagerFactory.instance().getProperty(GridTag.RULE_USERGRID_POPUP));
                    } catch (IOException e) {
                        log.error("Failed to changed user preferences for " + GridTag.RULE_USERGRID_POPUP, e);
                    }
                }

            }

            // Process any state related to the Tree Widget
            for (Iterator it = root.getChildren(XML_DISPLAY).iterator(); it.hasNext();) {
                Element data = (Element) it.next();
                int rowId = Integer.parseInt(data.getAttribute(XML_ROW).getValue());
                Boolean expanded = new Boolean(data.getAttribute(XML_EXPANDED).getValue());
                String contents = getContents(data);
                GridModelRow row = model.getRow(rowId);
                row.addElement("isDisplayed", new Boolean(contents));
                row.addElement("isExpanded", expanded);
            }

        } catch (Exception e) {
            log.error("Failed to create Java content objects for marshalling into XML", e);
            model.setErrorInSavingUserSettings(true);
        }
    }

    private static Element getRootElement(String xmlIn) {
        // Convert to valid XML
        String xml = XML_START + (xmlIn == null ? "" : xmlIn) + XML_END;

        // Load the input into JDOM
        Document doc = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            doc = builder.build(new BufferedReader(new StringReader(xml)));
        } catch (org.jdom.JDOMException e) {
            String str = "Invalid Packed Data From Widget. Can't Load into XML\n" + "JDOM Reason : " + e.getMessage() + '\n' + "XML Data was : " + xml;
            log.error(str, e);
            throw new XmlStructureRuntimeException(str, e);
        } catch (IOException e) {
            String str = "IOException thrown while writing the XML to a String";
            log.error(str, e);
            throw new XmlStructureRuntimeException(str, e);
        }

        return doc.getRootElement();
    }

    private static String getContents(Element element) {
        String contents = null;

        // if this element has inner tags, then get the corresponding XML fragment
        if (!element.getChildren().isEmpty()) {
            try {
                XMLOutputter xout = new XMLOutputter();
                StringWriter sw = new StringWriter();
                xout.outputElementContent(element, sw);
                contents = sw.getBuffer().toString();
            } catch (IOException e) {
                String str = "IOException thrown while writing the XML to a String";
                log.error(str, e);
                throw new XmlStructureRuntimeException(str, e);
            }
        } else {
            contents = element.getTextTrim();
        }

        // header.js converts # to %23 when packaging the data. Lets revert that change
        return contents != null ? contents.replaceAll("%23", "#") : null;
    }

    private static void interpretAndUpdateModel(String contents, Object model) {
        if (model instanceof SimpleWidgetModel) {
            SimpleWidgetController.updateModel(contents, (SimpleWidgetModel) model);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("UnSupported model retrieved from a GridModelRow '" + model.getClass() + "'... Cannot be updated");
            }
        }
    }
}
