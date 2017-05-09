<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="org.jaffa.persistence.UOW"%>
<%@page import="org.jaffa.datatypes.Parser"%>
<%@page import="org.jaffa.util.BeanHelper"%>


<html>
    <head>
        <title>Send Messages using JaffaComponentsMessaging in a transaction</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>

    <body>
        <h1>Send Messages using JaffaComponentsMessaging in a transaction</h1>
        <br>

        <form action="sendMessageUsingJaffaTransaction.jsp" method="post">
            <table>
                <tr>
                    <td>Key value: </td>
                    <td><input type="text" name="keyValue"> <i>for composite keys enter a comma separated list of values</i></td>
                </tr>
                <tr>
                    <td>Commit? </td>
                    <td><input type="checkbox" name="commit" value="true" checked></td>
                </tr>
                <tr><td colspan="2"><input type="submit" value="Send"></td></tr>
            </table>
        </form>
        <br>

        <%
        String keyValue = request.getParameter("keyValue");
        String[] keyValues = null;
        if (keyValue != null && keyValue.length() == 0)
            keyValue = null;
        if (keyValue != null)
            keyValues = keyValue.split(",");
        Boolean commit = Parser.parseBoolean(request.getParameter("commit"));
        if (keyValues != null && keyValues.length > 0) {
            List objects = new LinkedList();
            Object obj = null;
            
            obj = Class.forName("com.mirotechnologies.usersecurity.user.domain.User").newInstance();
            BeanHelper.setField(obj, "userId", keyValues[0]);
            if (keyValues.length > 1) {
                BeanHelper.setField(obj, "userName", keyValues[1]);
            }
            if (keyValues.length > 2) {
                BeanHelper.setField(obj, "password", keyValues[2]);
            }
            if (keyValues.length > 3) {
                BeanHelper.setField(obj, "employeeStatus", keyValues[3]);
            }
            if (keyValues.length > 4) {
                BeanHelper.setField(obj, "mainMenu", keyValues[4]);
            }            
            if (keyValues.length > 5) {
                BeanHelper.setField(obj, "emailAddress", keyValues[5]);
            }
            if (keyValues.length > 6) {
                BeanHelper.setField(obj, "timeZone", keyValues[6]);
            }            
            objects.add(obj);
            
            if (keyValues.length > 1) {
                obj = Class.forName("com.mirotechnologies.usersecurity.user.domain.UserRole").newInstance();
                BeanHelper.setField(obj, "UserId", keyValues[0]);
                if (keyValues.length > 7) {
                  BeanHelper.setField(obj, "roleName", keyValues[7]);
                }
                objects.add(obj);
            }
            
            
            // The following code will send the message using JaffaComponentsMessaging in a transaction
            UOW uow = null;
            try {
                uow = new UOW();
                for (Iterator itr = objects.iterator(); itr.hasNext(); )
                    uow.add(itr.next());
                
                if (commit != null && commit.booleanValue()) {
                    out.println("Committing the delivery of messages");
                    uow.commit();
                } else {
                    out.println("Rolling back the delivery of messages");
                    uow.rollback();
                }
            } finally {
                if (uow != null)
                    uow.rollback();
            }
        }
        %>
    </body>
</html>