// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogfinder.ui;

import java.util.*;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.components.finder.*;
import org.jaffa.components.maint.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;

import org.jaffa.modules.messaging.components.businesseventlogfinder.IBusinessEventLogFinder;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderInDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;


import org.jaffa.modules.messaging.components.businesseventlogviewer.ui.BusinessEventLogViewerComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the BusinessEventLogFinder.
 */
public class BusinessEventLogFinderComponent extends FinderComponent2 {

    private static Logger log = Logger.getLogger(BusinessEventLogFinderComponent.class);

    private String m_logId = null;
    private String m_logIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_correlationType = null;
    private String m_correlationTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_correlationKey1 = null;
    private String m_correlationKey1Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_correlationKey2 = null;
    private String m_correlationKey2Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_correlationKey3 = null;
    private String m_correlationKey3Dd = CriteriaField.RELATIONAL_EQUALS;
    private String m_scheduledTaskId = null;
    private String m_scheduledTaskIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_messageId = null;
    private String m_messageIdDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_loggedOn = null;
    private String m_loggedOnDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_loggedBy = null;
    private String m_loggedByDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_processName = null;
    private String m_processNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_subProcessName = null;
    private String m_subProcessNameDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_messageType = null;
    private String m_messageTypeDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_messageText = null;
    private String m_messageTextDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_sourceClass = null;
    private String m_sourceClassDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_sourceMethod = null;
    private String m_sourceMethodDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_sourceLine = null;
    private String m_sourceLineDd = CriteriaField.RELATIONAL_EQUALS;
    private String m_stackTrace = null;
    private String m_stackTraceDd = CriteriaField.RELATIONAL_EQUALS;

    private IBusinessEventLogFinder m_tx = null;

    public BusinessEventLogFinderComponent() {
        super();
        super.setSortDropDown("LoggedOn desc");
    }

    /** Returns the Struts GlobalForward for the Criteria screen.
     * @return the Struts GlobalForward for the Criteria screen.
     */
    protected String getCriteriaFormName() {
        return "jaffa_messaging_businessEventLogFinderCriteria";
    }
    
    /** Returns the Struts GlobalForward for the Results screen.
     * @return the Struts GlobalForward for the Results screen.
     */
    protected String getResultsFormName() {
        return "jaffa_messaging_businessEventLogFinderResults";
    }
    
    /** Returns the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     * @return the Struts GlobalForward for the ConsolidatedCriteriaAndResults screen.
     */
    protected String getConsolidatedCriteriaAndResultsFormName() {
        return "jaffa_messaging_businessEventLogFinderConsolidatedCriteriaAndResults";
    }

    /** Returns the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     * @return the Struts GlobalForward for the screen displaying the results as an Excel spreadsheet.
     */
    protected String getExcelFormName() {
        return "jaffa_messaging_businessEventLogFinderExcelResults";
    }
    
    /** Returns the Struts GlobalForward for the screen displaying the results in XML format.
     * @return the Struts GlobalForward for the screen displaying the results in XML format.
     */
    protected String getXmlFormName() {
        return "jaffa_messaging_businessEventLogFinderXmlResults";
    }

    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method //GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }

        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:logId_1_be
    /** Getter for property logId.
     * @return Value of property logId.
     */
    public String getLogId() {
        return m_logId;
    }

    /** Setter for property logId.
     * @param logId New value of property logId.
     */
    public void setLogId(String logId) {
        m_logId = logId;
    }

    /** Getter for property logIdDd.
     * @return Value of property logIdDd.
     */
    public String getLogIdDd() {
        return m_logIdDd;
    }

    /** Setter for property logIdDd.
     * @param logIdDd New value of property logIdDd.
     */
    public void setLogIdDd(String logIdDd) {
        m_logIdDd = logIdDd;
    }

    // .//GEN-END:logId_1_be
    // .//GEN-BEGIN:correlationType_1_be
    /** Getter for property correlationType.
     * @return Value of property correlationType.
     */
    public String getCorrelationType() {
        return m_correlationType;
    }

    /** Setter for property correlationType.
     * @param correlationType New value of property correlationType.
     */
    public void setCorrelationType(String correlationType) {
        m_correlationType = correlationType;
    }

