// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.modules.printing.components.formeventviewer.ui;

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

import org.jaffa.modules.printing.components.formeventviewer.IFormEventViewer;
import org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerInDto;
import org.jaffa.modules.printing.components.formeventviewer.dto.FormEventViewerOutDto;
import org.jaffa.modules.printing.domain.FormEvent;
import org.jaffa.modules.printing.domain.FormEventMeta;


import org.jaffa.modules.printing.components.formusageviewer.ui.FormUsageViewerComponent;
import org.jaffa.modules.printing.components.formusagemaintenance.ui.FormUsageMaintenanceComponent;

import org.jaffa.modules.printing.components.formeventmaintenance.ui.FormEventMaintenanceComponent;
// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports




// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the FormEventViewer.
 */
public class FormEventViewerComponent extends Component {
    
    private static Logger log = Logger.getLogger(FormEventViewerComponent.class);
    private HeaderDto m_headerDto = null;

    private java.lang.String m_eventName;
    private FormEventViewerOutDto m_outputDto = null;
    private IFormEventViewer m_tx = null;

    private FormEventMaintenanceComponent m_updateComponent = null;
    private IUpdateListener m_updateListener = null;
    private FormEventMaintenanceComponent m_deleteComponent = null;
    private IDeleteListener m_deleteListener = null;
    private FormUsageMaintenanceComponent m_updateFormUsage = null;
    private IUpdateListener m_updateListenerFormUsage = null;
    private FormUsageMaintenanceComponent m_deleteFormUsage = null;
    private IDeleteListener m_deleteListenerFormUsage = null;
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
        if (m_updateComponent != null) {
            m_updateComponent.quit();
            m_updateComponent = null;
        }
        m_updateListener = null;
        if (m_deleteComponent != null) {
            m_deleteComponent.quit();
            m_deleteComponent = null;
        }
        m_deleteListener = null;
        if (m_updateFormUsage != null) {
            m_updateFormUsage.quit();
            m_updateFormUsage = null;
        }
        m_updateListenerFormUsage = null;
        if (m_deleteFormUsage != null) {
            m_deleteFormUsage.quit();
            m_deleteFormUsage = null;
        }
        m_deleteListenerFormUsage = null;

        m_outputDto = null;
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:eventName_1_be
    /** Getter for property eventName.
     * @return Value of property eventName.
     */
    public java.lang.String getEventName() {
        return m_eventName;
    }
    
    /** Setter for property eventName.
     * @param eventName New value of property eventName.
     */
    public void setEventName(java.lang.String eventName) {
        m_eventName = eventName;
    }
    // .//GEN-END:eventName_1_be
    // .//GEN-BEGIN:_FormEventViewerOutDto_1_be
    /** Getter for property outputDto.
     * @return Value of property outputDto.
     */    
    public FormEventViewerOutDto getFormEventViewerOutDto() {
        return m_outputDto;
    }
    
