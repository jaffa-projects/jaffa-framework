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

package org.jaffa.modules.printing.services;

import com.pdflib.pdflib;
import java.io.File;
import java.io.FileWriter;
import org.apache.log4j.Logger;
import org.jaffa.util.LoggerHelper;

/**
 *
 * @author  PaulE
 */
public class ConvertPDFLibBlocks {

    /** Logger reference */
    public final static Logger log = Logger.getLogger(ConvertPDFLibBlocks.class);

    /** Creates a new instance of ConvertPDFLibBlocks */
    public static void convert(String templateFilename, String output) {
        try {
        if(log.isDebugEnabled())
            log.debug("Instantiate the PDFLib engine");
	pdflib pdf = new pdflib();

        // Generate a PDF in memory; insert a file name to create PDF on disk
        if (pdf.begin_document("", "") == -1) {
            log.error("Creating Blank PDF - " + pdf.get_errmsg());
            System.out.println("Error: " + pdf.get_errmsg());
            return;
        }


        if(log.isDebugEnabled())
            log.debug("Load the Template - " + templateFilename);
        // Load Template Page, will look in search path relative to bin folder
        int pdfTemplate = pdf.open_pdi(templateFilename, "", 0);
        if (pdfTemplate == -1) {
            log.error("Loading Base PDF (" + templateFilename + ") : "  + pdf.get_errmsg());
            System.out.println("Error: " + pdf.get_errmsg());
            return;
        }
        // Find out how many pages are in the template.
        int templatePages = 0;
        for(; pdf.open_pdi_page(pdfTemplate, templatePages+1, "") != -1; templatePages++);
        if(log.isDebugEnabled())
            log.debug("Template Has " + templatePages + " page(s)");

        // Error if the template has no pages!!!
        if(templatePages==0) {
            log.error("Template Form " + templateFilename + " has no pages!");
            System.out.println("Template Form " + templateFilename + " has no pages!");
            return;
        }

        // open output and write header row...
        File out = new File(output);
        FileWriter fw = new FileWriter(out);
        fw.write("Page,Field,x1',y1',x2',y2',Font,Size,Align,Multiline,Fit Method,Rotate,Style,Color,Background,Sample Data,Opacity\n");

        for(int templatePage = 0; templatePage < templatePages; templatePage++) {

            // Now get the names of all the fields on the form.
            if(log.isDebugEnabled())
                log.debug("Read the field names from template page " + (templatePage+1) + " of " + templatePages);

            // Get total number of Blocks on page
            int blockCount = (int) pdf.get_pdi_value("vdp/blockcount", pdfTemplate, templatePage, 0);
            if(log.isDebugEnabled())
                log.debug("   Field Count = " + blockCount);

            for (int i = 0; i < blockCount; i++) {

                // Page
                fw.write((templatePage+1)+",");

                // Field
                String fieldname = pdf.get_pdi_parameter("vdp/Blocks["+i+"]/Name", pdfTemplate, templatePage, 0);
                String fieldname2 = fieldname.replace('(','[');
                fieldname2 = fieldname2.replace(')',']');
                if(log.isDebugEnabled())
                    log.debug("    " + i + ") " + fieldname2);
                fw.write(fieldname2+",");

                // x1
                int x = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/Rect[0]", pdfTemplate, templatePage, 0);
                fw.write(x+",");

                //y1
                x = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/Rect[1]", pdfTemplate, templatePage, 0);
                fw.write(x+",");

                //x2
                x = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/Rect[2]", pdfTemplate, templatePage, 0);
                fw.write(x+",");

                // y2
                x = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/Rect[3]", pdfTemplate, templatePage, 0);
                fw.write(x+",");

                // Font
                String fontname = pdf.get_pdi_parameter("vdp/Blocks/"+fieldname+"/fontname", pdfTemplate, templatePage, 0);
                fw.write(fontname+",");

                // Size
                int fontsize = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/fontsize", pdfTemplate, templatePage, 0);
                fw.write(fontsize+",");

                // Align
                String align = pdf.get_pdi_parameter("vdp/Blocks/"+fieldname+"/alignment", pdfTemplate, templatePage, 0);
                if (align.length() > 0){
                    fw.write(align+",");
                }else{
                    fw.write("left,");
                }

                // Multiline
                fw.write("false");
                fw.write(',');

                // Fit Method.  For images, set the default fit method as "scale".
                String fitmethod;
                if (fontname.startsWith("Image") || fontname.startsWith("image")) {
                    fitmethod = "scale";
                    fw.write(fitmethod + ',');
                } else {
                    fw.write(',');
                }

                // Rotate
                int rotate = (int) pdf.get_pdi_value("vdp/Blocks/"+fieldname+"/rotate", pdfTemplate, templatePage, 0);
                fw.write(rotate + ",");               

                // Style
                String fontstyle;
                if (fontname.startsWith("Barcode")){
                    fontstyle = pdf.get_pdi_parameter("vdp/Blocks/"+fieldname+"/Custom/style", pdfTemplate, templatePage, 0);
                }else{
                    fontstyle = pdf.get_pdi_parameter("vdp/Blocks/"+fieldname+"/fontstyle", pdfTemplate, templatePage, 0);
                }
                fw.write(fontstyle+",");

                // Color
                fw.write(',');

                // Background
                fw.write(',');

                // Sample Data
                String defaulttext = pdf.get_pdi_parameter("vdp/Blocks/"+fieldname+"/defaulttext", pdfTemplate, templatePage, 0);
                fw.write(defaulttext+ ",");                

                // Opacity
                double opacity = (double) pdf.get_pdi_value("vdp/Blocks/" + fieldname + "/opacityfill", pdfTemplate, templatePage, 0);
                if (opacity != 0.0) {
                    fw.write(String.valueOf(opacity));
                }
                                    
                // END LINE
                fw.write('\n');

            }
        }

        fw.flush();
        fw.close();
        System.out.println("Written File " + output);
        } catch (Exception e) {
            System.out.println("Conversion Failed!");
            e.printStackTrace();
        }
    }


    /** Command line invocation of the converter
     * Arg0 = source pdf with block definitions
     * Arg1 = the target CSV file to create
     */
    public static void main(String[] args) {
        //LoggerHelper.init();
        if(args.length!=2) {
            System.out.println("java org.jaffa.printing.ConvertPDFLibBlocks [source pdf] [target csv]");
        } else {
            convert(args[0], args[1]);
        }
    }

}