    /** Getter for property correlationTypeDd.
     * @return Value of property correlationTypeDd.
     */
    public String getCorrelationTypeDd() {
        return m_correlationTypeDd;
    }

    /** Setter for property correlationTypeDd.
     * @param correlationTypeDd New value of property correlationTypeDd.
     */
    public void setCorrelationTypeDd(String correlationTypeDd) {
        m_correlationTypeDd = correlationTypeDd;
    }

    // .//GEN-END:correlationType_1_be
    // .//GEN-BEGIN:correlationKey1_1_be
    /** Getter for property correlationKey1.
     * @return Value of property correlationKey1.
     */
    public String getCorrelationKey1() {
        return m_correlationKey1;
    }

    /** Setter for property correlationKey1.
     * @param correlationKey1 New value of property correlationKey1.
     */
    public void setCorrelationKey1(String correlationKey1) {
        m_correlationKey1 = correlationKey1;
    }

    /** Getter for property correlationKey1Dd.
     * @return Value of property correlationKey1Dd.
     */
    public String getCorrelationKey1Dd() {
        return m_correlationKey1Dd;
    }

    /** Setter for property correlationKey1Dd.
     * @param correlationKey1Dd New value of property correlationKey1Dd.
     */
    public void setCorrelationKey1Dd(String correlationKey1Dd) {
        m_correlationKey1Dd = correlationKey1Dd;
    }

    // .//GEN-END:correlationKey1_1_be
    // .//GEN-BEGIN:correlationKey2_1_be
    /** Getter for property correlationKey2.
     * @return Value of property correlationKey2.
     */
    public String getCorrelationKey2() {
        return m_correlationKey2;
    }

    /** Setter for property correlationKey2.
     * @param correlationKey2 New value of property correlationKey2.
     */
    public void setCorrelationKey2(String correlationKey2) {
        m_correlationKey2 = correlationKey2;
    }

    /** Getter for property correlationKey2Dd.
     * @return Value of property correlationKey2Dd.
     */
    public String getCorrelationKey2Dd() {
        return m_correlationKey2Dd;
    }

    /** Setter for property correlationKey2Dd.
     * @param correlationKey2Dd New value of property correlationKey2Dd.
     */
    public void setCorrelationKey2Dd(String correlationKey2Dd) {
        m_correlationKey2Dd = correlationKey2Dd;
    }

    // .//GEN-END:correlationKey2_1_be
    // .//GEN-BEGIN:correlationKey3_1_be
    /** Getter for property correlationKey3.
     * @return Value of property correlationKey3.
     */
    public String getCorrelationKey3() {
        return m_correlationKey3;
    }

    /** Setter for property correlationKey3.
     * @param correlationKey3 New value of property correlationKey3.
     */
    public void setCorrelationKey3(String correlationKey3) {
        m_correlationKey3 = correlationKey3;
    }

    /** Getter for property correlationKey3Dd.
     * @return Value of property correlationKey3Dd.
     */
    public String getCorrelationKey3Dd() {
        return m_correlationKey3Dd;
    }

    /** Setter for property correlationKey3Dd.
     * @param correlationKey3Dd New value of property correlationKey3Dd.
     */
    public void setCorrelationKey3Dd(String correlationKey3Dd) {
        m_correlationKey3Dd = correlationKey3Dd;
    }

    // .//GEN-END:correlationKey3_1_be
    // .//GEN-BEGIN:scheduledTaskId_1_be
    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public String getScheduledTaskId() {
        return m_scheduledTaskId;
    }

    /** Setter for property scheduledTaskId.
     * @param scheduledTaskId New value of property scheduledTaskId.
     */
    public void setScheduledTaskId(String scheduledTaskId) {
        m_scheduledTaskId = scheduledTaskId;
    }

    /** Getter for property scheduledTaskIdDd.
     * @return Value of property scheduledTaskIdDd.
     */
    public String getScheduledTaskIdDd() {
        return m_scheduledTaskIdDd;
    }

