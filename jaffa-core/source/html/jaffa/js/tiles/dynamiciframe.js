var iframeids = new Array(); 
var iframehide="no"; 
    
if (window.addEventListener) 
  window.addEventListener("load", resizeCaller, false) 
else if (window.attachEvent) 
  window.attachEvent("onload", resizeCaller) 
else 
  window.onload=resizeCaller 
    
   
// resize all IFRAMEs registed on this page
function resizeCaller() { 
  //alert("Resize Frames");
  var dyniframe=new Array();
  for (i=0; i<iframeids.length; i++) {
    if (document.getElementById) 
      resizeIframe(iframeids[i]) 
    //reveal iframe for lower end browsers? (see var above): 
    if ((document.all || document.getElementById) && iframehide=="no") { 
      var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i]);
      tempobj.style.display="block";
    } 
  } 
} 


// resize specified IFRAME based on its inter content
function resizeIframe(frameid) {
  //alert("Resize - " + frameid);
  b=document.getElementById(frameid + ":body");
  c=document.getElementById(frameid + ":cell");
  b.style.display= "block";
  var currentfr=document.getElementById(frameid);
  if (currentfr && !window.opera) {
    currentfr.style.display="block" 
    //ns6 syntax 
    if (currentfr.contentDocument && currentfr.contentDocument.body.offsetHeight){ 
      var originalFrame = b.innerHTML; 
      b.innerHTML = currentfr.contentDocument.body.innerHTML; 
      windowheight = b.offsetHeight; 
      windowwidth = b.offsetWidth; 
      currentfr.style.height = (windowheight + 20) + "px"; 
      c.style.height = windowheight; 
      c.style.width = windowwidth; 
      b.innerHTML = null; 
      b.style.display='';
    } 
    
    else if (currentfr.Document && currentfr.Document.body.scrollHeight) { 
      currentfr.style.height = currentfr.Document.body.scrollHeight + 1; 
      currentfr.style.width = currentfr.Document.body.scrollWidth + 1; 
    } 
    //ie5+ syntax 
    if (currentfr.addEventListener) 
      currentfr.addEventListener("load", readjustIframe, false) 
    else if (currentfr.attachEvent){ 
      currentfr.detachEvent("onload", readjustIframe) // Bug fix line 
      currentfr.attachEvent("onload", readjustIframe) 
    
    } 
  } 
} 

// Event to resize an IFRAME
function readjustIframe(loadevt) { 
  var crossevt=(window.event)? event : loadevt;
  var iframeroot=(crossevt.currentTarget)? crossevt.currentTarget : crossevt.srcElement;
  if (iframeroot) 
    resizeIframe(iframeroot.id); 
} 

// Load new content into an IFRAME    
function loadintoIframe(iframeid, url) { 
  if (document.getElementById) 
    document.getElementById(iframeid).src=url;
} 