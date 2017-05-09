/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002-2004 JAFFA Development Group
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Properties;
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
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.SplitString;

/** This is the a templating implementation of the form printing engine, based
 * on the Jakarta Velocity templating engine. It is designed for outputing non-pdf
 * based files like text, html,
 * <a href='http://h20000.www2.hp.com/bc/docs/support/SupportManual/bpl13210/bpl13210.pdf'>PCL5</a> or
 * <a href="http://epsfiles.intermec.com/eps_files/eps_man/066396.pdf">IPL</a>.
 *
 * @author PaulE
 * @version 1.0
 */
public class FormPrintEngineVelocity implements IFormPrintEngine {

    // Store document properties
    private Properties m_documentProperties;

    // Source objet for all dats inserts
    private Object m_dataSource;

    // Name of the pdf template document
    private String m_templateName;

    // Search path to find the templates
    private String m_templateSearchPath;

    // has the pdf content been generated yet?
    private boolean m_processed = false;

    // Generated output file
    private StringWriter m_output = null;

    // Form format helper object
    private FormFormatHelper fmt;

    /** Logger reference */
    public final static Logger log = Logger.getLogger(FormPrintEngineVelocity.class);

    public void generate() throws FormPrintException {

        // make a Context and put data into it
        log.debug("Set Up Context");
        VelocityContext context = new VelocityContext();
        context.put("data", getDataSource());
        fmt = new FormFormatHelper();
        context.put("fmt", fmt);
        context.put("context", ContextManagerFactory.instance().getThreadContext());
        log.debug("data=" + BeanMoulder.printBean(getDataSource()));

        // Split the template file between path and name
        File templateFile = new File(getTemplateName());
        if(!templateFile.exists()) {
            String err = "Velocity Template Not Found - " + getTemplateName();
            log.error(err);
            throw new EngineProcessingException(err);
        }
        String path = templateFile.getParent();
        String file = templateFile.getName(); //(templateFile.getParent()==null?getTemplateName():getTemplateName().substring(templateFile.getParent().length()));
        if(path!=null) {
            Velocity.setProperty("file.resource.loader.path",path);
        }
        log.debug("SearchPath = " + path + ", template = " + file);

        // init the runtime engine.  Defaults are fine.
        try {
            Velocity.setProperty("runtime.log.logsystem.class","org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
            Velocity.init();
            log.debug("Initialized Velocity");
        } catch (Exception e) {
            String err = "Velocity Initialization - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        }

        // Try and load the template
        Template template = null;
        try {
            log.debug("Velocity file.resource.loader.path property = " + Velocity.getProperty("file.resource.loader.path"));
            template = Velocity.getTemplate(file);
        } catch (ResourceNotFoundException e) {
            String err = "Error opening template - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        } catch (ParseErrorException e) {
            String err = "Template Parse Error - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        } catch (Exception e) {
            String err = "Template Load Error - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        }

        try {
            m_output = new StringWriter();
            // render a template
            template.merge(context, m_output );
            //log.debug("Generated outbut based on template...\n" + m_output.getBuffer().toString());
            m_processed = true;

        } catch (Exception e) {
            String err = "Velocity Error - " + e.getLocalizedMessage();
            log.error(err,e);
            throw new EngineProcessingException(err,e);
        }
    }


    /**
     * These permissions are not applicable for the velocity engine.
     * @param canPrint Allow generated PDF to be printed?
     * @param canCopy Allow generated PDF contents to be copied?
     * @param canModify Allow generated PDF contents to be modified?
     * @throws FormPrintException Throw if there was an error setting these properties.
     */
    public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException {
        if(isProcessed())
            throw new IllegalStateException("The form has already been processed");
        // DO NOTHING
    }

    /** Write the PDF to a temp file, returns the file handle, or null if it failed!
     * The file will typically end up in the java temp folder
     * ( <CODE>System.getProperty("java.io.tempdir")</CODE> )
     * @throws FormPrintException thrown if any pdf access error occurs
     * @return return the file handle to the file that was written containing
     * the generated PDF document. This will be null if there were any
     * write errors.
     */
    public File writeForm() throws FormPrintException {
        return writeForm(null);
    }

    /** Write the PDF to a specified file
     * @param fileout file handle to use to write out the pdf
     * @throws FormPrintException thrown if any pdf access error occurs
     * @return return the file handle to the file that was written containing
     * the generated PDF document. This will be null if there were any
     * write errors.
     */
    public File writeForm(File fileout) throws FormPrintException {
        if(!isProcessed())
            throw new IllegalStateException("The form not been processed yet");
        try {
            if(fileout == null)
                fileout = File.createTempFile("form_", ".txt");
            OutputStream bos = new FileOutputStream(fileout);
            bos.write(m_output.getBuffer().toString().getBytes());
            bos.close();
        } catch (IOException e) {
            log.error("Error Writing out PDF", e);
            return null;
        }
        return fileout;
    }


    /** Return the generated PDF document. The generate() method MUST be called
     * prior to this else an exception will be thrown
     * @throws FormPrintException thrown if any pdf access error occurs
     * @return the generated pdf as a byte array
     */
    public byte[] getGeneratedForm() throws FormPrintException {
        if(!isProcessed())
            throw new IllegalStateException("The form not been processed yet");
        return m_output.getBuffer().toString().getBytes();

    }

    /**
     * Get the datasource bean
     * @return Return bean being used as root data source
     */
    public Object getDataSource() {
        return m_dataSource;
    }

    /**
     * Get template name being used to generate the final PDF
     * @return Template Name
     */
    public String getTemplateName() {
        return m_templateName;
    }

    /**
     * Get search path being used to locate the template
     * @return Search Path
     */
    public String getTemplateSearchPath() {
        return m_templateSearchPath;
    }

    /**
     * Set bean that will be used as the root data element for resolving field value
     * @param dataSource Object to be intropected
     */
    public void setDataSource(Object dataSource) {
        m_dataSource = dataSource;
    }

    /**
     * Set template name
     * @param templateName Template Name
     */
    public void setTemplateName(String templateName) {
        m_templateName = templateName;
    }

    /**
     * Set Search Path
     * @param templateSearchPath Search Path
     */
    public void setTemplateSearchPath(String templateSearchPath) {
        m_templateSearchPath = templateSearchPath;
    }

    /**
     * Has the document been generated internally yet?
     * @return True if docuemnt has been processed
     */
    public boolean isProcessed() {
        return m_processed;
    }


    /** Get the properties for the document */
    public void setDocumentProperties(Properties documentProperties) {
        m_documentProperties = documentProperties;
    }

    /** Get the properties for the document */
    protected Properties getDocumentProperties() {
        return m_documentProperties;
    }

    /** Get the specified property for the document */
    protected String getDocumentProperty(String key) {
        if (m_documentProperties == null)
            return null;
        return m_documentProperties.getProperty(key);
    }

    /** Get the specified property for the document */
    protected String getDocumentProperty(String key, String defaultValue) {
        if (m_documentProperties == null)
            return defaultValue;
        return m_documentProperties.getProperty(key, defaultValue);
    }




    //-------------------------------------------------------------------
    //-------------------------------------------------------------------
    // Stuff in the Interface that is not supported or needed by Velocity
    //-------------------------------------------------------------------
    //-------------------------------------------------------------------

    /** No special initialization needed */
    public void initialize() throws FormPrintException {
        // do nothing
    }

    /**
     * Getter for property currentPageOffset.
     * @return Value of property currentPageOffset.
     */
    public int getCurrentPageOffset() {
        // page counts are not supported
        return 0;
    }

    /**
     * Setter for property currentPageOffset.
     * @param currentPageOffset set value of of property currentPageOffset.
     */
    public void setCurrentPageOffset(int currentPageOffset) {
        // Do nothing as we don't support page counting
    }

    /**
     * Getter for property totalPagesOverride.
     * @return Value of property totalPagesOverride.
     */
    public int getTotalPagesOverride() {
        // page counts are not supported
        return 0;
    }

    /**
     * Setter for property totalPagesOverride.
     * @param totalPagesOverride set value of of property totalPagesOverride.
     */
    public void setTotalPagesOverride(int totalPagesOverride) {
        // Do nothing as we don't support page counting
    }

    /**
     * Getter for property totalPages.
     * @return Value of property totalPages.
     */
    public int getTotalPages() {
        // Don't know how many pages will be generated
        return 0;
    }
}
