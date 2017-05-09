/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2008 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/
/**
 * @author SeanZ
 */
var viewport = null;

Ext.onReady(function(){
  Ext.QuickTips.init();
  
  Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
  var fsHeight = 110, labelWidth = 300;
  
  viewport = new Ext.Viewport({
    layout: 'border',
    items: [{
      region: 'center',
      xtype: 'panel',
      title: document.title,
      height : fsHeight*3+20,
      autoscroll : true,
      items: [new Ext.form.FieldSet({
        title : 'DateField test',
        height : fsHeight,
        labelWidth : labelWidth, 
        items: [{
          xtype: 'textfield',
          width: 350,
          fieldLabel: 'Date from server expressed in GMT Time Zone',
          readOnly: true,
          mapping: 'serverGen'
        }, {
          xtype: 'datefield',
          fieldLabel: 'Date from browser',
          inputField: true,
          disabled: true,
          disabledClass: ' ',
          mapping: 'serverGen'
        }, {
          xtype: 'datefield',
          fieldLabel: 'Pick a date',
          outputField: true,
          mapping: 'serverGen'
        }]
      }), new Ext.form.FieldSet({
        title : 'DateTime test',
        height : fsHeight,
        labelWidth : labelWidth, 
        items: [{
          xtype: 'textfield',
          width: 350,
          fieldLabel: 'DateTime from server expressed in GMT Time Zone',
          readOnly: true,
          mapping: 'dtGen'
        }, {
          xtype: 'xdatetime',
          fieldLabel: 'DateTime from browser',
          inputField: true,
          disabled: true,
          disabledClass: ' ',
          mapping: 'dtGen'
        }, {
          xtype: 'xdatetime',
          fieldLabel: 'Pick a date time',
          outputField: true,
          mapping: 'dtGen'
        }]
      })],
      bbar: [{
        xtype: 'button',
        text: 'Send',
        handler: function(){
          var flds = viewport.find('outputField', true);
          var params = {}, hasParams = false;
          for (var i = 0; i < flds.length; i++) {
            if (flds[i].mapping && flds[i].getValue()) {
              var v = flds[i].getValue().getTime();
              if (v) {
                params[flds[i].mapping] = v;
                hasParams = true;
              }
            }
          }
          if (hasParams) {
            viewport.conn.request({
              url: valuesFromServer.appCtx + '/tests/extjs/serverTimeZone/ServerTimeZone.jsp',
              params: params,
              scope: viewport,
              success: function(resp){
                eval("var aa=" + resp.responseText);
                viewport.load(aa);
              }
            });
          }
        }
      }, '->', {
        xtype: 'label',
        html: '<a href="../../../logout.dop">logout</a>'
      }]
    }],
    conn: new Ext.data.Connection(),
    load: function(response){
      var fds = this.find('xtype', 'textfield');
      for (var i = 0; i < fds.length; i++) {
        if (fds[i].mapping)
          if (typeof response.dateStrings[fds[i].mapping] !== 'undefined')
            fds[i].setValue(response.dateStrings[fds[i].mapping]);
      }
      
      var fds = this.find('inputField', true);
      for (var i = 0; i < fds.length; i++) {
        if (fds[i].mapping) {
          if (typeof response.dateValues[fds[i].mapping] !== 'undefined')
            fds[i].setValue(response.dateValues[fds[i].mapping]);
        }
      }
    }
  });
  viewport.load(valuesFromServer);
  
});
