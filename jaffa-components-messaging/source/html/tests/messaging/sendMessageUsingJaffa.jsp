<%@page import="org.jaffa.modules.messaging.services.JmsClientHelper"%>
<%@page import="org.jaffa.util.BeanHelper"%>

<html>
    <head>
        <title>Send Messages using JaffaComponentsMessaging</title>
        <link href='/jaffa/themes/default/css/jaffa.css' type='text/css' rel='StyleSheet'>
    </head>
    
    <body>
        <h1>Send Messages using JaffaComponentsMessaging</h1>
        <br>
        
        <form action="sendMessageUsingJaffa.jsp" method="post">
            <table>
                <tr>
                    <td>Data Bean: </td>
                    <td>
                        <select name="dataBeanClassName">
                            <option value='org.jaffa.applications.jaffa.modules.admin.domain.User'>User</option>
                            <option value='org.jaffa.applications.jaffa.modules.admin.domain.UserRole'>UserRole</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Key value: </td>
                    <td><input type="text" name="keyValue"> <i>for composite keys enter a comma separated list of values</i></td>
                </tr>
                <tr><td colspan="2"><input type="submit" value="Send"></td></tr>
            </table>
        </form>
        <br>
        
        <%
        String dataBeanClassName = request.getParameter("dataBeanClassName");
        String keyValue = request.getParameter("keyValue");
        String[] keyValues = null;
        if (keyValue != null && keyValue.length() == 0)
            keyValue = null;
        if (keyValue != null)
            keyValues = keyValue.split(",");
        if (dataBeanClassName != null) {
            if (keyValues != null && keyValues.length > 0) {
                Object obj = Class.forName(dataBeanClassName).newInstance();
                if ("org.jaffa.applications.jaffa.modules.admin.domain.User".equals(dataBeanClassName)) {
                    BeanHelper.setField(obj, "userName", keyValues[0]);
                    String messageId = JmsClientHelper.send(obj);
                    out.println("Sent message " + messageId);
                } else if ("org.jaffa.applications.jaffa.modules.admin.domain.UserRole".equals(dataBeanClassName)) {
                    if (keyValues.length > 1) {
                        BeanHelper.setField(obj, "userName", keyValues[0]);
                        BeanHelper.setField(obj, "roleName", keyValues[1]);
                        String messageId = JmsClientHelper.send(obj);
                        out.println("Sent message " + messageId);
                    } else {
                        out.println("ERROR: Enter a comma separated list in keyValue containing at least 2 elements");
                    }
                } else {
                    out.println("ERROR: Unsupported data bean class: " + dataBeanClassName);
                }
            } else {
                out.println("ERROR: Enter the keyValue");
            }
        }
        %>
    </body>
</html>