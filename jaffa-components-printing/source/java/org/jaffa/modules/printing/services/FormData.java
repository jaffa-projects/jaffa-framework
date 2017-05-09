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

import org.jaffa.modules.printing.services.exceptions.FormPrintException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.util.MessageHelper;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;

/**
 * @author DennisL
 * The base class for root form data objects.
 */
public class FormData {
    /** Logger reference */
    private final static Logger log = Logger.getLogger(FormData.class);

    private static final String VELOCITY_TEMPLATE = "label.Jaffa.Printing.VelocityTemplate.";
    private static final String VELOCITY_TEMPLATE2 = "label.Jaffa.Printing.VelocityTemplate2.";
    private static final String VELOCITY_TEMPLATE3 = "label.Jaffa.Printing.VelocityTemplate3.";
    private String[] m_lines;
    private String[] m_lines2;
    private String[] m_lines3;
    private String m_velocityTemplate;

    /** Creates a new instance of FormData */
    public FormData() {
    }

    /**
     * Returns a string array of a rendered Velocity template. This method is
     * called by the print engine when a print form field layout (.csv file)
     * uses the field name "lines".  The field name "lines" will typically be
     * used as "lines.0", "lines.1", etc., corresponding to each element of the
     * output string array.  To populate the string array, the form
     * databean must invoke setLines().
     * @return a string array of a rendered Velocity template.
     */
    public String[] getLines() {
        return m_lines;
    }

    /**
     * Setter for property lines.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines() throws ApplicationExceptions {
        if (m_lines == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE+this.getClass().getSimpleName(), null);
            if (m_velocityTemplate != null && m_velocityTemplate.length() > 0) {
                m_lines = renderTemplate();
            }
        }
    }
    
    /**
     * Setter for property lines.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines(String formName) throws ApplicationExceptions {
        if (m_lines == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE+this.getClass().getSimpleName()+"."+formName, null);
            m_lines = renderTemplate();
        }
    }
  
    /**
     * Returns a string array of a rendered Velocity template. This method is
     * called by the print engine when a print form field layout (.csv file)
     * uses the field name "lines2".  The field name "lines2" will typically be
     * used as "lines2.0", "lines2.1", etc., corresponding to each element of the
     * output string array.  To populate the string array, the form
     * databean must invoke setLines2().
     * @return a string array of a rendered Velocity template.
     */
    public String[] getLines2() {
        return m_lines2;
    }
    
    /**
     * Setter for property lines2.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines2' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines2() throws ApplicationExceptions {
        if (m_lines2 == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE2+this.getClass().getSimpleName(), null);
            m_lines2 = renderTemplate();
        }
    }
  
    /**
     * Setter for property lines2.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines2' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines2(String formName) throws ApplicationExceptions {
        if (m_lines2 == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE2+this.getClass().getSimpleName()+"."+formName, null);
            m_lines2 = renderTemplate();
        }
    }

    /**
     * Returns a string array of a rendered Velocity template. This method is
     * called by the print engine when a print form field layout (.csv file)
     * uses the field name "lines3".  The field name "lines3" will typically be
     * used as "lines3.0", "lines3.1", etc., corresponding to each element of the
     * output string array.  To populate the string array, the form
     * databean must invoke setLines3().
     * @return a string array of a rendered Velocity template.
     */
    public String[] getLines3() {
        return m_lines3;
    }
    
    /**
     * Setter for property lines3.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines3' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines3() throws ApplicationExceptions {
        if (m_lines3 == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE3+this.getClass().getSimpleName(), null);
            m_lines3 = renderTemplate();
        }
    }
 
    /**
     * Setter for property lines3.
     * Renders a Velocity template then splits the rendered template at each
     * new line, putting each line into 'lines3' String array.
     * As a pre-requisite, the Velocity template must be defined as a label in
     * the standard label file.
     */
    public void setLines3(String formName) throws ApplicationExceptions {
        if (m_lines3 == null) {
            m_velocityTemplate = MessageHelper.findMessage(VELOCITY_TEMPLATE3+this.getClass().getSimpleName()+"."+formName, null);
            m_lines3 = renderTemplate();
        }
    }

    public String[] renderTemplate() throws ApplicationExceptions {
        if (m_velocityTemplate != null && m_velocityTemplate.length() > 0 && !m_velocityTemplate.startsWith("???")) {
            try {
                VelocityTemplateHelper vth = new VelocityTemplateHelper();
                vth.setDataSource(this);
                vth.setTemplate(m_velocityTemplate);
                return (vth.renderTemplateLines());
            } catch (FormPrintException fpe) {
                fpe.printStackTrace();
                throw new ApplicationExceptions(new ApplicationException("exception.org.jaffa.modules.printing.services.FormData.VelocityTemplateError") {});
            }
        }else{
            return (null);
        }
    }

    /** Getter for property currentDateTime.
     * @return Value of property currentDateTime.
     */
    public String getCurrentDateTime() {
        return Formatter.format(new DateTime());
    }
}
