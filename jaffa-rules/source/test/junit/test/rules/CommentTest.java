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

package test.rules;

import junit.framework.TestCase;
import org.jaffa.rules.IPropertyRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;

/**
 *
 * @author PaulE
 */
public class CommentTest extends TestCase {

    /** Creates new CommentTest
     * @param name The name of the test case.
     */
    public CommentTest(String name) {
        super(name);
    }
    
    public void testPlain() {
        try {
            CommentBusinessBean obj = new CommentBusinessBean();
            obj.setField1("initial-comments");
            obj.validate();
            assertEquals("initial-comments", obj.getField1());
            
            obj.setField1("new-comments");
            obj.validate();
            assertEquals("new-comments", obj.getField1());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testLifo() {
        try {
            CommentBusinessBean obj = new CommentBusinessBean();
            obj.setField2("initial-comments");
            obj.validate();
            assertTrue("initial-comments should have been added with a stamp", obj.getField2().matches("^------- Comments .+ -------\ninitial-comments$"));

            obj.clearChanges();
            obj.setField2("new-comments");
            obj.validate();
            assertTrue("new-comments should have been added before the old-comments", obj.getField2().matches("^------- Additional Comments .+ -------\nnew-comments\n\n------- Comments .+ -------\ninitial-comments$"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testFifo() {
        try {
            CommentBusinessBean obj = new CommentBusinessBean();
            obj.setField3("initial-comments");
            obj.validate();
            assertTrue("initial-comments should have been added with a stamp", obj.getField3().matches("^------- Comments .+ -------\ninitial-comments$"));
            
            obj.clearChanges();
            obj.setField3("new-comments");
            obj.validate();
            assertTrue("new-comments should have been added before the old-comments", obj.getField3().matches("^------- Comments .+ -------\ninitial-comments\n\n------- Additional Comments .+ -------\nnew-comments$"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
    public void testCommentOnNonBusinessRealm() {
        try {
            // There should be no conversion on a bean defined in a non-business realm
            CommentBean obj = new CommentBean();
            obj.setField1("f1");
            obj.setField2("f2");
            obj.setField3("f3");
            obj.validate();
            assertEquals("f1", obj.getField1());
            assertEquals("f2", obj.getField2());
            assertEquals("f3", obj.getField3());
            
            obj.clearChanges();
            obj.setField1("new1");
            obj.setField2("new2");
            obj.setField3("new3");
            obj.validate();
            assertEquals("new1", obj.getField1());
            assertEquals("new2", obj.getField2());
            assertEquals("new3", obj.getField3());
            
            // The comment-style should however be available through the Introspector
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj);
            assertEquals("plain", w.getCommentStyle());
            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field2", obj);
            assertEquals("lifo", w.getCommentStyle());
            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj);
            assertEquals("fifo", w.getCommentStyle());
            
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    
}
