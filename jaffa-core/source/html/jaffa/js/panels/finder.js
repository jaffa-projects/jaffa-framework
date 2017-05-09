/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/


//@todo - figure out if these are still needed
var v_selectedExportTypeObject, v_webPageExportTypeObject;

// This is a hack to post a form to a new window if the export type is either 'E'xcel or 'X'ml
function exportType() {
  // determine the v_exportTypeSelected
  var v_exportTypeSelected;
  v_elements = document.getElementsByName("exportTypeWV");
  if (v_elements != null && v_elements.length > 0) {
    for (v_i = 0; v_i < v_elements.length; v_i++) {
      if (v_elements[v_i].type == "radio" && v_elements[v_i].checked) {
        v_exportTypeSelected = v_elements[v_i].value;
        v_selectedExportTypeObject = v_elements[v_i];
      }
      if(v_elements[v_i].value == "W")
        v_webPageExportTypeObject = v_elements[v_i];
    }
    debug("Value of exportType is: " + v_exportTypeSelected);
  }
  return v_exportTypeSelected;
}

function postProcess(frm, fld) {
  // determine the v_eventId
  v_elements = document.getElementsByName("eventId");
  if (v_elements != null && v_elements.length > 0)
  {
    v_eventId = v_elements[0].value;
    v_formName = v_elements[0].form.name;
    debug("Value of eventId is: " + v_eventId);
    debug("Value of formName is: " + v_formName);
  }

  // set the target to '_blank' if the eventId='Search;Clicked' and exportType is 'E'xcel or 'X'ml
  // also get rid of the transition page
  v_exportType=exportType();
  if (v_eventId != null && v_eventId == "Search;Clicked" && (v_exportType == "E" || v_exportType == "X"))  {
    document.forms[0].target = '_blank';
    if (document.getElementById(v_formName + "_EntirePage") != null &&
        document.getElementById(v_formName + "_EntirePage").style.display == "none") {
      document.getElementById(v_formName + "_EntirePage").style.display = "block";
      document.getElementById(v_formName + "_PushPage").style.display = "none";
    }
  }
  if (v_eventId != null && v_eventId == "RunQuery;Clicked")  {
    var savedQueryDropDown = document.getElementById('j1_savedQueryId');
    if(savedQueryDropDown && queryTypes[savedQueryDropDown.value]!='W'){
      document.forms[0].target = '_blank';
    }
  }
}

function excelExport(){
  var exportData = {columns:[],records:[]};
  var columns = [];
  $( ".exportTable" ).children("thead").find(".exportColumnHeader").each(
    function(i){
      columns[columns.length]={dataIndex:$(this).text().replace(/\s+/g, ''),header:$(this).text(), dataType:$(this).parent().attr("type")};
      if (columns[i].dataType!=null && columns[i].dataType!='')
        exportData.columns[exportData.columns.length]=columns[i];
    }
  );
  $( ".exportTable" ).children("tbody").children("tr").each(
    function(j){
      var record={};
      $(this).children("td").each(
        function(k){
          if (columns[k].dataType!=null && columns[k].dataType!=''){
            if($(this).find(".WidgetCheckBox") && $(this).find(".WidgetCheckBox").length && $(this).find(".WidgetCheckBox").length>0){
              record[columns[k].dataIndex]=$(this).find(".WidgetCheckBox").val();
            }else if ($(this).find(':checkbox') && $(this).find(':checkbox').length && $(this).find(':checkbox').length >0){
              record[columns[k].dataIndex]=$(this).find(':checkbox')[0].checked;
            }else if($(this).find(".WidgetEditBox") && $(this).find(".WidgetEditBox").length && $(this).find(".WidgetEditBox").length>0){
              record[columns[k].dataIndex]=$(this).find(".WidgetEditBox").val();
            }else if($(this).find(".WidgetMandatoryEditBox") && $(this).find(".WidgetMandatoryEditBox").length && $(this).find(".WidgetMandatoryEditBox").length>0){
              record[columns[k].dataIndex]=$(this).find(".WidgetMandatoryEditBox").val();
            }else if($(this).find(".WidgetRadioButton") && $(this).find(".WidgetRadioButton").length && $(this).find(".WidgetRadioButton").length>0){
              record[columns[k].dataIndex]=$(this).find(".WidgetRadioButton").is(":checked");
	    }else{
              record[columns[k].dataIndex]=$(this).text();
            }
          }
        }
      )
      exportData.records[exportData.records.length]=record;
    }
  );

  var form = $('<form method="POST" action=js/extjs/jaffa/grid/simpleExportToExcel.jsp/>');
  var input = $('<input type="hidden" name="jsonToExport" </input>');
  input.val(JSON.stringify(exportData));
  var sheetName = $('<input type="hidden" name="sheetName"</input>');
  sheetName.val(window.document.title);
  var excelName = $('<input type="hidden" name="excelName"</input>');
  excelName.val(window.document.title.replace(/\s+/g, ''));

  form.append(input);
  form.append(sheetName);
  form.append(excelName);
  $(document.body).append(form);

  form.submit();
  setTimeout(function(){form.remove();}, 100);
}

$( document ).ready(
  function() {
    $('input[name="exportTypeWV"][value="E"]').parent().remove();
    if ($( ".exportTable" ) && $( ".exportTable" ).length ==1){
      $(".contextNavBody").each(
        function (index, table){
          $(table).append("<tr><td><img src='jaffa/imgs/icons/excel.gif' ><a style='padding-left:5px;' href='javascript:excelExport()'>" + Labels.exportToExcel + "</a><tr><td>");
        }
      )
    }
  }
);
