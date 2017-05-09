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

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;
import org.jaffa.util.BeanHelper;
import org.jaffa.modules.printing.components.formselectionmaintenance.IAdditionalDataObject;

/**
 *
 * @author PaulE
 */
public class CustomDataBeanFactory implements IDataBeanFactory {
    
    private static final Logger log = Logger.getLogger(CustomDataBeanFactory.class);
    
    private Class m_beanClass = null;
    private HashMap<String,String> m_keys = new HashMap<String,String>();
    private String m_formName = null;
    private Object additionalDataObject = null;
    
    public void setBeanClass(Class beanClass) {
        m_beanClass = beanClass;
    }
    
    public void setKey(String key, String value) {
        m_keys.put(key,value);
    }
 
    public void setFormName(String formName) {
        m_formName = formName;
    }
    
    /** Setter for property additionalDataObject.
     * @param additionalDataObject New value of property additionalDataObject.
     */
    public void setAdditionalDataObject(java.lang.Object additionalDataObject) {
        this.additionalDataObject = additionalDataObject;
    }
  
    public IDataBean getInstance() throws FrameworkException, ApplicationExceptions {
        // Construct the DataBean
        IDataBean dataBean = null;
        IAdditionalDataObject dataBean2 = null;
        try {
            log.debug("Created a Custom DataBean " + m_beanClass);
            dataBean = (IDataBean) m_beanClass.newInstance();
            if (m_beanClass.newInstance() instanceof IAdditionalDataObject) {                
                if (dataBean != null) {
                    dataBean2 = (IAdditionalDataObject) dataBean;
                    dataBean2.setAdditionalDataObject(additionalDataObject);
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Can't create data bean",e);
            throw new EngineProcessingException(e.getMessage(),e);
        } catch (InstantiationException e) {
            log.error("Can't create data bean",e);
            throw new EngineProcessingException(e.getMessage(),e);
        }
        if(dataBean==null)
            throw new EngineProcessingException("No CustomDataBean created");       
        
        // Populate the keys
        if(m_keys!=null) {
            for(String field : m_keys.keySet()) {
                String value = m_keys.get(field);
                try {
                    BeanHelper.setField(dataBean, field, value);
                } catch (IntrospectionException e) {
                    log.error("Can't set bean property " + field,e);
                } catch (IllegalAccessException e) {
                    log.error("Can't set bean property " + field,e);
                } catch (InvocationTargetException e) {
                    log.error("Can't set bean property " + field,e);
                }
            }
        }
        
        if (m_formName != null) {
            try {
                BeanHelper.setField(dataBean, "formName", m_formName);
            } catch (IntrospectionException e) {
                log.error("Can't set bean property formName. " + e);
            } catch (IllegalAccessException e) {
                log.error("Can't set bean property formName. " + e);
            } catch (InvocationTargetException e) {
                log.error("Can't set bean property formName. " + e);
            }
        }
             
        // Now make sure the bean has data in it
        dataBean.populate();
        return dataBean;
    }
    
}
