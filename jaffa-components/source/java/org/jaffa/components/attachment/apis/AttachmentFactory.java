package org.jaffa.components.attachment.apis;


/**
 * Created by stuta on 1/5/2017.
 */
public class AttachmentFactory {

    private static IAttachmentDataImplFactory attachmentDataImplFactory;

    /**
     * Sets the instance of the attachment data implementation to use.
     *
     * @param attachmentData the attachment implementation instance to use.
     */
    public static void setAttachmentDataImplFactory(IAttachmentDataImplFactory attachmentDataImplFactory) {
        AttachmentFactory.attachmentDataImplFactory = attachmentDataImplFactory;
    }


    /**
     * Returns AttachmentData Implementation instance
     * @return
     */
    public static IAttachmentData getAttachmentDataImpl(){
        return AttachmentFactory.attachmentDataImplFactory!=null ? AttachmentFactory.attachmentDataImplFactory.getAttachmentDataImpl() : null;
    }
}
