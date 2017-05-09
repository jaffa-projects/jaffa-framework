
var arrayStart = browser.isIE?0:1;

// Normal onClick switch code passes (tab=div of new tab)
// Initialize strip code passes (tab=null, stripId="name" [,"startIndex"])
function switchTab(tab,stripId) {
  if(arguments.length>2)
    jaffaSwitchTab(tab,stripId,jaffaShowTab,jaffaHideTab,arguments[2]);
  else
    jaffaSwitchTab(tab,stripId,jaffaShowTab,jaffaHideTab);
}

//tab=[DIV Element] node that clicked on to show this tab
//stripId=[String] Tab Strip Id given to the complete tab strip
//show=[Function] method to invoke when tab is selected
//hide=[Function] method to invoke when tab is deselected
//arg[4]=[Number] index of tab to initialize to if stripId is provided an tab strip not initialized
function jaffaSwitchTab(tab,stripId,show,hide) {
  var pane=null,oId=null,ptab=null;
  
  //If tab is supplied, the stored one is the old one
  if(tab!=null) {
    pane=tab.parentNode.parentNode; //BIG ASSUMPTION base on our HTML
    oId=document.getElementById(pane.id+"Idx");
    if(oId.value!="")
      ptab=document.getElementById(oId.value);
  } else {
    //tab should be initialized from hidden field
    pane=document.getElementById(stripId);
    if(pane==null) {/*alert("INVALID Tab Pane Name : " + stripId);*/return;}
    oId=document.getElementById(pane.id+"Idx");

    //alert("OldTab="+oId.value);
    //If value in hidden field is not valid, unset it
    if(oId.value!="" && document.getElementById(oId.value)==null) {
      //alert("Tab="+oId.value+" Doesnot Exist");
      oId.value="";
    }

    //Initialize to first tab (or the one specified in arg3)  
    if(oId.value==null||oId.value=="") {
      var i=arguments.length>4?arguments[4]:1;
      for(;i<10;i++) {
        if(document.getElementById(stripId+"-"+i)) {
          oId.value=stripId+"-"+i;
          //alert("Initialize Tab to "+oId.value);
          break;
        }  
      }
    }
    tab=document.getElementById(oId.value);
  }
  // Deselect/Hide previous tab if set
  if(ptab!=null) {
    if(ptab==tab) {return;} //Current Tab Clicked
    ptab.className="jaffaTab";
    if (ptab.childNodes[arrayStart] && ptab.childNodes[arrayStart].childNodes && ptab.childNodes[arrayStart].childNodes.length > 0) {
      ptab.childNodes[arrayStart].childNodes[arrayStart].tabindex=-1;
    }
    hide(ptab,document.getElementById(ptab.id+"p"));
  }
  // Select/Show current tab
  tab.className="jaffaTab current";
  if (tab.childNodes[arrayStart] && tab.childNodes[arrayStart].childNodes && tab.childNodes[arrayStart].childNodes.length > 0) {
    tab.childNodes[arrayStart].childNodes[arrayStart].tabindex=0;
  }
  oId.value=tab.id;
  show(tab,document.getElementById(tab.id+"p"));
}

//When the tab is displayed, it makes the div pane visible
//If there is a 'jaffaDataUrl' attribute, this content is loaded via AJAX
function jaffaShowTab(tab,pane) {
  if(tab!=null) tab.style.display="";//Make sure it is visible
  var url = pane.getAttribute("jaffaDataUrl");
  if(url!=null && pane.innerHTML=="") {
    sendAjaxRequest(pane, url, null);
  } else {
    var onload = pane.getAttribute("onload");
    if (onload != null && onload != "") {
      try {
        eval(onload);
      } catch (e) {
        alert("Tab onLoad code failed for div.id="+pane.id+" : "+e);
      }
    }
  }
  pane.style.display="";
}

//When the tab is deselected, it makes the div pane hidden
function jaffaHideTab(tab,pane) {
  pane.style.display="none";
}

// Find the TabPane this element is in
function findContainingPane(e) {
  while(e.parentNode != null && e.parentNode!=e) {
    e=e.parentNode;
    if(e.className=="jaffaTabPane")
      return e;
  }
  return null;
}

// Find the ajaxForward being used by the specified TabPane
function findPaneForward(e) {
  if(e.className!="jaffaTabPane")
    return null;
  var u = e.getAttribute("jaffaDataUrl");
  if(u==null) return null;
  var p = u.indexOf("&ajaxForward=");
  if(p==-1) return null;
  u=u.substring(p+13);
  p = u.indexOf("&");
  if(p>=0)
    u=u.substring(0,p-1);
  //alert("Forward is " + u);
  return u;
}


