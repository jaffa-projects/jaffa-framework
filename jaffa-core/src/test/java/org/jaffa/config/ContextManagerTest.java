/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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

package org.jaffa.config;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.loader.config.ApplicationRulesManager;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.session.IContextManager;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test for ContextManager to check ApplicationRulesLoader loaded the rules as
 * expected
 */
public class ContextManagerTest {

    public static final String USER_ATTRIBUTE = "org.jaffa.presentation.portlet.session.UserInfo";
    public static final String USER = "GOLDADMIN";
    public static final String VARIATION = "NULL";

    private static AnnotationConfigApplicationContext xmlLoaderConfig =
            new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    /**
     * testContextManager - Verifies that the application rules have been loaded correctly for both global and variation
     * specific rules files.
     * @throws Exception
     */
    @Test
    public void testContextManager() throws Exception {

        ApplicationRulesManager applicationRulesManager =
                xmlLoaderConfig.getBean(ApplicationRulesManager.class);
        assertNotNull(applicationRulesManager.getApplicationRulesRepository());

        MockHttpServletRequest request = new MockHttpServletRequest();

        // creating user session
        UserSession us = UserSession.getUserSession(request);
        us.setUserId(USER);
        us.setVariation(VARIATION);

        // creating mock http session for the same use session
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(USER_ATTRIBUTE, us);

        // setting the mock session into request
        request.setSession(session);

        ContextManagerFactory.instance().setThreadContext(request);

        // Property from global
        IContextManager iContextManager = ContextManagerFactory.instance();
        assertNotNull(iContextManager.getProperty("org.jaffa.config.global"));

        assertNull(iContextManager.getProperty("nonExistentKey"));

        // Property from variation
        assertNotNull(ContextManagerFactory.instance().getProperty("org.jaffa.config.variation"));

        // Property from variation
        assertEquals("true", ContextManagerFactory.instance().getProperty("org.jaffa.config.hidepanel"));

        // Property from variation
        assertEquals("true", ContextManagerFactory.instance().getProperty("org.jaffa.config.hidemaintenancepanel"));

        assertEquals("datadist_root/outbound", ContextManagerFactory.instance().getProperty("datadist.outboundFolder"));

        assertEquals("datadist_root/outbound/fragments", ContextManagerFactory.instance().getProperty("datadist.outboundFolder.fragments"));

        assertEquals("c:/test/interfaces_root/outbound/test", ContextManagerFactory.instance().getProperty("interfaces.outboundTestFolder"));




    }

}
