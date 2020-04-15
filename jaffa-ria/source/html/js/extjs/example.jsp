<%@ page import = "org.jaffa.util.URLHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%
  String pageRef=request.getRequestURI().substring(request.getContextPath().length()+1);
%>
<html>
<head>
  <base href='<%= URLHelper.getBase((HttpServletRequest) pageContext.getRequest()) %>' />
  <!-- ExtJS Core -->
  <script type="text/javascript" src="js/extjs/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="js/extjs/ext-all-debug.js"></script>

  <!-- ExtJS Add-Ons -->
  <script type="text/javascript" src="js/extjs/ext-extensions.js"></script>
  <script type="text/javascript" src="js/extjs/ext-overrides.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/data/Util.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/data/MemoryProxy.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/data/Record.js"></script>

  <!-- DWR Service Infrastructure -->
  <script type='text/javascript' src='dwr/engine.js'></script>
  <script type='text/javascript' src='dwr/interface/XxxxXxxx.js'></script>
  <script type="text/javascript" src="js/extjs/data/DWRProxy.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/soa/DWRService.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/metadata/ClassMetaData.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/metadata/classMetaData.jsp?..."></script>

  <!-- ExtJS UI Extentions -->
  <script type="text/javascript" src="js/extjs/jaffa/metadata/labels.jsp?ref=<%=StringHelper.escapeHtml(pageRef)%>"></script>
  <script type="text/javascript" src="js/extjs/grid/RowExpander.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderComboBox.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderComboGrid.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderGridPanel.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderOutDto.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderReader.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/finder/FinderStore.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/form/MyTextField.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/layout/FullAccordion.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/tree/DwrTreeLoader.js"></script>
  <script type="text/javascript" src="js/extjs/ux/TabCloseMenu.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/GridFilters.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/BooleanFilter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/DateFilter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/Filter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/ListFilter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/NumericFilter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/grid/filter/StringFilter.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/menu/EditableItem.js"></script>
  <script type="text/javascript" src="js/extjs/ux/filter/menu/RangeMenu.js"></script>
  <script type="text/javascript" src="js/extjs/ux/form/DateTime.js"></script>

  <!-- State Management -->
  <script type="text/javascript" src="js/extjs/state/SessionProvider.js"></script>
  <script type="text/javascript" src="js/extjs/jaffa/state/querySaver.jsp?pageRef=<%=StringHelper.escapeHtml(pageRef)%>"></script>
  <script type="text/javascript" src="js/extjs/jaffa/state/widgetStateSaver.jsp?pageRef=<%=StringHelper.escapeHtml(pageRef)%>"></script>
  <script type="text/javascript" src="js/extjs/jaffa/state/Criteria.js"></script>

</head>
<body>
</body>
</html>
