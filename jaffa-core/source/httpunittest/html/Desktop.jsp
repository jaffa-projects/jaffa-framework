<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ page import="java.io.*,java.util.*,javax.mail.*,javax.mail.internet.*,javax.naming.*,org.jaffa.util.*,javax.servlet.*" %>
<tiles:insert definition="<%=request.getParameter("strutsTilesTemplate")%>" flush="true" />
