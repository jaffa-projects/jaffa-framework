<%--
This JSP invokes a query service. The output from the service will be returned as a simple HTML table, which can then be easily renderd inside Excel.

Inputs to this JSP are:
  - sheetName: the name of the excel sheet (optional, defaults to exportToExcel)
  - criteriaClassName: the name of the criteria class
  - criteriaObject: criteria as a JSON structure
  - serviceClassName: the name of the service class that should contain the method 'public AGraphDataObject[] query(AGraphCriteria criteria)'
  - serviceMethodName: the name of the service class method to call that should look like 'public AGraphDataObject[] serviceClassMethod(AGraphCriteria criteria)'
  - columnModel: JSON structure containing an array of column elements, each element having 'header', 'mapping' and 'layout' for providing the column-title, data and layout respectively
  - masterKeyFieldNames: JSON structure of an array of key field names in the master object
  - detailCriteriaClassName: the name of the criteria class
  - detailCriteria: criteria as a JSON structure (should contain {#} for the key field values to be replaced)
  - detailServiceClassName: the name of the service class that should contain the method 'public AGraphDataObject[] query(AGraphCriteria criteria)'
  - detailColumnModel: JSON structure containing an array of column elements, each element having 'header', 'mapping' and 'layout' for providing the column-title, data and layout respectively

Note: The implementation is generic that applies to a master object class aggregating a child object class. 

@author Sean Zhou, Sep, 2010. extends to handle aggregating with single child object. 

--%>

<%@ page import = "org.jaffa.ria.finder.apis.QueryServiceConfig" %>
<%@ page import = "org.jaffa.qm.finder.apis.ExcelExportService" %>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import = "org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import = "org.apache.poi.ss.usermodel.Workbook" %>
<%@ page import = "org.apache.poi.xssf.streaming.SXSSFWorkbook" %>
<%@ page import = "org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.util.StringHelper" %>

<%@ taglib uri='/WEB-INF/jaffa-portlet.tld' prefix='j' %>

<%
final Logger log = Logger.getLogger("js.extjs.jaffa.exportToExcel");

QueryServiceConfig master = new QueryServiceConfig();
QueryServiceConfig child = null;

master.setCriteriaClassName(StringHelper.escapeJavascript(request.getParameter("criteriaClassName")));
master.setCriteriaObject(StringHelper.escapeJavascript(request.getParameter("criteriaObject")));
master.setServiceClassName(StringHelper.escapeJavascript(request.getParameter("serviceClassName")));
master.setServiceClassMethodName(StringHelper.escapeJavascript(request.getParameter("serviceClassMethodName")));
master.setColumnModel(ExcelExportService.jsonArrayToBeanArray(StringHelper.escapeJavascript(request.getParameter("columnModel")), null));

String mkfns = StringHelper.escapeJavascript(request.getParameter("masterKeyFieldNames"));
if (mkfns != null) {
    child = new QueryServiceConfig();
    child.setMasterKeyFieldNames(ExcelExportService.jsonArrayToBeanArray(mkfns, null));
    child.setCriteriaClassName(StringHelper.escapeJavascript(request.getParameter("detailCriteriaClassName")));
    child.setCriteriaObject(StringHelper.escapeJavascript(request.getParameter("detailCriteria")));
    child.setServiceClassName(StringHelper.escapeJavascript(request.getParameter("detailServiceClassName")));
    child.setColumnModel(ExcelExportService.jsonArrayToBeanArray(StringHelper.escapeJavascript(request.getParameter("detailColumnModel")), null));
}
if(log.isDebugEnabled()) {
    log.debug("criteriaClassName = " + master.getCriteriaClassName());
    log.debug("serviceClassName = " + master.getServiceClassName());
    log.debug("criteriaObject = " + master.getCriteriaObject());
    log.debug("columnModel = " + master.getColumnModel().toString());
    if (child != null) {
        log.debug("masterKeyFieldNames = " + child.getMasterKeyFieldNames().toString());
        log.debug("detailCriteriaClassName = " + child.getCriteriaClassName());
        log.debug("detailServiceClassName = " + child.getServiceClassName());
        log.debug("detailCriteriaObject = " + child.getCriteriaObject());
        log.debug("detailColumnModel = " + child.getColumnModel().toString());
    }
}

Workbook wb;
String sheetName = StringHelper.escapeJavascript(request.getParameter("sheetName"));

String legacyExport = (String) ContextManagerFactory.instance().getProperty("jaffa.widgets.exportToExcel.legacy");
if (legacyExport!=null && legacyExport.equals("T")){
  if (sheetName==null){
    wb = ExcelExportService.generateExcel(master, child);
    sheetName = "exportToExcel.xls";
  }else {
    wb = ExcelExportService.generateExcel(master, child, sheetName);
    sheetName += ".xls";
  }
  response.setContentType("application/vnd.ms-excel");
}else{
  if (sheetName==null){
    wb = ExcelExportService.generateExcel(master, child);
    sheetName = "exportToExcel.xlsx";
  }else {
    wb = ExcelExportService.generateExcel(master, child, sheetName);
    sheetName += ".xlsx";
  }
  response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
}
response.setHeader("Content-Disposition", "attachment; filename=\""+sheetName+"\"");
ServletOutputStream os = response.getOutputStream();

wb.write(os);
out.clear();
out = pageContext.pushBody();

if (legacyExport!=null && !legacyExport.equals("T")){
  ((SXSSFWorkbook) wb).dispose();
}

%>
