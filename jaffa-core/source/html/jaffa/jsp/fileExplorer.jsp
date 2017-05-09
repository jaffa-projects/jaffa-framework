<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page language="java" %>
<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="j" uri="/WEB-INF/jaffa-portlet.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%! private static final Logger log = Logger.getLogger("jaffa.jsp.fileExplorer"); %>

<h1>Folder : /<c:out value="${FileExplorerBean.folderName}"/></h1>
<%String base=(String)request.getAttribute("javax.servlet.forward.request_uri");%>
<table class="outline" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr class="subtitles">
    <td>Document</td>
    <td>Size</td>
    <td>Date</td>
    <td>Action</td>
  </tr>
  <c:set var="even" value="true"/>
    
  <c:if test="${FileExplorerBean.parentAvailable}">
    <tr class="<c:if test='${!even}'>alt</c:if>sort">
      <td class="sort"><a href="<%=base%>/..">/..</a></td>
      <td class="sort">&nbsp;</td>
      <td class="sort"><c:out value="${row.lastModified}"/></td>
      <td class="sort">
        <c:if test='${row.deleteAllowed}'>
        <a href='<c:out value="${row.name}"/>?action=delete'>Delete</a>
        </c:if>
      </td>
    </tr>
    <c:set var="even" value="${!even}"/>
  </c:if>

  
  
  <c:forEach var="row" begin="0" items="${FileExplorerBean.folders}">
    <tr class="<c:if test='${!even}'>alt</c:if>sort">
      <td class="sort">
      <a href="<c:out value='${base}/${row.name}'/>"/>\..</a>
      \
      </td>
      <td class="sort">&nbsp;</td>
      <td class="sort"><c:out value="${row.lastModified}"/></td>
      <td class="sort">
        <c:if test='${row.deleteAllowed}'>
        <a href='<c:out value="${row.name}"/>?action=delete'>Delete</a>
        </c:if>
      </td>
    </tr>
    <c:set var="even" value="${!even}"/>
  </c:forEach>

  <c:forEach var="row" begin="0" items="${FileExplorerBean.files}">
   <tr class="<c:if test='${!even}'>alt</c:if>sort">
      <td class="sort"><c:out value="${row.name}"/></td>
      <td class="sort"><c:out value="${row.formattedSize}"/></td>
      <td class="sort"><c:out value="${row.lastModified}"/></td>
      <td class="sort">
        <c:if test='${row.deleteAllowed}'>
        <a href='<c:out value="${row.name}"/>?action=delete'>Delete</a>
        </c:if>
      </td>
    </tr>
    <c:set var="even" value="${!even}"/>
  </c:forEach>
</table>

