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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode;
import com.lowagie.text.pdf.BarcodePDF417;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jaffa.modules.printing.services.FormPrintEngine.PageDetails;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.modules.printing.services.exceptions.FormPrintException;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.StringHelper;


/** This is the iText implementation of the form printing engine, for those
 * who want an free open source engine. It uses an additional CSV data file to
 * get all the fields and position information to display, as opposed to the
 * PDFlib implementation which embeds the meta data into the PDF template.
 *<p>
 * The following attributes are used from the CSV file
 * <li><b>Page</b> - What page in the template this field is on, first page is 1
 * <li><b>Field</b> - Name of the field in the template, must be unique per page
 * This can include filters(|) like UPPER,UPPER1,LOWER,LOWER1,[n],[n:m].
 * If the same field is needed twice (like for a barcode and plain text) an empty filter
 * can be used to make the field name unique, i.e. orderNo vs. orderNo| vs. orderNo||
 * <li><b>x1',y1',x2',y2'</b> - The bounding box on the page based on 1/72" scale.
 * <b>x</b> is measured from the left edge, and <b>y</b> is mesured from the bottom
 * <li><b>Font</b> - Name of the font, build in ones are Courier, Helvetica, Times Roman,
 * Symbol and ZapfDingBats&reg;. All others are loaded from the local OS font folders
 * and may vary per deployment. If a barcode display is required then any of the supported
 * Barcode styles can be specified here (ie Barcode39, Barcode128,BarcodeEAN, BarcodeEAN128,
 * BarcodeInter25, BarcodePDF417, BarcodePostnet). These barcodes translate to the Barcode
 * classes in the com.lowagie.text.pdf package.
 * <li><b>Size</b> - Font size in points, i.e 12 means 12pt
 * <li><b>Align</b> - Alignment of text in the box. Left, Right or Center
 * <li><b>Multiline</b> - Not Used
 * <li><b>Fit Method</b> - If empty of set to "clip" it will clip the text to the
 * bounding box, any other value will not clip the contents
 * <li><b>Rotate</b> - Supported for images and watermarks.  Rotates  to the specified angle in degrees. 
 * <li><b>Style</b> - If this is a normal font, an ';' separated list of styles can
 * be specified (i.e. 'Bold;Italics;Underline;StrikeThru'). In the case of Barcodes,
 * the values here will be reflected onto the related barcode classes (see the Font),
 * for example on the Barcode39 you could use 'barHeight=24;n=3;x=1.0'
 * <li><b>Color</b> - This must be a known color name (see ColorMappings.properties) or
 * a RGB color value in the format '#FFFFFF'
 * <li><b>Background</b> - Not Supported
 * <li><b>Sample Data</b> - Will display this value in the field instead of its name in template mode
 * <li><b>Opacity</b> - Sets the opacity level for watermarks.  The smaller the value, the more transparent. 
 * Defaults to 1 which has no transparency.
 * </ul>
 * @author PaulE
 * @version 1.0
 * @since 2.0
 */
public class FormPrintEngineIText extends FormPrintEngine {

    /** Logger reference */
    public final static Logger log = Logger.getLogger(FormPrintEngineIText.class);


    private static List HEADINGS = Arrays.asList(
            new String[] {"Page","Field","x1'","y1'","x2'","y2'",
            "Font","Size","Align","Multiline","Fit Method",
            "Rotate","Style","Color","Background","Sample Data","Opacity"} );
    private static final int COLUMN_PAGE = 0;
    private static final int COLUMN_FIELD = 1;
    private static final int COLUMN_X1 = 2;
    private static final int COLUMN_Y1 = 3;
    private static final int COLUMN_X2 = 4;
    private static final int COLUMN_Y2 = 5;
    private static final int COLUMN_FONT = 6;
    private static final int COLUMN_SIZE = 7;
    private static final int COLUMN_ALIGN = 8;
    private static final int COLUMN_MULTILINE = 9;
    private static final int COLUMN_FIT_METHOD = 10;
    private static final int COLUMN_ROTATE = 11;
    private static final int COLUMN_STYLE = 12;
    private static final int COLUMN_COLOR = 13;
    private static final int COLUMN_BACKGROUND = 14;
    private static final int COLUMN_SAMPLE_DATA = 15;
    private static final int COLUMN_OPACITY = 16;

