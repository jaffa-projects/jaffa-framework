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
package org.jaffa.components.attachment.apis.data;

import org.jaffa.beans.factory.config.StaticContext;
import org.jaffa.datatypes.DateTime;
import org.jaffa.soa.graph.GraphDataObject;

/**
 * This class is used to manage domain data in graph-based service.
 *
 * @author GautamJ
 */
public class AttachmentGraph extends GraphDataObject {

    private String attachmentId;
    private String documentReferenceId;
    private String serializedKey;
    private String originalFileName;
    private String attachmentType;
    private String contentType;
    private String description;
    private String remarks;
    private String supercededBy;
    private DateTime createdOn;
    private String createdBy;
    private DateTime lastChangedOn;
    private String lastChangedBy;
    private byte[] data;

    /**
     * Default constructor.
     */
    public AttachmentGraph() {
        StaticContext.configure(this);
    }

    /**
     * Getter for property attachmentId.
     *
     * @return Value of property attachmentId.
     */
    public String getAttachmentId() {
        return attachmentId;
    }

    /**
     * Setter for property attachmentId.
     *
     * @param attachmentId Value of property attachmentId.
     */
    public void setAttachmentId(String attachmentId) {
        String oldAttachmentId = this.attachmentId;
        this.attachmentId = attachmentId;
        propertyChangeSupport.firePropertyChange("attachmentId", oldAttachmentId, attachmentId);
    }

    /**
     * Getter for property documentReferenceId.
     *
     * @return Value of property documentReferenceId.
     */
    public String getDocumentReferenceId() {
        return documentReferenceId;
    }

    /**
     * Setter for property documentReferenceId.
     *
     * @param documentReferenceId Value of property documentReferenceId.
     */
    public void setDocumentReferenceId(String documentReferenceId) {
        String oldDocumentReferenceId = this.documentReferenceId;
        this.documentReferenceId = documentReferenceId;
        propertyChangeSupport.firePropertyChange("documentReferenceId", oldDocumentReferenceId, documentReferenceId);
    }

    /**
     * Getter for property serializedKey.
     *
     * @return Value of property serializedKey.
     */
    public String getSerializedKey() {
        return serializedKey;
    }

    /**
     * Setter for property serializedKey.
     *
     * @param serializedKey Value of property serializedKey.
     */
    public void setSerializedKey(String serializedKey) {
        String oldSerializedKey = this.serializedKey;
        this.serializedKey = serializedKey;
        propertyChangeSupport.firePropertyChange("serializedKey", oldSerializedKey, serializedKey);
    }

    /**
     * Getter for property originalFileName.
     *
     * @return Value of property originalFileName.
     */
    public String getOriginalFileName() {
        return originalFileName;
    }

    /**
     * Setter for property originalFileName.
     *
     * @param originalFileName Value of property originalFileName.
     */
    public void setOriginalFileName(String originalFileName) {
        String oldOriginalFileName = this.originalFileName;
        this.originalFileName = originalFileName;
        propertyChangeSupport.firePropertyChange("originalFileName", oldOriginalFileName, originalFileName);
    }

    /**
     * Getter for property attachmentType.
     *
     * @return Value of property attachmentType.
     */
    public String getAttachmentType() {
        return attachmentType;
    }

    /**
     * Setter for property attachmentType.
     *
     * @param attachmentType Value of property attachmentType.
     */
    public void setAttachmentType(String attachmentType) {
        String oldAttachmentType = this.attachmentType;
        this.attachmentType = attachmentType;
        propertyChangeSupport.firePropertyChange("attachmentType", oldAttachmentType, attachmentType);
    }

    /**
     * Getter for property contentType.
     *
     * @return Value of property contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Setter for property contentType.
     *
     * @param contentType Value of property contentType.
     */
    public void setContentType(String contentType) {
        String oldContentType = this.contentType;
        this.contentType = contentType;
        propertyChangeSupport.firePropertyChange("contentType", oldContentType, contentType);
    }

    /**
     * Getter for property description.
     *
     * @return Value of property description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for property description.
     *
     * @param description Value of property description.
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange("description", oldDescription, description);
    }

    /**
     * Getter for property remarks.
     *
     * @return Value of property remarks.
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter for property remarks.
     *
     * @param remarks Value of property remarks.
     */
    public void setRemarks(String remarks) {
        String oldRemarks = this.remarks;
        this.remarks = remarks;
        propertyChangeSupport.firePropertyChange("remarks", oldRemarks, remarks);
    }

    /**
     * Getter for property supercededBy.
     *
     * @return Value of property supercededBy.
     */
    public String getSupercededBy() {
        return supercededBy;
    }

    /**
     * Setter for property supercededBy.
     *
     * @param supercededBy Value of property supercededBy.
     */
    public void setSupercededBy(String supercededBy) {
        String oldSupercededBy = this.supercededBy;
        this.supercededBy = supercededBy;
        propertyChangeSupport.firePropertyChange("supercededBy", oldSupercededBy, supercededBy);
    }

    /**
     * Getter for property createdOn.
     *
     * @return Value of property createdOn.
     */
    public DateTime getCreatedOn() {
        return createdOn;
    }

    /**
     * Setter for property createdOn.
     *
     * @param createdOn Value of property createdOn.
     */
    public void setCreatedOn(DateTime createdOn) {
        DateTime oldCreatedOn = this.createdOn;
        this.createdOn = createdOn;
        propertyChangeSupport.firePropertyChange("createdOn", oldCreatedOn, createdOn);
    }

    /**
     * Getter for property createdBy.
     *
     * @return Value of property createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for property createdBy.
     *
     * @param createdBy Value of property createdBy.
     */
    public void setCreatedBy(String createdBy) {
        String oldCreatedBy = this.createdBy;
        this.createdBy = createdBy;
        propertyChangeSupport.firePropertyChange("createdBy", oldCreatedBy, createdBy);
    }

    /**
     * Getter for property lastChangedOn.
     *
     * @return Value of property lastChangedOn.
     */
    public DateTime getLastChangedOn() {
        return lastChangedOn;
    }

    /**
     * Setter for property lastChangedOn.
     *
     * @param lastChangedOn Value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTime lastChangedOn) {
        DateTime oldLastChangedOn = this.lastChangedOn;
        this.lastChangedOn = lastChangedOn;
        propertyChangeSupport.firePropertyChange("lastChangedOn", oldLastChangedOn, lastChangedOn);
    }

    /**
     * Getter for property lastChangedBy.
     *
     * @return Value of property lastChangedBy.
     */
    public String getLastChangedBy() {
        return lastChangedBy;
    }

    /**
     * Setter for property lastChangedBy.
     *
     * @param lastChangedBy Value of property lastChangedBy.
     */
    public void setLastChangedBy(String lastChangedBy) {
        String oldLastChangedBy = this.lastChangedBy;
        this.lastChangedBy = lastChangedBy;
        propertyChangeSupport.firePropertyChange("lastChangedBy", oldLastChangedBy, lastChangedBy);
    }

    /**
     * Getter for property data.
     *
     * @return Value of property data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Setter for property data.
     *
     * @param data Value of property data.
     */
    public void setData(byte[] data) {
        byte[] oldData = this.data;
        this.data = data;
        propertyChangeSupport.firePropertyChange("data", oldData, data);
    }
}
