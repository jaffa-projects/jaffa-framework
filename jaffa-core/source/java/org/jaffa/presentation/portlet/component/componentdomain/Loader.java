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
 * 1. Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3. The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4. Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5. Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

/*
 * Loader.java
 *
 * Created on April 17, 2002, 11:53 AM
 */
package org.jaffa.presentation.portlet.component.componentdomain;

import org.apache.log4j.Logger;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.components.ComponentManager;
import org.jaffa.presentation.portlet.component.ComponentDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is used to load the domain information from the Domain Objects based
 * on the XML data, into definition objects that can be used by the rest of the architecture
 *
 * @author  paule
 * @version 1.0
 */
public class Loader {

    private static final Logger LOGGER = Logger.getLogger(Loader.class);

	/** The singleton Loader. */
	private static Loader loaderSingleton = null;

	/** The ComponentManager that maintains the registered components.  */
	private static ComponentManager componentManager = null;

	/**
	 * Returns the singleton Loader, creating it if necessary.
	 * @return the singleton
     */
	public static Loader getInstance() {
		if (loaderSingleton == null) {
			synchronized (Loader.class) {
				if (loaderSingleton == null) {
					loaderSingleton = new Loader();
				}
			}
		}
		return loaderSingleton;
	}

	/** The private no-arg constructor forces use of getInstance() to
	 * obtain the singleton Loader.
	 */
	private Loader() {
	}

    /** Returns the component definitions that were defined in XML.
     * @return Returns a Map where the key is the component name,
	 * and the value is a ComponentDefinition object
     */
	public static synchronized Map<String, ComponentDefinition> getComponentPool() {
        IRepository<ComponentDefinition> repository = componentManager.getComponentRepository();
		return repository.getMyRepository();
	}

	public static void setComponentManager(ComponentManager componentManager) {
		Loader.componentManager = componentManager;
	}

}
