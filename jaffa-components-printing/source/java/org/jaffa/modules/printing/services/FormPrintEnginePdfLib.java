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

import com.pdflib.PDFlibException;
import com.pdflib.pdflib;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.modules.printing.services.FormPrintEngine.PageDetails;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.modules.printing.services.exceptions.FormPrintException;




/** The prind engine is a wrapper for the PDFlib utility. This engine will take
 * a template PDF will block fields defined, and then merge that defintion
 * with the data supplied in a JavaBean. The generated bdf can then be
 * access, or written out to a file.
 * @author PaulE
 * @version 1.0
 * @since 2.0
 */
public class FormPrintEnginePdfLib extends FormPrintEngine {

    /** Logger reference */
    public final static Logger log = Logger.getLogger(FormPrintEnginePdfLib.class);

    public final static String DEFAULT_FONT = "Helvetica";

    // Possible permissions to be used
    private String m_permissions = null;

    // Main handle on PDF generation
    private pdflib m_pdf = null;

    // PDFLib Reference to the template being used
    private int m_pdfTemplate;

    // PDFLib Reference to the current page being generated
    private int m_pdfPage;


    /** Perform any initialization activity for generating the document */
    protected void initEngine() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Instantiate the PDFLib engine");
        try {
            m_pdf = new pdflib();

            m_pdf.set_parameter("SearchPath", getTemplateSearchPath());

            // Set permissions if there are any
            if(m_permissions!=null) {
                m_pdf.set_parameter("masterpassword", "anabahabic");
                m_pdf.set_parameter("permissions", m_permissions);
            }

            // Generate a PDF in memory; insert a file name to create PDF on disk
            if (m_pdf.begin_document("", "") == -1) {
                log.error("Creating Blank PDF - " + m_pdf.get_errmsg());
            }
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }

    /**
     * Process the template and do the following
     * <ol>
     * <li>Throw errors if there is something wrong with the template (in getTemplateName())
     * <li>Determine to the total numer of pages in the template
     *    (The template should have at least one page)
     * <li>Populate an array where each template page should have one entry in it.
     *    Each entry will a PageDetails object, containing a list of fields
     *    on the page, and a list of repeating entities.
     * </ol>
     * @throws FormPrintException Thrown if there is an error parsing the template pdf
     * @return This returns a List of PageDetails objects which contain data about each page.
     * It is assumed that the size of the list indicated the number of pages in
     * the template document being used
     */
    protected List parseTemplatePages() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Load the Template - " + getTemplateName());
        try {

            // Load Template Page, will look in search path relative to bin folder
            m_pdfTemplate = m_pdf.open_pdi(getTemplateName(), "", 0);
            if (m_pdfTemplate == -1) {
                log.error("Loading Base PDF (" + getTemplateName() + ") : "  + m_pdf.get_errmsg());
                throw new PDFlibException("Error: " + m_pdf.get_errmsg());
            }

            // Set document properties
            m_pdf.set_info("Creator", "From Template " + getTemplateName());
            m_pdf.set_info("Author", "AuRA4D");


            // Load referenced fonts
            m_pdf.load_font(DEFAULT_FONT, "winansi", "");


            // Find out how many pages are in the template.
            int templatePages = 0;
            for(; m_pdf.open_pdi_page(m_pdfTemplate, templatePages+1, "") != -1; templatePages++);
            if(log.isDebugEnabled())
                log.debug("Template '" + getTemplateName() + "' has " + templatePages + " page(s)");

            // Error if the template has no pages!!!
            if(templatePages==0) {
                log.error("Template Form " + getTemplateName() + " has no pages!");
                throw new EngineProcessingException("Template Form " + getTemplateName() + " has no pages!");
            }

            ArrayList pageData = new ArrayList();
            for(int templatePage = 0; templatePage < templatePages; templatePage++) {
                PageDetails page = new PageDetails();
                pageData.add(page);

                // Now get the names of all the fields on the form.
                if(log.isDebugEnabled())
                    log.debug("Read the field names from template page " + (templatePage+1) + " of " + templatePages);

                // Get total number of Blocks on page
                int blockCount = (int) m_pdf.get_pdi_value("vdp/blockcount", m_pdfTemplate, templatePage, 0);
                if(log.isDebugEnabled())
                    log.debug("   Field Count = " + blockCount);

                for (int i = 0; i < blockCount; i++) {
                    String name = m_pdf.get_pdi_parameter("vdp/Blocks["+i+"]/Name", m_pdfTemplate, templatePage, 0);
                    if(log.isDebugEnabled())
                        log.debug("    " + i + ") " + name);
                    page.fieldList.add(name);
                    /* @TODO We could do something here like
                          get_pdi_parameter("vdp/Blocks["+i+"]/SampleData",...
                       to get a value from PDFlib to use as sample data in the
                       new DomValue() in template mode...
                     */
                }
            }

            // Return the created template data
            return pageData;

        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }


