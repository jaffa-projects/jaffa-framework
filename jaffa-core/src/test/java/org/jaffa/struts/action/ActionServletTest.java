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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.struts.action;

import org.apache.struts.config.ModuleConfig;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for Custom Jaffa implementation of ActionServlet, which
 * is used to read struts-config from classpath with in jars
 */
public class ActionServletTest {
    private ActionServlet target;
    private ServletConfig mockServletConfig;
    private ServletContext mockServletContext;

    /**
     * setting the up objects/properties before a Test is run
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        mockServletConfig = mock(ServletConfig.class);
        mockServletContext = mock(ServletContext.class);

        target = new ActionServlet(){
            public  ServletConfig getServletConfig(){
                return mockServletConfig;
            }
            public  ServletContext getServletContext(){
                return mockServletContext;
            }
        };
    }

    /**
     * Tests whether the New functionality is called when the paths is passed with
     *  "classpath*:/META-INF/struts-config.xml".
     * and successfully parses the struts-config.xml which is there in META-INF folder.
     * @throws Exception
     */
    @Test
    public void testWithMetaInfConfig() throws Exception {
        //initialize
        String prefix = "MyTest";
        String paths = "classpath*:/META-INF/struts-config.xml";

        //test
        ModuleConfig moduleConfig = target.initModuleConfig(prefix, paths);

        //verify
        assertNotNull("ActionFormBean should have been set", moduleConfig.getActionFormBeanClass());
        assertNotNull("ActionForward should have been set", moduleConfig.getActionForwardClass());
        assertNotNull("ActionMapping should have been set", moduleConfig.getActionMappingClass());
        assertEquals("Prefix Value should be set on ModuleConfig", prefix, moduleConfig.getPrefix());
        assertEquals("Prefix Value should be set on ModuleConfig", prefix, moduleConfig.getPrefix());
        verify(mockServletContext).setAttribute(anyString(), any());
    }

    /**
     * Tests whether the Existing functionality is called when the paths is not passed with
     *  "classpath*:/META-INF/struts-config.xml". This throws Exception as
     *  some of the classes are not initialized which is expected as
     *  we are not testing existing struts behaviour here.
     *
     * @throws Exception
     */
    @Test
    public void testWithoutMetaInfConfig() throws Exception {
        try {
            //initialize
            String prefix = "MyTest";
            String paths = "struts-config.xml";

            //test
            target.initModuleConfig(prefix, paths);

            //verify
            fail("This should throw an Exception as Servlet Internal is not Initialized");
        }catch (Exception e){

        }
    }

}
