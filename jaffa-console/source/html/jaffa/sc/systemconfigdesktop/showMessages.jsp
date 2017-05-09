<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Properties"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.jms.TextMessage"%>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ taglib uri="/WEB-INF/jaffa-portlet.tld" prefix="Portlet" %>
<Portlet:ComponentGuard name="Jaffa.SC.SystemConfigDesktop">
<html>
    <head>
        <title><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.ConsumeMessages", null)  %></title>
        <link href="../css/main1.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="utils.js"></script>
    </head>
    <body>
        <form name="showForm" action="showMessages.jsp" method="post">
            <input type="hidden" name="subscriptionName"  value=""/>
            <input type="hidden" name="numberOfMsgs"  value=""/>
            <input type="hidden" name="clientID"  value=""/>
            <input type="hidden" name="userName"  value=""/>
            <input type="hidden" name="password"  value=""/>
            <input type="hidden" name="topicName"  value=""/>
            <table class="outer">
                <tr>
                    <td align='center'><h2><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.Messages", null)  %></h2></td>
                </tr>
                <tr>
                    <td>
                        <div class="panel">
                            <h2><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.DurableSubscriberMessages", null)  %></h2>
                            <div class="panelcontent">
                                <table class="inner">
                                    <thead class="header"><th><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.SNo", null)  %></th><th><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.MessageID", null)  %></th><th><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.Destination", null)  %></th><th><%= MessageHelper.findMessage("label.Jaffa.SC.SystemConfigDesktop.Message", null)  %></th></thead>
                                    <% int i = 1; %>
                                    <c:forEach items="${sessionScope.consumedList}" var="message">
                                    <tr class="<%= (i % 2 == 0 ? "odd" : "even")%>">
                                        <td><%= (i++) %></td>
                                        <td>${message.JMSMessageID}</td>
                                        <td>${message.JMSDestination}</td>
                                        <td><textarea rows="5" cols="80">${message.text}</textarea></td>
                                    </tr>
                                    </c:forEach>
                                    <% session.removeAttribute("consumedList"); %>
                                </table>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
</Portlet:ComponentGuard>