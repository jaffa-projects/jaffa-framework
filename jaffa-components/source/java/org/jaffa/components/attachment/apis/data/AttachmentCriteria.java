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

import org.jaffa.components.attachment.domain.AttachmentMeta;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;

/**
 * This class is used for passing query criteria to a graph-based service.
 * @author GautamJ
 */
public class AttachmentCriteria extends GraphCriteria {

    private StringCriteriaField attachmentId;
    private StringCriteriaField serializedKey;
    private StringCriteriaField originalFileName;
    private StringCriteriaField attachmentType;
    private StringCriteriaField contentType;
    private StringCriteriaField description;
    private StringCriteriaField remarks;
    private StringCriteriaField supercededBy;
    private DateTimeCriteriaField createdOn;
    private StringCriteriaField createdBy;
    private DateTimeCriteriaField lastChangedOn;
    private StringCriteriaField lastChangedBy;

    /**
     * Getter for property attachmentId.
     * @return Value of property attachmentId.
     */
    public StringCriteriaField getAttachmentId() {
        return attachmentId;
    }

    /**
     * Setter for property attachmentId.
     * @param attachmentId Value of property attachmentId.
     */
    public void setAttachmentId(StringCriteriaField attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * Getter for property serializedKey.
     * @return Value of property serializedKey.
     */
    public StringCriteriaField getSerializedKey() {
        return serializedKey;
    }

    /**
     * Setter for property serializedKey.
     * @param serializedKey Value of property serializedKey.
     */
    public void setSerializedKey(StringCriteriaField serializedKey) {
        this.serializedKey = serializedKey;
    }

    /**
     * Getter for property originalFileName.
     * @return Value of property originalFileName.
     */
    public StringCriteriaField getOriginalFileName() {
        return originalFileName;
    }

    /**
     * Setter for property originalFileName.
     * @param originalFileName Value of property originalFileName.
     */
    public void setOriginalFileName(StringCriteriaField originalFileName) {
        this.originalFileName = originalFileName;
    }

    /**
     * Getter for property attachmentType.
     * @return Value of property attachmentType.
     */
    public StringCriteriaField getAttachmentType() {
        return attachmentType;
    }

    /**
     * Setter for property attachmentType.
     * @param attachmentType Value of property attachmentType.
     */
    public void setAttachmentType(StringCriteriaField attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * Getter for property contentType.
     * @return Value of property contentType.
     */
    public StringCriteriaField getContentType() {
        return contentType;
    }

    /**
     * Setter for property contentType.
     * @param contentType Value of property contentType.
     */
    public void setContentType(StringCriteriaField contentType) {
        this.contentType = contentType;
    }

    /**
     * Getter for property description.
     * @return Value of property description.
     */
    public StringCriteriaField getDescription() {
        return description;
    }

    /**
     * Setter for property description.
     * @param description Value of property description.
     */
    public void setDescription(StringCriteriaField description) {
        this.description = description;
    }

    /**
     * Getter for property remarks.
     * @return Value of property remarks.
     */
    public StringCriteriaField getRemarks() {
        return remarks;
    }

    /**
     * Setter for property remarks.
     * @param remarks Value of property remarks.
     */
    public void setRemarks(StringCriteriaField remarks) {
        this.remarks = remarks;
    }

    /**
     * Getter for property supercededBy.
     * @return Value of property supercededBy.
     */
    public StringCriteriaField getSupercededBy() {
        return supercededBy;
    }

    /**
     * Setter for property supercededBy.
     * @param supercededBy Value of property supercededBy.
     */
    public void setSupercededBy(StringCriteriaField supercededBy) {
        this.supercededBy = supercededBy;
    }

    /**
     * Getter for property createdOn.
     * @return Value of property createdOn.
     */
    public DateTimeCriteriaField getCreatedOn() {
        return createdOn;
    }

    /**
     * Setter for property createdOn.
     * @param createdOn Value of property createdOn.
     */
    public void setCreatedOn(DateTimeCriteriaField createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Getter for property createdBy.
     * @return Value of property createdBy.
     */
    public StringCriteriaField getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for property createdBy.
     * @param createdBy Value of property createdBy.
     */
    public void setCreatedBy(StringCriteriaField createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for property lastChangedOn.
     * @return Value of property lastChangedOn.
     */
    public DateTimeCriteriaField getLastChangedOn() {
        return lastChangedOn;
    }

    /**
     * Setter for property lastChangedOn.
     * @param lastChangedOn Value of property lastChangedOn.
     */
    public void setLastChangedOn(DateTimeCriteriaField lastChangedOn) {
        this.lastChangedOn = lastChangedOn;
    }

    /**
     * Getter for property lastChangedBy.
     * @return Value of property lastChangedBy.
     */
    public StringCriteriaField getLastChangedBy() {
        return lastChangedBy;
    }

    /**
     * Setter for property lastChangedBy.
     * @param lastChangedBy Value of property lastChangedBy.
     */
    public void setLastChangedBy(StringCriteriaField lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    /** Returns the criteria object used for retrieving domain objects.
     * @param nestedClause Minimal criteria used to retrieve the nested object. Will be null for the root query.
     * @return the criteria object used for retrieving domain objects.
     */
    @Override
    public Criteria returnQueryClause(Criteria nestedClause) {
        Criteria c = super.returnQueryClause(nestedClause);
        c.setTable(AttachmentMeta.getName());
        FinderTx.addCriteria(getAttachmentId(), AttachmentMeta.ATTACHMENT_ID, c);
        FinderTx.addCriteria(getSerializedKey(), AttachmentMeta.SERIALIZED_KEY, c);
        FinderTx.addCriteria(getOriginalFileName(), AttachmentMeta.ORIGINAL_FILE_NAME, c);
        FinderTx.addCriteria(getAttachmentType(), AttachmentMeta.ATTACHMENT_TYPE, c);
        FinderTx.addCriteria(getContentType(), AttachmentMeta.CONTENT_TYPE, c);
        FinderTx.addCriteria(getDescription(), AttachmentMeta.DESCRIPTION, c);
        FinderTx.addCriteria(getRemarks(), AttachmentMeta.REMARKS, c);
        FinderTx.addCriteria(getSupercededBy(), AttachmentMeta.SUPERCEDED_BY, c);
        FinderTx.addCriteria(getCreatedOn(), AttachmentMeta.CREATED_ON, c);
        FinderTx.addCriteria(getCreatedBy(), AttachmentMeta.CREATED_BY, c);
        FinderTx.addCriteria(getLastChangedOn(), AttachmentMeta.LAST_CHANGED_ON, c);
        FinderTx.addCriteria(getLastChangedBy(), AttachmentMeta.LAST_CHANGED_BY, c);
        return c;
    }
}
