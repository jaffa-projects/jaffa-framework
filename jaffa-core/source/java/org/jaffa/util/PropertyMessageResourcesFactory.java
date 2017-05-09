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

package org.jaffa.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.jaffa.config.Config;

/**
 * Factory for PropertyMessageResources instances. The
 * configuration paramter for such instances is the base Java package
 * name of the resources entries from which our keys and values will be
 * loaded.
 *
 * This class is based on the struts class org.apache.struts.util.PropertyMessageResourcesFactory
 * It creates instances of org.jaffa.util.PropertyMessageResources, which loads the properties file using File I/O and supports the flushing of the message-resource cache.
 * Only one PropertyMessageResourcesFactory instance will be created per config.
 * @author  GautamJ
 */
public class PropertyMessageResourcesFactory extends MessageResourcesFactory {
    
    /** This Map will ensure that only one instance of the MessageResources is instantiated per config.
     * It will contain config/MessageResources pairs.
     */
    private static Map c_messageResourcesMap = new HashMap();
    
    /** This static field will maintain a reference to the default MessageResources instance, which will point to the ResourceBundle as specified in the framework.properties file. */
    private static MessageResources c_defaultMessageResources = null;
    
    /** This static block will instantiate the default MessageResources object. */
    static {
        String config = (String) Config.getProperty(Config.PROP_MESSAGE_RESOURCES_BUNDLE, null);
        if (config != null)
            c_defaultMessageResources = new PropertyMessageResourcesFactory().createResources(config);
    }
    
    /** Create and return a newly instansiated MessageResources.
     * This method must be implemented by concrete subclasses.
     * It'll ensure that only one instance of the MessageResources is instantiated per config.
     *
     * @param config Configuration parameter(s) for the requested bundle
     * @return a newly instansiated MessageResources.
     */
    public MessageResources createResources(String config) {
        MessageResources messageResources = (MessageResources) c_messageResourcesMap.get(config);
        if (messageResources == null)
            messageResources = instantiateMessageResources(config);
        return messageResources;
    }
    
    /** Returns a reference to the default MessageResources instance, which will point to the ResourceBundle as specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * A null will be returned in case an invalid or if no ResourceBundle is specified in the 'framework.messageResources.bundle' property of framework.properties file.
     * @return the default MessageResources instance.
     */
    public static MessageResources getDefaultMessageResources() {
        return c_defaultMessageResources;
    }

    
    
    
    /** Creates an instance of PropertyMessageResources with the proper synchronized setting. */
    private MessageResources instantiateMessageResources(String config) {
        synchronized(c_messageResourcesMap) {
            MessageResources messageResources = (MessageResources) c_messageResourcesMap.get(config);
            if (messageResources == null) {
                messageResources = new PropertyMessageResources(this, config, this.returnNull);
                c_messageResourcesMap.put(config, messageResources);
            }
            return messageResources;
        }
    }
}
