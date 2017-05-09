/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002-2008 JAFFA Development Group
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
package org.jaffa.modules.printing.services;

import java.io.IOException;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jaffa.beans.moulding.mapping.BeanMoulder;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.modules.printing.services.exceptions.FormPrintException;
import org.jaffa.modules.printing.services.FormCache;
import org.jaffa.session.ContextManagerFactory;

/**
 * @author DennisL
 */
public class VelocityTemplateHelper {

    // Source form data object
    private Object m_dataSource;

    // Form format helper object
    private FormFormatHelper fmt;

    // Field layout template
    private String m_template;

    // Generated output file
    private StringWriter m_output = null;

    /** Logger reference */
    public final static Logger log = Logger.getLogger(VelocityTemplateHelper.class);

    public String[] renderTemplateLines() throws FormPrintException {
        String[] lines;
        String line;
        if(log.isDebugEnabled())
                log.debug("Begin Velocity template rendering process... ");

        if (getDataSource() == null) {
            log.debug("Data source is not set, returning.");
            return null;
        }

        if (getTemplate() == null) {
            log.debug("Template definition is not set, returning.");
            return null;
        }

        try {
            // Since Velocity can only be initialized one time, set the template path used by label printing.
            FormCache cache = new FormCache();
            String path = cache.getTemplatePath();
            Velocity.setProperty("file.resource.loader.path", path);
            Velocity.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
            // init the runtime engine.  Use mostly defaulted values.
            Velocity.init();
            if(log.isDebugEnabled()){
                log.debug("Initialized Velocity");
                log.debug("Velocity file.resource.loader.path property = " + Velocity.getProperty("file.resource.loader.path"));
            }
        } catch (Exception e) {
            String err = "Velocity Initialization - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        }

        // make a Context and put data into it
        if(log.isDebugEnabled())
            log.debug("Set Up Context");
        VelocityContext context = new VelocityContext();
        context.put("data", getDataSource());
        fmt = new FormFormatHelper();
        context.put("fmt", fmt);
        context.put("context", ContextManagerFactory.instance().getThreadContext());
        if(log.isDebugEnabled()){
            log.debug("*** Template Source Data = \n" + BeanMoulder.printBean(getDataSource()) + "\n*** End of Source Data.");
            log.debug("*** Velocity Template = \n" + getTemplate() + "\n*** End of Velocity Template.");
        }
        try {
            m_output = new StringWriter();
            if(log.isDebugEnabled())
                log.debug("Evaluate the Template... ");
            // Render the template
            Velocity.evaluate( context, m_output, "formTemplate", getTemplate());
            line = m_output.toString();
            lines = line.split("\n");
            if(log.isDebugEnabled()) {
                log.debug("*** Rendered Template = \n" + line + "\n*** End of Rendered Template.");
            }
        } catch (Exception e) {
            String err = "Velocity Error - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        }
        if(log.isDebugEnabled())
                log.debug("Finished Velocity template rendering process. ");
        return lines;
    }

    /**
     * Get the datasource bean
     * @return bean being used as root data source
     */
    public Object getDataSource() {
        return m_dataSource;
    }

    /**
     * Get template definition
     * @return template definition
     */
    public String getTemplate() {
        return m_template;
    }

    /**
     * Set data object to be used as the data source to render the template.
     * @param dataSource Object to be used as the data source to render the template.
     */
    public void setDataSource(Object dataSource) {
        m_dataSource = dataSource;
    }

    /**
     * Set template
     * @param template Template definition
     */
    public void setTemplate(String template) {
        m_template = template;
    }
}
