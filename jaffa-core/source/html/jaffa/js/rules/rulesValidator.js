/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *------------------------------------------------------------------------
 * rulesValidator.js - New javascript rules engine based on widget's extended attributes
 *
 * By Jonny Reid
 * Version: 1.0
 * Modified: 23-Feb-2005
 *------------------------------------------------------------------------
 * Javascript object to implement the rules engine for all the fields
 * on the page. 
 * All rules must be registered with the engine using the 'addFieldRule()' or 'addGlobalRule()' methods
 * All widgets must be regustered (via there Tag) using the 'addField()' method
 * A field is validated onBlur using 'validationRules.validateField(this)'
 * A page is validated before posting using 'validationRules.validatePage()'
 ************************************************************************/

function rules() {

  //Variable Registration
  this.addField = addField;
  this.fieldRules = new Array();
  this.global = false;
  this.globalRules = new Array();
  this.promptField = null;
  this.registeredFields = new Array();
  this.valid = true;

  //Method Registration
  this.addFieldRule = addFieldRule;
  this.addGlobalRule = addGlobalRule;
  this.getFieldCall = getFieldCall;
  this.getFieldErrors = getFieldErrors;
  this.getGlobalCall = getGlobalCall;
  this.setError = setError;
  this.setGlobalError = setGlobalError;
  this.validateField = validateField;
  this.validatePage = validatePage;

  //Add field rule method
  function addFieldRule(rule , method ) {
    this.fieldRules[this.fieldRules.length] = rule;
    this.fieldRules[rule] = method;
  }

  //Add page rule method
  function addGlobalRule(rule , method) {
    this.globalRules[this.globalRules.length] = rule;
    this.globalRules[rule] = method;
  }

  //Add field
  function addField(el) {
    this.registeredFields[this.registeredFields.length] = el;
  }

  //Performs the buiding of method call and eval for field level method
  function getFieldCall(indexAttribute , field) {
    return eval(this.fieldRules[this.fieldRules[indexAttribute]] + "(document.getElementById('" + field.id + "'),\"" + field.getAttribute(this.fieldRules[indexAttribute]) + "\")");
  }

  //Performs the buiding of method call and eval for global method
  function getGlobalCall(indexAttribute , field) {
    return eval(this.globalRules[this.globalRules[indexAttribute]] + "(document.getElementById('" + field.id + "'),\"" + field.getAttribute(this.globalRules[indexAttribute]) + "\")");
  }


  //Performs field level validation
  function getFieldErrors(el) {
    var errorString = "";
    for (j=0 ; j < this.fieldRules.length ; j++) {
      if(el.getAttribute(this.fieldRules[j])  != null) {
        var error = this.getFieldCall(j , el);
        if (error != null && error.length > 0) {
          if (errorString != "") errorString += ", ";
          errorString += error;
        }
      }
    }
    // If there is no error, check for any custom validations
    if ((errorString == null) || (errorString.length == 0)) {
      source = el.getAttribute("rules-serverValidation");
      if (source != null && source != "") 
      try {
        var field = el;
        var error = "";
        eval(source);
        errorString = error;
      } catch (e) {
        alert("Error:" + e + "\nExecuting Custom Code\n" + source);
        errorString = "Error in custom validation script";
      }
    }
    if ((errorString != null) && (errorString.length > 0)) 
      return errorString;
    else
      return "";
  }


  //Performs field level validation
  function validateField(el) {
    var errorString = null;
    if (this.global != "true") {
      errorString = this.getFieldErrors(el);
      
      if (errorString == "") {
        try {
          if (el.getAttribute('beenChanged') == "true") {
            document.body.removeChild(document.getElementById(el.id + "-error"));
          }
        } catch (e) {
        }
      } else
        this.setError(el , errorString);
    }
  }

  //Performs page level validation
  function validatePage() {
    var objs = foldingSectionList;
    for(i = 0;i<objs.length;i++){
      try {
        document.body.removeChild(document.getElementById(objs[i].id + "section-error"));
      } catch (e) {
      }
    }
    this.global = "true";
    this.valid = true;
    this.promptField=null;
    var validField = true;
    for (i=0 ; i < this.registeredFields.length ; i++) {
      var errorString = this.getFieldErrors(this.registeredFields[i]);
      for (j=0 ; j < this.globalRules.length ; j++) {
        if (this.registeredFields[i].getAttribute(this.globalRules[j]) != null) {
          var singleError = this.getGlobalCall(j , this.registeredFields[i]);
          if (singleError != null && singleError.length > 0) {
            if (errorString != "")
              errorString += ", ";
            errorString += singleError;
          }
        }
      }
      validField = this.setGlobalError(this.registeredFields[i] , errorString , true);
      if (validField != false) {
      } else {
        this.valid = validField;
      }
    }
    if (this.promptField != null) {
      el = this.promptField;
      eval("setTimeout(\"try{ document.getElementById('" + el.id + "').focus();document.getElementById('" + el.id + "').select();} catch(e) { }\" , 10);");
      try {
        el.focus();
      } catch(e) { }
    }
    this.global = "false";
    return this.valid;
  }


  //Assigns error
  function setError(el , message) {
    if (message != null) {
      this.promptField = null;
      this.setGlobalError(el, message);
      if (this.promptField != null) {
        el = this.promptField;
        this.valid = false;
      } else {
        this.promptField == null;
        this.valid = true;
      }
    }
  }



  //Builds error list and message DIV
  function setGlobalError(el , message , globalMode) {
    try {
      if (el.getAttribute('beenChanged') == "true") {
             document.body.removeChild(document.getElementById(el.id + "-error"));
      }
    } catch (e) {
    }
    if(message != null && message.length > 0) {

      var elFoldingSection = el;
      try {
        while (elFoldingSection.className != "WidgetFoldingSection") {
          if (elFoldingSection.parentNode != null) {
            elFoldingSection = elFoldingSection.parentNode;
          } else {
            break;
          }
        }
      } catch (e) {
      }

      if (elFoldingSection.className=="WidgetFoldingSection" && elFoldingSection.style.display!="block") {
	  // Move display to the right of any lookup
	   //Based on the language selection the corresponding x value calculated and
		//display the error message.
		//local variables declared
		var displayX;
		var line;
		var singleLeft;
		var singleRight;
	    if(rtlLangValue=="ar_OM"){
			displayX = findPosXRTL(elFoldingSection);
			line = "right";
			singleLeft = "singleRight";
			singleRight = "singleLeft";
		
		}else{
			displayX = findPosX(elFoldingSection) + elFoldingSection.offsetWidth;
		}
		
        elFoldingSection = document.getElementById(elFoldingSection.id + "section");
        var newHTML = "<div id='" + elFoldingSection.id + "-error' class='rulesError' style='top:" + (findPosY(elFoldingSection)-5) + "px" + ";"+line+":" + displayX + "px" + ";' >";
        newHTML+="<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        newHTML+="<tr>";
        newHTML+="<td width='100%' class='line'>&nbsp;</td>";
        newHTML+="<td width='1' class='"+singleLeft+"' width='5px'>&nbsp;</td>";
        newHTML+="<td class='mainError' nowrap>There were errors within section</td>";
        newHTML+="<td width='1' class='"+singleRight+"'>&nbsp;</td>";
        newHTML+="</tr>";
        newHTML+="</div>";
        if (document.getElementById(elFoldingSection.id + "-error") == null) {
          document.body.insertAdjacentHTML("afterBegin", newHTML);
        }
      } else {
        // Move display to the right of any lookup		
        var displayEl = el;
        var list = el.parentNode.childNodes;
        var afterNode=false;
        for(var i=0;i<list.length; i++)  {
           if(list[i]==el) afterNode=true;
           if(afterNode && (list[i].className=="Lookup"||list[i].className=="Calendar")) {
             displayEl = list[i];
             break;
           }
        }
        // Look for errors on the same line...and increment the Y position if there is an error displayed there!
		
		// Added Changes for moving mandatory message alert boxes from right to left.
		//Based on the language selection the corresponding x value calculated and
		//display the error message.

		var rtlLangValue = sessionStorage.getItem("RTLlanguage");
		if(rtlLangValue=="ar_OM"){
			displayX = findPosXRTL(displayEl);
			line = "right";
			singleLeft = "singleRight";
			singleRight = "singleLeft";
	    }
		else{	
			displayX = findPosX(displayEl) + displayEl.offsetWidth;
			line = "left";
			singleLeft = "singleLeft";
			singleRight = "singleRight";
		}
		var displayY = findPosY(el);
        var adjusted=true;
        while(adjusted) {
          list = document.getElementsByTagName('div');
          adjusted=false;
          for(var i=0;i<list.length; i++)  {
            if(list[i].className=="rulesError") {
              if(list[i].style.top==displayY+"px") {
                displayY+=3;
                adjusted=true;
                break;
              }  
            }
          }
        }  
        var newHTML="<div id='" + el.id + "-error' class='rulesError' style='top:" + displayY + "px" + ";"+line+":" + displayX + "px" + ";' >";
        newHTML+="<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
        newHTML+="<tr>";
        newHTML+="<td width='100%' class='line'>&nbsp;</td>";
        newHTML+="<td width='1' class='"+singleLeft+"' width='5px'>&nbsp;</td>";
        newHTML+="<td class='mainError' nowrap>"+ message + "</td>";
        newHTML+="<td width='1' class='"+singleRight+"'>&nbsp;</td>";
        newHTML+="</tr>";
        newHTML+="</div>";
        if (document.getElementById(el.id + "-error") == null) {
          document.body.insertAdjacentHTML("afterBegin", newHTML);
        }
      }

      el.setAttribute('validated' ,'false');
      el.setAttribute('beenChanged' , 'true');
      if (this.promptField == null) {
        this.promptField = el;
      }
      return false;
    } else {
      el.setAttribute('validated' ,'true');
      el.setAttribute('beenChanged' , 'false');
      return true;
    }
  }

}//rules


