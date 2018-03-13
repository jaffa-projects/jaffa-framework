<%@ page language="java" contentType="application/vnd.ms-excel"%>
<%@ page import = "java.io.*" %>
<%@ page import = "net.sf.json.JSONObject" %>
<%@ page import = "net.sf.json.JSONArray" %>
<%@ page import = "net.sf.json.JSONSerializer" %>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import = "org.apache.poi.ss.usermodel.CellStyle" %>
<%@ page import = "org.apache.poi.ss.usermodel.IndexedColors" %>
<%@ page import = "org.apache.poi.ss.usermodel.CreationHelper" %>
<%@ page import = "org.apache.poi.ss.usermodel.Cell" %>
<%@ page import = "org.apache.poi.ss.usermodel.Row" %>
<%@ page import = "org.apache.poi.ss.usermodel.Sheet" %>
<%@ page import = "org.apache.poi.ss.usermodel.Workbook" %>
<%@ page import = "org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@ page import = "org.apache.poi.xssf.streaming.SXSSFWorkbook" %>
<%@ page import = "org.apache.poi.ss.usermodel.Font" %>
<%@ page import = "org.apache.poi.ss.util.CellRangeAddress" %>
<%@ page import = "org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.util.StringHelper" %>

<%!
    private static final Logger log = Logger.getLogger("jaffa.grid.simpleExportToExcel");
    private int rowCount = 1;
    private Workbook generateExcelFromGrid(String jsonStr,String sheetName) throws Exception {

        Workbook wb = null;
        String legacyExport = (String) ContextManagerFactory.instance().getProperty("jaffa.widgets.exportToExcel.legacy");
        if (legacyExport!=null && legacyExport.equals("T")){
          wb = new HSSFWorkbook();
        } else {
          wb = new SXSSFWorkbook(100);
        }
        try {

            CreationHelper createHelper = wb.getCreationHelper();

            //Creating worksheet
            Sheet sheet = wb.createSheet(sheetName);

            //creating a custom palette for the workbook
            CellStyle style = wb.createCellStyle();
            style = wb.createCellStyle();

            //setting the forground color to gray
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            //Setting the border for the cells
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setAlignment(CellStyle.ALIGN_CENTER);

            //setting font weight
            Font titleFont = wb.createFont();
            titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(titleFont);

            JSONObject gridJson = (JSONObject) JSONSerializer.toJSON(jsonStr);
            int headerRowCount = 0;

            // Creating the group header rows.
            JSONArray headerArray = new JSONArray();
            if (gridJson.optJSONArray("headers") != null)
                headerArray = gridJson.optJSONArray("headers");

            for (int i = 0; i < headerArray.size(); i++) {
                Row row = sheet.createRow((short) headerRowCount);
                headerRowCount = headerRowCount + 1;
                JSONArray headerRecord = (JSONArray) headerArray.get(i);
                int colIndex = 0;
                for (int j = 0; j < headerRecord.size(); j++) {
                    JSONObject headerData = (JSONObject) headerRecord.get(j);
                    int span = 1;
                    if (headerData.getString("colspan")!=null && Integer.parseInt(headerData.getString("colspan"))>1){
                        span = Integer.parseInt(headerData.getString("colspan"));
                    }
                    row.createCell(colIndex).setCellValue(headerData.getString("header"));
                    Cell cell = row.getCell(colIndex);
                    cell.setCellStyle(style);
                    sheet.autoSizeColumn(colIndex);
                    if (span>1){
                        //merge cells in group header together
                        sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(),cell.getRowIndex(),cell.getColumnIndex(),(cell.getColumnIndex() + span -1)));
                        //apply border to all cells in merged header
                        for (int k = 1; k < span; k++){
                            row.createCell(colIndex+k);
                            row.getCell(colIndex+k).setCellStyle(style);
                        }
                    }
                    colIndex = colIndex + span;
                }
            }

            // Creating the header row.
            Row row = sheet.createRow((short) headerRowCount);
            headerRowCount = headerRowCount + 1;

            JSONArray columns = gridJson.getJSONArray("columns");

            for (int i = 0; i < columns.size(); i++) {
                JSONObject columnData = (JSONObject) columns.get(i);
                row.createCell(i).setCellValue(columnData.getString("header"));
                row.getCell(i).setCellStyle(style);
                sheet.autoSizeColumn(i);
            }

            JSONArray records = gridJson.getJSONArray("records");

            int rowCount = headerRowCount;

            for (int j = 0; j < records.size(); j++) {
              row = sheet.createRow((short) rowCount);
              JSONObject recordData = (JSONObject) records.get(j);
              for (int k = 0; k < columns.size(); k++) {
                JSONObject columnData = (JSONObject) columns.get(k);
                String dataIndex = columnData.getString("dataIndex");
                String fieldValue = recordData.getString(dataIndex);

                row.createCell(k).setCellValue(fieldValue);
              }
              rowCount++;
            }

            // Freeze header rows
            sheet.createFreezePane(0, headerRowCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

%>
<%
    String jsonStr = request.getParameter("jsonToExport") != null ? StringHelper.escapeJavascript(request.getParameter("jsonToExport")) : "{}";
    String sheetName = request.getParameter("sheetName") != null ? StringHelper.escapeJavascript(request.getParameter("sheetName")) : "Grid Export";
    String excelName = request.getParameter("excelName") != null ? StringHelper.escapeJavascript(request.getParameter("excelName")) : "GridExport";
    rowCount = 1;

    Workbook wb;
    wb = generateExcelFromGrid(jsonStr, sheetName);

    String legacyExport = (String) ContextManagerFactory.instance().getProperty("jaffa.widgets.exportToExcel.legacy");
    if (legacyExport!=null && legacyExport.equals("T")){
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition","attachment;filename="+excelName+".xls");
    }else{
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setHeader("Content-Disposition","attachment;filename="+excelName+".xlsx");
    }
    ServletOutputStream os = response.getOutputStream();

    wb.write(os);
    out.clear();
    out = pageContext.pushBody();

    if (legacyExport!=null && !legacyExport.equals("T")){
      ((SXSSFWorkbook) wb).dispose();
    }
%>