// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
package org.jaffa.modules.messaging.components.businesseventlogfinder.ui;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.jaffa.metadata.*;
import org.jaffa.datatypes.Formatter;
import org.jaffa.datatypes.*;
import org.jaffa.components.finder.*;
import org.jaffa.presentation.portlet.widgets.model.*;
import org.jaffa.presentation.portlet.widgets.controller.*;
import org.jaffa.util.StringHelper;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutDto;
import org.jaffa.modules.messaging.components.businesseventlogfinder.dto.BusinessEventLogFinderOutRowDto;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The FormBean class to support BusinessEventLogFinder.
 */
public class BusinessEventLogFinderForm extends FinderForm {

    private static Logger log = Logger.getLogger(BusinessEventLogFinderForm.class);
    // .//GEN-END:_2_be
    // .//GEN-BEGIN:logId_1_be
    /** Getter for property logId.
     * @return Value of property logId.
     */
    public String getLogId() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLogId();
    }

    /** Setter for property logId.
     * @param logId New value of property logId.
     */
    public void setLogId(String logId) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLogId(logId);
    }

    /** Getter for property logId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property logId.
     */
    public EditBoxModel getLogIdWM() {
        EditBoxModel logIdModel = (EditBoxModel) getWidgetCache().getModel("logId");
        if (logIdModel == null) {
            if (getLogId() != null)
                logIdModel = new EditBoxModel( getLogId() );
            else
                logIdModel = new EditBoxModel();
            logIdModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_LOG_ID).getCaseType() );

            // .//GEN-END:logId_1_be
            // Add custom code //GEN-FIRST:logId_1


            // .//GEN-LAST:logId_1
            // .//GEN-BEGIN:logId_2_be
            getWidgetCache().addModel("logId", logIdModel);
        }
        return logIdModel;
    }

    /** Setter for property logId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property logId.
     */
    public void setLogIdWV(String value) {
        EditBoxController.updateModel(value, getLogIdWM());
    }

    /** Getter for DropDown property logId.
     * @return Value of property logIdDd.
     */
    public String getLogIdDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLogIdDd();
    }

    /** Setter for DropDown property logId.
     * @param logIdDd New value of property logIdDd.
     */
    public void setLogIdDd(String logIdDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLogIdDd(logIdDd);
    }

    /** Getter for DropDown property logId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property logIdDd.
     */
    public DropDownModel getLogIdDdWM() {
        DropDownModel logIdDdModel = (DropDownModel) getWidgetCache().getModel("logIdDd");
        if (logIdDdModel == null) {
            if (getLogIdDd() != null)
                logIdDdModel = new DropDownModel( getLogIdDd() );
            else
                logIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                logIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("logIdDd", logIdDdModel);
        }
        return logIdDdModel;
    }

    /** Setter for DropDown property logId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property logIdDd.
     */
    public void setLogIdDdWV(String value) {
        DropDownController.updateModel(value, getLogIdDdWM());
    }

    // .//GEN-END:logId_2_be
    // .//GEN-BEGIN:correlationType_1_be
    /** Getter for property correlationType.
     * @return Value of property correlationType.
     */
    public String getCorrelationType() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationType();
    }

    /** Setter for property correlationType.
     * @param correlationType New value of property correlationType.
     */
    public void setCorrelationType(String correlationType) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationType(correlationType);
    }

    /** Getter for property correlationType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationType.
     */
    public EditBoxModel getCorrelationTypeWM() {
        EditBoxModel correlationTypeModel = (EditBoxModel) getWidgetCache().getModel("correlationType");
        if (correlationTypeModel == null) {
            if (getCorrelationType() != null)
                correlationTypeModel = new EditBoxModel( getCorrelationType() );
            else
                correlationTypeModel = new EditBoxModel();
            correlationTypeModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_TYPE).getCaseType() );

            // .//GEN-END:correlationType_1_be
            // Add custom code //GEN-FIRST:correlationType_1


            // .//GEN-LAST:correlationType_1
            // .//GEN-BEGIN:correlationType_2_be
            getWidgetCache().addModel("correlationType", correlationTypeModel);
        }
        return correlationTypeModel;
    }

    /** Setter for property correlationType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationType.
     */
    public void setCorrelationTypeWV(String value) {
        EditBoxController.updateModel(value, getCorrelationTypeWM());
    }

    /** Getter for DropDown property correlationType.
     * @return Value of property correlationTypeDd.
     */
    public String getCorrelationTypeDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationTypeDd();
    }

    /** Setter for DropDown property correlationType.
     * @param correlationTypeDd New value of property correlationTypeDd.
     */
    public void setCorrelationTypeDd(String correlationTypeDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationTypeDd(correlationTypeDd);
    }

    /** Getter for DropDown property correlationType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationTypeDd.
     */
    public DropDownModel getCorrelationTypeDdWM() {
        DropDownModel correlationTypeDdModel = (DropDownModel) getWidgetCache().getModel("correlationTypeDd");
        if (correlationTypeDdModel == null) {
            if (getCorrelationTypeDd() != null)
                correlationTypeDdModel = new DropDownModel( getCorrelationTypeDd() );
            else
                correlationTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                correlationTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("correlationTypeDd", correlationTypeDdModel);
        }
        return correlationTypeDdModel;
    }

    /** Setter for DropDown property correlationType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationTypeDd.
     */
    public void setCorrelationTypeDdWV(String value) {
        DropDownController.updateModel(value, getCorrelationTypeDdWM());
    }

    // .//GEN-END:correlationType_2_be
    // .//GEN-BEGIN:correlationKey1_1_be
    /** Getter for property correlationKey1.
     * @return Value of property correlationKey1.
     */
    public String getCorrelationKey1() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey1();
    }

    /** Setter for property correlationKey1.
     * @param correlationKey1 New value of property correlationKey1.
     */
    public void setCorrelationKey1(String correlationKey1) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey1(correlationKey1);
    }

    /** Getter for property correlationKey1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey1.
     */
    public EditBoxModel getCorrelationKey1WM() {
        EditBoxModel correlationKey1Model = (EditBoxModel) getWidgetCache().getModel("correlationKey1");
        if (correlationKey1Model == null) {
            if (getCorrelationKey1() != null)
                correlationKey1Model = new EditBoxModel( getCorrelationKey1() );
            else
                correlationKey1Model = new EditBoxModel();
            correlationKey1Model.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY1).getCaseType() );

            // .//GEN-END:correlationKey1_1_be
            // Add custom code //GEN-FIRST:correlationKey1_1


            // .//GEN-LAST:correlationKey1_1
            // .//GEN-BEGIN:correlationKey1_2_be
            getWidgetCache().addModel("correlationKey1", correlationKey1Model);
        }
        return correlationKey1Model;
    }

    /** Setter for property correlationKey1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey1.
     */
    public void setCorrelationKey1WV(String value) {
        EditBoxController.updateModel(value, getCorrelationKey1WM());
    }

    /** Getter for DropDown property correlationKey1.
     * @return Value of property correlationKey1Dd.
     */
    public String getCorrelationKey1Dd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey1Dd();
    }

    /** Setter for DropDown property correlationKey1.
     * @param correlationKey1Dd New value of property correlationKey1Dd.
     */
    public void setCorrelationKey1Dd(String correlationKey1Dd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey1Dd(correlationKey1Dd);
    }

    /** Getter for DropDown property correlationKey1. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey1Dd.
     */
    public DropDownModel getCorrelationKey1DdWM() {
        DropDownModel correlationKey1DdModel = (DropDownModel) getWidgetCache().getModel("correlationKey1Dd");
        if (correlationKey1DdModel == null) {
            if (getCorrelationKey1Dd() != null)
                correlationKey1DdModel = new DropDownModel( getCorrelationKey1Dd() );
            else
                correlationKey1DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                correlationKey1DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("correlationKey1Dd", correlationKey1DdModel);
        }
        return correlationKey1DdModel;
    }

    /** Setter for DropDown property correlationKey1. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey1Dd.
     */
    public void setCorrelationKey1DdWV(String value) {
        DropDownController.updateModel(value, getCorrelationKey1DdWM());
    }

    // .//GEN-END:correlationKey1_2_be
    // .//GEN-BEGIN:correlationKey2_1_be
    /** Getter for property correlationKey2.
     * @return Value of property correlationKey2.
     */
    public String getCorrelationKey2() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey2();
    }

    /** Setter for property correlationKey2.
     * @param correlationKey2 New value of property correlationKey2.
     */
    public void setCorrelationKey2(String correlationKey2) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey2(correlationKey2);
    }

    /** Getter for property correlationKey2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey2.
     */
    public EditBoxModel getCorrelationKey2WM() {
        EditBoxModel correlationKey2Model = (EditBoxModel) getWidgetCache().getModel("correlationKey2");
        if (correlationKey2Model == null) {
            if (getCorrelationKey2() != null)
                correlationKey2Model = new EditBoxModel( getCorrelationKey2() );
            else
                correlationKey2Model = new EditBoxModel();
            correlationKey2Model.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY2).getCaseType() );

            // .//GEN-END:correlationKey2_1_be
            // Add custom code //GEN-FIRST:correlationKey2_1


            // .//GEN-LAST:correlationKey2_1
            // .//GEN-BEGIN:correlationKey2_2_be
            getWidgetCache().addModel("correlationKey2", correlationKey2Model);
        }
        return correlationKey2Model;
    }

    /** Setter for property correlationKey2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey2.
     */
    public void setCorrelationKey2WV(String value) {
        EditBoxController.updateModel(value, getCorrelationKey2WM());
    }

    /** Getter for DropDown property correlationKey2.
     * @return Value of property correlationKey2Dd.
     */
    public String getCorrelationKey2Dd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey2Dd();
    }

    /** Setter for DropDown property correlationKey2.
     * @param correlationKey2Dd New value of property correlationKey2Dd.
     */
    public void setCorrelationKey2Dd(String correlationKey2Dd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey2Dd(correlationKey2Dd);
    }

    /** Getter for DropDown property correlationKey2. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey2Dd.
     */
    public DropDownModel getCorrelationKey2DdWM() {
        DropDownModel correlationKey2DdModel = (DropDownModel) getWidgetCache().getModel("correlationKey2Dd");
        if (correlationKey2DdModel == null) {
            if (getCorrelationKey2Dd() != null)
                correlationKey2DdModel = new DropDownModel( getCorrelationKey2Dd() );
            else
                correlationKey2DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                correlationKey2DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("correlationKey2Dd", correlationKey2DdModel);
        }
        return correlationKey2DdModel;
    }

    /** Setter for DropDown property correlationKey2. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey2Dd.
     */
    public void setCorrelationKey2DdWV(String value) {
        DropDownController.updateModel(value, getCorrelationKey2DdWM());
    }

    // .//GEN-END:correlationKey2_2_be
    // .//GEN-BEGIN:correlationKey3_1_be
    /** Getter for property correlationKey3.
     * @return Value of property correlationKey3.
     */
    public String getCorrelationKey3() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey3();
    }

    /** Setter for property correlationKey3.
     * @param correlationKey3 New value of property correlationKey3.
     */
    public void setCorrelationKey3(String correlationKey3) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey3(correlationKey3);
    }

    /** Getter for property correlationKey3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey3.
     */
    public EditBoxModel getCorrelationKey3WM() {
        EditBoxModel correlationKey3Model = (EditBoxModel) getWidgetCache().getModel("correlationKey3");
        if (correlationKey3Model == null) {
            if (getCorrelationKey3() != null)
                correlationKey3Model = new EditBoxModel( getCorrelationKey3() );
            else
                correlationKey3Model = new EditBoxModel();
            correlationKey3Model.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_CORRELATION_KEY3).getCaseType() );

            // .//GEN-END:correlationKey3_1_be
            // Add custom code //GEN-FIRST:correlationKey3_1


            // .//GEN-LAST:correlationKey3_1
            // .//GEN-BEGIN:correlationKey3_2_be
            getWidgetCache().addModel("correlationKey3", correlationKey3Model);
        }
        return correlationKey3Model;
    }

    /** Setter for property correlationKey3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey3.
     */
    public void setCorrelationKey3WV(String value) {
        EditBoxController.updateModel(value, getCorrelationKey3WM());
    }

    /** Getter for DropDown property correlationKey3.
     * @return Value of property correlationKey3Dd.
     */
    public String getCorrelationKey3Dd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getCorrelationKey3Dd();
    }

    /** Setter for DropDown property correlationKey3.
     * @param correlationKey3Dd New value of property correlationKey3Dd.
     */
    public void setCorrelationKey3Dd(String correlationKey3Dd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setCorrelationKey3Dd(correlationKey3Dd);
    }

    /** Getter for DropDown property correlationKey3. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property correlationKey3Dd.
     */
    public DropDownModel getCorrelationKey3DdWM() {
        DropDownModel correlationKey3DdModel = (DropDownModel) getWidgetCache().getModel("correlationKey3Dd");
        if (correlationKey3DdModel == null) {
            if (getCorrelationKey3Dd() != null)
                correlationKey3DdModel = new DropDownModel( getCorrelationKey3Dd() );
            else
                correlationKey3DdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                correlationKey3DdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("correlationKey3Dd", correlationKey3DdModel);
        }
        return correlationKey3DdModel;
    }

    /** Setter for DropDown property correlationKey3. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property correlationKey3Dd.
     */
    public void setCorrelationKey3DdWV(String value) {
        DropDownController.updateModel(value, getCorrelationKey3DdWM());
    }

    // .//GEN-END:correlationKey3_2_be
    // .//GEN-BEGIN:scheduledTaskId_1_be
    /** Getter for property scheduledTaskId.
     * @return Value of property scheduledTaskId.
     */
    public String getScheduledTaskId() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getScheduledTaskId();
    }

    /** Setter for property scheduledTaskId.
     * @param scheduledTaskId New value of property scheduledTaskId.
     */
    public void setScheduledTaskId(String scheduledTaskId) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setScheduledTaskId(scheduledTaskId);
    }

    /** Getter for property scheduledTaskId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scheduledTaskId.
     */
    public EditBoxModel getScheduledTaskIdWM() {
        EditBoxModel scheduledTaskIdModel = (EditBoxModel) getWidgetCache().getModel("scheduledTaskId");
        if (scheduledTaskIdModel == null) {
            if (getScheduledTaskId() != null)
                scheduledTaskIdModel = new EditBoxModel( getScheduledTaskId() );
            else
                scheduledTaskIdModel = new EditBoxModel();
            scheduledTaskIdModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_SCHEDULED_TASK_ID).getCaseType() );

            // .//GEN-END:scheduledTaskId_1_be
            // Add custom code //GEN-FIRST:scheduledTaskId_1


            // .//GEN-LAST:scheduledTaskId_1
            // .//GEN-BEGIN:scheduledTaskId_2_be
            getWidgetCache().addModel("scheduledTaskId", scheduledTaskIdModel);
        }
        return scheduledTaskIdModel;
    }

    /** Setter for property scheduledTaskId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property scheduledTaskId.
     */
    public void setScheduledTaskIdWV(String value) {
        EditBoxController.updateModel(value, getScheduledTaskIdWM());
    }

    /** Getter for DropDown property scheduledTaskId.
     * @return Value of property scheduledTaskIdDd.
     */
    public String getScheduledTaskIdDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getScheduledTaskIdDd();
    }

    /** Setter for DropDown property scheduledTaskId.
     * @param scheduledTaskIdDd New value of property scheduledTaskIdDd.
     */
    public void setScheduledTaskIdDd(String scheduledTaskIdDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setScheduledTaskIdDd(scheduledTaskIdDd);
    }

    /** Getter for DropDown property scheduledTaskId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property scheduledTaskIdDd.
     */
    public DropDownModel getScheduledTaskIdDdWM() {
        DropDownModel scheduledTaskIdDdModel = (DropDownModel) getWidgetCache().getModel("scheduledTaskIdDd");
        if (scheduledTaskIdDdModel == null) {
            if (getScheduledTaskIdDd() != null)
                scheduledTaskIdDdModel = new DropDownModel( getScheduledTaskIdDd() );
            else
                scheduledTaskIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                scheduledTaskIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("scheduledTaskIdDd", scheduledTaskIdDdModel);
        }
        return scheduledTaskIdDdModel;
    }

    /** Setter for DropDown property scheduledTaskId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property scheduledTaskIdDd.
     */
    public void setScheduledTaskIdDdWV(String value) {
        DropDownController.updateModel(value, getScheduledTaskIdDdWM());
    }

    // .//GEN-END:scheduledTaskId_2_be
    // .//GEN-BEGIN:messageId_1_be
    /** Getter for property messageId.
     * @return Value of property messageId.
     */
    public String getMessageId() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageId();
    }

    /** Setter for property messageId.
     * @param messageId New value of property messageId.
     */
    public void setMessageId(String messageId) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageId(messageId);
    }

    /** Getter for property messageId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageId.
     */
    public EditBoxModel getMessageIdWM() {
        EditBoxModel messageIdModel = (EditBoxModel) getWidgetCache().getModel("messageId");
        if (messageIdModel == null) {
            if (getMessageId() != null)
                messageIdModel = new EditBoxModel( getMessageId() );
            else
                messageIdModel = new EditBoxModel();
            messageIdModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_ID).getCaseType() );

            // .//GEN-END:messageId_1_be
            // Add custom code //GEN-FIRST:messageId_1


            // .//GEN-LAST:messageId_1
            // .//GEN-BEGIN:messageId_2_be
            getWidgetCache().addModel("messageId", messageIdModel);
        }
        return messageIdModel;
    }

    /** Setter for property messageId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageId.
     */
    public void setMessageIdWV(String value) {
        EditBoxController.updateModel(value, getMessageIdWM());
    }

    /** Getter for DropDown property messageId.
     * @return Value of property messageIdDd.
     */
    public String getMessageIdDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageIdDd();
    }

    /** Setter for DropDown property messageId.
     * @param messageIdDd New value of property messageIdDd.
     */
    public void setMessageIdDd(String messageIdDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageIdDd(messageIdDd);
    }

    /** Getter for DropDown property messageId. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageIdDd.
     */
    public DropDownModel getMessageIdDdWM() {
        DropDownModel messageIdDdModel = (DropDownModel) getWidgetCache().getModel("messageIdDd");
        if (messageIdDdModel == null) {
            if (getMessageIdDd() != null)
                messageIdDdModel = new DropDownModel( getMessageIdDd() );
            else
                messageIdDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                messageIdDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("messageIdDd", messageIdDdModel);
        }
        return messageIdDdModel;
    }

    /** Setter for DropDown property messageId. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageIdDd.
     */
    public void setMessageIdDdWV(String value) {
        DropDownController.updateModel(value, getMessageIdDdWM());
    }

    // .//GEN-END:messageId_2_be
    // .//GEN-BEGIN:loggedOn_1_be
    /** Getter for property loggedOn.
     * @return Value of property loggedOn.
     */
    public String getLoggedOn() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLoggedOn();
    }

    /** Setter for property loggedOn.
     * @param loggedOn New value of property loggedOn.
     */
    public void setLoggedOn(String loggedOn) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLoggedOn(loggedOn);
    }

    /** Getter for property loggedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property loggedOn.
     */
    public EditBoxModel getLoggedOnWM() {
        EditBoxModel loggedOnModel = (EditBoxModel) getWidgetCache().getModel("loggedOn");
        if (loggedOnModel == null) {
            if (getLoggedOn() != null)
                loggedOnModel = new EditBoxModel( getLoggedOn() );
            else
                loggedOnModel = new EditBoxModel();

            // .//GEN-END:loggedOn_1_be
            // Add custom code //GEN-FIRST:loggedOn_1


            // .//GEN-LAST:loggedOn_1
            // .//GEN-BEGIN:loggedOn_2_be
            getWidgetCache().addModel("loggedOn", loggedOnModel);
        }
        return loggedOnModel;
    }

    /** Setter for property loggedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property loggedOn.
     */
    public void setLoggedOnWV(String value) {
        EditBoxController.updateModel(value, getLoggedOnWM());
    }

    /** Getter for DropDown property loggedOn.
     * @return Value of property loggedOnDd.
     */
    public String getLoggedOnDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLoggedOnDd();
    }

    /** Setter for DropDown property loggedOn.
     * @param loggedOnDd New value of property loggedOnDd.
     */
    public void setLoggedOnDd(String loggedOnDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLoggedOnDd(loggedOnDd);
    }

    /** Getter for DropDown property loggedOn. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property loggedOnDd.
     */
    public DropDownModel getLoggedOnDdWM() {
        DropDownModel loggedOnDdModel = (DropDownModel) getWidgetCache().getModel("loggedOnDd");
        if (loggedOnDdModel == null) {
            if (getLoggedOnDd() != null)
                loggedOnDdModel = new DropDownModel( getLoggedOnDd() );
            else
                loggedOnDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getDateCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                loggedOnDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("loggedOnDd", loggedOnDdModel);
        }
        return loggedOnDdModel;
    }

    /** Setter for DropDown property loggedOn. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property loggedOnDd.
     */
    public void setLoggedOnDdWV(String value) {
        DropDownController.updateModel(value, getLoggedOnDdWM());
    }

    // .//GEN-END:loggedOn_2_be
    // .//GEN-BEGIN:loggedBy_1_be
    /** Getter for property loggedBy.
     * @return Value of property loggedBy.
     */
    public String getLoggedBy() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLoggedBy();
    }

    /** Setter for property loggedBy.
     * @param loggedBy New value of property loggedBy.
     */
    public void setLoggedBy(String loggedBy) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLoggedBy(loggedBy);
    }

    /** Getter for property loggedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property loggedBy.
     */
    public EditBoxModel getLoggedByWM() {
        EditBoxModel loggedByModel = (EditBoxModel) getWidgetCache().getModel("loggedBy");
        if (loggedByModel == null) {
            if (getLoggedBy() != null)
                loggedByModel = new EditBoxModel( getLoggedBy() );
            else
                loggedByModel = new EditBoxModel();
            loggedByModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_LOGGED_BY).getCaseType() );

            // .//GEN-END:loggedBy_1_be
            // Add custom code //GEN-FIRST:loggedBy_1


            // .//GEN-LAST:loggedBy_1
            // .//GEN-BEGIN:loggedBy_2_be
            getWidgetCache().addModel("loggedBy", loggedByModel);
        }
        return loggedByModel;
    }

    /** Setter for property loggedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property loggedBy.
     */
    public void setLoggedByWV(String value) {
        EditBoxController.updateModel(value, getLoggedByWM());
    }

    /** Getter for DropDown property loggedBy.
     * @return Value of property loggedByDd.
     */
    public String getLoggedByDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getLoggedByDd();
    }

    /** Setter for DropDown property loggedBy.
     * @param loggedByDd New value of property loggedByDd.
     */
    public void setLoggedByDd(String loggedByDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setLoggedByDd(loggedByDd);
    }

    /** Getter for DropDown property loggedBy. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property loggedByDd.
     */
    public DropDownModel getLoggedByDdWM() {
        DropDownModel loggedByDdModel = (DropDownModel) getWidgetCache().getModel("loggedByDd");
        if (loggedByDdModel == null) {
            if (getLoggedByDd() != null)
                loggedByDdModel = new DropDownModel( getLoggedByDd() );
            else
                loggedByDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                loggedByDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("loggedByDd", loggedByDdModel);
        }
        return loggedByDdModel;
    }

    /** Setter for DropDown property loggedBy. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property loggedByDd.
     */
    public void setLoggedByDdWV(String value) {
        DropDownController.updateModel(value, getLoggedByDdWM());
    }

    // .//GEN-END:loggedBy_2_be
    // .//GEN-BEGIN:processName_1_be
    /** Getter for property processName.
     * @return Value of property processName.
     */
    public String getProcessName() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getProcessName();
    }

    /** Setter for property processName.
     * @param processName New value of property processName.
     */
    public void setProcessName(String processName) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setProcessName(processName);
    }

    /** Getter for property processName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processName.
     */
    public EditBoxModel getProcessNameWM() {
        EditBoxModel processNameModel = (EditBoxModel) getWidgetCache().getModel("processName");
        if (processNameModel == null) {
            if (getProcessName() != null)
                processNameModel = new EditBoxModel( getProcessName() );
            else
                processNameModel = new EditBoxModel();
            processNameModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_PROCESS_NAME).getCaseType() );

            // .//GEN-END:processName_1_be
            // Add custom code //GEN-FIRST:processName_1


            // .//GEN-LAST:processName_1
            // .//GEN-BEGIN:processName_2_be
            getWidgetCache().addModel("processName", processNameModel);
        }
        return processNameModel;
    }

    /** Setter for property processName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processName.
     */
    public void setProcessNameWV(String value) {
        EditBoxController.updateModel(value, getProcessNameWM());
    }

    /** Getter for DropDown property processName.
     * @return Value of property processNameDd.
     */
    public String getProcessNameDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getProcessNameDd();
    }

    /** Setter for DropDown property processName.
     * @param processNameDd New value of property processNameDd.
     */
    public void setProcessNameDd(String processNameDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setProcessNameDd(processNameDd);
    }

    /** Getter for DropDown property processName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property processNameDd.
     */
    public DropDownModel getProcessNameDdWM() {
        DropDownModel processNameDdModel = (DropDownModel) getWidgetCache().getModel("processNameDd");
        if (processNameDdModel == null) {
            if (getProcessNameDd() != null)
                processNameDdModel = new DropDownModel( getProcessNameDd() );
            else
                processNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                processNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("processNameDd", processNameDdModel);
        }
        return processNameDdModel;
    }

    /** Setter for DropDown property processName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property processNameDd.
     */
    public void setProcessNameDdWV(String value) {
        DropDownController.updateModel(value, getProcessNameDdWM());
    }

    // .//GEN-END:processName_2_be
    // .//GEN-BEGIN:subProcessName_1_be
    /** Getter for property subProcessName.
     * @return Value of property subProcessName.
     */
    public String getSubProcessName() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSubProcessName();
    }

    /** Setter for property subProcessName.
     * @param subProcessName New value of property subProcessName.
     */
    public void setSubProcessName(String subProcessName) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSubProcessName(subProcessName);
    }

    /** Getter for property subProcessName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property subProcessName.
     */
    public EditBoxModel getSubProcessNameWM() {
        EditBoxModel subProcessNameModel = (EditBoxModel) getWidgetCache().getModel("subProcessName");
        if (subProcessNameModel == null) {
            if (getSubProcessName() != null)
                subProcessNameModel = new EditBoxModel( getSubProcessName() );
            else
                subProcessNameModel = new EditBoxModel();
            subProcessNameModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_SUB_PROCESS_NAME).getCaseType() );

            // .//GEN-END:subProcessName_1_be
            // Add custom code //GEN-FIRST:subProcessName_1


            // .//GEN-LAST:subProcessName_1
            // .//GEN-BEGIN:subProcessName_2_be
            getWidgetCache().addModel("subProcessName", subProcessNameModel);
        }
        return subProcessNameModel;
    }

    /** Setter for property subProcessName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property subProcessName.
     */
    public void setSubProcessNameWV(String value) {
        EditBoxController.updateModel(value, getSubProcessNameWM());
    }

    /** Getter for DropDown property subProcessName.
     * @return Value of property subProcessNameDd.
     */
    public String getSubProcessNameDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSubProcessNameDd();
    }

    /** Setter for DropDown property subProcessName.
     * @param subProcessNameDd New value of property subProcessNameDd.
     */
    public void setSubProcessNameDd(String subProcessNameDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSubProcessNameDd(subProcessNameDd);
    }

    /** Getter for DropDown property subProcessName. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property subProcessNameDd.
     */
    public DropDownModel getSubProcessNameDdWM() {
        DropDownModel subProcessNameDdModel = (DropDownModel) getWidgetCache().getModel("subProcessNameDd");
        if (subProcessNameDdModel == null) {
            if (getSubProcessNameDd() != null)
                subProcessNameDdModel = new DropDownModel( getSubProcessNameDd() );
            else
                subProcessNameDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                subProcessNameDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("subProcessNameDd", subProcessNameDdModel);
        }
        return subProcessNameDdModel;
    }

    /** Setter for DropDown property subProcessName. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property subProcessNameDd.
     */
    public void setSubProcessNameDdWV(String value) {
        DropDownController.updateModel(value, getSubProcessNameDdWM());
    }

    // .//GEN-END:subProcessName_2_be
    // .//GEN-BEGIN:messageType_1_be
    /** Getter for property messageType.
     * @return Value of property messageType.
     */
    public String getMessageType() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageType();
    }

    /** Setter for property messageType.
     * @param messageType New value of property messageType.
     */
    public void setMessageType(String messageType) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageType(messageType);
    }

    /** Getter for property messageType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageType.
     */
    public EditBoxModel getMessageTypeWM() {
        EditBoxModel messageTypeModel = (EditBoxModel) getWidgetCache().getModel("messageType");
        if (messageTypeModel == null) {
            if (getMessageType() != null)
                messageTypeModel = new EditBoxModel( getMessageType() );
            else
                messageTypeModel = new EditBoxModel();
            messageTypeModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_TYPE).getCaseType() );

            // .//GEN-END:messageType_1_be
            // Add custom code //GEN-FIRST:messageType_1


            // .//GEN-LAST:messageType_1
            // .//GEN-BEGIN:messageType_2_be
            getWidgetCache().addModel("messageType", messageTypeModel);
        }
        return messageTypeModel;
    }

    /** Setter for property messageType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageType.
     */
    public void setMessageTypeWV(String value) {
        EditBoxController.updateModel(value, getMessageTypeWM());
    }

    /** Getter for DropDown property messageType.
     * @return Value of property messageTypeDd.
     */
    public String getMessageTypeDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageTypeDd();
    }

    /** Setter for DropDown property messageType.
     * @param messageTypeDd New value of property messageTypeDd.
     */
    public void setMessageTypeDd(String messageTypeDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageTypeDd(messageTypeDd);
    }

    /** Getter for DropDown property messageType. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageTypeDd.
     */
    public DropDownModel getMessageTypeDdWM() {
        DropDownModel messageTypeDdModel = (DropDownModel) getWidgetCache().getModel("messageTypeDd");
        if (messageTypeDdModel == null) {
            if (getMessageTypeDd() != null)
                messageTypeDdModel = new DropDownModel( getMessageTypeDd() );
            else
                messageTypeDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                messageTypeDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("messageTypeDd", messageTypeDdModel);
        }
        return messageTypeDdModel;
    }

    /** Setter for DropDown property messageType. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageTypeDd.
     */
    public void setMessageTypeDdWV(String value) {
        DropDownController.updateModel(value, getMessageTypeDdWM());
    }

    // .//GEN-END:messageType_2_be
    // .//GEN-BEGIN:messageText_1_be
    /** Getter for property messageText.
     * @return Value of property messageText.
     */
    public String getMessageText() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageText();
    }

    /** Setter for property messageText.
     * @param messageText New value of property messageText.
     */
    public void setMessageText(String messageText) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageText(messageText);
    }

    /** Getter for property messageText. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageText.
     */
    public EditBoxModel getMessageTextWM() {
        EditBoxModel messageTextModel = (EditBoxModel) getWidgetCache().getModel("messageText");
        if (messageTextModel == null) {
            if (getMessageText() != null)
                messageTextModel = new EditBoxModel( getMessageText() );
            else
                messageTextModel = new EditBoxModel();
            messageTextModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_MESSAGE_TEXT).getCaseType() );

            // .//GEN-END:messageText_1_be
            // Add custom code //GEN-FIRST:messageText_1


            // .//GEN-LAST:messageText_1
            // .//GEN-BEGIN:messageText_2_be
            getWidgetCache().addModel("messageText", messageTextModel);
        }
        return messageTextModel;
    }

    /** Setter for property messageText. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageText.
     */
    public void setMessageTextWV(String value) {
        EditBoxController.updateModel(value, getMessageTextWM());
    }

    /** Getter for DropDown property messageText.
     * @return Value of property messageTextDd.
     */
    public String getMessageTextDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getMessageTextDd();
    }

    /** Setter for DropDown property messageText.
     * @param messageTextDd New value of property messageTextDd.
     */
    public void setMessageTextDd(String messageTextDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setMessageTextDd(messageTextDd);
    }

    /** Getter for DropDown property messageText. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property messageTextDd.
     */
    public DropDownModel getMessageTextDdWM() {
        DropDownModel messageTextDdModel = (DropDownModel) getWidgetCache().getModel("messageTextDd");
        if (messageTextDdModel == null) {
            if (getMessageTextDd() != null)
                messageTextDdModel = new DropDownModel( getMessageTextDd() );
            else
                messageTextDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                messageTextDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("messageTextDd", messageTextDdModel);
        }
        return messageTextDdModel;
    }

    /** Setter for DropDown property messageText. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property messageTextDd.
     */
    public void setMessageTextDdWV(String value) {
        DropDownController.updateModel(value, getMessageTextDdWM());
    }

    // .//GEN-END:messageText_2_be
    // .//GEN-BEGIN:sourceClass_1_be
    /** Getter for property sourceClass.
     * @return Value of property sourceClass.
     */
    public String getSourceClass() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceClass();
    }

    /** Setter for property sourceClass.
     * @param sourceClass New value of property sourceClass.
     */
    public void setSourceClass(String sourceClass) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceClass(sourceClass);
    }

    /** Getter for property sourceClass. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceClass.
     */
    public EditBoxModel getSourceClassWM() {
        EditBoxModel sourceClassModel = (EditBoxModel) getWidgetCache().getModel("sourceClass");
        if (sourceClassModel == null) {
            if (getSourceClass() != null)
                sourceClassModel = new EditBoxModel( getSourceClass() );
            else
                sourceClassModel = new EditBoxModel();
            sourceClassModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_SOURCE_CLASS).getCaseType() );

            // .//GEN-END:sourceClass_1_be
            // Add custom code //GEN-FIRST:sourceClass_1


            // .//GEN-LAST:sourceClass_1
            // .//GEN-BEGIN:sourceClass_2_be
            getWidgetCache().addModel("sourceClass", sourceClassModel);
        }
        return sourceClassModel;
    }

    /** Setter for property sourceClass. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceClass.
     */
    public void setSourceClassWV(String value) {
        EditBoxController.updateModel(value, getSourceClassWM());
    }

    /** Getter for DropDown property sourceClass.
     * @return Value of property sourceClassDd.
     */
    public String getSourceClassDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceClassDd();
    }

    /** Setter for DropDown property sourceClass.
     * @param sourceClassDd New value of property sourceClassDd.
     */
    public void setSourceClassDd(String sourceClassDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceClassDd(sourceClassDd);
    }

    /** Getter for DropDown property sourceClass. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceClassDd.
     */
    public DropDownModel getSourceClassDdWM() {
        DropDownModel sourceClassDdModel = (DropDownModel) getWidgetCache().getModel("sourceClassDd");
        if (sourceClassDdModel == null) {
            if (getSourceClassDd() != null)
                sourceClassDdModel = new DropDownModel( getSourceClassDd() );
            else
                sourceClassDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                sourceClassDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("sourceClassDd", sourceClassDdModel);
        }
        return sourceClassDdModel;
    }

    /** Setter for DropDown property sourceClass. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceClassDd.
     */
    public void setSourceClassDdWV(String value) {
        DropDownController.updateModel(value, getSourceClassDdWM());
    }

    // .//GEN-END:sourceClass_2_be
    // .//GEN-BEGIN:sourceMethod_1_be
    /** Getter for property sourceMethod.
     * @return Value of property sourceMethod.
     */
    public String getSourceMethod() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceMethod();
    }

    /** Setter for property sourceMethod.
     * @param sourceMethod New value of property sourceMethod.
     */
    public void setSourceMethod(String sourceMethod) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceMethod(sourceMethod);
    }

    /** Getter for property sourceMethod. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceMethod.
     */
    public EditBoxModel getSourceMethodWM() {
        EditBoxModel sourceMethodModel = (EditBoxModel) getWidgetCache().getModel("sourceMethod");
        if (sourceMethodModel == null) {
            if (getSourceMethod() != null)
                sourceMethodModel = new EditBoxModel( getSourceMethod() );
            else
                sourceMethodModel = new EditBoxModel();
            sourceMethodModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_SOURCE_METHOD).getCaseType() );

            // .//GEN-END:sourceMethod_1_be
            // Add custom code //GEN-FIRST:sourceMethod_1


            // .//GEN-LAST:sourceMethod_1
            // .//GEN-BEGIN:sourceMethod_2_be
            getWidgetCache().addModel("sourceMethod", sourceMethodModel);
        }
        return sourceMethodModel;
    }

    /** Setter for property sourceMethod. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceMethod.
     */
    public void setSourceMethodWV(String value) {
        EditBoxController.updateModel(value, getSourceMethodWM());
    }

    /** Getter for DropDown property sourceMethod.
     * @return Value of property sourceMethodDd.
     */
    public String getSourceMethodDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceMethodDd();
    }

    /** Setter for DropDown property sourceMethod.
     * @param sourceMethodDd New value of property sourceMethodDd.
     */
    public void setSourceMethodDd(String sourceMethodDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceMethodDd(sourceMethodDd);
    }

    /** Getter for DropDown property sourceMethod. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceMethodDd.
     */
    public DropDownModel getSourceMethodDdWM() {
        DropDownModel sourceMethodDdModel = (DropDownModel) getWidgetCache().getModel("sourceMethodDd");
        if (sourceMethodDdModel == null) {
            if (getSourceMethodDd() != null)
                sourceMethodDdModel = new DropDownModel( getSourceMethodDd() );
            else
                sourceMethodDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                sourceMethodDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("sourceMethodDd", sourceMethodDdModel);
        }
        return sourceMethodDdModel;
    }

    /** Setter for DropDown property sourceMethod. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceMethodDd.
     */
    public void setSourceMethodDdWV(String value) {
        DropDownController.updateModel(value, getSourceMethodDdWM());
    }

    // .//GEN-END:sourceMethod_2_be
    // .//GEN-BEGIN:sourceLine_1_be
    /** Getter for property sourceLine.
     * @return Value of property sourceLine.
     */
    public String getSourceLine() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceLine();
    }

    /** Setter for property sourceLine.
     * @param sourceLine New value of property sourceLine.
     */
    public void setSourceLine(String sourceLine) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceLine(sourceLine);
    }

    /** Getter for property sourceLine. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceLine.
     */
    public EditBoxModel getSourceLineWM() {
        EditBoxModel sourceLineModel = (EditBoxModel) getWidgetCache().getModel("sourceLine");
        if (sourceLineModel == null) {
            if (getSourceLine() != null)
                sourceLineModel = new EditBoxModel( getSourceLine() );
            else
                sourceLineModel = new EditBoxModel();

            // .//GEN-END:sourceLine_1_be
            // Add custom code //GEN-FIRST:sourceLine_1


            // .//GEN-LAST:sourceLine_1
            // .//GEN-BEGIN:sourceLine_2_be
            getWidgetCache().addModel("sourceLine", sourceLineModel);
        }
        return sourceLineModel;
    }

    /** Setter for property sourceLine. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceLine.
     */
    public void setSourceLineWV(String value) {
        EditBoxController.updateModel(value, getSourceLineWM());
    }

    /** Getter for DropDown property sourceLine.
     * @return Value of property sourceLineDd.
     */
    public String getSourceLineDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getSourceLineDd();
    }

    /** Setter for DropDown property sourceLine.
     * @param sourceLineDd New value of property sourceLineDd.
     */
    public void setSourceLineDd(String sourceLineDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setSourceLineDd(sourceLineDd);
    }

    /** Getter for DropDown property sourceLine. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property sourceLineDd.
     */
    public DropDownModel getSourceLineDdWM() {
        DropDownModel sourceLineDdModel = (DropDownModel) getWidgetCache().getModel("sourceLineDd");
        if (sourceLineDdModel == null) {
            if (getSourceLineDd() != null)
                sourceLineDdModel = new DropDownModel( getSourceLineDd() );
            else
                sourceLineDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getNumericalCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                sourceLineDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("sourceLineDd", sourceLineDdModel);
        }
        return sourceLineDdModel;
    }

    /** Setter for DropDown property sourceLine. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property sourceLineDd.
     */
    public void setSourceLineDdWV(String value) {
        DropDownController.updateModel(value, getSourceLineDdWM());
    }

    // .//GEN-END:sourceLine_2_be
    // .//GEN-BEGIN:stackTrace_1_be
    /** Getter for property stackTrace.
     * @return Value of property stackTrace.
     */
    public String getStackTrace() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getStackTrace();
    }

    /** Setter for property stackTrace.
     * @param stackTrace New value of property stackTrace.
     */
    public void setStackTrace(String stackTrace) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setStackTrace(stackTrace);
    }

    /** Getter for property stackTrace. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property stackTrace.
     */
    public EditBoxModel getStackTraceWM() {
        EditBoxModel stackTraceModel = (EditBoxModel) getWidgetCache().getModel("stackTrace");
        if (stackTraceModel == null) {
            if (getStackTrace() != null)
                stackTraceModel = new EditBoxModel( getStackTrace() );
            else
                stackTraceModel = new EditBoxModel();
            stackTraceModel.setStringCase( ((StringFieldMetaData) BusinessEventLogMeta.META_STACK_TRACE).getCaseType() );

            // .//GEN-END:stackTrace_1_be
            // Add custom code //GEN-FIRST:stackTrace_1


            // .//GEN-LAST:stackTrace_1
            // .//GEN-BEGIN:stackTrace_2_be
            getWidgetCache().addModel("stackTrace", stackTraceModel);
        }
        return stackTraceModel;
    }

    /** Setter for property stackTrace. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property stackTrace.
     */
    public void setStackTraceWV(String value) {
        EditBoxController.updateModel(value, getStackTraceWM());
    }

    /** Getter for DropDown property stackTrace.
     * @return Value of property stackTraceDd.
     */
    public String getStackTraceDd() {
        return ( (BusinessEventLogFinderComponent) getComponent() ).getStackTraceDd();
    }

    /** Setter for DropDown property stackTrace.
     * @param stackTraceDd New value of property stackTraceDd.
     */
    public void setStackTraceDd(String stackTraceDd) {
        ( (BusinessEventLogFinderComponent) getComponent() ).setStackTraceDd(stackTraceDd);
    }

    /** Getter for DropDown property stackTrace. This is invoked by the custom tag, when the jsp is rendered, to get the current value.
     * @return Value of property stackTraceDd.
     */
    public DropDownModel getStackTraceDdWM() {
        DropDownModel stackTraceDdModel = (DropDownModel) getWidgetCache().getModel("stackTraceDd");
        if (stackTraceDdModel == null) {
            if (getStackTraceDd() != null)
                stackTraceDdModel = new DropDownModel( getStackTraceDd() );
            else
                stackTraceDdModel = new DropDownModel(CriteriaField.RELATIONAL_EQUALS);
            Map optionsMap = CriteriaDropDownOptions.getAllCriteriaDropDownOptions();
            Set options = optionsMap.entrySet();
            for (Iterator i = options.iterator(); i.hasNext();) {
                Map.Entry option = (Map.Entry) i.next();
                stackTraceDdModel.addOption((String) option.getValue(), (String) option.getKey());
            }
            getWidgetCache().addModel("stackTraceDd", stackTraceDdModel);
        }
        return stackTraceDdModel;
    }

    /** Setter for DropDown property stackTrace. This is invoked by the servlet, when a post is done on the Criteria screen.
     * @param value New value of property stackTraceDd.
     */
    public void setStackTraceDdWV(String value) {
        DropDownController.updateModel(value, getStackTraceDdWM());
    }

    // .//GEN-END:stackTrace_2_be
    // .//GEN-BEGIN:_doValidate_1_be
    /** This method should be invoked to ensure a valid state of the FormBean. It will validate the data in the models and set the corresponding properties.
     * Errors will be raised in the FormBean, if any validation fails.
     * @param request The request stream
     * @return A true indicates validations went through successfully. */
    public boolean doValidate(HttpServletRequest request) {
        boolean valid = super.doValidate(request);
        String value = null;
        StringBuffer buf = null;

        value = getLogIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLogId(value);
        setLogIdDd(getLogIdDdWM().getValue());

        value = getCorrelationTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCorrelationType(value);
        setCorrelationTypeDd(getCorrelationTypeDdWM().getValue());

        value = getCorrelationKey1WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCorrelationKey1(value);
        setCorrelationKey1Dd(getCorrelationKey1DdWM().getValue());

        value = getCorrelationKey2WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCorrelationKey2(value);
        setCorrelationKey2Dd(getCorrelationKey2DdWM().getValue());

        value = getCorrelationKey3WM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setCorrelationKey3(value);
        setCorrelationKey3Dd(getCorrelationKey3DdWM().getValue());

        value = getScheduledTaskIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setScheduledTaskId(value);
        setScheduledTaskIdDd(getScheduledTaskIdDdWM().getValue());

        value = getMessageIdWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setMessageId(value);
        setMessageIdDd(getMessageIdDdWM().getValue());

        value = getLoggedOnWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLoggedOn(value);
        setLoggedOnDd(getLoggedOnDdWM().getValue());

        value = getLoggedByWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setLoggedBy(value);
        setLoggedByDd(getLoggedByDdWM().getValue());

        value = getProcessNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setProcessName(value);
        setProcessNameDd(getProcessNameDdWM().getValue());

        value = getSubProcessNameWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSubProcessName(value);
        setSubProcessNameDd(getSubProcessNameDdWM().getValue());

        value = getMessageTypeWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setMessageType(value);
        setMessageTypeDd(getMessageTypeDdWM().getValue());

        value = getMessageTextWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setMessageText(value);
        setMessageTextDd(getMessageTextDdWM().getValue());

        value = getSourceClassWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSourceClass(value);
        setSourceClassDd(getSourceClassDdWM().getValue());

        value = getSourceMethodWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSourceMethod(value);
        setSourceMethodDd(getSourceMethodDdWM().getValue());

        value = getSourceLineWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setSourceLine(value);
        setSourceLineDd(getSourceLineDdWM().getValue());

        value = getStackTraceWM().getValue();
        if (value != null && value.trim().length() == 0)
            value = null;
        setStackTrace(value);
        setStackTraceDd(getStackTraceDdWM().getValue());

        // .//GEN-END:_doValidate_1_be
        // Add custom code //GEN-FIRST:_doValidate_1



        // .//GEN-LAST:_doValidate_1
        // .//GEN-BEGIN:_doValidate_2_be
        return valid;
    }
    // .//GEN-END:_doValidate_2_be
    // .//GEN-BEGIN:_populateRows_1_be
    /** This will populate the input GridModel with the data in the finderOutDto of the Component.
     * @param rows The GridModel object to populate.
     */
    public void populateRows(GridModel rows) {
        rows.clearRows();
        BusinessEventLogFinderOutDto outputDto = (BusinessEventLogFinderOutDto) ((BusinessEventLogFinderComponent) getComponent()).getFinderOutDto();
        if (outputDto != null) {
            GridModelRow row;
            for (int i = 0; i < outputDto.getRowsCount(); i++) {
                BusinessEventLogFinderOutRowDto rowDto = outputDto.getRows(i);
                row = rows.newRow();
                row.addElement("logId", rowDto.getLogId());
                row.addElement("correlationType", rowDto.getCorrelationType());
                row.addElement("correlationKey1", rowDto.getCorrelationKey1());
                row.addElement("correlationKey2", rowDto.getCorrelationKey2());
                row.addElement("correlationKey3", rowDto.getCorrelationKey3());
                row.addElement("scheduledTaskId", rowDto.getScheduledTaskId());
                row.addElement("messageId", rowDto.getMessageId());
                row.addElement("loggedOn", rowDto.getLoggedOn());
                row.addElement("loggedBy", rowDto.getLoggedBy());
                row.addElement("processName", rowDto.getProcessName());
                row.addElement("subProcessName", rowDto.getSubProcessName());
                row.addElement("messageType", rowDto.getMessageType());
                row.addElement("messageText", rowDto.getMessageText());
                row.addElement("sourceClass", rowDto.getSourceClass());
                row.addElement("sourceMethod", rowDto.getSourceMethod());
                row.addElement("sourceLine", rowDto.getSourceLine());
                row.addElement("stackTrace", rowDto.getStackTrace());
                // .//GEN-END:_populateRows_1_be
                // Add custom code for the row //GEN-FIRST:_populateRows_1


                // .//GEN-LAST:_populateRows_1
                // .//GEN-BEGIN:_populateRows_2_be
            }
        }
    }
    // .//GEN-END:_populateRows_2_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
