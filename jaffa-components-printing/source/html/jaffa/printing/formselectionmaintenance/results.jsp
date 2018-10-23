<%-- ----------------------------------------------------------------
  -- Code Generated From JAFFA Framework Default Pattern           --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>

<%@ page language="java" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.TagHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ page import="org.jaffa.datatypes.Formatter" %>
<%@ page import="org.jaffa.exceptions.ApplicationExceptions" %>
<%@ page import="org.jaffa.exceptions.ApplicationException" %>
<%@ page import="org.jaffa.exceptions.FrameworkException" %>

<%@ page import="org.jaffa.modules.printing.services.FormPrintFactory" %>
<%@ page import="org.jaffa.components.finder.FinderComponent2" %>
<%@ page import="org.jaffa.components.finder.FinderForm" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.taglib.UserGridTag" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.modules.printing.components.formselectionmaintenance.dto.*" %>
<%@ page import="org.jaffa.modules.printing.components.formselectionmaintenance.ui.FormSelectionMaintenanceForm" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>

<bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_WEB_PAGE %>"/>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_EXCEL %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_EXCEL %>"/>
</logic:equal>
<logic:equal name="<%= TagHelper.getFormName(pageContext) %>" property="exportType" value="<%= FinderComponent2.EXPORT_TYPE_XML %>">
  <bean:define id="outputType" type="java.lang.String" value="<%= UserGridTag.OUTPUT_TYPE_XML %>"/>
</logic:equal>

<%! private static int MAX_VALUE_LENGTH = 50; %>

<Portlet:UserGrid field="rows" userGridId="jaffa_printing_formSelectionMaintenance" outputType="<%= outputType %>">