    private static final List FIT_METHODS = Arrays.asList("noclip","clip","fill","scale");
    /** For text an images, this will not truncate things that display outside the bounding box */
    private static final int FIT_METHOD_NOCLIP = 0;
    /** For text an images, this will truncate things that display outside the bounding box */
    private static final int FIT_METHOD_CLIP = 1;
    /** For images, this will stretch/shrink both the X/Y to fit the bounding box */
    private static final int FIT_METHOD_FILL = 2;
    /** For images, this will stretch/shrink preserving the aspect ratio to fit the bounding box */
    private static final int FIT_METHOD_SCALE = 3;

    private ByteArrayOutputStream m_output = null;
    private PdfReader m_templateReader;
    private Document m_generatedDoc;
    private PdfWriter m_writer;
    private final String  defaultWatermarkColor = "gray";

    // Load any additional fonts that may be needed
    static {
        FontFactory.registerDirectories();
    }

    /**
     * Perform any initialization activity for generating the document
     * @throws FormPrintException Thrown if there were initialization errors
     */
    protected void initEngine() throws FormPrintException {
        log.debug("initEngine:");
        if (getTemplateName() != null){
            if( !(new File(getTemplateName())).exists()) {
                FormPrintException e = new EngineProcessingException("Form Template Not Found",new FileNotFoundException(getTemplateName()));
                log.error("Form Template Not Found." + getTemplateName(),e);
                throw e;
            }
        }else{
            FormPrintException e = new EngineProcessingException("Form Template Name Missing.");
            log.error(" Form Template Name Missing. ");
            throw e;
        }
    }

