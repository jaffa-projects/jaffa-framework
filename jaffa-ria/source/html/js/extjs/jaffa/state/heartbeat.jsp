<%--
heartbeat.jsp

Generates javascript that sends a heartbeat to the server at regular intervals, ensuring that the user's session never times out.

Should be included in the main JSP that loads all the java script using the following...
<script type="text/javascript" src="js/extjs/jaffa/state/heartbeat.jsp"></script>
--%>

<%@ page import = "org.jaffa.session.ContextManagerFactory" %>

<%!
/** Resource to be pinged at regular intervals. Do not reference an image or html document, since the browser may cache such an artifact. */
private static final String RESOURCE_URL = "Readme.txt";

/** Assume a safety margin of 60 seconds. */
private static final int MARGIN = 60;
%>


<%
// Apply timeout management only if the session timeout is positive and exceeds a certain safety margin.
final int timeoutInSeconds = session.getMaxInactiveInterval();

// Get the enforce timeout property value
String isEnforceTimeout = (String) ContextManagerFactory.instance().getProperty("jaffa.jaffaRIA.enforceTimeout");

if (isEnforceTimeout ==  null || isEnforceTimeout.isEmpty()){
  isEnforceTimeout = "true";
}

// If the enforce timeout property is true, do not schedule the heartbeat timer
if ("false".equalsIgnoreCase(isEnforceTimeout) && (timeoutInSeconds > MARGIN)) {
%>

  // Send a heartbeat at regular intervals, such that the session never times out
  var jaffaSessionHeartbeatId = setInterval('sendHeartbeat()', <%= (timeoutInSeconds - MARGIN) * 1000 %>);

  // Sends a heartbeat by pinging a resource on the server
  function sendHeartbeat() {
    Ext.Ajax.request({
      url: '<%= RESOURCE_URL %>',
      success: function(response, options) {
        // Successfully pinged the resource. Do nothing.
      },
      failure: function(response, options) {
        // No response from the server. It could be down for maintenance.
        var msg = 'No response from the server. It could be down for maintenance. Start a new session.';
        console.warn(msg, response);

        // Stop sending any more heartbeats
        clearInterval(jaffaSessionHeartbeatId);

        // Mask the page
        Ext.get(document.body).mask(msg);
      }
    });
  }
<%}%>