    /** Setter for property scheduledTaskIdDd.
     * @param scheduledTaskIdDd New value of property scheduledTaskIdDd.
     */
    public void setScheduledTaskIdDd(String scheduledTaskIdDd) {
        m_scheduledTaskIdDd = scheduledTaskIdDd;
    }

    // .//GEN-END:scheduledTaskId_1_be
    // .//GEN-BEGIN:messageId_1_be
    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public String getMessageId() {
        return m_messageId;
    }

    /** Setter for property messageId.
     * @param messageId New value of property messageId.
     */
    public void setMessageId(String messageId) {
        m_messageId = messageId;
    }

    /** Getter for property messageIdDd.
     * @return Value of property messageIdDd.
     */
    public String getMessageIdDd() {
        return m_messageIdDd;
    }

    /** Setter for property messageIdDd.
     * @param messageIdDd New value of property messageIdDd.
     */
    public void setMessageIdDd(String messageIdDd) {
        m_messageIdDd = messageIdDd;
    }

    // .//GEN-END:messageId_1_be
    // .//GEN-BEGIN:loggedOn_1_be
    /** Getter for property loggedOn.
     * @return Value of property loggedOn.
     */
    public String getLoggedOn() {
        return m_loggedOn;
    }

    /** Setter for property loggedOn.
     * @param loggedOn New value of property loggedOn.
     */
    public void setLoggedOn(String loggedOn) {
        m_loggedOn = loggedOn;
    }

    /** Getter for property loggedOnDd.
     * @return Value of property loggedOnDd.
     */
    public String getLoggedOnDd() {
        return m_loggedOnDd;
    }

    /** Setter for property loggedOnDd.
     * @param loggedOnDd New value of property loggedOnDd.
     */
    public void setLoggedOnDd(String loggedOnDd) {
        m_loggedOnDd = loggedOnDd;
    }

    // .//GEN-END:loggedOn_1_be
    // .//GEN-BEGIN:loggedBy_1_be
    /** Getter for property loggedBy.
     * @return Value of property loggedBy.
     */
    public String getLoggedBy() {
        return m_loggedBy;
    }

    /** Setter for property loggedBy.
     * @param loggedBy New value of property loggedBy.
     */
    public void setLoggedBy(String loggedBy) {
        m_loggedBy = loggedBy;
    }

    /** Getter for property loggedByDd.
     * @return Value of property loggedByDd.
     */
    public String getLoggedByDd() {
        return m_loggedByDd;
    }

    /** Setter for property loggedByDd.
     * @param loggedByDd New value of property loggedByDd.
     */
    public void setLoggedByDd(String loggedByDd) {
        m_loggedByDd = loggedByDd;
    }

    // .//GEN-END:loggedBy_1_be
    // .//GEN-BEGIN:processName_1_be
    /** Getter for property processName.
     * @return Value of property processName.
     */
    public String getProcessName() {
        return m_processName;
    }

    /** Setter for property processName.
     * @param processName New value of property processName.
     */
    public void setProcessName(String processName) {
        m_processName = processName;
    }

    /** Getter for property processNameDd.
     * @return Value of property processNameDd.
     */
    public String getProcessNameDd() {
        return m_processNameDd;
    }

    /** Setter for property processNameDd.
     * @param processNameDd New value of property processNameDd.
     */
    public void setProcessNameDd(String processNameDd) {
        m_processNameDd = processNameDd;
    }

    // .//GEN-END:processName_1_be
    // .//GEN-BEGIN:subProcessName_1_be
    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public String getSubProcessName() {
        return m_subProcessName;
    }

    /** Setter for property subProcessName.
     * @param subProcessName New value of property subProcessName.
     */
    public void setSubProcessName(String subProcessName) {
        m_subProcessName = subProcessName;
    }

    /** Getter for property subProcessNameDd.
     * @return Value of property subProcessNameDd.
     */
    public String getSubProcessNameDd() {
        return m_subProcessNameDd;
    }

    /** Setter for property subProcessNameDd.
     * @param subProcessNameDd New value of property subProcessNameDd.
     */
    public void setSubProcessNameDd(String subProcessNameDd) {
        m_subProcessNameDd = subProcessNameDd;
    }