    /** Setter for property outputDto.
     * @param outputDto New value of property outputDto.
     */    
    public void setFormEventViewerOutDto(FormEventViewerOutDto outputDto) {
        m_outputDto = outputDto;
    }
    // .//GEN-END:_FormEventViewerOutDto_1_be
    // .//GEN-BEGIN:_display_1_be
    /** This retrieves the details for the FormEvent.
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
        if (getEventName() == null) {
            if (appExps == null)
                appExps = new ApplicationExceptions();
            appExps.add(new MandatoryFieldException(FormEventMeta.META_EVENT_NAME.getLabelToken()) );
        }
        if (appExps != null && appExps.size() > 0)
            throw appExps;

        doInquiry();
        return getViewerFormKey();
    }
    // .//GEN-END:_display_2_be
    // .//GEN-BEGIN:_inquiry_1_be
    private void doInquiry() throws ApplicationExceptions, FrameworkException {
        FormEventViewerInDto inputDto = new FormEventViewerInDto();
        // .//GEN-END:_inquiry_1_be
        // Add custom code before building the input dto //GEN-FIRST:_inquiry_1


        // .//GEN-LAST:_inquiry_1
        // .//GEN-BEGIN:_inquiry_2_be
        inputDto.setEventName(m_eventName);

        inputDto.setHeaderDto(createHeaderDto());

        // create the Tx
        if (m_tx == null)
            m_tx = (IFormEventViewer) Factory.createObject(IFormEventViewer.class);

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
            appExps.add(new DomainObjectNotFoundException(FormEventMeta.getLabelToken()));
            throw appExps;
        }
    }
    // .//GEN-END:_inquiry_4_be
    // .//GEN-BEGIN:viewFormUsage_1_be
    /** This invokes the FormUsageViewerComponent screen.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the View screen.
     */    
    public FormKey viewFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException {
        FormUsageViewerComponent formUsageViewerComponent = (FormUsageViewerComponent) run("Jaffa.Printing.FormUsageViewer");
        formUsageViewerComponent.setReturnToFormKey(FormKey.getCloseBrowserFormKey());
        formUsageViewerComponent.setFormName(formName);
        // .//GEN-END:viewFormUsage_1_be
        // Add custom code before invoking the component //GEN-FIRST:viewFormUsage_1


        // .//GEN-LAST:viewFormUsage_1
        // .//GEN-BEGIN:viewFormUsage_2_be
        return formUsageViewerComponent.display();
    }
    // .//GEN-END:viewFormUsage_2_be
    // .//GEN-BEGIN:_updateFormUsage_1_be
    /** Calls the Jaffa.Printing.FormUsageMaintenance component for updating the FormUsage object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException {
        if (m_updateFormUsage == null || !m_updateFormUsage.isActive()) {
            m_updateFormUsage = (FormUsageMaintenanceComponent) run("Jaffa.Printing.FormUsageMaintenance");
            m_updateFormUsage.setReturnToFormKey(getViewerFormKey());
            addListenersFormUsage(m_updateFormUsage);
        }
        m_updateFormUsage.setFormName(formName);
        if (m_updateFormUsage instanceof IMaintComponent)
            ((IMaintComponent) m_updateFormUsage).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateFormUsage_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateFormUsage_2


        // .//GEN-LAST:_updateFormUsage_2
        // .//GEN-BEGIN:_updateFormUsage_2_be
        return m_updateFormUsage.display();
    }

    private IUpdateListener getUpdateListenerFormUsage() {
        if (m_updateListenerFormUsage == null) {
            m_updateListenerFormUsage = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateFormUsage_2_be
                        // Add custom code //GEN-FIRST:_updateFormUsage_1


                        // .//GEN-LAST:_updateFormUsage_1
                        // .//GEN-BEGIN:_updateFormUsage_3_be
                        doInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListenerFormUsage;
    }
    // .//GEN-END:_updateFormUsage_3_be
    // .//GEN-BEGIN:_deleteFormUsage_1_be
    /** Calls the Jaffa.Printing.FormUsageMaintenance component for deleting the FormUsage object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteFormUsage(java.lang.String formName) throws ApplicationExceptions, FrameworkException {
        if (m_deleteFormUsage == null || !m_deleteFormUsage.isActive()) {
            m_deleteFormUsage = (FormUsageMaintenanceComponent) run("Jaffa.Printing.FormUsageMaintenance");
            m_deleteFormUsage.setReturnToFormKey(getViewerFormKey());
            addListenersFormUsage(m_deleteFormUsage);
        }
        m_deleteFormUsage.setFormName(formName);
        if (m_deleteFormUsage instanceof IMaintComponent)
            ((IMaintComponent) m_deleteFormUsage).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteFormUsage_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteFormUsage_2


        // .//GEN-LAST:_deleteFormUsage_2
        // .//GEN-BEGIN:_deleteFormUsage_2_be
        return m_deleteFormUsage.display();
    }

    private IDeleteListener getDeleteListenerFormUsage() {
        if (m_deleteListenerFormUsage == null) {
            m_deleteListenerFormUsage = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteFormUsage_2_be
                        // Add custom code //GEN-FIRST:_deleteFormUsage_1


                        // .//GEN-LAST:_deleteFormUsage_1
                        // .//GEN-BEGIN:_deleteFormUsage_3_be
                        doInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListenerFormUsage;
    }
    // .//GEN-END:_deleteFormUsage_3_be
    // .//GEN-BEGIN:_addListenersFormUsage_1_be
    private void addListenersFormUsage(Component comp) {
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListenerFormUsage());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListenerFormUsage());
    }
    // .//GEN-END:_addListenersFormUsage_1_be
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
        return new FormKey(FormEventViewerForm.NAME, getComponentId());
    }
    // .//GEN-END:_getViewerFormKey_1_be
    // .//GEN-BEGIN:_updateObject_1_be
    /** Calls the Jaffa.Printing.FormEventMaintenance component for updating the FormEvent object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Update screen.
     */
    public FormKey updateObject() throws ApplicationExceptions, FrameworkException {
        if (m_updateComponent == null || !m_updateComponent.isActive()) {
            m_updateComponent = (FormEventMaintenanceComponent) run("Jaffa.Printing.FormEventMaintenance");
            m_updateComponent.setReturnToFormKey(getViewerFormKey());
            addListeners(m_updateComponent);
        }
        m_updateComponent.setEventName(getEventName());
        if (m_updateComponent instanceof IMaintComponent)
            ((IMaintComponent) m_updateComponent).setMode(IMaintComponent.MODE_UPDATE);
        // .//GEN-END:_updateObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_updateObject_2


        // .//GEN-LAST:_updateObject_2
        // .//GEN-BEGIN:_updateObject_2_be
        return m_updateComponent.display();
    }

