
/**
 * @class Jaffa.util.HttpPostUtil
 * @extends Ext.util.Observable
 * @autor ChrisO            
 *                The HttpPostUtil is used to send post parameters to specified url using only javascript.
 *                A hidden form is dynamically created and added to the body of the document and submitted.
 *                All data sent to the new document will be converted to a json string.
 * @example 
 * 
 *   var postUtil = new Jaffa.util.HttpPostUtil({
 *     action: 'startComponent.do?component=WorkRecording.Core.WorkPackageMaintenance', 
 *     data: {
 *       userId: 'ASDF',
 *       part: { part: '1234', cage: '0000'}
 *     }
 *   });
 *   postUtil.setParameter('statusCode', 3); //adds a parameter after the postUtil has been contructed
 *   postUtil.submit();
 *
 */
 
Jaffa.util.HttpPostUtil = Ext.extend(Ext.util.Observable, {
    /**
     * @cfg {String} action
     * The relative or absolute path of the destination page.
     * (defaults to null)
     */
    action: null,
    
    /**
     * @cfg {Object} data
     * The literal object that will be posted to the destination page
     * the data's first level properties will be converted to a Json string, it is up the response screen to decode it
     * (defaults to an empty literal object {})
     */
    data: {},
    
    /**
     * @cfg {String} method
     * the method of sending the parameters to the server. Currently GET is not supported by this utility.
     * (defaults to 'POST') 
     */
    method: 'POST',
    
    /**
     * @cfg {String} target
     * controls where the new document will be displayed when the http request is made .
     * (defaults to '_blank') . by default the request will be displayed in a new window
     * other options include : '_blank', '_parent', '_self', '_top'  
     */
    target: '_blank',
    
    //private constuctor
    constructor: function(config){
      this.addEvents(
        /**
        * @event beforesubmit
        * Fires before the submit method. You may cancel sumbit by returning false
        * @param {HttpPostUtil} this
        */
        'beforesubmit'
      ); 
      Ext.apply(this, config);
      Jaffa.util.HttpPostUtil.superclass.constructor.call(this, config);
    },
    
    //private
    getFormConfig: function(){
      this.form = {
        id: (this.id) ? 'tempForm_' + this.id : 'tempForm', //in case you need to multiple
        tag: 'form',
        action: this.action,
        method: this.method,
        target: this.target,
        children: []
      };
      return this.form;
    },
    
    /**
     * @private
     * Method to construct and add dom input elements into the hidden form
     */
    buildParams: function(){
      var form = this.getFormConfig();
      var data = this.data;
      if(data){
        for(var p in data){
		  var val = (typeof data[p] === 'object') ? Ext.encode(data[p]) : data[p] //stringifys the parameter if it is an object
          if(val && Ext.isString(val)){
            val = val.replace(/"/g, "&quot;") //escapes double quotes which have special meaning inside HTML
          }
          var input = {
            tag: 'input',
            name: p,
            value: val
          };
          form.children.push(input);
        }
      }
    },
    
    /**
     * adds a parameter to the data object being posted. If the parameter already exist it will be overwritted
     * @param {String} name
     * @param {Mixed} value
     */
    setParameter: function(name, value){
      this.data = this.data || {};
      this.data[name] = value;
    },
    
    /**
     * performs the http post.
     */
    submit: function(){
      if(this.fireEvent('beforesubmit', this) !== false){
        this.buildParams();
        var domForm = Ext.DomHelper.append(Ext.getBody(), this.form);
        domForm.submit();
      }
    }
});
