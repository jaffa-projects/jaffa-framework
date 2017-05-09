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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.persistence.UOW;

/** This beam implements the IDataBean interface and is used to wrapper any
 * domain object for use as the root node of a DataBean for form printing.
 * <p>
 * It is constructed via the DomainDataBeanFactory, via the AbstractDataBeanFactory
 *
 * @author PaulE
 * @version 1.0
 */
public class DomainDataBean implements IDataBean {
    
    private static final Logger log = Logger.getLogger(DomainDataBean.class);
    private Class m_beanClass = null;
    private List m_keys = null;
    private Object m_domainObject = null;
    
    
    /** Creates a new instance of DomainDataBean */
    DomainDataBean(Class domainClass, List keys) {
        m_beanClass = domainClass;
        m_keys = keys;
    }
    
    public void populate() throws FrameworkException, ApplicationExceptions {
        // Lookup the "findByPK" method for this domain object
        log.debug("Get the findByPK method for " + m_beanClass.getName());
        try {
            for(Method method : m_beanClass.getDeclaredMethods()) {
                if( method.getName().equals("findByPK") &&
                    method.getParameterTypes()[0].equals(UOW.class) &&
                    method.getParameterTypes().length == m_keys.size()+1 ) {
                    
                    // We have found a sutable method
                    log.debug("Found Method : " + method.toString());
                    Object[] args = new Object[method.getParameterTypes().length];
                    args[0] = (UOW)null;
                    int argCount=1;
                    for(Object arg : m_keys) {
                        log.debug("    Argument " + argCount + " = " + arg);
                        args[argCount++] = arg;
                    }
                    try {
                        m_domainObject = method.invoke(null,args);
                        log.debug("Found Domain Object : " + m_domainObject);
                    } catch (IllegalAccessException e) {
                        log.error("Can't invoke " + method.toString(), e);
                        throw new EngineProcessingException(e.getMessage(),e);
                    } catch (InvocationTargetException e) {
                        log.error("Can't invoke " + method.toString(), e);
                        throw new EngineProcessingException(e.getMessage(),e);
                    }
                    if(m_domainObject == null) {
                        throw new DataNotFoundException(new String[] {"No Method", m_beanClass.getName()});
                    }
                    return;
                }
            }
            
            //Error - method not found
            throw new DataNotFoundException("No findByPK method");
            
        } catch (ApplicationException e) {
            throw new ApplicationExceptions(e);
        }
        
    }
    
    public Object getDocumentRoot() {
        return m_domainObject;
    }
    
    public String getFormAlternateName() {
        return null;
    }
    
    public DocumentPrintedListener getDocumentPrintedListener() {
        return null;
    }
    
}