    private IUpdateListener getUpdateListener() {
        if (m_updateListener == null) {
            m_updateListener = new IUpdateListener() {
                public void updateDone(EventObject source) {
                    try {
                        // .//GEN-END:_updateObject_2_be
                        // Add custom code //GEN-FIRST:_updateObject_1


                        // .//GEN-LAST:_updateObject_1
                        // .//GEN-BEGIN:_updateObject_3_be
                        doInquiry();
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Update", e);
                    }
                }
            };
        }
        return m_updateListener;
    }
    // .//GEN-END:_updateObject_3_be
    // .//GEN-BEGIN:_deleteObject_1_be
    /** Calls the Jaffa.Printing.FormEventMaintenance component for deleting the FormEvent object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     * @return The FormKey for the Delete screen.
     */
    public FormKey deleteObject() throws ApplicationExceptions, FrameworkException {
        if (m_deleteComponent == null || !m_deleteComponent.isActive()) {
            m_deleteComponent = (FormEventMaintenanceComponent) run("Jaffa.Printing.FormEventMaintenance");
            m_deleteComponent.setReturnToFormKey(getViewerFormKey());
            addListeners(m_deleteComponent);
        }
        m_deleteComponent.setEventName(getEventName());
        if (m_deleteComponent instanceof IMaintComponent)
            ((IMaintComponent) m_deleteComponent).setMode(IMaintComponent.MODE_DELETE);
        // .//GEN-END:_deleteObject_1_be
        // Add custom code before invoking the component //GEN-FIRST:_deleteObject_2


        // .//GEN-LAST:_deleteObject_2
        // .//GEN-BEGIN:_deleteObject_2_be
        return m_deleteComponent.display();
    }

    private IDeleteListener getDeleteListener() {
        if (m_deleteListener == null) {
            m_deleteListener = new IDeleteListener() {
                public void deleteDone(EventObject source) {
                    try {
                        // .//GEN-END:_deleteObject_2_be
                        // Add custom code //GEN-FIRST:_deleteObject_1


                        // .//GEN-LAST:_deleteObject_1
                        // .//GEN-BEGIN:_deleteObject_3_be
                        // Quit this component and reset the ReturnFormKey on the DeleteComponent so as to close the browser
                        ((Component) source.getSource()).setReturnToFormKey(quitAndReturnToCallingScreen());
                    } catch (Exception e) {
                        log.warn("Error in refreshing the Results screen after the Delete", e);
                    }
                }
            };
        }
        return m_deleteListener;
    }
    // .//GEN-END:_deleteObject_3_be
    // .//GEN-BEGIN:_addListeners_1_be
    private void addListeners(Component comp) {
        if (comp  instanceof IUpdateComponent)
            ((IUpdateComponent) comp).addUpdateListener(getUpdateListener());
        if (comp  instanceof IDeleteComponent)
            ((IDeleteComponent) comp).addDeleteListener(getDeleteListener());
    }
    // .//GEN-END:_addListeners_1_be
    // All the custom code goes here //GEN-FIRST:_custom






    // .//GEN-LAST:_custom
}
