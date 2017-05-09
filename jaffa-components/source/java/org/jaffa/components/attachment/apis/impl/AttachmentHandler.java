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
package org.jaffa.components.attachment.apis.impl;

import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jaffa.components.attachment.apis.AttachmentFactory;
import org.jaffa.components.attachment.apis.IAttachmentData;
import org.jaffa.components.attachment.apis.data.AttachmentGraph;
import org.jaffa.components.attachment.domain.Attachment;
import org.jaffa.components.attachment.domain.AttachmentMeta;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.util.PersistentHelper;
import org.jaffa.security.SecurityManager;
import org.jaffa.soa.dataaccess.MappingFilter;
import org.jaffa.soa.dataaccess.TransformationHandler;
import org.jaffa.soa.graph.GraphCriteria;
import org.jaffa.util.ExceptionHelper;

/**
 * A Handler class to support the AttachmentService.
 */
public class AttachmentHandler extends TransformationHandler {

    private static final Logger log = Logger.getLogger(AttachmentHandler.class);

    /**
     * Creates new instance.
     * @param uow the UOW.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    public AttachmentHandler(UOW uow) throws ApplicationExceptions, FrameworkException {
    }

    /**
     * Called prior to adding the target bean to the persistent store.
     * @param path This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    @Override
    public void startBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        checkFunctionAccess((Attachment) target, true);
    }

    public void endBeanAdd(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if(source instanceof AttachmentGraph && target instanceof Attachment) {
            AttachmentGraph attachmentGraph = (AttachmentGraph)source;
            Attachment attachment = (Attachment)target;
            IAttachmentData attachmentDataImpl = AttachmentFactory.getAttachmentDataImpl();
            if (attachmentDataImpl != null && attachment.getDocumentReferenceId()!=null) {
                if(attachmentDataImpl.read(attachment.getDocumentReferenceId()) == null){
                    attachmentDataImpl.create(attachment.getDocumentReferenceId(), attachmentGraph.getData());
                }
            }
        }
    }

    public void endBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if(source instanceof AttachmentGraph && target instanceof Attachment) {
            AttachmentGraph attachmentGraph = (AttachmentGraph)source;
            Attachment attachment = (Attachment)target;
            IAttachmentData attachmentDataImpl = AttachmentFactory.getAttachmentDataImpl();
            if (attachmentDataImpl != null && attachment.getDocumentReferenceId()!=null) {
                Criteria c = new Criteria();
                c.setTable(AttachmentMeta.getName());
                c.addCriteria(AttachmentMeta.DOCUMENT_REFERENCE_ID, attachment.getDocumentReferenceId());
                c.addCriteria(AttachmentMeta.ATTACHMENT_ID, Criteria.RELATIONAL_NOT_EQUALS, attachment.getAttachmentId());
                c.addFunction(Criteria.FUNCTION_COUNT, null, Criteria.ID_FUNCTION_COUNT);
                Iterator<Attachment> iterator = attachment.getUOW().query(c).iterator();
                if (iterator.hasNext()) {
                    Number count = (Number) ((Map) iterator.next()).get(Criteria.ID_FUNCTION_COUNT);
                    if(count == null || count.intValue() == 0){
                        attachmentDataImpl.delete(attachment.getDocumentReferenceId());
                    }
                }
            }
        }
    }


    /**
     * Called prior to updating the target bean in the persistent store.
     * @param path This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    @Override
    public void startBeanUpdate(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        checkFunctionAccess((Attachment) target, true);
    }

    /**
     * Called prior to deleting the target bean from the persistent store.
     * @param path This is the source path of this graph, used when processing a more complex tree, where this is the path to get to this root object being processed.
     * @param source the graph object being processed.
     * @param target the corresponding domain object.
     * @throws ApplicationException if any Application error occurs. SkipTransformException may be thrown to stop the processing of this graph.
     * @throws ApplicationExceptions if multiple Application errors occur.
     * @throws FrameworkException if an internal error occurs.
     */
    @Override
    public void startBeanDelete(String path, Object source, Object target) throws ApplicationException, ApplicationExceptions, FrameworkException {
        checkFunctionAccess((Attachment) target, true);
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
        checkFunctionAccess((Attachment) source, false);

        if(source instanceof Attachment && target instanceof AttachmentGraph) {
            AttachmentGraph attachmentGraph = (AttachmentGraph)target;
            Attachment attachment = (Attachment)source;
            IAttachmentData attachmentDataImpl = AttachmentFactory.getAttachmentDataImpl();
            if (attachmentDataImpl != null && attachment.getDocumentReferenceId()!=null && (originalCriteria.getResultGraphRules()== null ||
                    originalCriteria.getResultGraphRules().length == 0 || !Arrays.asList(originalCriteria.getResultGraphRules()).contains("-data"))) {
                byte[] data = attachmentDataImpl.read(attachment.getDocumentReferenceId());
                if(data!=null){
                    attachmentGraph.setData(data);
                }
            }
        }

    }

    @Override
    public void endBeanClone(String path, Object source, Object target, Object newGraph) throws ApplicationException, ApplicationExceptions, FrameworkException {
        if(source instanceof AttachmentGraph && target instanceof Attachment) {
            AttachmentGraph attachmentGraph = (AttachmentGraph)source;
            Attachment attachment = (Attachment)target;
            IAttachmentData attachmentDataImpl = AttachmentFactory.getAttachmentDataImpl();
            if (attachmentDataImpl != null && attachment.getDocumentReferenceId()!=null) {
                if(attachmentDataImpl.read(attachment.getDocumentReferenceId()) == null){
                    attachmentDataImpl.create(attachment.getDocumentReferenceId(), attachmentGraph.getData());
                }
            }
        }
    }
    private void checkFunctionAccess(Attachment attachment, boolean forMaintenance) throws ApplicationExceptions, FrameworkException, AccessControlException {
        if (attachment != null && attachment.getSerializedKey() != null) {
            // Retrieve the domain object to which the attachment belongs
            IPersistent attachTo = null;
            try {
                attachTo = PersistentHelper.loadFromSerializedKey(attachment.getUOW(), attachment.getSerializedKey());
            } catch (Exception e) {
                log.error("Exception thrown while retrieving the domain object to which the attachment belongs", e);
                throw ExceptionHelper.throwAFR(e);
            }

            if(attachTo!=null) {
                // Ensure that the user has access to the domain object's attachments
                String function = "Attachment." + attachTo.getClass().getSimpleName() + (forMaintenance ? ".Maintenance" : ".Inquiry");
                if (!SecurityManager.checkFunctionAccess(function)) {
                    String str = "Access to business-function '" + function + "' is required to be able to " + (forMaintenance ? "manage" : "view") + " attachments for the domain class " + attachTo.getClass().getName();
                    log.error(str);
                    throw new AccessControlException(str);
                }
            }
        }
    }
}
