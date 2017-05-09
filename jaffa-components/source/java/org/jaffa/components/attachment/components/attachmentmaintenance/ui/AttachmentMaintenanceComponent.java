// .//GEN-BEGIN:_1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 *
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/
 package org.jaffa.components.attachment.components.attachmentmaintenance.ui;

import java.util.EventObject;
import java.util.List;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.component.Component;
import org.jaffa.presentation.portlet.FormKey;
import org.jaffa.middleware.Factory;
import org.jaffa.util.BeanHelper;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.components.codehelper.dto.*;
import org.jaffa.components.maint.*;
import org.jaffa.components.finder.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

import org.jaffa.components.attachment.components.attachmentmaintenance.IAttachmentMaintenance;
import org.jaffa.components.attachment.components.attachmentmaintenance.dto.*;


// .//GEN-END:_1_be
// Add additional imports//GEN-FIRST:_imports
import org.apache.struts.upload.FormFile;


// .//GEN-LAST:_imports
// .//GEN-BEGIN:_2_be
/** The controller for the AttachmentMaintenance.
 */
public class AttachmentMaintenanceComponent extends MaintComponent2 {

    private static Logger log = Logger.getLogger(AttachmentMaintenanceComponent.class);
    private IAttachmentMaintenance m_tx = null;

    private java.lang.String m_attachmentId = null;
    private java.lang.String m_serializedKey = null;
    private java.lang.String m_originalFileName = null;
    private java.lang.String m_attachmentType = null;
    private java.lang.String m_contentType = null;
    private java.lang.String m_description = null;
    private java.lang.String m_remarks = null;
    private java.lang.String m_supercededBy = null;
    private org.jaffa.datatypes.DateTime m_createdOn = null;
    private java.lang.String m_createdBy = null;
    private org.jaffa.datatypes.DateTime m_lastChangedOn = null;
    private java.lang.String m_lastChangedBy = null;
    private byte[] m_data = null;


