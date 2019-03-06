<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*" %>
<%@ page import = "org.jaffa.config.Config" %>
<%@ page import = "org.jaffa.util.URLHelper" %>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import="org.jaffa.util.StringHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<%! private static final Logger log = Logger.getLogger("jaffa.jsp.log4j"); %>
<html>
<head>
    <TITLE><%= log.getName() %></TITLE>
    <Portlet:Header/>
    <LINK rel='STYLESHEET' type='text/css' href='jaffa/css/jaffa.css'>
</head>
<body>

<%  String fileName = (String) Config.getProperty(Config.PROP_LOG4J_CONFIG, "default");
    if ( fileName.equalsIgnoreCase("none") ) {%>
    <h2>Log4J was not configured through the JAFFA framework</h2>
    <p>Change the 'framework.Log4JConfig' property in framework.properties if you want to dynamically configure logging.</p>

<%  } else if ( fileName.equalsIgnoreCase("default") ) {%>
    <h2>Log4J was configured using the BasicConfigurator</h2>
    <p>Change the 'framework.Log4JConfig' property in framework.properties if you want to dynamically configure logging.</p>

<%  } else {
        String absoluteFileName = URLHelper.newExtendedURL(fileName).getPath();
        String fileContents = request.getParameter("fileContents");
        if (fileContents == null) {
            // read the contents of the file
            BufferedReader reader = new BufferedReader(new FileReader(absoluteFileName));
            StringBuffer buf = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
                buf.append("\r\n");
            }
            reader.close();
            fileContents = buf.toString();
        } else {
            // update the contents of the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(absoluteFileName));
            writer.write(fileContents);
            writer.flush();
            writer.close();
        }
 %>
    <FORM method='POST'>
    <TABLE>
    <TR><TD align='center'><h2><%= absoluteFileName %></h2></TD></TR>
    <TR><TD><TEXTAREA name="fileContents" rows='32' cols='120'><%= StringHelper.escapeHtml(fileContents) %></TEXTAREA></TD></TR>
    <TR><TD align='center'><INPUT type='submit' value='Update Configuration'/></TD></TR>
    </TABLE>
    </FORM>
<%  }%>
</body>
</html>
