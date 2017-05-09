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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.Parser;
import org.jaffa.modules.printing.services.exceptions.FormPrintException;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.IRulesEngine;
import org.jaffa.rules.RulesEngineException;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.MessageHelper;
import org.jaffa.util.SplitString;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;

/** The prind engine is a wrapper for the PDFlib utility. This engine will take
 * a template PDF will block fields defined, and then merge that defintion
 * with the data supplied in a JavaBean. The generated bdf can then be
 * access, or written out to a file.
 * @author PaulE
 * @version 1.0
 * @since 2.0
 */
public abstract class FormPrintEngine implements IFormPrintEngine {

    /** Logger reference */
    public final static Logger log = Logger.getLogger(FormPrintEngine.class);
    /**
     * Default Font
     */
    public final static String DEFAULT_FONT = "Helvetica";
    // This holds for each array, how may rows of data there are
    private Map m_arrayDataSize = new HashMap();
    // This holds for each array, what is the index offset relative the to the page
    private Map m_arrayPageCounters = new HashMap();
    // List of each page, with it data in a 'PageDetails' class
    private List m_arrayNames = new ArrayList();
    // List of each page, with it data in a 'PageDetails' class
    private List m_pageData = new ArrayList();
    // has the pdf content been generated yet?
    private boolean m_processed = false;
    // has the pdf engine been initialized yet?
    private boolean m_initialized = false;
    // Source objet for all dats inserts
    private Object m_dataSource;
    // Name of the pdf template document
    private String m_templateFilename;
    // Search path to find the templates
    private String m_searchPath = "c:\\sandbox\\forms";
    // The number of pages in the template PDF being used.
    private int m_templatePages = 0;
    // Current page being generated
    private int m_currentPage = 1;
    // This is the offset of the current page, this is used if this document it to
    // be merged with another document and the page numbers in this document need
    // to be offset by the number of pages already printed.
    private int m_currentPageOffset = 0;
    // Reference to Data about the current page
    // Short cut to m_pageData.get(m_currentTemplatePage-1)
    private PageDetails m_currentPageData = null;
    // Template page being use to generate current page
    private int m_currentTemplatePage = 0;
    // Calculated total of pages based on repeating groups
    private int m_totalPages = 1;
    // If this document is part of a larger document, then this value can be set
    // so that it reports the total number of pages overall. If not set the number
    // of pages that this document will produce is used on the display
    private int m_totalPagesOverride = 0;
    // Store document properties
    private Properties m_documentProperties = null;
    // Store resolved DOM references
    private HashMap domRefs = new HashMap();
    // Reference to the rules engine, for introspection
    private IRulesEngine rulesEngine = null;
    // Is the engine running in template mode, where it should print out the
    // template with no real data in it.
    private boolean m_templateMode = false;

    /** Is the engine running in template mode, where it should print out the
     * template with no real data in it.
     */
    public boolean isTemplateMode() {
        return m_templateMode;
    }

    /**
     * Initialize the engine. This can be called before the generate() method, but if
     * not, it is automatically called by generate().
     * <p>
     * Once the engine has been initialized, it will have loaded the templates, and
     * based on the data, calculated the number of pages it will be generating
     * <p>
     * At this point the getTotalPages() method can be used, and if need be the
     * setCurrentPageOffset() and setTotalPagesOverride() can be used if this is being
     * used to print multiple documents
     * @throws FormPrintException thrown if any pdf access error occurs
     */
    public void initialize() throws FormPrintException {
        if (m_processed) {
            throw new IllegalStateException("The form has already been processed");
        }
        if (m_initialized) {
            return; // Already done
        }
        // initialize the document
        m_templatePages = 0;
        m_currentPage = 1;
        m_currentTemplatePage = 1;
        m_totalPages = 1;
        // See if template mode is set
        if (m_documentProperties != null) {
            Boolean flag = Parser.parseBoolean((String) m_documentProperties.remove(DOCUMENT_PROPERTY_TEMPLATE_MODE));
            m_templateMode = flag == null ? false : flag.booleanValue();
        }

        initEngine();

        // Get page template info
        m_pageData = parseTemplatePages();
        m_templatePages = m_pageData.size();

        // Get unique list of repeating groups
        List m_arrayNames = new ArrayList();
        for (Iterator it = m_pageData.iterator(); it.hasNext();) {
            PageDetails page = (PageDetails) it.next();
            page.calcArraySizes();
            if (page.arrayFormSize != null) {
                for (Iterator it2 = page.arrayFormSize.keySet().iterator(); it2.hasNext();) {
                    String k = (String) it2.next();
                    if (!m_arrayNames.contains(k)) {
                        m_arrayNames.add(k);
                    }
                }
            }
        }

        // Get total page count
        m_totalPages = calcTotalPages();

        m_initialized = true;
    }

