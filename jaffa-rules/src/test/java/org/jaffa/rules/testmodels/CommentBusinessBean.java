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

package org.jaffa.rules.testmodels;

import java.util.HashMap;
import java.util.Map;

public class CommentBusinessBean {
    private Map<String, String> changes = new HashMap<>();
    private String field1 = null;
    private String field2 = null;
    private String field3 = null;

    public String getField1() {
        return field1;
    }

    public void setField1(String f) {
        if (!changes.containsKey("field1")) changes.put("field1", field1);
        field1 = f;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String f) {
        if (!changes.containsKey("field2")) changes.put("field2", field2);
        field2 = f;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String f) {
        if (!changes.containsKey("field3")) changes.put("field3", field3);
        field3 = f;
    }

    public String xgetField3() {
        return field3;
    }

    /**
     * Clear all the changes on this bean. Will cause all future calls to
     * {@link #hasChanged(String)} to return false
     */
    public void clearChanges() {
        changes.clear();
    }

    /**
     * Has the bean changed since it was created or last cleared.
     *
     * @return true if the bean has been modified
     */
    public boolean hasChanged() {
        return changes != null && changes.size() > 0;
    }

    /**
     * Has the specified bean property been changed since the bean was
     * created or last cleared
     *
     * @param property Name of bean property to check
     * @return true if the property has been modified
     */
    public boolean hasChanged(String property) {
        return changes.containsKey(property);
    }

    /**
     * Get the original value for this field.
     *
     * @param property Name of bean property to check
     * @return The object representing the original values. Primitives are return as their
     * Object counterparts.
     */
    public Object getOriginalValue(String property) {
        return changes.get(property);
    }

    /**
     * This method is used for binding various validation interceptors.
     */
    public void validate() {
    }
}
