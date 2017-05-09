<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  --
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%-- The contents of this JSP will appear in the 'desktopNav'--%>
<%@ page import="java.util.*,org.jaffa.util.*" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import='org.jaffa.components.navigation.NavAccessor' %>
<%@ page import='org.jaffa.components.navigation.NavOption' %>
<%@ page import='org.jaffa.exceptions.FrameworkException' %>
<%@ page import='org.jaffa.exceptions.ApplicationExceptions' %>
<%@ page import='org.jaffa.presentation.portlet.FormKey' %>
<%@ page import='org.jaffa.presentation.portlet.FormKeyChangeEvent' %>
<%@ page import='org.jaffa.presentation.portlet.FormKeyChangeListener' %>
<%@ page import='org.jaffa.presentation.portlet.component.ComponentManager' %>
<%@ page import='org.jaffa.presentation.portlet.component.IComponent' %>
<%@ page import='org.jaffa.presentation.portlet.session.UserSession' %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.config.ModuleConfig" %>
<%@ page import="org.apache.struts.config.ForwardConfig" %>
<%@ page import="org.apache.log4j.Logger" %>

<%@ taglib uri = '/WEB-INF/struts-tiles.tld' prefix = 'tiles'%>
<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='Portlet' %>
<%!
    private static final Logger log = Logger.getLogger("jaffa.jsp.tiles.desktopNav");

    private String getLabelEditorLink(PageContext pageContext, NavOption navOption) {
        return navOption.getToken() != null ? TagHelper.getLabelEditorLink(pageContext, navOption.getToken()) : "";
    }

    private String printDesktopMenu(PageContext pageContext, List menuNodes, int menuLevel, String desktopId, String desktopName, String strutsTileTemplate)
    throws java.io.UnsupportedEncodingException {
        StringBuffer out = new StringBuffer();
        menuLevel++;
        boolean hasChildren = false;
        for(Iterator it = menuNodes.iterator(); it.hasNext(); ) {
            NavOption nOption = (NavOption) it.next();
            out.append("          ");
            String labelEditorLink = getLabelEditorLink(pageContext, nOption);
            out.append("<tr><td class=\"menuLevel");
            out.append(menuLevel);
            out.append("\">");
            if (nOption.isSubMenu()) {
                out.append(nOption.getLabel() + labelEditorLink);
                out.append("<td></tr>\n");
                if (nOption.getChildren() != null)
                    out.append(printDesktopMenu(pageContext, nOption.getChildren(), menuLevel, desktopId, desktopName, strutsTileTemplate));
            } else {
                out.append("<a class=\"menuLevel");
                out.append(menuLevel);
                out.append("\"  href=\"");
                out.append(nOption.isURL()? nOption.getURL() : "startComponent.do?component=" + nOption.getComponent() + (nOption.getParameters() != null ? "&" + nOption.getParameters() : "") + "&finalUrl=" + java.net.URLEncoder.encode((strutsTileTemplate != null ? strutsTileTemplate : "global.desktop") + "?jaffa.desktopId=" + desktopId + "&desktopName=" + desktopName, "UTF-8"));
                if (nOption.getTarget() != null && nOption.getTarget().length() > 0)
                    out.append("\" target=\"").append(nOption.getTarget());
                out.append("\">");
                out.append(nOption.getLabel());
                out.append("</a>");
                out.append(labelEditorLink);
                out.append("</td></tr>\n");
            }
        }
        return out.toString();
    }

    private String getHomePage(ServletContext application, HttpServletRequest request, HttpServletResponse response, NavOption nOption)
    throws FrameworkException, ApplicationExceptions {
        String homePage = null;
        if (nOption.getHomePage() != null) {
            // The homePage could refer to a component having a format {componentName}{?param1=value1&param2=value2&...}
            String componentName =  nOption.getHomePage();
            String[] componentParameters = null;
            int i = componentName.indexOf('?');
            if (i != -1) {
                if (i + 1 < componentName.length())
                    componentParameters = componentName.substring(i + 1).split("&");
                componentName = componentName.substring(0, i);
            }

            // Check if the HomePage refers to a componentName. This will throw an exception otherwise
            if( ComponentManager.find(componentName) == null)
                // The HomePage does not refer to any component.. treat it like a URL
                homePage = "../../../" + nOption.getHomePage();

            if (homePage == null) {
                // We are dealing with a desktop component
                UserSession userSession = UserSession.getUserSession(request);
                final String sessionAttributeName = nOption.getClass().getName() + '/' + componentName;
                final HttpSession thisSession = request.getSession(); //An anonymous listener requires a final variable

                // Check if a FormKey already exists for this component in the Session and ensure it points to an active component
                FormKey currentIncludedScreenFormKey = (FormKey) thisSession.getAttribute(sessionAttributeName);
                // Get the component
                IComponent component = null;
                if (currentIncludedScreenFormKey != null && currentIncludedScreenFormKey.getComponentId() != null)
                    component = userSession.getComponent(currentIncludedScreenFormKey.getComponentId());
                if(component!=null) {
                    if(!component.isActive())
                        component=null;
                    else if("true".equalsIgnoreCase(request.getParameter("newInstance"))) {
                        log.debug("Current component what reuqested to be killed");
                        component.quit();
                        component=null;
                    }
                }

                // Create an instance of the component and set parameters on it
                if (component == null) {
                    try{
                        component = ComponentManager.run(componentName, userSession);
                        if (componentParameters != null) {
                            for (i = 0; i < componentParameters.length; i++) {
                                String[] valrep = componentParameters[i].split("=");
                                if (valrep != null && valrep.length == 2) {
                                    try {
                                        log.debug("Set Param " + valrep[0] + "=" +  valrep[1]);
                                        BeanHelper.setField(component, valrep[0], valrep[1]);
                                    } catch (Exception e) {
                                        // do nothing
                                        log.info("Error Setting Param " + valrep[0]);
                                    }
                                }
                            }
                        }
                        // Also reflect any request parameters onto the bean
                        for(Iterator it=request.getParameterMap().keySet().iterator();it.hasNext();) {
                            String name = (String)it.next();
                            try {
                                log.debug("Set Param " + name + "=" +  request.getParameter(name));
                                BeanHelper.setField(component, name, request.getParameter(name));
                            } catch (Exception e) {
                            // do nothing
                            log.info("Error Setting Param " + name);
                            }
                        }
                    }catch (java.security.AccessControlException e) {
                        return ("desktopError.jsp");
                    }


                    // Create a FormKey to represent the current desktop
                    FormKey desktopScreen = new FormKey((nOption.getStrutsTileTemplate() != null ? nOption.getStrutsTileTemplate() : "global.desktop") + "?jaffa.desktopId=" + nOption.getDesktopId(), null);
                    component.setReturnToFormKey(desktopScreen);

                    // Set the ContainerFormKey.. This will ensure that the desktopScreen is rendered whenever the component returns any FormKey
                    component.setContainerFormKey(desktopScreen);

                    // Register a Listener, so that the desktop has a handle on the current state of the component
                    component.addFormKeyChangeListener(new FormKeyChangeListener() {
                        public void formKeyChanged(FormKeyChangeEvent e) {
                            thisSession.setAttribute(sessionAttributeName, e.getNewFormKey());
                        }
                    });

                    currentIncludedScreenFormKey = component.display();
                    thisSession.setAttribute(sessionAttributeName, currentIncludedScreenFormKey);
                }

                // Set the FormKey attribute to be used by the included screen
                request.setAttribute(FormKey.class.getName(), currentIncludedScreenFormKey);

                // The path for the included JSP will be determined using struts-config.xml and tiles-definitions.xml
                ForwardConfig forwardConfig = ((ModuleConfig) application.getAttribute(Globals.MODULE_KEY)).findForwardConfig(currentIncludedScreenFormKey.getFormName());
                homePage = forwardConfig != null ? forwardConfig.getPath() : currentIncludedScreenFormKey.getFormName();
            }
        }
        return homePage;
    }
