/**
 * A record to represent an Attachment.
 * @class Jaffa.attachment.Record
 */
Jaffa.attachment.Record = Ext.data.Record.create([
  {name: 'attachmentId'},
  {name: 'serializedKey'},
  {name: 'originalFileName', sortable: true},
  {name: 'attachmentType',   sortable: true},
  {name: 'contentType',      sortable: true},
  {name: 'description'},
  {name: 'remarks'},
  {name: 'supercededBy',     sortable: true},
  {name: 'createdOn',        sortable: true},
  {name: 'createdBy',        sortable: true},
  {name: 'lastChangedOn',    sortable: true},
  {name: 'lastChangedBy',    sortable: true}
]);
Jaffa.attachment.Record.defaultMetaClass = 'AttachmentGraph';
