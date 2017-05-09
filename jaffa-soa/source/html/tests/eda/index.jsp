<%@ page language="java" %>
<html>
<head>
    <title>EDA Test Screens</title>
    <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
</head>
<body>
<h1>EDA Test Screens</h1>
<ul>
    <li><a href="eventDbLoadTest.jsp">Event Database Load Tester</a>- Persists the event ot the database then
        immediately processes it without the use of JMS or ActiveMQ.
    <li><a href="eventLoadTest.jsp">Event Load Tester</a>- Processes events without persisting them.
    <li><a href="soaEventTest.jsp">Event Tester</a>- Processes and persists events.
    <li><a href="transactionLoadTest.jsp">Event/Transaction Load Tester</a>- Allows for events and transactions to be
        created at a specified interval for a specified duration.
    <li><a href="transactionTester.jsp">Transaction Tester Multi-threaded</a>- Submits multi-threaded transactions to be processed, all transactions
        are added to one Unit of Work.
    <li><a href="transactionTesterSingleton.jsp">Transaction Tester Singleton</a>- Submits singleton transactions to be processed, all transactions
        are added to one Unit of Work.
</ul>
</body>
</html>