%>
<%

  NavOption nOption  = (NavOption) request.getAttribute("jaffa.desktopOption");
  if (nOption != null && nOption.getChildren() != null) {
%>
<!-- Start of '/jaffa/jsp/tiles/desktopNav.jsp' -->
<script>
function saveSettings(Id) {
  if (document.getElementById("settingsIFrame") != null)
    document.getElementById('iframe').removeChild(document.getElementById("settingsIFrame"));
  newHTML =  "<iframe id='settingsIFrame'  src='jaffa/jsp/tiles/saveDefaultDesktop.jsp?Id=" + Id + "'></iframe>";
  try {
    document.getElementById('iframe').insertAdjacentHTML("afterBegin",newHTML);
  } catch (e) {}
}
</script>
<table height="100%"  cellspacing="0" cellpadding="0" border="0" align='top'>
  <tr>
    <td>
      <table height="100%" class="leftNav" cellspacing="0" cellpadding="0" border="0" valign="top">
        <tr>
           <td>
             <jsp:include page="/jaffa/jsp/tiles/jumpMenuNav.jsp" flush="true"/>
           </td>
        </tr>
        <tr>
          <td>
            <div id="iframe" style="display:none"></div>
            <table width="100%" align="center" cellspacing="0" cellpadding="0" border="0">
<%=printDesktopMenu(pageContext, nOption.getChildren(),0,nOption.getDesktopId(),nOption.getToken() != null ? nOption.getToken() : nOption.getLabel(),nOption.getStrutsTileTemplate())%>
              <tr><td class="menuLevel1"><Portlet:Label key="label.Jaffa.Desktop.UserPreferences"/></td><td></td></tr>
              <tr><td class="menuLevel2"><a class="menuLevel2" href="javascript:void(0);" onClick="javascript:saveSettings(<%=nOption.getDesktopId()%>);"><Portlet:Label key="label.Jaffa.Desktop.MakeDefault"/></a></td></tr>
            </table>
          </td>
        </tr>
        <tr height="100%"><td>&nbsp;</td></tr>
        <tr class="copyright">
          <td class="copyright">
            <a class="copyright" href="http://www.mirotechnologies.com" target="_blank"><img src="imgs/mirologowht.gif" border="0"></a>
          </td>
        </tr>
      </table>
    </td>
    <td align="center" valign="top">
      <tiles:insert name='<%= getHomePage(application, request, response, nOption) %>' ignore='true'/>
    </td>
  </tr>
</table>
<% } %>
<!-- End of '/jaffa/jsp/tiles/desktopNav.jsp' -->
