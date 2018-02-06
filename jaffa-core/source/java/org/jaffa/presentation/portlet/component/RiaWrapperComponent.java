/*
 * RiaWrapperComponent.java
 *
 * Created on June 30, 2008, 5:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jaffa.presentation.portlet.component;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.presentation.portlet.FormKey;

/** This is a dummy component used to lauch a new Web 2.0 component using the existing
 * Web 1.0 architecture.
 * <p>
 * To use this you should put the name of the initial RIA web page (must be a JSP)
 * in the component class tag in component.xml.
 *
 * <p>
 * For Example
 <code>
<component id="WorkRecording.Setup.RulesMaintenance">
  <class>workrecording/setup/rulesmaintenance/index.jsp</class>
  <type>Setup</type>
  <mandatory-function name="WorkRecording.Setup.RulesMaintenance" />
</component>
 </code>
 *
 * @author PaulE
 */
public class RiaWrapperComponent extends Component {
    private static Logger log = Logger.getLogger(RiaWrapperComponent.class);

    private Properties parameters = new Properties();

    /**
     * Get the URL for the RIA component to run. By default this comes from the
     * Component Definitions class name, that would end in '.jsp'. If you are subclassing
     * RiaWrapperComponent you will be putting the class of the component it this field and
     * in this case need to override this method to specify the JSP to run.
     *
     * @return The URL for the RIA component to run from the ComponentDefinition's Class field
     */
    public String getRiaUrl() {
        return getComponentDefinition().getComponentClass();
    }

    /**
     * Returns a FormKey, which has the componentId & the formName to which control should be passed
     *
     * @return the FormKey object
     * @throws FrameworkException if any framework error occurs.
     * @throws ApplicationExceptions if any application error occurs.
     */
    public FormKey display() throws FrameworkException, ApplicationExceptions {
        String url = getRiaUrl();
        if(!url.startsWith("/"))
            url = "/"+url;
        for(Map.Entry e : parameters.entrySet())
            try {
                url+=(url.indexOf("?") < 0 ? "?": "&")
                   + e.getKey()
                   + "="
                   + URLEncoder.encode((e.getValue()==null)?"":e.getValue().toString(), "UTF-8");
            } catch (java.io.UnsupportedEncodingException ex) {
                log.error("Ignored request parameter " + e.getKey() + " = " + e.getValue(), ex);
            }
        log.debug("Forward to Web 2.0 URL - " + url);
        quit();
        return new FormKey( url , null, null, true);
    }

    /**
     * Getter for property parameters.
     * @return Value of property parameters.
     */
    public Properties getParameters() {
        return this.parameters;
    }

    /**
     * Setter for property parameters.
     * @param parameters New value of property parameters.
     */
    public void setParameters(Properties parameters) {
        this.parameters = parameters;
    }

    /** Override base implementation which expects a bean property for each parameter,
     * where as this assumes the parameters are loaded into a property object
     * @param request The HTTP request we are processing
     */
    public void reflectAndSetParms(HttpServletRequest request) {
        Class clazz = this.getClass();
        for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String parameterName = (String) enumeration.nextElement();
            if(!"component".equals(parameterName))
                parameters.setProperty(parameterName, request.getParameter(parameterName));
        }
    }

}
