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

package org.jaffa.persistence.engines.jdbcengine.interceptor;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.persistence.engines.jdbcengine.datasource.PersistentTransaction;
import org.jaffa.persistence.engines.jdbcengine.querygenerator.JdbcBridge;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.exceptions.UOWException;
import org.jaffa.persistence.exceptions.AddFailedException;
import org.jaffa.persistence.exceptions.PreAddFailedException;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.util.MessageHelper;


/** This is the Interceptor which adds Persistent objects to the database.
 */
public class AddInterceptor extends AbstractInterceptor {
    
    private static final Logger log = Logger.getLogger(AddInterceptor.class);

    private static final int PRIMARY_KEY_ERROR_CODE = 1;
    
    /**Performs the logic associated with adding Persistent objects to the database.
     * This will add each object in the PersistentTransaction's ADD collection to the database, utilising the JdbcBridge.
     * It will then pass on the control to the next Interceptor in the chain.
     * @param pt The PersistentTransaction object, on which the Interceptor is to be executed.
     * @throws UOWException if any error occurs.
     * @return the output from the next Interceptor in the chain.
     */
    public Object invoke(PersistentTransaction pt) throws UOWException {
        Collection objects = pt.getAdds();
        if (objects != null) {
            // add the objects to the database
            for (Iterator i = objects.iterator(); i.hasNext();) {
                IPersistent object = (IPersistent) i.next();
                try {
                    if (log.isDebugEnabled())
                        log.debug("Invoking JdbcBridge.executeAdd() for the object " + object);
                    JdbcBridge.executeAdd(object, pt.getDataSource());
                    pt.getDataSource().clearObjectCache();
                } catch(SQLIntegrityConstraintViolationException e) {
                    if(e.getErrorCode() == PRIMARY_KEY_ERROR_CODE) {
                        String str = "The primary-key is not unique: " + this;
                        log.error(str);
                        String labelToken = null;
                        try {
                            labelToken = PersistentHelper.getLabelToken(object.getClass().getName());
                        } catch (Exception ex) {
                            // don't do anything.. just return the domainClassName
                        }
                        if (labelToken == null)
                            labelToken = MessageHelper.tokenize(object.getClass().getName());

                        throw new PreAddFailedException(null, new DuplicateKeyException(labelToken));
                    }else {
                        String str = "Error while adding the Persistent object to the database: " + object;
                        log.error(str, e);
                        // Report as a PreAddFailedException as this is an integrity constraint violation, which
                        // was checked in pre-add previously
                        throw new PreAddFailedException(null, e);
                    }
                }
                catch (Exception e) {
                    String str = "Error while adding the Persistent object to the database: " + object;
                    log.error(str, e);
                    throw new AddFailedException(null, e);
                }
                i.remove();
            }
        }
        
        // pass control to the next interceptor in the chain
        if(getNextInterceptor() != null) {
            if (log.isDebugEnabled())
                log.debug("Invoking the next Interceptor in the chain " + getNextInterceptor().getClass().getName());
            return getNextInterceptor().invoke(pt);
        } else {
            if (log.isDebugEnabled())
                log.debug("This is the end of the Interceptor chain");
            return null;
        }
    }
    
}
