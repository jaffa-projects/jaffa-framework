<%--
This test will create the specified number of singleton transactions, add them all to one UOW and commit the UOW.
Once the UOW is committed, all of the transactions will be sent for consumption.
--%>
<%@page import="org.jaffa.transaction.tester.TestMessageSingleton,org.jaffa.persistence.UOW"%>
<%@page import="java.util.List,java.util.ArrayList,org.jaffa.util.StringHelper"%>

<%!
    /** Convert the input to HTML compatible String. */
    private String toHtml(Object obj) {
        return obj == null ? "" : StringHelper.convertToHTML(obj.toString());
    }

    private String[] submitTransactions(UOW uow,Long qty, String sendEmail, String fail, Long pause, Long dbQuery, String emailAddress, String[] dependsOn) throws Exception {


        List<String> dependsOnIds = new ArrayList<String>();
        System.out.println("SubmitTransactions");
        for(int p=0;p<qty;p++) {
            TestMessageSingleton tm = new TestMessageSingleton();
            if("on".equals(sendEmail)) {
                tm.setEmailAddress(emailAddress);
            }
            tm.setFail("on".equals(fail)?true:false);
            tm.setDelay(pause);
            tm.setDbQuery(dbQuery);
            String transactionId = uow.addMessage(tm,dependsOn);

            dependsOnIds.add(transactionId);
            System.out.println("TransactionId "+transactionId);
        }

        return dependsOnIds.toArray(new String[0]);
    }


    private void showResults(String[] firstTransIds,String[] middleTransIds,String[] lastTransIds,JspWriter out) throws Exception {


        out.write("    <h3>Results</h3>\n");
        out.write("    <table>\n");
        out.write("      <thead>\n");
        out.write("        <tr class='row0'>\n");
        out.write("          <td>First</td>\n");
        out.write("          <td>Middle</td>\n");
        out.write("          <td>Last</td>\n");
        out.write("        </tr>\n");
        out.write("      </thead>\n");
        out.write("      <tbody>\n");


        for(int p=0;(p<firstTransIds.length) || (p<middleTransIds.length) || (p<lastTransIds.length); p++) {
            out.write("        <tr class='row0'>\n");
            if(firstTransIds!=null && p<firstTransIds.length)
                out.write("<td><a target='_blank' href=/GOLD/jaffa/transaction/transactionmaintenance/main.jsp?id="+firstTransIds[p] + ">"+toHtml(firstTransIds[p]) + "</a></td>\n");
            else
                out.write("<td></td>\n");

            if(middleTransIds!=null && p<middleTransIds.length)
                out.write("<td><a target='_blank' href=/GOLD/jaffa/transaction/transactionmaintenance/main.jsp?id="+middleTransIds[p] + ">"+toHtml(middleTransIds[p]) + "</a></td>\n");
            else
                out.write("<td></td>\n");

            if(lastTransIds!=null && p<lastTransIds.length)
                out.write("<td><a target='_blank' href=/GOLD/jaffa/transaction/transactionmaintenance/main.jsp?id="+lastTransIds[p] + ">"+toHtml(lastTransIds[p]) + "</a></td>\n");
            else
                out.write("<td></td>\n");
        }

    }





    private void execute(HttpServletRequest request,JspWriter out) throws Exception {
        UOW uow = null;
        try {
            String event = request.getParameter("event");
            //if("Start".equals(event)) {
            uow = new UOW();



            List<String> dependsOnFirst = new ArrayList<String>();
            Long firstQty =  Long.parseLong(request.getParameter("firstQty"));


            String[] firstTransIds = submitTransactions(uow,
                                                        Long.parseLong(request.getParameter("firstQty")),
                                                        request.getParameter("firstEmailAddress"),
                                                        request.getParameter("firstFail"),
                                                        Long.parseLong(request.getParameter("firstDelay")),
                                                        Long.parseLong(request.getParameter("firstDbQuery")),
                                                        request.getParameter("emailAddress"),
                                                        null
            );

            String[] middleTransIds = submitTransactions(uow,
                                                         Long.parseLong(request.getParameter("middleQty")),
                                                         null,
                                                         request.getParameter("middleFail"),
                                                         Long.parseLong(request.getParameter("middleDelay")),
                                                         Long.parseLong(request.getParameter("middleDbQuery")),
                                                         request.getParameter("emailAddress"),
                                                         firstTransIds
            );

            String[] lastTransIds = submitTransactions(uow,
                                                       Long.parseLong(request.getParameter("lastQty")),
                                                       request.getParameter("lastEmailAddress"),
                                                       request.getParameter("lastFail"),
                                                       Long.parseLong(request.getParameter("lastDelay")),
                                                       Long.parseLong(request.getParameter("lastDbQuery")),
                                                       request.getParameter("emailAddress"),
                                                       middleTransIds
            );
            uow.commit();

            showResults(firstTransIds,middleTransIds,lastTransIds,out);

            //}
        }
        finally {
            if(uow!=null) {
                try {
                    uow.rollback();
                } catch(Exception e) {
                    out.println(e);
                    //the uow cannot be rolled back
                }
            }
        }
    }
