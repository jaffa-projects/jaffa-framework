
Ext.onReady(function(){
  Ext.QuickTips.init();

  //******************start Viewport
  //Viewport is a container which will take the whole area of the browser
  //********************************************************************
    var viewport = new Ext.Viewport({
      layout: 'border',
      autoScroll: true,
      id:'viewport',
      items: [{
        xtype: 'finderheader',
        titleToken: 'title.Jaffa.Setup.ValidFieldValueMaintenance', //This will set the main window header at the top
        dataDictionaryDomain: 'ValidFieldValue',
        helpPath: 'jaffa/setup/validfieldValuemaintenance'
      }, Jaffa.Setup.ValidFieldValueFinderContainer({
        layout: params._layout,
        ref :'finderContainer'
      })
    ]
 
  });
  //end Viewport
  
  if(params.displayResultsScreen == "true"){
    viewport.finderContainer.criteriaPanel.search();
  } else {
    // if there is a default query, load it and do a search();
    viewport.finderContainer.criteriaPanel.runDefaultQuery();
  }
});
