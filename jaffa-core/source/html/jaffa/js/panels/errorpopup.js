/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//??var objects = new Array();
//??var expanded = null;
// begin objects array with the document
//??objects[0] = new Array(document, "_document", false);
var errorWin = null;
var messageList = new Array();
function addMessage(ermsg) {messageList[messageList.length] = ermsg;}

function openDOMBrowser(activeElement) {
  if(!jaffaMessageRoot){
    jaffaMessageRoot = {};
  }
  var errorTop = (self.screen.availHeight / 2) - 100;
  var errorLeft = (self.screen.availWidth / 2) - 150;  
  // opens/reopens the window
  var args = "width=500,height=200,left=20,top=20,,resizable,top=" + errorTop + ",left=" + errorLeft;  
  try {
    errorWin = window.open('', jaffaMessageRoot.errorpopup.WindoeTitle, args);
    if(errorWin==null) throw new Error(jaffaMessageRoot.errorpopup.CannotCreateWindowMsg);

    // Ensure that we can write to the window. Else create a new window
    var maxPopups = 25;
    var currentPopupCount = 1;
    while (true) {
      try {
        errorWin.document.open("text/html", "replace");
        break;
      } catch (e2) {
        if (++currentPopupCount > maxPopups) throw new Error(jaffaMessageRoot.errorpopup.ManyPopupsWindows+" " + maxPopups);
        errorWin = window.open('', jaffaMessageRoot.errorpopup.WindoeTitle + currentPopupCount, args);
        if(errorWin==null) throw new Error(jaffaMessageRoot.errorpopup.CannotCreateWindowMsg);
      }
    }

    try {
      errorWin.focus();
    } catch (e2) {
      alert(jaffaMessageRoot.errorpopup.FailedFocusErrorWindowMsg+" " + e2);
    }
  } catch (e) {
    var errStr = jaffaMessageRoot.errorpopup.FailedMessage 
               + jaffaMessageRoot.errorpopup.FailedMessageErrorList +"\n";
    for (var pi = 0; pi < messageList.length; pi++)
      errStr+="- "+messageList[pi]+"\n";
     alert(errStr);
     return;
  }
  // clears the expanded array (to avoid infinate loops in the DOM)
  //??expanded = new Array();
  // document is about to be expanded
  //??expanded["_document"] = true;
  // writes HTML to the window
  with (errorWin.document) {
    writeln("<html><head><title>");
	writeln(jaffaMessageRoot.errorpopup.titleHeader);
	writeln("</title>");
    writeln("<link href='jaffa/css/widgets/errorpopup.css' type='text/css' rel='StyleSheet'>");
    writeln("<script>");
    writeln("var messageList=new Array();");
    writeln("var index=0;");
    writeln("function init() {");
    writeln("  if (messageList.length > 0) {");
    writeln("    if (messageList.length < 2) {");
    writeln("      document.getElementById('previous').style.visibility = 'hidden';");
    writeln("      document.getElementById('next').style.visibility = 'hidden';");
    writeln("    }");
    writeln("    showMessages(index);");
    writeln("  }");
    writeln("}");
    writeln("function showMessages(i) {");
    writeln("  if(i < messageList.length) {");
    writeln("    document.getElementById('msg').innerHTML = messageList[i];");
    writeln("    if ((i + 1) == messageList.length)");
    writeln("      document.getElementById('next').style.visibility = 'hidden';");
    writeln("    else");
    writeln("      document.getElementById('next').style.visibility = '';");
    writeln("    if (i  == 0)");
    writeln("      document.getElementById('previous').style.visibility = 'hidden';");
    writeln("    else");
    writeln("      document.getElementById('previous').style.visibility = '';");
    writeln("  }");
    writeln("}");
    writeln("function previous() {");
    writeln("  if (index != 0) {");
    writeln("    showMessages(--index);");
    writeln("    if (document.getElementById('calendardd') != null)");
    writeln("      document.getElementById('calendardd').disabled = false;");
    writeln("  }");
    writeln("}");
    writeln("function next() {");
    writeln("  if (index < messageList.length - 1) {");
    writeln("    showMessages(++index);");
    writeln("    if (document.getElementById('calendardd') != null)");
    writeln("      document.getElementById('calendardd').disabled = false;");
    writeln("  }");
    writeln("}");
    writeln("function okerror() {");
    writeln("  document.getElementById('errorBox').style.display = 'none';");
    writeln("  if (document.getElementById('calendardd') != null)");
    writeln("    document.getElementById('calendardd').disabled = true;");
    writeln("}");
    writeln("function addMessage(ermsg) {messageList[messageList.length]=ermsg;}");
    // finishes writing HTML and closes
    for (var pi = 0; pi < messageList.length; pi++)
      writeln("addMessage(\"" + messageList[pi].replace(/&lt;.*?&gt;/g, '') + "\");");
    writeln("</script>");
    writeln("</head>");
	//Added the Localization code for RTL Error pop up 
	var rtlLangValue = sessionStorage.getItem("RTLlanguage");
		if(rtlLangValue=="ar_OM"){
    writeln("<body onLoad='init();' dir='rtl'>");
		}
		else{
		writeln("<body onLoad='init();'>");	
		}
    writeln("<h1>");
	writeln(jaffaMessageRoot.errorpopup.ErrorTitle);
	writeln("</h1>");
    writeln("<table width='100%' cellspacing='0' cellpadding='10'>")
    writeln("<tr><td height='25'></td></tr><tr><td><table><tr><td>");
    writeln("<p>");
	writeln(jaffaMessageRoot.errorpopup.Message+":");
	writeln("</p>");
    writeln("</td><td><p id='msg'></p></td>");
    writeln("</tr></table></td></tr><tr><td align='right'><table><tr><td class='previous'>");
    writeln("<span id='previous' class='previous'><a href='javascript:previous()'>");
	writeln(jaffaMessageRoot.errorpopup.PreviousLink);
	writeln("</a></span>");
    writeln("</td><td></td><td class='next'><span id='next' class='next'><a href='javascript:next()'>");
	writeln(jaffaMessageRoot.errorpopup.NextLink);
	writeln("</a></span>");
    writeln("</td><td></td><td>");
    writeln("</td></tr></table></td></tr></table>");
    writeln("<script>window.onload(init())</script>");

    // If the target for an event is a new window, and the event-handler throws errors, then along with the error-popup, the current-page was being re-rendered in the new window
    // The following code closes the new window. It assumes that the history for such an unnecessary window will be 1. All other windows have history > 1
    if (browser.isIE) {
      writeln("<script>if (window.opener.history.length <= 0) window.opener.close();</script>");
    } else {
      writeln("<script>if (window.opener.history.length <= 1) window.opener.close();</script>");
    }

    writeln("</ul>");
    writeln("</body></html>");
    close();
  }
  
}