    // .//GEN-END:subProcessName_1_be
    // .//GEN-BEGIN:messageType_1_be
    /** Getter for property messageType.
     * @return Value of property messageType.
     */
    public String getMessageType() {
        return m_messageType;
    }

    /** Setter for property messageType.
     * @param messageType New value of property messageType.
     */
    public void setMessageType(String messageType) {
        m_messageType = messageType;
    }

    /** Getter for property messageTypeDd.
     * @return Value of property messageTypeDd.
     */
    public String getMessageTypeDd() {
        return m_messageTypeDd;
    }

    /** Setter for property messageTypeDd.
     * @param messageTypeDd New value of property messageTypeDd.
     */
    public void setMessageTypeDd(String messageTypeDd) {
        m_messageTypeDd = messageTypeDd;
    }

    // .//GEN-END:messageType_1_be
    // .//GEN-BEGIN:messageText_1_be
    /** Getter for property messageText.
     * @return Value of property messageText.
     */
    public String getMessageText() {
        return m_messageText;
    }

    /** Setter for property messageText.
     * @param messageText New value of property messageText.
     */
    public void setMessageText(String messageText) {
        m_messageText = messageText;
    }

    /** Getter for property messageTextDd.
     * @return Value of property messageTextDd.
     */
    public String getMessageTextDd() {
        return m_messageTextDd;
    }

    /** Setter for property messageTextDd.
     * @param messageTextDd New value of property messageTextDd.
     */
    public void setMessageTextDd(String messageTextDd) {
        m_messageTextDd = messageTextDd;
    }

    // .//GEN-END:messageText_1_be
    // .//GEN-BEGIN:sourceClass_1_be
    /** Getter for property sourceClass.
     * @return Value of property sourceClass.
     */
    public String getSourceClass() {
        return m_sourceClass;
    }

    /** Setter for property sourceClass.
     * @param sourceClass New value of property sourceClass.
     */
    public void setSourceClass(String sourceClass) {
        m_sourceClass = sourceClass;
    }

    /** Getter for property sourceClassDd.
     * @return Value of property sourceClassDd.
     */
    public String getSourceClassDd() {
        return m_sourceClassDd;
    }

    /** Setter for property sourceClassDd.
     * @param sourceClassDd New value of property sourceClassDd.
     */
    public void setSourceClassDd(String sourceClassDd) {
        m_sourceClassDd = sourceClassDd;
    }

    // .//GEN-END:sourceClass_1_be
    // .//GEN-BEGIN:sourceMethod_1_be
    /** Getter for property sourceMethod.
     * @return Value of property sourceMethod.
     */
    public String getSourceMethod() {
        return m_sourceMethod;
    }

    /** Setter for property sourceMethod.
     * @param sourceMethod New value of property sourceMethod.
     */
    public void setSourceMethod(String sourceMethod) {
        m_sourceMethod = sourceMethod;
    }

    /** Getter for property sourceMethodDd.
     * @return Value of property sourceMethodDd.
     */
    public String getSourceMethodDd() {
        return m_sourceMethodDd;
    }

    /** Setter for property sourceMethodDd.
     * @param sourceMethodDd New value of property sourceMethodDd.
     */
    public void setSourceMethodDd(String sourceMethodDd) {
        m_sourceMethodDd = sourceMethodDd;
    }

    // .//GEN-END:sourceMethod_1_be
    // .//GEN-BEGIN:sourceLine_1_be
    /** Getter for property sourceLine.
     * @return Value of property sourceLine.
     */
    public String getSourceLine() {
        return m_sourceLine;
    }

    /** Setter for property sourceLine.
     * @param sourceLine New value of property sourceLine.
     */
    public void setSourceLine(String sourceLine) {
        m_sourceLine = sourceLine;
    }

    /** Getter for property sourceLineDd.
     * @return Value of property sourceLineDd.
     */
    public String getSourceLineDd() {
        return m_sourceLineDd;
    }

    /** Setter for property sourceLineDd.
     * @param sourceLineDd New value of property sourceLineDd.
     */
    public void setSourceLineDd(String sourceLineDd) {
        m_sourceLineDd = sourceLineDd;
    }

