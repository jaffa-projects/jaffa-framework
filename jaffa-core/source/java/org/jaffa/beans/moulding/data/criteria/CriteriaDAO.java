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

/*
 * CriteriaDAO.java
 *
 * Created on February 12, 2004, 11:39 AM
 */

package org.jaffa.beans.moulding.data.criteria;

import org.jaffa.persistence.Criteria;

/** The base class for all Criteria Data Access Objects.
 *<p>
 * This provides some common properties that are used by all Criteria objects.
 * This includes such things as Max Rows to return and what fields/objects should
 * be included in the resultant domain object graphs.
 *
 * @author  PaulE
 * @version 1.0
 */
public abstract class CriteriaDAO {

    /** Holds value of property objectLimit. */
    private Integer objectLimit = new Integer(25);

    /** Holds value of property resultGraphRules. */
    private String[] resultGraphRules;


    /** Creates a new instance of CriteriaDAO */
    public CriteriaDAO() {
    }

    /** Getter for property objectLimit.
     * @return Value of property objectLimit.
     *
     */
    public Integer getObjectLimit() {
        return this.objectLimit;
    }

    /** Setter for property objectLimit.
     * <p>
     * This is the maximum root level object to be returned by the query. This
     * is implemented as a safe guard to prevent 'runaway' queries that could
     * try and bring back millions of rows.
     * <p>
     * Defaults to 25 objects any number below 1 implies ALL objects.
     * @param objectLimit New value of property objectLimit.
     *
     */
    public void setObjectLimit(Integer objectLimit) {
        this.objectLimit = objectLimit;
    }

    /**
     * Return the real retrieve clause that will be executed for this query.
     * <p>
     * THIS MUST BE OVERRIDDEN BY THE SUPERCLASS
     * @return return the generated clause
     */
    public abstract Criteria returnQueryClause();


    /** Getter for property resultGraphRules.
     * @return Value of property resultGraphRules.
     *
     */
    public String[] getResultGraphRules() {
        return this.resultGraphRules;
    }


    /** Setter for property resultGraphRules.
     *<p>
     * These rules control what fields and related objects should be included
     * in the returned Domain Object Graph. By default only the top level object,
     * and skeleton related foreign objects are included.
     *<p>
     * See <a href="./package-summary.html#howto">How to control what is returned in a
     * domain object graph</a> for examples of how to use this to control what
     * is returned in the output graph.
     *<p>
     * @param resultGraphRules New value of property resultGraphRules.
     *
     */
    public void setResultGraphRules(String[] resultGraphRules) {
        this.resultGraphRules = resultGraphRules;
    }

}