    // .//GEN-END:_2_be
    // .//GEN-BEGIN:_quit_1_be
    /** This should be invoked when done with the component.
     */
    public void quit() {
        // .//GEN-END:_quit_1_be
        // Add custom code before processing the method//GEN-FIRST:_quit_1


        // .//GEN-LAST:_quit_1
        // .//GEN-BEGIN:_quit_2_be
        if (m_tx != null) {
            m_tx.destroy();
            m_tx = null;
        }
        super.quit();
    }
    // .//GEN-END:_quit_2_be
    // .//GEN-BEGIN:attachmentId_1_be
    /** Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public java.lang.String getAttachmentId() {
        return m_attachmentId;
    }

    /** Setter for property attachmentId.
     * @param attachmentId New value of property attachmentId.
     */
    public void setAttachmentId(java.lang.String attachmentId) {
        m_attachmentId = attachmentId;
    }
    // .//GEN-END:attachmentId_1_be
    // .//GEN-BEGIN:serializedKey_1_be
    /** Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public java.lang.String getSerializedKey() {
        return m_serializedKey;
    }

    /** Setter for property serializedKey.
     * @param serializedKey New value of property serializedKey.
     */
    public void setSerializedKey(java.lang.String serializedKey) {
        m_serializedKey = serializedKey;
    }
    // .//GEN-END:serializedKey_1_be
    // .//GEN-BEGIN:originalFileName_1_be
    /** Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public java.lang.String getOriginalFileName() {
        return m_originalFileName;
    }

    /** Setter for property originalFileName.
     * @param originalFileName New value of property originalFileName.
     */
    public void setOriginalFileName(java.lang.String originalFileName) {
        m_originalFileName = originalFileName;
    }
    // .//GEN-END:originalFileName_1_be
    // .//GEN-BEGIN:attachmentType_1_be
    /** Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public java.lang.String getAttachmentType() {
        return m_attachmentType;
    }

    /** Setter for property attachmentType.
     * @param attachmentType New value of property attachmentType.
     */
    public void setAttachmentType(java.lang.String attachmentType) {
        m_attachmentType = attachmentType;
    }
    // .//GEN-END:attachmentType_1_be
    // .//GEN-BEGIN:contentType_1_be
    /** Getter for property contentType.
     * @return Value of property contentType.
     */
    public java.lang.String getContentType() {
        return m_contentType;
    }

    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     */
    public void setContentType(java.lang.String contentType) {
        m_contentType = contentType;
    }
    // .//GEN-END:contentType_1_be
    // .//GEN-BEGIN:description_1_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return m_description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        m_description = description;
    }
    // .//GEN-END:description_1_be
    // .//GEN-BEGIN:remarks_1_be
    /** Getter for property remarks.
     * @return Value of property remarks.
     */
    public java.lang.String getRemarks() {
        return m_remarks;
    }

    /** Setter for property remarks.
     * @param remarks New value of property remarks.
     */
    public void setRemarks(java.lang.String remarks) {
        m_remarks = remarks;
    }
    // .//GEN-END:remarks_1_be
    // .//GEN-BEGIN:supercededBy_1_be
    /** Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public java.lang.String getSupercededBy() {
        return m_supercededBy;
    }

    /** Setter for property supercededBy.
     * @param supercededBy New value of property supercededBy.
     */
    public void setSupercededBy(java.lang.String supercededBy) {
        m_supercededBy = supercededBy;
    }
    // .//GEN-END:supercededBy_1_be
    // .//GEN-BEGIN:createdOn_1_be
    /** Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public org.jaffa.datatypes.DateTime getCreatedOn() {
        return m_createdOn;
    }

    /** Setter for property createdOn.
     * @param createdOn New value of property createdOn.
     */
    public void setCreatedOn(org.jaffa.datatypes.DateTime createdOn) {
        m_createdOn = createdOn;
    }
    // .//GEN-END:createdOn_1_be
    // .//GEN-BEGIN:createdBy_1_be
    /** Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public java.lang.String getCreatedBy() {
        return m_createdBy;
    }

    /** Setter for property createdBy.
     * @param createdBy New value of property createdBy.
     */
    public void setCreatedBy(java.lang.String createdBy) {
        m_createdBy = createdBy;
    }
    // .//GEN-END:createdBy_1_be
    // .//GEN-BEGIN:lastChangedOn_1_be
    /** Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public org.jaffa.datatypes.DateTime getLastChangedOn() {
        return m_lastChangedOn;
    }

    /** Setter for property lastChangedOn.
     * @param lastChangedOn New value of property lastChangedOn.
     */
    public void setLastChangedOn(org.jaffa.datatypes.DateTime lastChangedOn) {
        m_lastChangedOn = lastChangedOn;
    }
    // .//GEN-END:lastChangedOn_1_be
    // .//GEN-BEGIN:lastChangedBy_1_be
    /** Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public java.lang.String getLastChangedBy() {
        return m_lastChangedBy;
    }

    /** Setter for property lastChangedBy.
     * @param lastChangedBy New value of property lastChangedBy.
     */
    public void setLastChangedBy(java.lang.String lastChangedBy) {
        m_lastChangedBy = lastChangedBy;
    }
    // .//GEN-END:lastChangedBy_1_be
    // .//GEN-BEGIN:data_1_be
    /** Getter for property data.
     * @return Value of property data.
     */
    public byte[] getData() {
        return m_data;
    }

    /** Setter for property data.
     * @param data New value of property data.
     */
    public void setData(byte[] data) {
        m_data = data;
    }
    // .//GEN-END:data_1_be
    // .//GEN-BEGIN:_doCreate_1_be
    /** This will invoke the create method on the transaction to create a new domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doCreate() throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceCreateInDto input = new AttachmentMaintenanceCreateInDto();
        // .//GEN-END:_doCreate_1_be
        // Add custom code//GEN-FIRST:_doCreate_1


        // .//GEN-LAST:_doCreate_1
        // .//GEN-BEGIN:_doCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setAttachmentId(getAttachmentId());
        input.setSerializedKey(getSerializedKey());
        input.setOriginalFileName(getOriginalFileName());
        input.setAttachmentType(getAttachmentType());
        input.setContentType(getContentType());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setSupercededBy(getSupercededBy());
        input.setData(getData());
        AttachmentMaintenanceRetrieveOutDto output = createTx().create(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doCreate_2_be
        // Add custom code//GEN-FIRST:_doCreate_2


        // .//GEN-LAST:_doCreate_2
        // .//GEN-BEGIN:_doCreate_3_be
    }
    // .//GEN-END:_doCreate_3_be
    // .//GEN-BEGIN:_doUpdate_1_be
    /** This will invoke the update method on the transaction to update an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceUpdateInDto input = new AttachmentMaintenanceUpdateInDto();
        // .//GEN-END:_doUpdate_1_be
        // Add custom code//GEN-FIRST:_doUpdate_1


        // .//GEN-LAST:_doUpdate_1
        // .//GEN-BEGIN:_doUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setAttachmentId(getAttachmentId());
        input.setSerializedKey(getSerializedKey());
        input.setOriginalFileName(getOriginalFileName());
        input.setAttachmentType(getAttachmentType());
        input.setContentType(getContentType());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setSupercededBy(getSupercededBy());
        input.setData(getData());
        input.setLastChangedOn(getLastChangedOn());
        AttachmentMaintenanceRetrieveOutDto output = createTx().update(input);
        loadRetrieveOutDto(output);
        // .//GEN-END:_doUpdate_2_be
        // Add custom code//GEN-FIRST:_doUpdate_2


        // .//GEN-LAST:_doUpdate_2
        // .//GEN-BEGIN:_doUpdate_3_be
    }
    // .//GEN-END:_doUpdate_3_be
    // .//GEN-BEGIN:_doDelete_1_be
    /** This will invoke the delete method on the transaction to delete an existing domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to a delete.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doDelete(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceDeleteInDto input = new AttachmentMaintenanceDeleteInDto();
        // .//GEN-END:_doDelete_1_be
        // Add custom code//GEN-FIRST:_doDelete_1


        // .//GEN-LAST:_doDelete_1
        // .//GEN-BEGIN:_doDelete_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setAttachmentId(getAttachmentId());
        input.setLastChangedOn(getLastChangedOn());
        createTx().delete(input);
        // .//GEN-END:_doDelete_2_be
        // Add custom code//GEN-FIRST:_doDelete_2


        // .//GEN-LAST:_doDelete_2
        // .//GEN-BEGIN:_doDelete_3_be
    }
    // .//GEN-END:_doDelete_3_be
    // .//GEN-BEGIN:_doRetrieve_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doRetrieve() throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceRetrieveOutDto output = obtainRetrieveOutDto();
        loadRetrieveOutDto(output);
    }
    // .//GEN-END:_doRetrieve_1_be
    // .//GEN-BEGIN:_addScreens_1_be
    /** This sets up the screen information.
     * @param screens The component should add MaintComponent2.Screen objects to this list.
     */
    protected void addScreens(List screens) {
        screens.add(new MaintComponent2.Screen("jaffa_attachment_attachmentMaintenance_main", true, true, true, true));
        // .//GEN-END:_addScreens_1_be
        // Add custom code//GEN-FIRST:_addScreens_1


        // .//GEN-LAST:_addScreens_1
        // .//GEN-BEGIN:_addScreens_2_be
    }
    // .//GEN-END:_addScreens_2_be
    // .//GEN-BEGIN:_doPrevalidateCreate_1_be
    /** This performs prevalidations before creating a domain object.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateCreate() throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceCreateInDto input = new AttachmentMaintenanceCreateInDto();
        // .//GEN-END:_doPrevalidateCreate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_1


        // .//GEN-LAST:_doPrevalidateCreate_1
        // .//GEN-BEGIN:_doPrevalidateCreate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setAttachmentId(getAttachmentId());
        input.setSerializedKey(getSerializedKey());
        input.setOriginalFileName(getOriginalFileName());
        input.setAttachmentType(getAttachmentType());
        input.setContentType(getContentType());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setSupercededBy(getSupercededBy());
        input.setData(getData());
        AttachmentMaintenancePrevalidateOutDto output = createTx().prevalidateCreate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateCreate_2_be
        // Add custom code//GEN-FIRST:_doPrevalidateCreate_2


        // .//GEN-LAST:_doPrevalidateCreate_2
        // .//GEN-BEGIN:_doPrevalidateCreate_3_be
    }
    // .//GEN-END:_doPrevalidateCreate_3_be
    // .//GEN-BEGIN:_doPrevalidateUpdate_1_be
    /** This performs prevalidations before updating a domain object.
     * @param performDirtyReadCheck this will determine if the Dirty Read check if to be performed prior to an update.
     * @throws ApplicationExceptions This will be thrown in case any invalid data has been set.
     * @throws FrameworkException Indicates some system error.
     */
    protected void doPrevalidateUpdate(boolean performDirtyReadCheck) throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceUpdateInDto input = new AttachmentMaintenanceUpdateInDto();
        // .//GEN-END:_doPrevalidateUpdate_1_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_1


        // .//GEN-LAST:_doPrevalidateUpdate_1
        // .//GEN-BEGIN:_doPrevalidateUpdate_2_be
        input.setHeaderDto(getHeaderDto());
        input.setPerformDirtyReadCheck(new Boolean(performDirtyReadCheck));
        input.setAttachmentId(getAttachmentId());
        input.setSerializedKey(getSerializedKey());
        input.setOriginalFileName(getOriginalFileName());
        input.setAttachmentType(getAttachmentType());
        input.setContentType(getContentType());
        input.setDescription(getDescription());
        input.setRemarks(getRemarks());
        input.setSupercededBy(getSupercededBy());
        input.setData(getData());
        input.setLastChangedOn(getLastChangedOn());
        AttachmentMaintenancePrevalidateOutDto output = createTx().prevalidateUpdate(input);
        loadPrevalidateOutDto(output);
        // .//GEN-END:_doPrevalidateUpdate_2_be
        // Add custom code//GEN-FIRST:_doPrevalidateUpdate_2


        // .//GEN-LAST:_doPrevalidateUpdate_2
        // .//GEN-BEGIN:_doPrevalidateUpdate_3_be
    }
    // .//GEN-END:_doPrevalidateUpdate_3_be
    // .//GEN-BEGIN:_initDropDownCodes_1_be
    /** This will retrieve the set of codes for dropdowns, if any are required
     */
    protected void initDropDownCodes() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = null;
        CodeHelperInDto input = null;




    }
    // .//GEN-END:_initDropDownCodes_1_be
    // .//GEN-BEGIN:_createTx_1_be
    private IAttachmentMaintenance createTx() throws FrameworkException {
        if (m_tx == null)
            m_tx = (IAttachmentMaintenance) Factory.createObject(IAttachmentMaintenance.class);
        return m_tx;
    }
    // .//GEN-END:_createTx_1_be
    // .//GEN-BEGIN:_obtainRetrieveOutDto_1_be
    /** This will invoke the retrieve method on the transaction to retrieve an existing domain object.
     * @throws ApplicationExceptions Indicates some functional error.
     * @throws FrameworkException Indicates some system error.
     * @return the AttachmentMaintenanceRetrieveOutDto containing the output of the retrieve.
     */
    private AttachmentMaintenanceRetrieveOutDto obtainRetrieveOutDto() throws ApplicationExceptions, FrameworkException {
        AttachmentMaintenanceRetrieveInDto input = new AttachmentMaintenanceRetrieveInDto();
        // .//GEN-END:_obtainRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_1


        // .//GEN-LAST:_obtainRetrieveOutDto_1
        // .//GEN-BEGIN:_obtainRetrieveOutDto_2_be
        input.setHeaderDto(getHeaderDto());
        input.setAttachmentId(getAttachmentId());
        AttachmentMaintenanceRetrieveOutDto output = createTx().retrieve(input);
        // .//GEN-END:_obtainRetrieveOutDto_2_be
        // Add custom code//GEN-FIRST:_obtainRetrieveOutDto_2


        // .//GEN-LAST:_obtainRetrieveOutDto_2
        // .//GEN-BEGIN:_obtainRetrieveOutDto_3_be
        return output;
    }
    // .//GEN-END:_obtainRetrieveOutDto_3_be
    // .//GEN-BEGIN:_loadRetrieveOutDto_1_be
    /** This will load the contents of AttachmentMaintenanceRetrieveOutDto into the component.
     */
    private void loadRetrieveOutDto(AttachmentMaintenanceRetrieveOutDto retrieveOutDto) {
        // clear the widget cache
        uncacheWidgetModels();

        if (retrieveOutDto != null) {
            setAttachmentId(retrieveOutDto.getAttachmentId());
            setSerializedKey(retrieveOutDto.getSerializedKey());
            setOriginalFileName(retrieveOutDto.getOriginalFileName());
            setAttachmentType(retrieveOutDto.getAttachmentType());
            setContentType(retrieveOutDto.getContentType());
            setDescription(retrieveOutDto.getDescription());
            setRemarks(retrieveOutDto.getRemarks());
            setSupercededBy(retrieveOutDto.getSupercededBy());
            setCreatedOn(retrieveOutDto.getCreatedOn());
            setCreatedBy(retrieveOutDto.getCreatedBy());
            setLastChangedOn(retrieveOutDto.getLastChangedOn());
            setLastChangedBy(retrieveOutDto.getLastChangedBy());
            setData(retrieveOutDto.getData());
        }
        // .//GEN-END:_loadRetrieveOutDto_1_be
        // Add custom code//GEN-FIRST:_loadRetrieveOutDto_1
        if ("L".equals(getAttachmentType())) {
            setLocalLink(getOriginalFileName());
            setWebLink(null);
            setEmbeddedFileName(null);
        } else if ("W".equals(getAttachmentType())) {
            setLocalLink(null);
            setWebLink(getOriginalFileName());
            setEmbeddedFileName(null);
        } else if ("E".equals(getAttachmentType())) {
            setLocalLink(null);
            setWebLink(null);
            setEmbeddedFileName(getOriginalFileName());
        }
        
        // .//GEN-LAST:_loadRetrieveOutDto_1
        // .//GEN-BEGIN:_loadRetrieveOutDto_2_be
    }
    // .//GEN-END:_loadRetrieveOutDto_2_be
    // .//GEN-BEGIN:_loadPrevalidateOutDto_1_be
    /** This will load the contents of AttachmentMaintenancePrevalidateOutDto into the component.
     */
    private void loadPrevalidateOutDto(AttachmentMaintenancePrevalidateOutDto prevalidateOutDto) {
        if (prevalidateOutDto != null) {
            setAttachmentId(prevalidateOutDto.getAttachmentId());
            setSerializedKey(prevalidateOutDto.getSerializedKey());
            setOriginalFileName(prevalidateOutDto.getOriginalFileName());
            setAttachmentType(prevalidateOutDto.getAttachmentType());
            setContentType(prevalidateOutDto.getContentType());
            setDescription(prevalidateOutDto.getDescription());
            setRemarks(prevalidateOutDto.getRemarks());
            setSupercededBy(prevalidateOutDto.getSupercededBy());
            setCreatedOn(prevalidateOutDto.getCreatedOn());
            setCreatedBy(prevalidateOutDto.getCreatedBy());
            setLastChangedOn(prevalidateOutDto.getLastChangedOn());
            setLastChangedBy(prevalidateOutDto.getLastChangedBy());
            setData(prevalidateOutDto.getData());
        }
        // .//GEN-END:_loadPrevalidateOutDto_1_be
        // Add custom code//GEN-FIRST:_loadPrevalidateOutDto_1


        // .//GEN-LAST:_loadPrevalidateOutDto_1
        // .//GEN-BEGIN:_loadPrevalidateOutDto_2_be
    }
    // .//GEN-END:_loadPrevalidateOutDto_2_be
    // All the custom code goes here//GEN-FIRST:_custom
    
    private String m_localLink = null;
    private String m_webLink = null;
    private String m_embeddedFileName = null;
    private FormFile m_embeddedFile = null;
    
    /** Getter for property localLink.
     * @return Value of property localLink.
     */
    public String getLocalLink() {
        return m_localLink;
    }
    
    /** Setter for property localLink.
     * @param localLink New value of property localLink.
     */
    public void setLocalLink(String localLink) {
        m_localLink = localLink;
    }
    
    /** Getter for property webLink.
     * @return Value of property webLink.
     */
    public String getWebLink() {
        return m_webLink;
    }
    
    /** Setter for property webLink.
     * @param webLink New value of property webLink.
     */
    public void setWebLink(String webLink) {
        m_webLink = webLink;
    }
    
    /** Getter for property embeddedFileName.
     * @return Value of property embeddedFileName.
     */
    public String getEmbeddedFileName() {
        return m_embeddedFileName;
    }
    
    /** Setter for property embeddedFileName.
     * @param embeddedFileName New value of property embeddedFileName.
     */
    public void setEmbeddedFileName(String embeddedFileName) {
        m_embeddedFileName = embeddedFileName;
    }
    
    /** Getter for property embeddedFile.
     * @return Value of property embeddedFile.
     */
    public FormFile getEmbeddedFile() {
        return m_embeddedFile;
    }
    
    /** Setter for property embeddedFile.
     * @param embeddedFile New value of property embeddedFile.
     */
    public void setEmbeddedFile(FormFile embeddedFile) {
        m_embeddedFile = embeddedFile;
    }
    
    
    // .//GEN-LAST:_custom
}