    /**
     * Process the template and do the following
     * <ol>
     * <li>Throw errors if there is something wrong with the template (in getTemplateName())
     * <li>Determine to the total number of pages in the template
     *    (The template should have at least one page)
     * <li>Populate an array where each template page should have one entry in it.
     *    Each entry will a PageDetails object, containing a list of fields
     *    on the page, and a list of repeating entities.
     * </ol>
     * On return from this the engine will calculate the total no of template pages,
     * and the unique list of reappearing group names
     * @throws FormPrintException Thrown if there is an error parsing the template pdf
     * @return This returns a List of PageDetails objects which contain data about
     * each page. It is assumed that the size of the list indicated the
     * number of pages in the template document being used
     */
    protected List parseTemplatePages() throws FormPrintException {
        log.debug("parseTemplatePages:");

        try {
            m_templateReader = new PdfReader(getTemplateName());
        } catch (IOException e) {
            log.error("Error opening template - " + e.getMessage() ,e);
            throw new EngineProcessingException("Error opening template - " + e.getMessage());
        }
        // we retrieve the total number of pages
        int templatePages = m_templateReader.getNumberOfPages();
        if(templatePages<1)
            throw new EngineProcessingException("Template Document has no pages!");
        if(log.isDebugEnabled())
            log.debug("Template '" + getTemplateName() + "' has " + templatePages + " page(s)");

        // Create Empty array of page data
        ArrayList pageData = new ArrayList();
        for(int templatePage = 0; templatePage < templatePages; templatePage++) {
            PageDetailsExtended page = new PageDetailsExtended();
            pageData.add(page);
        }

        // Look for a related data file...
        File data = new File(getTemplateName()+".csv");
        if(!data.exists()) {
            log.warn("CSV Parse Error: No data file found for field layout");
        } else {
            // Read the file, and populate PageDetailsExtended
            try {
                BufferedReader in = new BufferedReader( new FileReader(data) );
                StringBuffer buf1 = new StringBuffer();
                String line = null;
                int[] colOrder = null;
                int row = 0;
                while ( (line = in.readLine()) != null) {
                    row++;

                    if(line.startsWith("#") || line.length()==0)
                        // ignore commented out or blank lines.
                        continue;

                    String[] cols = line.split(",");
                    if(row==1) {
                        // Must be first line, analyse column names....
                        colOrder = new int[cols.length];
                        for(int i=0; i< cols.length; i++) {
                            String col = cols[i];
                            colOrder[i] = HEADINGS.indexOf(col);
                            if (colOrder[i]==-1) {
                                log.error("CSV Parse Error: Unknown column heading '" + col + "' in column " + (i+1) + ". Can't Process File");
                                throw new EngineProcessingException("Can't read layout data file, column headings incorrect");
                            }
                        }
                    } else {
                        // This is a row of data, process it
                        int pageNo = 0;
                        String field = null;
                        FormPrintEngineIText.FieldProperties props = new FormPrintEngineIText.FieldProperties();
                        for(int i=0; i< cols.length; i++) {
                            try {
                                if(cols[i]!=null && cols[i].trim().length()>0) {
                                    String value = cols[i].trim();
                                    switch(colOrder[i]) {
                                        case COLUMN_PAGE:
                                            pageNo = Integer.parseInt(value);
                                            break;
                                        case COLUMN_FIELD:
                                            field = value;
                                            break;
                                        case COLUMN_X1:
                                            props.x1 = Float.parseFloat(value);
                                            break;
                                        case COLUMN_Y1:
                                            props.y1 = Float.parseFloat(value);
                                            break;
                                        case COLUMN_X2:
                                            props.x2 = Float.parseFloat(value);
                                            break;
                                        case COLUMN_Y2:
                                            props.y2 = Float.parseFloat(value);
                                            break;
                                        case COLUMN_FONT:
                                            props.fontFace = value;
                                            break;
                                        case COLUMN_SIZE:
                                            props.fontSize = Float.parseFloat(value);
                                            break;
                                        case COLUMN_ALIGN:
                                            if("center".equalsIgnoreCase(value)) {
                                                props.align = Element.ALIGN_CENTER;
                                            } else if("right".equalsIgnoreCase(value)) {
                                                props.align = Element.ALIGN_RIGHT;
                                            }
                                            break;
                                        case COLUMN_MULTILINE:
                                            props.multiLine = Boolean.valueOf(value).booleanValue();
                                            break;
                                        case COLUMN_FIT_METHOD:
                                            if(FIT_METHODS.contains(value.toLowerCase()))
                                                props.fitMethod = FIT_METHODS.indexOf(value.toLowerCase());
                                            break;
                                        case COLUMN_ROTATE:
                                            props.rotate = Float.parseFloat(value);
                                            break;
                                        case COLUMN_STYLE:
                                            props.style = value;
                                            break;
                                        case COLUMN_COLOR:
                                            props.color = value;
                                            break;
                                        case COLUMN_BACKGROUND:
                                            props.background = value;
                                            break;
                                        case COLUMN_SAMPLE_DATA:
                                            props.sampleData = URLDecoder.decode(value,"UTF-8");
                                            break;
                                        case COLUMN_OPACITY:
                                            props.opacity = Float.parseFloat(value);
                                            break;

                                    } // switch
                                }
                            } catch (NumberFormatException ne) {
                                String err = "CSV Parse Error: Invalid Numeric Value in Form Layout Data. Row=" + row + ", Column=" + i + ", Value='"+cols[i]+"'";
                                log.error(err);
                                throw new EngineProcessingException(err);
                            }
                        } // for
                        if(pageNo < 1 || pageNo > templatePages) {
                            String err = "CSV Parse Error: Invalid Page Number " + pageNo + " on row " + row;
                            log.error(err);
                            throw new EngineProcessingException(err);
                        }
                        if(field==null) {
                            String err = "CSV Parse Error: Invalid/Missing Field Name on row " + row;
                            log.error(err);
                            throw new EngineProcessingException(err);
                        }
                        PageDetailsExtended page = (PageDetailsExtended) pageData.get(pageNo-1);
                        page.fieldList.add(field);
                        page.fieldProperties.put(field, props);
                    }
                } // while loop on each input line
            } catch (IOException e) {
                log.warn("CSV Parse Error: Failed to read layout data file", e);
            }
        }

        return pageData;
    }