    /** Any work to start off printing the document */
    protected void startDocument() throws FormPrintException {

    }


    /** Any work to start off printing a page of the document
     * getCurrentPage() will contain the page being printed, and
     * getCurrentTemplatePage() will contain the template page number to base this
     * new page on.
     */
    protected void startPage() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Creating Page " + getCurrentPage() + " from template page " + getCurrentTemplatePage());

        try {
            // Create the first page using the template page
            m_pdfPage = m_pdf.open_pdi_page(m_pdfTemplate, getCurrentTemplatePage(), "");
            if (m_pdfPage == -1) {
                log.error("Creating 1st Page in PDF - "  + m_pdf.get_errmsg());
                throw new EngineProcessingException("Error: " + m_pdf.get_errmsg());
            }

            // dummy page size
            m_pdf.begin_page_ext(20, 20, "");
            //m_pdf.begin_page(20, 20);

            // This will adjust the page size to the block container's size.
            m_pdf.fit_pdi_page(m_pdfPage, 0, 0, "adjustpage");

        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }


    /** This will fill in the page with data,
     * getCurrentPage()Data contains the details of the current page being printed
     */
    protected void fillPageFields() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Fill in data for Page " + getCurrentPage() + " based on template page " + getCurrentTemplatePage());

        try {
            for(Iterator i = getCurrentPageData().fieldList.iterator(); i.hasNext(); ) {
                String fieldname = (String) i.next();
                String sampleData=null;
                //@TODO - could store SampleData in PageDetail in a per field HashMap
                //sampleData=getCurrentPageData().sampleData.get(fieldName);
                
                String data = (new DomValue(fieldname, sampleData)).getValue();
                if(log.isDebugEnabled())
                    log.debug("  Set Page " + getCurrentPage() + " [id=" + m_pdfPage + "] " + fieldname + "=" + data);
                if(data!=null)
                    if( m_pdf.fill_textblock(m_pdfPage, fieldname, data, "embedding encoding=winansi") == -1) {
                        log.warn("Fill Warning on " + fieldname + " = " + m_pdf.get_errmsg());
                    }
            }
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }


    /** Any work to end printing the page */
    protected void endPage() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Closing Page " + getCurrentPage());
        try {
            // close the current PDF page
            m_pdf.end_page_ext("");
            m_pdf.close_pdi_page(m_pdfPage);
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }


    /** Any work to end printing the document */
    protected void endDocument() throws FormPrintException {
        if(log.isDebugEnabled())
            log.debug("Closing Document");
        try {
            // close the document
            m_pdf.end_document("");
            // close PDF Template
            m_pdf.close_pdi(m_pdfTemplate);
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
    }


    /**
     * Set the permissions on the generated PDF file. <B>NOTE: These must be
     * set prior to generating the PDF</B>
     * @param canPrint Allow generated PDF to be printed?
     * @param canCopy Allow generated PDF contents to be copied?
     * @param canModify Allow generated PDF contents to be modified?
     * @throws FormPrintException Throw if there was an error setting these properties.
     */
    public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException {
        if(isProcessed())
            throw new IllegalStateException("The form has already been processed");
        m_permissions = "";
        if(!canPrint)
            m_permissions += "noprint ";
        if(!canCopy)
            m_permissions += "nocopy ";
        if(!canModify)
            m_permissions += "nomodify ";
    }


    /** Return the generated PDF document. The generate() method MUST be called
     * prior to this else an exception will be thrown
     * @throws FormPrintException thrown if any pdf access error occurs
     * @return the generated pdf as a byte array
     */
    public byte[] getGeneratedForm() throws FormPrintException {
        if(!isProcessed())
            throw new IllegalStateException("The form not been processed yet");
        try {
            return m_pdf.get_buffer();
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
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
                fileout = File.createTempFile("form_", ".pdf");
            OutputStream bos = new FileOutputStream(fileout);
            bos.write(m_pdf.get_buffer());
            bos.flush();
            bos.close();
            if(log.isDebugEnabled())
                log.debug("PDFLib wrote out form " + fileout.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error Reading Uploaded File", e);
            return null;
        } catch (PDFlibException e) {
            log.error("PDFlibException: " + m_pdf.get_errmsg());
            throw new EngineProcessingException(m_pdf.get_errmsg());
        }
        return fileout;
    }


}
