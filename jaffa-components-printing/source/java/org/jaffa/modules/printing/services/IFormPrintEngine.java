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


import java.io.File;
import java.util.Properties;

import org.jaffa.modules.printing.services.exceptions.FormPrintException;

/** The prind engine is a wrapper for the PDFlib utility. This engine will take
 * a template PDF will block fields defined, and then merge that defintion
 * with the data supplied in a JavaBean. The generated bdf can then be
 * access, or written out to a file.
 * @author PaulE
 * @version 1.0
 * @since 2.0
 */
public interface IFormPrintEngine {

    /**
     * Initialize the engine. This can be called before the generate() method, but if
     * not, it is automatically called by generate().
     * <p>
     * Once the engine has been initialized, it will have loaded the templates, and
     * based on the data, calculated the number of pages it will be generating
     * <p>
     * At this point the getTotalPages() method can be used, and if need be the
     * setCurrentPageOffest() and setTotalPagesOverride() can be used if this is being
     * used to print multiple documents
     * @throws FormPrintException thrown if any pdf access error occurs
     */
    public void initialize() throws FormPrintException;

    /**
     * The core method in the engine is the generate() method, it must only be
     * called once, and must be called before you call any method that
     * accessed the generated PDF ( like writeForm() or getGeneratedForm() )
     * 
     * 
     * @throws FormPrintException This is thrown if there is any error in generating the PDF document.
     * The details will be in the message text
     */
    public void generate() throws FormPrintException;

    /**
     * Set the permissions on the generated PDF file. <B>NOTE: These must be
     * set prior to generating the PDF</B>
     * @param canPrint Allow generated PDF to be printed?
     * @param canCopy Allow generated PDF contents to be copied?
     * @param canModify Allow generated PDF contents to be modified?
     * @throws FormPrintException Throw if there was an error setting these properties.
     */
    public void setPermissions(boolean canPrint, boolean canCopy, boolean canModify) throws FormPrintException;

    /**
     * Return the generated PDF document. The generate() method MUST be called
     * prior to this else an exception will be thrown
     * @return the generated pdf as a byte array
     * @throws FormPrintException Thrown if there was any kind of output error, (or generation error if this
     * call has to also generate the form)
     */
    public byte[] getGeneratedForm() throws FormPrintException;

    /**
     * Write the PDF to a temp file, returns the file handle, or null if it failed!
     * The file will typically end up in the java temp folder
     * ( <CODE>System.getProperty("java.io.tempdir")</CODE> )
     * @return return the file handle to the file that was written containing
     * the generated PDF document. This will be null if there were any
     * write errors.
     * @throws FormPrintException Thrown if there was any kind of output error, (or generation error if this
     * call has to also generate the form)
     */
    public File writeForm() throws FormPrintException;

    /**
     * Write the PDF to a specified file
     * @return return the file handle to the file that was written containing
     * the generated PDF document. This will be null if there were any
     * write errors.
     * @param fileout file handle to use to write out the pdf
     * @throws FormPrintException Thrown if there was any kind of output error, (or generation error if this
     * call has to also generate the form)
     */
    public File writeForm(File fileout) throws FormPrintException;


    /**
     * Getter for property dataSource.
     * @return Value of property dataSource.
     */
    public Object getDataSource();

    /**
     * Setter for property dataSource. This is the root java bean that will be
     * introspected for values to fill in on this form.
     * @param dataSource New value of property dataSource.
     */
    public void setDataSource(Object dataSource);

    /**
     * Getter for property templateName.
     * @return Value of property templateName.
     */
    public String getTemplateName();

    /**
     * Setter for property templateName. This is the filename of the the PDF
     * document to use as a template. It is first looks for as if it is a fully
     * qualified name, if it is not found it then looks for it relative to the
     * 'templateSearchPath', if it has been set.
     * @param templateName New value of property templateName.
     */
    public void setTemplateName(String templateName);

    /**
     * Getter for property templateSearchPath.
     * @return Value of property templateSearchPath.
     */
    public String getTemplateSearchPath();

    /**
     * Setter for property templateSearchPath. Location to look for template PDF
     * documents
     * @param templateSearchPath New value of property templateSearchPath.
     */
    public void setTemplateSearchPath(String templateSearchPath);

    /**
     * Setter for general document properties. This allows properties specific to the
     * engin being used to be supplied, it is optional for an engine to use these
     * properties. In the case of PDF document generation, there are some standard
     * names for the PDF document properties
     * <ul>
     *   <li>title
     *   <li>subject
     *   <li>keywords
     *   <li>creator
     *   <li>author
     * </ul>
     * documents
     * @param documentProperties New value of property documentProperties.
     */
    public void setDocumentProperties(Properties documentProperties);

    /**
     * Get the number of pages this document will generate. Only valid after the
     * initialize() method has been called.
     * @return Value of property totalPages.
     */
    public int getTotalPages();

    /**
     * This sets the offset for the current page property. This can be used
     * if this document is to be merged with another document and the page numbers
     * in this document need to be offset by the number of pages already generated.
     * <p>
     * It impacts the DOM token 'CurrentPage#'
     * @param currentPageOffset set value of of property currentPageOffset.
     */
    public void setCurrentPageOffset(int currentPageOffset);

    /**
     * Getter for property currentPageOffset.
     * @return Value of property currentPageOffset.
     */
    public int getCurrentPageOffset();

    /**
     * This sets the override for the total pages property. This can be used
     * if this document is to be merged with another document and the overall page
     * totals need to be displayed in each document fragment.
     * <p>
     * It impacts the DOM token 'TotalPage#'
     * @param totalPagesOverride set value of of property totalPagesOverride.
     */
    public void setTotalPagesOverride(int totalPagesOverride);

    /**
     * Getter for property totalPagesOverride.
     * @return Value of property totalPagesOverride.
     */
    public int getTotalPagesOverride();


    /** Name of document property for title */
    public static final String DOCUMENT_PROPERTY_TITLE = "title";
    /** Name of document property for subject */
    public static final String DOCUMENT_PROPERTY_SUBJECT = "subject";
    /** Name of document property for keywords */
    public static final String DOCUMENT_PROPERTY_KEYWORDS = "keywords";
    /** Name of document property for creator */
    public static final String DOCUMENT_PROPERTY_CREATOR = "creator";
    /** Name of document property for author */
    public static final String DOCUMENT_PROPERTY_AUTHOR = "author";
    /** Make the engine work in test mode, ie no real data */
    public static final String DOCUMENT_PROPERTY_TEMPLATE_MODE = "templateMode";
}