    /**
     * The core method in the engine is the generate() method, it must only be
     * called once, and must be should be called before you call any method that
     * accessed the generated PDF ( like writePdf() or getGeneratedPdf() ).
     * All setters that are used to set up parameters for the engine must be called
     * before the generate method is invoked, or they won't have any effect on the
     * processing.
     * @throws FormPrintException thrown if any pdf access error occurs
     */
    public void generate() throws FormPrintException {
        if (m_processed) {
            throw new IllegalStateException("The form has already been processed");
        }
        if (!m_initialized) {
            initialize();
        }

        // start the document
        startDocument();

        // Loop through all the pages
        do {
            // Set the page data to reflect the template page being used
            m_currentPageData = (PageDetails) m_pageData.get(m_currentTemplatePage - 1);

            startPage();

            fillPageFields();

            endPage();

            // increment the page counter
            m_currentPage++;

            // change the arry offset counters
            for (Iterator i = m_arrayNames.iterator(); i.hasNext();) {
                String key = (String) i.next();
                if (m_currentPageData.arrayFormSize.containsKey(key)) {
                    int rows = ((Integer) m_currentPageData.arrayFormSize.get(key)).intValue();
                    int offset = 0;
                    if (m_arrayPageCounters.containsKey(key)) {
                        offset = ((Integer) m_arrayPageCounters.get(key)).intValue();
                    }
                    m_arrayPageCounters.put(key, new Integer(offset + rows));
                }
            }

            // increment the template page to use
            if (m_currentTemplatePage < m_templatePages) {
                m_currentTemplatePage++;
            }


        } while (m_currentPage <= m_totalPages);


        // close the document
        endDocument();

        m_processed = true;
    }

    /**
     * Perform any initialization activity for generating the document
     * @throws FormPrintException Thrown if there were initalization errors
     */
    protected abstract void initEngine() throws FormPrintException;

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
     * On return from this the engine will calculate the total no of template pages,
     * and the unique list of repearing group names
     * @throws FormPrintException Thrown if there is an error parsing the template pdf
     * @return This returns a List of PageDetails objects which contain data about
     * each page. It is assumed that the size of the list indicated the
     * number of pages in the template document being used
     */
    protected abstract List parseTemplatePages() throws FormPrintException;

