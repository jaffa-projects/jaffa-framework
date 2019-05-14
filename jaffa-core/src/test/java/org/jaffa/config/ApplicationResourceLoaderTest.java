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

package org.jaffa.config;

import org.jaffa.loader.CoreLoaderConfig;
import org.jaffa.loader.config.ApplicationResourcesManager;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Locale;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for ApplicationResourceLoader
 * <p>
 * This test class test both ApplicationResourceLoader and
 * PropertyMessageResources
 */
public class ApplicationResourceLoaderTest {

    private static AnnotationConfigApplicationContext resourceLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    /**
     * setting the up objects/properties before a Test is run
     */
    @Before
    public void setup() {
        ApplicationResourcesManager applicationResourcesManager = resourceLoaderConfig.getBean(ApplicationResourcesManager.class);

        MockHttpServletRequest request = new MockHttpServletRequest();

        // creating user session
        UserSession us = UserSession.getUserSession(request);
        us.setUserId("USER");
        us.setVariation("NULL");

        // creating mock http session for the same use session
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("org.jaffa.presentation.portlet.session.UserInfo", us);

        // setting the mock session into request
        request.setSession(session);
        ContextManagerFactory.instance().setThreadContext(request);
         String overrideFileDir = ClassLoader.getSystemResource("ApplicationResourcesOverride.properties").getFile();
        overrideFileDir = overrideFileDir.substring(1,overrideFileDir.lastIndexOf("/"));
        ContextManagerFactory.instance().setProperty("data.directory",overrideFileDir);
    }

    @Test
    public void testApplicationResourceLoader() {

		/*
         * ApplicationResourceLoader Test
		 */
        ApplicationResourceLoader resourceLoader = new ApplicationResourceLoader();

        Properties defaultProperties = resourceLoader.getLocaleProperties("en_GB_NULL");
        String defaultResourceLoadResult = (defaultProperties == null || defaultProperties.size() < 1)
                ? "Default Resource Load Fail" : "Default Resource Load Success";

        Properties localeProperties = resourceLoader.getLocaleProperties("ar_OM");
        String localeResourceLoadResult = (localeProperties == null || localeProperties.size() < 1)
                ? "Locale Resource Load Fail" : "Locale Resource Load Success";

        Properties overrideProperties = resourceLoader.getApplicationResourcesOverride(null);
        System.out.println(overrideProperties);

        // Default Resource
        assertEquals("Default Resource Load Success", defaultResourceLoadResult);

        // Locale Specific Resource
        assertEquals("Locale Resource Load Success", localeResourceLoadResult);

        assertNotNull(defaultProperties);

        // Override resource check
        assertEquals("Printer Name", defaultProperties.getProperty("label.Jaffa.Printing.PrinterDefinition.RealPrinterName"));

        assertEquals("Edit Label", defaultProperties.getProperty("label.Jaffa.Admin.LabelEditor.Label"));

        assertEquals("Label", resourceLoader.getApplicationResourcesDefault().get("label.Jaffa.Admin.LabelEditor.Label"));

        assertEquals("Edit Label", resourceLoader.getApplicationResourcesOverride(null).get("label.Jaffa.Admin.LabelEditor.Label"));


		/*
		 * PropertyMessageResources Test
		 */
        // Default Language Test
        assertEquals("Edit Label", MessageHelper.findMessage("label.Jaffa.Admin.LabelEditor.Label", null));

        // Locale ar_OM Test
        LocaleContext.setLocale(new Locale.Builder().setLanguage("ar").setRegion("OM").build());
        assertNotNull(MessageHelper.findMessage("label.Jaffa.Admin.LabelEditor.Label", null));
    }

}