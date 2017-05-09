/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2015 JAFFA Development Group
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
package org.jaffa.rules.meta;

import org.jaffa.rules.TestModel;
import org.jaffa.rules.TestSupport;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;

/**
 * The meta-data repository should be populatable from code configuration.
 */
public class PropertyMetaDataTest extends TestSupport {
    // By extending TestSupport, intentionally wait for repositories to be configured.

    /**
     * Demonstrate configuration as a chain of method calls.
     */
//    @Test
//    public void testMethodChain() {
//        PropertyMetaData target = new PropertyMetaData();
//        target.setClassMetaData(new ClassMetaData());
//        target.label("label-value", "label-cond");
//
//        List<RuleMetaData> rules = target.getRules(TestModel.class.getName());
//        assertNotNull(rules);
//        assertEquals(1, rules.size());
//        assertContains(rules, "label", "label-value", "label-cond");
//    }

    /**
     * Formatting a label means creating a new label using the class format.
     */
    @Test
    public void testFormatLabel() {
        ClassMetaData classMetaData = new ClassMetaData("x");
        classMetaData.setLabelFormat("[xyz.%s]");
        PropertyMetaData target = new PropertyMetaData();
        target.setName("abc");
        target.setClassMetaData(classMetaData);

        target.formatLabel();

        List<RuleMetaData> rules = target.getRules(TestModel.class.getName());
        assertNotNull(rules);
        assertEquals(1, rules.size());
        assertContains(rules, "label", "[xyz.Abc]", null);
    }

    /**
     * Asserts that the supplied list of rules contains specific parameterized rule.
     *
     * @param rules     the rules.
     * @param name      the name of the rule.
     * @param value     the mandatory value.
     * @param condition the optional condition.
     */
    private void assertContains(List<RuleMetaData> rules, String name, String value, String condition) {
        for (RuleMetaData rule : rules) {
            if (name.equals(rule.getName())) {
                assertEquals(value, rule.getParameter(RuleMetaData.PARAMETER_VALUE));
                if (condition != null) {
                    assertEquals(condition, rule.getParameter(RuleMetaData.PARAMETER_CONDITION));
                }
                return;
            }
        }
        fail("Rules did not contain " + name);
    }
}
