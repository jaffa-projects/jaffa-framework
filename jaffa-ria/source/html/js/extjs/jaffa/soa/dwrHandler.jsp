<%--
dwrHandler.jsp

Generates javascript that registers handlers with the DWR Engine to cope with various errors.

When using serverTime:
1) Modifies DWR Engine's _serializeAll() function to serialize date objects into formatted strings.
2) Overrides the Date class to ensure that 'new Date()' always returns serverTime

@param ref the page being rendered

Should be included in the main JSP that loads all the java script using the following...
<script type="text/javascript" src="js/extjs/jaffa/soa/dwrHandler.jsp?ref=..."></script>
--%>

<%@page import="java.util.Date" %>
<%@page import="java.util.TimeZone" %>
<%@page import="org.jaffa.datatypes.Parser" %>
<%@page import="org.jaffa.session.ContextManagerFactory" %>
<%@ page import="org.jaffa.util.MessageHelper" %>
<%@ page import="org.jaffa.util.StringHelper" %>

/**
 * The textHtmlHandler is invoked when a DWR request encounters an expired Session.
 * This handler will attempt to reload the current page, which should first bring up the login page.
 */
dwr.engine.setTextHtmlHandler(function() {
  Ext.MessageBox.show( {
    title : '<%= MessageHelper.findMessage("label.Jaffa.DWR.Timeout.Title", null) %>',
    msg : '<%= MessageHelper.findMessage("label.Jaffa.DWR.Timeout.Message", null) %>',
    buttons : Ext.MessageBox.OK,
    fn: redirect,
    cls: 'ext-timeout-messageBox-toFront',
    icon: Ext.MessageBox.ERROR
  });
});

/**
* redirect the user to the login screen
*/
redirect = function () {
    document.location = params.appCtx + '/<%= StringHelper.escapeJavascript(request.getParameter("ref")) %>';
}

<%
// When using serverTime:
// 1) modify DWR Engine's _serializeAll() function to serialize date objects into formatted strings
// 2) Override the Date class to ensure that 'new Date()' always returns serverTime
Boolean useServerTime = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty("jaffa.widgets.useServerTime"));
if (useServerTime != null && useServerTime) {
  TimeZone serverTZ = TimeZone.getDefault();
%>
  // Maintain a reference to the original dwr.engine._serializeAll function.
  if (!dwr.engine._jaffa_orig_serializeAll)
    dwr.engine._jaffa_orig_serializeAll = dwr.engine._serializeAll;

  // The following format is used to marshal date values to the server.
  // NOTE: The format should match the one used in the DWR converter on the server.
  // NOTE: It is important to add a character, say 'T', to the formatted string, so that it
  // is not treated as a numeric 'milliseconds since 1970' value.
  dwr.engine._jaffa_dateFormat = 'Ymd\\THisu';
  
  // Serialize date object into a formatted string. Invoke the original function for all others.
  dwr.engine._serializeAll = function (batch, referto, data, name) {
    if (data != null && typeof data === "object" && data instanceof Date) {
      console.debug('Custom serializing the date ', data, ' to the format ', dwr.engine._jaffa_dateFormat);
      var value = data.format(dwr.engine._jaffa_dateFormat);
      batch.map[name] = "Date:" + value;
      console.debug('Date serialized to ', value);
    } else {
      console.debug('Invoking the original dwr.engine._serializeAll function for serializing a non-date object');
      dwr.engine._jaffa_orig_serializeAll(batch, referto, data, name);
    }
  };

  // Maintain a reference to the original Date class.
  if (!Jaffa.util._origDate)
    Jaffa.util._origDate = Date;

  // Override the Date class to ensure that 'new Date()' always returns serverTime.
  Date = function () {
    switch (arguments.length) {
      case 0:
        var d = new Jaffa.util._origDate();
        return new Jaffa.util._origDate(d.getTime() + (d.getTimezoneOffset() * 60000) + Date.serverTimezone.tzOffset);
      case 1:
        if (typeof arguments[0] === 'object') {
          return new Jaffa.util._origDate(arguments[0]);
        } else if (isNaN(arguments[0])) {
          // A string argument has been passed. Try to parse it using the original Date.parse routine.
          // An invalid string results in 'Invalid Date' in firefox and 'NaN' in IE.
          // If invalid, then assume the string is as per the special format 'dwr.engine._jaffa_dateFormat' and parse accordingly.
          var d = new Jaffa.util._origDate(arguments[0]);
          return d && d.toString() != 'Invalid Date' && d.toString() != 'NaN' ? d : Date.parseDate(arguments[0], dwr.engine._jaffa_dateFormat);
        } else if (typeof arguments[0] === 'string') {
          // A numeric argument has been passed as a string. Must be 'milliseconds since 1970'
          return new Jaffa.util._origDate(parseInt(arguments[0]));
        } else {
          // A numeric argument has been passed. Must be 'milliseconds since 1970'
          return new Jaffa.util._origDate(arguments[0]);
        }
      case 2:
        return new Jaffa.util._origDate(arguments[0], arguments[1]);
      case 3:
        return new Jaffa.util._origDate(arguments[0], arguments[1], arguments[2]);
      case 4:
        return new Jaffa.util._origDate(arguments[0], arguments[1], arguments[2], arguments[3]);
      case 5:
        return new Jaffa.util._origDate(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
      case 6:
        return new Jaffa.util._origDate(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]);
      case 7:
        return new Jaffa.util._origDate(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]);
      default:
        console.debug('WARNING: The Date class does not support more than 7 arguments: ', arguments);
        return null;
    }
  };
  Date.prototype = Jaffa.util._origDate.prototype;
  for (var e in Jaffa.util._origDate)
    Date[e] = Jaffa.util._origDate[e];
  
  // The static functions of the Date class are not copied by the above for-in loop. Assign them explicitly
  Date.parse = Jaffa.util._origDate.parse;
  Date.UTC = Jaffa.util._origDate.UTC;
  
  /**
   * Adds server timezone information to the Date class.
   */
  Date.serverTimezone = {
    longName: '<%= serverTZ.getDisplayName(false, TimeZone.LONG, request.getLocale()) %>',
    shortName: '<%= serverTZ.getDisplayName(false, TimeZone.SHORT, request.getLocale()) %>',
    tzOffset: parseInt('<%= serverTZ.getOffset((new Date()).getTime()) %>'), // current timezone offset.
    stdOffset: parseInt('<%= serverTZ.getRawOffset() %>') // standard offset
  };
<% } %>

/**
 * Returns the current datetime.
 * NOTE: This function has been added to support existing code.
 * @deprecated. Instead simply invoke 'new Date()'.
 */
Date.newDate = function () {
  return new Date();
};
