/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

function dropdownManager(){
  this.dropDown = new Array();
  this.dropDownDetails = new Array();
  this.dropDownDependant = new Array();
  this.addDropDown = addDropDown;
  this.addDropDownDetails = addDropDownDetails;
  this.addDropDownDependant = addDropDownDependant;
  this.registerDropDown = registerDropDown;
  this.getDetails = getDetails;

  //Add DropDown rule method
  function addDropDown(id) {
    this.dropDown[this.dropDown.length] = id;
  }

  //Add Details rule method
  function addDropDownDetails(id , details) {
    this.dropDownDetails[id] = details;
  }

  function addDropDownDependant(id , dependantId) {
    if (this.dropDownDependant[id] == null) {
      dependantDD = new Array();
      dependantDD[0] = dependantId;
      dependantDD[dependantId] = dependantId;
      this.dropDownDependant[id] = dependantDD;
    } else {
      this.dropDownDependant[this.dropDownDependant.length] = dependantDD;
      if (this.dropDownDependant[id][dependantId] == null) {
        this.dropDownDependant[id][dependantId] = dependantId;
        this.dropDownDependant[id][this.dropDownDependant[id].length] = dependantId;
      }
    }
  }

  function getDetails() {
     alert(this.dropDown);
     alert(this.dropDownDetails["b"].criteriaDropDown);
     alert(this.dropDownDependant["c"]);
  }

  function registerDropDown(key1 , key2 , key3 , dd1 , dd2 , dd3 , domain, id , fieldName , description, widgetFieldName) {
    details = new dropdownDetails();
    if (dd1 !=null) {
       details.addCriteriaDropDowns(dd1);
       details.addDomainKey(dd1, key1);
       this.addDropDownDependant(dd1,id);
       addOnChangeEvent(document.getElementById(dd1));
    }

    if (dd2 !=null && dd2.length > 0) {
       details.addCriteriaDropDowns(dd2);
       details.addDomainKey(dd2, key2);
       this.addDropDownDependant(dd2,id);
       addOnChangeEvent(document.getElementById(dd2));
    }

    if (dd3 !=null && dd3.length > 0) {
       details.addCriteriaDropDowns(dd3);
       details.addDomainKey(dd3, key3);
       this.addDropDownDependant(dd3,id);
       addOnChangeEvent(document.getElementById(dd3));
    }
    addFocusEvent(document.getElementById(id));
    details.domain = domain;
    details.fieldName = fieldName;
    details.widgetFieldName = widgetFieldName;
    details.description = description;
    this.addDropDown(id);
    this.addDropDownDetails(id , details);
  }

}


function dropdownDetails(){
  this.criteriaDropDown = new Array();
  this.domainKey = new Array();
  this.domain = null;
  this.fieldName = null;
  this.widgetFieldName = null;
  this.description = null;
  this.value = null;
  this.addCriteriaDropDowns = addCriteriaDropDowns;
  this.addDomainKey = addDomainKey;
  function addCriteriaDropDowns(id) {
    this.criteriaDropDown[this.criteriaDropDown.length] = id;
  }
  function addDomainKey(id, key) {
    this.domainKey[id] = key;
  }
}

function addFocusEvent(elem) {
  if (elem.attachEvent)
    {
     elem.attachEvent("onfocus",buildchoices);
    }
  else if (elem.addEventListener)
    {
     elem.addEventListener("focus",buildchoices,false);
  }
}

function addOnChangeEvent(elem) {
  if (elem.attachEvent)
    {
     elem.attachEvent("onchange",resetDependants);
    }
  else if (elem.addEventListener)
    {
     elem.addEventListener("change",resetDependants,false);
  }
}


function buildchoices(evt) {
  evt = (evt) ? evt : ((window.event) ? window.event : "");
  var elem = (evt.target) ? evt.target : evt.srcElement;
  Id = elem.id;
  dropdown = ddm.dropDownDetails[Id]
  criteriaIds =  dropdown.criteriaDropDown;
  criteriaKeys = dropdown.domainKey;
  domain = dropdown.domain;
  fieldName = dropdown.fieldName;
  widgetFieldName = dropdown.widgetFieldName;
  description = dropdown.description;
  dd1= null;
  key1 = null;
  try {
    dd1= document.getElementById(criteriaIds[0]).value;
    key1 = criteriaKeys[criteriaIds[0]];
  } catch (e){
  }
  dd2= null;
  key2 = null;
  try {
    dd2= document.getElementById(criteriaIds[1]).value;
    key2 = criteriaKeys[criteriaIds[1]];
  } catch (e){
  }
  dd3= null;
  key3 = null;
  try {
    dd3= document.getElementById(criteriaIds[2]).value;
    key3 = criteriaKeys[criteriaIds[2]];
  }catch (e){}
  if (document.getElementById("settingsIFrame") != null) {
    document.getElementById('iframe').removeChild(document.getElementById("settingsIFrame"));
  }
  if (dropdown.value != dd1+dd2+dd3) {
    dropdown.value = dd1+dd2+dd3;
    if (dd1 != null && dd1.length > 0) {
      newHTML = "<iframe id=\"settingsIFrame\"  src=\"jaffa/jsp/iframe/retrieveDropdownValues.jsp?Id=" + Id + (key1 != null ? "&key1=" + key1 : "") + (dd1 != null ? "&dd1=" + dd1 :"") + (key2 != null ? "&key2=" + key2 : "") + (dd2 != null ? "&dd2=" + dd2 :"") + (key3 != null ? "&key3=" + key3 : "") + (dd3 != null ? "&dd3=" + dd3 :"")   + "&FieldName=" + fieldName + "&Description=" + description + "&Domain=" + domain + "&CurrentValue=" + elem.value + "&ComponentId=" + elem.form.componentId.value + "&WidgetFieldName=" + widgetFieldName + "\">";
      newHTML += "</iframe>";

      elem.options.length = 0;
      elem.options.add(new Option("Loading ..........","Loading .........."));
      elem.value = "Loading ..........";
      document.getElementById('iframe').insertAdjacentHTML("afterBegin",newHTML);
    }
 }
}

function resetDependants(evt) {
  evt = (evt) ? evt : ((window.event) ? window.event : "");
  var elem = (evt.target) ? evt.target : evt.srcElement;
  for (i = 0 ; i < ddm.dropDownDependant[elem.id].length ; i++) {
    try {
      document.getElementById(ddm.dropDownDependant[elem.id][i]).options.length = 0;
      resetKids(ddm.dropDownDependant[elem.id][i]);
    } catch (e) {
    }
  }

}

function resetKids(id) {
    for (i = 0 ; i < ddm.dropDownDependant[id].length ; i++) {
      try {
        document.getElementById(ddm.dropDownDependant[id][i]).options.length = 0;
        resetKids(ddm.dropDownDependant[id][i]);
      } catch (e) {
      }
    }
}



var ddm = new dropdownManager();