%>
<html>
<head>
    <title>Transaction Tester Singleton</title>
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
        table        {
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
            color:red;
        }
        .false {
            color:gray;
        }
    </style>
</head>
<body>
<form action="transactionTesterSingleton.jsp" method="post">
    <h2>Transaction Tester Singleton</h2>
    <%

        Long firstQty =  (request!=null && request.getParameter("firstQty")!=null ? Long.parseLong(request.getParameter("firstQty")) : 0);
        Long middleQty = (request!=null && request.getParameter("middleQty")!=null ? Long.parseLong(request.getParameter("middleQty")) : 0);
        Long lastQty =   (request!=null && request.getParameter("lastQty")!=null ? Long.parseLong(request.getParameter("lastQty")) : 0);
    %>
    <table>
        <thead>
        <tr class='row0'>
            <td></td>
            <td>First</td>
            <td>Middle</td>
            <td>Last</td>
        </tr>
        </thead>
        <tr>
            <td>Quantity: </td>
            <td><input type="text" name="firstQty" value="0" size="5"></td>
            <td><input type="text" name="middleQty" value="0" size="5"></td>
            <td><input type="text" name="lastQty" value="0" size="5"></td>
        </tr>
        <tr>
            <td>Email: </td>
            <td><input type="checkbox" name="firstEmailAddress"</td>
            <td></td>
            <td><input type="checkbox" name="lastEmailAddress"</td>
        </tr>
        <tr>
            <td>Fail: </td>
            <td><input type="checkbox" name="firstFail"</td>
            <td><input type="checkbox" name="middleFail"</td>
            <td><input type="checkbox" name="lastFail"</td>
        </tr>
        <tr>
            <td>Pause(ms): </td>
            <td><input type="text" name="firstDelay" value="0" size="5"></td>
            <td><input type="text" name="middleDelay" value="0" size="5"></td>
            <td><input type="text" name="lastDelay" value="0" size="5"></td>
        </tr>
        <tr>
            <td>DBQuery: </td>
            <td><input type="text" name="firstDbQuery" value="0" size="5"></td>
            <td><input type="text" name="middleDbQuery" value="0" size="5"></td>
            <td><input type="text" name="lastDbQuery" value="0" size="5"></td>
        </tr>
        <tr>
            <td>Email Address: </td>
            <td colspan="3"><input type = "text" name="emailAddress" size="40"></td>
        </tr>
        <tr>
            <td colspan="4" align="right"><input type="submit" value="Go"></td>
        </tr>
    </table>
</form>
<%
    if((firstQty!=null && firstQty > 0) ||
            (middleQty!=null && middleQty > 0) ||
            (lastQty!=null && lastQty > 0)) {

        execute(request,out);
    }
%>
</body>
</html>
