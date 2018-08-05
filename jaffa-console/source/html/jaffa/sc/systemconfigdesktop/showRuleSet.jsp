<%@page import="java.io.*,javax.servlet.*,javax.servlet.http.*" %>
<%@ page import="java.util.Set" %>
<%@ page import="org.jaffa.loader.drools.DroolsManager" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<%!
    private static Map<String, File> cachedDroolsMap = new HashMap<>();
    private void findDrools(File file, Map<String, File> droolsMap) {
        if(file!=null) {
            File[] files = file.listFiles();
            for (File droolFile : files) {
                if (droolFile.isFile()) {
                    droolsMap.put(droolFile.getName(), droolFile);
                } else {
                    findDrools(droolFile, droolsMap);
                }
            }
        }
    }
%>
<%  
    String fileName=request.getParameter("path");
    Map<String, File> droolsMap = cachedDroolsMap;
    if(droolsMap == null || droolsMap.size() == 0) {
        findDrools(new File(DroolsManager.DROOLS_FILE_DIRECTORY), droolsMap);
        cachedDroolsMap = droolsMap;
    }
    BufferedReader br = null;
    try {

            StringBuffer sb = new StringBuffer("<pre>");
            String sCurrentLine;

            br = new BufferedReader(new FileReader(droolsMap.get(fileName)));

            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
                sb.append("<br>");
            }
            sb.append("</pre>");
            
            out.print(sb.toString());

    } catch (IOException e) {
            e.printStackTrace();
    } finally {
          try {
                if (br != null)br.close();
          } catch (IOException ex) {
                ex.printStackTrace();
          }
    }     
%>  