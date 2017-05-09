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
 * 1.    Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.    Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.    The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.    Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.    Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

import org.apache.log4j.Logger;

import java.util.HashMap;

import org.jaffa.modules.printing.services.exceptions.EngineInstantiationException;
import org.jaffa.session.ContextManagerFactory;

/**
 * A factory class that returns an instance of a Persistence Engine.
 */
public class FormPrintFactory {

    public static final String ENGINE_TYPE_FOP = "FOP";
    public static final String ENGINE_TYPE_ITEXT = "iText";
    public static final String ENGINE_TYPE_PDFLIB = "PDFlib";
    public static final String ENGINE_TYPE_VELOCITY = "Velocity";
    public static final String ENGINE_TYPE_SCRIPTING = "BSF";
    private static final Logger log = Logger.getLogger(FormPrintFactory.class);
    private static final String ENGINE_KEY = "jaffa.printing.formPrintEngine.default";
    private static IFormPrintFactory externalFactory;

    public static IFormPrintFactory getExternalFactory() {
        return externalFactory;
    }

    public static void setExternalFactory(IFormPrintFactory externalFactory) {
        FormPrintFactory.externalFactory = externalFactory;
    }

    /**
     * Returns an instance of IFormPrintEngine.
     * It reads the 'framework.printing.formPrintFactory' property from the framework.properties file.
     * It then instantiates the class returned by the property.
     *
     * @return an instance of IFormPrintEngine.
     * @throws EngineInstantiationException if the property is not defined, or if
     *                                      the class cannot be found, or if the class canot be instantiated.
     */
    public static IFormPrintEngine newInstance()
            throws EngineInstantiationException {
        String engineType = (String) ContextManagerFactory.instance().getProperty(ENGINE_KEY);
        if (engineType == null)
            engineType = "default";
        return newInstance(engineType);
    }


    /**
     * Returns an instance of IFormPrintEngine.
     * It reads the 'framework.printing.formPrintFactory' property from the framework.properties file.
     * It then instantiates the class returned by the property.
     *
     * @return an instance of IFormPrintEngine.
     * @throws EngineInstantiationException if the property is not defined, or if
     *                                      the class cannot be found, or if the class canot be instantiated.
     */
    public static IFormPrintEngine newInstance(String engineType)
            throws EngineInstantiationException {
        if (log.isDebugEnabled())
            log.debug("Creating an instance of a FormPrintEngine, type=" + engineType);

        if (externalFactory != null) {
            IFormPrintEngine engine = externalFactory.newInstance(engineType);
            if (engine != null) {
                return engine;
            }
        }

        Class engineClass = null;
        String rule = "jaffa.printing.formPrintEngines." + engineType;
        String engineClassName = (String) ContextManagerFactory.instance().getProperty(rule);
        if (engineClassName == null) {
            if (engineType.equals("default"))
                engineClass = FormPrintEngineIText.class;
            else {
                String str = "Unknown Engine Type '" + engineType + "'. Should be defined under the name " + rule;
                log.error(str);
                throw new EngineInstantiationException(new String[]{str});
            }
        }
        if (engineClass == null)
            try {
                engineClass = Class.forName(engineClassName);
            } catch (ClassNotFoundException e) {
                String str = "Class " + engineClass + " not found for Engine Type '" + engineType + "'";
                log.error(str);
                throw new EngineInstantiationException(new String[]{str});
            }
        if (!IFormPrintEngine.class.isAssignableFrom(engineClass)) {
            String str = "Engine Class " + engineClass + " must implement interface IFormPrintEngine";
            log.error(str);
            throw new EngineInstantiationException(new String[]{str});
        }
        try {
            if (log.isDebugEnabled())
                log.debug("Creating an instance of the Form Print Engine: " + engineType + " -> " + engineClassName);
            return (IFormPrintEngine) engineClass.newInstance();
        } catch (Exception e) {
            String str = "The Form Print Engine '" + engineClassName + "' could not be instantiated";
            log.error(str, e);
            throw new EngineInstantiationException(new String[]{str}, e);
        }
    }


}