    /**
     * Any work to start off printing the document
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected void startDocument() throws FormPrintException {
        log.debug("startDocument:");

        Rectangle r = m_templateReader.getPageSize(getCurrentTemplatePage());
        log.debug( "Page Size      : t="+r.getTop()+",l="+r.getLeft()+",b="+r.getBottom()+",r="+r.getRight()+", rot="+r.getRotation() );
        r = m_templateReader.getPageSizeWithRotation(getCurrentTemplatePage());
        log.debug( "Page Size w/Rot: t="+r.getTop()+",l="+r.getLeft()+",b="+r.getBottom()+",r="+r.getRight()+", rot="+r.getRotation() );

        m_generatedDoc = new Document(m_templateReader.getPageSizeWithRotation(getCurrentTemplatePage()));
        //m_generatedDoc = new Document(m_templateReader.getPageSize(getCurrentTemplatePage()));
        m_output = new ByteArrayOutputStream();
        try {
            m_writer = PdfWriter.getInstance(m_generatedDoc, m_output);
        } catch (DocumentException e) {
            log.error("Error Creating Writer - " + e.getMessage() ,e);
            throw new EngineProcessingException("Error Creating Writer - " + e.getMessage());
        }

        if(getDocumentProperties()!=null) {
            Properties dp = (Properties) getDocumentProperties().clone();
            if(dp.getProperty(DOCUMENT_PROPERTY_TITLE)!=null) {
                m_generatedDoc.addTitle(dp.getProperty(DOCUMENT_PROPERTY_TITLE));
                dp.remove(DOCUMENT_PROPERTY_TITLE);
            }
            if(dp.getProperty(DOCUMENT_PROPERTY_SUBJECT)!=null) {
                m_generatedDoc.addSubject(dp.getProperty(DOCUMENT_PROPERTY_SUBJECT));
                dp.remove(DOCUMENT_PROPERTY_SUBJECT);
            }
            if(dp.getProperty(DOCUMENT_PROPERTY_KEYWORDS)!=null) {
                m_generatedDoc.addKeywords(dp.getProperty(DOCUMENT_PROPERTY_KEYWORDS));
                dp.remove(DOCUMENT_PROPERTY_KEYWORDS);
            }
            if(dp.getProperty(DOCUMENT_PROPERTY_CREATOR)!=null) {
                m_generatedDoc.addCreator(dp.getProperty(DOCUMENT_PROPERTY_CREATOR, "Jaffa Print Engine"));
                dp.remove(DOCUMENT_PROPERTY_CREATOR);
            }
            if(dp.getProperty(DOCUMENT_PROPERTY_AUTHOR)!=null) {
                m_generatedDoc.addAuthor(dp.getProperty(DOCUMENT_PROPERTY_AUTHOR));
                dp.remove(DOCUMENT_PROPERTY_AUTHOR);
            }
            // loop through other properties and set them as header parameters
            for(Enumeration en = dp.elements(); en.hasMoreElements();) {
                Map.Entry e = (Map.Entry) en.nextElement();
                if(e.getKey()!=null && e.getValue()!=null)
                    m_generatedDoc.addHeader(e.getKey().toString(),e.getValue().toString());
            }
        }
        m_generatedDoc.addCreationDate();

        m_generatedDoc.open();

    }

    /**
     * Any work to start off printing a page of the document
     * m_currentPage will contain the page being printed, and
     * m_currentTemplatePage will contain the template page number to base this
     * new page on.
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected void startPage() throws FormPrintException {
        log.debug("startPage: Page=" + getCurrentPage());

        Rectangle r = m_templateReader.getPageSize(getCurrentTemplatePage());
        log.debug( "Page Size      : t="+r.getTop()+",l="+r.getLeft()+",b="+r.getBottom()+",r="+r.getRight()+", rot="+r.getRotation() );
        r = m_templateReader.getPageSizeWithRotation(getCurrentTemplatePage());
        log.debug( "Page Size w/Rot: t="+r.getTop()+",l="+r.getLeft()+",b="+r.getBottom()+",r="+r.getRight()+", rot="+r.getRotation() );

        // Get rotation quadrent 0..3
        int q = (m_templateReader.getPageSizeWithRotation(getCurrentTemplatePage()).getRotation() % 360)/90;
        float tX = (q==2?r.getTop():0)+(q==3?r.getRight():0);
        float tY = (q==1?r.getTop():0)+(q==2?r.getRight():0);
        float sX=1f, sY=1f;
        double angle = -r.getRotation()*(Math.PI/180f);
        double transformA = sX * Math.cos(angle);
        double transformB = sY * Math.sin(angle);
        double transformC = -sX * Math.sin(angle);
        double transformD = sY * Math.cos(angle);
        double transformE = tX;
        double transformF = tY;


        m_generatedDoc.setPageSize(m_templateReader.getPageSizeWithRotation(getCurrentTemplatePage()) );
        //m_generatedDoc.setPageSize(m_templateReader.getPageSize(getCurrentTemplatePage()) );
        /**
         * try {
         * m_generatedDoc.newPage();
         * } catch (DocumentException e) {
         * log.error("Error Creating New Page - " + e.getMessage() ,e);
         * throw new EngineProcessingException("Error Creating New Page - " + e.getMessage());
         * }
         **/
        m_generatedDoc.newPage();

