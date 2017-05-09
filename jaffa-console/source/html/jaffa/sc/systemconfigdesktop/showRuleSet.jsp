<%@page import="java.io.*,javax.servlet.*,javax.servlet.http.*" %>  

<%  
    String fileName=request.getParameter("path");  
     
    BufferedReader br = null;
    try {

            StringBuffer sb = new StringBuffer("<pre>");
            String sCurrentLine;

            br = new BufferedReader(new FileReader(fileName));

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