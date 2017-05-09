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


/*
 * NavOptions.java
 *
 * Created on October 20, 2003, 3:29 PM
 */

package org.jaffa.components.navigation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.components.navigation.domain.ComponentAction;
import org.jaffa.components.navigation.domain.MenuOption;
import org.jaffa.components.navigation.NavAccessor;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.ComponentManager;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.MessageHelper;

/** This object represents a node in the menu structure
 * @author JonnyR
 */
public class NavOption {

    private static final Logger log = Logger.getLogger(NavOption.class);

    public static final String TYPE_COMPONENT = "component";
    public static final String TYPE_COMPONENT_RIA = "component-ria";
    public static final String TYPE_DESKTOP = "desktop";
    public static final String TYPE_URL = "url";
    public static final String TYPE_SUB_MENU = "sub menu";


    private String m_label = null;
    private String m_component = null;
    private String m_type = null;
    private String m_strutsTileTemplate = null;
    private String m_homePage = null;
    private String m_params = null;
    private List m_children = null;
    private String m_desktopId = null;
    private String m_url = null;
    private boolean m_urlFinal;
    private String m_target;
    private String m_token = null;

    /** Creates a new instance of NavOption from a menu option.
     * @throws SecurityException if the user should not have access to this option
     */
    public NavOption(NavAccessor nav, MenuOption mOpt) throws SecurityException {
        m_label = mOpt.getLabel();
        if (MessageHelper.hasTokens(m_label)) {
            m_token = m_label;
            m_label = MessageHelper.replaceTokens(m_label);
        }
        if(mOpt.getSubMenu() != null) {
            m_type = TYPE_SUB_MENU;
            m_children = new ArrayList();
            if ( nav.parseMenuOptions(mOpt.getSubMenu().getMenuOption(), m_children) == 0)
              throw new SecurityException("All Options Unavailable. No Access To Sub-Menu " + m_label);
        } else if(mOpt.getDesktopMenu() != null) {
            m_type = TYPE_DESKTOP;
            m_desktopId = "" + nav.getNextDesktopId();
            m_strutsTileTemplate = mOpt.getDesktopMenu().getStrutsTileTemplate();
            m_homePage = mOpt.getDesktopMenu().getHomePage();
            m_children = new ArrayList();
            if ( nav.parseMenuOptions(mOpt.getDesktopMenu().getMenuOption(), m_children) == 0)
              throw new SecurityException("All Options Unavailable. No Access To Desktop " + m_label);
            nav.addDesktopNavOption(m_desktopId, this);
        } else if(mOpt.getComponentAction() != null) {
            m_component = mOpt.getComponentAction().getComponentName();
            if(!SecurityManager.checkComponentAccess(m_component))
                throw new SecurityException("No User Access to Component " + m_component);
            ComponentDefinition cd = ComponentManager.find(m_component);
            if(cd!=null && cd.isRia()) {
                m_type = TYPE_COMPONENT_RIA;
                m_url = cd.getComponentClass();
            } else {
                m_type = TYPE_COMPONENT;
                m_url = mOpt.getComponentAction().getUrlSuffix();
            }
            if(mOpt.getComponentAction().getParam() != null) {
                // build up the parameter list...
                for(Iterator it = mOpt.getComponentAction().getParam().iterator(); it.hasNext(); ) {
                    ComponentAction.Param p = (ComponentAction.Param) it.next();
                    m_params = (m_params==null ? "" : m_params + "&" ) + p.getName() + "=" + p.getValue();
                }
            }
            m_target = mOpt.getComponentAction().getTarget();
        } else if(mOpt.getUrlAction() != null) {
            m_type = TYPE_URL;
            m_url = mOpt.getUrlAction().getUrl().getValue();
            m_urlFinal = mOpt.getUrlAction().getUrl().isAppendFinal();
            m_target = mOpt.getUrlAction().getTarget();

            if(mOpt.getUrlAction().getRequiresComponentAccess()!=null)
                for(Iterator it = mOpt.getUrlAction().getRequiresComponentAccess().iterator(); it.hasNext(); ) {
                    String comp = (String) it.next();
                    if(!SecurityManager.checkComponentAccess(comp))
                        throw new SecurityException("No Access to Component " + comp + " for option " + m_label);
                }

            if(mOpt.getUrlAction().getRequiresFunctionAccess()!=null)
                for(Iterator it = mOpt.getUrlAction().getRequiresFunctionAccess().iterator(); it.hasNext(); ) {
                    String func = (String) it.next();
                    if(!SecurityManager.checkFunctionAccess(func))
                        throw new SecurityException("No Access to Function " + func + " for option " + m_label);
                }


        } else {
            throw new SecurityException("Unknown Option Type for " + m_label);
        }

    }

    /** Returns a list of children (navOptions) that are under this node in the structure
     * @return List continaing navOption objects
     */
    public List getChildren() {
        if(TYPE_DESKTOP.equals(m_type) || TYPE_SUB_MENU.equals(m_type))
            return m_children;
        else
            throw new UnsupportedOperationException("Option Type " + m_type + " does not support getChildren()");
    }

    /** Method returns the String for the menu option
     * @return String that is the text to be displayed on the menu
     */
    public String getLabel() {
        return m_label;
    }

    /** Returns a URL that will either be complete or partial.
     * @return The String to build the URL on the HTML page.
     */
    public String getURL() {
        return m_url;
    }

    /** Returns the target for a URL.
     * @return the target for a URL.
     */
    public String getTarget() {
        return m_target;
    }

    /** Returns a string of any parameters necessary for the URL.
     * @return Returns a string that is any paramaters necessary for the URL.
     */
    public String getParameters() {
        return m_params;
    }


    /** Returns the component's ID in case the URL has to be built my hand.
     * @return Returns a string that is the component's ID
     */
    public String getComponent() {
        return m_component;
    }

    /** Returns the unique Id assigned to this desktop.
     * @return Returns a string that is the desktop's ID
     */
    public String getDesktopId() {
        return m_desktopId;
    }

    /** Returns the menu type
     * @return Returns the type of menu option
     */
    public String getType() {
        return m_type;
    }

    /** Returns the StrutsTileTemplate
     * @return Returns the StrutsTileTemplate of menu option
     */
    public String getStrutsTileTemplate() {
        return m_strutsTileTemplate;
    }

      /** Returns the StrutsTileTemplate
     * @return Returns the StrutsTileTemplate of menu option
     */
    public String getHomePage() {
        return m_homePage;
    }

    /** Returns the Token
     * @return Returns the Token of menu option
     */
    public String getToken() {
        return m_token;
    }
    /** Returns the menu type
     * @return Returns true if this is a desktop
     */
    public boolean isDesktop() {
        return TYPE_DESKTOP.equals(m_type);
    }
    /** Returns the menu type
     * @return Returns true if this is a sub menu, either in the Global or Desktop menu
     */
    public boolean isSubMenu() {
        return TYPE_SUB_MENU.equals(m_type);
    }
    /** Returns the menu type
     * @return Returns true if this is a component action
     */
    public boolean isComponent() {
        return TYPE_COMPONENT.equals(m_type);
    }
    /** Returns the menu type
     * @return Returns true if this is a Web 2.0/RIA component action
     */
    public boolean isRia() {
        return TYPE_COMPONENT_RIA.equals(m_type);
    }
    /** Returns the menu type
     * @return Returns true if this is a url action
     */
    public boolean isURL() {
        return TYPE_URL.equals(m_type);
    }



}