        PdfImportedPage page = m_writer.getImportedPage(m_templateReader, getCurrentTemplatePage() );
        PdfContentByte cb = m_writer.getDirectContent();
        //cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
        cb.addTemplate(page, (float)transformA, (float)transformB, (float)transformC,
                             (float)transformD, (float)transformE, (float)transformF);
        log.debug("Matrix = [A=" + transformA +", B=" + transformB +", C=" + transformC +
                          ", D=" + transformD +", E=" + transformE +", F=" + transformF +" ]");
    }

    /**
     * This will fill in the page with data,
     * m_currentPageData contains the details of the current page being printed
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected void fillPageFields() throws FormPrintException {
        log.debug("fillPageFields: Page=" + getCurrentPage());

        try {
            PdfContentByte cb = m_writer.getDirectContent();
            PageDetailsExtended page = (PageDetailsExtended) getCurrentPageData();

//                // Test code to throw a barcode on the page...
//                Barcode39 code39 = new Barcode39();
//                code39.setCode("CODE39-1234567890");
//                code39.setStartStopText(false);
//                code39.setSize(0);
//                Image image39 = code39.createImageWithBarcode(cb, null, null);
//                com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(2);
//                table.setWidthPercentage(100);
//                table.getDefaultCell().setBorder(com.lowagie.text.Rectangle.NO_BORDER);
//                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//                table.getDefaultCell().setFixedHeight(70);
//                table.addCell("CODE 39");
//                table.addCell(new Phrase(new Chunk(image39, 0, 0)));
//                m_generatedDoc.add(table);
//                //--------------------------------------------------


            // Loop through each field to be inserted
            for(Iterator i = page.fieldList.iterator(); i.hasNext(); ) {
                String fieldname = (String) i.next();

                // Get the properties for displaying this field
                FieldProperties props = (FieldProperties) page.fieldProperties.get(fieldname);

                // Get the data to display
                FormPrintEngine.DomValue data = new FormPrintEngine.DomValue(fieldname,props.sampleData);

                // Caluclate Clipping Region
                float x1 = Math.min(props.x1,props.x2);
                float x2 = Math.max(props.x1,props.x2);
                float y1 = Math.min(props.y1,props.y2);
                float y2 = Math.max(props.y1,props.y2);
                float w = Math.abs(props.x1-props.x2)+1;
                float h = Math.abs(props.y1-props.y2)+1;

                if(log.isDebugEnabled())
                    log.debug("Print Field " + fieldname + "=" + data.getObject() + " @ [("+x1+","+y1+")->("+x2+","+y2+")]");

                // Default the font if not specified
                String font = BaseFont.HELVETICA;
                if(props.fontFace!=null)
                    font = props.fontFace;

                // Handle Barcodes diffently withing iText, don't just use fonts
                if(font.startsWith("Barcode")) {
                    String bcClassName = "com.lowagie.text.pdf." + font;
                    Object bcode = null;
                    String dataStr = data.getValue();
                    if(dataStr!=null) {
                        log.debug("Barcode Data String = " + dataStr);
                        // Try and create the correct Barcode Object
                        try {
                            Class bcClass = Class.forName(bcClassName);
                            bcode = bcClass.newInstance();
                        } catch (Exception e) {
                            String err = "Can't Create Barcode Object for barcode type '"+font+"' on field " + fieldname;
                            log.error(err, e);
                        }

                        // Only continue if the barcode object was created
                        if(bcode!=null) {

                            // Generate and Print barcode, based on common interface
                            if(bcode instanceof Barcode) {
                                Barcode b = (Barcode) bcode;
                                // Set some default output a barcode
                                b.setCode(dataStr);
                                if(props.fontSize<=0) {
                                    // Hide text if font size is 0, and make the barcode height the size of the box
                                    b.setBarHeight(h);
                                    b.setFont(null);
                                } else {
                                    b.setSize(props.fontSize); // size of text under barcode
                                    b.setBarHeight(h - props.fontSize - 5); // Adjust Bar Height to allow for font size
                                }
                                b.setN(2); // Wide Bars

                                // Set custom parameters
                                setBarcodeParams(fieldname, bcode, props.style);

                                // Print out barcode
                                Image image = ((Barcode)bcode).createImageWithBarcode(cb, null, null);
                                printImage(image, cb, x1, y1, x2, y2, props.align, props.fitMethod, props.rotate);

                            } else

                                // Print PDF417 barcode, not based on common interface
                                if(bcode instanceof BarcodePDF417) {
                                BarcodePDF417 b = (BarcodePDF417) bcode;
                                // Set some default output a barcode
                                b.setText(dataStr);
                                b.setErrorLevel(5);
                                // Set custom parameters
                                setBarcodeParams(fieldname, bcode, props.style);
                                log.debug("PDF417 Settings\n" +
                                        "BitColumns=" + b.getBitColumns() + "\n" +
                                        "CodeColumns=" + b.getCodeColumns() + "\n" +
                                        "CodeRows=" + b.getCodeRows() + "\n" +
                                        "ErrorLevel=" + b.getErrorLevel() + "\n" +
                                        "YHeight=" + b.getYHeight() + "\n" +
                                        "AspectRatio=" + b.getAspectRatio() + "\n" +
                                        "Options=" + b.getOptions() + "\n" +
                                        "LenCodewords=" + b.getLenCodewords()
                                        );

                                // Print out barcode
                                //image = b.getImage();
                                printImage(b.getImage(), cb, x1, y1, x2, y2, props.align, props.fitMethod, props.rotate);

                                } else {
                                // Error, unknown barcode
                                String err = "Error, No print handler for barcode object " + bcode.getClass().getName();
                                log.error(err);
                                //throw new EngineProcessingException(err);
                                }
                        }
                    } else
                        log.debug("SKIPPED BARCODE : No data for " + fieldname);

                    // Handle Images differently within iText, native support for JFreeChart
                } else if ("image".equalsIgnoreCase(font)) {
                    try {
                        java.awt.Image image = data.getDomImage();
                        // Add an image to the page
                        if (image != null) {
                            if (fieldname.startsWith("watermark")) {
                                // Add an image-based watermark to the under content layer
                                PdfContentByte contentUnder = m_writer.getDirectContentUnder();
                                if (props.opacity != 1f) {
                                    PdfGState gs = new PdfGState();
                                    gs.setFillOpacity(props.opacity);
                                    contentUnder.setGState(gs);
                                }
                                printImage(image, contentUnder, x1, y1, x2, y2, props.align, props.fitMethod, props.rotate);
                            } else {
                                // Add an image to main page layer
                                printImage(image, cb, x1, y1, x2, y2, props.align, props.fitMethod, props.rotate);
                            }
                        }
                    } catch (IOException e) {
                        // Add Error on page.
                        Phrase text = new Phrase("Image Error", FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,
                                8f, 0, ColorHelper.getColor("red")));
                        ColumnText ct = new ColumnText(cb);
                        ct.setSimpleColumn(text, x1, y1, x2, y2, 8f, Element.ALIGN_LEFT);
                    }
                } else if (fieldname.startsWith("watermark")) {
                    // Add a text-based watermark
                    String text = data.getValue();
                    PdfContentByte contentUnder = m_writer.getDirectContentUnder();
                    if (props.opacity != 1f) {
                        PdfGState gs = new PdfGState();
                        gs.setFillOpacity(props.opacity);
                        contentUnder.setGState(gs);
                    }
                    // The text aligns (left, center, right) on the pivot point.
                    // Default to align left.
                    float pivotX = x1;
                    float pivotY = y1;
                    if (Element.ALIGN_CENTER == props.align) {
                        pivotX = (x1 / 2) + (x2 / 2);
                        pivotY = y1;
                    } else if (Element.ALIGN_RIGHT == props.align) {
                        pivotX = x2;
                        pivotY = y1;
                    }
                    Phrase watermark = new Phrase(text, FontFactory.getFont(props.fontFace, props.fontSize,
                            decodeFontStyle(props.style), ColorHelper.getColor(defaultWatermarkColor)));
                    ColumnText.showTextAligned(contentUnder, props.align, watermark, pivotX, pivotY, props.rotate);
                } else {
                    // Handle printing of basic Text
                    float lineHeight = props.fontSize;
                    String str = data.getValue();
                    if(str!=null) {
                        // Add a bounded column to add text to.
                        Phrase text = new Phrase(str, FontFactory.getFont( props.fontFace, props.fontSize,
                                decodeFontStyle(props.style), ColorHelper.getColor(props.color) ) );
                        ColumnText ct = new ColumnText(cb);
                        if(props.fitMethod==FIT_METHOD_CLIP)
                            // set up column with height/width restrictions
                            ct.setSimpleColumn(text, x1, y1, x2, y2, lineHeight, props.align);
                        else
                            // set up column without (i.e. large) height/width restrictions
                            ct.setSimpleColumn(text, x1, y1, 1000, 0, lineHeight, props.align);
                        ct.go();
                    }
                }

                // Draw outline boxes arround fields
                if(isTemplateMode()) {
                    cb.setLineWidth(0.5f);
                    cb.setLineDash(4f, 2f);
                    cb.setColorStroke(new Color(0xA0, 0xA0, 0xA0));
                    cb.moveTo(x1, y1);
                    cb.lineTo(x1, y2);
                    cb.lineTo(x2, y2);
                    cb.lineTo(x2, y1);
                    cb.lineTo(x1, y1);
                    cb.stroke();
                }
            } // end for-loop
        } catch (DocumentException e) {
            String err = "Error printing data - " + e.getMessage();
            log.error(err ,e);
            throw new EngineProcessingException(err);
//        } catch (IOException e) {
//            String err = "Error printing data - " + e.getMessage();
//            log.error(err ,e);
//            throw new EngineProcessingException(err);
        }

    }

    /** Print a java image */
    private void printImage(java.awt.Image image, PdfContentByte cb, float x1, float y1, float x2, float y2, int alignment, int fitMethod, float rotate)
    throws BadElementException, IOException, DocumentException {
        if(image!=null) {
            // Convert to an iText Image
            Image img = Image.getInstance(image, null);
            printImage(img,cb,x1,y1,x2,y2,alignment,fitMethod,rotate);
        }
    }

    /** Print an iText image */
    private void printImage(Image image, PdfContentByte cb, float x1, float y1, float x2, float y2, int alignment, int fitMethod, float rotate)
    throws DocumentException {
        if(image!=null) {
            float boxWidth = Math.abs(x2-x1)+1;
            float boxHeight = Math.abs(y2-y1)+1;
            log.debug("Print Image (Size w="+image.getPlainWidth()+",h="+image.getPlainHeight()+") wthin BOX (w="+boxWidth+",h="+boxHeight+") FitMethod = "+fitMethod);

            // Clip the image based on the bounding box
            if(fitMethod==FIT_METHOD_CLIP) {
                if( (boxWidth < image.getPlainWidth()) || (boxHeight < image.getPlainHeight()) ) {
                    // @TODO - Clip image
                    log.warn("IMAGE CLIPPING REQUIRED, but not implemented - default to 'SCALE'...");
                    fitMethod=FIT_METHOD_SCALE;
                }
            }
            // Stretch/shrink both the X/Y to fit the bounding box
            if(fitMethod==FIT_METHOD_FILL) {
                log.debug("Scale image to fill box");
                image.scaleToFit(x2-x1, y2-y1);
            }
            // Stretch/shrink preserving the aspect ratio to fit the bounding box
            if(fitMethod==FIT_METHOD_SCALE) {
                float multipler = Math.min(boxWidth / image.getPlainWidth(), boxHeight /image.getPlainHeight());
                log.debug("Need to scale image by " + (Math.floor(multipler*10000)/100) + "%");
                image.scalePercent(multipler*100);
            }
            log.debug("Print image at (" + x1 + "," + y1 +")");
            image.setAbsolutePosition(x1,y1);
            image.setRotationDegrees(rotate);
            cb.addImage(image);
            //Phrase text = new Phrase(new Chunk(image, 0, 0));
            //ColumnText ct = new ColumnText(cb);
            //ct.setSimpleColumn(text, x1, y1, x2, y2, 10, alignment);
            //ct.go();
        }
    }

    /** decode the style string and look for attribute that effect the font.
     * Will look for (ignoring case) BOLD,ITALIC,UNDERLINE and STRIKETHRU
     */
    private int decodeFontStyle(String style) {
        int styleCode = 0;
        if (style==null) return styleCode;
        String[] options = StringHelper.parseString(style,";");
        for(int i=0;i<options.length;i++) {
            String option = options[i].toUpperCase();
            if("BOLD".equals(option))
                styleCode+=1;
            else if("ITALIC".equals(option))
                styleCode+=2;
            else if("UNDERLINE".equals(option))
                styleCode+=4;
            else if("STRIKETHRU".equals(option))
                styleCode+=8;
        }
        return styleCode;
    }



    /** Set properties from the sytle field
     * @barcode the barcode object to set via introspection
     * @param params newline or ; seperated list of field=value pairs to set
     */
    private void setBarcodeParams(String fieldname, Object barcode, String props) {
        Properties bcprops = new Properties();
        if(props!=null)
            try {
                bcprops.load(new ByteArrayInputStream(StringHelper.replace(props,";","\n").getBytes()));
            } catch (IOException e) {
                log.error("Reading Error Properties - " + props);
            }

        for (Enumeration en = bcprops.keys(); en.hasMoreElements();) {
            String name = (String)en.nextElement();
            String value = bcprops.getProperty(name);
            try {
                BeanHelper.setField(barcode, name, value);
                log.debug("setProperty '"+name+"' to '"+value+"' on Barcode for field '"+fieldname+"'.");
            } catch (Exception e) {
                log.error("Can't set property '"+name+"' to '"+value+"' on Barcode for field '"+fieldname+"'. Reason - " + e.getMessage());
            }
        }
    }

    /**
     * Any work to end printing the page
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected void endPage() throws FormPrintException {
        log.debug("endPage: Page=" + getCurrentPage());
    }

    /**
     * Any work to end printing the document
     * @throws FormPrintException Thrown if there is any form processing problems
     */
    protected void endDocument() throws FormPrintException {
        log.debug("endDocument:");
        m_generatedDoc.close();
    }

    /** Return the generated PDF document. The generate() method MUST be called
     * prior to this else an exception will be thrown
     * @throws FormPrintException thrown if any pdf access error occurs
     * @return the generated pdf as a byte array
     */
    public byte[] getGeneratedForm() throws FormPrintException {
        if(!isProcessed())
            throw new IllegalStateException("The form not been processed yet");
        return m_output.toByteArray();
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
        //@TODO Document Properties not implemented in iText engine!
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
            bos.write(m_output.toByteArray());
            bos.close();
        } catch (IOException e) {
            log.error("Error Writing out PDF", e);
            return null;
        }
        return fileout;
    }

    class PageDetailsExtended extends PageDetails {
        HashMap fieldProperties = new HashMap();
    }

    class FieldProperties {
        String fontFace=null, style=null, color=null, background=null, sampleData=null;
        boolean multiLine=false;
        float x1, y1, x2, y2, fontSize=10.0f, rotate=0.0f, opacity=1.0f;
        int fitMethod=FIT_METHOD_CLIP, align=Element.ALIGN_LEFT;
    }

}