//Assigns beenChanged attribute to element
function fieldChange(el) {
  el.setAttribute('beenChanged' , 'true');
  el.setAttribute('validated' , 'false');

  // Change the case for an EditBox
  if (el.className == "WidgetEditBox" || el.className == "WidgetMandatoryEditBox") {
    if (el.style.textTransform == 'uppercase') {
      assignValueAndPreserveSelection(el, el.value.toUpperCase());
    } else if (el.style.textTransform == 'lowercase') {
      assignValueAndPreserveSelection(el, el.value.toLowerCase());
    }
  }
}

//Sets field as not being successfully validated
function setValidatedFalse(el) {
  el.setAttribute('validated','false');
}

// Bind a new method to the document object
document.removeChildNodes = function(node) {
  while (node.childNodes.length > 0) {
    node.removeChild(node.childNodes[0]);
  }
  node = null;
}

/** Some rules modify the value of a field, which messes up the current selection
  * The current selection is very important for the replaceSelection() method of lookup.jsp
  * This method assigns the newValue to the field, only if it is different from the field's existing value.
  * The current selection, if any, will be preserved.
  */
function assignValueAndPreserveSelection(el, newValue) {
  if (el.value != newValue) {
    if (el.type == "textarea") {
      if (document.selection) {
        // IE:Determine the current selection
        var range1 = document.selection.createRange();
        var text = range1.text;
        if (text != "") {
          /* Determine the startIndex of the current selection. This involves
            1- Create a new range
            2- Move the new range to the current element
            3- Collapse the new range so that it starts from index0
            4- Set its end-point to match the start-point of the current selection
            5- The startIndex is the length of the selected text in the new range
            6- Apply a correction to the startIndex, removing all possible carriageReturns
          */
          var range2 = document.body.createTextRange();
          range2.moveToElementText(el);
          range2.collapse(true);
          range2.setEndPoint("EndToStart", range1);
          var startIndex = range2.text.length;
          startIndex = startIndex - carriageReturnCount(range2.text);

          // Assign the newValue to the current field
          el.value = newValue;

          /* To preserve the current selection
            1- Move the range to the startIndex
            2- Search the selected text, if any
            3- Select the text
          */
          var maxMove = el.value.length - carriageReturnCount(el.value);
          if (startIndex <= maxMove)
            range1.move('character', startIndex);
          else
            range1.move('character', maxMove);
          range1.findText(text);
          range1.select();
        } else {
          el.value = newValue;
        }
      } else if (el.setSelectionRange) {
        try {
          // Mozilla: Life is a lot easier here
          var selectionStart = el.selectionStart;
          var selectionEnd = el.selectionEnd;
          
          // Assign the newValue to the current field
          el.value = newValue;
          
          if (selectionStart != selectionEnd) {
            // Reset the selection
            el.setSelectionRange(selectionStart, selectionEnd);
          }
        } catch (e) {
          // Since, Firefox 2.0.0.7, an exception is thrown if the field is inside a 'closed' FoldingSection
          el.value = newValue;
        }
      } else {
        el.value = newValue;
      }
    } else {
      el.value = newValue;
    }
  }
}