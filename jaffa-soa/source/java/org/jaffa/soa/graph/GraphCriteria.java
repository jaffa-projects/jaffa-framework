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
package org.jaffa.soa.graph;

import org.apache.log4j.Logger;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexCriteriaBean;
import org.jaffa.flexfields.IFlexCriteriaFields;
import org.jaffa.persistence.Criteria;

/**
 * The base class for all Graph Criteria Objects for the Root Query.
 * <p>
 * This provides some common properties that are used by all Criteria objects.
 * This includes such things as Max Rows to return and what fields/objects should
 * be included in the resultant domain object graphs.
 * <p>
 * <b>Note: The implementation of the returnQueryClause() method will be different
 * depending on whether this criteria is for the root object of the graph as compared
 * to a criteria for a nested graph object. {@link #returnQueryClause(Criteria)}
 * </b>
 * <p>
 * <b>Note: The properties, maxRecords and firstRecord, have been added to support existing UI
 * utilities. They are mirror images of the properties, objectLimit and objectStart, respectively.
 * </b>
 *
 * @author PaulE
 * @version 1.0
 */
public abstract class GraphCriteria implements IFlexCriteriaFields {

    public static final Logger log = Logger.getLogger(GraphCriteria.class);

    private OrderByField[] orderByFields;
    private Integer objectLimit = 25;
    private Integer objectStart = 0;
    private Boolean findTotalRecords;
    private String[] resultGraphRules;
    private FlexCriteriaBean flexCriteriaBean;
    private Boolean shouldLookupFlexbean = true;

    /**
     * Getter for property orderByFields.
     *
     * @return Value of property orderByFields.
     */
    public OrderByField[] getOrderByFields() {
        return orderByFields;
    }

    /**
     * Setter for property orderByFields.
     *
     * @param orderByFields New value of property orderByFields.
     */
    public void setOrderByFields(OrderByField[] orderByFields) {
        this.orderByFields = orderByFields;
    }

    /**
     * Getter for property objectLimit.
     *
     * @return Value of property objectLimit.
     */
    public Integer getObjectLimit() {
        return this.objectLimit;
    }

    /**
     * Setter for property objectLimit.
     * <p>
     * This is the maximum root level object to be returned by the query. This
     * is implemented as a safe guard to prevent 'runaway' queries that could
     * try and bring back millions of rows.
     * <p>
     * Defaults to 25 objects any number below 1 implies ALL objects.
     *
     * @param objectLimit New value of property objectLimit.
     */
    public void setObjectLimit(Integer objectLimit) {
        this.objectLimit = objectLimit;
    }

    /**
     * Getter for property objectLimit.
     * This property has been added to support existing UI utilties.
     * It is recommended to invoke {@link #getObjectLimit()}.
     *
     * @return Value of property objectLimit.
     */
    public Integer getMaxRecords() {
        return this.getObjectLimit();
    }

    /**
     * Setter for property objectLimit.
     * This property has been added to support existing UI utilties.
     * It is recommended to invoke {@link #setObjectLimit(Integer)}
     *
     * @param objectLimit New value of property objectLimit.
     */
    public void setMaxRecords(Integer objectLimit) {
        this.setObjectLimit(objectLimit);
    }

    /**
     * Getter for property objectStart.
     *
     * @return Value of property objectStart.
     */
    public Integer getObjectStart() {
        return this.objectStart;
    }

    /**
     * Setter for property objectStart.
     * <p>
     * This is the occurence number to start return objects from. This
     * is implemented as a safe mechanism to get "sets" of records with multiple
     * invocations where the initial retrieve has been limited with ObjectLimit
     * <p>
     * Defaults to 0 which is the first occurence. If you got back the default 25 rows,
     * and wanted more it would make sence to set this to 25 on the next invocation.
     *
     * @param objectStart New value of property objectStart.
     */
    public void setObjectStart(Integer objectStart) {
        this.objectStart = objectStart;
    }

    /**
     * Getter for property objectStart.
     * This property has been added to support existing UI utilties.
     * It is recommended to invoke {@link #getObjectStart()}.
     *
     * @return Value of property objectStart.
     */
    public Integer getFirstRecord() {
        return this.getObjectStart();
    }

    /**
     * Setter for property objectStart.
     * This property has been added to support existing UI utilties.
     * It is recommended to invoke {@link #setObjectStart(Integer)}
     *
     * @param objectStart New value of property objectStart.
     */
    public void setFirstRecord(Integer objectStart) {
        this.setObjectStart(objectStart);
    }

    /**
     * Getter for property findTotalRecords.
     *
     * @return Value of property findTotalRecords.
     */
    public Boolean getFindTotalRecords() {
        return findTotalRecords;
    }

    /**
     * Setter for property findTotalRecords.
     *
     * @param findTotalRecords New value of property findTotalRecords.
     */
    public void setFindTotalRecords(Boolean findTotalRecords) {
        this.findTotalRecords = findTotalRecords;
    }

