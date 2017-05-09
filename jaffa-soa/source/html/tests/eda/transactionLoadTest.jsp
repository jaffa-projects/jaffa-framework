<%--
This page combines four jsp tests in one place. All four tests will run at a specified interval for a specified
duration.
The first is the event tester that will create one UOW per event and will fire a specified number of events by a
specified number of producer threads.
The second is the part tester that will create parts.  This is done in a single thread.  Each part will be in
its own UOW.  Each part addition will fire 2 events, so creating 10 parts will fire 20 events.
The third test is the batch transaction test.  This will add all transactions to the same UOW.
Finally, we have the multi-threaded transaction test.  Each transaction will have its own UOW and the transactions
will be produced by the specified number of producer threads.
--%>
<%@page import="com.mirotechnologies.catalogvendor.core.domain.Part,org.jaffa.modules.user.services.UserContextWrapper" %>
<%@ page import="org.jaffa.modules.user.services.UserContextWrapperFactory" %>
<%@ page import="org.jaffa.persistence.UOW" %>
<%@ page import="org.jaffa.presentation.portlet.session.UserSession" %>
<%@ page import="org.jaffa.transaction.tester.SoaEventTest" %>
<%@ page import="org.jaffa.transaction.tester.TestMessage" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Timer" %>
<%@ page import="java.util.TimerTask" %>
<%@ page import="java.util.concurrent.ExecutorService" %>
<%@ page import="java.util.concurrent.Executors" %>
<%!

    // used to give unique names to parts in all threads
    private static int uniqueId = 0;

    /**
     * Prints the total number of each type of transaction to the GUI
     *
     * @param numberOfEvents the total number of events being sent
     * @param numberOfBatchTransactions the total number of batch transactions being sent
     * @param numberOfParts the total number of parts being sent
     * @param numberOfSingleTransactions the total number of single transactions being sent
     * @param out used to write results to the screen
     * @throws Exception
     */
    private void showResults(int numberOfEvents, int numberOfBatchTransactions, int numberOfParts, int numberOfSingleTransactions, JspWriter out) throws Exception {
        out.write("No of Events Created " + numberOfEvents + "<br>");
        out.write("No of Parts Created " + numberOfParts + "<br>");
        out.write("No of Batch Transactions Created " + numberOfBatchTransactions + "<br>");
        out.write("No of Single Transactions Created " + numberOfSingleTransactions + "<br>");
    }

    /**
     * Executes the test.  This will start timers for all tests being run.  The tests will repeat
     * at the specified interval until the total duration is reached.
     *
     * @param request contains user input for the tests
     * @param out used to write results to the screen
     * @throws Exception
     */
    private void execute(HttpServletRequest request, JspWriter out) throws Exception {
        System.out.println("Starting load test ********************************");

        // get the duration in ms to continue the test for
        final int duration = Integer.parseInt(request.getParameter("duration"));
        final String userId = UserSession.getUserSession(request).getUserId();

        // execute the SOA events
        int numberOfEvents = 0;
        if (isSoaEventSetup(request)) {
            numberOfEvents = executeSoaEvents(request, duration, userId);
        }

        // execute the batch transaction test
        int numberOfBatchTransactions = 0;
        if (isBatchTransactionSetup(request)) {
            numberOfBatchTransactions = executeBatchTransactions(request, duration, userId);
        }

        // execute the part test
        int numberOfParts = 0;
        if (isPartSetup(request)) {
            numberOfParts = executeParts(request, duration, userId);
        }

        // execute the single transaction test
        int numberOfSingleTransactions = 0;
        if (isSingleTransactionSetup(request)) {
            numberOfSingleTransactions = executeSingleTransactions(request, duration, userId);
        }

        // show the results of all of the tests
        showResults(numberOfEvents, numberOfBatchTransactions, numberOfParts, numberOfSingleTransactions, out);
    }

    /**
     * Checks if the SOA event test fields have been filled out
     *
     * @param request contains the user input
     * @return true if the necessary fields are filled out, false otherwise
     */
    private boolean isSoaEventSetup(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        if (request.getParameter("numberOfEvents") == null || request.getParameter("numberOfEvents").isEmpty()
                || Integer.parseInt(request.getParameter("numberOfEvents")) == 0) {
            return false;
        }
        if (request.getParameter("eventInterval") == null || request.getParameter("eventInterval").isEmpty()
                || Integer.parseInt(request.getParameter("eventInterval")) == 0) {
            return false;
        }
        if (request.getParameter("numberOfThreads") == null || request.getParameter("numberOfThreads").isEmpty()
                || Integer.parseInt(request.getParameter("numberOfThreads")) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the Batch Transaction test fields have been filled out
     *
     * @param request contains the user input
     * @return true if the necessary fields are filled out, false otherwise
     */
    private boolean isBatchTransactionSetup(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        if (request.getParameter("transactionsPerBatch") == null || request.getParameter("transactionsPerBatch").isEmpty()
                || Integer.parseInt(request.getParameter("transactionsPerBatch")) == 0) {
            return false;
        }
        if (request.getParameter("batchInterval") == null || request.getParameter("batchInterval").isEmpty()
                || Integer.parseInt(request.getParameter("batchInterval")) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the Create Part test fields have been filled out
     *
     * @param request contains the user input
     * @return true if the necessary fields are filled out, false otherwise
     */
    private boolean isPartSetup(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        if (request.getParameter("partName") == null || request.getParameter("partName").isEmpty()) {
            return false;
        }
        if (request.getParameter("partCage") == null || request.getParameter("partCage").isEmpty()) {
            return false;
        }
        if (request.getParameter("numberOfParts") == null || request.getParameter("numberOfParts").isEmpty()
                || Integer.parseInt(request.getParameter("numberOfParts")) == 0) {
            return false;
        }
        if (request.getParameter("partInterval") == null || request.getParameter("partInterval").isEmpty()
                || Integer.parseInt(request.getParameter("partInterval")) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the Single Transaction test fields have been filled out
     *
     * @param request contains the user input
     * @return true if the necessary fields are filled out, false otherwise
     */
    private boolean isSingleTransactionSetup(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        if (request.getParameter("numberOfTransactions") == null || request.getParameter("numberOfTransactions").isEmpty()
                || Integer.parseInt(request.getParameter("numberOfTransactions")) == 0) {
            return false;
        }
        if (request.getParameter("singleInterval") == null || request.getParameter("singleInterval").isEmpty()
                || Integer.parseInt(request.getParameter("singleInterval")) == 0) {
            return false;
        }
        if (request.getParameter("transactionThreads") == null || request.getParameter("transactionThreads").isEmpty()
                || Integer.parseInt(request.getParameter("transactionThreads")) == 0) {
            return false;
        }
        return true;
    }

    /**
     * Schedule the SOA event test to take place.  This will make one UOW per event and execute them in
     * the specified number of threads.  Returns the total number of events created.
     *
     * @param request contains user input for the test
     * @param duration the total duration in ms to do the test for
     * @param userId the user ID to set on the UOW
     * @return the total number of events fired
     */
    private int executeSoaEvents(HttpServletRequest request, int duration, final String userId) {

        // the events per interval and the interval in ms
        final int eventsPerInterval = Integer.parseInt(request.getParameter("numberOfEvents"));
        int interval = Integer.parseInt(request.getParameter("eventInterval"));

        // the number of threads to use for each invocation
        final int numOfThreads = Integer.parseInt(request.getParameter("numberOfThreads"));

        // fields to set on the event
        final String name = request.getParameter("name");
        final String value = request.getParameter("value");
        final String eventName = request.getParameter("eventName");
        final String eventDescription = request.getParameter("eventDescription");

        // fire the test at the specified interval for the specified time period
        if (interval == 0) {
            interval = 1;
        }
        final int numberOfExecutions = duration / interval;
        final Timer timer = new Timer();
        for (int i = 0; i < numberOfExecutions; i++) {
            timer.schedule(new TimerTask() {
                public void run() {
                    SoaEventTest test = new SoaEventTest();
                    test.testSoaEvent(userId, name, value, eventName, eventDescription, eventsPerInterval, numOfThreads);
                }
            }, (i * interval));
        }

        return (numberOfExecutions * eventsPerInterval);
    }

    /**
     * Creates 1 UOW per part and submits the specified number of parts at each interval.
     * The part will ad a transaction to the UOW, so each UOW will persist 1 part and process 1 transaction.
     * This is done in a single thread.
     *
     * @param request contains user input for the test
     * @param duration the total duration in ms to do the test for
     * @param userId the user ID to set on the UOW
     * @return the total number of events fired
     */
    private int executeParts(HttpServletRequest request, int duration, final String userId) {

        // get the part fields
        final String part = (String) request.getParameter("partName");
        final String cage = (String) request.getParameter("partCage");
        final int numberOfParts = Integer.parseInt(request.getParameter("numberOfParts"));
        final String delete = (String) request.getParameter("deleteParts");
        int interval = Integer.parseInt(request.getParameter("partInterval"));

        // fire the test at the specified interval for the specified time period
        if (interval == 0) {
            interval = 1;
        }
        final int numberOfExecutions = duration / interval;
        final Timer timer = new Timer();
        for (int i = 0; i < numberOfExecutions; i++) {
            timer.schedule(new TimerTask() {
                public void run() {
                    UOW uow = null;
                    UserContextWrapper ucw = null;
                    try {
                        List<String> idList = new ArrayList<String>();

                        synchronized (this) {
                            ucw = UserContextWrapperFactory.instance(userId);
                        }

                        for (int j = 0; j < numberOfParts; j++) {

                            uow = new UOW();
                            Part partDom = new Part();
                            String id = part + uniqueId++;
                            idList.add(id);
                            partDom.setPart(id);
                            partDom.setCage(cage);
                            partDom.setDescription("TEST" + j);
                            partDom.setStockNumber("NSL");
                            partDom.setRecordChanged1(Boolean.FALSE);
                            partDom.setRecordChanged2(Boolean.FALSE);
                            partDom.setRecordChanged3(Boolean.FALSE);
                            partDom.setRecordChanged4(Boolean.FALSE);
                            partDom.setRecordChanged5(Boolean.FALSE);
                            partDom.setRecordChanged6(Boolean.FALSE);
                            partDom.setRecordChanged7(Boolean.FALSE);
                            partDom.setRecordChanged8(Boolean.FALSE);
                            partDom.setUmIssueCode("EA");
                            partDom.setUmTurnCode("EA");
                            uow.add(partDom);
                            uow.commit();
                        }

                        if (delete != null && "on".equals(delete)) {
                            uow = new UOW();
                            for (String id : idList) {
                                Part partDom = Part.findByPK(uow, id, cage);
                                uow.delete(partDom);
                            }
                            uow.commit();
                        }
                        idList.clear();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        e.printStackTrace();
                    } finally {
                        synchronized (this) {
                            if (ucw != null) {
                                ucw.unsetContext();
                            }
                        }
                        if (uow != null) {
                            try {
                                uow.rollback();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }, (i * interval));
        }
        return (numberOfExecutions * numberOfParts);
    }

    /**
     * Executes the batch transaction test.  This will put all of the transactions per batch into
     * a single UOW and commit that UOW after all of the transaction have been added.
     *
     * @param request contains user input for the test
     * @param duration the total duration in ms to do the test for
     * @param userId the user ID to set on the UOW
     * @return the total number of transactions sent
     */
    private int executeBatchTransactions(HttpServletRequest request, int duration, final String userId) {

        // the transaction per batch and the interval in ms
        final int transactionPerBatch = Integer.parseInt(request.getParameter("transactionsPerBatch"));
        int interval = Integer.parseInt(request.getParameter("batchInterval"));

        // fields to set on the transaction
        final String sendEmail = request.getParameter("batchSendEmail");
        final String emailAddress = request.getParameter("batchEmailAddress");
        final String fail = request.getParameter("batchFail");
        final int pause = Integer.parseInt(request.getParameter("batchPause"));
        final int dbQuery = Integer.parseInt(request.getParameter("batchQuery"));

        // fire the test at the specified interval for the specified time period
        if (interval == 0) {
            interval = 1;
        }
        final int numberOfExecutions = duration / interval;
        final Timer timer = new Timer();
        for (int i = 0; i < numberOfExecutions; i++) {
            timer.schedule(new TimerTask() {
                public void run() {
                    UOW uow = null;
                    UserContextWrapper ucw = null;
                    try {
                        synchronized (this) {
                            ucw = UserContextWrapperFactory.instance(userId);
                        }
                        uow = new UOW();
                        for (int p = 0; p < transactionPerBatch; p++) {
                            TestMessage tm = new TestMessage();
                            if ("on".equals(sendEmail)) {
                                tm.setEmailAddress(emailAddress);
                            }
                            tm.setFail("on".equals(fail));
                            tm.setDelay(pause);
                            tm.setDbQuery(dbQuery);
                            uow.addMessage(tm);
                        }
                        uow.commit();
                    } catch (Exception e) {
                    } finally {
                        synchronized (this) {
                            if (ucw != null) {
                                ucw.unsetContext();
                            }
                        }
                        if (uow != null) {
                            try {
                                uow.rollback();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }, (i * interval));
        }

        return (numberOfExecutions * transactionPerBatch);
    }

    /**
     * Executes the single transaction test.  This create one UOW per transaction and send them separately.
     *
     * @param request contains user input for the test
     * @param duration the total duration in ms to do the test for
     * @param userId the user ID to set on the UOW
     * @return the total number of transactions sent
     */
    private int executeSingleTransactions(HttpServletRequest request, int duration, final String userId) {

        // the transaction per batch and the interval in ms
        final int transactionPerBatch = Integer.parseInt(request.getParameter("numberOfTransactions"));
        int interval = Integer.parseInt(request.getParameter("singleInterval"));

        // fields to set on the transaction
        final String sendEmail = request.getParameter("singleSendEmail");
        final String emailAddress = request.getParameter("singleEmailAddress");
        final String fail = request.getParameter("singleFail");
        final Long pause = Long.parseLong(request.getParameter("singlePause"));
        final Long dbQuery = Long.parseLong(request.getParameter("singleQuery"));
        final Integer poolSize = Integer.parseInt(request.getParameter("transactionThreads"));

        // fire the test at the specified interval for the specified time period
        if (interval == 0) {
            interval = 1;
        }
        final int numberOfExecutions = duration / interval;
        final Timer timer = new Timer();
        for (int i = 0; i < numberOfExecutions; i++) {
            timer.schedule(new TimerTask() {
                public void run() {

                    // submit one UOW per transaction, use the specified number of threads for this
                    ExecutorService executor = Executors.newFixedThreadPool(poolSize);
                    for (int i = 0; i < transactionPerBatch; i++) {
                        executor.execute(new Runnable() {
                            public void run() {

                                UOW uow = null;
                                UserContextWrapper ucw = null;
                                try {
                                    synchronized (this) {
                                        ucw = UserContextWrapperFactory.instance(userId);
                                    }
                                    uow = new UOW();
                                    TestMessage tm = new TestMessage();
                                    if ("on".equals(sendEmail)) {
                                        tm.setEmailAddress(emailAddress);
                                    }
                                    tm.setFail("on".equals(fail));
                                    tm.setDelay(pause);
                                    tm.setDbQuery(dbQuery);
                                    uow.addMessage(tm);
                                    uow.commit();
                                } catch (Exception e) {
                                } finally {
                                    synchronized (this) {
                                        if (ucw != null) {
                                            ucw.unsetContext();
                                        }
                                    }
                                    if (uow != null) {
                                        try {
                                            uow.rollback();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                                System.out.println("Test done");
                            }
                        });
                    }
                    executor.shutdown();
                }
            }, (i * interval));
        }

        return (numberOfExecutions * transactionPerBatch);
    }

%>
<html>
<head>
    <title>Transaction Load Test</title>
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

        td.alignTop {
            vertical-align: top;
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
<form action="transactionLoadTest.jsp" method="post">
    <h2>Transaction Load Test</h2>
    <%

        Long numberOfEvents = ((request != null && request.getParameter("numberOfEvents") != null && !request.getParameter("numberOfEvents").isEmpty()) ? Long.parseLong(request.getParameter("numberOfEvents")) : 0);
        Long numberOfParts = ((request != null && request.getParameter("numberOfParts") != null && !request.getParameter("numberOfParts").isEmpty()) ? Long.parseLong(request.getParameter("numberOfParts")) : 0);
        Long numberOfBatchTransactions = ((request != null && request.getParameter("transactionsPerBatch") != null && !request.getParameter("transactionsPerBatch").isEmpty()) ? Long.parseLong(request.getParameter("transactionsPerBatch")) : 0);
        Long numberOfSingleTransactions = ((request != null && request.getParameter("numberOfTransactions") != null && !request.getParameter("numberOfTransactions").isEmpty()) ? Long.parseLong(request.getParameter("numberOfTransactions")) : 0);
        Long duration = ((request != null && request.getParameter("duration") != null && !request.getParameter("duration").isEmpty()) ? Long.parseLong(request.getParameter("duration")) : 0);
    %>

    <%-- the overall table to hold all four test tables --%>
    <table>

        <%-- the overall table header --%>
        <thead>
        <tr class='row0'>
            <td>SOA Events</td>
            <td>Create Parts</td>
            <td>Batch Transactions</td>
            <td>Single Transactions</td>
        </tr>
        </thead>

        <%-- description of each test --%>
        <tr class='row0'>
            <td>Creates 1 UOW per event and processes them concurrently on the specified number of threads.</td>
            <td>Creates 1 UOW per part. Persists the part and processes 1 transaction per UOW. This is done on one
                thread.
            </td>
            <td>Creates 1 UOW and adds the specified number of transactions to it. This is done on one thread.</td>
            <td>Creates 1 UOW per transaction and processes them concurrently on the specified number of threads.</td>
        </tr>

        <tr>

            <%-- the SOA event table --%>
            <td class='alignTop'>
                <table>

                    <%-- the SOA event table --%>
                    <tr>
                        <td>Soa Event Name:</td>
                        <td><input type="text" id="eventName" name="eventName" size="5"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Soa Event Description:</td>
                        <td><input type="text" id="eventDescription" name="eventDescription" size="5"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Soa Event Parameters (name/value):</td>
                        <td><input type="text" id="name" name="name" size="5"/></td>
                        <td><input type="text" id="value" name="value" size="5"/></td>
                    </tr>
                    <tr>
                        <td>No of Soa Events:</td>
                        <td><input type="text" id="numberOfEvents" name="numberOfEvents" value="0" size="5"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>No of Concurrent Threads:</td>
                        <td><input type="text" id="numberOfThreads" name="numberOfThreads" value="0" size="10"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Interval (ms):</td>
                        <td><input type="text" id="eventInterval" name="eventInterval" value="0" size="10"/></td>
                        <td></td>
                    </tr>
                </table>
            </td>

            <%-- the part test table --%>
            <td class='alignTop'>
                <table>
                    <tr>
                        <td>Part:</td>
                        <td><input type="text" id="partName" name="partName" size="5"/></td>
                    </tr>
                    <tr>
                        <td>CAGE:</td>
                        <td><input type="text" id="partCage" name="partCage" size="5"/></td>
                    </tr>
                    <tr>
                        <td>No of Parts:</td>
                        <td><input type="text" id="numberOfParts" name="numberOfParts" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>Delete after Create:</td>
                        <td><input type="checkbox" id="deleteParts" name="deleteParts"/></td>
                    </tr>
                    <tr>
                        <td>Interval (ms):</td>
                        <td><input type="text" id="partInterval" name="partInterval" value="0" size="10"/></td>
                    </tr>
                </table>
            </td>

            <%-- the batch transaction table --%>
            <td class='alignTop'>
                <table>
                    <tr>
                        <td>Quantity:</td>
                        <td><input type="text" name="transactionsPerBatch" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="checkbox" name="batchSendEmail"/></td>
                    </tr>
                    <tr>
                        <td>Fail:</td>
                        <td><input type="checkbox" name="batchFail"/></td>
                    </tr>
                    <tr>
                        <td>Pause (ms):</td>
                        <td><input type="text" name="batchPause" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>DBQuery:</td>
                        <td><input type="text" name="batchQuery" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>Email Address:</td>
                        <td><input type="text" name="batchEmailAddress" size="40"/></td>
                    </tr>
                    <tr>
                        <td>Interval (ms):</td>
                        <td><input type="text" name="batchInterval" value="0" size="10"/></td>
                    </tr>
                </table>
            </td>

            <%-- the single transaction table --%>
            <td class='alignTop'>
                <table>
                    <tr>
                        <td>Quantity:</td>
                        <td><input type="text" id="numberOfTransactions" name="numberOfTransactions" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="checkbox" name="singleSendEmail"/></td>
                    </tr>
                    <tr>
                        <td>Fail:</td>
                        <td><input type="checkbox" name="singleFail"/></td>
                    </tr>
                    <tr>
                        <td>Pause (ms):</td>
                        <td><input type="text" name="singlePause" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>DBQuery:</td>
                        <td><input type="text" name="singleQuery" value="0" size="5"/></td>
                    </tr>
                    <tr>
                        <td>No of Concurrent Threads:</td>
                        <td><input type="text" id="transactionThreads" name="transactionThreads" value="0" size="10"/></td>
                    </tr>
                    <tr>
                        <td>Email Address:</td>
                        <td><input type="text" name="singleEmailAddress" size="40"/></td>
                    </tr>
                    <tr>
                        <td>Interval (ms):</td>
                        <td><input type="text" id="singleInterval" name="singleInterval" value="0" size="10"/></td>
                    </tr>
                </table>
            </td>

        </tr>

        <%-- the total duration in ms --%>
        <tr>
            <td></td>
            <td></td>
            <td>Total duration (ms):</td>
            <td><input type="text" id="duration" name="duration" size="10"/></td>
        </tr>

        <%-- the preload buttons --%>
        <tr>
            <td colspan="4" align="right"><input type="submit" name="submitType" value="Load 4 Hour Test"/></td>
        </tr>
        <tr>
            <td colspan="4" align="right"><input type="submit" name="submitType" value="Load 2 Hour Test"/></td>
        </tr>

        <%-- the submit button --%>
        <tr>
            <td colspan="4" align="right"><input type="submit" name="submitType" value="Go"/></td>
        </tr>
    </table>

</form>
<%
    // check which button was used to cause this submit
    String submitType = request.getParameter("submitType");

    // neither button caused the screen load, do nothing
    if (submitType == null) {
        return;
    }

    // if the load 4 hour button was clicked, populate the jsp with values
    if (submitType.equals("Load 4 Hour Test")) {
%>
<script type="text/javascript">

    // populate the SOA Event Test
    document.getElementById("eventName").value = "Test 4 hour";
    document.getElementById("eventDescription").value = "Test Event 4 Hour Test";
    document.getElementById("name").value = "key";
    document.getElementById("value").value = "value";
    document.getElementById("numberOfEvents").value = "15";
    document.getElementById("numberOfThreads").value = "100";
    document.getElementById("eventInterval").value = "28800";

    // populate the Create Parts test
    document.getElementById("partName").value = "testPart4Hour";
    document.getElementById("partCage").value = "91583";
    document.getElementById("numberOfParts").value = "1";
    document.getElementById("deleteParts").value = "true";
    document.getElementById("partInterval").value = "15000";

    // populate the Single Transactions test
    document.getElementById("numberOfTransactions").value = "67";
    document.getElementById("transactionThreads").value = "100";
    document.getElementById("singleInterval").value = "36000";

    // set the duration
    document.getElementById("duration").value = "14400000";

</script>
<%
        return;
    }

    // if the load 2 hour button was clicked, populate the jsp with values
    if (submitType.equals("Load 2 Hour Test")) {
%>
<script type="text/javascript">

    // populate the SOA Event Test
    document.getElementById("eventName").value = "Test 2 hour";
    document.getElementById("eventDescription").value = "Test Event 2 Hour test";
    document.getElementById("name").value = "key";
    document.getElementById("value").value = "value";
    document.getElementById("numberOfEvents").value = "25";
    document.getElementById("numberOfThreads").value = "100";
    document.getElementById("eventInterval").value = "28800";

    // populate the Create Parts test
    document.getElementById("partName").value = "testPart2hour";
    document.getElementById("partCage").value = "91583";
    document.getElementById("numberOfParts").value = "1";
    document.getElementById("deleteParts").value = true;
    document.getElementById("partInterval").value = "8571";

    // populate the Single Transactions test
    document.getElementById("numberOfTransactions").value = "60";
    document.getElementById("transactionThreads").value = "100";
    document.getElementById("singleInterval").value = "18000";

    // set the duration
    document.getElementById("duration").value = "7200000";

</script>
<%
        return;
    }

    // the go button was pressed, run the test
    if ((duration > 0) && ((numberOfEvents > 0) || (numberOfParts > 0) || (numberOfBatchTransactions > 0) || (numberOfSingleTransactions > 0))) {
        execute(request, out);
    }
%>
</body>
</html>