<%
String formType=null;
String key1=null;
String key2=null;
String key3=null;
String key4=null;
String key5=null;
String key6=null;
String value1=null;
String value2=null;
String value3=null;
String value4=null;
String value5=null;
String value6=null;
String key1Label=null;
String key2Label=null;
String key3Label=null;
String key4Label=null;
String key5Label=null;
String key6Label=null;
ApplicationExceptions appExps = null;
StringBuffer errors = null;
boolean isException = false;
if(TagHelper.getModelMap(pageContext)!=null){
  formType = (String) TagHelper.getModelMap(pageContext).get("formType");
  key1 = (String) TagHelper.getModelMap(pageContext).get("key1");
  key2 = (String) TagHelper.getModelMap(pageContext).get("key2");
  key3 = (String) TagHelper.getModelMap(pageContext).get("key3");
  key4 = (String) TagHelper.getModelMap(pageContext).get("key4");
  key5 = (String) TagHelper.getModelMap(pageContext).get("key5");
  key6 = (String) TagHelper.getModelMap(pageContext).get("key6");
  value1 = (String) TagHelper.getModelMap(pageContext).get("value1");
  if (value1 != null && value1.length() > MAX_VALUE_LENGTH)
    value1 = value1.substring(0, MAX_VALUE_LENGTH) + "...";
  value2 = (String) TagHelper.getModelMap(pageContext).get("value2");
  if (value2 != null && value2.length() > MAX_VALUE_LENGTH)
    value2 = value2.substring(0, MAX_VALUE_LENGTH) + "...";
  value3 = (String) TagHelper.getModelMap(pageContext).get("value3");
  if (value3 != null && value3.length() > MAX_VALUE_LENGTH)
    value3 = value3.substring(0, MAX_VALUE_LENGTH) + "...";    
  value4 = (String) TagHelper.getModelMap(pageContext).get("value4");
  if (value4 != null && value4.length() > MAX_VALUE_LENGTH)
    value4 = value4.substring(0, MAX_VALUE_LENGTH) + "...";
  value5 = (String) TagHelper.getModelMap(pageContext).get("value5");
  if (value5 != null && value5.length() > MAX_VALUE_LENGTH)
    value5 = value5.substring(0, MAX_VALUE_LENGTH) + "...";
  value6 = (String) TagHelper.getModelMap(pageContext).get("value6");
  if (value6 != null && value6.length() > MAX_VALUE_LENGTH)
    value6 = value6.substring(0, MAX_VALUE_LENGTH) + "...";        
  key1Label = "[label.Jaffa.Printing.FormSelection.Key."+key1+"]";
  key2Label = "[label.Jaffa.Printing.FormSelection.Key."+key2+"]";
  key3Label = "[label.Jaffa.Printing.FormSelection.Key."+key3+"]";
  key4Label = "[label.Jaffa.Printing.FormSelection.Key."+key4+"]";
  key5Label = "[label.Jaffa.Printing.FormSelection.Key."+key5+"]";
  key6Label = "[label.Jaffa.Printing.FormSelection.Key."+key6+"]";
  if (TagHelper.getModelMap(pageContext).get("errMessage") instanceof ApplicationExceptions) {
    appExps = (ApplicationExceptions) TagHelper.getModelMap(pageContext).get("errMessage");
    if (appExps != null && appExps.size() > 0) {
      for (Iterator i = appExps.iterator(); i.hasNext();) {
        ApplicationException appExp = (ApplicationException) i.next();
        isException = true;
        if (errors == null)
          errors = new StringBuffer();
        else
          errors.append(',');
        errors.append(MessageHelper.findMessage(pageContext, appExp.getMessage(), appExp.getArguments()));
      }
    }
  }
  if (TagHelper.getModelMap(pageContext).get("errMessage") instanceof FrameworkException) {
    FrameworkException fwExp = (FrameworkException) TagHelper.getModelMap(pageContext).get("errMessage");
    if (fwExp != null) {
      isException = true;
      errors = new StringBuffer();
      errors.append(MessageHelper.findMessage(pageContext, fwExp.getMessage(), fwExp.getArguments()));      
    }
  }  
}
%>

  <Portlet:Property field='select' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'> 
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.Select]'>
      <Portlet:CheckBox />
      <% if (errors != null) { %>                                
         <a href="javascript:displayPopup('<%=errors%>');"><img src="jaffa/imgs/icons/warningIcon.gif" title='<%=errors%>' border="0"></a>
      <%}%>                         
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <Portlet:Property field='formName' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.FormName]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <Portlet:Property field='event' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.Event]'>
      <Portlet:Text />
    </Portlet:UserGridColumn>
  </Portlet:Property>

  <% if (value1 !=null){%>
  <Portlet:Property field='value1' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key1Label%>'>
      <%= value1 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>

  <% if (value2 !=null){%>
  <Portlet:Property field='value2' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key2Label%>'>
      <%= value2 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>

  <% if (value3 !=null){%>
  <Portlet:Property field='value3' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key3Label%>'>
      <%= value3 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>
  
  <% if (value4 !=null){%>
  <Portlet:Property field='value4' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key4Label%>'>
      <%= value4 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>
  
  <% if (value5 !=null){%>
  <Portlet:Property field='value5' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key5Label%>'>
      <%= value5 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>
  
  <% if (value6 !=null){%>
  <Portlet:Property field='value6' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='<%=key6Label%>'>
      <%= value6 %>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  <%}%>  

  <logic:equal name="<%=TagHelper.getFormName(pageContext) %>" property="printing" value="true">
  <Portlet:Property field='printer' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.Printer]'>
      <Portlet:EditBox columns="20"/>
            <Portlet:Lookup component='Jaffa.Printing.PrinterDefinitionLookup' targetFields='printer=printerId' dynamicParameters='printerId=printer'>&nbsp;
          </Portlet:Lookup>&nbsp;
    </Portlet:UserGridColumn>
  </Portlet:Property>
  </logic:equal>

  <logic:equal name="<%=TagHelper.getFormName(pageContext) %>" property="email" value="true">
  <Portlet:Property field='email' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.Email]'>
      <%-- Only allow e-mail if the output is going to be PDF --%>
      <%if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(formType) || FormPrintFactory.ENGINE_TYPE_FOP.equals(formType)) {%>
      <Portlet:EditBox columns="20"/>
	  &nbsp;<Portlet:Lookup>&nbsp;</Portlet:Lookup>
      <%}else{%>
      <Portlet:Text/>
      <%}%>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  </logic:equal>

<%-- Publish Functionality not available yet
  <logic:equal name="<%=TagHelper.getFormName(pageContext) %>" property="webPublish" value="true">
  <Portlet:Property field='publish' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="CaseInsensitiveString" label='[label.Jaffa.Printing.FormSelection.Publish]'>
      < -- Only allow web publish if the output is going to be PDF -- >
      <%if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(formType) || FormPrintFactory.ENGINE_TYPE_FOP.equals(formType)) {%>
      <Portlet:CheckBox/>
      <%}else{%>
      <Portlet:CheckBox displayOnly="true"/>
      <%}%>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  </logic:equal>
--%>

  <logic:equal name="<%=TagHelper.getFormName(pageContext) %>" property="printing" value="true">
  <Portlet:Property field='copies' propertyClass='org.jaffa.modules.printing.components.formselectionmaintenance.dto.FormSelectionMaintenanceOutRowDto'>
    <Portlet:UserGridColumn dataType="Number" label='[label.Jaffa.Printing.FormSelection.Copies]'>
      <Portlet:EditBox columns="2"/>
    </Portlet:UserGridColumn>
  </Portlet:Property>
  </logic:equal>

  <Portlet:UserGridColumn label="[label.Jaffa.Widgets.Button.Action]">
    <%-- Hide the button if there is no detail screen --%>
    <%if(TagHelper.getModelMap(pageContext).get("additionalDataComponent") != null) {%>
    <Portlet:Button type="link" field="UpdateDetail" label="[label.Jaffa.Printing.FormSelection.Button.UpdateDetail]" />
    <%}%>
    
    <logic:equal name="<%=TagHelper.getFormName(pageContext) %>" property="directDisplay" value="true">
    <%if (FormPrintFactory.ENGINE_TYPE_ITEXT.equals(formType) || FormPrintFactory.ENGINE_TYPE_FOP.equals(formType)) {%>
      <%if (isException) {%>
        <Portlet:Button field="ShowForm" label="[label.Jaffa.Printing.FormSelection.Button.ShowForm]" image="jaffa/imgs/icons/pdf.gif" target="_self"/>
      <%} else {%>
        <Portlet:Button field="ShowForm" label="[label.Jaffa.Printing.FormSelection.Button.ShowForm]" image="jaffa/imgs/icons/pdf.gif" target="_blank" />
      <%}%>
    <%}%>
    </logic:equal>
    
  </Portlet:UserGridColumn>
</Portlet:UserGrid>

<SCRIPT type="text/javascript">
function displayPopup(str){   
 //Added the Localization code for RTL Error pop up 
	var isRTL = sessionStorage.getItem("isRTLlanguage");
  myWindow = window.open('','Warning','scrollbars=no,width=450,height=200;resizable,left=25;top=25;');
  myWindow.document.bgColor="white";
  if(Boolean(isRTL)){
  myWindow.document.write("<font color='darkred' size=3><h1 dir='rtl'>Error</h1></font>");
  myWindow.document.write("<body dir=rtl>",str);
  }else{
  myWindow.document.write("<font color='darkred' size=3><h1>Error</h1></font>");
  myWindow.document.write(str);
		}
  myWindow.document.close();
}


function preProcessConditionCode() {          
  var formId="<%=TagHelper.getFormTag(pageContext).getHtmlIdPrefix()%>";
  var formEl=document.getElementById(formId); 
  if (formEl.eventId.value == 'Finish;Clicked' || formEl.eventId.value == 'Preview;Clicked'){
    var directDisplayOnly=false;
    <%
    FormSelectionMaintenanceForm f = ((FormSelectionMaintenanceForm)TagHelper.getFormBase(pageContext));
    if( f.getDirectDisplay() && !(f.getPrinting()||f.getEmail()||f.getWebPublish()) ){
    %>
      directDisplayOnly=true;
    <%}%>
    var selected=false;
    var i=0;
    while(!selected) {
      var el = document.getElementById(formId+'_rows_'+i+'_select');
      if(el!=null){  
        if((el.type=="checkbox" && el.checked) || (el.type=="hidden" && el.value == "true")) {
          selected=true;
        }
      } else  
        break;
      i++;
    }
    if(!selected && formEl.eventId.value == 'Finish;Clicked'){ 
      formEl.target='_self';
      if(! confirm('<%= MessageHelper.findMessage(pageContext, "label.Jaffa.Printing.FormSelection.InvalidSelect", null) %>'))
        return;
    }else if(!selected && formEl.eventId.value == 'Preview;Clicked'){
      alert('<%=MessageHelper.findMessage(pageContext, "label.Jaffa.Printing.FormSelection.InvalidPreview", null) %>');
      return;
    }
    else if (directDisplayOnly){
      formEl.target='_self';
    }
  }    
  originalPostForm(formEl,true);
}

this.originalPostForm = postForm; 
this.postForm = preProcessConditionCode;
</SCRIPT>
