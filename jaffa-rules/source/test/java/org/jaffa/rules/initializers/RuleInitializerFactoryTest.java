/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2016 JAFFA Development Group
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

package org.jaffa.rules.initializers;

import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.FakeModel;
import org.jaffa.rules.JaffaRulesFrameworkException;
import org.jaffa.rules.TestConfig;
import org.jaffa.rules.TestModel;
import org.jaffa.rules.meta.ClassMetaData;
import org.jaffa.rules.realm.Realm;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.security.VariationContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * The rule initializer factory should construct and cache new initializers based on meta-data rules.
 */
public class RuleInitializerFactoryTest {
    private RuleInitializerFactory target;

    /**
     * Setup common data.
     */
    @Before
    public void before() {
        VariationContext.setVariation("A");

        Realm realm = new Realm("realm");
        realm.setSource("Test");
        realm.regex(TestModel.class.getName());
        realm.register();

        ClassMetaData data = new ClassMetaData(TestModel.class);
        data.property("field1").initialize(FieldInitializerTest.FIELD1_INIT_VALUE);
        data.property("field2").initialize(FieldInitializerTest.FIELD2_INIT_VALUE.toString());
        data.setExecutionRealm(realm.getName());
        data.setVariation(VariationContext.getVariation());
        data.register();

        new TestConfig().initializeRule();

        target = new RuleInitializerFactory();
    }

    /**
     * An empty initializer should be returned for classes that have no "initialize" rules defined.
     */
    @Test
    public void testNoInitializer() {
        assertNotNull(target.getInitializer(new Object()));
    }

    /**
     * If initialize rules are present for an object, an initializer should be returned.
     */
    @Test
    public void testInitializer() throws FrameworkException, ApplicationException {
        TestModel model = new TestModel();
        Initializer<TestModel> initializer = target.getInitializer(model);

        initializer.initialize(model);

        assertEquals(FieldInitializerTest.FIELD1_INIT_VALUE, model.getField1());
    }

    /**
     * Multiple hits to the same class/realm/variation should return the same instance.
     */
    @Test
    public void testCaching() {
        Initializer<TestModel> initializer1 = target.getInitializer(new TestModel());
        Initializer<TestModel> initializer2 = target.getInitializer(new TestModel());
        assertSame(initializer1, initializer2);
    }

    /**
     * Hits with different classes should return different instances.
     */
    @Test
    public void testDifferent() throws JaffaRulesFrameworkException {
        Initializer<TestModel> initializer1 = target.getInitializer(new TestModel());
        Initializer<FakeModel> initializer2 = target.getInitializer(new FakeModel());
        assertNotSame(initializer1, initializer2);
    }

    /**
     * Hits with different variations should return different instances.
     */
    @Test
    public void testVariation() throws JaffaRulesFrameworkException {
        Initializer<TestModel> initializer1 = target.getInitializer(new TestModel());
        VariationContext.setVariation("B");
        Initializer<TestModel> initializer2 = target.getInitializer(new TestModel());

        assertNotSame(initializer1, initializer2);
    }
}
