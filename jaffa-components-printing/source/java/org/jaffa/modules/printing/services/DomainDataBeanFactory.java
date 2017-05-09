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

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.modules.printing.services.exceptions.EngineProcessingException;

/**
 * The problem with this class is that we don't know what the order of
 * the keys to the domain object are as all we get from the findByPK method
 * is the classes. We have to assume then if the object has multiple key fields,
 * the setKey() method is called in the order of the keys. We in effect are ignoring
 * the name on the Key, and relying on the order they are passed.
 *
 * @author PaulE
 */
public class DomainDataBeanFactory implements IDataBeanFactory {

    /** Logger reference */
    private final static Logger log = Logger.getLogger(FormProcessor.class);
    
    private Class m_beanClass = null;
    private List m_keys = new ArrayList();
    private Object additionalDataObject = null;
    // m_formName is not currently used here, but must exists to satisfy the interface requirement.
    private String m_formName = null;
    
    public void setBeanClass(Class beanClass) {
        m_beanClass = beanClass;
    }

    public void setKey(String key, String value) {
        m_keys.add(value);
    }
  
    public void setFormName(String formName) {
        m_formName = formName;
    }
        
    public void setAdditionalDataObject(java.lang.Object additionalDataObject) {
        this.additionalDataObject = additionalDataObject;
    }
  
    public IDataBean getInstance() throws FrameworkException, ApplicationExceptions {
        // Construct the DataBean
        IDataBean dataBean = null;
        log.debug("Created a DomainDataBean to wrapper " + m_beanClass);
        dataBean = (IDataBean) new DomainDataBean(m_beanClass, m_keys);
        if(dataBean==null)
            throw new EngineProcessingException("No DomainDataBean created");

        // Now make sure the bean has data in it
        dataBean.populate();
        return dataBean;
    }
    
    
}