    /**
     * Any work to start off printing the document
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected abstract void startDocument() throws FormPrintException;

    /**
     * Any work to start off printing a page of the document
     * m_currentPage will contain the page being printed, and
     * m_currentTemplatePage will contain the template page number to base this
     * new page on.
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected abstract void startPage() throws FormPrintException;

    /**
     * This will fill in the page with data,
     * m_currentPageData contains the details of the current page being printed
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected abstract void fillPageFields() throws FormPrintException;

    /**
     * Any work to end printing the page
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected abstract void endPage() throws FormPrintException;

    /**
     * Any work to end printing the document
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected abstract void endDocument() throws FormPrintException;

    /** This should calculate based on the data being displayed, how many
     * pages will be printed. This is needed for the 'totalPage#' variable
     * available in the merged output
     */
    private int calcTotalPages() {
        if (isTemplateMode()) {
            return m_templatePages;
        }
        // Initial size should be based on how many pages are in the template assuming that
        // the last page is an optional overflow page.
        int totalPages = Math.max(1, m_templatePages - 1);
        for (Iterator i = m_arrayNames.iterator(); i.hasNext();) {
            String key = (String) i.next();
            int pages = 0;
            try {
                Object array = PropertyUtils.getProperty(m_dataSource, translateFormPath(key));
                if (log.isDebugEnabled()) {
                    log.debug("Array for " + key + " is " + array);
                }
                int rows = 0;
                if (array != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("Array " + key + " is of type " + array.getClass().getName());
                    }
                    if (array.getClass().isArray()) {
                        rows = ((Object[]) array).length;
                        if (log.isDebugEnabled()) {
                            log.debug("Object[] " + array.getClass().getName() + " " + key + " has " + rows + " rows");
                        }
                    } else if (array instanceof List) {
                        rows = ((List) array).size();
                        if (log.isDebugEnabled()) {
                            log.debug("List Array " + key + " has " + rows + " rows");
                        }
                    }
                }

                m_arrayDataSize.put(key, new Integer(rows));

                // See how may rows are used up on normal pages,
                // then repeat on the overflow page until they are all gone....
                int remainingRows = rows;
                pages = 0;
                for (int templatePage = 1; templatePage < m_templatePages; templatePage++) {
                    PageDetails page = (PageDetails) m_pageData.get(templatePage - 1);
                    // Calculate remaining rows only if the array begins on the normal page.
                    if (page.arrayFormSize.get(key) != null) {
                        remainingRows -= ((Integer) page.arrayFormSize.get(key)).intValue();
                    }
                    pages++;
                }
                if (log.isDebugEnabled()) {
                    log.debug("After " + pages + " standard pages " + remainingRows + " rows are left");
                }
                // If there are still rows left to be consumed, calculate it based on the last page
                if (remainingRows > 0) {
                    PageDetails page = (PageDetails) m_pageData.get(m_templatePages - 1);
                    int pageRows = ((Integer) page.arrayFormSize.get(key)).intValue();
                    pages += ((remainingRows - 1) / pageRows) + 1;
                }
                if (log.isDebugEnabled()) {
                    log.debug("Printing " + rows + " row(s) of " + key + " will take " + pages + " page(s)");
                }

            } catch (Exception e) {
                log.info("Can't find size of array for '" + key + "', assume 0!", e);
            }
            if (pages > totalPages) {
                totalPages = pages;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Total Pages = " + totalPages);
        }

        return totalPages;
    }

    /**
     * This function first tries to resolve built-in references
     * It then tries to strip of any '|xxx' mutation functions, for recursive processing
     * Once it gets down to a field reference, it translates it (with indexes relevent
     * to the page) and then gets its value. As the process un-recurses, the mutation
     * functions are applied.
     * <p>
     * It has now been enhanced so that it caches the obejct references, as these are now
     * needed for creating PropertyRulesIntrospectors, which allow the rules engine to
     * apply formatting information to any property
     * <p>
     * @param ref bean reference to be resolved, including transformations
     * @return returns the value referenced with the appropriate transformations applied
     */
//    protected String evaluateFormPath(String ref) {
//
//        // Try and resolve 'simulated fields' that are not really part of the dataSource
//        String function = resolveFunction(ref);
//        if(function != null) return function;
//
//        //  if this does not resolve, assume it is part of the data source
//        int pos = ref.lastIndexOf("|");
//        if(pos >= 0) {
//            String transform = ref.substring(pos+1);
//            String newRef = ref.substring(0,pos);
//            return transform(evaluateFormPath(newRef), transform);
//        } else try {
//            if(isTemplateMode())
//                // in template mode we show the field name, not the real data
//                return ref;
//            String path = translateFormPath(ref);
//            if(log.isDebugEnabled())
//                log.debug("Look for path - " + path);
//            /*
//            Object out = BeanUtils.getProperty(m_dataSource,path);
//            if(out!=null)
//                return out.toString();
//             */
//            String text = getDomValue(path);
//            if(text!=null)
//                return text;
//        } catch (Exception e) {
//            log.warn("DOM Field Not Found : " + ref, e);
//        }
//        return "";
//    }
    /** the commons.beanutils package expect arrays to be accessed via array[i].xyz, where as the
     * form archiecture uses referenced like array.i.xzy. This will convert a form reference into
     * a beanUtils reference.
     */
    private String translateFormPath(String ref) {
        return translateFormPath(ref, m_arrayPageCounters);
    }

