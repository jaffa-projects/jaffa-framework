package org.jaffa.components.attachment.apis;

/**
 * IAttachmentData is the interface for Attachment Data Implementations to provide CRUD operations on the attachments
 */
public interface IAttachmentData {

    /**
     * Create the attachment with key as documentReferenceId and data binary
     * @param documentReferenceId
     * @param data
     * @return
     */
    public boolean create(String documentReferenceId, byte[] data);

    /**
     * Read and return the attchment for the documentReferenceId provided
     * @param documentReferenceId
     * @return
     */
    public byte[] read(String documentReferenceId);

    /**
     * Delete the attachment for the provided documentReferenceId
     * @param documentReferenceId
     * @return
     */
    public void delete(String documentReferenceId);

}
