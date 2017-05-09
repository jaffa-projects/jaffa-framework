

<%@ page language="java" %>
<%@ page import="org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.session.IContextManager" %>
<%@ page import="org.jaffa.*" %>

<%@ page import="org.jaffa.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jaffa.components.codehelper.dto.CodeHelperInDto" %>
<%@ page import="org.jaffa.components.codehelper.dto.CodeHelperInElementDto" %>
<%@ page import="org.jaffa.components.codehelper.dto.CodeHelperOutDto" %>
<%@ page import="org.jaffa.components.codehelper.dto.CodeHelperOutElementDto" %>
<%@ page import="org.jaffa.components.codehelper.dto.CodeHelperOutCodeDto" %>
<%@ page import="org.jaffa.components.codehelper.dto.CriteriaElementDto" %>
<%@ page import="org.jaffa.persistence.Criteria.*" %>
<%@ page import="org.jaffa.presentation.portlet.widgets.model.DropDownModel" %>
<%@ page import="org.jaffa.presentation.portlet.session.UserSession" %>

 <script>    
     currentValue = parent.document.getElementById("<%=request.getParameter("Id")%>").value;
     parent.document.getElementById("<%=request.getParameter("Id")%>").options.length = 0;
     parent.document.getElementById("<%=request.getParameter("Id")%>").options.add(new Option("Loading ..........","Loading .........."));        
 </script>

<%
  IContextManager cm = ContextManagerFactory.instance();
  cm.setThreadContext(request);
  CriteriaElementDto[] criteriaElementDtos;
  criteriaElementDtos = new CriteriaElementDto[3];  
  org.jaffa.util.CallBackDropdownHelper cbddHelper = new org.jaffa.util.CallBackDropdownHelper();

  {
    CriteriaElementDto criteriaElementDto = new CriteriaElementDto();
    criteriaElementDto.setFieldName(request.getParameter("key1"));
    criteriaElementDto.setCriteria(org.jaffa.components.finder.StringCriteriaField.getStringCriteriaField(org.jaffa.components.finder.CriteriaField.RELATIONAL_EQUALS, request.getParameter("dd1") , null));
    criteriaElementDtos[0] = criteriaElementDto;
  }
  
  if (request.getParameter("dd2") != null && request.getParameter("dd2").length() > 0) {
   
    CriteriaElementDto criteriaElementDto = new CriteriaElementDto();
    criteriaElementDto.setFieldName(request.getParameter("key2"));
    criteriaElementDto.setCriteria(org.jaffa.components.finder.StringCriteriaField.getStringCriteriaField(org.jaffa.components.finder.CriteriaField.RELATIONAL_EQUALS, request.getParameter("dd2") , null));
    criteriaElementDtos[1] = criteriaElementDto;
  }
  if (request.getParameter("dd3") != null && request.getParameter("dd3").length() > 0) {
    CriteriaElementDto criteriaElementDto = new CriteriaElementDto();
    criteriaElementDto.setFieldName(request.getParameter("key3"));
    criteriaElementDto.setCriteria(org.jaffa.components.finder.StringCriteriaField.getStringCriteriaField(org.jaffa.components.finder.CriteriaField.RELATIONAL_EQUALS, request.getParameter("dd3") , null));
    criteriaElementDtos[criteriaElementDtos.length] = criteriaElementDto;
  }

  CodeHelperOutElementDto m_dropDownCodes = cbddHelper.getOptions(request, request.getParameter("FieldName"), request.getParameter("Description"), request.getParameter("Domain") ,criteriaElementDtos);
  
  
  DropDownModel dropDownModel = (DropDownModel) UserSession.getUserSession(request).getWidgetCache(request.getParameter("ComponentId")).getModel(request.getParameter("WidgetFieldName"));
  dropDownModel = new DropDownModel();
  
%>


<script>
     parent.document.getElementById("<%=request.getParameter("Id")%>").options.length = 0;
     <% for (int i=0; i< m_dropDownCodes.getCodeHelperOutCodeDtoCount(); i++) {
        dropDownModel.addOption("" + m_dropDownCodes.getCodeHelperOutCodeDto(i).getDescription() ,"" +  m_dropDownCodes.getCodeHelperOutCodeDto(i).getCode());          
     %>            
        parent.document.getElementById("<%=request.getParameter("Id")%>").options.add(new Option("<%=m_dropDownCodes.getCodeHelperOutCodeDto(i).getDescription()%>","<%=m_dropDownCodes.getCodeHelperOutCodeDto(i).getCode()%>"));        
     <% }     
     UserSession.getUserSession(request).getWidgetCache(request.getParameter("ComponentId")).addModel(request.getParameter("WidgetFieldName") ,dropDownModel );     
     %>
     parent.document.getElementById("<%=request.getParameter("Id")%>").value = "<%=request.getParameter("CurrentValue")%>";
 </script>
  
  
 
