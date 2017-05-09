/** @class QueueManagerHelper
 * This contains a collection of helper methods that need to be
 * used by multiple tabs in the Queue Manager 
 */
Jaffa.QM.QueueManagerHelper = {

  /**
   * Toggle the queue status
   * @param {Array} queues An array of queues to be started or paused
   */
  toggleQueueStatus: function(queues, callback){
    var queueService = new Ext.data.DWRProxy2(Jaffa_QM_QueueManager.toggleQueueStatus);
    queueService.load([queues], {
        read: function(r){
          return r;
        }
      }, callback, this);
  },

  deleteMessage: function(queues, callback, scope){
    var queueService = new Ext.data.DWRProxy2(Jaffa_QM_QueueManager.deleteMessage);
    queueService.load([queues], {
        read: function(r){
          return r;
        }
      }, callback, scope);
  },

  resubmitMessage: function(queues, callback, scope){
    var queueService = new Ext.data.DWRProxy2(Jaffa_QM_QueueManager.resubmitMessage);
    queueService.load([queues], {
        read: function(r){
          return r;
        }
      }, callback, scope);
  },
  
  queueRecord: function(){
    var rec = new Jaffa.data.Record.create([{
      name: 'id',
      type: 'string'
    }, {
      name: 'type',
      mapping: 'type',
      type: 'string'
    }, {
      name: 'status',
      mapping: 'status',
      type: 'string'
    }, {
      name: 'queueSystemId',
      mapping: 'queueMetaData.queueSystemId',
      type: 'string'
    }, {
      name: 'openCount',
      mapping: 'openCount',
      type: 'string'
    }, {
      name: 'inProcessCount',
      mapping: 'inProcessCount',
      type: 'int'
    }, {
      name: 'errorCount',
      mapping: 'errorCount',
      type: 'int'
    }, {
      name: 'displayStatus',
      type: 'string'
    }, {
      name: 'runStatus',
      type: 'string'
    }, {
      name: 'quickTip',
      type: 'string'
    }, {
      name: 'lastErroredOn',
      type: 'date'
    }]);

    Ext.override(rec, {
      initData: function() {     
        this.data.id = this.id;   
        var qsi = this.data.queueSystemId;
        
        this.data.quickTip = Labels.get('label.Jaffa.QM.QueueManager.Status') + ": " + this.data.status +
           "<br>" + Labels.get('label.Jaffa.QM.QueueManager.Pending') + ": " + this.data.openCount + 
           "<br>" + Labels.get('label.Jaffa.QM.QueueManager.InProcess') + ": " + this.data.inProcessCount +
           "<br>" + Labels.get('label.Jaffa.QM.QueueManager.Failed') + ": " + this.data.errorCount
           
        if (this.data.errorCount > 0)
          this.data.displayStatus = 'FAILED-DARK';
          if (this.data.lastErroredOn){
            if (this.data.lastErroredOn > new Date().add(Date.HOUR, -1)) {
              this.data.displayStatus = 'FAILED-BRIGHT';
            }
            else 
              if (this.data.lastErroredOn > new Date().add(Date.DAY, -1)) {
                this.data.displayStatus = 'FAILED';
              } 
          }
        else {
          if (this.data.status=='ACTIVE'){
            this.data.displayStatus = 'ACTIVE-DARK';
            if (this.data.openCount > 0){
              this.data.displayStatus = 'ACTIVE-BRIGHT';
            }
          }else{
            this.data.displayStatus = 'INACTIVE';
            if (this.data.openCount > 0) {
              this.data.displayStatus = 'INACTIVE-BRIGHT';
            }
          }
        }
          
        if (this.data.status=='ACTIVE')
          this.data.runStatus='R';
        else
          this.data.runStatus='P';
         
      }
    });    
    return rec;
  }
}