    /** Convert a PDF name style reference to a BeanUtils reference. Use the
     * map of array offsets to modify the index of any array access.
     * @param ref the orginal form reference
     * @param arrayOffset A map where the KEY is a String containing the name of the array,
     * and VALUE is an Integer, which and index to the given arry
     * should be incremented by.
     * @return the corrent reference to now use in the BeanUtils package
     * access the desired data element
     */
    public static String translateFormPath(String ref, Map arrayOffset) {
        String[] entries = StringHelper.parseString(ref, ".");
        StringBuffer newRef = new StringBuffer();
        for (int i = 0; i < entries.length; i++) {
            if (Pattern.matches("\\p{Digit}*", entries[i])) {
                int k = Integer.parseInt(entries[i]);
                if (arrayOffset != null) {
                    Integer j = (Integer) arrayOffset.get(newRef.toString());
                    // This is always null for the first page!!!
                    //if(j==null)
                    //    log.error("Can't find offset for array " + newRef.toString());
                    if (j != null) {
                        k += j.intValue();
                    }
                }
                newRef.append("[");
                newRef.append(k);
                newRef.append("]");
            } else {
                if (i > 0) {
                    newRef.append(".");
                }
                newRef.append(StringHelper.getLower1(entries[i]));
            }
        }
        return newRef.toString();
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
        return m_templateFilename;
    }

    /**
     * Get search path being used to locate the template
     * @return Search Path
     */
    public String getTemplateSearchPath() {
        return m_searchPath;
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
        m_templateFilename = templateName;
    }

    /**
     * Set Search Path
     * @param templateSearchPath Search Path
     */
    public void setTemplateSearchPath(String templateSearchPath) {
        m_searchPath = templateSearchPath;
    }

    /**
     * Has the document been generated internally yet?
     * @return True if docuemnt has been processed
     */
    public boolean isProcessed() {
        return m_processed;
    }

    /**
     * Getter for number of pages in the template
     * @return Number of pages in the template
     */
    public int getTemplatePages() {
        return m_templatePages;
    }

    /**
     * Getter for property currentPage.
     * @return Value of property currentPage.
     */
    public int getCurrentPage() {
        return m_currentPage;
    }

    /**
     * Getter for property totalPages.
     * @return Value of property totalPages.
     */
    public int getTotalPages() {
        return m_totalPages;
    }

    /**
     * Getter for property currentPageOffset.
     * @return Value of property currentPageOffset.
     */
    public int getCurrentPageOffset() {
        return m_currentPageOffset;
    }

    /**
     * Setter for property currentPageOffset.
     * @param currentPageOffset set value of of property currentPageOffset.
     */
    public void setCurrentPageOffset(int currentPageOffset) {
        m_currentPageOffset = currentPageOffset;
    }

    /**
     * Getter for property totalPagesOverride.
     * @return Value of property totalPagesOverride.
     */
    public int getTotalPagesOverride() {
        return m_totalPagesOverride;
    }

    /**
     * Setter for property totalPagesOverride.
     * @param totalPagesOverride set value of of property totalPagesOverride.
     */
    public void setTotalPagesOverride(int totalPagesOverride) {
        m_totalPagesOverride = totalPagesOverride;
    }

    /**
     * Getter for property currentPageData.
     * @return Value of property currentPageData.
     */
    public PageDetails getCurrentPageData() {
        return m_currentPageData;
    }

    /**
     * Getter for property currentTemplatePage.
     * @return Value of property currentTemplatePage.
     */
    public int getCurrentTemplatePage() {
        return m_currentTemplatePage;
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
        if (m_documentProperties == null) {
            return null;
        }
        return m_documentProperties.getProperty(key);
    }

    /** Get the specified property for the document */
    protected String getDocumentProperty(String key, String defaultValue) {
        if (m_documentProperties == null) {
            return defaultValue;
        }
        return m_documentProperties.getProperty(key, defaultValue);
    }

    /** Inner class to store details about each template page */
    class PageDetails {

        /** This holds for each array, how many rows are there on this form for any repeating group */
        Map arrayFormSize = new HashMap();
        /** List of all the fields extracted from the form definition */
        List fieldList = new ArrayList();