    // .//GEN-END:sourceLine_1_be
    // .//GEN-BEGIN:stackTrace_1_be
    /** Getter for property stackTrace.
     * @return Value of property stackTrace.
     */
    public String getStackTrace() {
        return m_stackTrace;
    }

    /** Setter for property stackTrace.
     * @param stackTrace New value of property stackTrace.
     */
    public void setStackTrace(String stackTrace) {
        m_stackTrace = stackTrace;
    }

    /** Getter for property stackTraceDd.
     * @return Value of property stackTraceDd.
     */
    public String getStackTraceDd() {
        return m_stackTraceDd;
    }

    /** Setter for property stackTraceDd.
     * @param stackTraceDd New value of property stackTraceDd.
     */
    public void setStackTraceDd(String stackTraceDd) {
        m_stackTraceDd = stackTraceDd;
    }

    // .//GEN-END:stackTrace_1_be
    // .//GEN-BEGIN:_doInquiry_1_be
    /** This performs the actual query to obtain the FinderOutDto.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return the FinderOutDto object.
     */
    protected FinderOutDto doInquiry() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        BusinessEventLogFinderInDto inputDto = new BusinessEventLogFinderInDto();
        // .//GEN-END:_doInquiry_1_be
        // Add custom code before processing the method //GEN-FIRST:_doInquiry_1


        // .//GEN-LAST:_doInquiry_1
        // .//GEN-BEGIN:_doInquiry_2_be
        inputDto.setMaxRecords(getMaxRecords());

