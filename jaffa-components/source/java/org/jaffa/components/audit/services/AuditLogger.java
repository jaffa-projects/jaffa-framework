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
package org.jaffa.components.audit.services;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.jaffa.components.audit.domain.AuditTransaction;
import org.jaffa.components.audit.domain.AuditTransactionField;
import org.jaffa.components.audit.domain.AuditTransactionOverflow;
import org.jaffa.components.audit.domain.AuditTransactionMeta;
import org.jaffa.components.audit.domain.AuditTransactionObject;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.Parser;
import org.jaffa.datatypes.ValidationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.flexfields.FlexBean;
import org.jaffa.flexfields.IFlexFields;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.logging.IPersistenceLoggingPlugin;
import org.jaffa.rules.IObjectRuleIntrospector;
import org.jaffa.rules.RulesEngineFactory;
import org.jaffa.security.SecurityManager;
import org.jaffa.soa.rules.PendingEventException;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;
import org.jaffa.util.StringHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class AuditLogger implements IPersistenceLoggingPlugin {

    private static Logger log = Logger.getLogger(AuditLogger.class);

    private enum ChangeType {

        INSERT,
        UPDATE,
        DELETE
    }
    private UOW m_uow = null;
    private String m_transactionId;
    Pattern eolPattern;

    /** This is called by the Persistence Engine when setting up a UOW. It may be used for initializing resources.
     * @param uow The UOW for a transaction.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void initialize(UOW uow) throws ApplicationExceptions, FrameworkException {
        m_uow = uow;
    }

    /** This is called by the Persistence Engine after performing an actual Insert.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void add(IPersistent object) throws ApplicationExceptions, FrameworkException {
        audit(object, ChangeType.INSERT);
    }

    /** This is called by the Persistence Engine after performing an actual Update.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void update(IPersistent object) throws ApplicationExceptions, FrameworkException {
        audit(object, ChangeType.UPDATE);
    }

    /** This is called by the Persistence Engine after performing an actual Delete.
     * @param object The persistent object.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void delete(IPersistent object) throws ApplicationExceptions, FrameworkException {
        audit(object, ChangeType.DELETE);
    }

    /** This is called by the Persistence Engine just before committing the transaction.
     *
     * NOTE: If an implementation class wants to log the events for a transaction into the persistent store,
     * it may do so by obtaining the UOW from any of the persistent objects that have been logged so far,
     * and then using that UOW to add log data into the persistent store.
     * To persist the logs, the implementation class will need to exlicitly call the flush() method on the UOW.
     *
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void writeLog() throws ApplicationExceptions, FrameworkException {
        // flush the UOW only if any Audit transaction was written
        if (m_transactionId != null)
            m_uow.flush();
    }

    /** This is called by the Persistence Engine no matter what. It may be used for cleaning the resources that may have been acquired/created in the initialize method.
     * @throws ApplicationExceptions if any application error occurs.
     * @throws FrameworkException if any framework error occurs.
     */
    public void clearLog() throws ApplicationExceptions, FrameworkException {
        // do nothing
    }

    private void audit(IPersistent object, ChangeType changeType) throws ApplicationExceptions, FrameworkException {
        IObjectRuleIntrospector introspector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(object);
        Properties auditInfo = introspector.getAuditInfo();
        if (auditInfo != null) {
            // find auditInfo for the various properties
            Map<String, Properties> auditInfoMap = introspector.getAuditInfoForProperties();

            // obtain information for flex fields
            FlexBean flexBean = null;
            IObjectRuleIntrospector flexIntrospector = null;
            Map<String, Properties> auditInfoOfFlexFields = null;
            if (object instanceof IFlexFields) {
                flexBean = ((IFlexFields) object).getFlexBean();
                if (flexBean != null) {
                    flexIntrospector = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(flexBean.getDynaClass().getName(), flexBean);
                    auditInfoOfFlexFields = flexIntrospector.getAuditInfoForProperties();
                }
            }

            // determine if auditing is required
            boolean doAudit = changeType == ChangeType.INSERT || changeType == ChangeType.DELETE;
            if (changeType == ChangeType.UPDATE) {
                // In update-mode, auditing is required only if one of the audit fields has changed
                for (String propertyName : auditInfoMap.keySet()) {
                    if (object.isModified(propertyName) || object.isModified(StringHelper.getUpper1(propertyName))) {
                        doAudit = true;
                        break;
                    }
                }

                // check if flex fields have changed
                if (!doAudit && auditInfoOfFlexFields != null) {
                    for (String propertyName : auditInfoOfFlexFields.keySet()) {
                        if (flexBean.hasChanged(propertyName)) {
                            doAudit = true;
                            break;
                        }
                    }

                }
            }

            if (doAudit) {
                // Raise PendingEventException if reason is required, but is missing in the MDC
                Boolean reasonRequired = Parser.parseBoolean(auditInfo.getProperty("reason-required"));
                if (reasonRequired != null && reasonRequired && MDC.get(AuditTransactionMeta.REASON) == null) {
                    if (log.isDebugEnabled())
                        log.debug("Throwing PendingEventException. 'Reason' needs to be provided in the MDC, since the reason-required attribute has been set to true in the audit rule for " + object.getClass());
                    throw new ApplicationExceptions(new PendingEventException("ReasonRequiredForAuditing"));
                }

                // Create AuditTransaction
                createAuditTransaction();

                // Create AuditTransactionObject
                String objectId = createAuditTransactionObject(auditInfo.getProperty("name"), changeType);

                // Create AuditTransactionField instances
                createAuditTransactionFields(objectId, object, auditInfoMap, flexBean, auditInfoOfFlexFields);
            } else {
                if (log.isDebugEnabled())
                    log.debug("Audit logging not required since none of the auditable fields have changed in " + object);
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("Audit logging not enabled for " + object.getClass());
        }
    }

    /** Creates an AuditTransaction instance, if not already created, and adds it to the UOW. */
    private void createAuditTransaction() throws ApplicationExceptions, FrameworkException {
        if (m_transactionId == null) {
            try {
                AuditTransaction at = new AuditTransaction();
                at.generateKey();
                at.setProcessName((String) MDC.get(AuditTransactionMeta.PROCESS_NAME));
                at.setSubProcessName((String) MDC.get(AuditTransactionMeta.SUB_PROCESS_NAME));
                at.setReason((String) MDC.get(AuditTransactionMeta.REASON));
                if (SecurityManager.getPrincipal() != null && SecurityManager.getPrincipal().getName() != null)
                    at.setCreatedBy(SecurityManager.getPrincipal().getName());
                at.setCreatedOn(new DateTime());
                m_uow.addSpecial(at);
                m_transactionId = at.getTransactionId();
                if (log.isDebugEnabled())
                    log.debug("Created AuditTransaction: " + at);
            } catch (ValidationException e) {
                if (log.isDebugEnabled())
                    log.debug("Exception thrown during creation of AuditTransaction", e);
                throw new ApplicationExceptions(e);
            }
        }
    }

    /** Creates an AuditTransactionObject instance, adds it to the UOW and returns it's id. */
    private String createAuditTransactionObject(String objectName, ChangeType changeType) throws ApplicationExceptions, FrameworkException {
        try {
            AuditTransactionObject ato = new AuditTransactionObject();
            ato.generateKey();
            ato.setTransactionId(m_transactionId);
            ato.setObjectName(objectName);
            ato.setChangeType(changeType == ChangeType.INSERT ? "I" : (changeType == ChangeType.UPDATE ? "U" : "D"));
            m_uow.addSpecial(ato);
            if (log.isDebugEnabled())
                log.debug("Created AuditTransactionObject: " + ato);
            return ato.getObjectId();
        } catch (ValidationException e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown during creation of AuditTransactionObject", e);
            throw new ApplicationExceptions(e);
        }
    }

    /** Create AuditTransactionField instances. */
    private void createAuditTransactionFields(String objectId, IPersistent object, Map<String, Properties> auditInfoMap,
            FlexBean flexBean, Map<String, Properties> auditInfoOfFlexFields) throws RuntimeException, ApplicationExceptions, FrameworkException {
        try {
            for (Map.Entry<String, Properties> me : auditInfoMap.entrySet()) {
                String propertyName = me.getKey();
                Properties auditInfo = me.getValue();
                Boolean recordAlways = Parser.parseBoolean(auditInfo.getProperty("record-always"));
                boolean changed = object.isModified(propertyName) || object.isModified(StringHelper.getUpper1(propertyName));
                if (changed || (recordAlways != null && recordAlways)) {
                    AuditTransactionField atf = new AuditTransactionField();
                    atf.generateKey();
                    atf.setObjectId(objectId);
                    atf.setFieldName(auditInfo.getProperty("name"));
                    String layout = auditInfo.getProperty("layout");
                    String dataFormat = auditInfo.getProperty("data-format");
                    String toValue = Formatter.format(BeanHelper.getField(object, propertyName), layout);
                    String fromValue = null;
                    if (changed) {
                        Object initialValue = object.returnInitialValue(propertyName);
                        if (initialValue == null)
                            initialValue = object.returnInitialValue(StringHelper.getUpper1(propertyName));
                        fromValue = Formatter.format(initialValue, layout);
                    } else {
                        fromValue = toValue;
                    }
                    if ("html".equals(dataFormat)) {
                        createAuditTransactionOverflow(atf, fromValue, toValue);
                        atf.setOverflowFlag(Boolean.TRUE);
                        atf.setMultilineHtmlFlag("H");
                        if (fromValue != null) {
                            fromValue = trimValue(dataFormat, fromValue);
                        }
                        if (toValue != null) {
                            toValue = trimValue(dataFormat, toValue);
                        }
                    } else if ("multiline".equals(dataFormat)) {
                        atf.setMultilineHtmlFlag("M");
                        if ((fromValue != null && fromValue.length() > 2000) || (toValue != null && toValue.length() > 2000)) {
                            createAuditTransactionOverflow(atf, fromValue, toValue);
                            atf.setOverflowFlag(Boolean.TRUE);
                            if (fromValue != null) {
                                fromValue = trimValue(dataFormat, fromValue);
                            }
                            if (toValue != null) {
                                toValue = trimValue(dataFormat, toValue);
                            }
                        }
                    }
                    atf.setFromValue(fromValue);
                    atf.setToValue(toValue);
                    atf.setChanged(changed);
                    atf.setFlex(Boolean.FALSE);
                    m_uow.addSpecial(atf);
                    if (log.isDebugEnabled())
                        log.debug("Created AuditTransactionField: " + atf);
                }
            }

            if (flexBean != null && auditInfoOfFlexFields != null) {
                for (Map.Entry<String, Properties> me : auditInfoOfFlexFields.entrySet()) {
                    String propertyName = me.getKey();
                    Properties auditInfo = me.getValue();
                    Boolean recordAlways = Parser.parseBoolean(auditInfo.getProperty("record-always"));
                    boolean changed = flexBean.hasChanged(propertyName);
                    if (changed || (recordAlways != null && recordAlways)) {
                        AuditTransactionField atf = new AuditTransactionField();
                        atf.generateKey();
                        atf.setObjectId(objectId);
                        atf.setFieldName(auditInfo.getProperty("name"));
                        String layout = auditInfo.getProperty("layout");
                        atf.setToValue(Formatter.format(BeanHelper.getField(flexBean, propertyName), layout));
                        if (changed) {
                            Object fromValue = flexBean.getOriginalValue(propertyName);
                            atf.setFromValue(Formatter.format(fromValue, layout));
                        } else
                            atf.setFromValue(atf.getToValue());
                        atf.setChanged(changed);
                        atf.setFlex(Boolean.TRUE);
                        m_uow.addSpecial(atf);
                        if (log.isDebugEnabled())
                            log.debug("Created AuditTransactionField: " + atf);
                    }
                }
            }
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown during creation of AuditTransactionField", e);
            throw ExceptionHelper.throwAFR(e);
        }
    }

    /** Create AuditTransactionOverflow instances. */
    private void createAuditTransactionOverflow(AuditTransactionField atf, String fromValue, String toValue) throws RuntimeException, ApplicationExceptions, FrameworkException {
        try {
            AuditTransactionOverflow ato = new AuditTransactionOverflow();
            ato.setFieldId(atf.getFieldId());
            ato.setObjectId(atf.getObjectId());
            ato.setFieldName(atf.getFieldName());
            ato.setFromValue(fromValue);
            ato.setToValue(toValue);
            m_uow.addSpecial(ato);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("Exception thrown during creation of AuditTransactionOverflow", e);
            throw ExceptionHelper.throwAFR(e);
        }
    }

    private String trimValue(String dataFormat, String input) {
        String cleaned = null;
        if ("html".equals(dataFormat)) {
            Document document = Jsoup.parse(input);
            if (document != null) {
                document.outputSettings(new Document.OutputSettings().prettyPrint(false));
                document.select("br").append("\\n");
                String s = org.jsoup.parser.Parser.unescapeEntities(Jsoup.clean(document.html().replaceAll("\\\\n", " "), "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false)), false);
                if (s != null) {
                    cleaned = s.trim().replaceAll("\r", "").replaceAll("\n","");
                }
            }
        } else {
            cleaned = input.trim();
            if (this.eolPattern == null) {
                this.eolPattern = Pattern.compile("[\r|\n]");
            }
            Matcher m = this.eolPattern.matcher(cleaned);
            if (m.find()) {
                cleaned = cleaned.subSequence(0, m.start()).toString();
            }
        }
        return cleaned != null ? (cleaned.length() > 100 ? cleaned.substring(0, 100) : cleaned) : (input.length() > 100 ? input.substring(0, 100) : input);
    }

}
