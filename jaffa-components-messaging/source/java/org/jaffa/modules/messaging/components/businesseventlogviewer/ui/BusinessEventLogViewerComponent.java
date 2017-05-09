// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.messaging.components.businesseventlogviewer.ui;

import org.apache.log4j.Logger;
import java.util.EventObject;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.datatypes.exceptions.MandatoryFieldException;
import org.jaffa.middleware.Factory;
import org.jaffa.components.finder.OrderByField;
import org.jaffa.components.maint.*;
import org.jaffa.components.dto.HeaderDto;

import org.jaffa.modules.messaging.components.businesseventlogviewer.IBusinessEventLogViewer;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerInDto;
import org.jaffa.modules.messaging.components.businesseventlogviewer.dto.BusinessEventLogViewerOutDto;
import org.jaffa.modules.messaging.domain.BusinessEventLog;
import org.jaffa.modules.messaging.domain.BusinessEventLogMeta;


import org.jaffa.components.attachment.components.attachmentviewer.ui.AttachmentViewerComponent;

// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the BusinessEventLogViewer.
 */
public class BusinessEventLogViewerComponent extends Component {
    
    private static Logger log = Logger.getLogger(BusinessEventLogViewerComponent.class);
    private HeaderDto m_headerDto = null;

    private java.lang.String m_logId;
    private BusinessEventLogViewerOutDto m_outputDto = null;
    private IBusinessEventLogViewer m_tx = null;

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

        m_outputDto = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:logId_1_be
    /** Getter for property logId.
     * @return Value of property logId.
     */
    public java.lang.String getLogId() {
        return m_logId;
    }
    
    /** Setter for property logId.
     * @param logId New value of property logId.
     */
    public void setLogId(java.lang.String logId) {
        m_logId = logId;
    }
    // .//GEN-END:logId_1_be
    // .//GEN-BEGIN:_BusinessEventLogViewerOutDto_1_be
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */    
    public BusinessEventLogViewerOutDto getBusinessEventLogViewerOutDto() {
        return m_outputDto;
    }
    
    /** Setter for property outputDto.
     * @param outputDto New value of property outputDto.
     */    
    public void setBusinessEventLogViewerOutDto(BusinessEventLogViewerOutDto outputDto) {
        m_outputDto = outputDto;
    }
    // .//GEN-END:_BusinessEventLogViewerOutDto_1_be
    // .//GEN-BEGIN:_display_1_be
    /** This retrieves the details for the BusinessEventLog.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set, or if no data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */    
    public FormKey display() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        // .//GEN-END:_display_1_be
        // Add custom code before processing the method //GEN-FIRST:_display_1


        // .//GEN-LAST:_display_1
        // .//GEN-BEGIN:_display_2_be
        if (getLogId() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(BusinessEventLogMeta.META_LOG_ID.getLabelToken()) );
        }
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        doInquiry();
        return getViewerFormKey();
    }
    // .//GEN-END:_display_2_be
    // .//GEN-BEGIN:_inquiry_1_be
    private void doInquiry() throws ApplicationExceptions, FrameworkException {
        BusinessEventLogViewerInDto inputDto = new BusinessEventLogViewerInDto();
        // .//GEN-END:_inquiry_1_be
        // Add custom code before building the input dto //GEN-FIRST:_inquiry_1


        // .//GEN-LAST:_inquiry_1
        // .//GEN-BEGIN:_inquiry_2_be
        inputDto.setLogId(m_logId);

        inputDto.setHeaderDto(createHeaderDto());

        // create the Tx
        if (m_tx == null)
            m_tx = (IBusinessEventLogViewer) Factory.createObject(IBusinessEventLogViewer.class);

        // .//GEN-END:_inquiry_2_be
        // Add custom code before invoking the Tx //GEN-FIRST:_inquiry_2


        // .//GEN-LAST:_inquiry_2
        // .//GEN-BEGIN:_inquiry_3_be
        // now get the details
        m_outputDto = m_tx.read(inputDto);

        // uncache the widgets
        getUserSession().getWidgetCache(getComponentId()).clear();
        // .//GEN-END:_inquiry_3_be
        // Add custom code after invoking the Tx //GEN-FIRST:_inquiry_3


        // .//GEN-LAST:_inquiry_3
        // .//GEN-BEGIN:_inquiry_4_be
        // throw an exception if the output is null
        if (m_outputDto == null) {
            ApplicationExceptions appExps = new ApplicationExceptions();
            appExps.add(new DomainObjectNotFoundException(BusinessEventLogMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_inquiry_4_be
    // .//GEN-BEGIN:viewAttachment_1_be
    /** This invokes the AttachmentViewerComponent screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */    
    public FormKey viewAttachment(java.lang.String attachmentId) throws ApplicationExceptions, FrameworkException {
        AttachmentViewerComponent attachmentViewerComponent = (AttachmentViewerComponent) run("Jaffa.Attachment.AttachmentViewer");
        attachmentViewerComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        attachmentViewerComponent.setAttachmentId(attachmentId);
        // .//GEN-END:viewAttachment_1_be
        // Add custom code before invoking the component //GEN-FIRST:viewAttachment_1


        // .//GEN-LAST:viewAttachment_1
        // .//GEN-BEGIN:viewAttachment_2_be
        return attachmentViewerComponent.display();
    }
    // .//GEN-END:viewAttachment_2_be
    // .//GEN-BEGIN:_createHeaderDto_1_be
    private HeaderDto createHeaderDto() {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId( getUserSession().getUserId() );
            m_headerDto.setVariation( getUserSession().getVariation() );
            // .//GEN-END:_createHeaderDto_1_be
            // Add custom code before processing the action //GEN-FIRST:_createHeaderDto_1


            // .//GEN-LAST:_createHeaderDto_1
            // .//GEN-BEGIN:_createHeaderDto_2_be
        }
        return m_headerDto;
    }
    // .//GEN-END:_createHeaderDto_2_be
    // .//GEN-BEGIN:_getViewerFormKey_1_be
    public FormKey getViewerFormKey() {
        return new FormKey(BusinessEventLogViewerForm.NAME, getComponentId());
    }
    // .//GEN-END:_getViewerFormKey_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