        if (getLogId() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLogIdDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLogIdDd() ) )
            inputDto.setLogId(StringCriteriaField.getStringCriteriaField(getLogIdDd(), getLogId(), null));

        if (getCorrelationType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getCorrelationTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCorrelationTypeDd() ) )
            inputDto.setCorrelationType(StringCriteriaField.getStringCriteriaField(getCorrelationTypeDd(), getCorrelationType(), null));

        if (getCorrelationKey1() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getCorrelationKey1Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCorrelationKey1Dd() ) )
            inputDto.setCorrelationKey1(StringCriteriaField.getStringCriteriaField(getCorrelationKey1Dd(), getCorrelationKey1(), null));

        if (getCorrelationKey2() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getCorrelationKey2Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCorrelationKey2Dd() ) )
            inputDto.setCorrelationKey2(StringCriteriaField.getStringCriteriaField(getCorrelationKey2Dd(), getCorrelationKey2(), null));

        if (getCorrelationKey3() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getCorrelationKey3Dd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getCorrelationKey3Dd() ) )
            inputDto.setCorrelationKey3(StringCriteriaField.getStringCriteriaField(getCorrelationKey3Dd(), getCorrelationKey3(), null));

        if (getScheduledTaskId() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getScheduledTaskIdDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getScheduledTaskIdDd() ) )
            inputDto.setScheduledTaskId(StringCriteriaField.getStringCriteriaField(getScheduledTaskIdDd(), getScheduledTaskId(), null));

        if (getMessageId() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getMessageIdDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getMessageIdDd() ) )
            inputDto.setMessageId(StringCriteriaField.getStringCriteriaField(getMessageIdDd(), getMessageId(), null));

        try {
            if (getLoggedOn() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getLoggedOnDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLoggedOnDd() ) )
                inputDto.setLoggedOn(DateTimeCriteriaField.getDateTimeCriteriaField(getLoggedOnDd(), getLoggedOn(), (DateTimeFieldMetaData) BusinessEventLogMeta.META_LOGGED_ON));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getLoggedBy() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getLoggedByDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getLoggedByDd() ) )
            inputDto.setLoggedBy(StringCriteriaField.getStringCriteriaField(getLoggedByDd(), getLoggedBy(), null));

        if (getProcessName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getProcessNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getProcessNameDd() ) )
            inputDto.setProcessName(StringCriteriaField.getStringCriteriaField(getProcessNameDd(), getProcessName(), null));

        if (getSubProcessName() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSubProcessNameDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSubProcessNameDd() ) )
            inputDto.setSubProcessName(StringCriteriaField.getStringCriteriaField(getSubProcessNameDd(), getSubProcessName(), null));

        if (getMessageType() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getMessageTypeDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getMessageTypeDd() ) )
            inputDto.setMessageType(StringCriteriaField.getStringCriteriaField(getMessageTypeDd(), getMessageType(), null));

        if (getMessageText() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getMessageTextDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getMessageTextDd() ) )
            inputDto.setMessageText(StringCriteriaField.getStringCriteriaField(getMessageTextDd(), getMessageText(), null));

        if (getSourceClass() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSourceClassDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSourceClassDd() ) )
            inputDto.setSourceClass(StringCriteriaField.getStringCriteriaField(getSourceClassDd(), getSourceClass(), null));

        if (getSourceMethod() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getSourceMethodDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSourceMethodDd() ) )
            inputDto.setSourceMethod(StringCriteriaField.getStringCriteriaField(getSourceMethodDd(), getSourceMethod(), null));

        try {
            if (getSourceLine() != null
            || CriteriaField.RELATIONAL_IS_NULL.equals( getSourceLineDd() )
            || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getSourceLineDd() ) )
                inputDto.setSourceLine(IntegerCriteriaField.getIntegerCriteriaField(getSourceLineDd(), getSourceLine(), (IntegerFieldMetaData) BusinessEventLogMeta.META_SOURCE_LINE));
        } catch (ValidationException e) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(e);
        }

        if (getStackTrace() != null
        || CriteriaField.RELATIONAL_IS_NULL.equals( getStackTraceDd() )
        || CriteriaField.RELATIONAL_IS_NOT_NULL.equals( getStackTraceDd() ) )
            inputDto.setStackTrace(StringCriteriaField.getStringCriteriaField(getStackTraceDd(), getStackTrace(), null));


        // throw ApplicationExceptions, if any parsing errors occured
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        inputDto.setHeaderDto(getHeaderDto());
        addSortCriteria(inputDto);


        // perform the inquiry
        if (m_tx == null)
            m_tx = (IBusinessEventLogFinder) Factory.createObject(IBusinessEventLogFinder.class);
        FinderOutDto finderOutDto = m_tx.find(inputDto);
        // .//GEN-END:_doInquiry_2_be
        // Add custom code after the Transaction //GEN-FIRST:_doInquiry_2


        // .//GEN-LAST:_doInquiry_2
        // .//GEN-BEGIN:_doInquiry_3_be
        return finderOutDto;
    }
    // .//GEN-END:_doInquiry_3_be
    // .//GEN-BEGIN:_viewObject_1_be
    /** Calls the Jaffa.Messaging.BusinessEventLogViewer component for viewing the BusinessEventLog object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */
    public FormKey viewObject(java.lang.String logId) throws ApplicationExceptions, FrameworkException {
        BusinessEventLogViewerComponent viewComponent = (BusinessEventLogViewerComponent) run("Jaffa.Messaging.BusinessEventLogViewer");
        viewComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        viewComponent.setLogId(logId);
        // .//GEN-END:_viewObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_viewObject_1


        // .//GEN-LAST:_viewObject_1
        // .//GEN-BEGIN:_viewObject_2_be
        return viewComponent.display();
    }
    // .//GEN-END:_viewObject_2_be
    // .//GEN-BEGIN:_initializeCriteriaScreen_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void initializeCriteriaScreen() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initializeCriteriaScreen_1_be
    // All the custom code goes here //GEN-FIRST:_custom

    private boolean m_retrieveInputCorrelationTypeOnly = false;

    public boolean getRetrieveInputCorrelationTypeOnly() {
        return m_retrieveInputCorrelationTypeOnly;
    }

    public void setRetrieveInputCorrelationTypeOnly(boolean retrieveInputCorrelationTypeOnly) {
        m_retrieveInputCorrelationTypeOnly = retrieveInputCorrelationTypeOnly;
    }

    public FormKey display() throws ApplicationExceptions, FrameworkException {
        if (getRetrieveInputCorrelationTypeOnly() && getCorrelationType() != null)
            setCorrelationTypeDd(CriteriaField.RELATIONAL_EQUALS);
        return super.display();
    }

    // .//GEN-LAST:_custom
}
