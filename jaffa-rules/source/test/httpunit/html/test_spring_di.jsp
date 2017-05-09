<%@page contentType="text/html"%>
<%@page import="org.jaffa.soa.domain.SOAEvent, org.jaffa.persistence.UOW" %>

<html>
<head>
    <title>Spring DI  Tests</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body bgcolor="#FFFFFF" leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
<%
	//String TEST_STRING = "12345678901234567890";
	String TEST_STRING = "123456789012345678901";
    SOAEvent event = new SOAEvent();
    event.setEventId("eventid");
    event.setEventName("EventName");
	event.setDescription(TEST_STRING);
    event.setModified(true);

    try {
        UOW uow = new UOW();
        uow.add(event);
    } catch (Exception e) {
        out.println("<p>");
        out.println(e.getMessage());
        out.println("</p>");
    }
    out.println("<p>");
    out.println("SOAEvent: " + event.getEventName() + " was added.");
    out.println("</p>");
%>
</body>
</html>