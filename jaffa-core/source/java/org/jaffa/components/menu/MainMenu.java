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

package org.jaffa.components.menu;


import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.apache.struts.action.Action;
import org.apache.log4j.Logger;
import org.jaffa.security.SecurityTag;
import org.jaffa.config.Config;
import org.jaffa.util.URLHelper;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;

/** MainMenu Class - Reads the XML file containing the Menu Groups and Options
 * and builds a LinkedHashMap of Groups / Options that the User logged in has
 * security access to.
 *
 * @author MegS
 * @version 1.0
 */
public class MainMenu {
    /** Set up Logging for Log4J */
    private static Logger log = Logger.getLogger(MainMenu.class);

    // If no property is specified in the framework.properties file, this is where the system
    // will look for the menulist.xml policy file.
    private static final String DEFAULT_MENULIST_LOCATION = "classpath:///resources/menu-list.xml";

    private Option m_option = null;
    private Group m_group = null;
    private Option[] m_options = null;
    private Collection coll = new ArrayList();
    // XML Elements referred
    private static final String XML_GROUP = "group";
    private static final String XML_OPTION = "option";
    private static final String XML_GROUPNAME = "group-name";
    private static final String XML_DESC = "description";
    private static final String XML_GROUPICON = "group-icon";
    private static final String XML_OPTIONNAME = "option-name";
    private static final String XML_OPTIONICON = "option-icon";
    private static final String XML_COMPONENT = "component";
    private static final String XML_TITLE = "title";
    private static final String XML_URL = "url";
    private Map hm;


    /** Default constructor.
     */
    public MainMenu() {
        reset();
    }

    /** Builds the HashMap of the Menu Groups / Options the User logged in has
     * security access to, if the HashMap hasnt already been built.
     * @param request  The web server request to use for context */
    public void determineComp(HttpServletRequest request, PageContext pageContext) {
        if (hm == null) {
            try {
                this.process(request, pageContext);
            }catch  (JDOMException j){
                log.error("Error Loading Menu XML", j);
            }catch (FileNotFoundException f) {
                log.error("Error Loading Menu XML - File Not Found");
            }catch (IOException io) {
                log.error("Error Loading Menu XML - " + io.getMessage(), io);
            }
        }
    }

    private void process(HttpServletRequest request, PageContext pageContext)
    throws JDOMException, FileNotFoundException, IOException {
        String prop = (String) Config.getProperty(Config.PROP_MENULIST_URL, DEFAULT_MENULIST_LOCATION);
        URL roleUrl = null;
        try {
            // Create a URL for the resource file...
            roleUrl = URLHelper.newExtendedURL(prop);
        } catch (MalformedURLException e) {
            log.fatal("Can't Find Menu List File, Bad URL - " + prop, e);
            throw new SecurityException();
        }
        // parse the tempFileName.. Force XML validation
        SAXBuilder builder = new SAXBuilder(true);

        // Specifies the EntityResolver to resolve DTD used in XML documents
        builder.setEntityResolver(new DefaultEntityResolver());

        // Specifies the ErrorHandler to handle warning/error/fatalError conditions
        builder.setErrorHandler(new DefaultErrorHandler());

        Document document = builder.build(roleUrl);
        Element root = document.getRootElement();
        hm = new LinkedHashMap();
        List components = root.getChildren();

        List groups = new ArrayList();

        for (Iterator itr = components.iterator();itr.hasNext(); ) {

            if (m_group == null)
                m_group = new Group();
            else {
                if ( !(coll.isEmpty()) ) {
                    hm.put(m_group,coll);
                    coll = new ArrayList();
                    m_group = new Group();
                }
            }

            Element group = (Element) itr.next();
            String groupName = group.getChildTextTrim(XML_GROUPNAME);
            String description = group.getChildTextTrim(XML_DESC);
            String groupIcon = group.getChildTextTrim(XML_GROUPICON);
            String title = group.getChildTextTrim(XML_TITLE);
            String gTitle = null;
            if (MessageHelper.hasTokens(title)) {
                 gTitle = MessageHelper.replaceTokens(pageContext, title);
                 title = gTitle;
            }
            m_group.setGroupName(groupName);
            m_group.setDescription(description);
            m_group.setGroupIcon(groupIcon);
            m_group.setGroupTitle(title);

            List groupElements = group.getChildren(XML_OPTION);
            int i = 0;
            for (Iterator itr1 = groupElements.iterator();itr1.hasNext(); ) {
                Element build = (Element) itr1.next();
                String optionName = build.getChildTextTrim(XML_OPTIONNAME) ;
                String optionIcon = build.getChildTextTrim(XML_OPTIONICON) ;
                String component = build.getChildTextTrim(XML_COMPONENT) ;
                String comp_URL;
                boolean compExist = false;
                if (build.getChildTextTrim(XML_COMPONENT) == null || build.getChildTextTrim(XML_COMPONENT).length() == 0)
                    comp_URL = build.getChildTextTrim(XML_URL);
                else {
                    comp_URL = build.getChildTextTrim(XML_COMPONENT);
                    compExist = true;
                }

                if(log.isDebugEnabled())
                    log.debug(XML_COMPONENT +  ":" + comp_URL);
                m_option = new Option();
                String name = null;
                if (MessageHelper.hasTokens(optionName)) {
                    name = MessageHelper.replaceTokens(pageContext, optionName);
                    optionName = name;
                }
                if(log.isDebugEnabled())
                    log.debug("The Option Name is : " + optionName);
                m_option.setName(optionName);
                m_option.setIcon(optionIcon);
                m_option.setCompURL(comp_URL);
                if (compExist) {
                    m_option.setURLTrue(false);
                    // check if user has access to the component
                    if (SecurityTag.hasComponentAccess(request, comp_URL))
                        coll.add(m_option);
                } else {
                    m_option.setURLTrue(true);
                    coll.add(m_option);
                }
            }
        }
        if ( !(coll.isEmpty()) )
            hm.put(m_group,coll);
    }

    /** Reset the HashMap to null
     */
    public void reset() {
        hm = null;
    }

    /** Return Map containing list of Groups / Options User logged in has access.
     * @return Map - Groups and Options User has access to.
     */
    public Map getComponents() {
        return (Map) hm;
    }

    /** Test that the menu can be read ok */
    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.determineComp(null, null);
        Map mp = menu.getComponents();
        if (mp.isEmpty())  {
            System.out.println("HashMap is empty");
        } else {
            Collection coll = new ArrayList();

            Set set = mp.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry)i.next();
                Group group = (Group) me.getKey();
                ArrayList al = new ArrayList();
                al = (ArrayList) me.getValue();
                System.out.println("Group = " + group);
                String icon = group.getGroupIcon();
                String desc = group.getDescription();
                System.out.println("Desc = " + desc);
                System.out.println("Option = " + al);
                for (Iterator it = al.iterator();it.hasNext();) {
                    Option opt = (Option) it.next();
                    String optIcon = opt.getIcon();
                    String optLink = opt.getCompURL();
                    boolean URL = opt.getURLTrue();
                    System.out.println("Each option is: " + opt);
                    System.out.println("Icon is: " + optIcon);
                }
            }
        }
    }
}