    /**
     * Getter for property resultGraphRules.
     *
     * @return Value of property resultGraphRules.
     */
    public String[] getResultGraphRules() {
        return this.resultGraphRules;
    }

    /**
     * Setter for property resultGraphRules.
     * <p>
     * These rules control what fields and related objects should be included
     * in the returned Domain Object Graph. By default only the top level object,
     * and skeleton related foreign objects are included.
     * <p>
     * See <a href="./package-summary.html#howto">How to control what is returned in a
     * domain object graph</a> for examples of how to use this to control what
     * is returned in the output graph.
     * <p>
     *
     * @param resultGraphRules New value of property resultGraphRules.
     */
    public void setResultGraphRules(String[] resultGraphRules) {
        this.resultGraphRules = resultGraphRules;
    }

    /**
     * Return the real retrieve clause that will be executed for this query.
     * <p>
     * When this is used for a sub-query, a nestedClause will be passed in that
     * will already have the table name set, and have criteria that will limit
     * the query to only retrive child records of the already retrieved parent record.
     * <p>
     * THIS MUST BE OVERRIDDEN BY THE SUPERCLASS
     * <p>
     * The abstract implementation can be used to initially create a Criteria object
     * populated with the default fields on the base class (start, limit, orderBy)
     *
     * @param nestedClause Minimal criteria used to retrieve the nested object. Will be null for the root query.
     * @return return the generated clause
     */
    public Criteria returnQueryClause(Criteria nestedClause) {
        Criteria c = new Criteria();
        // append an orderBy clause to the criteria
        if (getOrderByFields() != null) {
            for (OrderByField orderByField : getOrderByFields()) {
                int sort = Criteria.ORDER_BY_ASC;
                if (orderByField.getSortAscending() != null && !orderByField.getSortAscending())
                    sort = Criteria.ORDER_BY_DESC;
                c.addOrderBy(orderByField.getFieldName(), sort);
            }
        }
        c.setFirstResult(getObjectStart());
        c.setMaxResults(getObjectLimit());

        // FlexField Support:
        // if a flexCriteria bean exists, then allow the flexCritieraBean to participate in the query
        if (flexCriteriaBean != null) {
            if (log.isDebugEnabled()) {
                log.debug("Invoking returnQueryClause() method on the FlexCriteriaBean");
            }
            try {
                c = flexCriteriaBean.returnQueryClause(c);
            } catch (FrameworkException e) {
                // For right now, we can just log this. Changing the method will cause build issues when upstream
                // integrations don't handle the exception.
                log.error("An error occurred while attempting to append to the returnQueryClause by a flexCriteria bean", e);
            }
        }

        return c;
    }

    /**
     * Returns diagnostic information.
     *
     * @return diagnostic information.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        if (getObjectLimit() != null)
            buf.append("<objectLimit>").append(getObjectLimit()).append("</objectLimit>");
        if (getObjectStart() != null)
            buf.append("<objectStart>").append(getObjectStart()).append("</objectStart>");
        if (getFindTotalRecords() != null)
            buf.append("<findTotalRecords>").append(getFindTotalRecords()).append("</findTotalRecords>");
        if (getOrderByFields() != null) {
            for (Object o : getOrderByFields())
                buf.append(o);
        }
        return buf.toString();
    }

    /**
     * Returns a FlexCriteriaBean instance that encapsulates the FlexFields for the persistent object.
     * FlexCriteriaBean instance, once created, is reused during subsequent calls.
     * <p>
     * Note that support for FlexCriteriaBeans is determined by custom aop rules, and that each type of child class may
     * not always support this feature.  Please use isFlexCriteriaAware to check for support before attempting to access
     * these methods.
     *
     * @return a FlexCriteriaBean instance that encapsulates the FlexFields for the persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public FlexCriteriaBean getFlexCriteriaBean() throws ApplicationExceptions, FrameworkException {
        if (shouldLookupFlexbean) {
            shouldLookupFlexbean = false;
            FlexCriteriaBean.configureFlexFields(this);
        }
        return flexCriteriaBean;
    }

    /**
     * Sets a FlexCriteriaBean instance that encapsulates the FlexFields for an implementation class.
     * <p>
     * Note that support for FlexCriteriaBeans is determined by custom aop rules, and that each type of child class may
     * not always support this feature.  Please use isFlexCriteriaAware to check for support before attempting to access
     * these methods.
     *
     * @param flexCriteriaBean a FlexCriteriaBean instance.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException    if any framework error occurs.
     */
    public void setFlexCriteriaBean(FlexCriteriaBean flexCriteriaBean) throws ApplicationExceptions, FrameworkException {
        //copy the flexCriteriaParams from the input to the current instance.
        FlexCriteriaBean currentInstance = getFlexCriteriaBean();
        if (currentInstance != null)
            currentInstance.setFlexCriteriaParams(flexCriteriaBean.getFlexCriteriaParams());
        else
            this.flexCriteriaBean = flexCriteriaBean;
    }
}