        // Calculate the array sizes based on the fieldList
        private void calcArraySizes() {

            for (Iterator i = fieldList.iterator(); i.hasNext();) {
                String field = (String) i.next();
                // Change the pattern to allow for fields such as "lines.0" or "item.0.part"
                //Pattern p = Pattern.compile("(\\p{Graph}+?)\\.(\\p{Digit}+)\\..*");
                Pattern p = Pattern.compile("(\\p{Graph}+?)\\.(\\p{Digit}+)(\\..*)?");
                Matcher m = p.matcher(field);
                if (m.matches()) {
                    String prefix = m.group(1);
                    int idx = Integer.parseInt(m.group(2)) + 1;
                    Integer max = (Integer) arrayFormSize.get(prefix);
                    if (max == null || max.intValue() < idx) {
                        arrayFormSize.put(prefix, new Integer(idx));
                    }
                    if (!m_arrayNames.contains(prefix)) {
                        m_arrayNames.add(prefix);
                    }
                }
            }

            if (log.isDebugEnabled()) {
                for (Iterator i = arrayFormSize.keySet().iterator(); i.hasNext();) {
                    String key = (String) i.next();
                    Integer n = (Integer) arrayFormSize.get(key);
                    if (log.isDebugEnabled()) {
                        log.debug("    Group " + key + " has " + n.intValue() + " rows");
                    }
                }
            }
        }
    } // End Inner Class - PageDetails

    /** New Inner class to represent a DOM value */
    class DomValue {

        private Object rootObject = null;
        private String propertyName = null;
        private Object propertyValue = null;
        private String filters = null;
        private String formattedValue = null;

        /** Decode a DomValue and provide access to the RAW and formatted value.
         * Use the domRef cache, the BeanUtils helper and the PropertyRuleIntrospector to return a
         * text representation of a field
         */
        DomValue(String path, String dataOverride) {
            String rootName = null;
            if (path == null) {
                return;
            }

            // Split off any transformations
            int pos = path.indexOf("|");
            if (pos >= 0) {
                filters = path.substring(pos + 1);
                path = path.substring(0, pos);
            }
            log.debug("<cinit> DomValue(" + path + ") : filter=" + filters + ")");

            // In template mode we show sample data or the field name, not the real data
            // This allow templates to be generated without needing a DOM.
            if (isTemplateMode()) {
                if (dataOverride != null) {
                    propertyValue = dataOverride;
                } else {
                    propertyValue = path;
                }
            } else {
                // Try and resolve 'simulated fields' that are not really part of the dataSource
                String function = resolveInternalRef(path);
                if (function != null) {
                    propertyName = path;
                    propertyValue = function;

                    // In template mode we show the field name, not the real data
                } else if (isTemplateMode()) {
                    propertyValue = path;

                    // Get the real value of the field
                } else {
                    // Convert any .0. refs to [0] for BeanUtils package
                    path = translateFormPath(path);

                    // Try and find the root object for the bean that has the specified field
                    SplitString split2 = new SplitString(path, ".", false);
                    if (split2.getSuffix() == null) {
                        // The DOM in this case is the rootObject
                        rootName = "this";
                        rootObject = m_dataSource;
                        propertyName = path;
                        log.debug("Found DOM root object " + rootName + " of type " + (rootObject == null ? "null" : rootObject.getClass().getName()));
                    } else {
                        rootName = split2.getPrefix();
                        propertyName = split2.getSuffix();
                        // Lookup root object in cache, as this saves using indirection has there is
                        // typically large amount of repeated referenced to the same root object per page.
                        if (domRefs.containsKey(rootName)) {
                            rootObject = domRefs.get(rootName);
                            log.debug("Found DOM cached object " + rootName + " of type " + (rootObject == null ? "null" : rootObject.getClass().getName()));
                        } else {
                            // Object not in cache, must get it using BeanUtils
                            try {
                                rootObject = PropertyUtils.getProperty(m_dataSource, rootName);
                                log.debug("Source DOM object of type " + (m_dataSource == null ? "null" : m_dataSource.getClass().getName()));
                                log.debug("Found DOM new object " + rootName + " of type " + (rootObject == null ? "null" : rootObject.getClass().getName()));
                                domRefs.put(rootName, rootObject);
                            } catch (Exception e) {
                                if (log.isDebugEnabled()) {
                                    log.debug("DOM Root Object Not Found : " + rootName + " - " + e.getMessage());
                                }
                            }
                        }
                    }

                    // Get the property value if there is a source object
                    if (rootObject != null) {
                        try {
                            propertyValue = PropertyUtils.getProperty(rootObject, propertyName);
                        } catch (Exception e) {
                            if (log.isDebugEnabled()) {
                                log.debug("DOM Bean Property Not Found : " + rootName + "->" + propertyName + " - " + e.getMessage());
                            }
                        }
                    }
                }
            }
        }

        /** Return the RAW value of the DOM  field */
        Object getObject() {
            return propertyValue;
        }

