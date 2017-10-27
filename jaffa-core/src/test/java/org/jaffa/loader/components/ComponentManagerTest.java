/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2012 JAFFA Development Group
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

package org.jaffa.loader.components;

import org.jaffa.loader.ContextKey;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.MapRepository;
import org.jaffa.presentation.portlet.component.ComponentDefinition;
import org.jaffa.presentation.portlet.component.componentdomain.Component;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the methods of ComponentManager
 * Created by kcassell on 8/8/2017.
 */
public class ComponentManagerTest {
    
    /** The manager being tested. */
    private ComponentManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new ComponentManager();
    }

    @After
    public void tearDown() throws Exception {
        manager = null;
    }

    /**
     * Tests registration of ComponentDefinition objects
     * @throws Exception
     */
    @Test
    public void testRegisterComponentDefinition() throws Exception {
        Component component = new Component();
        String name = "q1";
        component.setId(name);
        ComponentDefinition definition = new ComponentDefinition(component);
        ContextKey key = new ContextKey(name, "components.xml", "DEF", "0-PLATFORM");
        manager.registerComponentDefinition(key, definition);
        ComponentDefinition retrievedDefinition = manager.getComponentDefinition(name);
        assertEquals(definition, retrievedDefinition);
    }

    /**
     * Test that the message repository is retrievable
     * @throws Exception
     */
    @Test
    public void testGetComponentRepository() throws Exception {
        assertNotNull(manager.getComponentRepository());
    }

    /**
     * Tests that a set repository can be retrieved.
     * @throws Exception
     */
    @Test
    public void setComponentRepository() throws Exception {
        IRepository<ComponentDefinition> origRepository =
                manager.getComponentRepository();
        assertNotNull(origRepository);
        IRepository<ComponentDefinition> componentRepository1 =
                new MapRepository<>();
        manager.setComponentRepository(componentRepository1);
        IRepository<ComponentDefinition> retrievedRepository =
                manager.getComponentRepository();
        assertEquals(componentRepository1, retrievedRepository);
        assertNotEquals(origRepository, retrievedRepository);
    }

    /**
     * Tests that getResourceFileName returns an XML file by default.
     * @throws Exception
     */
    @Test
    public void getXmlFileName() throws Exception {
        String xmlFileName = manager.getResourceFileName();
        assertNotNull(xmlFileName);
        assertTrue(xmlFileName.contains(".xml"));
    }

    /**
     * Test the repository name retrieval function
     */
    @Test
    public void testGetName() throws Exception {
        //A componentDefinition must be registered first
        Component component = new Component();
        String name = "q1";
        component.setId(name);
        ComponentDefinition definition = new ComponentDefinition(component);
        ContextKey key = new ContextKey(name, "components.xml", "DEF", "0-PLATFORM");
        manager.registerComponentDefinition(key, definition);

        assertEquals("ComponentDefinition", manager.getComponentRepository().getName());
    }

    /**
     * Tests the ability of this IManager to retrieve a repository when given its String name
    */
     @Test
     public void testGetRepositoryByName() throws Exception {
         //A componentDefinition must be registered first
         Component component = new Component();
         String name = "q1";
         component.setId(name);
         ComponentDefinition definition = new ComponentDefinition(component);
         ContextKey key = new ContextKey(name, "components.xml", "DEF", "0-PLATFORM");
         manager.registerComponentDefinition(key, definition);

         //Negative test
         String repo = "ThieWillBeAnEmptyRepository";
         assertEquals("Empty Repository", manager.getRepositoryByName(repo).getName());

         //Positive test
         repo = "ComponentDefinition";
         assertEquals(repo, manager.getRepositoryByName(repo).getName());
     }

    /**
     * Test the retrieval of the list of repositories managed by this class
    */
    @Test
     public void testGetRepositoryNames() {
        for (String repositoryName : manager.getRepositoryNames()) {
            assertNotNull(manager.getRepositoryByName(repositoryName).getName());
        }
    }
}