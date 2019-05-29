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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.util.URLHelper;

import javax.xml.transform.TransformerException;

/**
 *
 * @author PaulE
 */
public class FormCache {

    /** Logger reference */
    public final static Logger log = Logger.getLogger(FormCache.class);

    public static String RULE_TEMP_TEMPLATE_PATH = "jaffa.printing.templates.tempPath";
    /**
     * This transformer replaces watermark text with an "<xsl:value-of..." tag in order for the watermark to show the
     * value of the XML data.  For example: transorm watermark text “$XML/formBSPlacard1078Data/watermarkReprintText”
     * into "<xsl:value-of select="$XML/formBSPlacard1078Data/watermarkReprintText"/>"
     */
    private static final String XSLT_TRANSFORMER = "resources/FopFormXsltTransformer.xsl";

    private Map<String, IDataBean> cache = new HashMap<String,IDataBean>();
    private String templatePath = null;

    public void addForm(FormDefinition form, IDataBean data) {
        String key = getKey(form);
        cache.put(key,data);
    }

    public IDataBean lookupForm(FormDefinition form) {
        String key = getKey(form);
        return cache.get(key);
    }

    /** Get the template file out of the BLOB's, write them to a file, and
     * return the path to that file.
     * A temp location for this can be defined, and in this case it will
     * cache them there and only read them from the database if they don't exist,
     * or are older that the database time stamp.
     */
    public String getTemplate(FormDefinition form, String engineType) throws FrameworkException {
        String extn = ".tpl";
        if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(engineType) ||
                FormPrintFactory.ENGINE_TYPE_PDFLIB.equals(engineType)) {
            extn = ".pdf";
        } else if (FormPrintFactory.ENGINE_TYPE_FOP.equals(engineType)){
            extn = ".xslt";
        }
       File temp = new File(getTemplatePath(), form.getFormId() + extn);

       // See if a cached version can be returned
        if (temp.exists()
                && ((form.getLastChangedOn() == null && form.getCreatedOn() != null && temp.lastModified() > form.getCreatedOn().timeInMillis())
                || (form.getLastChangedOn() != null && temp.lastModified() > form.getLastChangedOn().timeInMillis()))) {
            // If this is itext, make sure the CSV file is also current
            if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(engineType)) {
                File tempCsv = new File(getTemplatePath(), form.getFormId() + extn + ".csv");
                // See if a cached version can be returned
                if (tempCsv.exists()
                        && ((form.getLastChangedOn() == null && form.getCreatedOn() != null && tempCsv.lastModified() > form.getCreatedOn().timeInMillis())
                        || (form.getLastChangedOn() != null && tempCsv.lastModified() > form.getLastChangedOn().timeInMillis()))) {
                    log.debug("Use Cached Template/CSV :" + temp.getAbsolutePath());
                    return temp.getAbsolutePath();
                }
            } else {
                log.debug("Use Cached Template :" + temp.getAbsolutePath());
                return temp.getAbsolutePath();
            }
        }

       // Extract this from the Database
       FormTemplate formTemp = form.getFormTemplateObject();
       if(formTemp==null)
           return null;

       // Write out the template file
       if(temp.exists())
           temp.delete();
       if(formTemp.getTemplateData()!=null) {
           if(!temp.getParentFile().exists())
               temp.getParentFile().mkdirs();
           try {
               if (FormPrintFactory.ENGINE_TYPE_FOP.equals(engineType)) {
                   InputStream xsltTransformer = URLHelper.getInputStream(XSLT_TRANSFORMER);
                   try {
                       // Do all necessary transformations to the form XSLT file
                       XmlTransformerUtil.transform(formTemp.getTemplateData(), xsltTransformer, temp);
                   } catch (TransformerException e) {
                       String err = "Failed to Transform and write the Form Template " + temp.getAbsolutePath();
                       log.error(err, e);
                       throw new EngineProcessingException(err,e);
                   }
               } else {
                   try (FileOutputStream fos = new FileOutputStream(temp)) {
                       fos.write(formTemp.getTemplateData());
                   }
               }
               log.debug("Written Template File " + temp.getAbsolutePath());
           } catch (IOException e) {
               String err = "Failed to write Template File " + temp.getAbsolutePath();
               log.error(err, e);
               throw new EngineProcessingException(err,e);
           }
       } else {
           log.warn("No Template In Form Definition " + form.getFormId() + "(Name="+form.getFormName()
                   +",Alt="+form.getFormAlternate()+",Variation="+form.getFormVariation()+",Output"
                   +form.getOutputType() );
       }

       File tempLayout = null;
       // If the engine type is itext, specify where the CSV file should be written
        if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(engineType))
            tempLayout = new File(getTemplatePath(), form.getFormId() + ".pdf.csv");
       // See if there is layout data needed
       if(tempLayout != null) {
           if(tempLayout.exists())
               tempLayout.delete();
           if(formTemp.getLayoutData()!=null) {
               try {
                   FileOutputStream fos = new FileOutputStream(tempLayout);
                   fos.write(formTemp.getLayoutData());
                   fos.close();
                   log.debug("Written Template Layout File " + tempLayout.getAbsolutePath());
               } catch (IOException e) {
                   String err = "Failed to write Layout File " + tempLayout.getAbsolutePath();
                   log.error(err, e);
                   throw new EngineProcessingException(err,e);
               }
           }
       }

       // return the temp file
       return temp.getAbsolutePath();
    }

    public String getTemplatePath() {
        if(templatePath==null)
            templatePath = (String) ContextManagerFactory.instance().getProperty(RULE_TEMP_TEMPLATE_PATH);
        if(templatePath==null)
            templatePath = System.getProperty("java.io.tmpdir");
        return templatePath;
    }


    private String getKey(FormDefinition form) {
        return new StringBuffer()
            .append(form.getDomKey1()).append('|')
            .append(form.getDomKey2()).append('|')
            .append(form.getDomKey3()).append('|')
            .append(form.getDomKey4()).append('|')
            .append(form.getDomKey5()).append('|')
            .append(form.getDomKey6()).append('|')
            .append(form.getDomFactory()).append('|')
            .append(form.getDomClass()).toString();
    }
}