        /** Return the formatted String value of the DOM field */
        String getFormattedValue() {
            if (formattedValue == null) {
                String layout = null;
                // Now get the layout for this field from the Rules Engine
                if (rootObject != null) {
                    try {
                        rulesEngine = RulesEngineFactory.getRulesEngine();
                    } catch (RulesEngineException e) {
                        log.error("Can't Start Rules Engine");
                    }
                    if (rulesEngine != null) {
                        IPropertyRuleIntrospector rule = rulesEngine.getPropertyRuleIntrospector(rootObject.getClass().getName(), propertyName);
                        layout = rule.getLayout();
                    }
                }

                Object value = propertyValue;

                //Convert Boolean to default values "T" or "F".
                if (value instanceof Boolean) {
                    if (value.equals(Boolean.TRUE)) {
                        value = "T";
                    } else {
                        value = "F";
                    }
                }

                // Convert all Numbers to Longs for the formatter
                if (value instanceof Number && !(value instanceof Long)) {
                    //log.debug("Got Number - " + (Number)value);
                    value = new Long(((Number) value).longValue());
                }

                // Use the formatter to get a String representation of the field
                formattedValue = Formatter.format(value, layout);
                if (log.isDebugEnabled()) {
                    log.debug((rootObject == null ? "" : rootObject.getClass().getName() + ".") + propertyName + " = " + value + " [" + layout + "] = " + formattedValue);
                }
            }
            return formattedValue;
        }

        /** This takes the formatted value, and applies any translations for the field
         */
        String getValue() {
            String value = getFormattedValue();
            if (value != null) {
                if (filters == null) {
                    return value;
                }
                for (String filter : filters.split("\\|")) {
                    if (filter != null && filter.length() > 0) {
                        value = processFilter(filter, value);
                    }
                }
            }
            if (log.isDebugEnabled()) {
                log.debug((rootObject == null ? "" : rootObject.getClass().getName() + ".") + propertyName + " = " + getFormattedValue() + " |" + filters + " = " + value);
            }
            return value;
        }

        /** Read an image from the DOM field based on its data type */
        Image getDomImage() throws IOException, MalformedURLException {
            if (propertyValue == null) {
                return null;
            }
            if (propertyValue instanceof String) {
                return ImageIO.read(URLHelper.newExtendedURL((String) propertyValue));
            } else if (propertyValue instanceof File) {
                return ImageIO.read((File) propertyValue);
            } else if (propertyValue instanceof URL) {
                return ImageIO.read((URL) propertyValue);
            } else if (propertyValue instanceof Image) {
                return (Image) propertyValue;
            } else {
                String err = "Error, No Image Handler for Class " + propertyValue.getClass().getName();
                log.error(err);
                return null;
            }

        }

        /** See if the DOM reference it to an internal keyword, if so
         * return the value. If NULL is returned, assume this was not an
         * internal field reference
         */
        private String resolveInternalRef(String ref) {
            if ("logo".equals(ref)) {
                String logo = null;
                if (m_documentProperties != null) {
                    logo = (String) m_documentProperties.get("logo");
                }
                if (logo == null) {
                    logo = (String) ContextManagerFactory.instance().getProperty("jaffa.printing.defaultLogo");
                }
                return logo;
            } else if ("currentPage#".equals(ref) || "CurrentPage#".equals(ref)) {
                return "" + (m_currentPage + m_currentPageOffset);
            } else if ("totalPage#".equals(ref) || "TotalPage#".equals(ref)) {
                return "" + (m_totalPagesOverride > 0 ? m_totalPagesOverride : m_totalPages);
            } else if ("currentDocumentPage#".equals(ref) || "CurrentDocumentPage#".equals(ref)) {
                return "" + (m_currentPage);
            } else if ("totalDocumentPage#".equals(ref) || "TotalDocumentPage#".equals(ref)) {
                return "" + (m_totalPages);
            } else {
                Pattern p = Pattern.compile("(\\p{Graph}+?)\\.(\\p{Digit}+)\\.[Rr]ow#");
                Matcher m = p.matcher(ref);
                if (m.matches()) {
                    String key = m.group(1);
                    int idx = Integer.parseInt(m.group(2));
                    int offset = 0;
                    if (m_arrayPageCounters.containsKey(key)) {
                        offset = ((Integer) m_arrayPageCounters.get(key)).intValue();
                    }
                    idx += offset + 1;
                    // make sure that this is not greater that the row size for this group
                    Integer count = (Integer) m_arrayDataSize.get(key);
                    if (count == null || count.intValue() >= idx) {
                        if (log.isDebugEnabled()) {
                            log.debug("Ref " + ref + " on page " + m_currentPage + " = " + idx);
                        }
                        return "" + idx;
                    }
                }
            }
            return null;
        }

