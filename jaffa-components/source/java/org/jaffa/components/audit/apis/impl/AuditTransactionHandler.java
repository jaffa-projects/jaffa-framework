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
package org.jaffa.components.audit.apis.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jaffa.components.audit.apis.data.AuditTransactionFieldGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionObjectGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewGraph;
import org.jaffa.components.audit.apis.data.AuditTransactionViewQueryResponse;
import org.jaffa.components.audit.apis.helper.AuditTransactionHelper;
import org.jaffa.components.audit.domain.AuditTransactionField;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.UOW;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.soa.dataaccess.MappingFilter;
import org.jaffa.soa.dataaccess.TransformationHandler;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.util.MessageHelper;

/**
 * A handler for the AuditTransactionService.
 *
 * @author GautamJ
 */
public class AuditTransactionHandler extends TransformationHandler {

    private AuditTransactionHelper auditTransactionHelper = new AuditTransactionHelper();


    /**
     * Creates an instance.
     */
    public AuditTransactionHandler(UOW uow) {
    }

    /**
     * Called after a bean has been loaded.
     * @param path This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the domain object that was just queried.
     * @param target the graph object being molded.
     * @param filter Filter object that it is used to control what fields are populated or the target objects.
     * @param originalCriteria the original graph criteria.
     * @throws ApplicationException if any Application error occurs.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    @Override
    public void endBeanLoad(String path, Object source, Object target, MappingFilter filter, GraphCriteria originalCriteria) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if (target instanceof AuditTransactionObjectGraph) {
            AuditTransactionObjectGraph t = (AuditTransactionObjectGraph) target;
            t.setObjectLabel(auditTransactionHelper.findLabel(t.getObjectName()));
            auditTransactionHelper.removeHiddenFields(t);
            t.clearChanges();
        } else if (source instanceof AuditTransactionField && target instanceof AuditTransactionFieldGraph) {
            AuditTransactionField s = (AuditTransactionField) source;
            AuditTransactionFieldGraph t = (AuditTransactionFieldGraph) target;
            t.setFieldLabel(auditTransactionHelper.findLabel(s.getAuditTransactionObjectObject().getObjectName(), s.getFieldName()));
            t.clearChanges();
        } else if (target instanceof AuditTransactionViewGraph) {
            AuditTransactionViewGraph t = (AuditTransactionViewGraph) target;
            t.setFieldLabel(auditTransactionHelper.findLabel(t.getObjectName(), t.getFieldName()));
            t.setObjectLabel(auditTransactionHelper.findLabel(t.getObjectName()));
            t.clearChanges();
            if ("M".equals(t.getMultilineHtmlFlag())) {
                if (t.getFromValue() != null) {
                    t.setFromValue(auditTransactionHelper.trimValue(t.getFromValue()));
                }
                if (t.getToValue() != null) {
                    t.setToValue(auditTransactionHelper.trimValue(t.getToValue()));
                }
            }
        }
    }


}
