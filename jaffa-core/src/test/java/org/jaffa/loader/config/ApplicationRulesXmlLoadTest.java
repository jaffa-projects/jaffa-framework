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

package org.jaffa.loader.config;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.CoreLoaderConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * ApplicationRulesXmlLoadTest - Verifies that we can load a roles.xml file into the repository and use those roles to
 * verify to create, read, update, and delete roles from the repository.
 */
public class ApplicationRulesXmlLoadTest {

    private static AnnotationConfigApplicationContext xmlLoaderConfig = new AnnotationConfigApplicationContext(CoreLoaderConfig.class);

    /**
     * Verifies that we can load the rules from the ApplicationRules_*.properties file into our rule repository.
     */
    @Test
    public void testXmlLoad() {
        ApplicationRulesManager applicationRulesManager = xmlLoaderConfig.getBean(ApplicationRulesManager.class);
        assertNotNull(applicationRulesManager.getApplicationRulesRepository());
        Properties properties = applicationRulesManager.getApplicationRulesRepository().getAllValues().get(0);
        assertFalse(properties.isEmpty());
    }

    /**
     * testApplicationRulesRegistration - Verifies that the ApplicationRules_*.properties has been loaded correctly
     * into the NavigationManager.
     */
    @Test
    public void testApplicationRulesRegistration() {
        ApplicationRulesManager navigationManager = xmlLoaderConfig.getBean(ApplicationRulesManager.class);
        ContextKey key = new ContextKey("BNG", "Application_Rules", "DEF", "100-Highest");
        assertNull(navigationManager.getApplicationRulesRepository().query(key));
        Properties properties = new Properties();
        properties.setProperty("test", "testValue");
        properties.setProperty("test2", "testValue2");

        navigationManager.registerProperties(key, properties);
        assertNotNull(navigationManager.getApplicationRulesRepository().query(key));
        navigationManager.unregisterProperties(key);
        assertNull(navigationManager.getApplicationRulesRepository().query(key));
    }

    /**
     * Tests the ability of this IManager to retrieve a repository when given its String name
     */
    @Test
    public void testGetRepositoryByName() throws Exception {
        ApplicationRulesManager applicationRulesManager = xmlLoaderConfig.getBean(ApplicationRulesManager.class);

        String repo = "Properties";
        assertEquals(repo, applicationRulesManager.getRepositoryByName(repo).getName());
    }

    /**
     * Test the retrieval of the list of repositories managed by this class
     */
    @Test
    public void testGetRepositoryNames() {
        ApplicationRulesManager applicationRulesManager = xmlLoaderConfig.getBean(ApplicationRulesManager.class);
        for (Object repositoryName : applicationRulesManager.getRepositoryNames()) {
            assertEquals("Properties", applicationRulesManager.getRepositoryByName((String) repositoryName).getName());
        }
    }
}