        /** Mutate a string based on the selected filter.
         * @param filter Type of mutation (not case sensitive). Can be one of
         * <ul>
         * <li>barcode - Adds a * to the start and end of the data (used for 3of9 fonts)
         * <li>T=a,F=b - Converts Boolean True/False values to the values specified by "T=" and "F=". 
         * <li>upper - to uppercase
         * <li>upper1 - first character to uppercase
         * <li>lower - to lower case
         * <li>lower1 - first character to lower case
         * <li>[<start pos>:<end pos>] - substring from start to end. end is optional
         * </ul>
         * @param value String to be mutated
         * @return the mutated string
         */
        private String processFilter(String filter, String value) {
            String val = value == null ? "" : value;
            if ("BARCODE".equalsIgnoreCase(filter)) {
                if (val == "") {
                    return null;
                } else {
                    return "*" + val + "*";
                }
            } else if ("UPPER".equalsIgnoreCase(filter)) {
                return StringHelper.getUpper(val);
            } else if ("UPPER1".equalsIgnoreCase(filter)) {
                return StringHelper.getUpper1(val);
            } else if ("LOWER".equalsIgnoreCase(filter)) {
                return StringHelper.getLower(val);
            } else if ("LOWER1".equalsIgnoreCase(filter)) {
                return StringHelper.getLower1(val);
            } else if (filter.contains("T=") || filter.contains("t=") || filter.contains("F=") || filter.contains("f=")) {
                return booleanFilter(filter, val);
            } else {
                Pattern p = Pattern.compile("[\\[\\{]([0-9]+):?([0-9]*)[\\]\\}]");
                Matcher m = p.matcher(filter);
                if (m.matches()) {
                    int start = Integer.parseInt(m.group(1));
                    if (start < 1) {
                        start = 1;
                    }
                    if (start > val.length()) {
                        return "";
                    }
                    if (m.group(2) == null || m.group(2).length() == 0) {
                        return val.substring(start - 1);
                    } else {
                        int end = Integer.parseInt(m.group(2));
                        if (end > val.length()) {
                            end = val.length();
                        }
                        return val.substring(start - 1, end);
                    }
                } else {
                    return val;
                }
            }
        }

        /**
         * @param filter is the Boolean filter.  The format is "T=<value for true>;F=<value for false>".
         * The full field and filter would appear as "<fieldname>|T=<value for true>;F=<value for false>".
         * Filter example: "flightSafetyHazard|T=Y;F=N".
         * Both "T=x" and "F=x" do not have to be specified in the filter.  When one is omitted, the value returned
         * is an empty String for that boolean condition.
         * The filter can be expressed in one of the following formats:
         *     "field|T=x;F=z", "field|T=;F=z", "field|T=x;F=", "field|T=x", "field|F=z"
         * @param value is the Boolean object converted to a String of "T" or "F".
         * This method assumes Boolean values are converted to a String of "T" or "F" as done in getFormattedValue().
         * If value is not a "T" or "F" then this method returns the value as is.
         * @return the value specified by the filter.  If value is not "T" or "F" then return the value as is.
         */
        private String booleanFilter(String filter, String value) {
            String val = "";
            String upperVal;
            String lowerVal;
            int start;
            int end;

            if (value.equals("T")) {
                upperVal = "T=";
                lowerVal = "t=";
            } else if (value.equals("F")) {
                upperVal = "F=";
                lowerVal = "f=";
            } else {
                // Not a Boolean field
                return value;
            }

            start = filter.indexOf(upperVal.toString());
            if (start < 0) {
                start = filter.indexOf(lowerVal.toString());
            }
            if (start < 0) {
                val = "";
            } else {
                start = start + 1;
                end = filter.indexOf(";", start);
                if (end < 0) {
                    if (filter.length() > start) {
                        val = filter.substring(start + 1);
                    } else {
                        val = "";
                    }
                } else {
                    val = filter.substring(start + 1, end);
                }
            }
            return val;
        }
    } // End Inner Class - DomValue
}
