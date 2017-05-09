/**
 * Declare an object to which we can add real functions.
 */
Ext.namespace("Jaffa","Jaffa.Audit");

/**
 * @deprecated Use Jaffa.component.CRUDController instead.
 * @class Jaffa.Audit.AuditTransactionService
 * Service class based on the loading and saving WorkOrder Graphs
 * via the DWR 'AuditTransactionService'.
 * <br>
 * For example:
 * <pre><code>
    //Create service object with a specific query
    var auditTransactionService = new Jaffa.Audit.AuditTransactionService({
        criteria: {
            auditTransactionId:{values:['ATN-00001']},
            resultGraphRules:['**'],
            objectLimit:1
        }
    });
    
    // Create a panel to display work order fields
    var auditTransactionPanel = new AuditTransactionPanel();
    
    // Register a method to load the panel with data, once the data has been retrieved
    auditTransactionService.on("load", auditTransactionPanel.load, auditTransactionPanel);
    
    // Get service to execute query and load the results, provide a handler in case the record is not found
    auditTransactionService.load( function(service){
        if(service.isLoaded && service.model==null) {
            Ext.MessageBox.show( {
                title: service.objectName+' Load Error',
                msg: service.objectName+' '+service.criteria.auditTransactionId.values[0]+' Not Found',
                width:400,
                buttons: Ext.MessageBox.OK
            });
        }    
    });
</code></pre>
 */
Jaffa.Audit.AuditTransactionService = function(config) {
    Ext.apply(this, config);
    Jaffa.Audit.AuditTransactionService.superclass.constructor.call(this);
}
Ext.extend(Jaffa.Audit.AuditTransactionService, Jaffa.DWRService, {
    // This is used for user messages
    objectName: 'Audit Transaction',
    // This is the service to load data
    dwrQuery : Jaffa_Audit_AuditTransactionViewService.query
});
