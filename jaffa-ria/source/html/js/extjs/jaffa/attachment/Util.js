/**
 * A utility class for Attachments
 * @class Jaffa.attachment.Util
 */
Jaffa.attachment.Util = {
    /**
     * Loads the MetaData, if it has not been loaded already.
     */
    loadMetaData: function () {
        if (!ClassMetaData['AttachmentGraph']) {
            var metaSource = Ext.Ajax.synchronousRequest({
                url: 'js/extjs/jaffa/metadata/classMetaData.jsp',
                params: {
                    className: 'org.jaffa.components.attachment.apis.data.AttachmentGraph',
                    outputStyle: "JSON"
                }
            });
            if (metaSource) {
                ClassMetaData['AttachmentGraph'] = Ext.decode(metaSource);
            }
        }
    }
}
