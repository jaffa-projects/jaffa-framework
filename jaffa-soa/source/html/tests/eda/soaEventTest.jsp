<%--
This test will create the specified number of events using the specified number of producer threads.
Each event will have its own UOW.
--%>
<%@page import="org.jaffa.presentation.portlet.session.UserSession,org.jaffa.soa.services.SOAEventEnabledConfiguration,org.jaffa.transaction.tester.SoaEventTest,java.io.IOException" %>
<%@ page import="org.jaffa.soa.services.SOAEventEnabledConfigurationFactory" %>
<%!


    private void showResults(Long numberOfEvents, Long duration, JspWriter out) throws Exception {
        out.write("No of Events Created " + numberOfEvents + "<br>");
        out.write("Total Duration (sec)  " + duration);
    }


    private void execute(HttpServletRequest request, JspWriter out) throws Exception {
        Long noOfEvents = Long.parseLong(request.getParameter("numberOfEvents"));
        long startTime = System.currentTimeMillis();
        int numOfThreads = Integer.parseInt(request.getParameter("numberOfThreads"));
        SoaEventTest test = new SoaEventTest();
        test.testSoaEvent(UserSession.getUserSession(request).getUserId(), request.getParameter("name"), request.getParameter("value"), request.getParameter("eventName"), request.getParameter("eventDescription"), noOfEvents, numOfThreads);
        long endTime = System.currentTimeMillis();
        Long duration = (endTime - startTime) / 1000;
        showResults(noOfEvents, duration, out);
    }

    /**
     * Prints the names of all the disabled events
     * @param out JspWriter to print the event names to
     * @throws IOException Exception caused by writing to the JspWriter
     */
    private void printEventNames(JspWriter out) throws IOException {
        boolean first = true;
        for (String eventName : SOAEventEnabledConfigurationFactory.instance().getEventsInNonDefaultState()) {
            if (first) {
                first = false;
            } else {
                out.write(", ");
            }
            out.write(eventName);
        }
    }

%>
<html>
<head>
    <title>SoaEvent Queue Test</title>
    <style>
        body {
            color: black;
            font-size: 10pt;
            font-family: Tahoma;
        }

        .rulename {
            font-weight: bold;
            color: blue;
        }

        .fieldname {
            font-weight: bold;
            color: green;
        }

        table {
            font-size: 8pt;
            color: black;
            font-family: Tahoma;
        }

        thead td {
            font-size: 8pt;
            color: black;
            background: #CBE0F5;
            padding: 3px;
            text-align: left;
            border-right: 1px groove #B1CAEA;
        }

        tr {
            font-size: 8pt;
            color: black;
            background: #f1f1f1;
            padding: 3px;
        }

        tr.row0 {
            background: #f1f1f1;
        }

        tr.row1 {
            background: #e2e2e2;
        }

        td {
            font-size: 8pt;
            border-bottom: 1px solid #C8C8C8;
            border-left: 1px solid #C8C8C8;
            padding: 2px;
            color: black;
        }

        .true {
            font-weight: bold;
            color: red;
        }

        .false {
            color: gray;
        }
    </style>
</head>
<body>
<form action="soaEventTest.jsp" method="post">
    <h2>SoaEvent Queue Test</h2>
    <%
        Long numberOfEvents = (request != null && request.getParameter("numberOfEvents") != null ? Long.parseLong(request.getParameter("numberOfEvents")) : 0);
    %>
    <table>
        <thead>
        <tr class='row0'>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tr>
            <td>Soa Event Name:</td>
            <td><input type="text" name="eventName" size="5"></td>
            <td></td>
        </tr>
        <tr>
            <td>Soa Event Description:</td>
            <td><input type="text" name="eventDescription" size="5"></td>
            <td></td>
        </tr>
        <tr>
            <td>Soa Event Parameters (name/value):</td>
            <td><input type="text" name="name" size="5"></td>
            <td><input type="text" name="value" size="5"></td>
        </tr>
        <tr>
            <td>No of Soa Events:</td>
            <td><input type="text" name="numberOfEvents" value="0" size="5"></td>
            <td></td>
        </tr>
        <tr>
            <td>No of Concurrent Threads:</td>
            <td><input type="text" name="numberOfThreads" value="0" size="10"></td>
            <td></td>
        </tr>

        <tr>
            <td colspan="4" align="right"><input type="submit" value="Go"></td>
    </table>
</form>
<%
    if ((numberOfEvents != null && numberOfEvents > 0)) {
        execute(request, out);
    }

    // If there is are submitType and eventName parameters, update the event accordingly
    String submitType = request.getParameter("submitType");
    String eventName = request.getParameter("eventName");
    if (submitType != null && !submitType.isEmpty() && eventName != null && !eventName.isEmpty()) {
        boolean isEnable = submitType.equalsIgnoreCase("enable");
        SOAEventEnabledConfigurationFactory.instance().setEnabled(eventName, isEnable);
    }
%>

<form action="soaEventTest.jsp" method="post">
    <h2>Disabled SOA Events</h2>

    <h3>List of events that are <%= SOAEventEnabledConfigurationFactory.instance().areEventsEnabledByDefault() ? "disabled" : "enabled" %>:</h3>
    <i>
        <% printEventNames(out); %>
    </i>
    <br/><br/>
    <table>
        <thead>
        <tr class='row0'>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tr>
            <td>Soa Event Name:</td>
            <td><input type="text" name="eventName" size="5"></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="submit" name="submitType" value="Disable">
                <input type="submit" name="submitType" value="Enable">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
